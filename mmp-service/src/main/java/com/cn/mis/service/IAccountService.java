package com.cn.mis.service;

import com.cn.mis.domain.entity.Account;
import com.cn.mis.domain.entity.AccountWithBLOBs;

import java.util.List;


public interface IAccountService {
	public int deleteByPrimaryKey(Integer id);

    public int insert(Account record);

    public Account selectByPrimaryKey(Integer id);
    
    List<AccountWithBLOBs> selectBySql();

    public int updateByPrimaryKeySelective(Account record);
    
    int insertBatch(List<Account> list);
    
    int deleteBatch(List<Integer> list);
    
    int updateBatch(List<Account> list);
    
    int deleteAll();
    
    List<Account> selectByIds(List<Integer> list);
}
