package com.cn.mis.dao;

import com.cn.mis.domain.entity.HrmResource;

import java.util.List;

public interface HrmResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrmResource record);

    int insertSelective(HrmResource record);

    HrmResource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrmResource record);

    int updateByPrimaryKey(HrmResource record);
    
    List<HrmResource> selectAll();

    HrmResource login(HrmResource record);
}