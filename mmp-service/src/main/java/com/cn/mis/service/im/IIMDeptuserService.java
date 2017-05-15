package com.cn.mis.service.im;

import com.cn.mis.domain.entity.im.IMDeptuser;
import com.cn.mis.domain.entity.im.IMDeptuserKey;

/**
 * Created by yuejia on 2017/4/11.
 */
public interface IIMDeptuserService {
    int deleteByPrimaryKey(IMDeptuserKey key);

    int insert(IMDeptuser record);

    int insertSelective(IMDeptuser record);

    IMDeptuser selectByPrimaryKey(IMDeptuserKey key);

    int updateByPrimaryKeySelective(IMDeptuser record);

    int updateByPrimaryKey(IMDeptuser record);
}
