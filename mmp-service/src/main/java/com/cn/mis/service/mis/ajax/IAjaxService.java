package com.cn.mis.service.mis.ajax;

import com.cn.mis.domain.entity.mis.ajax.QD201706001DT1;
import com.cn.mis.domain.entity.mis.ajax.QD201706001DT2;

import java.util.List;

/**
 * Created by yuejia on 2017/5/22.
 */
public interface IAjaxService {
    int getMacCount();
    List<QD201706001DT1> Sel_QD201706001DT1(String orderon);

    List<QD201706001DT2> Sel_QD201706001DT2(String orderon);

}
