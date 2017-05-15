package com.cn.mis.service.mis.impl;

import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.dao.mis.AccountMapper;
import com.cn.mis.domain.entity.mis.Account;
import com.cn.mis.domain.entity.mis.AccountWithBLOBs;
import com.cn.mis.service.mis.IAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("accountService")
@Transactional
public class AccountServiceImpl implements IAccountService {
	
	@Resource
	private AccountMapper accountMapper;

	public int deleteByPrimaryKey(Integer id) {
		return accountMapper.deleteByPrimaryKey(id);
	}

	public int insert(Account record) {
		return accountMapper.insert(record);
	}

	public Account selectByPrimaryKey(Integer id) {
		return accountMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(Account record) {
		return accountMapper.updateByPrimaryKeySelective(record);
	}

	public int insertBatch(List<Account> list) {
		return accountMapper.insertBatch(list);
	}

	public int deleteBatch(List<Integer> list) {
		return accountMapper.deleteBatch(list);
	}

	public int updateBatch(List<Account> list) {
		return accountMapper.updateBatch(list);
	}

	public int deleteAll() {
		return accountMapper.deleteAll();
	}

	public List<Account> selectByIds(List<Integer> list) {
		return accountMapper.selectByIds(list);
	}

	@Override
	public List<AccountWithBLOBs> selectBySql() {
		return accountMapper.selectBySql();
	}
	
	
}
