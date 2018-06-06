package com.babytree.commons.protopack.marshal;

import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;
import com.babytree.commons.protopack.util.MarshallUtils;

import java.util.Map;


public class MapMarshal<T, K> implements Marshallable {

	public Class<T> keyType; 
	public Class<K> valueType; 
	public Map<T, K> data;
	
	public MapMarshal(Class<T> keyType, Class<K> valueType) {
		this.keyType = keyType;
		this.valueType = valueType;
	}
	public MapMarshal(Map<T, K> map, Class<T> keyType, Class<K> valueType) {
		this.keyType = keyType;
		this.valueType = valueType;
		this.data = map;
	}

	@Override
	public void marshal(Pack pack) {
		MarshallUtils.packMap(pack, data, keyType, valueType);
	}

	@Override
	public void unmarshal(Unpack unpack) {
		data = MarshallUtils.unpackMap(unpack, keyType, valueType, false);
	}
	
	@Override
	public String toString() {
		return data.toString();
	}

}
