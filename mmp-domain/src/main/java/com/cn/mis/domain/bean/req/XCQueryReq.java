package com.cn.mis.domain.bean.req;

import corp.openapicalls.contract.Authentification;
import lombok.Data;

/**
 * Created by yuejia on 2017/3/17.
 */
@Data
public class XCQueryReq {
    private XCAuth Auth;
    private String AccountID;
    private String DateFrom;
    private String DateTo;
}
