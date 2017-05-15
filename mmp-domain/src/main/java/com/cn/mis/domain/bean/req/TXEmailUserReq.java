package com.cn.mis.domain.bean.req;

import lombok.Data;

/**
 * Created by yuejia on 2017/4/24.
 */
@Data
public class TXEmailUserReq {
    private String userid;
    private String name;
    private Long department[];
    private String position;
    private String mobile;
    private String tel;
    private String extid;
    private String gender;
    private String slaves[];
    private String password;
    private Integer cpwd_login;
}
