package com.cn.mis.service;

import com.cn.mis.domain.entity.FM24WithBLOBs;

import java.util.List;

/**
 * Created by yuejia on 2017/3/9.
 */
public interface IFM24Service {
    List<FM24WithBLOBs> selectAll();

    int updateByPrimaryKeySelective(FM24WithBLOBs record);
}
