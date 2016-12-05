/**
 * 
 */
package cannon.prod.dal.test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

import org.apache.ibatis.logging.LogFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cannon.mybatis.sharding.plugin.ShardSequence;
import cannon.mybatis.sharding.plugin.ShardSequenceFactory;

/**
 * @author fangjialong
 * @date 2015年9月8日 下午6:02:47
 */
public class ShardSequenceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentDAOTest.class);

	private static ConcurrentLinkedQueue<Long> sequences = new ConcurrentLinkedQueue<Long>();

	/**
	 * 模拟多台服务器，多个线程同时创建sequence
	 * 
	 * @throws InterruptedException
	 **/
	@Test
	public void testConcurrent() throws Exception {
		LogFactory.useNoLogging();
		final CountDownLatch cdl = new CountDownLatch(10);
		for (int z = 0; z < 10; z++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						createSequence();
						cdl.countDown();
					} catch (Exception e) {
						LOGGER.error("Test Error", e);
					}
				}
			}).start();
		}
		cdl.await();
		Set<Long> ss = new HashSet<Long>();
		// 检测是否重复
		boolean i = false;
		for (long s : sequences) {
			if (ss.contains(s)) {
				i = true;
				LOGGER.error("Duplicate Sequence:{}", s);
			} else {
				ss.add(s);
			}
		}
		if (!i) {
			LOGGER.info("None Duplicate Sequence");
		}
	}

	private void createSequence() throws Exception {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					"META-INF/spring/applicationContext.xml");
			final ShardSequenceFactory sequenceFactory = context.getBean(ShardSequenceFactory.class);
			final CountDownLatch cdl = new CountDownLatch(50);
			for (int i = 0; i < 50; i++) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							for (int q = 0; q < 100; q++) {
								// 为目标物理库创建Sequence序列
								ShardSequence ss = sequenceFactory.getSequence("test", "_01", "_01");
								long sequence = ss.next();
								sequences.offer(sequence);
								// LOGGER.info("Create Sequence:{}", sequence);
							}
						} catch (RuntimeException e) {
							LOGGER.error("create failed", e);
						}
						cdl.countDown();
					}
				}).start();
			}
			cdl.await();
			context.close();
		} catch (RuntimeException e) {
			LOGGER.error("create failed", e);
			throw e;
		}
	}

}
