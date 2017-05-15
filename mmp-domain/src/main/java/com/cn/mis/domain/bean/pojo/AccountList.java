package com.cn.mis.domain.bean.pojo;

import com.cn.mis.domain.entity.mis.Account;

import java.util.List;


public class AccountList {
	private int totalSize;
	private int count;
	private List<Account> records;
	
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<Account> getRecords() {
		return records;
	}
	public void setRecords(List<Account> records) {
		this.records = records;
	}
}
