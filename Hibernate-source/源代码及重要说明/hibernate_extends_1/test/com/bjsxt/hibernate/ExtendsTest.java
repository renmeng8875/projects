package com.bjsxt.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import junit.framework.TestCase;

public class ExtendsTest extends TestCase {

	public void testSave1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Pig pig = new Pig();
			pig.setName("猪猪");
			pig.setSex(true);
			pig.setWeight(100);
			session.save(pig);
			
			Bird bird = new Bird();
			bird.setName("鸟鸟");
			bird.setSex(false);
			bird.setHeight(50);
			session.save(bird);
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	/**
	 * 采用load，通过Pig查询
	 */
	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Pig pig = (Pig)session.load(Pig.class, 1);
			System.out.println(pig.getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	/**
	 * 采用load，通过Animal查询
	 */
	public void testLoad2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Animal animal = (Animal)session.load(Animal.class, 1);
			System.out.println(animal.getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}			
	
	/**
	 * 采用load，通过Animal查询
	 */
	public void testLoad3() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Animal animal = (Animal)session.load(Animal.class, 1);
			
			//因为load默认只是lazy，因为我们看到的是Animal的代理对象
			//所以通过instanceof是反应不出正真的对象类型的
			//因此load在默认情况下是不支持多态查询的
			if (animal instanceof Pig) {
				System.out.println(animal.getName());
			}else {
				System.out.println("不是猪");
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}			
	
	/**
	 * 采用load，通过Animal查询,将<class>标签上的lazy=false
	 */
	public void testLoad4() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Animal animal = (Animal)session.load(Animal.class, 1);
			//可以正确的判断出Pig的类型，因为lazy=false，返回的是具体的Pig类型
			//此时load支持多态查询
			if (animal instanceof Pig) {
				System.out.println(animal.getName());
			}else {
				System.out.println("不是猪");
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
	
	/**
	 * 采用get，通过Animal查询
	 */
	public void testLoad5() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//可以正确的判断出Pig的类型，因为返回的是具体的Pig类型
			//get支持多态查询
			Animal animal = (Animal)session.get(Animal.class, 1);

			if (animal instanceof Pig) {
				System.out.println(animal.getName());
			}else {
				System.out.println("不是猪");
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	/**
	 * 采用get，通过Animal查询
	 */
	public void testLoad6() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
//			List animalList = session.createQuery("from Animal").list();
//			for (Iterator iter = animalList.iterator(); iter.hasNext();) {
//				Animal a = (Animal)iter.next();
//				//能够正确的鉴别出正真的类型，hql是支持多态查询的
//				if (a instanceof Pig) {
//					System.out.println("是Pig");
//				}else if (a instanceof Bird) {
//					System.out.println("是bird");
//				} 
//			}
			
			List list = session.createQuery("from java.lang.Object").list();
			for (Iterator iter=list.iterator(); iter.hasNext();) {
				Object o = iter.next();
				if (o instanceof Pig) {
					System.out.println("是Pig");
				}else if (o instanceof Bird) {
					System.out.println("是bird");
				} 
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
