package com.cn.mis.web.controller;

import com.cn.mis.domain.bean.resp.IMSyncDepResp;
import com.cn.mis.domain.entity.im.IMCompdept;
import com.cn.mis.domain.entity.im.IMDeptuser;
import com.cn.mis.domain.entity.im.IMUser;
import com.cn.mis.domain.entity.inface.*;
import com.cn.mis.service.im.IIMDeptuserService;
import com.cn.mis.service.inface.IIFHrmDepartmentService;
import com.cn.mis.service.im.IIMCompdeptService;
import com.cn.mis.service.im.IIMUserService;
import com.cn.mis.service.inface.IIFHrmResourceService;
import com.cn.mis.utils.http.HttpClientUtil;
import com.cn.mis.utils.json.JsonUtil;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuejia on 2017/4/5.
 */
@Controller
@RequestMapping("operate/im")
@Log4j
public class OperateIMDataController {
    @Resource
    private IIMUserService iimUserService;

    @Resource
    private IIMCompdeptService iimCompdeptService;

    @Resource
    private IIMDeptuserService iimDeptuserService;

    @Resource
    private IIFHrmResourceService iifHrmResourceService;

    @Resource
    private IIFHrmDepartmentService iifHrmDepartmentService;

    @RequestMapping("syncdep")
    @ResponseBody
    public String syncdep(){
        //INFACE->IM
//        List<IFHrmDepartmentWithIM> list = iifHrmDepartmentService.selectAllNeedInit();
//        for (IFHrmDepartmentWithIM hd:list){
//            IMCompdept idp = new IMCompdept();
//            idp.setDeptname(hd.getDepartmentname());
//            idp.setPid(0);
//            idp.setCompid(hd.getSubcompanyid1());
//            idp.setUpdateTime(new Date());
//            idp.setSortmanager(hd.getShoworder());
//            iimCompdeptService.insertSelective(idp);
//            IFHrmDepartmentWithIM ihd = new IFHrmDepartmentWithIM();
//            ihd.setId(hd.getId());
//            ihd.setImid(idp.getId());
//            ihd.setImcreatetime(new Date());
//            ihd.setImsyncflag(1);
//            iifHrmDepartmentService.updateSyncFlag(ihd);
//            log.info("【INFACE->IM】新增【部门】:【"+ JsonUtil.toJson(idp)+"】");
//        }
//
//        List<IFHrmDepartmentWithCustom> updates = iifHrmDepartmentService.selectAllNeedUpdate();
//
//        for (IFHrmDepartmentWithCustom hdc:updates){
//            IMCompdept idp = new IMCompdept();
//            idp.setId(hdc.getImid());
//            idp.setDeptname(hdc.getDepartmentname());
//            idp.setPid(hdc.getImpid());
//            idp.setCompid(hdc.getSubcompanyid1());
//            idp.setUpdateTime(new Date());
//            idp.setSortmanager(hdc.getShoworder());
//            iimCompdeptService.updateByPrimaryKeySelective(idp);
//            IFHrmDepartmentWithCustom ihd = new IFHrmDepartmentWithCustom();
//            ihd.setId(hdc.getId());
//            ihd.setImmodifytime(new Date());
//            ihd.setImsyncflag(2);
//            iifHrmDepartmentService.updateSyncFlag(ihd);
//            log.info("【INFACE->IM】更新【部门】:【"+ JsonUtil.toJson(idp)+"】");
//
//        }
        List<IFHrmDepartmentWithIM> list = iifHrmDepartmentService.selectAllNeedInit();
        for (IFHrmDepartmentWithIM hd:list){
            HashMap<String,String> params = new HashMap<>();
            params.put("deptname",hd.getDepartmentname());
            params.put("sortmanager",hd.getShoworder()+"");
            String resp = HttpClientUtil.sendPostSSLRequest("http://10.37.253.71:8085/service-api/compDept/saveDept",params);
            IMSyncDepResp imSyncDepResp = (IMSyncDepResp) JsonUtil.fromJson(new TypeToken<IMSyncDepResp>(){}.getType(),resp);
            if (imSyncDepResp.getCode().equals("200")){
                IFHrmDepartmentWithCustom ihd = new IFHrmDepartmentWithCustom();
                ihd.setId(hd.getId());
                ihd.setImid(imSyncDepResp.getDate().getId());
                ihd.setImcreatetime(new Date());
                ihd.setImsyncflag(1);
                iifHrmDepartmentService.updateSyncFlag(ihd);
                log.info("【INFACE->IM】新增【部门】:【"+ JsonUtil.toJson(ihd)+"】");
            }
            System.out.printf(resp);
        }

        List<IFHrmDepartmentWithCustom> updates = iifHrmDepartmentService.selectAllNeedUpdate();
        for (IFHrmDepartmentWithCustom hdc:updates){
            HashMap<String,String> params = new HashMap<>();
            params.put("id",hdc.getImid()+"");
            params.put("pId",hdc.getImpid()+"");
            String resp1 = HttpClientUtil.sendPostSSLRequest("http://10.37.253.71:8085/service-api/compDept/savaParentDept",params);
            IMSyncDepResp imSyncDepResp1 = (IMSyncDepResp) JsonUtil.fromJson(new TypeToken<IMSyncDepResp>(){}.getType(),resp1);

            HashMap<String,String> params2 = new HashMap<>();
            params2.put("deptname",hdc.getDepartmentname());
            params2.put("id",hdc.getImid()+"");
            String resp2 = HttpClientUtil.sendPostSSLRequest("http://10.37.253.71:8085/service-api/compDept/updateDept",params2);
            IMSyncDepResp imSyncDepResp2 = (IMSyncDepResp) JsonUtil.fromJson(new TypeToken<IMSyncDepResp>(){}.getType(),resp2);

            IMSyncDepResp imSyncDepResp3 = null;
            if ("1".equals(hdc.getCanceled())){
                HashMap<String,String> params3 = new HashMap<>();
                params3.put("id",hdc.getImid()+"");
                String resp3 = HttpClientUtil.sendPostSSLRequest("http://10.37.253.71:8085/service-api/compDept/deleteDept",params3);
                imSyncDepResp3 = (IMSyncDepResp) JsonUtil.fromJson(new TypeToken<IMSyncDepResp>(){}.getType(),resp3);

            }
            if (("200".equals(imSyncDepResp1.getCode()) && "200".equals(imSyncDepResp2.getCode()) && !"1".equals(hdc.getCanceled()))||
                    ("200".equals(imSyncDepResp1.getCode()) && "200".equals(imSyncDepResp2.getCode()) && "200".equals(imSyncDepResp3.getCode()))){
                IFHrmDepartmentWithCustom ihd = new IFHrmDepartmentWithCustom();
                ihd.setId(hdc.getId());
                ihd.setImmodifytime(new Date());
                ihd.setImsyncflag(2);
                iifHrmDepartmentService.updateSyncFlag(ihd);
                log.info("【INFACE->IM】更新【部门】:【"+ JsonUtil.toJson(ihd)+"】");
            }
        }
        return "新增："+JsonUtil.toJson(list)+",更新："+JsonUtil.toJson(updates);
    }

    @RequestMapping("syncuser")
    @ResponseBody
    public String syncuser(){
//        List<IFHrmResourceWithCustom> list = iifHrmResourceService.selectAllNeedInit();
//
//        for (IFHrmResourceWithCustom hrc:list){
//            IMUser iu = new IMUser();
//            iu.setUsercode(hrc.getLoginid());
//            iu.setPassword(hrc.getPassword());
//            iu.setName(hrc.getLastname());
//            if ("1".equals(hrc.getSex())){
//                iu.setSex("0");
//            }else {
//                iu.setSex("1");
//            }
//            iu.setTel(hrc.getTelephone());
//            iu.setPhone(hrc.getMobile());
//            iu.setEmail(hrc.getEmail());
//            iu.setCompid(hrc.getSubcompanyid1());
//            iu.setForbidden(0);
//            iu.setUpdateTime(new Date());
//            iu.setUserType(2);
//            iimUserService.insertSelective(iu);
//
//            IMDeptuser idu = new IMDeptuser();
//            idu.setUserid(iu.getId());
//            idu.setDeptid(hrc.getImdid());
//            idu.setUpdateTime(new Date());
//            idu.setUsercode(hrc.getLoginid());
//            iimDeptuserService.insertSelective(idu);
//
//            IFHrmResourceWithIM ihr = new IFHrmResourceWithIM();
//            ihr.setId(hrc.getId());
//            ihr.setImid(iu.getId());
//            ihr.setImsyncflag(2);
//            ihr.setImcreatetime(new Date());
//            iifHrmResourceService.updateSyncFlag(ihr);
//            log.info("【INFACE->IM】新增【用户】:【"+ JsonUtil.toJson(iu)+"】");
//            log.info("【INFACE->IM】新增【用户部门映射】:【"+ JsonUtil.toJson(idu)+"】");
//        }
//
//        List<IFHrmResourceWithCustom> updates = iifHrmResourceService.selectAllNeedUpdate();
//        for (IFHrmResourceWithCustom hrc:updates){
//            IMUser iu = new IMUser();
//            iu.setId(hrc.getImid());
//            iu.setName(hrc.getLastname());
//            if ("1".equals(hrc.getSex())){
//                iu.setSex("0");
//            }else {
//                iu.setSex("1");
//            }
//            iu.setTel(hrc.getTelephone());
//            iu.setPhone(hrc.getMobile());
//            iu.setEmail(hrc.getEmail());
//            iu.setCompid(hrc.getSubcompanyid1());
//            iu.setUserType(2);
//            switch (hrc.getStatus()){
//                case 0:
//                    iu.setUsercode(hrc.getLoginid());
//                    iu.setPassword(hrc.getPassword());
//                    iu.setForbidden(0);
//                    break;
//                case 1:
//                    iu.setUsercode(hrc.getLoginid());
//                    iu.setPassword(hrc.getPassword());
//                    iu.setForbidden(0);
//                    break;
//                case 2:
//                    iu.setUsercode(hrc.getLoginid());
//                    iu.setPassword(hrc.getPassword());
//                    iu.setForbidden(0);
//                    break;
//                case 3:
//                    iu.setUsercode(hrc.getLoginid());
//                    iu.setPassword(hrc.getPassword());
//                    iu.setForbidden(0);
//                    break;
//                default:
//                    iu.setForbidden(1);
//            }
//            iu.setUpdateTime(new Date());
//            iimUserService.updateByPrimaryKeySelective(iu);
//
//            IMDeptuser idu = new IMDeptuser();
//            idu.setUserid(hrc.getImid());
//            idu.setDeptid(hrc.getImdid());
//            idu.setUpdateTime(new Date());
//            idu.setUsercode(hrc.getLoginid());
//            iimDeptuserService.deleteByPrimaryKey(idu);
//            iimDeptuserService.insertSelective(idu);
//
//            IFHrmResourceWithIM ihr = new IFHrmResourceWithIM();
//            ihr.setId(hrc.getId());
//            ihr.setImsyncflag(2);
//            ihr.setImmodifytime(new Date());
//            iifHrmResourceService.updateSyncFlag(ihr);
//            log.info("【INFACE->IM】更新【用户】:【"+ JsonUtil.toJson(iu)+"】");
//            log.info("【INFACE->IM】更新【用户部门映射】:【"+ JsonUtil.toJson(idu)+"】");
//        }

        List<IFHrmResourceWithCustom> list = iifHrmResourceService.selectAllNeedInit();
        for (IFHrmResourceWithCustom hrc:list){
            HashMap<String,String> params = new HashMap<>();
            params.put("name",hrc.getLastname());
            params.put("usercode",hrc.getLoginid());
            params.put("tel",hrc.getTelephone());
            params.put("phone",hrc.getMobile());
            params.put("email",hrc.getEmail());
            params.put("password",hrc.getPassword());
            params.put("power","0");
            params.put("birth","");
            if ("1".equals(hrc.getSex())){
                params.put("sex","0");
            }else {
                params.put("sex","1");
            }
            String resp = HttpClientUtil.sendPostSSLRequest("http://10.37.253.71:8085/service-api/user/saveUser",params);
            System.out.println(resp);
            IMSyncDepResp imSyncDepResp = (IMSyncDepResp) JsonUtil.fromJson(new TypeToken<IMSyncDepResp>(){}.getType(),resp);

            HashMap<String,String> params2 = new HashMap<>();
            params2.put("deptid",hrc.getImdid()+"");
            params2.put("id",imSyncDepResp.getDate().getId()+"");
            double dd = hrc.getDsporder();
            int i = (int)dd;
            params2.put("usersort",i+"");
            String resp2 = HttpClientUtil.sendPostSSLRequest("http://10.37.253.71:8085/service-api/user/saveUserDept",params2);
            System.out.println(resp2);
            IMSyncDepResp imSyncDepResp2 = (IMSyncDepResp) JsonUtil.fromJson(new TypeToken<IMSyncDepResp>(){}.getType(),resp2);

            if ("200".equals(imSyncDepResp.getCode()) && "200".equals(imSyncDepResp2.getCode())){
                IFHrmResourceWithIM ihr = new IFHrmResourceWithIM();
                ihr.setId(hrc.getId());
                ihr.setImid(imSyncDepResp.getDate().getId());
                ihr.setImsyncflag(2);
                ihr.setImcreatetime(new Date());
                iifHrmResourceService.updateSyncFlag(ihr);
                log.info("【INFACE->IM】新增【用户】:【"+ JsonUtil.toJson(ihr)+"】");
            }
        }

        List<IFHrmResourceWithCustom> updates = iifHrmResourceService.selectAllNeedUpdate();
        for (IFHrmResourceWithCustom hrc:updates){
            IMSyncDepResp imSyncDepResp3 = null;
            IMSyncDepResp imSyncDepResp4 = null;
            if (hrc.getLoginid() != null){
                HashMap<String,String> params3 = new HashMap<>();
                params3.put("id",hrc.getImid()+"");
                params3.put("name",hrc.getLastname());
                params3.put("usercode",hrc.getLoginid());
                if ("1".equals(hrc.getSex())){
                    params3.put("sex","0");
                }else {
                    params3.put("sex","1");
                }
                params3.put("power","0");
                params3.put("tel",hrc.getTelephone());
                params3.put("phone",hrc.getMobile());
                params3.put("email",hrc.getEmail());
                params3.put("password",hrc.getPassword());
                String resp3 = HttpClientUtil.sendPostSSLRequest("http://10.37.253.71:8085/service-api/user/updateUser",params3);
                imSyncDepResp3 = (IMSyncDepResp) JsonUtil.fromJson(new TypeToken<IMSyncDepResp>(){}.getType(),resp3);
            } else {
                HashMap<String,String> params4 = new HashMap<>();
                params4.put("id",hrc.getImid()+"");
                String resp4 = HttpClientUtil.sendPostSSLRequest("http://10.37.253.71:8085/service-api/user/deleteUser",params4);
                imSyncDepResp4 = (IMSyncDepResp) JsonUtil.fromJson(new TypeToken<IMSyncDepResp>(){}.getType(),resp4);
            }
            if ("200".equals(imSyncDepResp3.getCode()) || "200".equals(imSyncDepResp4.getCode())){
                IFHrmResourceWithIM ihr = new IFHrmResourceWithIM();
                ihr.setId(hrc.getId());
                ihr.setImsyncflag(2);
                ihr.setImmodifytime(new Date());
                iifHrmResourceService.updateSyncFlag(ihr);
                log.info("【INFACE->IM】更新【用户】:【"+ JsonUtil.toJson(ihr)+"】");
            }
        }
        return "新增："+JsonUtil.toJson(list)+",更新："+JsonUtil.toJson(updates);
    }
}
