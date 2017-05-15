package com.cn.mis.domain.entity.inface;

import lombok.Data;

/**
 * Created by yuejia on 2017/4/19.
 */
@Data
public class IFHrmDepartmentWithCustomTX extends IFHrmDepartmentWithTX {
    private Long txpid;
}
