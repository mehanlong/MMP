package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.inface.IFHrmDepartment;
import com.cn.mis.domain.entity.mis.HrmDepartment;
import com.cn.mis.domain.entity.mis.HrmDepartmentWithBLOBs;

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

    List<IFHrmDepartment> checkAllWithIm();
}