package com.cn.mis.service.impl;

import com.cn.mis.dao.CRMAccountMapper;
import com.cn.mis.domain.entity.CRMAccount;
import com.cn.mis.service.ICRMAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yuejia on 2017/2/14.
 */
@Service("crmAccountService")
public class CRMAccountServiceImpl implements ICRMAccountService {
    @Resource
    private CRMAccountMapper crmAccountMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return crmAccountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CRMAccount record) {
        return crmAccountMapper.insert(record);
    }

    @Override
    public int insertSelective(CRMAccount record) {
        return crmAccountMapper.insertSelective(record);
    }

    @Override
    public CRMAccount selectByPrimaryKey(Integer id) {
        return crmAccountMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CRMAccount record) {
        return crmAccountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CRMAccount record) {
        return crmAccountMapper.updateByPrimaryKey(record);
    }
}
