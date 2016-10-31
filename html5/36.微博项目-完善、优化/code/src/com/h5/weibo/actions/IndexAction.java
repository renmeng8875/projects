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
import com.h5.weibo.service.UserService;
import com.h5.weibo.service.WeiboService;


public class IndexAction extends BaseAction{
	
	@Action(value="/index",results= {
		@Result(name="success",location="/index.jsp")
	})
	public String index() {
		User u = getCurrentUser();
		// 获取微博列表
		Pair<DataPage<Weibo>,Map<String,User>> pair = WeiboService.getWBs(u.getId(), ApplicationConstant.pageSize, 1);
		DataPage<Weibo> dp = pair.getFirst();
		List<Weibo> wbs = dp.getRecords();
		this.add("wbs", wbs);
		
		this.add("users",  pair.getSecond());
		this.add("nextPage", dp.hasNextPage());
		
		return "success";
	}
	
	@Action(value="/index_more_do")
	public String more() {
		User u = getCurrentUser();
		int pageNo = getI("pageNo", 1);
		// 获取微博列表
		Pair<DataPage<Weibo>,Map<String,User>> pair = WeiboService.getWBs(u.getId(), ApplicationConstant.pageSize, pageNo);
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
