package com.cn.mis.service.mis.ajax;

import com.cn.mis.domain.entity.mis.UFPermanaWithBLOBs;

import java.util.List;

/**
 * Created by yuejia on 2017/4/26.
 */
public interface IUFPermanaService {

    List<UFPermanaWithBLOBs> selectByParames(UFPermanaWithBLOBs record);
}
