package com.babytree.commons.protopack.marshal;


import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;

public class ShortMarshal implements Marshallable {

	public short data;
	public ShortMarshal() {
	}
	public ShortMarshal(short data) {
		this.data = data;
	}

	@Override
	public void marshal(Pack pack) {
		pack.putShort(data);
	}

	@Override
	public void unmarshal(Unpack unpack) {
		data = unpack.popShort();
	}
	@Override
	public String toString() {
		return String.valueOf(data);
	}

}
