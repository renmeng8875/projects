package com.h5.weibo.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.h5.weibo.model.DataPage;
import com.h5.weibo.model.User;
import com.h5.weibo.model.Weibo;
import com.h5.weibo.service.UserService;
import com.h5.weibo.service.WeiboService;

public class AtAction extends BaseAction{
	
private int pageSize = 2;
	
	@Action(value="/at",results= {
		@Result(name="success",location="/at.jsp")
	})
	public String index() {
		User u = getCurrentUser();
		// 获取微博列表
		DataPage<Weibo> dp = WeiboService.getAtWbs(u.getId(), pageSize, 1);
		List<Weibo> wbs = dp.getRecords();
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
		this.add("nextPage", dp.hasNextPage());
		
		return "success";
	}
	
	@Action(value="/at_more_do")
	public String more() {
		User u = getCurrentUser();
		int pageNo = getI("pageNo", 1);
		// 获取微博列表
		DataPage<Weibo> dp = WeiboService.getAtWbs(u.getId(), pageSize, pageNo);
		List<Weibo> wbs = dp.getRecords();
		
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
		Map<String,Object> out = new HashMap<String, Object>();
		out.put("wbs", wbs);
		out.put("users", users);
		out.put("nextPage", dp.hasNextPage());
		
		this.renderJson(out);
		
		return null;
	}
}
