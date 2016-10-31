package com.bjsxt.hibernate;

import java.util.Set;

public class User {
	
	private int id;
	
	private String name;
	
	private Set roles; 
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getRoles() {
		return roles;
	}

	public void setRoles(Set roles) {
		this.roles = roles;
	}
	
}
