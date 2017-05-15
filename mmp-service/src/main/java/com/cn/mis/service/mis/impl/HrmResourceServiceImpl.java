package com.cn.mis.service.mis.impl;

import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.dao.mis.HrmResourceMapper;
import com.cn.mis.domain.entity.inface.IFHrmResource;
import com.cn.mis.domain.entity.mis.HrmResource;
import com.cn.mis.domain.entity.mis.HrmResourceWithDepartment;
import com.cn.mis.service.mis.IHrmResourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("hrmResourceService")
@Transactional
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

	@Override
	public List<HrmResourceWithDepartment> selectAllWithDepartment() {
		return hrmResourceMapper.selectAllWithDepartment();
	}

	@Override
	public HrmResource login(HrmResource record) {
		return hrmResourceMapper.login(record);
	}

	@Override
	public int updateBath(List<Integer> list) {
		return hrmResourceMapper.updateBath(list);
	}

	@Override
	public List<IFHrmResource> checkAllWithIm() {
		return hrmResourceMapper.checkAllWithIm();
	}

	@Override
	public List<HrmResource> selectByEmail(HrmResource record) {
		return hrmResourceMapper.selectByEmail(record);
	}

}
