package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.ajax.QD201706001DT1;
import com.cn.mis.domain.entity.mis.ajax.QD201706001DT2;

import java.util.List;

/**
 * Created by yuejia on 2017/5/22.
 */
public interface AjaxMapper {
    int getMacCount();

    List<QD201706001DT1> Sel_QD201706001DT1(String orderno);

    List<QD201706001DT2> Sel_QD201706001DT2(String orderno);
}
