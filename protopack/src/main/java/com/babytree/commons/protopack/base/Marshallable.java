package com.babytree.commons.protopack.base;


/**
 * @describe: 打包解包接口
 * @author: renmeng
 * @date: 2018/5/31
 **/
public interface Marshallable
{
	public abstract void marshal(Pack pack);
	
	public abstract void unmarshal(Unpack unpack);
}