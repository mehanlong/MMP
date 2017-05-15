package com.cn.mis.service.mis;

import com.cn.mis.domain.entity.mis.OLSyncProject;

import java.util.List;


public interface IOLSyncProjectService {
    
    List<OLSyncProject> selectByIds(List<Long> list);
    
    int insertBatch(List<OLSyncProject> list);
    
    int updateBatch(List<OLSyncProject> list);
    
    int deleteBatch(List<Integer> list);
}
