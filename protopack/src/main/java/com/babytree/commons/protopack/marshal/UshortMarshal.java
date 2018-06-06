package com.babytree.commons.protopack.marshal;


import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;
import com.babytree.commons.protopack.util.Ushort;

public class UshortMarshal implements Marshallable {

	public Ushort data;
	public UshortMarshal() {
	}
	public UshortMarshal(Ushort ulong) {
		data = ulong;
	}
	
	@Override
	public void marshal(Pack pack) {
		pack.putUshort(data);
	}

	@Override
	public void unmarshal(Unpack unpack) {
		data = unpack.popUshort();
	}

	@Override
	public String toString() {
		return String.valueOf(data);
	}
}
