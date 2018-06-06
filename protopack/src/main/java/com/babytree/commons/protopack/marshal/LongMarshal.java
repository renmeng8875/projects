package com.babytree.commons.protopack.marshal;


import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;

public class LongMarshal implements Marshallable {

	public long data;
	public LongMarshal() {
	}
	public LongMarshal(long data) {
		this.data = data;
	}

	@Override
	public void marshal(Pack pack) {
		pack.putLong(data);
	}

	@Override
	public void unmarshal(Unpack unpack) {
		data = unpack.popLong();
	}
	
	@Override
	public String toString() {
		return String.valueOf(data);
	}

}
