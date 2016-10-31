package com.h5.weibo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session;

import com.h5.weibo.dao.HibernateUtil;
import com.h5.weibo.model.ReferWeibo;
import com.h5.weibo.model.User;
import com.h5.weibo.model.Weibo;

public class WeiboService {

	/**
	 * 写微博
	 * @param wb
	 */
	public static void insertWeibo(Weibo wb) {
		wb.setSendTime((int)(System.currentTimeMillis() / 1000));
		
		Session s = HibernateUtil.getCurrentSession();
		try {
			HibernateUtil.beginTransation();
			s.save(wb);
			
			List<ReferWeibo> rws = analysisAt(wb.getContent(), wb.getId());
			for(ReferWeibo rw : rws) {
				s.save(rw);
			}
			
			HibernateUtil.commitTransation();
			
		}catch(Exception e) {
			e.printStackTrace();
			HibernateUtil.rollbackTransation();
		}finally {
			HibernateUtil.closeSession();
		}
	}
	/**
	 * 正则分析抄送给人的微博
	 */
	private static Pattern atP = Pattern.compile("(@[A-Z|a-z|\\d|_]+)");
	
	private static List<ReferWeibo> analysisAt(String content,long wbId) {
		List<ReferWeibo> wbs = new ArrayList<ReferWeibo>();
		
		Matcher m = atP.matcher(content);
		while(m.find()) {
			String userName = m.group(0).substring(1);

			// 检测用户名是否存在
			User u = UserService.getUserByName(userName);
			if(u != null) {
				ReferWeibo rw = new ReferWeibo();
				rw.setReferId(u.getId());
				rw.setWeiboId(wbId);
				
				wbs.add(rw);
			}
		}

		return wbs;
	}
	
	public static void main(String[] args) {
		String str = "萨德维奇asd@sdfew 申请 @fsf_3123阿萨德前往";
		
		//analysisAt(str);
	}
}
