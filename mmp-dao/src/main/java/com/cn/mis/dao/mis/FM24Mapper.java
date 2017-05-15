package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.FM24;
import com.cn.mis.domain.entity.mis.FM24WithBLOBs;

import java.util.List;

public interface FM24Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FM24WithBLOBs record);

    int insertSelective(FM24WithBLOBs record);

    FM24WithBLOBs selectByPrimaryKey(Integer id);

    List<FM24WithBLOBs> selectAll();

    int updateByPrimaryKeySelective(FM24WithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(FM24WithBLOBs record);

    int updateByPrimaryKey(FM24 record);
}