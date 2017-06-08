package com.cn.mis.web.controller;

import com.cn.mis.common.Common;
import com.cn.mis.domain.bean.TXEmailToken;
import com.cn.mis.domain.bean.req.TXEmailDeptReq;
import com.cn.mis.domain.bean.req.TXEmailUserReq;
import com.cn.mis.domain.bean.req.TXQueryDeptReq;
import com.cn.mis.domain.bean.resp.*;
import com.cn.mis.domain.entity.inface.*;
import com.cn.mis.service.inface.IIFHrmDepartmentService;
import com.cn.mis.service.inface.IIFHrmResourceService;
import com.cn.mis.utils.http.HttpClientUtil;
import com.cn.mis.utils.json.JsonUtil;
import com.google.common.reflect.TypeToken;
import com.qiniu.util.Json;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yuejia on 2017/4/18.
 */
@Controller
@RequestMapping("operate/txemail")
@Log4j
public class OperateTXEmailController {
    @Resource
    private IIFHrmResourceService iifHrmResourceService;

    @Resource
    private IIFHrmDepartmentService iifHrmDepartmentService;

    private TXEmailToken txEmailToken;

    @RequestMapping("getdeplistbyname/{name}")
    @ResponseBody
    private String getDepListByName(@PathVariable String name){
        TXQueryDeptReq req = new TXQueryDeptReq();
        req.setName(name);
        req.setFuzzy(0);
        String resp = HttpClientUtil.sendPostSSLRequest("https://api.exmail.qq.com/cgi-bin/department/search?access_token="+getToken().getAccess_token(),JsonUtil.toJson(req));
        TXQueryDeptResp rootResp = (TXQueryDeptResp) JsonUtil.fromJson(new TypeToken<TXQueryDeptResp>(){}.getType(),resp);
        return JsonUtil.toJson(rootResp);
    }

    @RequestMapping("getdeplistbyid/{id}")
    @ResponseBody
    private String getDepListByID(@PathVariable long id){
        String resp = HttpClientUtil.sendGetRequest("https://api.exmail.qq.com/cgi-bin/department/list?access_token="+getToken().getAccess_token()+"&id="+id,"UTF-8");
        TXQueryDeptResp rootResp = (TXQueryDeptResp) JsonUtil.fromJson(new TypeToken<TXQueryDeptResp>(){}.getType(),resp);
        return JsonUtil.toJson(rootResp);
    }

    @RequestMapping("initifdep")
    @ResponseBody
    private String initInfaceDep(){
        String resp = HttpClientUtil.sendGetRequest("https://api.exmail.qq.com/cgi-bin/department/list?access_token="+getToken().getAccess_token()+"&id=1","UTF-8");
        TXQueryDeptResp rootResp = (TXQueryDeptResp) JsonUtil.fromJson(new TypeToken<TXQueryDeptResp>(){}.getType(),resp);
        List<IFHrmDepartmentWithTX> rootlist = new ArrayList<>();
        for (TXDeptment txDeptment:rootResp.getDepartment()){
            IFHrmDepartmentWithTX ihd = new IFHrmDepartmentWithTX();
            ihd.setTxsyncflag(1);
            ihd.setDepartmentname(txDeptment.getName());
            ihd.setTxid(txDeptment.getId());
            rootlist.add(ihd);
        }
        int count = iifHrmDepartmentService.updateInitBatchTx(rootlist);

        for (TXDeptment txDeptment:rootResp.getDepartment()){
            String loop2resp = HttpClientUtil.sendGetRequest("https://api.exmail.qq.com/cgi-bin/department/list?access_token="+getToken().getAccess_token()+"&id="+txDeptment.getId(),"UTF-8");
            TXQueryDeptResp loop2Resp = (TXQueryDeptResp) JsonUtil.fromJson(new TypeToken<TXQueryDeptResp>(){}.getType(),loop2resp);
            List<IFHrmDepartmentWithTX> loop2list = new ArrayList<>();
            for (TXDeptment txDeptment2:loop2Resp.getDepartment()){
                IFHrmDepartmentWithTX hd2 = new IFHrmDepartmentWithTX();
                hd2.setTxsyncflag(1);
                hd2.setDepartmentname(txDeptment2.getName());
                hd2.setTxid(txDeptment2.getId());
                loop2list.add(hd2);
                String loop3resp = HttpClientUtil.sendGetRequest("https://api.exmail.qq.com/cgi-bin/department/list?access_token="+getToken().getAccess_token()+"&id="+txDeptment2.getId(),"UTF-8");
                TXQueryDeptResp loop3Resp = (TXQueryDeptResp) JsonUtil.fromJson(new TypeToken<TXQueryDeptResp>(){}.getType(),loop3resp);
                List<IFHrmDepartmentWithTX> loop3list = new ArrayList<>();
                for (TXDeptment txDeptment3:loop3Resp.getDepartment()){
                    IFHrmDepartmentWithTX hd3 = new IFHrmDepartmentWithTX();
                    hd3.setTxsyncflag(1);
                    hd3.setDepartmentname(txDeptment3.getName());
                    hd3.setTxid(txDeptment3.getId());
                    loop3list.add(hd3);
                    String loop4resp = HttpClientUtil.sendGetRequest("https://api.exmail.qq.com/cgi-bin/department/list?access_token="+getToken().getAccess_token()+"&id="+txDeptment3.getId(),"UTF-8");
                    TXQueryDeptResp loop4Resp = (TXQueryDeptResp) JsonUtil.fromJson(new TypeToken<TXQueryDeptResp>(){}.getType(),loop4resp);
                    List<IFHrmDepartmentWithTX> loop4list = new ArrayList<>();
                    for (TXDeptment txDeptment4:loop4Resp.getDepartment()){
                        IFHrmDepartmentWithTX hd4 = new IFHrmDepartmentWithTX();
                        hd4.setTxsyncflag(1);
                        hd4.setDepartmentname(txDeptment4.getName());
                        hd4.setTxid(txDeptment4.getId());
                        loop4list.add(hd4);
                    }
                    int countloop4 = iifHrmDepartmentService.updateInitBatchTx(loop4list);
                }
                int countloop3 = iifHrmDepartmentService.updateInitBatchTx(loop3list);
            }
            int countloop2 = iifHrmDepartmentService.updateInitBatchTx(loop2list);
        }
        return "";
    }

    @RequestMapping("initifuser")
    @ResponseBody
    private String initInfaceUser(){
        String resp = HttpClientUtil.sendGetRequest("https://api.exmail.qq.com/cgi-bin/user/simplelist?access_token="+getToken().getAccess_token()+"&department_id=1&fetch_child=1","UTF-8");
        TXQueryUserResp rootResp = (TXQueryUserResp) JsonUtil.fromJson(new TypeToken<TXQueryUserResp>(){}.getType(),resp);
        List<IFHrmResourceWithTX> rootlist = new ArrayList<>();
        for (int i=0;i<rootResp.getUserlist().size();i++){
            TXUser txUser = rootResp.getUserlist().get(i);
            IFHrmResourceWithTX ihr = new IFHrmResourceWithTX();
            ihr.setEmail(txUser.getUserid());
            ihr.setTxsyncflag(1);
            ihr.setTxid(txUser.getUserid());
            rootlist.add(ihr);
            if ((i+1)%40 == 0 || rootResp.getUserlist().size()-(i+1) == 0){
                int countroot = iifHrmResourceService.updateInitBatchTx(rootlist);
                rootlist.clear();
            }
        }
        return "";
    }

    @RequestMapping("syncdep")
    @ResponseBody
    private String syncDep(){
        //INFACE->IM
        List<IFHrmDepartmentWithTX> list = iifHrmDepartmentService.selectAllNeedInitTx();
        for (IFHrmDepartmentWithTX hd:list){
            TXEmailDeptReq req = new TXEmailDeptReq();
            req.setName(hd.getDepartmentname());
            req.setParentid(Long.valueOf(1));
            req.setOrder(hd.getShoworder());
            String resp = HttpClientUtil.sendPostSSLRequest("https://api.exmail.qq.com/cgi-bin/department/create?access_token="+getToken().getAccess_token(),JsonUtil.toJson(req));
            if (resp != null && !resp.equals("")){
                TXEmailDeptResp txEmailResp = (TXEmailDeptResp) JsonUtil.fromJson(new TypeToken<TXEmailDeptResp>(){}.getType(),resp);
                if (txEmailResp.getErrcode() == 0){
                    hd.setTxid(txEmailResp.getId());
                    hd.setTxcreatetime(new Date());
                    hd.setTxsyncflag(1);
                    iifHrmDepartmentService.updateSyncFlagTx(hd);
                }
                log.info("【INFACE->TX】新增【部门】:【"+ JsonUtil.toJson(resp)+"】");
            }
        }

        List<IFHrmDepartmentWithCustomTX> updates = iifHrmDepartmentService.selectAllNeedUpdateTx();
        for (IFHrmDepartmentWithCustomTX hd:updates){
            TXEmailDeptReq req = new TXEmailDeptReq();
            req.setId(hd.getTxid());
            req.setName(hd.getDepartmentname());
            req.setOrder(hd.getShoworder());
            req.setParentid(Long.valueOf(hd.getTxpid()==null?1:hd.getTxpid()));
            String resp = HttpClientUtil.sendPostSSLRequest("https://api.exmail.qq.com/cgi-bin/department/update?access_token="+getToken().getAccess_token(),JsonUtil.toJson(req));
            TXEmailDeptResp txEmailResp = (TXEmailDeptResp) JsonUtil.fromJson(new TypeToken<TXEmailDeptResp>(){}.getType(),resp);
            if (txEmailResp.getErrcode() == 0){
                hd.setTxsyncflag(2);
                hd.setTxmodifytime(new Date());
                iifHrmDepartmentService.updateSyncFlagTx(hd);
            }
            log.info("【INFACE->TX】更新【部门】:【"+ JsonUtil.toJson(resp)+"】");
        }
        return JsonUtil.toJson(updates);
    }

    @RequestMapping("syncuser")
    @ResponseBody
    private String syncUser(){
        List<IFHrmResourceWithCustomTX> list = iifHrmResourceService.selectAllNeedInitTx();
        for (IFHrmResourceWithCustomTX hr:list){
            TXEmailUserReq req = new TXEmailUserReq();
            req.setUserid(hr.getLoginid()+"@qding.me");
            req.setName(hr.getLastname());
            Long dept[] = {hr.getTxdid()==null?1:hr.getTxdid()};
            req.setDepartment(dept);
            req.setMobile(hr.getMobile());
            if ("0".equals(hr.getSex())){
                req.setGender("1");
            }else {
                req.setGender("2");
            }
            req.setPassword("qd@2014");
            req.setCpwd_login(0);
            String resp = HttpClientUtil.sendPostSSLRequest("https://api.exmail.qq.com/cgi-bin/user/create?access_token="+getToken().getAccess_token(),JsonUtil.toJson(req));
            if (resp != null && !resp.equals("")) {
                TXEmailUserResp txEmailUserResp = (TXEmailUserResp) JsonUtil.fromJson(new TypeToken<TXEmailUserResp>() {
                }.getType(), resp);
                if (txEmailUserResp.getErrcode() == 0) {
                    hr.setTxsyncflag(2);
                    hr.setTxcreatetime(new Date());
                    iifHrmResourceService.updateSyncFlagTx(hr);
                }
                log.info("【INFACE->TX】新增【成员】:【"+ JsonUtil.toJson(resp)+"】");
            }

        }

        List<IFHrmResourceWithCustomTX> updates = iifHrmResourceService.selectAllNeedUpdateTx();
        for (IFHrmResourceWithCustomTX hr:updates){
            TXEmailUserReq req = new TXEmailUserReq();
            req.setUserid(hr.getEmail());
            req.setName(hr.getLastname());
            Long dept[] = {hr.getTxdid()==null?1:hr.getTxdid()};
            req.setDepartment(dept);
            req.setMobile(hr.getMobile());
            if ("0".equals(hr.getSex())){
                req.setGender("1");
            }else {
                req.setGender("2");
            }
            req.setPassword("qd@2014");
            req.setCpwd_login(0);
            String resp = HttpClientUtil.sendPostSSLRequest("https://api.exmail.qq.com/cgi-bin/user/update?access_token="+getToken().getAccess_token(),JsonUtil.toJson(req));
            if (resp != null && !resp.equals("")) {
                TXEmailUserResp txEmailUserResp = (TXEmailUserResp) JsonUtil.fromJson(new TypeToken<TXEmailUserResp>() {
                }.getType(), resp);
                if (txEmailUserResp.getErrcode() == 0) {
                    hr.setTxsyncflag(2);
                    hr.setTxmodifytime(new Date());
                    iifHrmResourceService.updateSyncFlagTx(hr);
                }
                log.info("【INFACE->TX】更新【成员】:【"+ JsonUtil.toJson(resp)+"】");
            }
        }
        return JsonUtil.toJson(list);
    }
    @RequestMapping("/updateDep/{id}/{parentid}/{name}")
    @ResponseBody
    private String UpdateDep(@PathVariable long id,@PathVariable long parentid,@PathVariable String name){
        TXEmailDeptReq req = new TXEmailDeptReq();
        req.setId(id);
        req.setName(name);
        req.setParentid(parentid);
        String resp = HttpClientUtil.sendPostSSLRequest("https://api.exmail.qq.com/cgi-bin/department/update?access_token="+getToken().getAccess_token(),JsonUtil.toJson(req));
        TXEmailDeptResp txEmailResp = (TXEmailDeptResp) JsonUtil.fromJson(new TypeToken<TXEmailDeptResp>(){}.getType(),resp);
        log.info("【INFACE->TX】更新【部门】:【"+ JsonUtil.toJson(resp)+"】");
        return "【INFACE->TX】更新【部门】:【"+ JsonUtil.toJson(resp)+"】";
    }



    private TXEmailToken getToken(){
        if (txEmailToken == null){
            String token = HttpClientUtil.sendGetSSLRequest("https://api.exmail.qq.com/cgi-bin/gettoken?corpid="+Common.tx_corpid+"&corpsecret="+Common.tx_abcorpsecret,"UTF-8");
            txEmailToken = (TXEmailToken) JsonUtil.fromJson(new TypeToken<TXEmailToken>(){}.getType(),token);
        }
        return txEmailToken;
    }

}
