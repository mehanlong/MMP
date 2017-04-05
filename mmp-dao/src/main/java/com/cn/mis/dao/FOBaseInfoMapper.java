package com.cn.mis.dao;

import com.cn.mis.domain.entity.FOBaseInfo;

public interface FOBaseInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FOBaseInfo record);

    int insertSelective(FOBaseInfo record);

    FOBaseInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FOBaseInfo record);

    int updateByPrimaryKey(FOBaseInfo record);
}