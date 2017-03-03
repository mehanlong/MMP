package com.cn.mis.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.dao.OperateProjectMapper;
import com.cn.mis.domain.entity.OperateProject;
import com.cn.mis.service.IOperateProjectService;
import org.springframework.stereotype.Service;
@Service("operateProjectService")
public class OperateProjectServiceImpl implements IOperateProjectService {
	@Resource
	private OperateProjectMapper operateProjectMapper;

	@Override
	public List<OperateProject> selectInsertBySql() {
		return operateProjectMapper.selectInsertBySql();
	}

	@Override
	public List<OperateProject> selectUpadteBySql() {
		return operateProjectMapper.selectUpadteBySql();
	}

	@Override
	public int returnWriteBossIdByCommunity(OperateProject record) {
		return operateProjectMapper.returnWriteBossIdByCommunity(record);
	}

	@Override
	public int returnWriteUpdateFlag0ByCommunity(OperateProject record) {
		return operateProjectMapper.returnWriteUpdateFlag0ByCommunity(record);
	}

	@Override
	public List<OperateProject> selectBySql() {
		return operateProjectMapper.selectBySql();
	}

}
