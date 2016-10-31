package com.bjsxt.hibernate;

import org.hibernate.Session;

import junit.framework.TestCase;

/**
 * 运行本单元测试的条件：
 * 	
 * 设置<class>标签上的lazy=true，也就是默认配置
 *  
 * @author Administrator
 *
 */
public class ClassLazyTest extends TestCase {

	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//不会发出sql
			Group group = (Group)session.load(Group.class, 1);
			
			//不会发出sql
			System.out.println("group.id=" + group.getId());
			
			//会发出sql
			System.out.println("group.name=" + group.getName());
			
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
		Group group = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			group = (Group)session.load(Group.class, 1);
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
		//不能正确输出，抛出LazyInitializationException 异常，因为session已经关闭
		//hibernate支持lazy策略只有在session打开状态下有效
		System.out.println("group.name=" + group.getName());
	}	
	
}
