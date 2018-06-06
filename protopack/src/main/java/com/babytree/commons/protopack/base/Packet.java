package com.babytree.commons.protopack.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 协议包，包括包头和包体，并提供基础的打包方法
 * @author: renmeng
 * @date: 2018/5/31
 */
public class Packet implements Marshallable{

	protected Header header;

	protected List<Marshallable> contents = new ArrayList<Marshallable>();

	protected Unpack unpack;

	protected Pack pack = new Pack();

	public Packet(){}

	public Packet(Header header){
		this.header = header;
	}
	public Packet(Header header,Unpack unpack){
		this.header = header;
		this.unpack = unpack;
	}

	public Packet(Header header, Marshallable... contents){
		this(header, Arrays.asList(contents));
	}

	public Packet(Header header, List<Marshallable> contents){
		this.header = header;
		this.contents = contents;
	}

	public Packet(Header header, Unpack unpack, Marshallable... contents){
		this(header, unpack, Arrays.asList(contents));
	}

	public Packet(Header header, Unpack unpack, List<Marshallable> contents){
		this.header = header;
		this.unpack = unpack;
		this.contents = contents;
	}

	public void marshalHeader(Pack pack){
		if (header != null) {
			header.marshal(pack);
		}
	}

	public void unmarshalHeader(Unpack unpack){
		if (header != null) {
			header.unmarshal(unpack);
		}
	}

	public void unmarshalContent(Unpack unpack)
	{
		if(contents!=null && contents.size()!=0){
			int length = contents.size();
			for (int i = 0; i < length; i++) {
				Marshallable mar = contents.get(i);
				unpack.popMarshallable(mar);
			}
		}
	}

	public void marshalContent(Pack pack)
	{
		if(contents!=null && contents.size()!=0){
			for(Marshallable mar : contents)
			{
				pack.putMarshallable(mar);
			}
		}
	}

	public void marshalHeader(){
		marshalHeader(pack);
	}

	public void unmarshalHeader(){
		unmarshalHeader(unpack);
	}

	public void unmarshalContent()
	{
		unmarshalContent(unpack);
	}

	public void marshalContent()
	{
		marshalContent(pack);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public List<Marshallable> getContents() {
		return contents;
	}

	public void setContents(List<Marshallable> contents) {
		this.contents = contents;
	}

	public void putContent(Marshallable content){
		this.contents.add(content);
	}

	public void putContents(List<Marshallable> contents){
		this.contents.addAll(contents);
	}

	public void popContent(Marshallable content,Unpack unpack){
		unpack.popMarshallable(content);
		this.contents.add(content);
	}

	public void popContents(List<Marshallable> contents,Unpack unpack){
		if(contents!=null && contents.size()!=0){
			for(Marshallable mar : contents)
			{
				unpack.popMarshallable(mar);
				this.contents.add(mar);
			}
		}
	}

	public void marshal() {
		marshal(pack);
	}

	public void unmarshal() {
		unmarshal(unpack);
	}

	@Override
	public void marshal(Pack pack) {
		marshalHeader(pack);
		marshalContent(pack);
	}

	@Override
	public void unmarshal(Unpack unpack) {
		unmarshalHeader(unpack);
		unmarshalContent(unpack);
	}

	public Pack getPack() {
		return pack;
	}

	@Override
	public String toString() {
		String str = super.toString()+ " packet header:" + header + " contents:" + contents;
		/*Pack p = new Pack();
		this.marshal(p);
		str += " packet buffer : " + p.toString();*/
		return str;
	}
}
