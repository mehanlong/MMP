package com.cn.mis.service.mis.impl;

import com.cn.mis.dao.mis.QIPaymentMapper;
import com.cn.mis.domain.entity.mis.QIPayment;
import com.cn.mis.service.mis.IQIPaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/3/29.
 */
@Service("qiPaymentService")
public class QIPaymentServiceImpl implements IQIPaymentService {
    @Resource
    private QIPaymentMapper qiPaymentMapper;

    @Override
    public int deleteByPrimaryKey(Integer requestid) {
        return qiPaymentMapper.deleteByPrimaryKey(requestid);
    }

    @Override
    public int insert(QIPayment record) {
        return qiPaymentMapper.insert(record);
    }

    @Override
    public int insertSelective(QIPayment record) {
        return qiPaymentMapper.insertSelective(record);
    }

    @Override
    public QIPayment selectByPrimaryKey(Integer requestid) {
        return qiPaymentMapper.selectByPrimaryKey(requestid);
    }

    @Override
    public List<QIPayment> selectAllNeedSend() {
        return qiPaymentMapper.selectAllNeedSend();
    }

    @Override
    public int updateByPrimaryKeySelective(QIPayment record) {
        return qiPaymentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(QIPayment record) {
        return qiPaymentMapper.updateByPrimaryKey(record);
    }
}
