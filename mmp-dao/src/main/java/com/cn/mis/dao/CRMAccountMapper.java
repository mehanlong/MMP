package com.cn.mis.dao;

import com.cn.mis.domain.entity.CRMAccount;

public interface CRMAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CRMAccount record);

    int insertSelective(CRMAccount record);

    CRMAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CRMAccount record);

    int updateByPrimaryKey(CRMAccount record);
}