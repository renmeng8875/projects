package cn.itcast.day3.aopframework;

public class MyJob implements Job 
{
	public MyJob(){}
	
	public void realWork()
	{
		System.out.println("努力工作中.....");
	}
}
