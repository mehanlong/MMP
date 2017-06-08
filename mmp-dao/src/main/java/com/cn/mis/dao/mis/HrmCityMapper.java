package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.HrmCity;

import java.util.List;

public interface HrmCityMapper {
    int insert(HrmCity record);

    int insertSelective(HrmCity record);

    List<HrmCity> selectByName(String name);
}