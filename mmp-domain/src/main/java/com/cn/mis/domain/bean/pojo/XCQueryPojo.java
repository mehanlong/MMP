package com.cn.mis.domain.bean.pojo;

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
