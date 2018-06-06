package com.babytree.commons.protopack.util;

import java.math.BigInteger;

/**
 * 无等号长整型类
 * @author: renmeng
 * @date: 2018/5/31
 */
public class Ulong extends Number implements Comparable<Ulong>
{

	private static final long serialVersionUID = 1L;
	private BigInteger value;
	
	public Ulong(long i)
	{
		if(i < 0)
		{
			String s = Long.toBinaryString(i);
			value = new BigInteger(s, 2);
		}
		else
		{
			value = new BigInteger(i+"");
		}
	}
	
	public Ulong(String l)
	{
		value = new BigInteger(l);
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
		Ulong other = (Ulong)obj;
		if(value != other.value)
			return false;
		return true;
	}
		
	@Override
	public String toString()
	{
		return value.toString();
	}

	@Override
	public int intValue() {
		return this.value.intValue();
	}

	@Override
	public long longValue() {
		return this.value.longValue();
	}

	@Override
	public float floatValue() {
		return this.value.floatValue();
	}

	@Override
	public double doubleValue() {
		return this.value.doubleValue();
	}

	@Override
	public int compareTo(Ulong o) {
		return this.value.divide(o.value).intValue();
	}

}
