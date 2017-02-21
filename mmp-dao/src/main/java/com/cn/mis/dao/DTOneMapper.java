package com.cn.mis.dao;

import com.cn.mis.domain.entity.DTOne;
import com.cn.mis.domain.entity.DTOneWithBLOBs;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import java.util.ArrayList;

public interface DTOneMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DTOneWithBLOBs record);

    int insertSelective(DTOneWithBLOBs record);

    DTOneWithBLOBs selectByPrimaryKey(Integer id);

    ArrayList<DTOneWithBLOBs> selectAll();

    int updateByPrimaryKeySelective(DTOneWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(DTOneWithBLOBs record);

    int updateByPrimaryKey(DTOne record);
}