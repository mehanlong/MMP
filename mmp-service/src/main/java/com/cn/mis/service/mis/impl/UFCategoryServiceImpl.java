package com.cn.mis.service.mis.impl;

import com.cn.mis.dao.mis.UFCategoryMapper;
import com.cn.mis.domain.entity.mis.UFCategory;
import com.cn.mis.service.mis.IUFCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/5/24.
 */
@Service("ufCategoryService")
public class UFCategoryServiceImpl implements IUFCategoryService{
    @Resource
    UFCategoryMapper ufCategoryMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return ufCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UFCategory record) {
        return ufCategoryMapper.insert(record);
    }

    @Override
    public int insertSelective(UFCategory record) {
        return ufCategoryMapper.insertSelective(record);
    }

    @Override
    public UFCategory selectByPrimaryKey(Integer id) {
        return ufCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UFCategory record) {
        return ufCategoryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UFCategory record) {
        return ufCategoryMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<UFCategory> selectAll() {
        return ufCategoryMapper.selectAll();
    }

    @Override
    public int insertBatch(List<UFCategory> list) {
        return ufCategoryMapper.insertBatch(list);
    }

    @Override
    public int updateBatch(List<UFCategory> list) {
        return ufCategoryMapper.updateBatch(list);
    }
}
