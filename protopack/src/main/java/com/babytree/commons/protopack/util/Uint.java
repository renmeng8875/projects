package com.babytree.commons.protopack.util;

import java.math.BigInteger;

/**
 * 无等号整型类
 * @author: renmeng
 * @date: 2018/5/31
 */
public class Uint extends Number implements Comparable<Uint>
{

	private static final long serialVersionUID = 1L;
	private long value;
	
	public Uint(int value)
	{
		if(value < 0)
		{
			String s = Integer.toBinaryString(value);
			this.value = Long.valueOf(s, 2);
		}
		else
		{
			this.value = value;
		}
	}
	
	public Uint(long value)
	{
		this.value = value;
	}
	
	public Uint(String value)
	{
		this.value = Long.valueOf(value);
	}
	
	public static Uint toUInt(int value)
	{
		return new Uint(value);
	}
	
	@Override
	public String toString()
	{
		return Long.toString(value);
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int)(value ^ (value >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Uint other = (Uint)obj;
		if(value != other.value)
			return false;
		return true;
	}

	@Override
	public int compareTo(Uint o) {
		return (int)(this.value - o.longValue());
	}

	@Override
	public int intValue() {
		return  (int)value;
	}

	@Override
	public long longValue() {
		return value;
	}

	@Override
	public float floatValue() {
		return (float)value;
	}

	@Override
	public double doubleValue() {
		return (double)value;
	}
	
	public static void main(String[] args) {
//		String s = Integer.toBinaryString(-5);
//		System.out.println("s:"+s);
//		long value = Long.valueOf(s, 2);
//		System.out.println(value);
		
		String s = Long.toBinaryString(-5);
		System.out.println("s:"+s+",temp:"+new BigInteger("123"));
		BigInteger value = new BigInteger(s, 2);
		System.out.println(value);
	}
}
