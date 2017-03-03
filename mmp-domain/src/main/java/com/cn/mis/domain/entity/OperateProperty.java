package com.cn.mis.domain.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OperateProperty {
	private Integer id;
	private String accountName;
	private String manager_name;
	private String manager_phone;
	private String lastname;
	private Long boss_id;
	private Timestamp create_at;
	
	
}
