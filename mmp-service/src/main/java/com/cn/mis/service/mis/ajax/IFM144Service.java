package com.cn.mis.service.mis.ajax;

import com.cn.mis.domain.entity.mis.FM144WithBLOBs;

import java.util.List;

/**
 * Created by yuejia on 2017/4/26.
 */
public interface IFM144Service {

    List<FM144WithBLOBs> selectByParames(FM144WithBLOBs record);
}
