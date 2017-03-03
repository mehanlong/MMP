package com.cn.mis.service;

import com.cn.mis.domain.entity.OLSyncProject;

import java.util.List;


public interface IOLSyncProjectService {
    
    List<OLSyncProject> selectByIds(List<Long> list);
    
    int insertBatch(List<OLSyncProject> list);
    
    int updateBatch(List<OLSyncProject> list);
    
    int deleteBatch(List<Integer> list);
}
