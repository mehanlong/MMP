package com.cn.mis.service.mis;

import com.cn.mis.domain.entity.mis.HrmCity;

import java.util.List;

/**
 * Created by yuejia on 2017/6/1.
 */
public interface IHrmCityService {
    int insert(HrmCity record);

    int insertSelective(HrmCity record);

    List<HrmCity> selectByName(String name);
}
