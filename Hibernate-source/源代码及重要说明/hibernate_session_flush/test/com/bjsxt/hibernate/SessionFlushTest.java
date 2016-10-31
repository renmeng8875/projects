package com.bjsxt.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import junit.framework.TestCase;

public class SessionFlushTest extends TestCase {
	
	/**
	 * 测试uuid主键生成策略
	 */
	public void testSave1() {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtils.getSession();
			tx = session.beginTransaction();

			User1 user = new User1();
			user.setName("李四");
			user.setPassword("123");
			user.setCreateTime(new Date());
			user.setExpireTime(new Date());
			
			//因为user的主键生成侧路采用的是uuid，所以调用完成save后，只是将user纳入到了session的管理
			//不会发出insert语句，但是id已经生成，session中existsInDatebase状态为false
			session.save(user);
			
			//调用flush，hibernate会清理缓存，执行sql
			//如果数据库的隔离级别设置为为提交读，那么我们可以看到flush过的数据
			//并且session中existsInDatebase状态为true
			session.flush();
			
			//提交事务
			//默认情况下commit操作会先执行flush清理缓存，所以不用显示的调用flush
			//commit后数据是无法回滚的
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	/**
	 * 测试native主键生成策略
	 */
	public void testSave2() {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtils.getSession();
			tx = session.beginTransaction();

			User2 user = new User2();
			user.setName("张三1");
			user.setPassword("123");
			user.setCreateTime(new Date());
			user.setExpireTime(new Date());
			
			//因为user的主键生成策略为native,所以调用session.save后，将执行insert语句，返回有数据库生成的id
			//纳入了session的管理，修改了session中existsInDatebase状态为true
			//如果数据库的隔离级别设置为为提交读，那么我们可以看到save过的数据
			session.save(user);
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	
	/**
	 * 测试uuid主键生成策略
	 */
	public void testSave3() {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtils.getSession();
			tx = session.beginTransaction();

			User1 user = new User1();
			user.setName("王五");
			user.setPassword("123");
			user.setCreateTime(new Date());
			user.setExpireTime(new Date());
			
			//因为user的主键生成侧路采用的是uuid，所以调用完成save后，只是将user纳入到了session的管理
			//不会发出insert语句，但是id已经生成，session中existsInDatebase状态为false
			session.save(user);
			
			//将user对象从session中逐出，即session的EntityEntries属性中逐出
			session.evict(user);
			
			//无法成功提交，因为hibernate在清理缓存时，在session的insertions集合中取出user对象进行insert操作后
			//需要更新entityEntries属性中的existsInDatabase为true，而我们采用evict已经将user从session的entityEntries
			//中逐出了，所以找不到相关数据，无法更新，抛出异常
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	/**
	 * 测试uuid主键生成策略
	 */
	public void testSave4() {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtils.getSession();
			tx = session.beginTransaction();

			User1 user = new User1();
			user.setName("王五");
			user.setPassword("123");
			user.setCreateTime(new Date());
			user.setExpireTime(new Date());
			
			//因为user的主键生成侧路采用的是uuid，所以调用完成save后，只是将user纳入到了session的管理
			//不会发出insert语句，但是id已经生成，session中existsInDatebase状态为false
			session.save(user);
			
			//flush后hibernate会清理缓存，会将user对象保存到数据库中，将session中的insertions中的user对象
			//清除，并且设置session中existsInDatebase的状态为true
			session.flush();
			
			//将user对象从session中逐出，即session的EntityEntries属性中逐出
			session.evict(user);
			
			//可以成功提交，因为hibernate在清理缓存时，在session的insertions集合中无法找到user对象
			//所以就不会发出insert语句，也不会更新session中的existsInDatabase的状态
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	/**
	 * 测试native主键生成策略
	 */
	public void testSave5() {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtils.getSession();
			tx = session.beginTransaction();

			User2 user = new User2();
			user.setName("张三11");
			user.setPassword("123");
			user.setCreateTime(new Date());
			user.setExpireTime(new Date());
			
			//因为user的主键生成策略为native,所以调用session.save后，将执行insert语句，返回有数据库生成的id
			//纳入了session的管理，修改了session中existsInDatebase状态为true
			//如果数据库的隔离级别设置为为提交读，那么我们可以看到save过的数据
			session.save(user);
			
			//将user对象从session中逐出，即session的EntityEntries属性中逐出
			session.evict(user);
			
			//可以成功提交，因为hibernate在清理缓存时，在session的insertions集合中无法找到user对象
			//所以就不会发出insert语句，也不会更新session中的existsInDatabase的状态
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	/**
	 * 测试assigned主键生成策略
	 * 
	 */
	public void testSave6() {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtils.getSession();
			tx = session.beginTransaction();

			User3 user = new User3();
			user.setId("001");
			user.setName("张三");
			
			session.save(user);
			
			user.setName("王五");
			session.update(user);
			
			User3 user3 = new User3();
			user3.setId("002");
			user3.setName("李四");
			session.save(user3);
			
			//Hibernate: insert into t_user3 (name, password, create_time, expire_time, user_id) values (?, ?, ?, ?, ?)
			//Hibernate: insert into t_user3 (name, password, create_time, expire_time, user_id) values (?, ?, ?, ?, ?)
			//Hibernate: update t_user3 set name=?, password=?, create_time=?, expire_time=? where user_id=?
			//hibernate按照save(insert),update、delete顺序提交相关操作
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	/**
	 * 测试assigned主键生成策略
	 * 
	 */
	public void testSave7() {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtils.getSession();
			tx = session.beginTransaction();

			User3 user = new User3();
			user.setId("003");
			user.setName("张三");
			
			session.save(user);
			
			user.setName("王五");
			session.update(user);
			
			session.flush();
			
			User3 user3 = new User3();
			user3.setId("004");
			user3.setName("李四");
			session.save(user3);
			
			//Hibernate: insert into t_user3 (name, password, create_time, expire_time, user_id) values (?, ?, ?, ?, ?)
			//Hibernate: update t_user3 set name=?, password=?, create_time=?, expire_time=? where user_id=?
			//Hibernate: insert into t_user3 (name, password, create_time, expire_time, user_id) values (?, ?, ?, ?, ?)
			//因为我们在session.udpate(user)后执行了flush，所以在清理缓存时执行flush前的sql不会生成
			//sql会按照我们的意愿执行
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
}
