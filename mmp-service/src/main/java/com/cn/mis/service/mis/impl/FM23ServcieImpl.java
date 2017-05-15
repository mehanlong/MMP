package com.cn.mis.service.mis.impl;

import com.cn.mis.dao.mis.FM24Mapper;
import com.cn.mis.domain.entity.mis.FM24WithBLOBs;
import com.cn.mis.service.mis.IFM24Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/3/9.
 */
@Service("fm23Service")
public class FM23ServcieImpl implements IFM24Service {
    @Resource
    private FM24Mapper fm24Mapper;
    @Override
    public List<FM24WithBLOBs> selectAll() {
        return fm24Mapper.selectAll();
    }

    @Override
    public int updateByPrimaryKeySelective(FM24WithBLOBs record) {
        return fm24Mapper.updateByPrimaryKeySelective(record);
    }
}
