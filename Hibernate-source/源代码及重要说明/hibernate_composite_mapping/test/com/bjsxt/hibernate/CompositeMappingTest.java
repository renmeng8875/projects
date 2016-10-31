package com.bjsxt.hibernate;

import java.util.Date;

import org.hibernate.Session;

import junit.framework.TestCase;

public class CompositeMappingTest extends TestCase {

	public void testSave1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			FiscalYearPeriod fiscalYearPeriod = new FiscalYearPeriod();

			FiscalYearPeriodPK pk = new FiscalYearPeriodPK();
			pk.setFiscalYear(2008);
			pk.setFiscalPeriod(8);
			
			fiscalYearPeriod.setFiscalYearPeriodPK(pk);
			
			fiscalYearPeriod.setBeginDate(new Date());
			fiscalYearPeriod.setEndDate(new Date());
			fiscalYearPeriod.setPeriodSts("Y");
			
			session.save(fiscalYearPeriod);
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
			
			FiscalYearPeriodPK pk = new FiscalYearPeriodPK();
			pk.setFiscalYear(2008);
			pk.setFiscalPeriod(8);
			
			FiscalYearPeriod fiscalYearPeriod = (FiscalYearPeriod)session.load(FiscalYearPeriod.class, pk);
			
			System.out.println(fiscalYearPeriod.getPeriodSts());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}			
}
