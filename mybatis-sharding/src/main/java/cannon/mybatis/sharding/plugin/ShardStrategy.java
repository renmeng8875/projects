/**
 * 
 */
package cannon.mybatis.sharding.plugin;

import java.util.Map;

/**
 * @author fangjialong
 * @date 2015年9月5日 上午8:56:49
 */
public interface ShardStrategy {

	/**
	 * @param params
	 * @return
	 */
	public ShardCondition parse(Map<String, Object> params);
}
