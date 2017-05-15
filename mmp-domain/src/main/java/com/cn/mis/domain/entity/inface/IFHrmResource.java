package com.cn.mis.domain.entity.inface;

import java.util.Date;

public class IFHrmResource {
    private Integer id;

    private String loginid;

    private String password;

    private String lastname;

    private String sex;

    private String telephone;

    private String mobile;

    private String email;

    private String startdate;

    private String enddate;

    private Integer jobtitle;

    private Integer seclevel;

    private Integer departmentid;

    private Integer subcompanyid1;

    private Integer costcenterid;

    private Integer managerid;

    private Integer assistantid;

    private Integer resourceimageid;

    private Integer createrid;

    private String createdate;

    private Integer lastmodid;

    private String lastmoddate;

    private String lastlogindate;

    private String workcode;

    private String managerstr;

    private Integer status;

    private String probationenddate;

    private String passwdchgdate;

    private Double dsporder;

    private Integer accounttype;

    private Integer belongto;

    private String dactylogram;

    private String assistantdactylogram;

    private String pinyinlastname;

    private Integer mobileshowtype;

    private String ecologyPinyinSearch;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid == null ? null : loginid.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname == null ? null : lastname.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate == null ? null : startdate.trim();
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    public Integer getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(Integer jobtitle) {
        this.jobtitle = jobtitle;
    }

    public Integer getSeclevel() {
        return seclevel;
    }

    public void setSeclevel(Integer seclevel) {
        this.seclevel = seclevel;
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public Integer getSubcompanyid1() {
        return subcompanyid1;
    }

    public void setSubcompanyid1(Integer subcompanyid1) {
        this.subcompanyid1 = subcompanyid1;
    }

    public Integer getCostcenterid() {
        return costcenterid;
    }

    public void setCostcenterid(Integer costcenterid) {
        this.costcenterid = costcenterid;
    }

    public Integer getManagerid() {
        return managerid;
    }

    public void setManagerid(Integer managerid) {
        this.managerid = managerid;
    }

    public Integer getAssistantid() {
        return assistantid;
    }

    public void setAssistantid(Integer assistantid) {
        this.assistantid = assistantid;
    }

    public Integer getResourceimageid() {
        return resourceimageid;
    }

    public void setResourceimageid(Integer resourceimageid) {
        this.resourceimageid = resourceimageid;
    }

    public Integer getCreaterid() {
        return createrid;
    }

    public void setCreaterid(Integer createrid) {
        this.createrid = createrid;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate == null ? null : createdate.trim();
    }

    public Integer getLastmodid() {
        return lastmodid;
    }

    public void setLastmodid(Integer lastmodid) {
        this.lastmodid = lastmodid;
    }

    public String getLastmoddate() {
        return lastmoddate;
    }

    public void setLastmoddate(String lastmoddate) {
        this.lastmoddate = lastmoddate == null ? null : lastmoddate.trim();
    }

    public String getLastlogindate() {
        return lastlogindate;
    }

    public void setLastlogindate(String lastlogindate) {
        this.lastlogindate = lastlogindate == null ? null : lastlogindate.trim();
    }

    public String getWorkcode() {
        return workcode;
    }

    public void setWorkcode(String workcode) {
        this.workcode = workcode == null ? null : workcode.trim();
    }

    public String getManagerstr() {
        return managerstr;
    }

    public void setManagerstr(String managerstr) {
        this.managerstr = managerstr == null ? null : managerstr.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProbationenddate() {
        return probationenddate;
    }

    public void setProbationenddate(String probationenddate) {
        this.probationenddate = probationenddate == null ? null : probationenddate.trim();
    }

    public String getPasswdchgdate() {
        return passwdchgdate;
    }

    public void setPasswdchgdate(String passwdchgdate) {
        this.passwdchgdate = passwdchgdate == null ? null : passwdchgdate.trim();
    }

    public Double getDsporder() {
        return dsporder;
    }

    public void setDsporder(Double dsporder) {
        this.dsporder = dsporder;
    }

    public Integer getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(Integer accounttype) {
        this.accounttype = accounttype;
    }

    public Integer getBelongto() {
        return belongto;
    }

    public void setBelongto(Integer belongto) {
        this.belongto = belongto;
    }

    public String getDactylogram() {
        return dactylogram;
    }

    public void setDactylogram(String dactylogram) {
        this.dactylogram = dactylogram == null ? null : dactylogram.trim();
    }

    public String getAssistantdactylogram() {
        return assistantdactylogram;
    }

    public void setAssistantdactylogram(String assistantdactylogram) {
        this.assistantdactylogram = assistantdactylogram == null ? null : assistantdactylogram.trim();
    }

    public String getPinyinlastname() {
        return pinyinlastname;
    }

    public void setPinyinlastname(String pinyinlastname) {
        this.pinyinlastname = pinyinlastname == null ? null : pinyinlastname.trim();
    }

    public Integer getMobileshowtype() {
        return mobileshowtype;
    }

    public void setMobileshowtype(Integer mobileshowtype) {
        this.mobileshowtype = mobileshowtype;
    }

    public String getEcologyPinyinSearch() {
        return ecologyPinyinSearch;
    }

    public void setEcologyPinyinSearch(String ecologyPinyinSearch) {
        this.ecologyPinyinSearch = ecologyPinyinSearch == null ? null : ecologyPinyinSearch.trim();
    }


}