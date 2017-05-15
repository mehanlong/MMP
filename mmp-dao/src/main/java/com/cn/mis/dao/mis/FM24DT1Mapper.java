package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.FM24DT1;

import java.util.List;

public interface FM24DT1Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FM24DT1 record);

    int insertSelective(FM24DT1 record);

    FM24DT1 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FM24DT1 record);

    int updateByPrimaryKeyWithBLOBs(FM24DT1 record);

    int updateByPrimaryKey(FM24DT1 record);

    List<FM24DT1> selectAll();
}