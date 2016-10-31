package com.h5.weibo.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.h5.weibo.model.User;
import com.h5.weibo.model.Weibo;
import com.h5.weibo.service.UserService;
import com.h5.weibo.service.WeiboService;


public class IndexAction extends BaseAction{
	private int pageSize = 20;
	
	@Action(value="/index",results= {
		@Result(name="success",location="/index.jsp")
	})
	public String index() {
		User u = getCurrentUser();
		// 获取微博列表
		List<Weibo> wbs = WeiboService.getWBs(u.getId(), pageSize, 1);
		this.add("wbs", wbs);
		
		// 获取对应用户的个人资料
		Map<Long,User> users = new HashMap<Long, User>(); 
		for(Weibo wb : wbs) {
			if(users.containsKey(wb.getWriterId()))
				continue;
			
			User user = UserService.getUser(wb.getWriterId());
			users.put(user.getId(), user);
			
			// 获取转发微博的用户资料
			if(wb.getForwardWibo() != null) {
				if(users.containsKey(wb.getForwardWibo().getWriterId()))
					continue;
				
				User user2 = UserService.getUser(wb.getForwardWibo().getWriterId());
				users.put(user2.getId(), user2);
			}
		}
		this.add("users", users);
		
		return "success";
	}
}
