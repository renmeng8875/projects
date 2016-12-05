/**
 * 
 */
package cannon.mybatis.sharding.plugin;

/**
 * 分布式序列构造器
 * 
 * @author fangjialong
 * @date 2015年9月8日 下午4:49:52
 */
public interface ShardSequence {
	/**
	 * 获取序列构造
	 * 
	 * @return
	 */
	public long next();
}
