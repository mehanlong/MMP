package com.cn.mis.domain.entity.mis;

import lombok.Data;

/**
 * Created by yuejia on 2017/5/26.
 */
@Data
public class UFContractAWithVendor {
    private Integer id;
    private String contract_code;
    private String contract;
    private String sign_date;
    private String contract_start_date;
    private String contract_end_date;
    private String party_b;
    private Long boss_id;
    private String vendor;
    private String business_moble;
    private String vendor_email;
    private String person_in_charge;
    private String financial_contact;
    private String financial_moble;
    private Long bankid;
    private Integer settlement_cycle;
    private Integer settlement_type;
    private Integer settlement_days;
    private String comment;
    private Integer update_flag;
    private String boss_contractcode;
    private Integer modedatacreater;
    private String creatername;
}
