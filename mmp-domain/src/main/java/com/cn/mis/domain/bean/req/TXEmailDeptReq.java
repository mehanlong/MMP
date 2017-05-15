package com.cn.mis.domain.bean.req;

import lombok.Data;

/**
 * Created by yuejia on 2017/4/21.
 */
@Data
public class TXEmailDeptReq {
    Long id;
    String name;
    Long parentid;
    Integer order;
}
