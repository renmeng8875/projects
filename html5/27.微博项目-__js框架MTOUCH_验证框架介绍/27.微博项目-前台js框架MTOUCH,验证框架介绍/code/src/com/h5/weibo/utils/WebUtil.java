package com.h5.weibo.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {
	/**
	 * 缓存放射的对象
	 */
	private static Map<String,Method[]> reflectCache = new HashMap<String, Method[]>(); 
	
	/**
     * 应用请求值,把请求的值赋值给某个对象
     * @param c 反射的class
     * @param instance  control实例
     * @param request
     */
    public static <T> T getBeanFromRequest(Class<?> c, HttpServletRequest request) throws Exception{
    	Object instance = c.newInstance();
    	
    	// 先从缓存中获取数据
        Method[] ms = reflectCache.get(c.getName());
        if(ms == null) {
        	ms = c.getDeclaredMethods();
        	reflectCache.get(c.getName());
        }
        for (int i = 0; i < ms.length; i++) {
            String name = ms[i].getName();
            if (!name.startsWith("set")) {    //  不是setter 
                continue;
            }
            Class[] params = ms[i].getParameterTypes();
            // 参数不是1个的
            if (params.length != 1) {
                continue;
            }
            // 判断参数类型，如果是接口和数组则不赋值
            if(params[0].isArray() || params[0].isInterface()){
                continue;
            }
            // 获取参数类型
            String type = params[0].getName(); // parameter type
            // 获取字段的名称
            String propName = Character.toLowerCase(name.charAt(3)) + name.substring(4);
            // 取参数值  
            String paramValue =  request.getParameter(propName);

            if (type.equals("java.lang.String")) {
                if(paramValue != null){
                    ms[i].invoke(instance, new Object[] {paramValue});
                }
            } else if (type.equals("java.lang.Integer") || type.equals("int")) {
                if(paramValue != null){
                    ms[i].invoke(instance, new Object[] {new Integer(paramValue)});
                }
            }else if(type.equals("long") || type.equals("java.lang.Long")) {
                if(paramValue != null){
                    ms[i].invoke(instance, new Object[] {new Long(paramValue)});
                }
            }else if(type.equals("boolean") || type.equals("java.lang.Boolean")){
                if(paramValue != null){
                    ms[i].invoke(instance, new Object[] {new Boolean(paramValue)});
                }
            }else if(type.equals("float") || type.equals("java.lang.Float")){
                if(paramValue != null){
                    ms[i].invoke(instance, new Object[] {new Float(paramValue)});
                }
            }else if(type.equals("short") || type.equals("java.lang.Short")){
                if(paramValue != null){
                    ms[i].invoke(instance, new Object[] {new Short(paramValue)});
                }                
            }else{
                
            }
        }
        return (T) instance;
    }

}
