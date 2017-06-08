package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.UFCategory;

import java.util.List;

public interface UFCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UFCategory record);

    int insertSelective(UFCategory record);

    UFCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UFCategory record);

    int updateByPrimaryKey(UFCategory record);

    List<UFCategory> selectAll();

    int insertBatch(List<UFCategory> list);

    int updateBatch(List<UFCategory> list);
}