package com.bjsxt.hibernate;

public class Course {
	private int id;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String courseName) {
		this.name = courseName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
