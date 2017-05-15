package com.cn.mis.domain.entity.mis;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OperateProject {
	private Integer id;					//MIS社区ID
	private String community;			//社区名
	private Long upcBossId;				//所属物业ID
	private Integer total_households;	//总户数
	private String lastname;			//新增人/修改人
	private Timestamp createAt;				//创建日期
	private Timestamp updateAt;				//更新日期
	private String ucBossId;				//新增成功后回写

	private Integer access_control_install; 				//是否开通门禁
	private Integer whether_to_open_the_newspaper_repair;   //是否开通报事报修
	private String service_center_address;					//所在小区物业客服中心地址
	private String gd_province;								//省
	private String gd_city;									//市
	private String gd_district;								//区
	
	private String city;				//old接口
	private Integer online_households;  //old接口

}
