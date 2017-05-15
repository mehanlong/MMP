package com.cn.mis.domain.entity.inface;

import java.util.Date;

public class IFHrmDepartment {
    private String departmentmark;

    private String departmentname;

    private Integer subcompanyid1;

    private Integer supdepid;

    private Integer showorder;

    private String canceled;

    private String departmentcode;

    private String ecologyPinyinSearch;

    private Integer id;

    public String getDepartmentmark() {
        return departmentmark;
    }

    public void setDepartmentmark(String departmentmark) {
        this.departmentmark = departmentmark == null ? null : departmentmark.trim();
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname == null ? null : departmentname.trim();
    }

    public Integer getSubcompanyid1() {
        return subcompanyid1;
    }

    public void setSubcompanyid1(Integer subcompanyid1) {
        this.subcompanyid1 = subcompanyid1;
    }

    public Integer getSupdepid() {
        return supdepid;
    }

    public void setSupdepid(Integer supdepid) {
        this.supdepid = supdepid;
    }

    public Integer getShoworder() {
        return showorder;
    }

    public void setShoworder(Integer showorder) {
        this.showorder = showorder;
    }

    public String getCanceled() {
        return canceled;
    }

    public void setCanceled(String canceled) {
        this.canceled = canceled == null ? null : canceled.trim();
    }

    public String getDepartmentcode() {
        return departmentcode;
    }

    public void setDepartmentcode(String departmentcode) {
        this.departmentcode = departmentcode == null ? null : departmentcode.trim();
    }

    public String getEcologyPinyinSearch() {
        return ecologyPinyinSearch;
    }

    public void setEcologyPinyinSearch(String ecologyPinyinSearch) {
        this.ecologyPinyinSearch = ecologyPinyinSearch == null ? null : ecologyPinyinSearch.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}