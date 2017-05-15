package com.cn.mis.dao.mis;

import com.cn.mis.domain.bean.pojo.SubmitStatus;
import com.cn.mis.domain.bean.req.PayRegisterReq;
import com.cn.mis.domain.entity.mis.PayRegister;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yuejia on 2017/3/9.
 */
public interface PayRegisterMapper {
    List<PayRegister> selectDailySubmitByPage(PayRegisterReq req);

    List<PayRegister> selectDailyContractByPage(PayRegisterReq req);

    List<PayRegister> selectPropertyContractByPage(PayRegisterReq req);

    List<PayRegister> selectReceiveLoanByPage(PayRegisterReq req);

    List<PayRegister> selectAllBank(PayRegisterReq req);

    int updateDCPayerAccountBatch(@Param("payer_bank") String payer_bank,@Param("payer_account") String payer_account,@Param("pay_date") String pay_date,@Param("payer_bank_account") String payer_bank_account,@Param("list") List<Integer> list);
    int updatePCPayerAccountBatch(@Param("payer_bank") String payer_bank,@Param("payer_account") String payer_account,@Param("pay_date") String pay_date,@Param("payer_bank_account") String payer_bank_account,@Param("list") List<Integer> list);
    int updateDSPayerAccountBatch(@Param("payer_bank") String payer_bank,@Param("payer_account") String payer_account,@Param("pay_date") String pay_date,@Param("payer_bank_account") String payer_bank_account,@Param("list") List<Integer> list);
    int updateRLPayerAccountBatch(@Param("payer_bank") String payer_bank,@Param("payer_account") String payer_account,@Param("pay_date") String pay_date,@Param("payer_bank_account") String payer_bank_account,@Param("list") List<Integer> list);

    List<SubmitStatus> selectSubmitStatus();
}
