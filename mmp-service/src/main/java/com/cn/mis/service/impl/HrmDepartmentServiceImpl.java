package com.cn.mis.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.dao.HrmDepartmentMapper;
import com.cn.mis.domain.entity.HrmDepartment;
import com.cn.mis.domain.entity.HrmDepartmentWithBLOBs;
import com.cn.mis.service.IHrmDepartmentService;
import org.springframework.stereotype.Service;
@Service("hrmDepartmentService")
public class HrmDepartmentServiceImpl implements IHrmDepartmentService {
	@Resource
	private HrmDepartmentMapper hrmDepartmentMapper;

	public int deleteByPrimaryKey(Integer id) {
		return hrmDepartmentMapper.deleteByPrimaryKey(id);
	}

	public int insert(HrmDepartmentWithBLOBs record) {
		return hrmDepartmentMapper.insert(record);
	}

	public int insertSelective(HrmDepartmentWithBLOBs record) {
		return hrmDepartmentMapper.insertSelective(record);
	}

	public HrmDepartmentWithBLOBs selectByPrimaryKey(Integer id) {
		return hrmDepartmentMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(HrmDepartmentWithBLOBs record) {
		return hrmDepartmentMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKeyWithBLOBs(HrmDepartmentWithBLOBs record) {
		return hrmDepartmentMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	public int updateByPrimaryKey(HrmDepartment record) {
		return hrmDepartmentMapper.updateByPrimaryKey(record);
	}

	public List<HrmDepartmentWithBLOBs> selectAll() {
		return hrmDepartmentMapper.selectAll();
	}
}