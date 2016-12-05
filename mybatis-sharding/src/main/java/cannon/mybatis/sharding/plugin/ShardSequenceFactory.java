/**
 * 
 */
package cannon.mybatis.sharding.plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 如果需要使用分库分表Sequence需要为每个物理表所在的库创建Sequence表
 * 详细DDL请查看mybatis-sharding-sequence.sql
 * @author fangjialong
 * @date 2015年9月8日 下午4:42:30
 */
public class ShardSequenceFactory {

	/** LOGGER **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ShardSequenceFactory.class);

	/** 序列存储表 **/
	private String sequenceLogicTable;

	/** 序列缓存递增步长 **/
	private long step = 100;

	/** 分布式数据源 **/
	private ShardDataSource database;

	private Map<String, Map<String, ShardSequence>> sequences = new ConcurrentHashMap<String, Map<String, ShardSequence>>();

	public ShardSequence getSequence(String logicTable, String dbSuffix, String tbSuffix) {

		String logicDatabase = database.getLogicDatabase();

		Map<String, ShardSequence> container = sequences.get(logicTable);

		// 虽然JDK乱序执行无法保证该处锁的有效性，但也能最大限度的防止重复创建
		if (container == null) {
			synchronized (logicTable) {
				container = sequences.get(logicTable);
				if (container == null) {
					container = new HashMap<String, ShardSequence>();
					sequences.put(logicTable, container);
				}
			}
		}

		String targetTable = logicTable + tbSuffix;
		String targetDatabase = logicDatabase + dbSuffix;
		String key = targetDatabase + "." + targetTable;
		ShardSequence sequence = container.get(key);
		
		// 虽然JDK乱序执行无法保证该处锁的有效性，但也能最大限度的防止重复创建
		if (sequence == null) {
			synchronized (this) {
				sequence = container.get(key);
				if (sequence == null) {
					sequence = new ShardSequenceImpl(targetDatabase, logicTable, tbSuffix);
					container.put(key, sequence);
				}
			}
		}
		return sequence;
	}

	/**
	 * @param step
	 *            the step to set
	 */
	public void setStep(long step) {
		this.step = step;
	}

	/**
	 * @param database
	 *            the database to set
	 */
	public void setDatabase(ShardDataSource database) {
		this.database = database;
	}

	/**
	 * @param sequenceLogicTable
	 *            the sequenceLogicTable to set
	 */
	public void setSequenceLogicTable(String sequenceLogicTable) {
		this.sequenceLogicTable = sequenceLogicTable;
	}

	private class ShardSequenceImpl implements ShardSequence {

		private final String targetDatabase;
		private final String logicTable;
		private final String tbSuffix;

		private AtomicLong sequence = null;
		private long maxSequence = 0;

		private ShardSequenceImpl(String targetDatabase, String logicTable, String tbSuffix) {
			this.targetDatabase = targetDatabase;
			this.logicTable = logicTable;
			this.tbSuffix = tbSuffix;
		}

		/**
		 * @see cannon.mybatis.sharding.plugin.ShardSequence#next()
		 */
		@Override
		public synchronized long next() {
			if (sequence == null) {
				refresh();
			}
			long now = sequence.incrementAndGet();
			if (now > maxSequence) {
				refresh();
				return next();
			}
			return now;
		}

		private void refresh() {
			ShardProxyConnection conn = null;
			try {
				conn = (ShardProxyConnection) database.getConnection();
				conn.prepare(targetDatabase);
				refresh(conn);
			} catch (Throwable t) {
				throw new ShardException(t);
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						LOGGER.error("Shard Connection Close Failed", e);
					}
				}
			}
		}

		private void refresh(Connection conn) throws SQLException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				long nowStep = getCurrentStep(conn);
				long toStep = nowStep + ShardSequenceFactory.this.step;
				String updateSql = "UPDATE `" + targetDatabase + "`." + "`" + sequenceLogicTable + tbSuffix
						+ "` SET `gmt_modified`=now(),`sequence`=? WHERE `sequence`=?";
				ps = conn.prepareStatement(updateSql);
				ps.setLong(1, toStep);
				ps.setLong(2, nowStep);
				int result = ps.executeUpdate();
				if (result > 0) {
					maxSequence = toStep;
					sequence = new AtomicLong(nowStep);
				} else {
					refresh();
				}
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			}

		}

		private long getCurrentStep(Connection conn) throws SQLException {

			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "SELECT * FROM `" + targetDatabase + "`." + "`" + sequenceLogicTable + tbSuffix
						+ "` WHERE `logic_table`=? LIMIT 1";

				ps = conn.prepareStatement(sql);
				ps.setString(1, logicTable);
				rs = ps.executeQuery();
				if (!rs.next()) {
					create(conn);
					refresh(conn);
				}
				long nowStep = rs.getLong("sequence");
				return nowStep;
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			}
		}

		private void create(Connection conn) throws SQLException {
			PreparedStatement ps = null;
			try {
				String sql = "INSERT INTO `" + targetDatabase + "`." + "`" + sequenceLogicTable + tbSuffix
						+ "` (`gmt_create`,`gmt_modified`,`logic_table`,`sequence`)VALUES(now(),now(),?,0)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, this.logicTable);
				ps.executeUpdate();
			} finally {
				if (ps != null) {
					ps.close();
				}
			}
		}

	}

}
