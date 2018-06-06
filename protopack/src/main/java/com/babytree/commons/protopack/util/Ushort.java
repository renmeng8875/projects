package com.babytree.commons.protopack.util;

/**
 * 无等号整型类
 * @author: renmeng
 * @date: 2018/5/31
 */
public class Ushort extends Number implements Comparable<Ushort>
{

	private static final long serialVersionUID = 1L;
	private int value;
	
	public Ushort(short value)
	{
		if(value < 0)
		{
            int h = value >>> 8 & 0xff;
            String s = Integer.toBinaryString((h << 8 | value) & 0xffff);
			this.value = Integer.valueOf(s, 2);
		}
		else
		{
			this.value = value;
		}
	}
	
	public Ushort(int value)
	{
		this.value = value;
	}
	
	public Ushort(String value)
	{
		this.value = Integer.valueOf(value);
	}
	
	public static Ushort toUInt(int value)
	{
		return new Ushort(value);
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(value);
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
		if(this == obj){
			return true;}
		if(obj == null){
			return false;}
		if(getClass() != obj.getClass()){
			return false;}
		Ushort other = (Ushort)obj;
		if(value != other.value)
			return false;
		return true;
	}

	@Override
	public int compareTo(Ushort o) {
		return (int)(this.value - o.intValue());
	}

	@Override
	public int intValue() {
		return  value;
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
	
	@Override
	public short shortValue() {
        return (short)value;
    }
}
