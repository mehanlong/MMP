package com.cn.mis.dao;

import com.cn.mis.domain.entity.FOPassenger;

public interface FOPassengerMapper {
    int deleteByPrimaryKey(Integer seqence);

    int insert(FOPassenger record);

    int insertSelective(FOPassenger record);

    FOPassenger selectByPrimaryKey(Integer seqence);

    int updateByPrimaryKeySelective(FOPassenger record);

    int updateByPrimaryKey(FOPassenger record);
}