package com.cn.mis.domain.entity;

import lombok.Data;

@Data
public class FORebookInfo {
    private Integer RecordID;

    private Integer Sequence;

    private String PassengerName;

    private String RebookingTime;

    private String RebookedTime;

    private String RebookTakeOffTime;

    private String RebookArrivalTime;

    private String SubClass;

    private String ExchangeFlight;

    private String TicketNo;

}