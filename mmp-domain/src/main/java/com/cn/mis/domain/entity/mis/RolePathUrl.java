package com.cn.mis.domain.entity.mis;

import lombok.Data;

/**
 * Created by yuejia on 2017/3/7.
 */
@Data
public class RolePathUrl {
    private Integer id;
    private Integer roleId;
    private String description;
    private String url;
    private Integer seq;

}
