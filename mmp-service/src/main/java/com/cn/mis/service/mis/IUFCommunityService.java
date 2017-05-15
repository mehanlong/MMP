package com.cn.mis.service.mis;


import com.cn.mis.domain.entity.mis.UFCommunity;
import com.cn.mis.domain.entity.mis.UFCommunityWithBLOBs;

public interface IUFCommunityService {
	int deleteByPrimaryKey(Integer id);

    int insert(UFCommunityWithBLOBs record);

    int insertSelective(UFCommunityWithBLOBs record);

    UFCommunityWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UFCommunityWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UFCommunityWithBLOBs record);

    int updateByPrimaryKey(UFCommunity record);
    
    UFCommunityWithBLOBs selectByBossId(Long bossId);
}
