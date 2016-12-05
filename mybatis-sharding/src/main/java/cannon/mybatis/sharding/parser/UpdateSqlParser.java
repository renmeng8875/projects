/**
 * 
 */
package cannon.mybatis.sharding.parser;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

/**
 * @author fangjialong
 * @date 2015年9月5日 下午3:55:28
 */
public class UpdateSqlParser implements SqlParser {

	private boolean inited = false;

	private Update statement;

	private List<Table> tables = new ArrayList<Table>();

	public UpdateSqlParser(Update statement) {
		this.statement = statement;
	}

	@Override
	public List<Table> getTables() {
		return tables;
	}

	public void init() {
		if (inited) {
			return;
		}
		inited = true;
		tables.addAll(statement.getTables());
	}

	@Override
	public String toSQL() {
		StatementDeParser deParser = new StatementDeParser(new StringBuilder());
		statement.accept(deParser);
		return deParser.getBuffer().toString();
	}

}