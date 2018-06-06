package com.babytree.commons.protopack.marshal;


import net.sf.json.JSONObject;

public class JSONMarshal extends StringMarshal
{
	public JSONMarshal(){
		
	}
	public JSONMarshal(JSONObject json) {
		super(json.toString());
	}
	
	public JSONObject getJSONObject(){
		return JSONObject.fromObject(super.data);
	}
	
	@Override
	public String toString() {
		return super.data;
	}
}

