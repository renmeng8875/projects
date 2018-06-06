package com.babytree.commons.protopack.marshal;


import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;
import com.babytree.commons.protopack.util.Ulong;

public class UlongMarshal implements Marshallable {

	public Ulong data;
	public UlongMarshal() {
	}
	public UlongMarshal(Ulong ulong) {
		data = ulong;
	}
	
	@Override
	public void marshal(Pack pack) {
		pack.putUlong(data);
	}

	@Override
	public void unmarshal(Unpack unpack) {
		data = unpack.popUlong();
	}

	@Override
	public String toString() {
		return String.valueOf(data);
	}
}
