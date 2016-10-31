package com.h5.weibo.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.Session;

import com.h5.weibo.dao.HibernateUtil;
import com.h5.weibo.model.DataPage;
import com.h5.weibo.model.Fans;
import com.h5.weibo.model.ReferWeibo;
import com.h5.weibo.model.User;
import com.h5.weibo.model.Weibo;

public class WeiboService {
	/**
	 * 获取@我的微博
	 * @return
	 */
	public static DataPage<Weibo> getAtWbs(long userId,int pageSize,int pageNo){
		Session s = HibernateUtil.getCurrentSession();
		// 获取我收听的微博用户id
		Query q = s.createQuery("from ReferWeibo where referId=?");
		q.setLong(0, userId);
		List<ReferWeibo> rwbs = q.list();
		
		List<Long> ids = new ArrayList<Long>();
		for(ReferWeibo f : rwbs) {
			ids.add(f.getWeiboId());
		}
				
		// 获取总数量
		q = s.createQuery("select count(*) from Weibo where id in (:ids)");
		q.setParameterList("ids", ids);
		long count = (Long)q.uniqueResult();
		
		if(count == 0)
			return new DataPage<Weibo>(new ArrayList<Weibo>(), pageSize, pageNo, 0);
		
		q = s.createQuery("from Weibo where id in (:ids) order by sendTime desc");
		q.setFirstResult(pageSize*(pageNo-1));
		q.setMaxResults(pageSize);
		q.setParameterList("ids", ids);
		
		List<Weibo> wbs = q.list();
		for(Weibo wb : wbs) {
			if(wb.getForwardId() > 0) {
				Weibo fwb = (Weibo) s.get(Weibo.class, wb.getForwardId());
				wb.setForwardWibo(fwb);
			}
		}
		
		
		return new DataPage<Weibo>(wbs, pageSize, pageNo, (int)count);
	}
	
	/**
	 * 获取收听的微博列表
	 * @return
	 */
	public static DataPage<Weibo> getWBs(long userId,int pageSize,int pageNo){
		Session s = HibernateUtil.getCurrentSession();
		// 获取我收听的微博用户id
		Query q = s.createQuery("from Fans where fansId=?");
		q.setLong(0, userId);
		List<Fans> fans = q.list();
		
		List<Long> ids = new ArrayList<Long>();
		for(Fans f : fans) {
			ids.add(f.getListenId());
		}
		ids.add(userId);
		
		// 获取总数量
		q = s.createQuery("select count(*) from Weibo where writerId in (:ids)");
		q.setParameterList("ids", ids);
		long count = (Long)q.uniqueResult();
		
		if(count == 0)
			return new DataPage<Weibo>(new ArrayList<Weibo>(), pageSize, pageNo, 0);
		
		q = s.createQuery("from Weibo where writerId in (:ids) order by sendTime desc");
		q.setFirstResult(pageSize*(pageNo-1));
		q.setMaxResults(pageSize);
		q.setParameterList("ids", ids);
		
		List<Weibo> wbs = q.list();
		for(Weibo wb : wbs) {
			if(wb.getForwardId() > 0) {
				Weibo fwb = (Weibo) s.get(Weibo.class, wb.getForwardId());
				wb.setForwardWibo(fwb);
			}
		}
		
		
		return new DataPage<Weibo>(wbs, pageSize, pageNo, (int)count);
	}
	
	public static Weibo getWeibo(long fid) {
		Session s = HibernateUtil.getCurrentSession();
		return (Weibo)s.get(Weibo.class, fid);
	}
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
	
}
