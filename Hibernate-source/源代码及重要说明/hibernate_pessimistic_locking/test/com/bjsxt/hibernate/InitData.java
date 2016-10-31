package com.bjsxt.hibernate;

import org.hibernate.Session;

public class InitData {

	public static void main(String[] args) {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Inventory inv = new  Inventory();
			inv.setItemNo(1001);
			inv.setItemName("дт╟в╫П");
			inv.setQuantity(1000);
			
			session.save(inv);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}	
	}
}
