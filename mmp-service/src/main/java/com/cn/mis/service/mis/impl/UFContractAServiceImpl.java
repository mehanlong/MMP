package com.cn.mis.service.mis.impl;

import com.cn.mis.dao.mis.UFContractAMapper;
import com.cn.mis.domain.entity.mis.UFCategoryWithContractA;
import com.cn.mis.domain.entity.mis.UFContractAWithVendor;
import com.cn.mis.service.mis.IUFContractAService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/5/27.
 */
@Service("ufContractAService")
public class UFContractAServiceImpl implements IUFContractAService {
    @Resource
    UFContractAMapper ufContractAMapper;
    @Override
    public List<UFContractAWithVendor> selectAllNeedInit() {
        return ufContractAMapper.selectAllNeedInit();
    }

    @Override
    public List<UFContractAWithVendor> selectAllNeedUpdate() {
        return ufContractAMapper.selectAllNeedUpdate();
    }

    @Override
    public List<UFContractAWithVendor> selectAllNeedAdd() {
        return ufContractAMapper.selectAllNeedAdd();
    }

    @Override
    public int updateBossReturnData(UFContractAWithVendor record) {
        return ufContractAMapper.updateBossReturnData(record);
    }

    @Override
    public List<UFCategoryWithContractA> selectCategoryByMainIdNew(Integer mainid) {
        return ufContractAMapper.selectCategoryByMainIdNew(mainid);
    }

    @Override
    public List<UFCategoryWithContractA> selectCategoryByMainIdOld(Integer mainid) {
        return ufContractAMapper.selectCategoryByMainIdOld(mainid);
    }

    @Override
    public int updateCategoryReturnData(UFCategoryWithContractA record) {
        return ufContractAMapper.updateCategoryReturnData(record);
    }
}
