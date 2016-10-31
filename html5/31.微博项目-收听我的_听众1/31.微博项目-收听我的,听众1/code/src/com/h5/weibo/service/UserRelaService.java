package com.h5.weibo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.h5.weibo.dao.HibernateUtil;
import com.h5.weibo.model.Fans;
import com.h5.weibo.model.User;

public class UserRelaService {
	
	
	/**
	 * 搜索用户
	 * @param key
	 * @return
	 */
	public static List<User> search(String key){
		Session s = HibernateUtil.getCurrentSession();
		Query q = s.createQuery("from User where nickName like '%" + key + "%'");
		return q.list();
	}
	
	public static List<User> getMyListen(long userId){
		Session s = HibernateUtil.getCurrentSession();
		Query q = s.createQuery("from Fans where fansId=?");
		q.setLong(0, userId);
		List<Fans> fans = q.list();
		
		List<User> users = new ArrayList<User>();
		for(Fans f : fans) {
			User u = (User) s.get(User.class, f.getListenId());
			users.add(u);
		}
		
		return users;
	}
	
	public static List<User> getMyFans(long userId){
		Session s = HibernateUtil.getCurrentSession();
		Query q = s.createQuery("from Fans where listenId=?");
		q.setLong(0, userId);
		List<Fans> fans = q.list();
		
		List<User> users = new ArrayList<User>();
		for(Fans f : fans) {
			User u = (User) s.get(User.class, f.getFansId());
			users.add(u);
		}
		
		return users;
	}
	
	/**
	 * 收听用户
	 */
	public static boolean listenUser(long fansId,long listenId) {
		Session s = HibernateUtil.getCurrentSession();
		try {	
			// 先判断记录是否存在
			Query q = s.createQuery("select id from Fans where fansId=? and listenId=?");
			q.setLong(0, fansId);
			q.setLong(1, listenId);
			Iterator iter = q.iterate();
			if(iter.hasNext())
				return false;
			
			Fans fan = new Fans();
			fan.setFansId(fansId);
			fan.setListenId(listenId);
			fan.setTime(System.currentTimeMillis() / 1000);
			
			HibernateUtil.beginTransation();
			s.saveOrUpdate(fan);
			HibernateUtil.commitTransation();
			
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			HibernateUtil.rollbackTransation();
		}finally {
			HibernateUtil.closeSession();
		}
		
		return false;
	}
	/**
	 * 取消收听
	 * @param fansId
	 * @param listenId
	 * @return
	 */
	public static boolean cancelListenUser(long fansId,long listenId) {
		Session s = HibernateUtil.getCurrentSession();
		try {	
			// 先判断记录是否存在
			Query q = s.createQuery("delete from Fans where fansId=? and listenId=?");
			q.setLong(0, fansId);
			q.setLong(1, listenId);
			
			
			HibernateUtil.beginTransation();
			q.executeUpdate();
			HibernateUtil.commitTransation();
			
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			HibernateUtil.rollbackTransation();
		}finally {
			HibernateUtil.closeSession();
		}
		
		return false;
	}
	/**
	 * 获取我的所有用户关系数据
	 * @param userId
	 * @return
	 */
	public static Map<Long,Integer> getMyUserRela(long userId){
		Session s = HibernateUtil.getCurrentSession();

		// 先判断记录是否存在
		Query q = s.createQuery("from Fans where fansId=? or listenId=?");
		q.setLong(0, userId);
		q.setLong(1, userId);
		List<Fans> fans = q.list();
		
		Map<Long,Integer> relas = new HashMap<Long, Integer>();
		for(Fans f : fans) {
			int rela = 0;
			long otherUser = 0;
			if(f.getFansId() == userId) {
				rela = Fans.RELA_LISTEN;
				otherUser = f.getListenId();
			}else if(f.getListenId() == userId) {
				rela = Fans.RELA_FANS;
				otherUser = f.getFansId();
			}
			
			if(rela <=0)
				continue;
			
			Integer existRela = relas.get(otherUser);
			if(existRela == null) {
				relas.put(otherUser, rela);
			}else if(existRela != rela) {
				// 已互相收听
				relas.put(otherUser, Fans.RELA_BOTH);
			}
		}
		
		return relas;
	}
}
