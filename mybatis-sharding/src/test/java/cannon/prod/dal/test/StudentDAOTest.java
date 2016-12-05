package cannon.prod.dal.test;
/**
 * 
 */

import java.util.List;

import org.apache.ibatis.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cannon.prod.dal.dao.StudentDAO;
import cannon.prod.dal.model.Student;

import cannon.mybatis.sharding.plugin.ShardCondition;
import cannon.mybatis.sharding.plugin.ShardConditionHolder;
import junit.framework.Assert;

/**
 * @author fangjialong
 * @date 2015年9月4日 下午5:02:53
 */
public class StudentDAOTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentDAOTest.class);
	private ClassPathXmlApplicationContext context;
	private StudentDAO studentDAO;

	@Before
	public void init() {
		try {
			LogFactory.useNoLogging();
			this.context = new ClassPathXmlApplicationContext("META-INF/spring/applicationContext.xml");
			this.studentDAO = context.getBean(StudentDAO.class);
		} catch (RuntimeException e) {
			LOGGER.error("init failed", e);
			throw e;
		}
	}

	@Test
	public void testSelect() throws Exception {

		Assert.assertNotNull(studentDAO);
		Student student = studentDAO.getByNo("0001");
		System.out.println(student);
		LOGGER.info("Query Result:{}", student);
		student = studentDAO.getByNo("1001");
		LOGGER.info("Query Result:{}", student);

	}

	@Test
	public void testInsert() throws Exception {
	    for (int i = 0; i < 20; i++) {
	    	
	    	Student s = new Student();
			s.setNo(i+"");
			s.setName("wwh"+i);
			s.setSex(i%2);
			studentDAO.create(s);
		}
	   
	}

	@Test
	public void testUpdate() {
		Student s = new Student();
		s.setNo("0001");
		s.setName("房佳龙");

		studentDAO.update(s);
	}
	
	@Test
	public void getByTable() {
		ShardCondition sc = new ShardCondition();
		sc.setDatabaseSuffix("_01");
		sc.setTableSuffix("_01");
		ShardConditionHolder.put(sc);
		List<Student> list = studentDAO.getByTable();
		for(Student s:list){
			LOGGER.info("getByTable Result:{}", s);
		}
	}

	@After
	public void destroy() {
		if(context != null){
			context.close();
		}
	}
}
