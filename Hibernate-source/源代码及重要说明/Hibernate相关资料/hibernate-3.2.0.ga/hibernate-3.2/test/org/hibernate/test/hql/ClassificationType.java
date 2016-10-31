package org.hibernate.test.hql;

import org.hibernate.usertype.UserType;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.HibernateException;
import org.hibernate.Hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.io.Serializable;

/**
 * todo: describe ClassificationType
 *
 * @author Steve Ebersole
 */
public class ClassificationType implements EnhancedUserType {

	public int[] sqlTypes() {
		return new int[] { Types.TINYINT };
	}

	public Class returnedClass() {
		return Classification.class;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		if ( x == null && y == null ) {
			return false;
		}
		else if ( x != null ) {
			return x.equals( y );
		}
		else {
			return y.equals( x );
		}
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		String name = ( String ) Hibernate.STRING.nullSafeGet( rs, names[0] );
		return name == null ? null : Classification.valueOf( name );
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		String name = value == null ? null : ( ( Classification ) value ).name();
		Hibernate.STRING.nullSafeSet( st, name, index );
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return ( Classification ) value;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	public String objectToSQLString(Object value) {
		return '\'' + extractName( value ) + '\'';
	}

	public String toXMLString(Object value) {
		return extractName( value );
	}

	public Object fromXMLString(String xmlValue) {
		return Classification.valueOf( xmlValue );
	}

	private String extractName(Object obj) {
		return ( ( Classification ) obj ).name();
	}
}
