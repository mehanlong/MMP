package com.cn.mis.service;

import com.cn.mis.domain.entity.QIPayment;

import java.util.List;

/**
 * Created by yuejia on 2017/3/29.
 */
public interface IQIPaymentService {
    int deleteByPrimaryKey(Integer requestid);

    int insert(QIPayment record);

    int insertSelective(QIPayment record);

    QIPayment selectByPrimaryKey(Integer requestid);

    List<QIPayment> selectAllNeedSend();

    int updateByPrimaryKeySelective(QIPayment record);

    int updateByPrimaryKey(QIPayment record);
}
