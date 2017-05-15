package com.cn.mis.dao.im;

import com.cn.mis.domain.entity.im.IMCompany;

public interface IMCompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IMCompany record);

    int insertSelective(IMCompany record);

    IMCompany selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IMCompany record);

    int updateByPrimaryKey(IMCompany record);
}