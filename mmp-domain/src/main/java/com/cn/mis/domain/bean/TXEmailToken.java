package com.cn.mis.domain.bean;

import lombok.Data;

/**
 * Created by yuejia on 2017/4/24.
 */
@Data
public class TXEmailToken {
    Integer errcode;
    String errmsg;
    String access_token;
    Integer expires_in;
}
