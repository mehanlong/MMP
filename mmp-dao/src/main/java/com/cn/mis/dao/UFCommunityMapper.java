package com.cn.mis.dao;


import com.cn.mis.domain.entity.UFCommunity;
import com.cn.mis.domain.entity.UFCommunityWithBLOBs;

public interface UFCommunityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UFCommunityWithBLOBs record);

    int insertSelective(UFCommunityWithBLOBs record);

    UFCommunityWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UFCommunityWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UFCommunityWithBLOBs record);

    int updateByPrimaryKey(UFCommunity record);
    
    UFCommunityWithBLOBs selectByBossId(Long bossId);
}