package com.cn.mis.domain.entity;

public class Account {
    private Integer id;

    private Integer dbcInteger1;

    private Integer dbcInteger6;

    private String dbcVarchar2;

    private Double dbcReal1;

    private Integer dbcSelect3;

    private String dbcSVarchar1;

    private Integer ownerId;

    private String accountName;

    private Integer level;

    private String state;

    private String city;

    private String region;

    private Integer lockStatus;

    private String ownerPhone;

    private String levelExplain;

    private String ownerName;

    private String dbcSelect3Explain;
    
    private String lockStatusExplain;
    			 
    private String department;
    
    //同步状态
    private String importStatus;
    
    //本地属性
    private boolean updateFlag;
    //boss
    private Integer departmentid;
    
    private String account_type;
    
    private Integer highSeaId;
    
    private Integer highSeaStatus;
    
    

	public Integer getHighSeaStatus() {
		return highSeaStatus;
	}

	public void setHighSeaStatus(Integer highSeaStatus) {
		this.highSeaStatus = highSeaStatus;
	}

	public Integer getHighSeaId() {
		return highSeaId;
	}

	public void setHighSeaId(Integer highSeaId) {
		this.highSeaId = highSeaId;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getImportStatus() {
		return importStatus;
	}

	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}

	public Integer getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(Integer departmentid) {
		this.departmentid = departmentid;
	}

	public boolean isUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(boolean updateFlag) {
		this.updateFlag = updateFlag;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDbcInteger1() {
		return dbcInteger1;
	}

	public void setDbcInteger1(Integer dbcInteger1) {
		this.dbcInteger1 = dbcInteger1;
	}

	public Integer getDbcInteger6() {
		return dbcInteger6;
	}

	public void setDbcInteger6(Integer dbcInteger6) {
		this.dbcInteger6 = dbcInteger6;
	}

	public String getDbcVarchar2() {
		return dbcVarchar2;
	}

	public void setDbcVarchar2(String dbcVarchar2) {
		this.dbcVarchar2 = dbcVarchar2;
	}

	public Double getDbcReal1() {
		return dbcReal1;
	}

	public void setDbcReal1(Double dbcReal1) {
		this.dbcReal1 = dbcReal1;
	}

	public Integer getDbcSelect3() {
		return dbcSelect3;
	}

	public void setDbcSelect3(Integer dbcSelect3) {
		this.dbcSelect3 = dbcSelect3;
	}

	public String getDbcSVarchar1() {
		return dbcSVarchar1;
	}

	public void setDbcSVarchar1(String dbcSVarchar1) {
		this.dbcSVarchar1 = dbcSVarchar1;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Integer getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public String getLevelExplain() {
		return levelExplain;
	}

	public void setLevelExplain(String levelExplain) {
		this.levelExplain = levelExplain;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getDbcSelect3Explain() {
		return dbcSelect3Explain;
	}

	public void setDbcSelect3Explain(String dbcSelect3Explain) {
		this.dbcSelect3Explain = dbcSelect3Explain;
	}

	public String getLockStatusExplain() {
		return lockStatusExplain;
	}

	public void setLockStatusExplain(String lockStatusExplain) {
		this.lockStatusExplain = lockStatusExplain;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

    
}