package com.cn.mis.domain.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by yuejia on 2017/3/9.
 *
 */
@Data
public class PayRegister {
    //日常报销
    private Integer id;
    private String requestname;
    private String employee;
    private Integer department;
    private String departmentname;
    private String request_date;
    private String lastname;
    private Integer requestId;
    private String code;
    private Integer status;
    private String payer_a;
    private String vendor;
    private String payer_bank;
    private String payer_account;
    private String account;
    private String pay_date;
    private BigDecimal pay_amount;
    //日常合同
    private BigDecimal amount;
    private String payment_code;
    //领借款
    private BigDecimal loan_amount;


    //选择银行
    private Integer bank_id;
    //private String account;
    private String bank;
//    private String vendor;
    private Integer corp_id;

}
