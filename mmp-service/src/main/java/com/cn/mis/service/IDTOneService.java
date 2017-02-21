package com.cn.mis.service;

import com.cn.mis.domain.entity.DTOne;
import com.cn.mis.domain.entity.DTOneWithBLOBs;

import java.util.ArrayList;

/**
 * Created by yuejia on 2017/2/21.
 */
public interface IDTOneService {
    int deleteByPrimaryKey(Integer id);

    int insert(DTOneWithBLOBs record);

    int insertSelective(DTOneWithBLOBs record);

    DTOneWithBLOBs selectByPrimaryKey(Integer id);

    ArrayList<DTOneWithBLOBs> selectAll();

    int updateByPrimaryKeySelective(DTOneWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(DTOneWithBLOBs record);

    int updateByPrimaryKey(DTOne record);
}
