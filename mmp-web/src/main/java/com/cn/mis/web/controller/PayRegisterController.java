package com.cn.mis.web.controller;

import com.cn.mis.domain.bean.pojo.SubmitStatus;
import com.cn.mis.domain.bean.req.PayRegisterReq;
import com.cn.mis.domain.bean.resp.BaseResult;
import com.cn.mis.domain.bean.resp.ListResult;
import com.cn.mis.domain.entity.mis.PayRegister;
import com.cn.mis.service.mis.IPayRegisterService;
import com.cn.mis.utils.date.DateStyle;
import com.cn.mis.utils.date.DateUtil;
import com.cn.mis.utils.json.JsonUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yuejia on 2017/3/6.
 */
@Controller
@Log4j
@RequestMapping("/payregister")
public class PayRegisterController {
    @Resource
    private IPayRegisterService iPayRegisterService;

    @RequestMapping("/selectDailyContract")
    @ResponseBody
    private String selectPayContract(){
        return "/jsp/dailycontract.jsp";
    }

    @RequestMapping("/selectPropertyContract")
    @ResponseBody
    private String selectPropertyContract() {
        return "/jsp/propertycontract.jsp";
    }

    @RequestMapping("/selectDailySubmit")
    @ResponseBody
    private String selectDailySubmit(){
        return "/jsp/dailysubmit.jsp";
    }

    @RequestMapping("selectReceiveLoan")
    @ResponseBody
    private String selectReceiveLoan(){
        return "/jsp/receiveloan.jsp";
    }

    @RequestMapping("index")
    private String toindex(){
        return "/index.jsp";
    }

    @RequestMapping("/selectSubmitStatus")
    @ResponseBody
    private Object selectSubmitStatus(){
        List<SubmitStatus> list = iPayRegisterService.selectSubmitStatus();
        return BaseResult.ok("success",list);
    }
    @RequestMapping("/queryContract/{type}/{date}")
    @ResponseBody
    private Object queryContract(@PathVariable int type,HttpServletRequest req, @RequestParam("page")int page, @RequestParam("rows")int rows) {
        PayRegisterReq registerReq = new PayRegisterReq();
        String status = req.getParameter("status");
        if (type == 0){
            String payment_code = req.getParameter("payment_code");
            if (StringUtil.isNotEmpty(payment_code)) {
                registerReq.setPayment_code(payment_code);
            }
        }
        if (type == 1){
            String code = req.getParameter("code");
            if (StringUtil.isNotEmpty(code)) {
                registerReq.setCode(code);
            }
        }
        String requestname = req.getParameter("requestname");
        String lastname = req.getParameter("lastname");
        String departmentname = req.getParameter("departmentname");
        String startdate = req.getParameter("startdate");
        String enddate = req.getParameter("enddate");
        String vendor = req.getParameter("vendor");

        if (StringUtil.isNotEmpty(status)) {
            registerReq.setStatus(Integer.valueOf(status));
        }

        if (StringUtil.isNotEmpty(requestname)) {
            registerReq.setRequestname(requestname);
        }
        if (StringUtil.isNotEmpty(lastname)) {
            registerReq.setLastname(lastname);
        }
        if (StringUtil.isNotEmpty(departmentname)) {
            registerReq.setDepartmentname(departmentname);
        }
        if (StringUtil.isNotEmpty(startdate)) {
            registerReq.setStartdate(startdate);
        }
        if (StringUtil.isNotEmpty(enddate)) {
            registerReq.setEnddate(enddate);
        }
        if (StringUtil.isNotEmpty(vendor)){
            registerReq.setVendor(vendor);
        }
        PageInfo<PayRegister> payRegisterPageInfo = null;
        switch (type){
            case 0:
                payRegisterPageInfo = iPayRegisterService.selectDailyContractByPage(registerReq, page, rows);
                break;
            case 1:
                payRegisterPageInfo = iPayRegisterService.selectPropertyContractByPage(registerReq,page,rows);
                break;
        }


        ListResult listResult = new ListResult();
        listResult.setRows(payRegisterPageInfo.getList());
        listResult.setTotal(payRegisterPageInfo.getTotal());
        String json = JsonUtil.toJson(listResult);

        return json;
    }

    @RequestMapping("/queryDailySubmit/{date}")
    @ResponseBody
    private Object queryDailySubmit(HttpServletRequest req, @RequestParam("page")int page, @RequestParam("rows")int rows){
        PayRegisterReq registerReq = new PayRegisterReq();
        String status = req.getParameter("status");
        String code = req.getParameter("code");
        String requestname = req.getParameter("requestname");
        String lastname = req.getParameter("lastname");
        String departmentname = req.getParameter("departmentname");
        String startdate = req.getParameter("startdate");
        String enddate = req.getParameter("enddate");
        String vendor = req.getParameter("vendor");

        if (StringUtil.isNotEmpty(status)){
            registerReq.setStatus(Integer.valueOf(status));
        }
        if (StringUtil.isNotEmpty(code)){
            registerReq.setCode(code);
        }
        if (StringUtil.isNotEmpty(requestname)){
            registerReq.setRequestname(requestname);
        }
        if (StringUtil.isNotEmpty(lastname)){
            registerReq.setLastname(lastname);
        }
        if (StringUtil.isNotEmpty(departmentname)){
            registerReq.setDepartmentname(departmentname);
        }
        if (StringUtil.isNotEmpty(startdate)){
            registerReq.setStartdate(startdate);
        }
        if (StringUtil.isNotEmpty(enddate)){
            registerReq.setEnddate(enddate);
        }
        if (StringUtil.isNotEmpty(vendor)){
            registerReq.setVendor(vendor);
        }
        PageInfo<PayRegister> payRegisterPageInfo = iPayRegisterService.selectDailySubmitByPage(registerReq,page,rows);

        ListResult listResult = new ListResult();
        listResult.setRows(payRegisterPageInfo.getList());
        listResult.setTotal(payRegisterPageInfo.getTotal());
        String json = JsonUtil.toJson(listResult);

        return json;
    }

    @RequestMapping("/queryReceiveLoan/{date}")
    @ResponseBody
    private Object queryReceiveLoan(HttpServletRequest req, @RequestParam("page")int page, @RequestParam("rows")int rows){
        PayRegisterReq registerReq = new PayRegisterReq();
        String status = req.getParameter("status");
        String code = req.getParameter("code");
        String requestname = req.getParameter("requestname");
        String lastname = req.getParameter("lastname");
        String departmentname = req.getParameter("departmentname");
        String startdate = req.getParameter("startdate");
        String enddate = req.getParameter("enddate");
        String vendor = req.getParameter("vendor");

        if (StringUtil.isNotEmpty(status)){
            registerReq.setStatus(Integer.valueOf(status));
        }
        if (StringUtil.isNotEmpty(code)){
            registerReq.setCode(code);
        }
        if (StringUtil.isNotEmpty(requestname)){
            registerReq.setRequestname(requestname);
        }
        if (StringUtil.isNotEmpty(lastname)){
            registerReq.setLastname(lastname);
        }
        if (StringUtil.isNotEmpty(departmentname)){
            registerReq.setDepartmentname(departmentname);
        }
        if (StringUtil.isNotEmpty(startdate)){
            registerReq.setStartdate(startdate);
        }
        if (StringUtil.isNotEmpty(enddate)){
            registerReq.setEnddate(enddate);
        }
        if (StringUtil.isNotEmpty(vendor)){
            registerReq.setVendor(vendor);
        }
        PageInfo<PayRegister> payRegisterPageInfo = iPayRegisterService.selectReceiveLoanByPage(registerReq,page,rows);

        ListResult listResult = new ListResult();
        listResult.setRows(payRegisterPageInfo.getList());
        listResult.setTotal(payRegisterPageInfo.getTotal());
        String json = JsonUtil.toJson(listResult);

        return json;
    }

    @RequestMapping("getids")
    @ResponseBody
    private Object getids(HttpServletRequest req){
        if (StringUtil.isNotEmpty(req.getParameter("ids"))){
            String ids = req.getParameter("ids");
            String[] vendors = req.getParameter("vendors").split(",");
            for ( int i =1 ; i<vendors.length ; i++) {
                if ( !vendors[0].equals( vendors[ i ]) ) {
                    return BaseResult.fail("请选择同一个付款单位");
                }
            }
            return BaseResult.ok("接受到参数",ids);
        } else {
            return BaseResult.fail("请选择至少1个报销单");
        }
    }

    @RequestMapping("/changebank/{type}/{date}")
    @ResponseBody
    private Object changebank(HttpServletRequest req, @PathVariable int type){
        String[] ids = req.getParameter("ids").split(",");
        String bank_id = req.getParameter("bankid");
        String account = req.getParameter("account");
        String bank = req.getParameter("bank");
        List<Integer> list = new ArrayList<>();
        for (int i=0;i<ids.length;i++){
            list.add(Integer.valueOf(ids[i]));
        }
        switch (type){
            case 0:
                iPayRegisterService.updateDCPayerAccountBatch(bank,bank_id, DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD),account,list);
                break;
            case 1:
                iPayRegisterService.updatePCPayerAccountBatch(bank,bank_id, DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD),account,list);
                break;
            case 2:
                iPayRegisterService.updateDSPayerAccountBatch(bank,bank_id, DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD),account,list);
                break;
            case 3:
                iPayRegisterService.updateRLPayerAccountBatch(bank,bank_id, DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD),account,list);
                break;
        }
        return BaseResult.ok("更新成功");
    }

    @RequestMapping("/queryBankOption/{date}")
    @ResponseBody
    private Object queryBankOption(HttpServletRequest req, @RequestParam("page")int page, @RequestParam("rows")int rows){
        PayRegisterReq registerReq = new PayRegisterReq();
        String account = req.getParameter("account");
        String bank = req.getParameter("bank");
        String vendor = req.getParameter("vendor");

        if (StringUtil.isNotEmpty(account)){
            registerReq.setAccount(account);
        }
        if (StringUtil.isNotEmpty(bank)){
            registerReq.setBank(bank);
        }
        if (StringUtil.isNotEmpty(vendor)){
            registerReq.setVendor(vendor);
        }
        PageInfo<PayRegister> payRegisterPageInfo = iPayRegisterService.selectAllBank(registerReq,page,rows);

        ListResult listResult = new ListResult();
        listResult.setRows(payRegisterPageInfo.getList());
        listResult.setTotal(payRegisterPageInfo.getTotal());
        String json = JsonUtil.toJson(listResult);

        return json;
    }


}
