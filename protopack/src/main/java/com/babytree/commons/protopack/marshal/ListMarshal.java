package com.babytree.commons.protopack.marshal;

import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;
import com.babytree.commons.protopack.util.MarshallUtils;

import java.util.List;



public class ListMarshal<T> implements Marshallable {

	public List<T> list;
	public Class<T> elemType;
	public ListMarshal(Class<T> elemType) {
		this.elemType = elemType;
	}
	public ListMarshal(List<T> data, Class<T> elemType){
		this.list = data;
		this.elemType = elemType;
	}
	@Override
	public void marshal(Pack pack) {
		MarshallUtils.packList(pack, list, elemType);
	}

	@Override
	public void unmarshal(Unpack unpack) {
		list = MarshallUtils.unpackList(unpack, elemType);
	}
	
	@Override
	public String toString() {
		return list.toString();
	}

}
