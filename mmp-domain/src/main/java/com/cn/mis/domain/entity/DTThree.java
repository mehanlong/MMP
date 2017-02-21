package com.cn.mis.domain.entity;

import java.math.BigDecimal;

public class DTThree {
    private Integer id;

    private Integer mainid;

    private String name;

    private String abstract_1;

    private String costDate;

    private String amount;

    private Integer receiptType;

    private BigDecimal payAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMainid() {
        return mainid;
    }

    public void setMainid(Integer mainid) {
        this.mainid = mainid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }



    public String getCostDate() {
        return costDate;
    }

    public void setCostDate(String costDate) {
        this.costDate = costDate == null ? null : costDate.trim();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount == null ? null : amount.trim();
    }

    public Integer getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(Integer receiptType) {
        this.receiptType = receiptType;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getAbstract_1() {
        return abstract_1;
    }

    public void setAbstract_1(String abstract_1) {
        this.abstract_1 = abstract_1;
    }
}