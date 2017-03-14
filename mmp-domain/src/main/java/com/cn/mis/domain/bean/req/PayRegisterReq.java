package com.cn.mis.domain.bean.req;

import lombok.Data;

/**
 * Created by yuejia on 2017/3/9.
 */
@Data
public class PayRegisterReq {
    private Integer status;
    private String code;
    private String requestname;
    private String lastname;
    private String departmentname;
    private String startdate;
    private String enddate;

    private String payment_code;

    private String account;
    private String bank;
    private String vendor;
}
