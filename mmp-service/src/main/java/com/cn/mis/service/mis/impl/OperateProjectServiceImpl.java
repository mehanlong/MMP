package com.cn.mis.service.mis.impl;

import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.dao.mis.OperateProjectMapper;
import com.cn.mis.domain.entity.mis.OperateProject;
import com.cn.mis.service.mis.IOperateProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("operateProjectService")
@Transactional
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
