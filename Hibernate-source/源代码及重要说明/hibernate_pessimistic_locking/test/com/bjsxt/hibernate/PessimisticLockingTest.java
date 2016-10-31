package com.bjsxt.hibernate;

import org.hibernate.LockMode;
import org.hibernate.Session;

import junit.framework.TestCase;

public class PessimisticLockingTest extends TestCase {

	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Inventory inv = (Inventory)session.load(Inventory.class, 1, LockMode.UPGRADE);
			System.out.println("itemName=" + inv.getItemName());
			System.out.println("quantity=" + inv.getQuantity());
			inv.setQuantity(inv.getQuantity() - 200);
			session.update(inv);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}	
	}
	
	public void testLoad2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Inventory inv = (Inventory)session.load(Inventory.class, 1, LockMode.UPGRADE);
			System.out.println("itemName=" + inv.getItemName());
			System.out.println("quantity=" + inv.getQuantity());
			inv.setQuantity(inv.getQuantity() - 200);
			session.update(inv);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}	
	}
	
}
