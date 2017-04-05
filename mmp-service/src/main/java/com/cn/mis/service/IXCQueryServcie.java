package com.cn.mis.service;

import com.cn.mis.domain.entity.*;

/**
 * Created by yuejia on 2017/3/20.
 */
public interface IXCQueryServcie {
    int insertSelectiveBaseInfo(FOBaseInfo record);

    int insertSelectivePassenger(FOPassenger record);

    int insertSelectiveRebookInfo(FORebookInfo record);

    int insertSelectiveRefundInfo(FORefundInfo record);

    int insertSelectiveSettlementBaseInfo(FOSettlementBaseInfo record);

    int insertSelectiveInfo(OFInfo record);
}
