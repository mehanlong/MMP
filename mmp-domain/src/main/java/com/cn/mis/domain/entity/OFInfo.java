package com.cn.mis.domain.entity;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class OFInfo {
    private Integer RecordID;

    private Integer Sequence;

    private String Reason;

    private String ReasonDesc;

    private String LowFlight;

    private String LowClass;

    private String LowDTime;

    private BigDecimal LowRate;

    private BigDecimal LowPrice;

    private String PreBookReason;

    private String PreBookReasonDesc;

    private String StraightType;

    private String TakeOffTime;

    private String ArrivalTime;

    private String DCityName;

    private String ACityName;

    private String DPortName;

    private String APortName;

    private String Flight;

    private String DClass;

    private String ClassName;

    private String SubClass;

    private String BfReturn;

    private String CustomerID;

    private BigDecimal StandardPrice;

    private BigDecimal PriceRate;


}