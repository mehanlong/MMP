package com.cn.mis.dao.inface;

import com.cn.mis.domain.entity.inface.*;

import java.util.List;

public interface IFHrmResourceMapper {
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