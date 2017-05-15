package com.cn.mis.service.mis.ajax;

import com.cn.mis.domain.entity.mis.FM146WithBLOBs;

import java.util.List;

/**
 * Created by yuejia on 2017/4/26.
 */
public interface IFM146Service {

    List<FM146WithBLOBs> selectByParames(FM146WithBLOBs record);
}
