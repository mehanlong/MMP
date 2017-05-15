package com.cn.mis.service.im;

import com.cn.mis.domain.entity.im.IMCompdept;

/**
 * Created by yuejia on 2017/4/7.
 */
public interface IIMCompdeptService {
    int deleteByPrimaryKey(Integer id);

    int insert(IMCompdept record);

    int insertSelective(IMCompdept record);

    IMCompdept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IMCompdept record);

    int updateByPrimaryKey(IMCompdept record);
}
