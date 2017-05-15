package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.HrmSubCompany;

public interface HrmSubCompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrmSubCompany record);

    int insertSelective(HrmSubCompany record);

    HrmSubCompany selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrmSubCompany record);

    int updateByPrimaryKey(HrmSubCompany record);
}