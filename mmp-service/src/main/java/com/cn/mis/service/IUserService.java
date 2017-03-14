package com.cn.mis.service;

import com.cn.mis.domain.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IUserService {
	public User getUserById(int userId);
	public int insert(User user);
	public int updateByPrimaryKeySelective(User user);
	public int deleteByPrimaryKey(int id);
	public int insertBatch(List<User> list);
	public int updateBatch(List<User> list);
	public int deleteBatch(List<Integer> list);
	public List<User> selectAll();
	public List<User> selectByIds(List<Integer> list);
	public PageInfo<User> selectByPage(int pageNum, int pageSize);
}
