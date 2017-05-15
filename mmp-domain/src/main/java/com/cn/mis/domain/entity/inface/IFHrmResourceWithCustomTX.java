package com.cn.mis.domain.entity.inface;

import lombok.Data;

/**
 * Created by yuejia on 2017/4/21.
 */
@Data
public class IFHrmResourceWithCustomTX extends IFHrmResourceWithTX {
    private Long txdid;
}
