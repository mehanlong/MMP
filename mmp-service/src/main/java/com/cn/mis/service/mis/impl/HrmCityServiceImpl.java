package com.cn.mis.service.mis.impl;

import com.cn.mis.dao.mis.HrmCityMapper;
import com.cn.mis.domain.entity.mis.HrmCity;
import com.cn.mis.service.mis.IHrmCityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/6/1.
 */
@Service("hrmCityService")
public class HrmCityServiceImpl implements IHrmCityService {
    @Resource
    HrmCityMapper hrmCityMapper;

    @Override
    public int insert(HrmCity record) {
        return hrmCityMapper.insert(record);
    }

    @Override
    public int insertSelective(HrmCity record) {
        return hrmCityMapper.insertSelective(record);
    }

    @Override
    public List<HrmCity> selectByName(String name) {
        return hrmCityMapper.selectByName(name);
    }
}
