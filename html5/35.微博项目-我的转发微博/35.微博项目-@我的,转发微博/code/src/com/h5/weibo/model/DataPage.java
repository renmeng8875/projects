package com.h5.weibo.model;

import java.util.List;

public class DataPage<K> {

	private int pageNo;
	
	private int pageSize;
	
	private int recordCount;
	
	private List<K> records;

	private boolean nextPage = true;
	
	public DataPage(List<K> records,int pageSize,int pageNo,int recordCount) {
		this.records = records;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.recordCount = recordCount;
		
		if((pageNo * pageSize) >= recordCount) {
			nextPage = false;
		}
	}
	
	public boolean hasNextPage() {
		return nextPage;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public List<K> getRecords() {
		return records;
	}
}
