package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.UFCategoryWithContractA;
import com.cn.mis.domain.entity.mis.UFContractAWithVendor;

import java.util.List;

/**
 * Created by yuejia on 2017/5/26.
 */
public interface UFContractAMapper {
    List<UFContractAWithVendor> selectAllNeedInit();
    List<UFContractAWithVendor> selectAllNeedAdd();

    List<UFContractAWithVendor> selectAllNeedUpdate();

    int updateBossReturnData(UFContractAWithVendor record);

    List<UFCategoryWithContractA> selectCategoryByMainIdNew(Integer mainid);
    List<UFCategoryWithContractA> selectCategoryByMainIdOld(Integer mainid);
    int updateCategoryReturnData(UFCategoryWithContractA record);
}
