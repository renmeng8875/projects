package com.h5.weibo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	
	static {
		//AnnotationConfiguration ac = new AnnotationConfiguration();

		//sessionFactory = new AnnotationConfiguration().buildSessionFactory();
		sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
	}
	
	private static ThreadLocal<Session> localSession = new ThreadLocal<Session>();

    /**
     * 获取当前线程的session
     * @return
     */
    public static Session getCurrentSession(){

       Session s = localSession.get();
        if (s == null) {
            s = sessionFactory.openSession();

            localSession.set(s);
        } else if (!s.isConnected()) {      // 如果已关闭，则重开链接
            s = sessionFactory.openSession();
            localSession.set(s);
        }

        return s;
    } 
    
    public static void beginTransation() {
    	getCurrentSession().getTransaction().begin();
    }
    
    public static void commitTransation() {
    	getCurrentSession().getTransaction().commit();
    }
    
    public static void rollbackTransation() {
    	getCurrentSession().getTransaction().rollback();
    }
    /**
     * 关闭连接
     */
    public static void closeSession(){
        Session s = localSession.get();
        if(s == null) return;

        localSession.set(null);
        s.close();
        s = null;
    }
}
