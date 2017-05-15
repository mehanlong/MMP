package com.cn.mis.service.im.impl;

import com.cn.mis.dao.im.IMUserMapper;
import com.cn.mis.domain.entity.im.IMUser;
import com.cn.mis.domain.entity.im.IMUserKey;
import com.cn.mis.service.im.IIMUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/4/5.
 */
@Service("imUserServcie")
public class IMUserServiceImpl implements IIMUserService{

    @Resource
    private IMUserMapper imUserMapper;

    @Override
    public int deleteByPrimaryKey(IMUserKey key) {
        return imUserMapper.deleteByPrimaryKey(key);
    }

    @Override
    public int insert(IMUser record) {
        return imUserMapper.insert(record);
    }

    @Override
    public int insertSelective(IMUser record) {
        return imUserMapper.insertSelective(record);
    }

    @Override
    public IMUser selectByPrimaryKey(IMUserKey key) {
        return imUserMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(IMUser record) {
        return imUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(IMUser record) {
        return imUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<IMUser> selectAll() {
        return imUserMapper.selectAll();
    }
}
