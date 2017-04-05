package com.cn.mis.domain.entity;

import lombok.Data;

/**
 * Created by yuejia on 2017/3/14.
 */
@Data
public class HrmResourceWithDepartment extends HrmResource {
    private String departmentname;
    private String locationname;
    private String name;
}
