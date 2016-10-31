package org.hibernate.test.instrument.cases;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractExecutable implements Executable {

	private SessionFactory factory;

	public void prepare() {
		factory = new Configuration()
				.setProperty( Environment.HBM2DDL_AUTO, "create-drop" )
				.addResource( "org/hibernate/test/instrument/domain/Documents.hbm.xml" )
				.buildSessionFactory();
	}

	public void complete() {
		factory.close();
	}

	protected SessionFactory getFactory() {
		return factory;
	}
}
