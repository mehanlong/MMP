package com.cn.mis.service.mis.ajax.impl;

import com.cn.mis.dao.mis.FM146Mapper;
import com.cn.mis.domain.entity.mis.FM146WithBLOBs;
import com.cn.mis.service.mis.ajax.IFM146Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/4/26.
 */
@Service("fm146Service")
public class FM146ServiceImpl implements IFM146Service {
    @Resource
    FM146Mapper fm146Mapper;

    @Override
    public List<FM146WithBLOBs> selectByParames(FM146WithBLOBs record) {
        return fm146Mapper.selectByParames(record);
    }
}
