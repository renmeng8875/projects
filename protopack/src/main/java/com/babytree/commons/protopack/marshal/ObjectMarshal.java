package com.babytree.commons.protopack.marshal;


import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;

/**
 * 动态打包解包Marshal Bean，而无需继承BeanMarshal
 * 
 * @created 2014-5-17
 */
public class ObjectMarshal extends BeanMarshal {
	private Object obj;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public ObjectMarshal(Object obj) {
		this.obj = obj;
	}

	@Override
	public void marshal(Pack pack) {

		marshalObject(pack, this.obj);
	}

	@Override
	public void unmarshal(Unpack unpack) {
		unmarshalObject(unpack, this.obj);
	}


}

