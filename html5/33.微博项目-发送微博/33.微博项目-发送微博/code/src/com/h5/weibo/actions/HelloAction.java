package com.h5.weibo.actions;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

/**
 * **************************
**获取用户输入的名字，然后输出到前台
**author: Administrator
**date: 2012-8-18
****************************
 */
public class HelloAction extends BaseAction{

	@Action(value="/say",results= {
		@Result(name="success",location="/sayHello.jsp")
	})
	public String sayHello() {
		String name = this.getStr("name", "");
		this.add("name", name);
		
		return "success";
	}
	
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
}
