package com.cn.mis.domain.entity.inface;

import lombok.Data;

import java.util.Date;

/**
 * Created by yuejia on 2017/4/19.
 */
@Data
public class IFHrmDepartmentWithTX extends IFHrmDepartment {
    private Long txid;

    private Integer txsyncflag;

    private Date txcreatetime;

    private Date txmodifytime;
}
