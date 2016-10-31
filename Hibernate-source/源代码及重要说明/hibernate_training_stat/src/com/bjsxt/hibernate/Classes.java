package com.bjsxt.hibernate;

import java.util.Set;

public class Classes {
	private int id;
	private String classesNo;
	private Set students;
	
	public String getClassesNo() {
		return classesNo;
	}
	public void setClassesNo(String classesNo) {
		this.classesNo = classesNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Set getStudents() {
		return students;
	}
	public void setStudents(Set students) {
		this.students = students;
	}
}
