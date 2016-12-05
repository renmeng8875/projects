/**
 * 
 */
package cannon.mybatis.sharding.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import javax.sql.DataSource;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 分布式数据源 可以理解成逻辑数据源，负责管理所有物理数据源集群 使用者可以将多个数据源理解成一个逻辑数据源，执行DB操作时针对该逻辑数据源操作
 * 
 * @author fangjialong
 * @date 2015年9月5日 上午11:42:09
 */
public class ShardDataSource implements DataSource {

	private static Logger LOGGER = LoggerFactory.getLogger(ShardDataSource.class);

	/** 分布式数据库容器 **/
	private final Map<String, ComboPooledDataSource> datasources = new HashMap<String, ComboPooledDataSource>();

	/** 全局配置 **/
	private final Map<String, String> globalConfigs = new HashMap<String, String>();

	/** 配置路径 **/
	private String configsLocation;

	/** 逻辑数据库名称 **/
	private String logicDatabase;

	private boolean inited = false;

	public void init() throws Exception {
		if (inited) {
			return;
		}
		if (configsLocation == null) {
			throw new IllegalArgumentException(
					"ShardPlugin[" + getClass().getName() + "] Property[configsLocation] Cannot Empty");
		}
		LOGGER.debug("Configs Location:{}", configsLocation);
		ClassLoader classLoader = this.getClass().getClassLoader();
		InputStream configInputStream = null;
		InputStream validateInputStream = null;
		InputStream xsdInputStream = null;
		try {
			String clazzName = this.getClass().getName();
			String xsdPath = clazzName.substring(0, clazzName.lastIndexOf('.') + 1).replace('.', '/')
					+ "mybatis-sharding-db.xsd";

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
			parseRoot(root);
			inited = true;
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
	 * @throws Exception
	 */
	private void parseRoot(Element root) throws Exception {
		this.logicDatabase = root.element("logicName").attribute("value").getStringValue();

		Element configs = root.element("configs");
		if (configs != null) {
			List<?> properties = configs.elements("property");
			for (Object o : properties) {
				Element property = (Element) o;
				String name = property.attribute("name").getStringValue();
				String value = property.attribute("value").getStringValue();
				this.globalConfigs.put(name, value);
			}
		}

		List<?> databases = root.elements("database");
		for (Object o : databases) {
			Element database = (Element) o;
			parseDatabase(database);
		}
	}

	/**
	 * @param database
	 * @throws Exception
	 */
	private void parseDatabase(Element database) throws Exception {
		String suffix = database.attribute("suffix").getStringValue();
		String targetDatabase = this.logicDatabase + suffix;
		String username = database.attribute("username").getStringValue();
		String password = database.attribute("password").getStringValue();
		String jdbcUrl = database.attribute("jdbcUrl").getStringValue();
		List<?> properties = database.elements("property");
		Map<String, String> configs = new HashMap<String, String>();
		for (Object o : properties) {
			Element property = (Element) o;
			String name = property.attribute("name").getStringValue();
			String value = property.attribute("value").getStringValue();
			configs.put(name, value);
		}
		int minPoolSize = 2;
		int maxPoolSize = 8;
		String driverClass = "com.mysql.jdbc.Driver";
		int maxIdleTime = 900;
		int idleConnectionTestPeriod = 1800;
		if (configs.containsKey("minPoolSize")) {
			minPoolSize = Integer.parseInt(configs.get("minPoolSize"));
		} else if (globalConfigs.containsKey("minPoolSize")) {
			minPoolSize = Integer.parseInt(globalConfigs.get("minPoolSize"));
		}

		if (configs.containsKey("maxPoolSize")) {
			maxPoolSize = Integer.parseInt(configs.get("maxPoolSize"));
		} else if (globalConfigs.containsKey("maxPoolSize")) {
			maxPoolSize = Integer.parseInt(globalConfigs.get("maxPoolSize"));
		}

		if (configs.containsKey("driverClass")) {
			driverClass = configs.get("driverClass");
		} else if (globalConfigs.containsKey("driverClass")) {
			driverClass = globalConfigs.get("driverClass");
		}

		if (configs.containsKey("maxIdleTime")) {
			maxIdleTime = Integer.parseInt(configs.get("maxIdleTime"));
		} else if (globalConfigs.containsKey("maxIdleTime")) {
			maxIdleTime = Integer.parseInt(globalConfigs.get("maxIdleTime"));
		}

		if (configs.containsKey("idleConnectionTestPeriod")) {
			idleConnectionTestPeriod = Integer.parseInt(configs.get("idleConnectionTestPeriod"));
		} else if (globalConfigs.containsKey("idleConnectionTestPeriod")) {
			idleConnectionTestPeriod = Integer.parseInt(globalConfigs.get("idleConnectionTestPeriod"));
		}

		Class.forName(driverClass);
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(driverClass);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUser(username);
		dataSource.setPassword(password);
		dataSource.setMinPoolSize(minPoolSize);
		dataSource.setMaxPoolSize(maxPoolSize);

		dataSource.setMaxIdleTime(maxIdleTime);
		// 每隔1800秒检查间接持中的空闲连接
		dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);

		datasources.put(targetDatabase, dataSource);

	}

	public void destroy() {
		for (ComboPooledDataSource ds : datasources.values()) {
			ds.close();
		}
	}

	/**
	 * @param configsLocation
	 *            the configsLocation to set
	 */
	public void setConfigsLocation(String configsLocation) {
		this.configsLocation = configsLocation;
	}

	@Override
	public Connection getConnection() throws SQLException {
		if (!inited) {
			throw new SQLException("Shard Data Source Uninitialized");
		}
		return new ShardProxyConnectionImpl();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		if (!inited) {
			throw new SQLException("Shard Data Source Uninitialized");
		}
		return new ShardProxyConnectionImpl(username, password);
	}

	/**
	 * @return the logicDatabase
	 */
	public String getLogicDatabase() {
		return logicDatabase;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return DriverManager.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		DriverManager.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		DriverManager.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return DriverManager.getLoginTimeout();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException(getClass() + " is not a wrapper.");
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	private class ShardProxyConnectionImpl implements Connection, ShardProxyConnection {

		private Connection instance = null;

		private boolean autoCommit = true;

		private boolean closed = false;

		private boolean prepared = false;

		private String username = null;

		private String password = null;

		public ShardProxyConnectionImpl() {

		}

		/**
		 * @param username
		 * @param password
		 */
		public ShardProxyConnectionImpl(String username, String password) {
			this.username = username;
			this.password = password;
		}

		@Override
		public String getLogicDatabase() {
			return logicDatabase;
		}

		public void prepare(ShardCondition condition) throws SQLException {
			if (prepared) {
				throw new SQLException("Shard Proxy Connection Initialized");
			}
			prepared = true;
			String targetDatabase = logicDatabase + condition.getDatabaseSuffix();
			prepare(targetDatabase);
		}

		public void prepare(String targetDatabase) throws SQLException {
			DataSource datasource = datasources.get(targetDatabase);
			if (datasource == null) {
				throw new SQLException("Shard Datasource Unavailable");
			}
			if (username == null && password == null) {
				instance = datasource.getConnection();
			} else {
				instance = datasource.getConnection(username, password);
			}
			instance.setAutoCommit(autoCommit);
			LOGGER.debug("Prepare Connection,Target Database[{}]", targetDatabase);
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.unwrap(iface);
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.isWrapperFor(iface);
		}

		@Override
		public Statement createStatement() throws SQLException {

			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.createStatement();
		}

		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.prepareStatement(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.prepareCall(sql);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.nativeSQL(sql);
		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			if (instance == null) {
				this.autoCommit = autoCommit;
			} else {
				instance.setAutoCommit(autoCommit);
			}
		}

		@Override
		public boolean getAutoCommit() throws SQLException {
			if (instance == null) {
				return autoCommit;
			} else {
				return instance.getAutoCommit();
			}
		}

		@Override
		public void commit() throws SQLException {
			if (instance != null) {
				instance.commit();
			}
		}

		@Override
		public void rollback() throws SQLException {
			if (instance != null) {
				instance.rollback();
			}
		}

		@Override
		public void close() throws SQLException {
			if (instance != null) {
				instance.close();
			} else {
				closed = true;
			}
		}

		@Override
		public boolean isClosed() throws SQLException {
			if (instance != null) {
				return instance.isClosed();
			} else {
				return closed;
			}
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.getMetaData();
		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			instance.setReadOnly(readOnly);
		}

		@Override
		public boolean isReadOnly() throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.isReadOnly();
		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			instance.setCatalog(catalog);
		}

		@Override
		public String getCatalog() throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.getCatalog();
		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			instance.setTransactionIsolation(level);
		}

		@Override
		public int getTransactionIsolation() throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.getTransactionIsolation();
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.getWarnings();
		}

		@Override
		public void clearWarnings() throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			instance.clearWarnings();
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.createStatement(resultSetType, resultSetConcurrency);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.prepareCall(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.getTypeMap();
		}

		@Override
		public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			instance.setTypeMap(map);
		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			instance.setHoldability(holdability);
		}

		@Override
		public int getHoldability() throws SQLException {
			return instance.getHoldability();
		}

		@Override
		public Savepoint setSavepoint() throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.setSavepoint();
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.setSavepoint(name);
		}

		@Override
		public void rollback(Savepoint savepoint) throws SQLException {
			if (instance != null) {
				instance.rollback(savepoint);
			}
		}

		@Override
		public void releaseSavepoint(Savepoint savepoint) throws SQLException {
			if (instance != null) {
				instance.releaseSavepoint(savepoint);
			}
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.prepareStatement(sql, autoGeneratedKeys);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.prepareStatement(sql, columnIndexes);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.prepareStatement(sql, columnNames);
		}

		@Override
		public Clob createClob() throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.createClob();
		}

		@Override
		public Blob createBlob() throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.createBlob();
		}

		@Override
		public NClob createNClob() throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.createNClob();
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.createSQLXML();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.isValid(timeout);
		}

		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			if (instance == null) {
				throw new RuntimeException("Shard Proxy Connection Uninitialized");
			}
			instance.setClientInfo(name, value);
		}

		@Override
		public void setClientInfo(Properties properties) throws SQLClientInfoException {
			if (instance == null) {
				throw new RuntimeException("Shard Proxy Connection Uninitialized");
			}
			instance.setClientInfo(properties);
		}

		@Override
		public String getClientInfo(String name) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.getClientInfo(name);
		}

		@Override
		public Properties getClientInfo() throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.getClientInfo();
		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.createArrayOf(typeName, elements);
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			if (instance == null) {
				throw new SQLException("Shard Proxy Connection Uninitialized");
			}
			return instance.createStruct(typeName, attributes);
		}

		@Override
		public void setSchema(String schema) throws SQLException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getSchema() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void abort(Executor executor) throws SQLException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setNetworkTimeout(Executor executor, int milliseconds)
				throws SQLException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getNetworkTimeout() throws SQLException {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	@Override
	public java.util.logging.Logger getParentLogger()
			throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
