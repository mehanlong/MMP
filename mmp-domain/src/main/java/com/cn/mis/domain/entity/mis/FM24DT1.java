package com.cn.mis.domain.entity.mis;

import java.math.BigDecimal;

public class FM24DT1 {
    private Integer id;

    private Integer mainid;

    private String community;

    private String householdNum;

    private String yearServiceAmount;

    private String planOnlineDate;

    private Integer unitAccessFlag;

    private Integer operationFlag;

    private String property;

    private String serviceAmount;

    private String communityAdd;

    private BigDecimal managmentArea;

    private String onlineHouseholds;

    private Integer section;

    private Integer propertyType;

    private Integer city1;

    private Integer householdNumNew;

    private Integer onlineHouseholdsNew;

    private BigDecimal yearServiceAmountNew;

    private BigDecimal propertyNew;

    private BigDecimal serviceAmountNew;

    private String comment;

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

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community == null ? null : community.trim();
    }

    public String getHouseholdNum() {
        return householdNum;
    }

    public void setHouseholdNum(String householdNum) {
        this.householdNum = householdNum == null ? null : householdNum.trim();
    }

    public String getYearServiceAmount() {
        return yearServiceAmount;
    }

    public void setYearServiceAmount(String yearServiceAmount) {
        this.yearServiceAmount = yearServiceAmount == null ? null : yearServiceAmount.trim();
    }

    public String getPlanOnlineDate() {
        return planOnlineDate;
    }

    public void setPlanOnlineDate(String planOnlineDate) {
        this.planOnlineDate = planOnlineDate == null ? null : planOnlineDate.trim();
    }

    public Integer getUnitAccessFlag() {
        return unitAccessFlag;
    }

    public void setUnitAccessFlag(Integer unitAccessFlag) {
        this.unitAccessFlag = unitAccessFlag;
    }

    public Integer getOperationFlag() {
        return operationFlag;
    }

    public void setOperationFlag(Integer operationFlag) {
        this.operationFlag = operationFlag;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property == null ? null : property.trim();
    }

    public String getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(String serviceAmount) {
        this.serviceAmount = serviceAmount == null ? null : serviceAmount.trim();
    }

    public String getCommunityAdd() {
        return communityAdd;
    }

    public void setCommunityAdd(String communityAdd) {
        this.communityAdd = communityAdd == null ? null : communityAdd.trim();
    }

    public BigDecimal getManagmentArea() {
        return managmentArea;
    }

    public void setManagmentArea(BigDecimal managmentArea) {
        this.managmentArea = managmentArea;
    }

    public String getOnlineHouseholds() {
        return onlineHouseholds;
    }

    public void setOnlineHouseholds(String onlineHouseholds) {
        this.onlineHouseholds = onlineHouseholds == null ? null : onlineHouseholds.trim();
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Integer getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Integer propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getCity1() {
        return city1;
    }

    public void setCity1(Integer city1) {
        this.city1 = city1;
    }

    public Integer getHouseholdNumNew() {
        return householdNumNew;
    }

    public void setHouseholdNumNew(Integer householdNumNew) {
        this.householdNumNew = householdNumNew;
    }

    public Integer getOnlineHouseholdsNew() {
        return onlineHouseholdsNew;
    }

    public void setOnlineHouseholdsNew(Integer onlineHouseholdsNew) {
        this.onlineHouseholdsNew = onlineHouseholdsNew;
    }

    public BigDecimal getYearServiceAmountNew() {
        return yearServiceAmountNew;
    }

    public void setYearServiceAmountNew(BigDecimal yearServiceAmountNew) {
        this.yearServiceAmountNew = yearServiceAmountNew;
    }

    public BigDecimal getPropertyNew() {
        return propertyNew;
    }

    public void setPropertyNew(BigDecimal propertyNew) {
        this.propertyNew = propertyNew;
    }

    public BigDecimal getServiceAmountNew() {
        return serviceAmountNew;
    }

    public void setServiceAmountNew(BigDecimal serviceAmountNew) {
        this.serviceAmountNew = serviceAmountNew;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}