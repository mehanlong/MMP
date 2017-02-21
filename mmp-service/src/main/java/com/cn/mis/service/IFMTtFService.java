package com.cn.mis.service;

import com.cn.mis.domain.entity.FMTtF;
import com.cn.mis.domain.entity.FMTtFWithBLOBs;

import java.util.ArrayList;

/**
 * Created by yuejia on 2017/2/21.
 */
public interface IFMTtFService {
    int deleteByPrimaryKey(Integer id);

    int insert(FMTtFWithBLOBs record);

    int insertSelective(FMTtFWithBLOBs record);

    FMTtFWithBLOBs selectByPrimaryKey(Integer id);

    ArrayList<FMTtFWithBLOBs> selectBySql();

    int updateByPrimaryKeySelective(FMTtFWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(FMTtFWithBLOBs record);

    int updateByPrimaryKey(FMTtF record);
}
