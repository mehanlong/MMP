package com.cn.mis.service.mis.impl;

import com.cn.mis.dao.mis.UFVendorMapper;
import com.cn.mis.domain.entity.mis.ImageFiled;
import com.cn.mis.domain.entity.mis.UFVendor;
import com.cn.mis.domain.entity.mis.UFVendorWithResource;
import com.cn.mis.service.mis.IUFVendorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/5/19.
 */
@Service("ufVendorService")
public class UFVendorServiceImpl implements IUFVendorService {
    @Resource
    UFVendorMapper ufVendorMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return ufVendorMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UFVendor record) {
        return ufVendorMapper.insert(record);
    }

    @Override
    public int insertSelective(UFVendor record) {
        return ufVendorMapper.insertSelective(record);
    }

    @Override
    public UFVendor selectByPrimaryKey(Integer id) {
        return ufVendorMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UFVendor record) {
        return ufVendorMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UFVendor record) {
        return ufVendorMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<UFVendorWithResource> selectAllNeedInit() {
        return ufVendorMapper.selectAllNeedInit();
    }

    @Override
    public List<UFVendorWithResource> selectAllNeedUpdate() {
        return ufVendorMapper.selectAllNeedUpdate();
    }

    @Override
    public int updateBossReturnData(UFVendorWithResource record) {
        return ufVendorMapper.updateBossReturnData(record);
    }

    @Override
    public List<ImageFiled> SelImageFiledPhoto(List<String> ids) {
        return ufVendorMapper.SelImageFiledPhoto(ids);
    }

    @Override
    public List<ImageFiled> SelImageFiledComment(List<String> ids) {
        return ufVendorMapper.SelImageFiledComment(ids);
    }
}
