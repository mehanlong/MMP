package com.cn.mis.service;

import com.cn.mis.domain.entity.DTThree;

import java.util.ArrayList;

/**
 * Created by yuejia on 2017/2/21.
 */
public interface IDTThreeService {
    int deleteByPrimaryKey(Integer id);

    int insert(DTThree record);

    int insertSelective(DTThree record);

    DTThree selectByPrimaryKey(Integer id);

    ArrayList<DTThree> selectBySql();

    int updateByPrimaryKeySelective(DTThree record);

    int updateByPrimaryKey(DTThree record);
}
