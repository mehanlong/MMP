package com.cn.mis.service;

import com.cn.mis.domain.entity.RolePathUrl;

import java.util.List;

/**
 * Created by yuejia on 2017/3/7.
 */
public interface IRolePathUrlService {
    List<RolePathUrl> getTopMenuByRole(int roleId);
    List<RolePathUrl> getChiledMenuByRole(int roleId, int pid);
}
