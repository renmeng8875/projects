package com.bjsxt.hibernate;

import org.hibernate.Session;

import junit.framework.TestCase;

/**
 * 将<many-to-one>中的lazy设置为false,其它默认
 * @author Administrator
 *
 */
public class SingleEndTest2 extends TestCase {
	
	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//不会发出sql
			User user = (User)session.load(User.class, 1);
			
			//会发出sql，发出两条sql分别加载User和Group
			System.out.println("user.name=" + user.getName());
			
			//不会发出sql
			Group group = user.getGroup();
			
			//不会发出sql
			System.out.println("group.name=" + group.getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
}
