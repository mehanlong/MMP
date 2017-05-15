package com.cn.mis.service.inface.impl;

import com.cn.mis.dao.inface.IFHrmResourceMapper;
import com.cn.mis.domain.entity.inface.*;
import com.cn.mis.domain.entity.mis.HrmResource;
import com.cn.mis.service.inface.IIFHrmResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/4/11.
 */
@Service("ifHrmResourceService")
public class IFHrmResourceServiceImpl implements IIFHrmResourceService {
    @Resource
    IFHrmResourceMapper ifHrmResourceMapper;


    @Override
    public List<IFHrmResource> checkAllWithIm() {
        return ifHrmResourceMapper.checkAllWithIm();
    }

    @Override
    public List<IFHrmResource> checkAllWithImTx() {
        return ifHrmResourceMapper.checkAllWithImTx();
    }

    @Override
    public int updateSelBatch(List<IFHrmResourceWithIM> list) {
        return ifHrmResourceMapper.updateSelBatch(list);
    }

    @Override
    public int updateSelBatchTx(List<IFHrmResourceWithTX> list) {
        return ifHrmResourceMapper.updateSelBatchTx(list);
    }

    @Override
    public int updateInitBatchTx(List<IFHrmResourceWithTX> list) {
        return ifHrmResourceMapper.updateInitBatchTx(list);
    }

    @Override
    public int insertBatch(List<IFHrmResource> list) {
        return ifHrmResourceMapper.insertBatch(list);
    }

    @Override
    public int insertBatchTx(List<IFHrmResource> list) {
        return ifHrmResourceMapper.insertBatchTx(list);
    }

    @Override
    public List<IFHrmResourceWithCustom> selectAllNeedInit() {
        return ifHrmResourceMapper.selectAllNeedInit();
    }

    @Override
    public List<IFHrmResourceWithCustomTX> selectAllNeedInitTx() {
        return ifHrmResourceMapper.selectAllNeedInitTx();
    }

    @Override
    public List<IFHrmResourceWithCustom> selectAllNeedUpdate() {
        return ifHrmResourceMapper.selectAllNeedUpdate();
    }

    @Override
    public List<IFHrmResourceWithCustomTX> selectAllNeedUpdateTx() {
        return ifHrmResourceMapper.selectAllNeedUpdateTx();
    }

    @Override
    public int updateSyncFlag(IFHrmResourceWithIM record) {
        return ifHrmResourceMapper.updateSyncFlag(record);
    }

    @Override
    public int updateSyncFlagTx(IFHrmResourceWithTX record) {
        return ifHrmResourceMapper.updateSyncFlagTx(record);
    }
}
