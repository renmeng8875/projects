package com.bjsxt.hibernate;

import org.hibernate.Session;

import junit.framework.TestCase;

public class ComponentMappingTest extends TestCase {

	public void testSave1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			User user = new User();
			user.setName("ÕÅÈý");
			
			Contact contact = new Contact();
			contact.setAddress("xxxxx");
			contact.setEmail("xxx@rrr.com");
			contact.setZipCode("1111111");
			contact.setContactTel("9999999999");
			
			user.setContact(contact);
			
			session.save(user);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
}
