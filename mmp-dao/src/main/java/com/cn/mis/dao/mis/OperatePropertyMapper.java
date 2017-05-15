package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.OperateProperty;

import java.util.List;


public interface OperatePropertyMapper {
	
	List<OperateProperty> selectInsertBySql();
	
	List<OperateProperty> selectBySql();
	
	int returnWriteBossIdByAccountName(OperateProperty record);
}
