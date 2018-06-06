package com.babytree.commons.protopack.marshal;

import com.babytree.commons.protopack.base.Unpack;
import com.babytree.commons.protopack.util.MarshallUtils;

import java.util.Map;


/**
 * 
 * 用LinkedHashMap作为解包的类型
 * @author: renmeng
 * @date: 2018/5/31
 */
public class LinkedMapMarshal<T, K> extends MapMarshal<T, K> {

	public LinkedMapMarshal(Class<T> keyType, Class<K> valueType) {
		super(keyType, valueType);
	}

	public LinkedMapMarshal(Map<T, K> map, Class<T> keyType, Class<K> valueType) {
		super(map, keyType, valueType);
	}

	@Override
	public void unmarshal(Unpack unpack) {
		data = MarshallUtils.unpackMap(unpack, keyType, valueType, true);
	}
	
	@Override
	public String toString() {
		return data.toString();
	}

}
