package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.FM146;
import com.cn.mis.domain.entity.mis.FM146WithBLOBs;

import java.util.List;

public interface FM146Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FM146WithBLOBs record);

    int insertSelective(FM146WithBLOBs record);

    FM146WithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FM146WithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(FM146WithBLOBs record);

    int updateByPrimaryKey(FM146 record);

    List<FM146WithBLOBs> selectByParames(FM146WithBLOBs record);
}