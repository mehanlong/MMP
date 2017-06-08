package com.cn.mis.domain.entity.mis;

import lombok.Data;

/**
 * Created by yuejia on 2017/5/25.
 */
@Data
public class OperateProjectConnect {
    private Integer id;
    private String ucBossId;                                //BOSS社区ID
    private String community;			                    //社区名
    private String service_center_address;					//所在小区物业客服中心地址
    private String service_phone;							//服务中心电话
    private Long boss_service_center_address_id;          //BOSS社区服务中心地址ID
    private Integer boss_service_center_address_flage;       //BOSS社区服务中心地址同步标记位
    private String lastname;

}
