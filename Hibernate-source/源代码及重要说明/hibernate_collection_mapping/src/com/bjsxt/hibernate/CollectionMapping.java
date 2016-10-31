package com.bjsxt.hibernate;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionMapping {
	
	private int id;
	
	private String name;
	
	private Set setValue;
	
	private List listValue;
	
	private String[] arrayValue;
	
	private Map mapValue;

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

	public Set getSetValue() {
		return setValue;
	}

	public void setSetValue(Set setValue) {
		this.setValue = setValue;
	}

	public List getListValue() {
		return listValue;
	}

	public void setListValue(List listValue) {
		this.listValue = listValue;
	}

	public String[] getArrayValue() {
		return arrayValue;
	}

	public void setArrayValue(String[] arrayValue) {
		this.arrayValue = arrayValue;
	}

	public Map getMapValue() {
		return mapValue;
	}

	public void setMapValue(Map mapValue) {
		this.mapValue = mapValue;
	}
}
