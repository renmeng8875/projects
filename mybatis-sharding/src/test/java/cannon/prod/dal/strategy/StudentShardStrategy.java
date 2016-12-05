/**
 * 
 */
package cannon.prod.dal.strategy;

import java.util.Map;

import cannon.mybatis.sharding.plugin.ShardCondition;
import cannon.mybatis.sharding.plugin.ShardStrategy;


/**
 * @author fangjialong
 * @date 2015年9月5日 下午2:42:01
 */
public class StudentShardStrategy implements ShardStrategy {

	@Override
	public ShardCondition parse(Map<String, Object> params) {
		String no = (String) params.get("no");
		char c = no.charAt(0);
		ShardCondition condition = new ShardCondition();
		if (c == '1') {
			condition.setDatabaseSuffix("_01");
			condition.setTableSuffix("_01");
		} else {
			condition.setDatabaseSuffix("_00");
			condition.setTableSuffix("_00");
		}
		return condition;
	}

}
