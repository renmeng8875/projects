package com.babytree.commons.protopack.marshal;


import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;

public class StringMarshal implements Marshallable
{
	public String data;
	
	public StringMarshal(){
		
	}
	
	public StringMarshal(String data) {
		this.data = data;
	}
	
	@Override
	public void marshal(Pack pack)
	{
		pack.putVarstr(data);	
	}

	@Override
	public void unmarshal(Unpack unpack)
	{
		data = unpack.popVarstr();
	}
	
	@Override
	public String toString() {
		return data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof StringMarshal)) {
			return false;
		}
		StringMarshal other = (StringMarshal) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		return true;
	}
}

