package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.FOSettlementBaseInfo;

public interface FOSettlementBaseInfoMapper {
    int deleteByPrimaryKey(Integer recordid);

    int insert(FOSettlementBaseInfo record);

    int insertSelective(FOSettlementBaseInfo record);

    FOSettlementBaseInfo selectByPrimaryKey(Integer recordid);

    int updateByPrimaryKeySelective(FOSettlementBaseInfo record);

    int updateByPrimaryKey(FOSettlementBaseInfo record);
}