package com.cn.mis.service.impl;

import javax.annotation.Resource;

import com.cn.mis.dao.UFCommunityMapper;
import com.cn.mis.domain.entity.UFCommunity;
import com.cn.mis.domain.entity.UFCommunityWithBLOBs;
import com.cn.mis.service.IUFCommunityService;
import org.springframework.stereotype.Service;

@Service("uFCommunityService")
public class UFCommunityServiceImpl implements IUFCommunityService {
	@Resource
	private UFCommunityMapper ufCommunityMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return ufCommunityMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UFCommunityWithBLOBs record) {
		return ufCommunityMapper.insert(record);
	}

	@Override
	public int insertSelective(UFCommunityWithBLOBs record) {
		return ufCommunityMapper.insertSelective(record);
	}

	@Override
	public UFCommunityWithBLOBs selectByPrimaryKey(Integer id) {
		return ufCommunityMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UFCommunityWithBLOBs record) {
		return ufCommunityMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(UFCommunityWithBLOBs record) {
		return ufCommunityMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(UFCommunity record) {
		return ufCommunityMapper.updateByPrimaryKey(record);
	}

	@Override
	public UFCommunityWithBLOBs selectByBossId(Long bossId) {
		return ufCommunityMapper.selectByBossId(bossId);
	}

}
