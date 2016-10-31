

public class HelloWorld {
	
	
			
	public static void main(String[] args) {
		HelloWorld hw = new HelloWorld();
		Integer var1 = new Integer(1);
		
		Integer var2 = var1;
		
		hw.copy(var1);
		
		System.out.println(var1==var2);
		System.out.println(var1);
		System.out.println(var2);
		
		System.out.println("********************************");
		Person p = new Person();
		p.setName("rrr");
		p.setAge(20);
		hw.copy(p);
		System.out.println(p.getName()+"--"+p.getAge());
		
		
	}
	
	public  Integer  copy(Integer s){
		s = new Integer(2);
		return s;
	}
	
	public void copy(Person p){
		p.setName("www");
		p.setAge(18);
	}

}

class Person{
	private String name;
	
	private int age;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
}
