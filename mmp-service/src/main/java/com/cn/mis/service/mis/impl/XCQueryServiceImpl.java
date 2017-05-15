package com.cn.mis.service.mis.impl;

import com.cn.mis.dao.mis.*;
import com.cn.mis.domain.entity.mis.*;
import com.cn.mis.service.mis.IXCQueryServcie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by yuejia on 2017/3/20.
 */
@Service("xcQueryServcie")
@Transactional
public class XCQueryServiceImpl implements IXCQueryServcie {

    @Resource
    FOBaseInfoMapper foBaseInfoMapper;

    @Resource
    FOPassengerMapper foPassengerMapper;

    @Resource
    FORebookInfoMapper foRebookInfoMapper;

    @Resource
    FORefundInfoMapper foRefundInfoMapper;

    @Resource
    FOSettlementBaseInfoMapper foSettlementBaseInfoMapper;

    @Resource
    OFInfoMapper ofInfoMapper;

    @Override
    public int insertSelectiveBaseInfo(FOBaseInfo record) {
        return foBaseInfoMapper.insertSelective(record);
    }

    @Override
    public int insertSelectivePassenger(FOPassenger record) {
        return foPassengerMapper.insertSelective(record);
    }

    @Override
    public int insertSelectiveRebookInfo(FORebookInfo record) {
        return foRebookInfoMapper.insertSelective(record);
    }

    @Override
    public int insertSelectiveRefundInfo(FORefundInfo record) {
        return foRefundInfoMapper.insertSelective(record);
    }

    @Override
    public int insertSelectiveSettlementBaseInfo(FOSettlementBaseInfo record) {
        return foSettlementBaseInfoMapper.insertSelective(record);
    }

    @Override
    public int insertSelectiveInfo(OFInfo record) {
        return ofInfoMapper.insertSelective(record);
    }
}
