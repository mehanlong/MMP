package com.cn.mis.domain.entity.mis;

import java.math.BigDecimal;

public class UFCContract {
    private Integer id;

    private Integer mainid;

    private Integer request;

    private Integer contractCategory;

    private String addContractDate;

    private String contractAmount;

    private Integer budgetAccount;

    private String mainContract;

    private String title;

    private Integer contractStatus;

    private BigDecimal sumApplyAmount;

    private BigDecimal contractAmount1;

    private String agreementNumber;

    private Integer supplementalProtocolVersion;

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

    public Integer getRequest() {
        return request;
    }

    public void setRequest(Integer request) {
        this.request = request;
    }

    public Integer getContractCategory() {
        return contractCategory;
    }

    public void setContractCategory(Integer contractCategory) {
        this.contractCategory = contractCategory;
    }

    public String getAddContractDate() {
        return addContractDate;
    }

    public void setAddContractDate(String addContractDate) {
        this.addContractDate = addContractDate == null ? null : addContractDate.trim();
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount == null ? null : contractAmount.trim();
    }

    public Integer getBudgetAccount() {
        return budgetAccount;
    }

    public void setBudgetAccount(Integer budgetAccount) {
        this.budgetAccount = budgetAccount;
    }

    public String getMainContract() {
        return mainContract;
    }

    public void setMainContract(String mainContract) {
        this.mainContract = mainContract == null ? null : mainContract.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public BigDecimal getSumApplyAmount() {
        return sumApplyAmount;
    }

    public void setSumApplyAmount(BigDecimal sumApplyAmount) {
        this.sumApplyAmount = sumApplyAmount;
    }

    public BigDecimal getContractAmount1() {
        return contractAmount1;
    }

    public void setContractAmount1(BigDecimal contractAmount1) {
        this.contractAmount1 = contractAmount1;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber == null ? null : agreementNumber.trim();
    }

    public Integer getSupplementalProtocolVersion() {
        return supplementalProtocolVersion;
    }

    public void setSupplementalProtocolVersion(Integer supplementalProtocolVersion) {
        this.supplementalProtocolVersion = supplementalProtocolVersion;
    }
}