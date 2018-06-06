package com.babytree.commons.protopack.marshal;

import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;

/**
 *
 * @author: renmeng
 * @date: 2018/5/31
 */
public class IntegerMarshal implements Marshallable {
	
	public Integer data;

	public IntegerMarshal() {
	}
	
	public IntegerMarshal( Integer value) {
 		this.data = value;
	}
	
	@Override
	public void marshal(Pack pack) {
		pack.putInt(data);
	}

	@Override
	public void unmarshal(Unpack unpack) {
		data = unpack.popInt();
	}
	@Override
	public String toString() {
		return String.valueOf(data);
	}
}
