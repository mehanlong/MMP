package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.RolePathUrl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yuejia on 2017/3/7.
 */
public interface RolePathUrlMapper {
    List<RolePathUrl> getTopMenuByRole(@Param("roleId") int roleId);
    List<RolePathUrl> getChiledMenuByRole(@Param("roleId") int roleId,@Param("pid") int pid);
}
