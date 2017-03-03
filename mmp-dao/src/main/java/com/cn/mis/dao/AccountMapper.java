package com.cn.mis.dao;

import com.cn.mis.domain.entity.Account;
import com.cn.mis.domain.entity.AccountWithBLOBs;

import java.util.List;


public interface AccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    Account selectByPrimaryKey(Integer id);
    
    List<AccountWithBLOBs> selectBySql();

    int updateByPrimaryKeySelective(Account record);
    
    int insertBatch(List<Account> list);
    
    int updateBatch(List<Account> list);
    
    int deleteBatch(List<Integer> list);
    
    int deleteAll();
    
    List<Account> selectByIds(List<Integer> list);
}