package com.cn.mis.service.inface;

import com.cn.mis.domain.entity.inface.*;
import com.cn.mis.domain.entity.mis.HrmResource;

import java.util.List;

/**
 * Created by yuejia on 2017/4/11.
 */
public interface IIFHrmResourceService {
    List<IFHrmResource> checkAllWithIm();
    List<IFHrmResource> checkAllWithImTx();

    int updateSelBatch(List<IFHrmResourceWithIM> list);
    int updateSelBatchTx(List<IFHrmResourceWithTX> list);
    int updateInitBatchTx(List<IFHrmResourceWithTX> list);

    int insertBatch(List<IFHrmResource> list);
    int insertBatchTx(List<IFHrmResource> list);

    List<IFHrmResourceWithCustom> selectAllNeedInit();
    List<IFHrmResourceWithCustomTX> selectAllNeedInitTx();

    List<IFHrmResourceWithCustom> selectAllNeedUpdate();
    List<IFHrmResourceWithCustomTX> selectAllNeedUpdateTx();

    int updateSyncFlag(IFHrmResourceWithIM record);
    int updateSyncFlagTx(IFHrmResourceWithTX record);
}
