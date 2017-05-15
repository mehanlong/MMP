package com.cn.mis.service.mis.ajax.impl;

import com.cn.mis.dao.mis.FM144Mapper;
import com.cn.mis.domain.entity.mis.FM144WithBLOBs;
import com.cn.mis.service.mis.ajax.IFM144Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/4/26.
 */
@Service("fm144Service")
public class FM144ServiceImpl implements IFM144Service {
    @Resource
    FM144Mapper fm144Mapper;

    @Override
    public List<FM144WithBLOBs> selectByParames(FM144WithBLOBs record) {
        return fm144Mapper.selectByParames(record);
    }
}
