package com.cn.mis.service.mis;

import com.cn.mis.domain.entity.mis.FM24DT1;

import java.util.List;

/**
 * Created by yuejia on 2017/4/12.
 */
public interface IFM24DT1Service {
    int deleteByPrimaryKey(Integer id);

    int insert(FM24DT1 record);

    int insertSelective(FM24DT1 record);

    FM24DT1 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FM24DT1 record);

    int updateByPrimaryKeyWithBLOBs(FM24DT1 record);

    int updateByPrimaryKey(FM24DT1 record);

    List<FM24DT1> selectAll();
}
