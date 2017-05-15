package com.cn.mis.service.inface.impl;

import com.cn.mis.dao.inface.IFHrmDepartmentMapper;
import com.cn.mis.domain.entity.inface.*;
import com.cn.mis.domain.entity.mis.HrmDepartmentWithBLOBs;
import com.cn.mis.service.inface.IIFHrmDepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/4/7.
 */
@Service("ifHrmDepartmentService")
public class IFHrmDepartmentServiceImpl implements IIFHrmDepartmentService {
    @Resource
    IFHrmDepartmentMapper ifHrmDepartmentMapper;

    @Override
    public List<IFHrmDepartment> checkAllWithIm() {
        return ifHrmDepartmentMapper.checkAllWithIm();
    }

    @Override
    public List<IFHrmDepartment> checkAllWithTx() {
        return ifHrmDepartmentMapper.checkAllWithTx();
    }

    @Override
    public int insertBatch(List<IFHrmDepartment> list) {
        return ifHrmDepartmentMapper.insertBatch(list);
    }

    @Override
    public int insertBatchTx(List<IFHrmDepartment> list) {
        return ifHrmDepartmentMapper.insertBatchTx(list);
    }

    @Override
    public int updateSelBatch(List<IFHrmDepartmentWithIM> list) {
        return ifHrmDepartmentMapper.updateSelBatch(list);
    }

    @Override
    public int updateSelBatchTx(List<IFHrmDepartmentWithTX> list) {
        return ifHrmDepartmentMapper.updateSelBatchTx(list);
    }

    @Override
    public int updateInitBatchTx(List<IFHrmDepartmentWithTX> list) {
        return ifHrmDepartmentMapper.updateInitBatchTx(list);
    }

    @Override
    public List<IFHrmDepartmentWithIM> selectAllNeedInit() {
        return ifHrmDepartmentMapper.selectAllNeedInit();
    }

    @Override
    public List<IFHrmDepartmentWithTX> selectAllNeedInitTx() {
        return ifHrmDepartmentMapper.selectAllNeedInitTx();
    }

    @Override
    public List<IFHrmDepartmentWithCustom> selectAllNeedUpdate() {
        return ifHrmDepartmentMapper.selectAllNeedUpdate();
    }

    @Override
    public List<IFHrmDepartmentWithCustomTX> selectAllNeedUpdateTx() {
        return ifHrmDepartmentMapper.selectAllNeedUpdateTx();
    }

    @Override
    public int updateSyncFlag(IFHrmDepartmentWithIM record) {
        return ifHrmDepartmentMapper.updateSyncFlag(record);
    }

    @Override
    public int updateSyncFlagTx(IFHrmDepartmentWithTX record) {
        return ifHrmDepartmentMapper.updateSyncFlagTx(record);
    }
}
