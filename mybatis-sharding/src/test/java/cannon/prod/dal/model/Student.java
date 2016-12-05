/**
 * 
 */
package cannon.prod.dal.model;

/**
 * @author fangjialong
 * @date 2015年9月4日 下午4:26:25
 */
public class Student {

	private String no;

	private String name;

	private int sex;

	/**
	 * @return the no
	 */
	public String getNo() {
		return no;
	}

	/**
	 * @param no
	 *            the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sex
	 */
	public int getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "Student[no=" + no + ",name=" + name + "]";
	}

}
