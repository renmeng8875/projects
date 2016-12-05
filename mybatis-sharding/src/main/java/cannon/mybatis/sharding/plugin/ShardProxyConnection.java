/**
 * 
 */
package cannon.mybatis.sharding.plugin;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author fangjialong
 * @date 2015年9月5日 下午12:22:13
 */
public interface ShardProxyConnection extends Connection {

	public String getLogicDatabase();

	public void prepare(ShardCondition condition) throws SQLException;

	public void prepare(String targetDatabase) throws SQLException;
}
