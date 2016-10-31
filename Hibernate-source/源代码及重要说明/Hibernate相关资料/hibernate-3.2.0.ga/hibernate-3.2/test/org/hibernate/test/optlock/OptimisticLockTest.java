//$Id: OptimisticLockTest.java 10409 2006-09-01 20:05:56Z steve.ebersole@jboss.com $
package org.hibernate.test.optlock;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.JDBCException;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.test.TestCase;

/**
 * Tests relating to the optimisitc-lock mapping option.
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public class OptimisticLockTest extends TestCase {

	public OptimisticLockTest(String str) {
		super(str);
	}

	protected String[] getMappings() {
		return new String[] { "optlock/Document.hbm.xml" };
	}

	protected void configure(Configuration cfg) {
		super.configure( cfg );
		cfg.setProperty( Environment.BATCH_VERSIONED_DATA, "false" );
	}

	public static Test suite() {
		return new TestSuite(OptimisticLockTest.class);
	}

	public void testOptimisticLockDirty() {
		testUpdateOptimisticLockFailure( "LockDirty" );
	}

	public void testOptimisticLockAll() {
		testUpdateOptimisticLockFailure( "LockAll" );
	}

	public void testOptimisticLockDirtyDelete() {
		testDeleteOptimisticLockFailure( "LockDirty" );
	}

	public void testOptimisticLockAllDelete() {
		testDeleteOptimisticLockFailure( "LockAll" );
	}

	private void testUpdateOptimisticLockFailure(String entityName) {
		Session s = openSession();
		Transaction t = s.beginTransaction();
		Document doc = new Document();
		doc.setTitle( "Hibernate in Action" );
		doc.setAuthor( "Bauer et al" );
		doc.setSummary( "Very boring book about persistence" );
		doc.setText( "blah blah yada yada yada" );
		doc.setPubDate( new PublicationDate( 2004 ) );
		s.save( entityName, doc );
		t.commit();
		s.close();

		s = openSession();
		t = s.beginTransaction();
		doc = ( Document ) s.get( entityName, doc.getId() );

		Session otherSession = openSession();
		otherSession.beginTransaction();
		Document otherDoc = ( Document ) otherSession.get( entityName, doc.getId() );
		otherDoc.setSummary( "A modern classic" );
		otherSession.getTransaction().commit();
		otherSession.close();

		try {
			doc.setSummary( "A machiavelian achievement of epic proportions" );
			s.flush();
			fail( "expecting opt lock failure" );
		}
		catch ( StaleObjectStateException expected ) {
			// expected result...
		}
		catch( JDBCException e ) {
			// SQLServer will report this condition via a SQLException
			// when using its SNAPSHOT transaction isolation...
			if ( ! ( getDialect() instanceof SQLServerDialect && e.getErrorCode() == 3960 ) ) {
				throw e;
			}
			else {
				// it seems to "lose track" of the transaction as well...
				t.rollback();
				t = s.beginTransaction();
			}
		}
		s.clear();
		t.commit();
		s.close();

		s = openSession();
		t = s.beginTransaction();
		doc = ( Document ) s.load( entityName, doc.getId() );
		s.delete( entityName, doc );
		t.commit();
		s.close();
	}

	private void testDeleteOptimisticLockFailure(String entityName) {
		Session s = openSession();
		Transaction t = s.beginTransaction();
		Document doc = new Document();
		doc.setTitle( "Hibernate in Action" );
		doc.setAuthor( "Bauer et al" );
		doc.setSummary( "Very boring book about persistence" );
		doc.setText( "blah blah yada yada yada" );
		doc.setPubDate( new PublicationDate( 2004 ) );
		s.save( entityName, doc );
		s.flush();
		doc.setSummary( "A modern classic" );
		s.flush();
		doc.getPubDate().setMonth( new Integer( 3 ) );
		s.flush();
		t.commit();
		s.close();

		s = openSession();
		t = s.beginTransaction();
		doc = ( Document ) s.get( entityName, doc.getId() );

		Session other = openSession();
		Transaction othert = other.beginTransaction();
		Document otherDoc = ( Document ) other.get( entityName, doc.getId() );
		otherDoc.setSummary( "my other summary" );
		other.flush();
		othert.commit();
		other.close();

		try {
			s.delete( doc );
			s.flush();
			fail( "expecting opt lock failure" );
		}
		catch ( StaleObjectStateException e ) {
			// expected
		}
		catch( JDBCException e ) {
			// SQLServer will report this condition via a SQLException
			// when using its SNAPSHOT transaction isolation...
			if ( ! ( getDialect() instanceof SQLServerDialect && e.getErrorCode() == 3960 ) ) {
				throw e;
			}
			else {
				// it seems to "lose track" of the transaction as well...
				t.rollback();
				t = s.beginTransaction();
			}
		}
		s.clear();
		t.commit();
		s.close();

		s = openSession();
		t = s.beginTransaction();
		doc = ( Document ) s.load( entityName, doc.getId() );
		s.delete( entityName, doc );
		t.commit();
		s.close();
	}

}

