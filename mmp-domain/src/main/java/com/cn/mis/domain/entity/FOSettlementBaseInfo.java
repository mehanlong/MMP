package com.cn.mis.domain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class FOSettlementBaseInfo {
    private Integer RecordID;

    private Long OrderID;

    private Integer Sequence;

    private Date CreateTime;

    private Integer AccountID;

    private String CorpID;

    private String AccCheckBatchNo;

    private String AccBalanceBatchNo;

    private String Remark;

    private BigDecimal UnDeterminedAmount;

    private BigDecimal Price;

    private BigDecimal Tax;

    private BigDecimal OilFee;

    private BigDecimal SendTicketFee;

    private BigDecimal InsuranceFee;

    private BigDecimal ServiceFee;

    private BigDecimal Coupon;

    private BigDecimal RefundServiceFee;

    private BigDecimal Refund;

    private BigDecimal Amount;

    private BigDecimal RebookQueryFee;

    private BigDecimal BaseServiceFee;

    private BigDecimal UnWorkTimeServiceFee;

    private BigDecimal VIPServiceFee;

    private BigDecimal BindServiceFee;

    private BigDecimal Subsidy;

    private BigDecimal ReBookingServiceFee;

    private String OrderDetailType;

}