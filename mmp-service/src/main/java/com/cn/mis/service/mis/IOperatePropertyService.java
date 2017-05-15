package com.cn.mis.service.mis;

import com.cn.mis.domain.entity.mis.OperateProperty;

import java.util.List;


public interface IOperatePropertyService {
	List<OperateProperty> selectInsertBySql();
	
	List<OperateProperty> selectBySql();
	
	int returnWriteBossIdByAccountName(OperateProperty record);
}
