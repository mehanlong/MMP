package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.UFVendorDT1;
import com.cn.mis.domain.entity.mis.UFVendorDT1WithResource;

import java.util.List;

public interface UFVendorDT1Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UFVendorDT1 record);

    int insertSelective(UFVendorDT1 record);

    UFVendorDT1 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UFVendorDT1 record);

    int updateByPrimaryKey(UFVendorDT1 record);

    List<UFVendorDT1WithResource> selectAllNeedInit();

    List<UFVendorDT1WithResource> selectAllNeedUpdate();

    int updateBossReturnData(UFVendorDT1WithResource record);
}