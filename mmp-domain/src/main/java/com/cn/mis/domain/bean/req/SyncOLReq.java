package com.cn.mis.domain.bean.req;

import com.cn.mis.domain.entity.OLSyncProject;

import java.util.List;



public class SyncOLReq {
	private List<OLSyncProject> records;

	public List<OLSyncProject> getRecords() {
		return records;
	}

	public void setRecords(List<OLSyncProject> records) {
		this.records = records;
	}
	
	
}
