package com.h5.weibo.utils;

public class StringUtil {
	
	/**
	 * 判断是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if(str == null || str.trim().length() == 0)
			return true;
		
		return false;
	}
}
