package com.babytree.commons.protopack.marshal;


import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;

public class ByteMarshal implements Marshallable {

	public byte data;
	
	public ByteMarshal() {
	}
	public ByteMarshal(byte data) {
		this.data = data;
	}

	@Override
	public void marshal(Pack pack) {
		pack.putByte(data);
	}

	@Override
	public void unmarshal(Unpack unpack) {
		data = unpack.popByte();
	}
	@Override
	public String toString() {
		return String.valueOf(data);
	}
}
