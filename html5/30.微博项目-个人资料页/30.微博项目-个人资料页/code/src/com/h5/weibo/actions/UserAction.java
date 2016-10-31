package com.h5.weibo.actions;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.h5.weibo.ApplicationConstant;
import com.h5.weibo.model.User;
import com.h5.weibo.service.UserService;
import com.h5.weibo.utils.StringUtil;

public class UserAction extends BaseAction{

	@Action(value="/user/register_do")
	public String register() {
		// -3：用户名和密码不能为空,-4:二次密码不一致
		int ret = 0;
		User u = this.getBeanFromRequest(User.class);
		if(StringUtil.isEmpty(u.getUserName()) || StringUtil.isEmpty(u.getPwd())) {
			ret = -3;
		}else if(!this.getStr("pwd1", "").equals(u.getPwd())) {
			ret = -4;
		}
		
		if(ret == 0) {
			// 默认昵称就是用户名
			u.setNickName(u.getUserName());
			//
			ret = UserService.register(u);
			if(ret == 0) {
				// 注册成功，登录信息保存到session
				this.getSession().setAttribute(ApplicationConstant.SESSION_USER, u);
			}
		}
		
		Map<String,Object> out = new HashMap<String,Object>();
		out.put("ret", ret);
		this.renderJson(out);
		
		return null;
	}
	@Action(value="/user/login_do")
	public String login() {
		int ret = 0;
		
		String name = getStr("userName", "");
		String pwd = getStr("pwd", "");
		
		if(StringUtil.isEmpty(name) || StringUtil.isEmpty(pwd)) {
			ret = -1;
		}else {
			User u = UserService.login(name, pwd);
			if(u == null) {
				// 登录失败
				ret = -1;
			}else {
				// 登录成功,登录信息保存到session
				this.getSession().setAttribute(ApplicationConstant.SESSION_USER, u);
				
				ret = 0;
			}
		}
		
		Map<String,Object> out = new HashMap<String, Object>();
		out.put("ret", ret);
		this.renderJson(out);
		return null;
	}
}
