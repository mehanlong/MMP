package com.cn.mis.service;

import com.cn.mis.domain.entity.OperateProperty;

import java.util.List;


public interface IOperatePropertyService {
	List<OperateProperty> selectInsertBySql();
	
	List<OperateProperty> selectBySql();
	
	int returnWriteBossIdByAccountName(OperateProperty record);
}
