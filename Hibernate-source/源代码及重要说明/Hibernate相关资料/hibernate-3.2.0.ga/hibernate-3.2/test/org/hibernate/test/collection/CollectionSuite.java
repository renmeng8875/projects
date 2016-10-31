package org.hibernate.test.collection;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.hibernate.test.collection.map.PersistentMapTest;
import org.hibernate.test.collection.set.PersistentSetTest;

/**
 * todo: describe CollectionSuite
 *
 * @author Steve Ebersole
 */
public class CollectionSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite( "Collection-related tests" );
		suite.addTest( CollectionTest.suite() );
		suite.addTest( PersistentMapTest.suite() );
		suite.addTest( PersistentSetTest.suite() );
		return suite;
	}

}
