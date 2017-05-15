package com.cn.mis.domain.entity.mis;

public class FM146 {
    private Integer id;

    private Integer requestid;

    private Integer department;

    private String code;

    private Integer employee;

    private String requestDate;

    private String title;

    private String performance;

    private Integer performanceScore;

    private String performanceCode;

    private Integer evaluationObject;

    private String alreadyConfirmed;

    private Integer directSuperior;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequestid() {
        return requestid;
    }

    public void setRequestid(Integer requestid) {
        this.requestid = requestid;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate == null ? null : requestDate.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance == null ? null : performance.trim();
    }

    public Integer getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(Integer performanceScore) {
        this.performanceScore = performanceScore;
    }

    public String getPerformanceCode() {
        return performanceCode;
    }

    public void setPerformanceCode(String performanceCode) {
        this.performanceCode = performanceCode == null ? null : performanceCode.trim();
    }

    public Integer getEvaluationObject() {
        return evaluationObject;
    }

    public void setEvaluationObject(Integer evaluationObject) {
        this.evaluationObject = evaluationObject;
    }

    public String getAlreadyConfirmed() {
        return alreadyConfirmed;
    }

    public void setAlreadyConfirmed(String alreadyConfirmed) {
        this.alreadyConfirmed = alreadyConfirmed == null ? null : alreadyConfirmed.trim();
    }

    public Integer getDirectSuperior() {
        return directSuperior;
    }

    public void setDirectSuperior(Integer directSuperior) {
        this.directSuperior = directSuperior;
    }
}