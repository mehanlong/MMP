package com.cn.mis.dao;

import com.cn.mis.domain.entity.XCEmail;

import java.util.List;


public interface XCEmailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XCEmail record);

    int insertSelective(XCEmail record);

    XCEmail selectByPrimaryKey(Integer id);
    
    List<XCEmail> selectAll();

    int updateByPrimaryKeySelective(XCEmail record);

    int updateByPrimaryKey(XCEmail record);
}