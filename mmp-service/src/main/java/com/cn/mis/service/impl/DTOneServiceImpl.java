package com.cn.mis.service.impl;

import com.cn.mis.dao.DTOneMapper;
import com.cn.mis.domain.entity.DTOne;
import com.cn.mis.domain.entity.DTOneWithBLOBs;
import com.cn.mis.service.IDTOneService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by yuejia on 2017/2/21.
 */
@Service("dTOneService")
public class DTOneServiceImpl implements IDTOneService {

    @Resource
    private DTOneMapper dtOneMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return dtOneMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(DTOneWithBLOBs record) {
        return dtOneMapper.insert(record);
    }

    @Override
    public int insertSelective(DTOneWithBLOBs record) {
        return dtOneMapper.insertSelective(record);
    }

    @Override
    public DTOneWithBLOBs selectByPrimaryKey(Integer id) {
        return dtOneMapper.selectByPrimaryKey(id);
    }

    @Override
    public ArrayList<DTOneWithBLOBs> selectAll() {
        return dtOneMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKeySelective(DTOneWithBLOBs record) {
        return dtOneMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(DTOneWithBLOBs record) {
        return dtOneMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(DTOne record) {
        return dtOneMapper.updateByPrimaryKey(record);
    }
}
