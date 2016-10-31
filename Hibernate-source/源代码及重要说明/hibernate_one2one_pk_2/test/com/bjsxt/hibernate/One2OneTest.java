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
			
			//不会出现TransientObjectException异常
			//因为一对一主键关联映射中，默认了cascade属性
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
			
			Person person = (Person)session.load(Person.class, 1);
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

	public void testLoad2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			IdCard idCard = (IdCard)session.load(IdCard.class, 1);
			System.out.println("idcard.cardNo=" + idCard.getCardNo());
			System.out.println("idcard.person.name=" + idCard.getPerson().getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
	
}
