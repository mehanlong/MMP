package com.cn.mis.service.mis;

import com.cn.mis.domain.entity.mis.XCEmail;

import java.util.List;


public interface IXCEmailService {
	int deleteByPrimaryKey(Integer id);

    XCEmail selectByPrimaryKey(Integer id);
    
    List<XCEmail> selectAll();

    int updateByPrimaryKeySelective(XCEmail record);
}
