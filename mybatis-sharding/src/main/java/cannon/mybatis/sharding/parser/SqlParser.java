/**
 * 
 */
package cannon.mybatis.sharding.parser;

import java.util.List;

import net.sf.jsqlparser.schema.Table;

/**
 * @author fangjialong
 * @date 2015年9月5日 下午1:55:12
 */
public interface SqlParser {

	public List<Table> getTables();
	
	public String toSQL();

}
