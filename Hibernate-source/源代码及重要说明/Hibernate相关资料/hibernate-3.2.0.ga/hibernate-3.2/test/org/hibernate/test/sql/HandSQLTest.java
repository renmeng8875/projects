package org.hibernate.test.sql;

import org.hibernate.test.DatabaseSpecificTestCase;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.LockMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.sql.SQLException;

/**
 * @author Steve Ebersole
 */
public abstract class HandSQLTest extends DatabaseSpecificTestCase {

	public HandSQLTest(String name) {
		super( name );
	}

	public String getCacheConcurrencyStrategy() {
		return null;
	}

	public void testHandSQL() {
		Session s = openSession();
		Transaction t = s.beginTransaction();
		Organization ifa = new Organization( "IFA" );
		Organization jboss = new Organization( "JBoss" );
		Person gavin = new Person( "Gavin" );
		Employment emp = new Employment( gavin, jboss, "AU" );
		Serializable orgId = s.save( jboss );
		Serializable orgId2 = s.save( ifa );
		s.save( gavin );
		s.save( emp );
		t.commit();

		t = s.beginTransaction();
		Person christian = new Person( "Christian" );
		s.save( christian );
		Employment emp2 = new Employment( christian, jboss, "EU" );
		s.save( emp2 );
		t.commit();
		s.close();

		getSessions().evict( Organization.class );
		getSessions().evict( Person.class );
		getSessions().evict( Employment.class );

		s = openSession();
		t = s.beginTransaction();
		jboss = ( Organization ) s.get( Organization.class, orgId );
		assertEquals( jboss.getEmployments().size(), 2 );
		emp = ( Employment ) jboss.getEmployments().iterator().next();
		gavin = emp.getEmployee();
		assertEquals( gavin.getName(), "GAVIN" );
		assertEquals( s.getCurrentLockMode( gavin ), LockMode.UPGRADE );
		emp.setEndDate( new Date() );
		Employment emp3 = new Employment( gavin, jboss, "US" );
		s.save( emp3 );
		t.commit();
		s.close();

		s = openSession();
		t = s.beginTransaction();
		Iterator iter = s.getNamedQuery( "allOrganizationsWithEmployees" ).list().iterator();
		assertTrue( iter.hasNext() );
		Organization o = ( Organization ) iter.next();
		assertEquals( o.getEmployments().size(), 3 );
		Iterator iter2 = o.getEmployments().iterator();
		while ( iter2.hasNext() ) {
			Employment e = ( Employment ) iter2.next();
			s.delete( e );
		}
		iter2 = o.getEmployments().iterator();
		while ( iter2.hasNext() ) {
			Employment e = ( Employment ) iter2.next();
			s.delete( e.getEmployee() );
		}
		s.delete( o );
		assertFalse( iter.hasNext() );
		s.delete( ifa );
		t.commit();
		s.close();
	}


	public void testScalarStoredProcedure() throws HibernateException, SQLException {
		Session s = openSession();
		Query namedQuery = s.getNamedQuery( "simpleScalar" );
		namedQuery.setLong( "number", 43 );
		List list = namedQuery.list();
		Object o[] = ( Object[] ) list.get( 0 );
		assertEquals( o[0], "getAll" );
		assertEquals( o[1], new Long( 43 ) );
		s.close();
	}

	public void testParameterHandling() throws HibernateException, SQLException {
		Session s = openSession();

		Query namedQuery = s.getNamedQuery( "paramhandling" );
		namedQuery.setLong( 0, 10 );
		namedQuery.setLong( 1, 20 );
		List list = namedQuery.list();
		Object[] o = ( Object[] ) list.get( 0 );
		assertEquals( o[0], new Long( 10 ) );
		assertEquals( o[1], new Long( 20 ) );

		namedQuery = s.getNamedQuery( "paramhandling_mixed" );
		namedQuery.setLong( 0, 10 );
		namedQuery.setLong( "second", 20 );
		list = namedQuery.list();
		o = ( Object[] ) list.get( 0 );
		assertEquals( o[0], new Long( 10 ) );
		assertEquals( o[1], new Long( 20 ) );
		s.close();
	}

	public void testEntityStoredProcedure() throws HibernateException, SQLException {
		Session s = openSession();
		Transaction t = s.beginTransaction();

		Organization ifa = new Organization( "IFA" );
		Organization jboss = new Organization( "JBoss" );
		Person gavin = new Person( "Gavin" );
		Employment emp = new Employment( gavin, jboss, "AU" );
		s.persist( ifa );
		s.persist( jboss );
		s.persist( gavin );
		s.persist( emp );

		Query namedQuery = s.getNamedQuery( "selectAllEmployments" );
		List list = namedQuery.list();
		assertTrue( list.get( 0 ) instanceof Employment );
		s.delete( emp );
		s.delete( ifa );
		s.delete( jboss );
		s.delete( gavin );

		t.commit();
		s.close();
	}


}
