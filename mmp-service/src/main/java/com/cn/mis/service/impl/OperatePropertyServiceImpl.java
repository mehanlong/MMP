package com.cn.mis.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.dao.OperatePropertyMapper;
import com.cn.mis.domain.entity.OperateProperty;
import com.cn.mis.service.IOperatePropertyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("operatePropertyService")
@Transactional
public class OperatePropertyServiceImpl implements IOperatePropertyService {
	@Resource
	private OperatePropertyMapper operatePropertyMapper;

	@Override
	public List<OperateProperty> selectInsertBySql() {
		return operatePropertyMapper.selectInsertBySql();
	}

	@Override
	public int returnWriteBossIdByAccountName(OperateProperty record) {
		return operatePropertyMapper.returnWriteBossIdByAccountName(record);
	}

	@Override
	public List<OperateProperty> selectBySql() {
		return operatePropertyMapper.selectBySql();
	}
	

}
