package com.bjsxt.hibernate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;

import junit.framework.TestCase;

public class Many2Many extends TestCase {

	public void testSave2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Role r1 = new Role();
			r1.setName("数据录入人员");
			session.save(r1);
			
			Role r2 = new Role();
			r2.setName("商务主管");
			session.save(r2);
			
			Role r3 = new Role();
			r3.setName("大区经理");
			session.save(r3);
			
			User u1 = new User();
			u1.setName("10");
			Set u1Roles = new HashSet();
			u1Roles.add(r1);
			u1Roles.add(r2);
			u1.setRoles(u1Roles);
			
			User u2 = new User();
			u2.setName("祖儿");
			Set u2Roles = new HashSet();
			u2Roles.add(r2);
			u2Roles.add(r3);
			u2.setRoles(u2Roles);
			
			User u3 = new User();
			u3.setName("杰伦");
			Set u3Roles = new HashSet();
			u3Roles.add(r1);
			u3Roles.add(r2);
			u3Roles.add(r3);
			u3.setRoles(u3Roles);
			
			session.save(u1);
			session.save(u2);
			session.save(u3);
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
	
	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			User user = (User)session.load(User.class, 1);
			System.out.println(user.getName());
			for (Iterator iter=user.getRoles().iterator(); iter.hasNext();) {
				Role role = (Role)iter.next();
				System.out.println(role.getName());
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
	
}
