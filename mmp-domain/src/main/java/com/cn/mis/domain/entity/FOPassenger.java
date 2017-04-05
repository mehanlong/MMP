package com.cn.mis.domain.entity;

import lombok.Data;

@Data
public class FOPassenger {
    private Integer RecordID;

    private Integer Sequence;

    private String PassengerName;

    private String SegStatus;

    private String TicketNo;

    private String CardTypeName;

    private String CardTypeNumber;


}