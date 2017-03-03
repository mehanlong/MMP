package com.cn.mis.service;

import com.cn.mis.domain.entity.OperateProject;

import java.util.List;


public interface IOperateProjectService {
	List<OperateProject> selectInsertBySql();
	
	List<OperateProject> selectUpadteBySql();
	
	List<OperateProject> selectBySql();
	
	int returnWriteBossIdByCommunity(OperateProject record);
	
	int returnWriteUpdateFlag0ByCommunity(OperateProject record);
}
