package com.cn.mis.service.mis;

import com.cn.mis.domain.entity.mis.UFCategory;

import java.util.List;

/**
 * Created by yuejia on 2017/5/24.
 */
public interface IUFCategoryService {
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
