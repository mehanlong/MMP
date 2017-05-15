package com.cn.mis.domain.bean.resp;

import lombok.Data;

/**
 * Created by yuejia on 2017/4/24.
 */
@Data
public class TXDeptment {
    private Long id;
    private String name;
    private Long parentid;
    private int order;
    private String path;
}
