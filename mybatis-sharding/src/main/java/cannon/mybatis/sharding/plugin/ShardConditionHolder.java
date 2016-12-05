/**
 * 
 */
package cannon.mybatis.sharding.plugin;

/**
 * 该类用于指定分库分表
 * 
 * @author fangjialong
 * @date 2015年9月7日 下午4:55:37
 */
public class ShardConditionHolder {

	/**
	 * 分库分表规则线程缓存
	 */
	private static final ThreadLocal<ShardCondition> THREAD_LOCAL = new ThreadLocal<ShardCondition>();

	/**
	 * 设置规则缓存
	 * 
	 * @param sc
	 */
	public static void put(ShardCondition sc) {
		THREAD_LOCAL.set(sc);
	}

	/**
	 * 获取规则缓存
	 * 
	 * @return
	 */
	public static ShardCondition get() {
		return THREAD_LOCAL.get();
	}

	/**
	 * 清除规则缓存
	 */
	public static void remove() {
		THREAD_LOCAL.remove();
	}
}
