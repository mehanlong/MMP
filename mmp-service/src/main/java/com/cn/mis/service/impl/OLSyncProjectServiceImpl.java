package com.cn.mis.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.dao.OLSyncProjectMapper;
import com.cn.mis.domain.entity.OLSyncProject;
import com.cn.mis.service.IOLSyncProjectService;
import org.springframework.stereotype.Service;


@Service("oLSyncProjectService")
public class OLSyncProjectServiceImpl implements IOLSyncProjectService {
	@Resource
	private OLSyncProjectMapper oLSyncProjectMapper;
	
	@Override
	public List<OLSyncProject> selectByIds(List<Long> list) {
		return oLSyncProjectMapper.selectByIds(list);
	}

	@Override
	public int insertBatch(List<OLSyncProject> list) {
		return oLSyncProjectMapper.insertBatch(list);
	}

	@Override
	public int updateBatch(List<OLSyncProject> list) {
		return oLSyncProjectMapper.updateBatch(list);
	}

	@Override
	public int deleteBatch(List<Integer> list) {
		return oLSyncProjectMapper.deleteBatch(list);
	}
	

}
