package com.cn.mis.service.im;

import com.cn.mis.domain.entity.im.IMUser;
import com.cn.mis.domain.entity.im.IMUserKey;

import java.util.List;

/**
 * Created by yuejia on 2017/4/5.
 */
public interface IIMUserService {
    int deleteByPrimaryKey(IMUserKey key);

    int insert(IMUser record);

    int insertSelective(IMUser record);

    IMUser selectByPrimaryKey(IMUserKey key);

    int updateByPrimaryKeySelective(IMUser record);

    int updateByPrimaryKey(IMUser record);

    List<IMUser> selectAll();
}
