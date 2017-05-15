package com.cn.mis.domain.entity.mis;

import lombok.Data;

import java.util.Date;

@Data
public class QIPayment {
    private Integer requestid;

    private Integer employee;

    private String contractCode;

    private String contractAdd;

    private Integer processcode;

    private String agreementNumber;

    private Date processtime;

    private String email;

    private String lastname;

    private String requestnamenew;

    private String request_type;

}