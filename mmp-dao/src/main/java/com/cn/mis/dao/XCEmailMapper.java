package com.cn.mis.dao;

import com.cn.mis.domain.entity.XCEmail;

import java.util.List;


public interface XCEmailMapper {
    int deleteByPrimaryKey(Integer id);

    XCEmail selectByPrimaryKey(Integer id);
    
    List<XCEmail> selectAll();

    int updateByPrimaryKeySelective(XCEmail record);
}