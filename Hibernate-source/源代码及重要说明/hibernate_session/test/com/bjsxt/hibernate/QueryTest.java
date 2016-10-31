package com.bjsxt.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import junit.framework.TestCase;

public class QueryTest extends TestCase {

	public void testQuery() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			Query query = session.createQuery("from User");
			query.setFirstResult(2);
			query.setMaxResults(2);
			List userList = query.list();
			for (Iterator iter=userList.iterator(); iter.hasNext();) {
				User user = (User)iter.next();
				System.out.println(user.getId());
				System.out.println(user.getName());
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
