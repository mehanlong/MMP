package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.FM144;
import com.cn.mis.domain.entity.mis.FM144WithBLOBs;

import java.util.List;

public interface FM144Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FM144WithBLOBs record);

    int insertSelective(FM144WithBLOBs record);

    FM144WithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FM144WithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(FM144WithBLOBs record);

    int updateByPrimaryKey(FM144 record);

    List<FM144WithBLOBs> selectByParames(FM144WithBLOBs record);
}