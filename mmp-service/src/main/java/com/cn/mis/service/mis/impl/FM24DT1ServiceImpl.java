package com.cn.mis.service.mis.impl;

import com.cn.mis.dao.mis.FM24DT1Mapper;
import com.cn.mis.domain.entity.mis.FM24DT1;
import com.cn.mis.service.mis.IFM24DT1Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/4/12.
 */
@Service("fm24DT1Service")
public class FM24DT1ServiceImpl implements IFM24DT1Service {
    @Resource
    FM24DT1Mapper fm24DT1Mapper;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(FM24DT1 record) {
        return 0;
    }

    @Override
    public int insertSelective(FM24DT1 record) {
        return 0;
    }

    @Override
    public FM24DT1 selectByPrimaryKey(Integer id) {
        return fm24DT1Mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(FM24DT1 record) {
        return fm24DT1Mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(FM24DT1 record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(FM24DT1 record) {
        return 0;
    }

    @Override
    public List<FM24DT1> selectAll() {
        return fm24DT1Mapper.selectAll();
    }
}
