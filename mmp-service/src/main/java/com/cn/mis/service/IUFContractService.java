package com.cn.mis.service;

import com.cn.mis.domain.entity.UFCContractWithBLOBs;

import java.util.List;

/**
 * Created by yuejia on 2017/3/9.
 */
public interface IUFContractService {

    List<UFCContractWithBLOBs> selectAll();

    UFCContractWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UFCContractWithBLOBs record);
}
