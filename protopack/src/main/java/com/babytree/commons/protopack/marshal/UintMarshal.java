package com.babytree.commons.protopack.marshal;


import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;
import com.babytree.commons.protopack.util.Uint;

public class UintMarshal implements Marshallable {

	public Uint data;
	public UintMarshal() {
	}
	public UintMarshal(Uint uint) {
		data = uint;
	}
	
	@Override
	public void marshal(Pack pack) {
		pack.putUInt(data);
	}

	@Override
	public void unmarshal(Unpack unpack) {
		data = unpack.popUInt();
	}

	@Override
	public String toString() {
		return String.valueOf(data);
	}
}
