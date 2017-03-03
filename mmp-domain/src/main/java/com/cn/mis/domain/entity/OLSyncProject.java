package com.cn.mis.domain.entity;

import lombok.Data;

@Data
public class OLSyncProject {
    private Long id;

    private String relDate;

    private Integer relUserNum;

    private Integer exRoomNum;

    private Integer exFullPhoneNum;

    private Integer exHidePhoneNum;

    private String stOneDate;

    private Integer stOneRegNum;

    private Integer stOneBunNum;

    private String curOsmosisRate;

    private String curBundingRate;
    
    private boolean updateFlag;
    
    private String importStatus;
    
}