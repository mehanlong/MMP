package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.UFPermana;
import com.cn.mis.domain.entity.mis.UFPermanaWithBLOBs;

import java.util.List;

public interface UFPermanaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UFPermanaWithBLOBs record);

    int insertSelective(UFPermanaWithBLOBs record);

    UFPermanaWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UFPermanaWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UFPermanaWithBLOBs record);

    int updateByPrimaryKey(UFPermana record);

    List<UFPermanaWithBLOBs> selectByParames(UFPermanaWithBLOBs record);
}