package com.h5.weibo.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.h5.weibo.model.User;
import com.h5.weibo.service.UserRelaService;

public class RelaAction extends BaseAction{

	/**
	 * 获取我收听的用户列表
	 * @return
	 */
	@Action(value="/rela/listen_do")
	public String getListen() {
		User my = getCurrentUser();
		long userId = my.getId();
		
		List<User> users = UserRelaService.getMyListen(userId);
		Map<Long,Integer> relas = UserRelaService.getMyUserRela(userId);
		
		Map<String,Object> out = new HashMap<String, Object>();
		out.put("users", users);
		out.put("relas", relas);
		
		this.renderJson(out);
		return null;
	}
	
	/**
	 * 我的粉丝
	 * @return
	 */
	@Action(value="/rela/fans_do")
	public String getFans() {
		User my = getCurrentUser();
		long userId = my.getId();
		
		List<User> users = UserRelaService.getMyFans(userId);
		Map<Long,Integer> relas = UserRelaService.getMyUserRela(userId);
		
		Map<String,Object> out = new HashMap<String, Object>();
		out.put("users", users);
		out.put("relas", relas);
		
		this.renderJson(out);
		return null;
	}
	/**
	 * 搜索
	 * @return
	 */
	@Action(value="/rela/search_do")
	public String search() {
		String key = getStr("q", "");

		User my = getCurrentUser();
		long userId = my.getId();
		
		List<User> users = UserRelaService.search(key);
		Map<Long,Integer> relas = UserRelaService.getMyUserRela(userId);
		
		Map<String,Object> out = new HashMap<String, Object>();
		out.put("users", users);
		out.put("relas", relas);
		
		this.renderJson(out);
		return null;
	}
	/**
	 * 收听某人
	 * @return
	 */
	@Action(value="/rela/add_listen_do")
	public String addListen() {
		User my = getCurrentUser();
		long userId = my.getId();
		long listenId = getL("id", -1);
		
		int ret = 0;
		if(listenId <= 0) {
			ret = -1;
		}else {
			UserRelaService.listenUser(userId, listenId);
		}
		
		Map<String,Object> out = new HashMap<String, Object>();
		out.put("ret", ret);
		this.renderJson(out);
		
		return null;
	}
	@Action(value="/rela/cancel_listen_do")
	public String cancelListen() {
		User my = getCurrentUser();
		long userId = my.getId();
		long listenId = getL("id", -1);
		
		int ret = 0;
		if(listenId <= 0) {
			ret = -1;
		}else {
			UserRelaService.cancelListenUser(userId, listenId);
		}
		
		Map<String,Object> out = new HashMap<String, Object>();
		out.put("ret", ret);
		this.renderJson(out);
		
		return null;
	}
}
