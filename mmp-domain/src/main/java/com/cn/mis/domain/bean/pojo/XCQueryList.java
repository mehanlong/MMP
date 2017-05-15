package com.cn.mis.domain.bean.pojo;

import com.cn.mis.domain.entity.mis.*;
import lombok.Data;

/**
 * Created by yuejia on 2017/3/20.
 */
@Data
public class XCQueryList {
    private FOSettlementBaseInfo OrderSettlementBaseInfo;
    private FOBaseInfo OrderBaseInfo;
    private FOPassenger OrderPassengerInfo;
    private OFInfo OrderFlightInfo;

    private FORebookInfo OrderRebookInfo;
    private FORefundInfo OrderRefundInfo;
}
