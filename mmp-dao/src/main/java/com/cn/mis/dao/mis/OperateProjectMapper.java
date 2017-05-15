package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.OperateProject;

import java.util.List;


public interface OperateProjectMapper {
	
	List<OperateProject> selectInsertBySql();
	
	List<OperateProject> selectUpadteBySql();
	
	List<OperateProject> selectBySql();
	
	int returnWriteBossIdByCommunity(OperateProject record);
	
	int returnWriteUpdateFlag0ByCommunity(OperateProject record);
}
