package com.cn.mis.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.dao.UserMapper;
import com.cn.mis.domain.entity.User;
import com.cn.mis.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {
	
	@Resource
	private UserMapper userMapper;

	public User getUserById(int userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	public int insert(User user) {
		return userMapper.insert(user);
	}

	public int updateByPrimaryKeySelective(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

	public int deleteByPrimaryKey(int id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	public int insertBatch(List<User> list) {
		return userMapper.insertBatch(list);
	}

	public int updateBatch(List<User> list) {
		return userMapper.updateBatch(list);
	}

	public int deleteBatch(List<Integer> list) {
		return userMapper.deleteBatch(list);
	}

	public List<User> selectAll() {
		return userMapper.selectAll();
	}

	public List<User> selectByIds(List<Integer> list) {
		return userMapper.selectByIds(list);
	}
}
