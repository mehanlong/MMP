package com.cn.mis.domain.entity.mis;

public class UFPermana {
    private Integer id;

    private Integer requestid;

    private Integer evaluationObject;

    private Integer performanceScore;

    private String performance;

    private Integer formmodeid;

    private Integer modedatacreater;

    private Integer modedatacreatertype;

    private String modedatacreatedate;

    private String modedatacreatetime;

    private Integer department;

    private String code;

    private String modeuuid;

    private Integer assessor;

    private String evaluationDate;

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

    public Integer getEvaluationObject() {
        return evaluationObject;
    }

    public void setEvaluationObject(Integer evaluationObject) {
        this.evaluationObject = evaluationObject;
    }

    public Integer getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(Integer performanceScore) {
        this.performanceScore = performanceScore;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance == null ? null : performance.trim();
    }

    public Integer getFormmodeid() {
        return formmodeid;
    }

    public void setFormmodeid(Integer formmodeid) {
        this.formmodeid = formmodeid;
    }

    public Integer getModedatacreater() {
        return modedatacreater;
    }

    public void setModedatacreater(Integer modedatacreater) {
        this.modedatacreater = modedatacreater;
    }

    public Integer getModedatacreatertype() {
        return modedatacreatertype;
    }

    public void setModedatacreatertype(Integer modedatacreatertype) {
        this.modedatacreatertype = modedatacreatertype;
    }

    public String getModedatacreatedate() {
        return modedatacreatedate;
    }

    public void setModedatacreatedate(String modedatacreatedate) {
        this.modedatacreatedate = modedatacreatedate == null ? null : modedatacreatedate.trim();
    }

    public String getModedatacreatetime() {
        return modedatacreatetime;
    }

    public void setModedatacreatetime(String modedatacreatetime) {
        this.modedatacreatetime = modedatacreatetime == null ? null : modedatacreatetime.trim();
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

    public String getModeuuid() {
        return modeuuid;
    }

    public void setModeuuid(String modeuuid) {
        this.modeuuid = modeuuid == null ? null : modeuuid.trim();
    }

    public Integer getAssessor() {
        return assessor;
    }

    public void setAssessor(Integer assessor) {
        this.assessor = assessor;
    }

    public String getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(String evaluationDate) {
        this.evaluationDate = evaluationDate == null ? null : evaluationDate.trim();
    }
}