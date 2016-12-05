/**
 * 
 */
package cannon.mybatis.sharding.plugin;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.jdbc.ConnectionLogger;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cannon.mybatis.sharding.parser.SqlParser;
import cannon.mybatis.sharding.parser.SqlParserFactory;
import net.sf.jsqlparser.schema.Table;

/**
 * @author fangjialong
 * @date 2015年9月5日 上午9:03:30
 */
@Intercepts({ @org.apache.ibatis.plugin.Signature(type = StatementHandler.class, method = "prepare", args = {
		java.sql.Connection.class }) })
public class ShardPlugin implements Interceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShardPlugin.class);

	private final static Set<Class<?>> SINGLE_PARAM_CLASSES = new HashSet<Class<?>>();

	static {
		SINGLE_PARAM_CLASSES.add(int.class);
		SINGLE_PARAM_CLASSES.add(Integer.class);

		SINGLE_PARAM_CLASSES.add(long.class);
		SINGLE_PARAM_CLASSES.add(Long.class);

		SINGLE_PARAM_CLASSES.add(short.class);
		SINGLE_PARAM_CLASSES.add(Short.class);

		SINGLE_PARAM_CLASSES.add(byte.class);
		SINGLE_PARAM_CLASSES.add(Byte.class);

		SINGLE_PARAM_CLASSES.add(float.class);
		SINGLE_PARAM_CLASSES.add(Float.class);

		SINGLE_PARAM_CLASSES.add(double.class);
		SINGLE_PARAM_CLASSES.add(Double.class);

		SINGLE_PARAM_CLASSES.add(boolean.class);
		SINGLE_PARAM_CLASSES.add(Boolean.class);

		SINGLE_PARAM_CLASSES.add(char.class);
		SINGLE_PARAM_CLASSES.add(Character.class);

		SINGLE_PARAM_CLASSES.add(String.class);

	}

	/**
	 * 分库分表规则解析策略
	 */
	private final Map<String, ShardStrategy> strategies = new HashMap<String, ShardStrategy>();

	private final Field boundSqlField;

	public ShardPlugin() {
		try {
			boundSqlField = BoundSql.class.getDeclaredField("sql");
			boundSqlField.setAccessible(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		Object arg1 = (Object) args[0];
		ShardProxyConnection conn = null;

		if (arg1 instanceof ShardProxyConnection) {
			conn = (ShardProxyConnection) arg1;
		} else {
			if (!Proxy.isProxyClass(arg1.getClass())) {
				return invocation.proceed();
			}

			InvocationHandler handler = Proxy.getInvocationHandler(arg1);
			if (!(handler instanceof ConnectionLogger)) {
				return invocation.proceed();
			}

			ConnectionLogger connLogger = (ConnectionLogger) handler;
			Connection c = connLogger.getConnection();
			if (c instanceof ShardProxyConnection) {
				conn = (ShardProxyConnection) c;
			} else { // return
				invocation.proceed();
			}
		}

		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		BoundSql boundSql = statementHandler.getBoundSql();

		String originalSql = boundSql.getSql();
		LOGGER.debug("Shard Original SQL:{}", originalSql);

		SqlParser sqlParser = SqlParserFactory.getInstance().createParser(originalSql);
		List<Table> tables = sqlParser.getTables();

		if (tables.isEmpty()) {
			return invocation.proceed();
		}

		Table table = tables.get(0);
		boolean strict = false;
		String n = table.getName();
		String logicTableName = null;
		if (n.startsWith("`") && n.endsWith("`")) {
			strict = true;
			logicTableName = n.substring(1, n.length() - 1);
		} else {
			logicTableName = n;
		}

		ShardStrategy strategy = strategies.get(logicTableName);
		if (strategy == null) {
			throw new SQLException("Shard Strategy Query Failed");
		}
		ShardCondition condition = null;
		ShardCondition userSetting = ShardConditionHolder.get();
		if (userSetting != null) {
			condition = userSetting;
			ShardConditionHolder.remove();
		} else {
			Object parameterObject = boundSql.getParameterObject();
			Map<String, Object> params = null;
			if (SINGLE_PARAM_CLASSES.contains(parameterObject.getClass())) {
				// 单一参数
				List<ParameterMapping> mapping = boundSql.getParameterMappings();
				if (mapping != null && !mapping.isEmpty()) {
					ParameterMapping m = mapping.get(0);
					params = new HashMap<String, Object>();
					params.put(m.getProperty(), parameterObject);
				} else {
					params = Collections.emptyMap();
				}
			} else {
				// 对象参数
				if (parameterObject instanceof Map) {
					params = (Map<String, Object>) parameterObject;
				} else {
					params = new HashMap<String, Object>();
					BeanInfo beanInfo = Introspector.getBeanInfo(parameterObject.getClass());
					PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
					if (proDescrtptors != null && proDescrtptors.length > 0) {
						for (PropertyDescriptor propDesc : proDescrtptors) {
							params.put(propDesc.getName(), propDesc.getReadMethod().invoke(parameterObject));
						}
					}
				}
			}
			condition = strategy.parse(params);
		}

		conn.prepare(condition);
		for (Table t : tables) {
			n = t.getName();
			logicTableName = null;
			if (n.startsWith("`") && n.endsWith("`")) {
				strict = true;
				logicTableName = n.substring(1, n.length() - 1);
			} else {
				strict = false;
				logicTableName = n;
			}
			if (strict) {
				String targetTableName = "`" + logicTableName + condition.getTableSuffix() + "`";
				t.setName(targetTableName);
				t.setSchemaName("`" + conn.getLogicDatabase() + condition.getDatabaseSuffix() + "`");
			} else {
				t.setName(logicTableName + condition.getTableSuffix());
				t.setSchemaName(conn.getLogicDatabase() + condition.getDatabaseSuffix());
			}
		}

		String targetSQL = sqlParser.toSQL();

		LOGGER.debug("Shard Convert SQL:{}", targetSQL);

		boundSqlField.set(boundSql, targetSQL);
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		String configsLocation = properties.getProperty("configsLocation");
		if (configsLocation == null) {
			throw new IllegalArgumentException(
					"ShardPlugin[" + getClass().getName() + "] Property[configsLocation] Cannot Empty");
		}
		ClassLoader classLoader = this.getClass().getClassLoader();

		InputStream configInputStream = null;
		InputStream validateInputStream = null;
		InputStream xsdInputStream = null;
		try {

			String clazzName = this.getClass().getName();
			String xsdPath = clazzName.substring(0, clazzName.lastIndexOf('.') + 1).replace('.', '/')
					+ "mybatis-sharding-config.xsd";

			xsdInputStream = classLoader.getResourceAsStream(xsdPath);
			configInputStream = classLoader.getResourceAsStream(configsLocation);
			validateInputStream = classLoader.getResourceAsStream(configsLocation);

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(new StreamSource(xsdInputStream));
			Validator validator = schema.newValidator();
			SAXParser parser = factory.newSAXParser();
			SAXReader reader = new SAXReader(parser.getXMLReader());
			validator.validate(new StreamSource(validateInputStream));

			Document document = reader.read(configInputStream);
			Element root = document.getRootElement();
			parseStrategies(root);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (validateInputStream != null) {
					validateInputStream.close();
				}
			} catch (IOException e) {
				// ignore
			}

			try {
				if (xsdInputStream != null) {
					xsdInputStream.close();
				}
			} catch (IOException e) {
				// ignore
			}

			try {
				if (configInputStream != null) {
					configInputStream.close();
				}
			} catch (IOException e) {
				// ignore
			}
		}

	}

	/**
	 * @param root
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private void parseStrategies(Element root)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		List<?> strategies = root.elements("strategy");
		if (strategies != null) {
			for (Object o : strategies) {
				Element strategy = (Element) o;
				String logicTable = strategy.attribute("logicTable").getStringValue();
				String strategyClass = strategy.attribute("class").getStringValue();
				Class<?> clazz = Class.forName(strategyClass);
				ShardStrategy shardStrategy = (ShardStrategy) clazz.newInstance();
				if (this.strategies.containsKey(logicTable)) {
					throw new IllegalArgumentException("LogicTable[" + logicTable + "] Duplicate");
				}
				this.strategies.put(logicTable, shardStrategy);
			}
		}
	}

}