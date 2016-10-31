package com.bjsxt.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;

import junit.framework.TestCase;

public class CollectionMappintTest extends TestCase {

	public void testSave1() {
		Session session = null;
		CollectionMapping c = new CollectionMapping();
		
		c.setName("xxx");
		
		Set setValue = new HashSet();
		setValue.add("a");
		setValue.add("b");
		c.setSetValue(setValue);
		
		List listValue = new ArrayList();
		listValue.add("c");
		listValue.add("d");
		c.setListValue(listValue);
		
		String[] arrayValue = new String[]{"e", "f"};
		c.setArrayValue(arrayValue);
		
		Map mapValue = new HashMap();
		mapValue.put("k1", "v1");
		mapValue.put("k2", "v2");
		c.setMapValue(mapValue);		
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			session.save(c);
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
			CollectionMapping c = (CollectionMapping)session.load(CollectionMapping.class, 1);
			System.out.println("name=" + c.getName());
			System.out.println("setvalue=" + c.getSetValue());
			System.out.println("mapvalue=" + c.getMapValue());
			System.out.println("listvalue=" + c.getListValue());
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}			
	
}
