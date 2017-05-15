package com.cn.mis.dao.inface;

import com.cn.mis.domain.entity.inface.*;

import java.util.List;

public interface IFHrmDepartmentMapper {
    List<IFHrmDepartment> checkAllWithIm();
    List<IFHrmDepartment> checkAllWithTx();

    int insertBatch(List<IFHrmDepartment> list);
    int insertBatchTx(List<IFHrmDepartment> list);

    int updateSelBatch(List<IFHrmDepartmentWithIM> list);
    int updateSelBatchTx(List<IFHrmDepartmentWithTX> list);
    int updateInitBatchTx(List<IFHrmDepartmentWithTX> list);

    List<IFHrmDepartmentWithIM> selectAllNeedInit();
    List<IFHrmDepartmentWithTX> selectAllNeedInitTx();

    List<IFHrmDepartmentWithCustom> selectAllNeedUpdate();
    List<IFHrmDepartmentWithCustomTX> selectAllNeedUpdateTx();

    int updateSyncFlag(IFHrmDepartmentWithIM record);
    int updateSyncFlagTx(IFHrmDepartmentWithTX record);

}