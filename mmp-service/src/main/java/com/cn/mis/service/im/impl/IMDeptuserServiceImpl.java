package com.cn.mis.service.im.impl;

import com.cn.mis.dao.im.IMDeptuserMapper;
import com.cn.mis.domain.entity.im.IMDeptuser;
import com.cn.mis.domain.entity.im.IMDeptuserKey;
import com.cn.mis.service.im.IIMDeptuserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yuejia on 2017/4/11.
 */
@Service("imDeptuserService")
public class IMDeptuserServiceImpl implements IIMDeptuserService {
    @Resource
    IMDeptuserMapper imDeptuserMapper;

    @Override
    public int deleteByPrimaryKey(IMDeptuserKey key) {
        return imDeptuserMapper.deleteByPrimaryKey(key);
    }

    @Override
    public int insert(IMDeptuser record) {
        return imDeptuserMapper.insert(record);
    }

    @Override
    public int insertSelective(IMDeptuser record) {
        return imDeptuserMapper.insertSelective(record);
    }

    @Override
    public IMDeptuser selectByPrimaryKey(IMDeptuserKey key) {
        return imDeptuserMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(IMDeptuser record) {
        return imDeptuserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(IMDeptuser record) {
        return imDeptuserMapper.updateByPrimaryKey(record);
    }
}
