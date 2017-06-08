package com.cn.mis.service.mis.ajax.impl;

import com.cn.mis.dao.mis.AjaxMapper;
import com.cn.mis.domain.entity.mis.ajax.QD201706001DT1;
import com.cn.mis.domain.entity.mis.ajax.QD201706001DT2;
import com.cn.mis.service.mis.ajax.IAjaxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/5/22.
 */
@Service("ajaxServcie")
public class AjaxServiceImpl implements IAjaxService {
    @Resource
    AjaxMapper ajaxMapper;

    @Override
    public int getMacCount() {
        return ajaxMapper.getMacCount();
    }

    @Override
    public List<QD201706001DT1> Sel_QD201706001DT1(String orderon) {
        return ajaxMapper.Sel_QD201706001DT1(orderon);
    }

    @Override
    public List<QD201706001DT2> Sel_QD201706001DT2(String orderon) {
        return ajaxMapper.Sel_QD201706001DT2(orderon);
    }
}
