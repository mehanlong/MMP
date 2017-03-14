package com.cn.mis.service;

import com.cn.mis.domain.bean.pojo.SubmitStatus;
import com.cn.mis.domain.bean.req.PayRegisterReq;
import com.cn.mis.domain.entity.PayRegister;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yuejia on 2017/3/9.
 */
public interface IPayRegisterService {

    PageInfo<PayRegister> selectDailyContractByPage(PayRegisterReq req,int pageNum,int pageSize);

    PageInfo<PayRegister> selectPropertyContractByPage(PayRegisterReq req,int pageNum,int pageSize);

    PageInfo<PayRegister> selectDailySubmitByPage(PayRegisterReq req,int pageNum,int pageSize);

    PageInfo<PayRegister> selectReceiveLoanByPage(PayRegisterReq req,int pageNum,int pageSize);

    PageInfo<PayRegister> selectAllBank(PayRegisterReq req,int pageNum,int pageSize);

    int updateDCPayerAccountBatch(String payer_bank,String payer_account, String pay_date, List<Integer> list);
    int updatePCPayerAccountBatch(String payer_bank,String payer_account, String pay_date, List<Integer> list);
    int updateDSPayerAccountBatch(String payer_bank,String payer_account, String pay_date, List<Integer> list);
    int updateRLPayerAccountBatch(String payer_bank,String payer_account, String pay_date, List<Integer> list);

    List<SubmitStatus> selectSubmitStatus();
}
