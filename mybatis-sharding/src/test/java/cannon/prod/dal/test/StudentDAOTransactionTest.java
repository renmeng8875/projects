/**
 * 
 */
package cannon.prod.dal.test;

import org.apache.ibatis.logging.LogFactory;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import cannon.prod.dal.dao.StudentDAO;
import cannon.prod.dal.model.Student;

/**
 * @author fangjialong
 * @date 2015年9月5日 下午4:27:50
 */
public class StudentDAOTransactionTest {
	private ClassPathXmlApplicationContext context;
	private StudentDAO studentDAO;
	private TransactionTemplate tt;
	@Test
	public void test() {
		LogFactory.useNoLogging();
		this.context = new ClassPathXmlApplicationContext("META-INF/spring/applicationContext.xml");
		this.studentDAO = context.getBean(StudentDAO.class);
		this.tt = context.getBean("distributeDefaultTransactionTemplate", TransactionTemplate.class);
		tt.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				Student s = new Student();
				s.setNo("0001");
				s.setName("房佳龙");
				studentDAO.update(s);
				status.setRollbackOnly();
			}
		});
		context.close();
	}
}
