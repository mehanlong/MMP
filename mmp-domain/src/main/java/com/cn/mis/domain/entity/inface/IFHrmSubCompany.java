package com.cn.mis.domain.entity.inface;

import java.util.Date;

public class IFHrmSubCompany {
    private Integer id;

    private String subcompanyname;

    private String subcompanydesc;

    private Byte companyid;

    private Integer supsubcomid;

    private String url;

    private Integer showorder;

    private String canceled;

    private String subcompanycode;

    private String outkey;

    private Integer budgetatuomoveorder;

    private String ecologyPinyinSearch;

    private Integer limitusers;

    private Integer tlevel;

    private Integer imid;

    private Integer imsyncflag;

    private Date imcreatetime;

    private Date immodifytime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubcompanyname() {
        return subcompanyname;
    }

    public void setSubcompanyname(String subcompanyname) {
        this.subcompanyname = subcompanyname == null ? null : subcompanyname.trim();
    }

    public String getSubcompanydesc() {
        return subcompanydesc;
    }

    public void setSubcompanydesc(String subcompanydesc) {
        this.subcompanydesc = subcompanydesc == null ? null : subcompanydesc.trim();
    }

    public Byte getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Byte companyid) {
        this.companyid = companyid;
    }

    public Integer getSupsubcomid() {
        return supsubcomid;
    }

    public void setSupsubcomid(Integer supsubcomid) {
        this.supsubcomid = supsubcomid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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

    public String getSubcompanycode() {
        return subcompanycode;
    }

    public void setSubcompanycode(String subcompanycode) {
        this.subcompanycode = subcompanycode == null ? null : subcompanycode.trim();
    }

    public String getOutkey() {
        return outkey;
    }

    public void setOutkey(String outkey) {
        this.outkey = outkey == null ? null : outkey.trim();
    }

    public Integer getBudgetatuomoveorder() {
        return budgetatuomoveorder;
    }

    public void setBudgetatuomoveorder(Integer budgetatuomoveorder) {
        this.budgetatuomoveorder = budgetatuomoveorder;
    }

    public String getEcologyPinyinSearch() {
        return ecologyPinyinSearch;
    }

    public void setEcologyPinyinSearch(String ecologyPinyinSearch) {
        this.ecologyPinyinSearch = ecologyPinyinSearch == null ? null : ecologyPinyinSearch.trim();
    }

    public Integer getLimitusers() {
        return limitusers;
    }

    public void setLimitusers(Integer limitusers) {
        this.limitusers = limitusers;
    }

    public Integer getTlevel() {
        return tlevel;
    }

    public void setTlevel(Integer tlevel) {
        this.tlevel = tlevel;
    }

    public Integer getImid() {
        return imid;
    }

    public void setImid(Integer imid) {
        this.imid = imid;
    }

    public Integer getImsyncflag() {
        return imsyncflag;
    }

    public void setImsyncflag(Integer imsyncflag) {
        this.imsyncflag = imsyncflag;
    }

    public Date getImcreatetime() {
        return imcreatetime;
    }

    public void setImcreatetime(Date imcreatetime) {
        this.imcreatetime = imcreatetime;
    }

    public Date getImmodifytime() {
        return immodifytime;
    }

    public void setImmodifytime(Date immodifytime) {
        this.immodifytime = immodifytime;
    }
}