package com.cn.mis.domain.entity.mis;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UFVendor {
    private Integer id;

    private Integer requestid;

    private String vendor;

    private Integer type;

    private String legalPerson;

    private Integer formmodeid;

    private Integer modedatacreater;

    private Integer modedatacreatertype;

    private String modedatacreatedate;

    private String modedatacreatetime;

    private Integer isdelete;

    private Integer whetherTheMargin;

    private BigDecimal marginAmount;

    private String validityStartDate;

    private String validityEndDate;

    private String personInCharge;

    private String propertyCorpId;

    private Integer isUse;

    private Integer createRequestid;

    private Integer updateRequestid;

    private Integer stopRequestid;

    private String businessMoble;

    private String financialContact;

    private String financialMoble;

    private String aftersalesContact;

    private String aftersalesMoble;

    private Integer ourContact;

    private String ourMoble;

    private Integer bossFlag;

    private Long bossId;

    private String vendorEmail;

    private String shortName;

    private String vendorType;

    private Integer vendorAttr;

    private String legalPersonCardid;

    private String bdEmail;

    private String financialEmail;

    private String aftersalesEmail;

    private String address;

    private String vendorComment;

    private String photo;
}