package com.cn.mis.dao;

import com.cn.mis.domain.entity.OFInfo;

public interface OFInfoMapper {
    int deleteByPrimaryKey(Integer seqence);

    int insert(OFInfo record);

    int insertSelective(OFInfo record);

    OFInfo selectByPrimaryKey(Integer seqence);

    int updateByPrimaryKeySelective(OFInfo record);

    int updateByPrimaryKey(OFInfo record);
}