package com.h5.weibo.actions;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.h5.weibo.model.User;
import com.h5.weibo.service.UserRelaService;
import com.h5.weibo.service.UserService;

public class ClientProfileAction extends BaseAction{

	@Action(value="/client_profile",results= {
			@Result(name="success",location="/client_profile.jsp")
	})
	public String index() {
		User my = getCurrentUser();
		long userId = getL("id", 0);
		
		// 用户基本信息
		User user = UserService.getUser(userId);
		this.add("user", user);
		// 用户关系信息
		Map<Long,Integer> relas = UserRelaService.getMyUserRela(my.getId());
		Integer rela = relas.get(userId);
		if(rela == null)
			rela = 0;
		this.add("rela", rela);
		
		//统计信息
		int[] counts = UserRelaService.getRelaStat(userId);
		this.add("fansCount", counts[0]);
		this.add("listenCount", counts[1]);
		
		return "success";
	}
}
