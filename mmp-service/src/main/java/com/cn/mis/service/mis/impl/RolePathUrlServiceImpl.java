package com.cn.mis.service.mis.impl;

import com.cn.mis.dao.mis.RolePathUrlMapper;
import com.cn.mis.domain.entity.mis.RolePathUrl;
import com.cn.mis.service.mis.IRolePathUrlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/3/7.
 */
@Service("rolePathUrlService")
@Transactional
public class RolePathUrlServiceImpl implements IRolePathUrlService {
    @Resource
    private RolePathUrlMapper rolePathUrlMapper;

    @Override
    public List<RolePathUrl> getTopMenuByRole(int roleId) {
        return rolePathUrlMapper.getTopMenuByRole(roleId);
    }

    @Override
    public List<RolePathUrl> getChiledMenuByRole(int roleId, int pid) {
        return rolePathUrlMapper.getChiledMenuByRole(roleId,pid);
    }
}
