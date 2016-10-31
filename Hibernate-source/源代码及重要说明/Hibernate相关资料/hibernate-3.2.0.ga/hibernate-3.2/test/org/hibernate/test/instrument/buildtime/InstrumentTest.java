//$Id: InstrumentTest.java 10271 2006-08-15 21:18:09Z steve.ebersole@jboss.com $
package org.hibernate.test.instrument.buildtime;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.hibernate.intercept.FieldInterceptionHelper;
import org.hibernate.test.TestCase;
import org.hibernate.test.instrument.domain.Document;
import org.hibernate.test.instrument.cases.TestDirtyCheckExecutable;
import org.hibernate.test.instrument.cases.TestFetchAllExecutable;
import org.hibernate.test.instrument.cases.TestLazyExecutable;
import org.hibernate.test.instrument.cases.TestLazyManyToOneExecutable;
import org.hibernate.test.instrument.cases.TestInjectFieldInterceptorExecutable;
import org.hibernate.test.instrument.cases.TestIsPropertyInitializedExecutable;
import org.hibernate.test.instrument.cases.TestManyToOneProxyExecutable;
import org.hibernate.test.instrument.cases.Executable;

/**
 * @author Gavin King
 */
public class InstrumentTest extends TestCase {

	public InstrumentTest(String str) {
		super(str);
	}

	public void testDirtyCheck() {
		execute( new TestDirtyCheckExecutable() );
	}

	public void testFetchAll() throws Exception {
		execute( new TestFetchAllExecutable() );
	}

	public void testLazy() throws Exception {
		execute( new TestLazyExecutable() );
	}

	public void testLazyManyToOne() {
		execute( new TestLazyManyToOneExecutable() );
	}

	public void testSetFieldInterceptor() {
		execute( new TestInjectFieldInterceptorExecutable() );
	}

	public void testPropertyInitialized() {
		execute( new TestIsPropertyInitializedExecutable() );
	}

	public void testManyToOneProxy() {
		execute( new TestManyToOneProxyExecutable() );
	}

	private void execute(Executable executable) {
		executable.prepare();
		try {
			executable.execute();
		}
		finally {
			executable.complete();
		}
	}

	protected String[] getMappings() {
		return new String[] { "instrument/domain/Documents.hbm.xml" };
	}

	protected void runTest() throws Throwable {
		if ( isRunnable() ) {
			super.runTest();
		}
		else {
			reportSkip( "domain classes not instrumented", "build-time instrumentation" );
		}
	}

	public static Test suite() {
		return new TestSuite(InstrumentTest.class);
	}

	public static boolean isRunnable() {
		return FieldInterceptionHelper.isInstrumented( new Document() );
	}
}

