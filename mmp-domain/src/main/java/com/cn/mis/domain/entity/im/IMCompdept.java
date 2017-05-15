package com.cn.mis.domain.entity.im;

import java.util.Date;

public class IMCompdept {
    private Integer id;

    private Integer compid;

    private String deptname;

    private Integer pid;

    private Date updateTime;

    private Integer updatetype;

    private Integer sortmanager;

    private Integer wdsort;

    private String tel;

    private String deptenname;

    private Long deptorgid;

    private Long porgid;

    private Integer isempty;

    private String deptshortname;

    private String showlevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompid() {
        return compid;
    }

    public void setCompid(Integer compid) {
        this.compid = compid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname == null ? null : deptname.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdatetype() {
        return updatetype;
    }

    public void setUpdatetype(Integer updatetype) {
        this.updatetype = updatetype;
    }

    public Integer getSortmanager() {
        return sortmanager;
    }

    public void setSortmanager(Integer sortmanager) {
        this.sortmanager = sortmanager;
    }

    public Integer getWdsort() {
        return wdsort;
    }

    public void setWdsort(Integer wdsort) {
        this.wdsort = wdsort;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getDeptenname() {
        return deptenname;
    }

    public void setDeptenname(String deptenname) {
        this.deptenname = deptenname == null ? null : deptenname.trim();
    }

    public Long getDeptorgid() {
        return deptorgid;
    }

    public void setDeptorgid(Long deptorgid) {
        this.deptorgid = deptorgid;
    }

    public Long getPorgid() {
        return porgid;
    }

    public void setPorgid(Long porgid) {
        this.porgid = porgid;
    }

    public Integer getIsempty() {
        return isempty;
    }

    public void setIsempty(Integer isempty) {
        this.isempty = isempty;
    }

    public String getDeptshortname() {
        return deptshortname;
    }

    public void setDeptshortname(String deptshortname) {
        this.deptshortname = deptshortname == null ? null : deptshortname.trim();
    }

    public String getShowlevel() {
        return showlevel;
    }

    public void setShowlevel(String showlevel) {
        this.showlevel = showlevel == null ? null : showlevel.trim();
    }
}