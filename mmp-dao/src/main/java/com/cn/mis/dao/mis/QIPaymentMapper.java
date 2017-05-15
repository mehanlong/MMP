package com.cn.mis.dao.mis;

import com.cn.mis.domain.entity.mis.QIPayment;

import java.util.List;

public interface QIPaymentMapper {
    int deleteByPrimaryKey(Integer requestid);

    int insert(QIPayment record);

    int insertSelective(QIPayment record);

    QIPayment selectByPrimaryKey(Integer requestid);

    List<QIPayment> selectAllNeedSend();

    int updateByPrimaryKeySelective(QIPayment record);

    int updateByPrimaryKey(QIPayment record);
}