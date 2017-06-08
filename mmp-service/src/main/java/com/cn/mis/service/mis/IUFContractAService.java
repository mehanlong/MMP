package com.cn.mis.service.mis;

import com.cn.mis.domain.entity.mis.UFCategoryWithContractA;
import com.cn.mis.domain.entity.mis.UFContractAWithVendor;

import java.util.List;

/**
 * Created by yuejia on 2017/5/27.
 */
public interface IUFContractAService {
    List<UFContractAWithVendor> selectAllNeedInit();

    List<UFContractAWithVendor> selectAllNeedUpdate();
    List<UFContractAWithVendor> selectAllNeedAdd();

    int updateBossReturnData(UFContractAWithVendor record);

    List<UFCategoryWithContractA> selectCategoryByMainIdNew(Integer mainid);
    List<UFCategoryWithContractA> selectCategoryByMainIdOld(Integer mainid);
    int updateCategoryReturnData(UFCategoryWithContractA record);
}
