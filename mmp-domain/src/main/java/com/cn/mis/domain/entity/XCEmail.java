package com.cn.mis.domain.entity;

import java.util.Date;

public class XCEmail {
    private Integer id;

    private String corpName;

    private String primaryDepartment;

    private String affiliationDepartment;

    private String bookingAgent;

    private String bookingType;

    private String name;

    private String dpartureCity;

    private String destinationCity;

    private Date departureDate;

    private Date destinationDate;

    private Date emailTime;

    private Date processTime;

    private Byte processFlag;

    private String amOrPm;

    private String pmRoAm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName == null ? null : corpName.trim();
    }

    public String getPrimaryDepartment() {
        return primaryDepartment;
    }

    public void setPrimaryDepartment(String primaryDepartment) {
        this.primaryDepartment = primaryDepartment == null ? null : primaryDepartment.trim();
    }

    public String getAffiliationDepartment() {
        return affiliationDepartment;
    }

    public void setAffiliationDepartment(String affiliationDepartment) {
        this.affiliationDepartment = affiliationDepartment == null ? null : affiliationDepartment.trim();
    }

    public String getBookingAgent() {
        return bookingAgent;
    }

    public void setBookingAgent(String bookingAgent) {
        this.bookingAgent = bookingAgent == null ? null : bookingAgent.trim();
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType == null ? null : bookingType.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDpartureCity() {
        return dpartureCity;
    }

    public void setDpartureCity(String dpartureCity) {
        this.dpartureCity = dpartureCity == null ? null : dpartureCity.trim();
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity == null ? null : destinationCity.trim();
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getDestinationDate() {
        return destinationDate;
    }

    public void setDestinationDate(Date destinationDate) {
        this.destinationDate = destinationDate;
    }

    public Date getEmailTime() {
        return emailTime;
    }

    public void setEmailTime(Date emailTime) {
        this.emailTime = emailTime;
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public Byte getProcessFlag() {
        return processFlag;
    }

    public void setProcessFlag(Byte processFlag) {
        this.processFlag = processFlag;
    }

    public String getAmOrPm() {
        return amOrPm;
    }

    public void setAmOrPm(String amOrPm) {
        this.amOrPm = amOrPm == null ? null : amOrPm.trim();
    }

    public String getPmRoAm() {
        return pmRoAm;
    }

    public void setPmRoAm(String pmRoAm) {
        this.pmRoAm = pmRoAm == null ? null : pmRoAm.trim();
    }
}