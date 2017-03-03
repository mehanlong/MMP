package com.cn.mis.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UFCommunity {
    private Integer id;

    private Integer requestid;

    private String community;

    private String householdNum;

    private String yearServiceAmount;

    private Integer unitAccessFlag;

    private Integer operationFlag;

    private String planOnlineDate;

    private String property;

    private String serviceAmount;

    private Integer formmodeid;

    private Integer modedatacreater;

    private Integer modedatacreatertype;

    private String modedatacreatedate;

    private String modedatacreatetime;

    private String modeuuid;

    private String communityAdd;

    private BigDecimal managmentArea;

    private Integer onlineHouseholds;

    private String propertyName;

    private String contractDate;

    private Integer communityStatus;

    private String city;

    private String communityManager;

    private String managerPhone;

    private String servicePhone;

    private Integer totalHouseholds;

    private BigDecimal dwellingArea;

    private BigDecimal officeArea;

    private BigDecimal businessArea;

    private BigDecimal otherArea;

    private String occupancyRate;

    private String chargeRate;

    private String callCenterArea;

    private Integer propertyEmpNum;

    private Integer unitDoorNum;

    private Integer unitAccessStatus;

    private Integer doorNum;

    private Integer doorAccessStatus;

    private Integer basementDoorNum;

    private Integer basementAccessStatus;

    private Integer qdAccessNum;

    private Integer qdAccessType;

    private String sidewalkGateFlag;

    private String sidewalkGateUse;

    private String roadGateFlag;

    private String roadGateUse;

    private String completedDate;

    private BigDecimal housingPrice;

    private BigDecimal rent;

    private String linkOrder;

    private Integer accessControlInstall;

    private String dateOfAccessControl;

    private Integer communityEnvironment;

    private Long bossId;

    private Integer communityOperationStatus;

    private String permeabilityRate;

    private String bindingRate;

    private Integer propertyType;

    private Integer section;

    private Integer sideAgreement;

    private Integer city1;

    private Integer roadNumIn;

    private Integer roadNumOut;

    private Integer sidwalkNum;

    private Integer islonghu;

    private String depUser;

    private String depUserTel;

    private Integer operationMethod;

    private String flag;

    private String ownerid;

    private String ownerTel;

    private String acOlineDate;

    private String requestDate;

    private Date createAt;

    private Integer createBy;

    private Date updateAt;

    private Integer updateBy;

    private Integer updateflag;

    private String requestOkDate;

    private Integer bossFaceFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequestid() {
        return requestid;
    }

    public void setRequestid(Integer requestid) {
        this.requestid = requestid;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community == null ? null : community.trim();
    }

    public String getHouseholdNum() {
        return householdNum;
    }

    public void setHouseholdNum(String householdNum) {
        this.householdNum = householdNum == null ? null : householdNum.trim();
    }

    public String getYearServiceAmount() {
        return yearServiceAmount;
    }

    public void setYearServiceAmount(String yearServiceAmount) {
        this.yearServiceAmount = yearServiceAmount == null ? null : yearServiceAmount.trim();
    }

    public Integer getUnitAccessFlag() {
        return unitAccessFlag;
    }

    public void setUnitAccessFlag(Integer unitAccessFlag) {
        this.unitAccessFlag = unitAccessFlag;
    }

    public Integer getOperationFlag() {
        return operationFlag;
    }

    public void setOperationFlag(Integer operationFlag) {
        this.operationFlag = operationFlag;
    }

    public String getPlanOnlineDate() {
        return planOnlineDate;
    }

    public void setPlanOnlineDate(String planOnlineDate) {
        this.planOnlineDate = planOnlineDate == null ? null : planOnlineDate.trim();
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property == null ? null : property.trim();
    }

    public String getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(String serviceAmount) {
        this.serviceAmount = serviceAmount == null ? null : serviceAmount.trim();
    }

    public Integer getFormmodeid() {
        return formmodeid;
    }

    public void setFormmodeid(Integer formmodeid) {
        this.formmodeid = formmodeid;
    }

    public Integer getModedatacreater() {
        return modedatacreater;
    }

    public void setModedatacreater(Integer modedatacreater) {
        this.modedatacreater = modedatacreater;
    }

    public Integer getModedatacreatertype() {
        return modedatacreatertype;
    }

    public void setModedatacreatertype(Integer modedatacreatertype) {
        this.modedatacreatertype = modedatacreatertype;
    }

    public String getModedatacreatedate() {
        return modedatacreatedate;
    }

    public void setModedatacreatedate(String modedatacreatedate) {
        this.modedatacreatedate = modedatacreatedate == null ? null : modedatacreatedate.trim();
    }

    public String getModedatacreatetime() {
        return modedatacreatetime;
    }

    public void setModedatacreatetime(String modedatacreatetime) {
        this.modedatacreatetime = modedatacreatetime == null ? null : modedatacreatetime.trim();
    }

    public String getModeuuid() {
        return modeuuid;
    }

    public void setModeuuid(String modeuuid) {
        this.modeuuid = modeuuid == null ? null : modeuuid.trim();
    }

    public String getCommunityAdd() {
        return communityAdd;
    }

    public void setCommunityAdd(String communityAdd) {
        this.communityAdd = communityAdd == null ? null : communityAdd.trim();
    }

    public BigDecimal getManagmentArea() {
        return managmentArea;
    }

    public void setManagmentArea(BigDecimal managmentArea) {
        this.managmentArea = managmentArea;
    }

    public Integer getOnlineHouseholds() {
        return onlineHouseholds;
    }

    public void setOnlineHouseholds(Integer onlineHouseholds) {
        this.onlineHouseholds = onlineHouseholds;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName == null ? null : propertyName.trim();
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate == null ? null : contractDate.trim();
    }

    public Integer getCommunityStatus() {
        return communityStatus;
    }

    public void setCommunityStatus(Integer communityStatus) {
        this.communityStatus = communityStatus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCommunityManager() {
        return communityManager;
    }

    public void setCommunityManager(String communityManager) {
        this.communityManager = communityManager == null ? null : communityManager.trim();
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone == null ? null : managerPhone.trim();
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone == null ? null : servicePhone.trim();
    }

    public Integer getTotalHouseholds() {
        return totalHouseholds;
    }

    public void setTotalHouseholds(Integer totalHouseholds) {
        this.totalHouseholds = totalHouseholds;
    }

    public BigDecimal getDwellingArea() {
        return dwellingArea;
    }

    public void setDwellingArea(BigDecimal dwellingArea) {
        this.dwellingArea = dwellingArea;
    }

    public BigDecimal getOfficeArea() {
        return officeArea;
    }

    public void setOfficeArea(BigDecimal officeArea) {
        this.officeArea = officeArea;
    }

    public BigDecimal getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(BigDecimal businessArea) {
        this.businessArea = businessArea;
    }

    public BigDecimal getOtherArea() {
        return otherArea;
    }

    public void setOtherArea(BigDecimal otherArea) {
        this.otherArea = otherArea;
    }

    public String getOccupancyRate() {
        return occupancyRate;
    }

    public void setOccupancyRate(String occupancyRate) {
        this.occupancyRate = occupancyRate == null ? null : occupancyRate.trim();
    }

    public String getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(String chargeRate) {
        this.chargeRate = chargeRate == null ? null : chargeRate.trim();
    }

    public String getCallCenterArea() {
        return callCenterArea;
    }

    public void setCallCenterArea(String callCenterArea) {
        this.callCenterArea = callCenterArea == null ? null : callCenterArea.trim();
    }

    public Integer getPropertyEmpNum() {
        return propertyEmpNum;
    }

    public void setPropertyEmpNum(Integer propertyEmpNum) {
        this.propertyEmpNum = propertyEmpNum;
    }

    public Integer getUnitDoorNum() {
        return unitDoorNum;
    }

    public void setUnitDoorNum(Integer unitDoorNum) {
        this.unitDoorNum = unitDoorNum;
    }

    public Integer getUnitAccessStatus() {
        return unitAccessStatus;
    }

    public void setUnitAccessStatus(Integer unitAccessStatus) {
        this.unitAccessStatus = unitAccessStatus;
    }

    public Integer getDoorNum() {
        return doorNum;
    }

    public void setDoorNum(Integer doorNum) {
        this.doorNum = doorNum;
    }

    public Integer getDoorAccessStatus() {
        return doorAccessStatus;
    }

    public void setDoorAccessStatus(Integer doorAccessStatus) {
        this.doorAccessStatus = doorAccessStatus;
    }

    public Integer getBasementDoorNum() {
        return basementDoorNum;
    }

    public void setBasementDoorNum(Integer basementDoorNum) {
        this.basementDoorNum = basementDoorNum;
    }

    public Integer getBasementAccessStatus() {
        return basementAccessStatus;
    }

    public void setBasementAccessStatus(Integer basementAccessStatus) {
        this.basementAccessStatus = basementAccessStatus;
    }

    public Integer getQdAccessNum() {
        return qdAccessNum;
    }

    public void setQdAccessNum(Integer qdAccessNum) {
        this.qdAccessNum = qdAccessNum;
    }

    public Integer getQdAccessType() {
        return qdAccessType;
    }

    public void setQdAccessType(Integer qdAccessType) {
        this.qdAccessType = qdAccessType;
    }

    public String getSidewalkGateFlag() {
        return sidewalkGateFlag;
    }

    public void setSidewalkGateFlag(String sidewalkGateFlag) {
        this.sidewalkGateFlag = sidewalkGateFlag == null ? null : sidewalkGateFlag.trim();
    }

    public String getSidewalkGateUse() {
        return sidewalkGateUse;
    }

    public void setSidewalkGateUse(String sidewalkGateUse) {
        this.sidewalkGateUse = sidewalkGateUse == null ? null : sidewalkGateUse.trim();
    }

    public String getRoadGateFlag() {
        return roadGateFlag;
    }

    public void setRoadGateFlag(String roadGateFlag) {
        this.roadGateFlag = roadGateFlag == null ? null : roadGateFlag.trim();
    }

    public String getRoadGateUse() {
        return roadGateUse;
    }

    public void setRoadGateUse(String roadGateUse) {
        this.roadGateUse = roadGateUse == null ? null : roadGateUse.trim();
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate == null ? null : completedDate.trim();
    }

    public BigDecimal getHousingPrice() {
        return housingPrice;
    }

    public void setHousingPrice(BigDecimal housingPrice) {
        this.housingPrice = housingPrice;
    }

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    public String getLinkOrder() {
        return linkOrder;
    }

    public void setLinkOrder(String linkOrder) {
        this.linkOrder = linkOrder == null ? null : linkOrder.trim();
    }

    public Integer getAccessControlInstall() {
        return accessControlInstall;
    }

    public void setAccessControlInstall(Integer accessControlInstall) {
        this.accessControlInstall = accessControlInstall;
    }

    public String getDateOfAccessControl() {
        return dateOfAccessControl;
    }

    public void setDateOfAccessControl(String dateOfAccessControl) {
        this.dateOfAccessControl = dateOfAccessControl == null ? null : dateOfAccessControl.trim();
    }

    public Integer getCommunityEnvironment() {
        return communityEnvironment;
    }

    public void setCommunityEnvironment(Integer communityEnvironment) {
        this.communityEnvironment = communityEnvironment;
    }

    public Long getBossId() {
        return bossId;
    }

    public void setBossId(Long bossId) {
        this.bossId = bossId;
    }

    public Integer getCommunityOperationStatus() {
        return communityOperationStatus;
    }

    public void setCommunityOperationStatus(Integer communityOperationStatus) {
        this.communityOperationStatus = communityOperationStatus;
    }

    public String getPermeabilityRate() {
        return permeabilityRate;
    }

    public void setPermeabilityRate(String permeabilityRate) {
        this.permeabilityRate = permeabilityRate == null ? null : permeabilityRate.trim();
    }

    public String getBindingRate() {
        return bindingRate;
    }

    public void setBindingRate(String bindingRate) {
        this.bindingRate = bindingRate == null ? null : bindingRate.trim();
    }

    public Integer getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Integer propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Integer getSideAgreement() {
        return sideAgreement;
    }

    public void setSideAgreement(Integer sideAgreement) {
        this.sideAgreement = sideAgreement;
    }

    public Integer getCity1() {
        return city1;
    }

    public void setCity1(Integer city1) {
        this.city1 = city1;
    }

    public Integer getRoadNumIn() {
        return roadNumIn;
    }

    public void setRoadNumIn(Integer roadNumIn) {
        this.roadNumIn = roadNumIn;
    }

    public Integer getRoadNumOut() {
        return roadNumOut;
    }

    public void setRoadNumOut(Integer roadNumOut) {
        this.roadNumOut = roadNumOut;
    }

    public Integer getSidwalkNum() {
        return sidwalkNum;
    }

    public void setSidwalkNum(Integer sidwalkNum) {
        this.sidwalkNum = sidwalkNum;
    }

    public Integer getIslonghu() {
        return islonghu;
    }

    public void setIslonghu(Integer islonghu) {
        this.islonghu = islonghu;
    }

    public String getDepUser() {
        return depUser;
    }

    public void setDepUser(String depUser) {
        this.depUser = depUser == null ? null : depUser.trim();
    }

    public String getDepUserTel() {
        return depUserTel;
    }

    public void setDepUserTel(String depUserTel) {
        this.depUserTel = depUserTel == null ? null : depUserTel.trim();
    }

    public Integer getOperationMethod() {
        return operationMethod;
    }

    public void setOperationMethod(Integer operationMethod) {
        this.operationMethod = operationMethod;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid == null ? null : ownerid.trim();
    }

    public String getOwnerTel() {
        return ownerTel;
    }

    public void setOwnerTel(String ownerTel) {
        this.ownerTel = ownerTel == null ? null : ownerTel.trim();
    }

    public String getAcOlineDate() {
        return acOlineDate;
    }

    public void setAcOlineDate(String acOlineDate) {
        this.acOlineDate = acOlineDate == null ? null : acOlineDate.trim();
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate == null ? null : requestDate.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getUpdateflag() {
        return updateflag;
    }

    public void setUpdateflag(Integer updateflag) {
        this.updateflag = updateflag;
    }

    public String getRequestOkDate() {
        return requestOkDate;
    }

    public void setRequestOkDate(String requestOkDate) {
        this.requestOkDate = requestOkDate == null ? null : requestOkDate.trim();
    }

    public Integer getBossFaceFlag() {
        return bossFaceFlag;
    }

    public void setBossFaceFlag(Integer bossFaceFlag) {
        this.bossFaceFlag = bossFaceFlag;
    }
}