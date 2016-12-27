/**
 * 
 */
package cannon.mybatis.sharding.parser;

import java.io.StringReader;
import java.sql.SQLException;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

/**
 * @author fangjialong
 * @date 2015年9月5日 下午1:49:22
 */
public class SqlParserFactory {

	private static SqlParserFactory instance = new SqlParserFactory();

	public static SqlParserFactory getInstance() {
		return instance;
	}

	private final CCJSqlParserManager manager;

	public SqlParserFactory() {
		manager = new CCJSqlParserManager();
	}

	public SqlParser createParser(String originalSql) throws SQLException {
		try {
			Statement statement = manager.parse(new StringReader(originalSql));
			if (statement instanceof Select) {
				SelectSqlParser select = new SelectSqlParser((Select) statement);
				select.init();
				return select;
			} else if (statement instanceof Update) {
				UpdateSqlParser update = new UpdateSqlParser((Update) statement);
				update.init();
				return update;
			} else if (statement instanceof Insert) {
				InsertSqlParser insert = new InsertSqlParser((Insert) statement);
				insert.init();
				return insert;
			} else if (statement instanceof Delete) {
				DeleteSqlParser delete = new DeleteSqlParser((Delete) statement);
				delete.init();
				return delete;
			} else {
				throw new SQLException("Unsupported Parser[" + statement.getClass().getName() + "]");
			}
		} catch (JSQLParserException e) {
			throw new SQLException("SQL Parse Failed", e);
		}

	}
	
	public static void main(String[] args) throws JSQLParserException, SQLException {
		SqlParser sqlParser = SqlParserFactory.getInstance().createParser("select * from user_00 where t.no=?");
		System.out.println(sqlParser.getTables().get(0));
		System.out.println(sqlParser.toSQL());
		
	}

}
