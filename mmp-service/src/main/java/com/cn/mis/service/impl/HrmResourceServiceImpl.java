package com.cn.mis.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.dao.HrmResourceMapper;
import com.cn.mis.domain.entity.HrmResource;
import com.cn.mis.service.IHrmResourceService;
import org.springframework.stereotype.Service;


@Service("hrmResourceService")
public class HrmResourceServiceImpl implements IHrmResourceService {
	
	@Resource
	private HrmResourceMapper hrmResourceMapper;

	public int deleteByPrimaryKey(Integer id) {
		return hrmResourceMapper.deleteByPrimaryKey(id);
	}

	public int insert(HrmResource record) {
		return hrmResourceMapper.insert(record);
	}

	public int insertSelective(HrmResource record) {
		return hrmResourceMapper.insertSelective(record);
	}

	public HrmResource selectByPrimaryKey(Integer id) {
		return hrmResourceMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(HrmResource record) {
		return hrmResourceMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(HrmResource record) {
		return hrmResourceMapper.updateByPrimaryKey(record);
	}

	public List<HrmResource> selectAll() {
		return hrmResourceMapper.selectAll();
	}

}
