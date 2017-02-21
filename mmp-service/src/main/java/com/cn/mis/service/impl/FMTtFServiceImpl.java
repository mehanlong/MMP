package com.cn.mis.service.impl;

import com.cn.mis.dao.FMTtFMapper;
import com.cn.mis.domain.entity.FMTtF;
import com.cn.mis.domain.entity.FMTtFWithBLOBs;
import com.cn.mis.service.IFMTtFService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by yuejia on 2017/2/21.
 */
@Service("fMTtFService")
public class FMTtFServiceImpl implements IFMTtFService {
    @Resource
    private FMTtFMapper fmTtFMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return fmTtFMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(FMTtFWithBLOBs record) {
        return fmTtFMapper.insert(record);
    }

    @Override
    public int insertSelective(FMTtFWithBLOBs record) {
        return fmTtFMapper.insertSelective(record);
    }

    @Override
    public FMTtFWithBLOBs selectByPrimaryKey(Integer id) {
        return fmTtFMapper.selectByPrimaryKey(id);
    }

    @Override
    public ArrayList<FMTtFWithBLOBs> selectBySql() {
        return fmTtFMapper.selectBySql();
    }

    @Override
    public int updateByPrimaryKeySelective(FMTtFWithBLOBs record) {
        return fmTtFMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(FMTtFWithBLOBs record) {
        return fmTtFMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(FMTtF record) {
        return fmTtFMapper.updateByPrimaryKey(record);
    }
}
