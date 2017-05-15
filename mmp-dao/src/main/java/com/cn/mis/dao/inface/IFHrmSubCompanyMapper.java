package com.cn.mis.dao.inface;

import com.cn.mis.domain.entity.inface.IFHrmSubCompany;

public interface IFHrmSubCompanyMapper {
    int insert(IFHrmSubCompany record);

    int insertSelective(IFHrmSubCompany record);
}