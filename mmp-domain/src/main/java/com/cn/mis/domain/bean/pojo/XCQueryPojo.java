package com.cn.mis.domain.bean.pojo;

import com.cn.mis.domain.entity.FOBaseInfo;
import com.cn.mis.domain.entity.FOPassenger;
import com.cn.mis.domain.entity.FOSettlementBaseInfo;
import com.cn.mis.domain.entity.OFInfo;
import lombok.Data;

import java.util.List;

/**
 * Created by yuejia on 2017/3/17.
 */
@Data
public class XCQueryPojo {
    private String AccountID;
    private List<XCQueryList> OrderSettlementList;


}
