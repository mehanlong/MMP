package com.cn.mis.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.dao.XCEmailMapper;
import com.cn.mis.domain.entity.XCEmail;
import com.cn.mis.service.IXCEmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("xcEmailService")
@Transactional
public class XCEmailServiceImpl implements IXCEmailService {
	
	@Resource
	private XCEmailMapper xcEmailMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return xcEmailMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(XCEmail record) {
		return xcEmailMapper.insert(record);
	}

	@Override
	public int insertSelective(XCEmail record) {
		return xcEmailMapper.insertSelective(record);
	}

	@Override
	public XCEmail selectByPrimaryKey(Integer id) {
		return xcEmailMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<XCEmail> selectAll() {
		return xcEmailMapper.selectAll();
	}

	@Override
	public int updateByPrimaryKeySelective(XCEmail record) {
		return xcEmailMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(XCEmail record) {
		return xcEmailMapper.updateByPrimaryKey(record);
	}

}
