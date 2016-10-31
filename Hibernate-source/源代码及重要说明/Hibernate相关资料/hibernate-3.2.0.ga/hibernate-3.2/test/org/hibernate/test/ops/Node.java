//$Id: Node.java 8897 2005-12-21 22:52:08Z steveebersole $
package org.hibernate.test.ops;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gavin King
 */
public class Node {
	
	private String name;
	private Node parent;
	private Set children = new HashSet();
	private String description;
	private Date created;
	
	public Node() {}
	
	public Node(String name) {
		this.name = name;
		created = generateCurrentDate();
	}

	public Set getChildren() {
		return children;
	}
	public void setChildren(Set children) {
		this.children = children;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public Node addChild(Node child) {
		children.add(child);
		child.setParent(this);
		return this;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	private Date generateCurrentDate() {
		// Note : done as java.sql.Date mainly to work around issue with
		// MySQL and its lack of milli-second precision on its DATETIME
		// and TIMESTAMP datatypes.
		return new Date( new java.util.Date().getTime() );
	}
}
