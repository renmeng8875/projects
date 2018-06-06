package com.babytree.commons.protopack.marshal;


import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;

public class BooleanMarshal implements Marshallable {

	public boolean data;
	
	public BooleanMarshal() {
	}
	public BooleanMarshal(boolean data) {
		this.data = data;
	}

	@Override
	public void marshal(Pack pack) {
		pack.putBoolean(data);
	}

	@Override
	public void unmarshal(Unpack unpack) {
		data = unpack.popBoolean();
	}
	@Override
	public String toString() {
		return String.valueOf(data);
	}
}
