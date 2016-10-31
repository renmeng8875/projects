package com.h5.weibo.model;

public class Pair<K,V> {

	private K first;
	
	private V second;

	public Pair(K first,V second) {
		this.first = first;
		this.second = second;
	}
	
	public K getFirst() {
		return first;
	}

	public V getSecond() {
		return second;
	}
	
}
