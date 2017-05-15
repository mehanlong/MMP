package com.cn.mis.service.mis.ajax.impl;

import com.cn.mis.dao.mis.UFPermanaMapper;
import com.cn.mis.domain.entity.mis.UFPermanaWithBLOBs;
import com.cn.mis.service.mis.ajax.IUFPermanaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/4/26.
 */
@Service("ufPermanaService")
public class UFPermanaServiceImpl implements IUFPermanaService {
    @Resource
    UFPermanaMapper ufPermanaMapper;

    @Override
    public List<UFPermanaWithBLOBs> selectByParames(UFPermanaWithBLOBs record) {
        return ufPermanaMapper.selectByParames(record);
    }
}
