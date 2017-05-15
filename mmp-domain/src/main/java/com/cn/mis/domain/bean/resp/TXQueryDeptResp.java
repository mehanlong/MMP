package com.cn.mis.domain.bean.resp;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by yuejia on 2017/4/25.
 */
@Data
public class TXQueryDeptResp {
    private int errcode;
    private String errmsg;
    private ArrayList<TXDeptment> department;
}
