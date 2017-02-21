package com.cn.mis.service.impl;

import com.cn.mis.dao.DTThreeMapper;
import com.cn.mis.domain.entity.DTThree;
import com.cn.mis.service.IDTThreeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by yuejia on 2017/2/21.
 */
@Service("dTThreeSetvie")
public class DTThreeServiceImpl implements IDTThreeService {

    @Resource
    private DTThreeMapper dtThreeMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return dtThreeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(DTThree record) {
        return dtThreeMapper.insert(record);
    }

    @Override
    public int insertSelective(DTThree record) {
        return dtThreeMapper.insertSelective(record);
    }

    @Override
    public DTThree selectByPrimaryKey(Integer id) {
        return dtThreeMapper.selectByPrimaryKey(id);
    }

    @Override
    public ArrayList<DTThree> selectBySql() {
        return dtThreeMapper.selectBySql();
    }

    @Override
    public int updateByPrimaryKeySelective(DTThree record) {
        return dtThreeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(DTThree record) {
        return dtThreeMapper.updateByPrimaryKey(record);
    }
}
