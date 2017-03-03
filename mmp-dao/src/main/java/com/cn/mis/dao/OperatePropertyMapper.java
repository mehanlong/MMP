package com.cn.mis.dao;

import com.cn.mis.domain.entity.OperateProperty;

import java.util.List;


public interface OperatePropertyMapper {
	
	List<OperateProperty> selectInsertBySql();
	
	List<OperateProperty> selectBySql();
	
	int returnWriteBossIdByAccountName(OperateProperty record);
}
