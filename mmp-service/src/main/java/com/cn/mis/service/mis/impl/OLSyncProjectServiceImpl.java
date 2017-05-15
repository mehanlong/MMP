package com.cn.mis.service.mis.impl;

import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.dao.mis.OLSyncProjectMapper;
import com.cn.mis.domain.entity.mis.OLSyncProject;
import com.cn.mis.service.mis.IOLSyncProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("oLSyncProjectService")
@Transactional
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
