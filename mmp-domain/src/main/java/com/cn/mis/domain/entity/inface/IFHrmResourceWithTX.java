package com.cn.mis.domain.entity.inface;

import lombok.Data;

import java.util.Date;

/**
 * Created by yuejia on 2017/4/21.
 */
@Data
public class IFHrmResourceWithTX extends IFHrmResource{
    private String txid;

    private Integer txsyncflag;

    private Date txcreatetime;

    private Date txmodifytime;
}
