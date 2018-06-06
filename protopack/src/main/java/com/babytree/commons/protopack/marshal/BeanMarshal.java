package com.babytree.commons.protopack.marshal;


import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;
import com.babytree.commons.protopack.exception.PackException;
import com.babytree.commons.protopack.exception.UnpackException;
import com.babytree.commons.protopack.util.GenericsType;
import com.babytree.commons.protopack.util.GenericsUtils;
import com.babytree.commons.protopack.util.TypeVo;
import com.babytree.commons.protopack.util.Uint;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 动态打包或解包bean中的非transient field
 * @author: renmeng
 * @date: 2018/5/31
 */
public abstract class BeanMarshal implements Marshallable {
	/**
	 * 缓存各子类的所有属性，为了提高反射效率
	 */
	private static Map<Object, Field[]> classFields = new HashMap<Object, Field[]>();
	
	@Override
	public void marshal(Pack pack) {
		
		marshalObject(pack,this);
	}

    private short getShortFiled(Object obj,Field field)throws Exception{
        Object result = field.get(obj);
        if(result == null){
            return  0;
        }
        return Short.parseShort(result.toString());
    }

    private int getIntFiled(Object obj,Field field)throws Exception{
        Object result = field.get(obj);
        if(result == null){
            return  0;
        }
        return Integer.parseInt(result.toString());
    }

    private long getLongFiled(Object obj,Field field)throws Exception{
        Object result = field.get(obj);
        if(result == null){
            return  0l;
        }
        return Long.parseLong(result.toString());
    }

    private String getStringFiled(Object obj,Field field)throws Exception{
        Object result = field.get(obj);
        if(result == null){
            return  "";
        }
        return result.toString();
    }

    private boolean getBooleanFiled(Object obj,Field field)throws Exception{
        Object result = field.get(obj);
        if(result == null){
            return  false;
        }
        return Boolean.parseBoolean(result.toString());
    }

	
	protected void marshalObject(Pack pack, Object obj) {
		if(obj==null){
			return ;
		}
		Field[] fields = getFields(obj);
		if(fields==null || fields.length==0){
			return ;
		}
		for (Field field : fields) {
			try {
				if(field.getType().isPrimitive()){
					if(short.class.isAssignableFrom(field.getType())){
						pack.putShort(field.getShort(obj));
					}else if(int.class.isAssignableFrom(field.getType())){
						pack.putInt(field.getInt(obj));
					}else if(long.class.isAssignableFrom(field.getType())){
						pack.putLong(field.getLong(obj));
					}else if(boolean.class.isAssignableFrom(field.getType())){
						pack.putBoolean(field.getBoolean(obj));
					}else if (byte.class.isAssignableFrom(field.getType())){
						pack.putByte(field.getByte(obj));
					}
				}else
				{
					if(String.class.isAssignableFrom(field.getType())){
						pack.putVarstr(getStringFiled(obj,field));
					}else if(Short.class.isAssignableFrom(field.getType())){
						pack.putShort(getShortFiled(obj,field));
					}else if(Integer.class.isAssignableFrom(field.getType())){
						pack.putInt(getIntFiled(obj,field));
					}else if(Long.class.isAssignableFrom(field.getType())){
						pack.putLong(getLongFiled(obj,field));
					}else if(Boolean.class.isAssignableFrom(field.getType())){
						pack.putBoolean(getBooleanFiled(obj,field));
					}else if (Byte.class.isAssignableFrom(field.getType())){
                        Object result = field.get(obj);
                        if(result != null){
                            pack.putByte(Byte.parseByte(result.toString()));
                        }
					}
					else if (Uint.class.isAssignableFrom(field.getType())){
                        Object result = field.get(obj);
                        if(result != null){
                            result = new Uint(0);
                        }
						pack.putUInt(new Uint(result.toString()));
					}
					else if(List.class.isAssignableFrom(field.getType())){
						List<?> list = (List<?>)field.get(obj);
						marshalList(pack,list,GenericsUtils.getGenericType(field.getGenericType()));
					}
					else if(Map.class.isAssignableFrom(field.getType())){
						marshalMap(pack,(Map<?,?>)field.get(obj), GenericsUtils.getGenericType(field.getGenericType()));
					}
					else if(Marshallable.class.isAssignableFrom(field.getType())){
						Object marObj = field.get(obj);
						if(marObj!=null){
							((Marshallable)marObj).marshal(pack);}
					}
					else if(Object.class.isAssignableFrom(field.getType())){
						marshalObject(pack,field.get(obj));
					}
					else{
						throw new PackException("unkown type to marshalObject : " + field.getType());
					}
				}
			} catch (Exception e) {
				throw new PackException("unkown type to marshalObject : " + field.getType());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void unmarshalObject(Unpack unpack, Object obj) {
		Field[] fields = getFields(obj);
		if(fields==null || fields.length==0){
			return ;
		}
		for (Field field : fields) {
			try {
				if(field.getType().isPrimitive()){
					if(short.class.isAssignableFrom(field.getType())){
						field.setShort(obj, unpack.popShort());
					}else if(int.class.isAssignableFrom(field.getType())){
						field.setInt(obj, unpack.popInt());
					}else if(long.class.isAssignableFrom(field.getType())){
						field.setLong(obj, unpack.popLong());
					}else if(boolean.class.isAssignableFrom(field.getType())){
						field.setBoolean(obj, unpack.popBoolean());
					}else if (byte.class.isAssignableFrom(field.getType())){
						field.setByte(obj, unpack.popByte());
					}
				}else
				{
					if(String.class.isAssignableFrom(field.getType())){
						field.set(obj, unpack.popVarstr());
					}else if(Short.class.isAssignableFrom(field.getType())){
						field.set(obj,unpack.popShort());
					}else if(Integer.class.isAssignableFrom(field.getType())){
						field.set(obj,unpack.popInt());
					}else if(Long.class.isAssignableFrom(field.getType())){
						field.set(obj,unpack.popLong());
					}else if(Boolean.class.isAssignableFrom(field.getType())){
						field.set(obj,unpack.popBoolean());
					}else if (Byte.class.isAssignableFrom(field.getType())){
						field.set(obj,unpack.popByte());
					}
					else if (Uint.class.isAssignableFrom(field.getType())){
						field.set(obj, unpack.popUInt());
					}
					else if(List.class.isAssignableFrom(field.getType())){
						List<Object> objValue = (List<Object>)field.get(obj);
						if(objValue==null){
							objValue = new ArrayList<Object>();
							field.set(obj, objValue);
						}
						unmarshalList(unpack,objValue,GenericsUtils.getGenericType(field.getGenericType()));
					}
					else if(Map.class.isAssignableFrom(field.getType())){
						Map<Object,Object> objValue = (Map<Object,Object>)field.get(obj);
						if(objValue==null){
							objValue = new HashMap<Object,Object>();
							field.set(obj, objValue);
						}
						unmarshalMap(unpack,objValue,GenericsUtils.getGenericType(field.getGenericType()));
					}
					else if(Marshallable.class.isAssignableFrom(field.getType())){
						Object objValue = field.get(obj);
						if(objValue==null){
							objValue = field.getType().newInstance();
							field.set(obj, objValue);
						}
						((Marshallable)objValue).unmarshal(unpack);
					}
					else if(Object.class.isAssignableFrom(field.getType())){
						Object objValue = field.get(obj);
						if(objValue==null){
							objValue = field.getType().newInstance();
							field.set(obj, objValue);
						}
						unmarshalObject(unpack,objValue);
					}
				}
			} catch (Exception e) {
				throw new PackException("unkown type to unmarshalObject : " + field.getType());
			}
		}
	}
	
	private void marshalList(Pack pack,List<?> list,GenericsType genericsType)throws Exception{
		if(list==null){
			pack.putInt(0);
			return ;
		}
		pack.putInt(list.size());
		if(list.isEmpty()){
			return ;
		}
		TypeVo vo = genericsType.getType(0);
		Class<?> genericClazz = vo.getCls();
		for(int i=0;i<list.size();i++){
			String value = list.get(i).toString();
			if(genericClazz == String.class){
				pack.putVarstr(value);
			}else if(genericClazz == Short.class){
				pack.putShort(Short.valueOf(value));
			}else if(genericClazz == Integer.class){
				pack.putInt(Integer.parseInt(value));
			}else if(genericClazz == Uint.class){
				pack.putUInt(new Uint(value));
			}else if(genericClazz == Long.class){
				pack.putLong(Long.parseLong(value));
			}else if(genericClazz == Boolean.class){
				pack.putBoolean(Boolean.parseBoolean(value));
			}else if (Byte.class.isAssignableFrom(genericClazz)){
				pack.putByte(Byte.parseByte(value));
			}
			else if(List.class.isAssignableFrom(genericClazz)){
				marshalList(pack,(List<?>)list.get(i),GenericsUtils.getGenericType(vo.getNextType()));
			}
			else if(Map.class.isAssignableFrom(genericClazz)){
				marshalMap(pack,(Map<?,?>)list.get(i),GenericsUtils.getGenericType(vo.getNextType()));
			}
			else if(Marshallable.class.isAssignableFrom(genericClazz)){
				((Marshallable)list.get(i)).marshal(pack);
			}
			else if(Object.class.isAssignableFrom(genericClazz)){
				marshalObject(pack,list.get(i));
			}
		}
		
	}
	
	private void unmarshalList(Unpack unpack,List<Object> list,GenericsType genericsType)throws Exception{
		int size = unpack.popInt();
		if(size==0)return ;
		TypeVo vo = genericsType.getType(0);
		Class<?> genericClazz = vo.getCls();
		for(int i=0;i<size;i++){
			if(genericClazz == String.class){
				list.add(unpack.popVarstr());
			}else if(genericClazz == Short.class){
				list.add(unpack.popShort());
			}else if(genericClazz == Integer.class){
				list.add(unpack.popInt());
			}else if(genericClazz == Uint.class){
				list.add(unpack.popUInt());
			}else if(genericClazz == Long.class){
				list.add(unpack.popLong());
			}else if(genericClazz == Boolean.class){
				list.add(unpack.popBoolean());
			}else if (Byte.class.isAssignableFrom(genericClazz)){
				list.add(unpack.popByte());
			}
			else if(List.class.isAssignableFrom(genericClazz)){
				List<Object> l = new ArrayList<Object>();
				list.add(l);
				unmarshalList(unpack,l,GenericsUtils.getGenericType(vo.getNextType()));
			}
			else if(Map.class.isAssignableFrom(genericClazz)){
				Map<Object,Object> chMap = new HashMap<Object,Object>();
				list.add(chMap);
				unmarshalMap(unpack,chMap,GenericsUtils.getGenericType(vo.getNextType()));
			}
			else if(Marshallable.class.isAssignableFrom(genericClazz)){
				Marshallable mar = (Marshallable)genericClazz.newInstance();
				list.add(mar);
				mar.unmarshal(unpack);
			}
			else if(Object.class.isAssignableFrom(genericClazz)){
				Object obj = genericClazz.newInstance();
				list.add(obj);
				unmarshalObject(unpack,obj);
			}
		}
	}

	private void marshalMap(Pack pack,Map<?,?> map,GenericsType genericsType)throws Exception{
		if(map==null){
			pack.putInt(0);
			return ;
		}
		pack.putInt(map.size());
		if(map.size()==0)return ;
		TypeVo voKey = genericsType.getType(0);
		TypeVo voValue = genericsType.getType(1);
		Class<?> genericClazzKey = voKey.getCls();
		Class<?> genericClazzValue = voValue.getCls();
		Set<?>keys = map.keySet();
		for(Object key:keys){
//			pack.putVarstr(key.toString()); // key打包的时候不应该打包为string
			
			// put key
			String keyStr = key.toString();
			if(genericClazzKey == String.class){
				pack.putVarstr(keyStr);
			}else if(genericClazzKey == Short.class){
				pack.putShort(Short.valueOf(keyStr));
			}else if(genericClazzKey == Integer.class){
				pack.putInt(Integer.parseInt(keyStr));
			}else if(genericClazzKey == Uint.class){
				pack.putUInt(new Uint(keyStr));
			}else if(genericClazzKey == Long.class){
				pack.putLong(Long.parseLong(keyStr));
			}else if(genericClazzKey == Boolean.class){
				pack.putBoolean(Boolean.parseBoolean(keyStr));
			}else if (Byte.class.isAssignableFrom(genericClazzKey)){
				pack.putByte(Byte.parseByte(keyStr));
			}else if(List.class.isAssignableFrom(genericClazzKey)){
				marshalList(pack,(List<?>)key,GenericsUtils.getGenericType(voKey.getNextType()));
			}else if(Map.class.isAssignableFrom(genericClazzKey)){
				marshalMap(pack,(Map<?,?>)key,GenericsUtils.getGenericType(voKey.getNextType()));
			}else if(Marshallable.class.isAssignableFrom(genericClazzKey)){
				((Marshallable)key).marshal(pack);
			}else if(Object.class.isAssignableFrom(genericClazzKey)){
				marshalObject(pack,key);
			}else{
				// 复杂类型，不处理，抛异常
				throw new PackException("key type in a map should not be complicated type :" + genericClazzKey);
			}
			
			// put value
			String value = map.get(key).toString();
			if(genericClazzValue == String.class){
				pack.putVarstr(value);
			}else if(genericClazzValue == Short.class){
				pack.putShort(Short.valueOf(value));
			}else if(genericClazzValue == Integer.class){
				pack.putInt(Integer.parseInt(value));
			}else if(genericClazzValue == Uint.class){
				pack.putUInt(new Uint(value));
			}else if(genericClazzValue == Long.class){
				pack.putLong(Long.parseLong(value));
			}else if(genericClazzValue == Boolean.class){
				pack.putBoolean(Boolean.parseBoolean(value));
			}else if (Byte.class.isAssignableFrom(genericClazzValue)){
				pack.putByte(Byte.parseByte(value));
			}
			else if(List.class.isAssignableFrom(genericClazzValue)){
				marshalList(pack,(List<?>)map.get(key),GenericsUtils.getGenericType(voValue.getNextType()));
			}
			else if(Map.class.isAssignableFrom(genericClazzValue)){
				marshalMap(pack,(Map<?,?>)map.get(key),GenericsUtils.getGenericType(voValue.getNextType()));
			}
			else if(Marshallable.class.isAssignableFrom(genericClazzValue)){
				((Marshallable)map.get(key)).marshal(pack);
			}
			else if(Object.class.isAssignableFrom(genericClazzValue)){
				marshalObject(pack,map.get(key));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void unmarshalMap(Unpack unpack,Map<Object,Object> map,GenericsType genericsType)throws Exception{
		int size = unpack.popInt();
		if(size==0){
			return ;
		}
		TypeVo keyVo = genericsType.getType(0);
		Class<?> keyValueType = keyVo.getCls();
		
		TypeVo vo = genericsType.getType(1);
		Class<?> valueType = vo.getCls();
		for(int i=0;i<size;i++){
			Object key = null;
			//key
			if(keyValueType == String.class){
				key = unpack.popVarstr();
			}else if(keyValueType == Short.class){
				key = unpack.popShort();
			}else if(keyValueType == Integer.class){
				key = unpack.popInt();
			}else if(keyValueType == Uint.class){
				key = unpack.popUInt();
			}else if(keyValueType == Long.class){
				key = unpack.popLong();
			}else if(keyValueType == Boolean.class){
				key = unpack.popBoolean();
			}else if (Byte.class.isAssignableFrom(keyValueType)){
				key = unpack.popByte();
			}
			else if(List.class.isAssignableFrom(keyValueType)){
				List<Object> list = new ArrayList<Object>();
				unmarshalList(unpack,list,GenericsUtils.getGenericType(vo.getNextType()));
				key = list;
			}
			else if(Map.class.isAssignableFrom(keyValueType)){
				Map<Object,Object> chMap = (Map<Object,Object>)key;
				unmarshalMap(unpack,chMap,GenericsUtils.getGenericType(vo.getNextType()));
				key = chMap;
			}
			else if(Marshallable.class.isAssignableFrom(keyValueType)){
				if(keyValueType.isInterface()){
					throw new UnpackException("create object error , only specify interface : " + keyValueType);
				}else{
					Marshallable mar = (Marshallable)keyValueType.newInstance();
					mar.unmarshal(unpack);
					key = mar;
				}
			}
			else if(Object.class.isAssignableFrom(keyValueType)){
				key = unpack.popObject(unpack);
			}else{
				// 复杂类型，不处理，抛异常
				throw new PackException("key type in a map should not be complicated type :" + keyValueType);
			}
			
			
			//value
			if(valueType == String.class){
				map.put(key, unpack.popVarstr());
			}else if(valueType == Short.class){
				map.put(key, unpack.popShort());
			}else if(valueType == Integer.class){
				map.put(key, unpack.popInt());
			}else if(valueType == Uint.class){
				map.put(key, unpack.popUInt());
			}else if(valueType == Long.class){
				map.put(key, unpack.popLong());
			}else if(valueType == Boolean.class){
				map.put(key, unpack.popBoolean());
			}
			else if (Byte.class.isAssignableFrom(valueType)){
				map.put(key, unpack.popByte());
			}
			else if(List.class.isAssignableFrom(valueType)){
				List<Object> list = (List<Object>)map.get(key);
				if(list==null){
					list = new ArrayList<Object>();
					map.put(key, list);
				}
				unmarshalList(unpack,list,GenericsUtils.getGenericType(vo.getNextType()));
			}
			else if(Map.class.isAssignableFrom(valueType)){
				Map<Object,Object> chMap = (Map<Object,Object>)map.get(key);
				if(chMap==null){
					chMap = new HashMap<Object,Object>();
					map.put(key, chMap);
				}
				unmarshalMap(unpack,chMap,GenericsUtils.getGenericType(vo.getNextType()));
			}
			else if(Marshallable.class.isAssignableFrom(valueType)){
//				((Marshallable)map.get(k)).unmarshal(unpack);
				if(valueType.isInterface()){
					throw new UnpackException("create object error , only specify interface : " + valueType);
				}else{
					Marshallable mar = (Marshallable)valueType.newInstance();
					map.put(key, mar);
					mar.unmarshal(unpack);
				}
			}
			else if(Object.class.isAssignableFrom(valueType)){
				unmarshalObject(unpack,map.get(key));
			}
		}
	}

	@Override
	public void unmarshal(Unpack unpack) {
		unmarshalObject(unpack, this);
	}

	/**
	 * 获取子类的属性
	 * @return
	 */
	public Field[] getFields(Object vo){
		Field[] fields = classFields.get(vo.getClass());
		if(fields == null){
			Field[] tmpFields = vo.getClass().getDeclaredFields();
			List<Field> mshFields = new ArrayList<Field>();
			for (Field field : tmpFields) {
				// boolean hasAnnotation = field.isAnnotationPresent(Transient.class); 
				 if(!Modifier.isTransient(field.getModifiers())){
					 field.setAccessible(true);//取消检查访问控制，提高反射效率
					 mshFields.add(field);
				 }
			}
			fields = mshFields.toArray(new Field[0]);
			classFields.put(vo.getClass(), fields);
		}
		return fields;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Field[] fields = getFields(this);
		sb.append("{");
		for (int i = 0; i < fields.length; i++) {
			try {
				Field field = fields[i];
				sb.append(field.getName() + "=" + field.get(this));
				if(i != fields.length - 1){
					sb.append(", ");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sb.append("}");
		return sb.toString();
	}
}
