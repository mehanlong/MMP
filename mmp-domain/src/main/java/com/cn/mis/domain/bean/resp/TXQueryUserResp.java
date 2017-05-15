package com.cn.mis.domain.bean.resp;

import lombok.Data;

import java.util.List;

/**
 * Created by yuejia on 2017/5/4.
 */
@Data
public class TXQueryUserResp {
    private Integer errcode;
    private String errmsg;
    private List<TXUser> userlist;
}
