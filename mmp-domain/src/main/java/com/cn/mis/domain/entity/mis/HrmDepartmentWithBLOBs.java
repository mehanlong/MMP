package com.cn.mis.domain.entity.mis;

public class HrmDepartmentWithBLOBs extends HrmDepartment {
    private String bmfzr;

    private String bmfgld;

    public String getBmfzr() {
        return bmfzr;
    }

    public void setBmfzr(String bmfzr) {
        this.bmfzr = bmfzr == null ? null : bmfzr.trim();
    }

    public String getBmfgld() {
        return bmfgld;
    }

    public void setBmfgld(String bmfgld) {
        this.bmfgld = bmfgld == null ? null : bmfgld.trim();
    }
}