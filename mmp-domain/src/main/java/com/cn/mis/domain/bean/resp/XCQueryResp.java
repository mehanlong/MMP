package com.cn.mis.domain.bean.resp;

import com.cn.mis.domain.bean.pojo.XCQueryPojo;
import com.cn.mis.domain.bean.pojo.XCStatus;
import com.cn.mis.domain.bean.req.XCQueryReq;
import lombok.Data;

import java.util.List;

/**
 * Created by yuejia on 2017/3/17.
 */
@Data
public class XCQueryResp {
    private List<XCQueryPojo> FlightOrderAccountSettlementList;
    private XCStatus Status;

}
