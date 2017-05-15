package com.cn.mis.domain.entity.im;

import java.util.Date;

public class IMDeptuser extends IMDeptuserKey {
    private Date updateTime;

    private Integer updatetype;

    private String usercode;

    private Integer rankid;

    private Integer proid;

    private Integer areaid;

    private Integer usersort;

    private Long deptorgid;

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

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    public Integer getRankid() {
        return rankid;
    }

    public void setRankid(Integer rankid) {
        this.rankid = rankid;
    }

    public Integer getProid() {
        return proid;
    }

    public void setProid(Integer proid) {
        this.proid = proid;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public Integer getUsersort() {
        return usersort;
    }

    public void setUsersort(Integer usersort) {
        this.usersort = usersort;
    }

    public Long getDeptorgid() {
        return deptorgid;
    }

    public void setDeptorgid(Long deptorgid) {
        this.deptorgid = deptorgid;
    }
}