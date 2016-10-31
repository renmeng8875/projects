package com.h5.weibo.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.h5.weibo.ApplicationConstant;
import com.h5.weibo.model.DataPage;
import com.h5.weibo.model.Pair;
import com.h5.weibo.model.User;
import com.h5.weibo.model.Weibo;
import com.h5.weibo.service.UserRelaService;
import com.h5.weibo.service.UserService;
import com.h5.weibo.service.WeiboService;

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
		
		
		Pair<DataPage<Weibo>,Map<String,User>> pair = WeiboService.getUserWeibos(userId, ApplicationConstant.pageSize, 1);
		DataPage<Weibo> dp = pair.getFirst();
		List<Weibo> wbs = dp.getRecords();
		this.add("wbs", wbs);
		this.add("users", pair.getSecond());
		this.add("nextPage", dp.hasNextPage());
		this.add("wbCount", dp.getRecordCount());
		
		
		return "success";
	}
	
	@Action(value="/cp_more_do")
	public String more() {
		int pageNo = getI("pageNo", 1);
		long userId = getL("id", 0);
		// 获取微博列表
		Pair<DataPage<Weibo>,Map<String,User>> pair = WeiboService.getUserWeibos(userId, ApplicationConstant.pageSize, pageNo);
		DataPage<Weibo> dp = pair.getFirst();
		List<Weibo> wbs = dp.getRecords();
		

		Map<String,Object> out = new HashMap<String, Object>();
		out.put("wbs", wbs);
		out.put("users", pair.getSecond());
		out.put("nextPage", dp.hasNextPage());
		
		this.renderJson(out);
		
		return null;
	}
}
