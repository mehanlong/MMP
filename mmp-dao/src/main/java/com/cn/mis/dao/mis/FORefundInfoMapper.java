package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.FORefundInfo;

public interface FORefundInfoMapper {
    int deleteByPrimaryKey(Integer seqence);

    int insert(FORefundInfo record);

    int insertSelective(FORefundInfo record);

    FORefundInfo selectByPrimaryKey(Integer seqence);

    int updateByPrimaryKeySelective(FORefundInfo record);

    int updateByPrimaryKey(FORefundInfo record);
}