package com.cn.mis.dao.im;

import com.cn.mis.domain.entity.im.IMDeptuser;
import com.cn.mis.domain.entity.im.IMDeptuserKey;

public interface IMDeptuserMapper {
    int deleteByPrimaryKey(IMDeptuserKey key);

    int insert(IMDeptuser record);

    int insertSelective(IMDeptuser record);

    IMDeptuser selectByPrimaryKey(IMDeptuserKey key);

    int updateByPrimaryKeySelective(IMDeptuser record);

    int updateByPrimaryKey(IMDeptuser record);
}