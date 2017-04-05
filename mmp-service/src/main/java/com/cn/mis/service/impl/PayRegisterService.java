package com.cn.mis.service.impl;

import com.cn.mis.dao.PayRegisterMapper;
import com.cn.mis.domain.bean.pojo.SubmitStatus;
import com.cn.mis.domain.bean.req.PayRegisterReq;
import com.cn.mis.domain.entity.PayRegister;
import com.cn.mis.service.IPayRegisterService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/3/9.
 */
@Service("payRegisterService")
public class PayRegisterService implements IPayRegisterService {
    @Resource
    private PayRegisterMapper payRegisterMapper;

    @Override
    public PageInfo<PayRegister> selectDailyContractByPage(PayRegisterReq req, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<PayRegister> list = payRegisterMapper.selectDailyContractByPage(req);
        PageInfo<PayRegister> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<PayRegister> selectPropertyContractByPage(PayRegisterReq req, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<PayRegister> list = payRegisterMapper.selectPropertyContractByPage(req);
        PageInfo<PayRegister> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<PayRegister> selectDailySubmitByPage(PayRegisterReq req, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<PayRegister> list = payRegisterMapper.selectDailySubmitByPage(req);
        PageInfo<PayRegister> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<PayRegister> selectReceiveLoanByPage(PayRegisterReq req, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<PayRegister> list = payRegisterMapper.selectReceiveLoanByPage(req);
        PageInfo<PayRegister> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<PayRegister> selectAllBank(PayRegisterReq req, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<PayRegister> list = payRegisterMapper.selectAllBank(req);
        PageInfo<PayRegister> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int updateDCPayerAccountBatch(String payer_bank, String payer_account, String pay_date,String payer_bank_account, List<Integer> list) {
        return payRegisterMapper.updateDCPayerAccountBatch(payer_bank,payer_account,pay_date,payer_bank_account,list);
    }

    @Override
    public int updatePCPayerAccountBatch(String payer_bank, String payer_account, String pay_date,String payer_bank_account, List<Integer> list) {
        return payRegisterMapper.updatePCPayerAccountBatch(payer_bank,payer_account,pay_date,payer_bank_account,list);
    }

    @Override
    public int updateDSPayerAccountBatch(String payer_bank, String payer_account, String pay_date,String payer_bank_account, List<Integer> list) {
        return payRegisterMapper.updateDSPayerAccountBatch(payer_bank,payer_account,pay_date,payer_bank_account,list);
    }

    @Override
    public int updateRLPayerAccountBatch(String payer_bank, String payer_account, String pay_date,String payer_bank_account, List<Integer> list) {
        return payRegisterMapper.updateRLPayerAccountBatch(payer_bank,payer_account,pay_date,payer_bank_account,list);
    }

    @Override
    public List<SubmitStatus> selectSubmitStatus() {
        return payRegisterMapper.selectSubmitStatus();
    }
}
