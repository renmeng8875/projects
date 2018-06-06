package com.babytree.commons.protopack.base;



import com.babytree.commons.protopack.util.Uint;
import com.babytree.commons.protopack.util.Ulong;
import com.babytree.commons.protopack.util.Ushort;

import java.util.List;
import java.util.Map;

/**
 * 使用者只需要面向IPackRequest的实现类
 * IPackRequest提供了addXxx的方法，屏蔽各种数据类型（以及复合数据类型）的实现细节，对用户只提供java语言的几个常用基本类型、List和Map集合以及Marshallable的序列化。
 * @author: renmeng
 * @date: 2018/5/31
 */
public interface Request{
	public void putUint(Uint uint);
	
	public void putInteger(Integer integer);
	
	public void putLong(Long lng);
	
	public void putBoolean(Boolean bool);
	
	public void putShort(Short sht);
	
	public void putByte(Byte byt);

    public void putBytes(byte[] byts);
	
	public void putString(String str);

    public void putString32(String str);

	public void putUshort(Ushort uint);
	
	public void putUlong(Ulong ulong);
	/**
	 * @param <T> 列表元素的类型
	 * @param list 要序列化的列表
	 * @param primitiveClazz 列表元素类型的class对象
	 */
	public <T> void putList(List<T> list, Class<T> primitiveClazz);
	
	/**
	 * 
	 * @param <K> map的key类型
	 * @param <V> map的value类型
	 * @param map 要进行序列化的map
	 * @param primitiveClazzK map的key类型的class对象
	 * @param primitiveClazzV map的value类型的class对象
	 */
	public <K, V> void putMap(Map<K, V> map, Class<K> primitiveClazzK, Class<V> primitiveClazzV);
	
	public void putMarshall(Marshallable mar);
	
	public void putBeanMarshal(Object marshallBean) throws Exception;

}
