/**
 * 
 */
package cannon.prod.dal.dao;

import java.util.List;

import cannon.prod.dal.model.Student;

/**
 * @author fangjialong
 * @date 2015年9月4日 下午4:27:10
 */
public interface StudentDAO {

	/**
	 * @param id
	 * @return
	 */
	public Student getByNo(String no);

	public int deleteByNo(String no);

	public void create(Student student);

	public void update(Student student);
	
	public List<Student> getByTable();

}
