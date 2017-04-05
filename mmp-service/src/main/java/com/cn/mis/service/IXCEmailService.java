package com.cn.mis.service;

import com.cn.mis.domain.entity.XCEmail;

import java.util.List;


public interface IXCEmailService {
	int deleteByPrimaryKey(Integer id);

    XCEmail selectByPrimaryKey(Integer id);
    
    List<XCEmail> selectAll();

    int updateByPrimaryKeySelective(XCEmail record);
}
