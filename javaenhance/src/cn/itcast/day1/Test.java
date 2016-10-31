package cn.itcast.day1;

import java.io.File;

public class Test 
{
	public static void main(String[] args) 
	{
		File dir = new File("D:/synchronized/453791694/code");
		File[] fileList = dir.listFiles();
		for(File f :fileList)
		{
			System.out.println(f.getName());
		}
	}
}
