package com.cn.mis.dao;

import com.cn.mis.domain.entity.OLSyncProject;

import java.util.List;

public interface OLSyncProjectMapper {
    
    List<OLSyncProject> selectByIds(List<Long> list);
    
    int insertBatch(List<OLSyncProject> list);
    
    int updateBatch(List<OLSyncProject> list);
    
    int deleteBatch(List<Integer> list);
}