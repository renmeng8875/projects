package com.bjsxt.hibernate;

import org.hibernate.Session;

import junit.framework.TestCase;

public class One2OneTest extends TestCase {

	public void testSave1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			IdCard idCard = new IdCard();
			idCard.setCardNo("88888888888888");
			
			Person person = new Person();
			person.setName("菜10");
			person.setIdCard(idCard);
			
			//不能成功保存，因为IdCard是Transient状态
			session.save(person);
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
	
	public void testSave2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			IdCard idCard = new IdCard();
			idCard.setCardNo("88888888888888");
			session.save(idCard);
			
			Person person = new Person();
			person.setName("菜10");
			person.setIdCard(idCard);
			
			session.save(person);
			
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
			
			Person person = (Person)session.load(Person.class, 2);
			System.out.println("person.name=" + person.getName());
			System.out.println("idCard.cardNo=" + person.getIdCard().getCardNo());
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
	
}
