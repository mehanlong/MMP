package com.cn.mis.dao;

import com.cn.mis.domain.entity.FORebookInfo;

public interface FORebookInfoMapper {
    int deleteByPrimaryKey(Integer seqence);

    int insert(FORebookInfo record);

    int insertSelective(FORebookInfo record);

    FORebookInfo selectByPrimaryKey(Integer seqence);

    int updateByPrimaryKeySelective(FORebookInfo record);

    int updateByPrimaryKey(FORebookInfo record);
}