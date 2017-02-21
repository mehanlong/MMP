package com.cn.mis.dao;

import com.cn.mis.domain.entity.DTThree;

import java.util.ArrayList;

public interface DTThreeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DTThree record);

    int insertSelective(DTThree record);

    DTThree selectByPrimaryKey(Integer id);

    ArrayList<DTThree> selectBySql();

    int updateByPrimaryKeySelective(DTThree record);

    int updateByPrimaryKey(DTThree record);
}