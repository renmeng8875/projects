/**
 * 
 */
package cannon.mybatis.sharding.plugin;

/**
 * @author fangjialong
 * @date 2015年9月5日 上午8:59:11
 */
public final class ShardCondition {

	/**
	 * 库后缀
	 */
	private String databaseSuffix;

	/**
	 * 表后缀
	 */
	private String tableSuffix;

	/**
	 * @return the databaseSuffix
	 */
	public String getDatabaseSuffix() {
		return databaseSuffix;
	}

	/**
	 * @param databaseSuffix
	 *            the databaseSuffix to set
	 */
	public void setDatabaseSuffix(String databaseSuffix) {
		this.databaseSuffix = databaseSuffix;
	}

	/**
	 * @return the tableSuffix
	 */
	public String getTableSuffix() {
		return tableSuffix;
	}

	/**
	 * @param tableSuffix
	 *            the tableSuffix to set
	 */
	public void setTableSuffix(String tableSuffix) {
		this.tableSuffix = tableSuffix;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShardCondition[databaseSuffix=" + databaseSuffix + ",tableSuffix=" + tableSuffix + "]";
	}

}
