package com.h5.weibo.actions;

import java.io.File;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.h5.weibo.ApplicationConstant;
import com.h5.weibo.model.User;
import com.h5.weibo.service.UserService;
import com.h5.weibo.utils.FileUtils;

public class ProfileAction extends BaseAction{

	@Action(value="/profile_update",results= {
		@Result(name="SUCCESS",location="/profile.jsp")	
	})
	public String update() {
		// 获取session中的用户信息
		User u = getCurrentUser();
		// 上传头像，如果有的话
		File file = getFile("logo");
		if(file != null && file.length() > 0) {
			String uploadName = getFileName("logo");
			//上传到update目录下
			String uploadPath = getRequest().getRealPath("/upload");
			String sufix  = FileUtils.getSufix(uploadName);
			// 获取最新上传的文件名(以用户id和系统时间组合在一起。)
			String logoName = u.getId() + "_" + System.currentTimeMillis() + sufix;
			String fileName = uploadPath + File.separator + logoName;

			FileUtils.copyFile(file.getAbsolutePath(),fileName);
			
			u.setLogo(logoName);
		}
		
		// 其他数据
		u.setBirthDay(getI("birthDay", 0));
		u.setBirthMonth(getI("birthMonth",0));
		u.setBirthYear(getI("birthYear",0));
		
		u.setIntro(getStr("intro", ""));
		u.setNickName(getStr("nickName", u.getUserName()));
		u.setSex(getI("sex",-1));
		
		int ret = 0;
		// 保存到数据库
		if(UserService.saveUser(u)) {
			// 更新session
			getRequest().setAttribute(ApplicationConstant.SESSION_USER, u);
		}else {
			// 保存失败
			ret = -1;
		}
		
		this.add("ret", ret);
		
		return "SUCCESS";
	}
}
