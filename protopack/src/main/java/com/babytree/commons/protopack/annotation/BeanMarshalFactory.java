package com.babytree.commons.protopack.annotation;

import java.util.HashSet;
import java.util.Set;

public class BeanMarshalFactory {

	// 缓存Beans
	public static Set<String> marshallBeanSet = new HashSet<String>();

	@SuppressWarnings("unchecked")
	public static boolean regist(String clazzName)
			throws ClassNotFoundException {

		Class<?> clazz = Class.forName(clazzName);
		regist(clazz);
		return true;
	}

	public static boolean regist(Class<?> clazz) {
		BeanMarshalAno bean =  clazz.getAnnotation(BeanMarshalAno.class);
		if (bean == null) {
			return false;
		}
		marshallBeanSet.add(clazz.getName());

		return true;
	}

	public static boolean ifMarshallBeanCheck(Object marshallBean) {
		if (ifExistMarshallBean(marshallBean.getClass())) {
			return true;
		} else {
			return regist(marshallBean.getClass());
		}
	}

	public static boolean ifExistMarshallBean(Class<?> clazz) {
		return marshallBeanSet.contains(clazz.getName());
	}

	// public static void main(String[] args) {
	//
	// try {
	// TestMarshallBean obj = new TestMarshallBean();
	// // MarshallBeanFactory.regist("MarshallBeanFactory");
	// // MarshallBeanFactory.regist("bean.TestMarshallBean");
	// MarshallBeanFactory.regist(obj.getClass());
	// MarshallBeanFactory
	// .regist("com.yy.ent.commons.yypclient.annotation.TestMarshallBean");
	// System.out.println(marshallBeanSet);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

}
