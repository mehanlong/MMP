package com.cn.mis.dao.im;

import com.cn.mis.domain.entity.im.IMCompdept;

public interface IMCompdeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IMCompdept record);

    int insertSelective(IMCompdept record);

    IMCompdept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IMCompdept record);

    int updateByPrimaryKey(IMCompdept record);
}