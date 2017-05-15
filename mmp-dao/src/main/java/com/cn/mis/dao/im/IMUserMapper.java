package com.cn.mis.dao.im;

import com.cn.mis.domain.entity.im.IMUser;
import com.cn.mis.domain.entity.im.IMUserKey;

import java.util.List;

public interface IMUserMapper {
    int deleteByPrimaryKey(IMUserKey key);

    int insert(IMUser record);

    int insertSelective(IMUser record);

    IMUser selectByPrimaryKey(IMUserKey key);

    int updateByPrimaryKeySelective(IMUser record);

    int updateByPrimaryKey(IMUser record);

    List<IMUser> selectAll();
}