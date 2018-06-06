package com.babytree.commons.protopack.base;



import com.babytree.commons.protopack.util.Uint;
import com.babytree.commons.protopack.util.Ulong;
import com.babytree.commons.protopack.util.Ushort;

import java.util.List;
import java.util.Map;

/**
 * 使用者只需要面向IPackResponse的实现类
 * IPackRequest提供了popXxx的方法，屏蔽各种数据类型（以及复合数据类型）的实现细节，对用户只提供java语言的几个常用基本类型、List和Map集合以及Marshallable的反序列化。
 * @author: renmeng
 * @date: 2018/5/31
 */
public interface Response{

	public Uint popUint();
	
	public Boolean popBoolean();
	
	public Integer popInteger();
	
	public Byte popByte();

    public byte[] popBytes();
	
	public Short popShort();
	
	public Long popLong();
	
	public String popString();

    public String popString32();

	public Ushort popUshort();
	
	public Ulong popUlong();
	
	public <T> List<T> popList(Class<T> clazz);
	
	public <K, V> Map<K, V> popMap(Class<K> primitiveClazzK, Class<V> primitiveClazzV);
	
	public <K, V> Map<K, V> popMap(Class<K> primitiveClazzK, Class<V> primitiveClazzV, boolean ordered);
	
	//public <T extends Marshallable> T popMarshallable(Class<T> clazz);
	public Marshallable popMarshallable(Marshallable mar);
	
	public Object popBeanMarshal(Object marshallBean) throws Exception;
}
