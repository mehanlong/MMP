package com.cn.mis.service.im.impl;

import com.cn.mis.dao.im.IMCompdeptMapper;
import com.cn.mis.domain.entity.im.IMCompdept;
import com.cn.mis.service.im.IIMCompdeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yuejia on 2017/4/7.
 */
@Service("imCompdeptService")
public class IMCompdeptServiceImpl implements IIMCompdeptService {
    @Resource
    IMCompdeptMapper imCompdeptMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return imCompdeptMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(IMCompdept record) {
        return imCompdeptMapper.insert(record);
    }

    @Override
    public int insertSelective(IMCompdept record) {
        return imCompdeptMapper.insertSelective(record);
    }

    @Override
    public IMCompdept selectByPrimaryKey(Integer id) {
        return imCompdeptMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(IMCompdept record) {
        return imCompdeptMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(IMCompdept record) {
        return imCompdeptMapper.updateByPrimaryKey(record);
    }
}
