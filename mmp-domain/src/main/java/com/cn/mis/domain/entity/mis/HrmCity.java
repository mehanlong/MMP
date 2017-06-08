package com.cn.mis.domain.entity.mis;

import java.math.BigDecimal;

public class HrmCity {
    private Integer id;

    private String cityname;

    private BigDecimal citylongitude;

    private BigDecimal citylatitude;

    private Integer provinceid;

    private Integer countryid;

    private String canceled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname == null ? null : cityname.trim();
    }

    public BigDecimal getCitylongitude() {
        return citylongitude;
    }

    public void setCitylongitude(BigDecimal citylongitude) {
        this.citylongitude = citylongitude;
    }

    public BigDecimal getCitylatitude() {
        return citylatitude;
    }

    public void setCitylatitude(BigDecimal citylatitude) {
        this.citylatitude = citylatitude;
    }

    public Integer getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(Integer provinceid) {
        this.provinceid = provinceid;
    }

    public Integer getCountryid() {
        return countryid;
    }

    public void setCountryid(Integer countryid) {
        this.countryid = countryid;
    }

    public String getCanceled() {
        return canceled;
    }

    public void setCanceled(String canceled) {
        this.canceled = canceled == null ? null : canceled.trim();
    }
}