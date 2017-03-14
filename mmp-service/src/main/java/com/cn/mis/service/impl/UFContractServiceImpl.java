package com.cn.mis.service.impl;

import com.cn.mis.dao.UFCContractMapper;
import com.cn.mis.domain.entity.UFCContractWithBLOBs;
import com.cn.mis.service.IUFContractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/3/9.
 */
@Service("ufContracrtService")
public class UFContractServiceImpl implements IUFContractService {
    @Resource
    private UFCContractMapper ufcContractMapper;


    @Override
    public List<UFCContractWithBLOBs> selectAll() {
        return ufcContractMapper.selectAll();
    }

    @Override
    public UFCContractWithBLOBs selectByPrimaryKey(Integer id) {
        return ufcContractMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UFCContractWithBLOBs record) {
        return ufcContractMapper.updateByPrimaryKeySelective(record);
    }
}
