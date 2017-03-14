package com.cn.mis.dao;

import com.cn.mis.domain.entity.UFCContract;
import com.cn.mis.domain.entity.UFCContractWithBLOBs;

import java.util.List;

public interface UFCContractMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UFCContractWithBLOBs record);

    int insertSelective(UFCContractWithBLOBs record);

    UFCContractWithBLOBs selectByPrimaryKey(Integer id);

    List<UFCContractWithBLOBs> selectAll();

    int updateByPrimaryKeySelective(UFCContractWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UFCContractWithBLOBs record);

    int updateByPrimaryKey(UFCContract record);
}