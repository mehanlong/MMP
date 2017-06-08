package com.cn.mis.service.mis.impl;

import com.cn.mis.dao.mis.UFVendorDT1Mapper;
import com.cn.mis.domain.entity.mis.UFVendor;
import com.cn.mis.domain.entity.mis.UFVendorDT1;
import com.cn.mis.domain.entity.mis.UFVendorDT1WithResource;
import com.cn.mis.domain.entity.mis.UFVendorWithResource;
import com.cn.mis.service.mis.IUFVendorDT1Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/5/22.
 */
@Service("ufVendorDT1Service")
public class UFVendorDT1ServiceImpl implements IUFVendorDT1Service {
    @Resource
    UFVendorDT1Mapper ufVendorDT1Mapper;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return ufVendorDT1Mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UFVendorDT1 record) {
        return ufVendorDT1Mapper.insert(record);
    }

    @Override
    public int insertSelective(UFVendorDT1 record) {
        return ufVendorDT1Mapper.insertSelective(record);
    }

    @Override
    public UFVendorDT1 selectByPrimaryKey(Integer id) {
        return ufVendorDT1Mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UFVendorDT1 record) {
        return ufVendorDT1Mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UFVendorDT1 record) {
        return ufVendorDT1Mapper.updateByPrimaryKey(record);
    }

    @Override
    public List<UFVendorDT1WithResource> selectAllNeedInit() {
        return ufVendorDT1Mapper.selectAllNeedInit();
    }

    @Override
    public List<UFVendorDT1WithResource> selectAllNeedUpdate() {
        return ufVendorDT1Mapper.selectAllNeedUpdate();
    }

    @Override
    public int updateBossReturnData(UFVendorDT1WithResource record) {
        return ufVendorDT1Mapper.updateBossReturnData(record);
    }
}
