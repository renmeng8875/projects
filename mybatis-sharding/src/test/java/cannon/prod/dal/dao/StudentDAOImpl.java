/**
 * 
 */
package cannon.prod.dal.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import cannon.prod.dal.model.Student;

/**
 * @author fangjialong
 * @date 2015年9月4日 下午4:30:29
 */
public class StudentDAOImpl extends SqlSessionDaoSupport implements StudentDAO {

	public Student getByNo(String no) {
		return getSqlSession().selectOne("cannon.prod.dal.mapper.Student.getByNo", no);
	}

	@Override
	public int deleteByNo(String no) {
		return getSqlSession().delete("cannon.prod.dal.mapper.Student.deleteByNo", no);
	}

	@Override
	public void create(Student student) {
		getSqlSession().insert("cannon.prod.dal.mapper.Student.create", student);
	}

	@Override
	public void update(Student student) {
		getSqlSession().insert("cannon.prod.dal.mapper.Student.update", student);
	}

	@Override
	public List<Student> getByTable() {
		return getSqlSession().selectList("cannon.prod.dal.mapper.Student.getByTable");
	}
}
