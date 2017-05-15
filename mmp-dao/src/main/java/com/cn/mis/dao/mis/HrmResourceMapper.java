package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.inface.IFHrmResource;
import com.cn.mis.domain.entity.mis.HrmResource;
import com.cn.mis.domain.entity.mis.HrmResourceWithDepartment;

import java.util.List;

public interface HrmResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrmResource record);

    int insertSelective(HrmResource record);

    HrmResource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrmResource record);

    int updateByPrimaryKey(HrmResource record);
    
    List<HrmResource> selectAll();

    List<HrmResourceWithDepartment> selectAllWithDepartment();

    HrmResource login(HrmResource record);

    int updateBath(List<Integer> list);

    List<IFHrmResource> checkAllWithIm();

    List<HrmResource> selectByEmail(HrmResource record);
}