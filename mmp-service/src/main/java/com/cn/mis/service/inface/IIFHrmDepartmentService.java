package com.cn.mis.service.inface;

import com.cn.mis.domain.entity.inface.*;
import com.cn.mis.domain.entity.mis.HrmDepartmentWithBLOBs;

import java.util.List;

/**
 * Created by yuejia on 2017/4/7.
 */
public interface IIFHrmDepartmentService {
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
