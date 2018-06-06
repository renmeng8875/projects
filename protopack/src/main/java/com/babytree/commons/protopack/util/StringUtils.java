package com.babytree.commons.protopack.util;

public class StringUtils {
	public static String byteToBitString(byte byt){
		StringBuilder sb = new StringBuilder(8);
		for(int i = 0; i < 8; i++){
			sb.append((byt & 1));
			byt >>= 1;
		}
		return sb.reverse().toString();
	}
}
