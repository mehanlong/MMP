package com.cn.mis.dao;

import com.cn.mis.domain.entity.HrmDepartment;
import com.cn.mis.domain.entity.HrmDepartmentWithBLOBs;

import java.util.List;


public interface HrmDepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrmDepartmentWithBLOBs record);

    int insertSelective(HrmDepartmentWithBLOBs record);

    HrmDepartmentWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrmDepartmentWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(HrmDepartmentWithBLOBs record);

    int updateByPrimaryKey(HrmDepartment record);
    
    List<HrmDepartmentWithBLOBs> selectAll();
}