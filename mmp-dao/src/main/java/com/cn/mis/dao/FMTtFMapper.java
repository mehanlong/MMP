package com.cn.mis.dao;

import com.cn.mis.domain.entity.FMTtF;
import com.cn.mis.domain.entity.FMTtFWithBLOBs;

import java.util.ArrayList;

public interface FMTtFMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FMTtFWithBLOBs record);

    int insertSelective(FMTtFWithBLOBs record);

    FMTtFWithBLOBs selectByPrimaryKey(Integer id);

    ArrayList<FMTtFWithBLOBs> selectBySql();

    int updateByPrimaryKeySelective(FMTtFWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(FMTtFWithBLOBs record);

    int updateByPrimaryKey(FMTtF record);
}