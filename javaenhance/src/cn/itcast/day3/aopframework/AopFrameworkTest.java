package cn.itcast.day3.aopframework;

import java.io.InputStream;

public class AopFrameworkTest {

	
	public static void main(String[] args) throws Exception 
	{
		InputStream ips = AopFrameworkTest.class.getResourceAsStream("config.properties");
		Job bean = (Job)new BeanFactory(ips).getBean("xxx");
		System.out.println(bean.getClass().getName());
		bean.realWork();
		
	}

}
