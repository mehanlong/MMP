package com.cn.mis.service;

import com.cn.mis.domain.entity.CRMAccount;

/**
 * Created by yuejia on 2017/2/14.
 */
public interface ICRMAccountService {
    int deleteByPrimaryKey(Integer id);

    int insert(CRMAccount record);

    int insertSelective(CRMAccount record);

    CRMAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CRMAccount record);

    int updateByPrimaryKey(CRMAccount record);
}
