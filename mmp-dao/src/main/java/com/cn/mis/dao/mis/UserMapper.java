package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.User;

import java.util.List;


public interface UserMapper {

    int insert(User record);

    User selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(User record);
    
    int deleteByPrimaryKey(Integer id);
    
    List<User> selectAll();
    
    int insertBatch(List<User> list);
    
    int updateBatch(List<User> list);
    
    int deleteBatch(List<Integer> list);
    
    List<User> selectByIds(List<Integer> list);
    
}