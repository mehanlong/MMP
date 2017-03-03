package com.cn.mis.domain.bean.pojo;

import java.util.ArrayList;

public class UserList {
	private int totleSize;
	private int count;
	private ArrayList<UserListDetail> records;
	public int getTotleSize() {
		return totleSize;
	}
	public void setTotleSize(int totleSize) {
		this.totleSize = totleSize;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public ArrayList<UserListDetail> getRecords() {
		return records;
	}
	public void setRecords(ArrayList<UserListDetail> records) {
		this.records = records;
	}
	
	
}
