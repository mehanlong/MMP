package com.cn.mis.timetask;

import com.cn.mis.domain.entity.im.IMCompdept;
import com.cn.mis.domain.entity.im.IMDeptuser;
import com.cn.mis.domain.entity.im.IMUser;
import com.cn.mis.domain.entity.inface.*;
import com.cn.mis.service.im.IIMCompdeptService;
import com.cn.mis.service.im.IIMDeptuserService;
import com.cn.mis.service.im.IIMUserService;
import com.cn.mis.service.inface.IIFHrmDepartmentService;
import com.cn.mis.service.inface.IIFHrmResourceService;
import com.cn.mis.service.mis.IHrmDepartmentService;
import com.cn.mis.service.mis.IHrmResourceService;
import com.cn.mis.utils.equals.EqualsUtil;
import com.cn.mis.utils.json.JsonUtil;
import com.cn.mis.utils.object.ClassUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yuejia on 2017/4/27.
 */
@Log4j
@Component("infaceTimeTask")
public class InfaceTimeTask {
    @Resource
    private IIFHrmResourceService iifHrmResourceService;

    @Resource
    private IIFHrmDepartmentService iifHrmDepartmentService;

    @Resource
    private IHrmResourceService iHrmResourceService;

    @Resource
    private IHrmDepartmentService iHrmDepartmentService;

    @Resource
    private IIMUserService iimUserService;

    @Resource
    private IIMCompdeptService iimCompdeptService;

    @Resource
    private IIMDeptuserService iimDeptuserService;

    public void run() {
        System.out.println("【InfaceTimeTask】start");
        misToInfaceDep();
        misToInfaceUser();
        infaceToImDep();
        infaceToImUser();
    }

    public String misToInfaceDep(){
        //MIS->INFACE
        List<IFHrmDepartment> newlist = iHrmDepartmentService.checkAllWithIm();
        List<IFHrmDepartment> oldlist = iifHrmDepartmentService.checkAllWithIm();

        ArrayList<IFHrmDepartment> insertList = new ArrayList<>();
        ArrayList<IFHrmDepartmentWithIM> updateList = new ArrayList<>();

        for(int i=0; i<newlist.size();i++){
            IFHrmDepartment nh = newlist.get(i);
            boolean updateflag = false;
            for (IFHrmDepartment oh:oldlist){
                if (nh.getId().equals(oh.getId())){
                    updateflag = true;
                    if (!EqualsUtil.domainEquals(nh,oh)){
                        IFHrmDepartmentWithIM im = new IFHrmDepartmentWithIM();
                        try {
                            ClassUtil.Copy(nh,im);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        im.setImsyncflag(1);
                        updateList.add(im);
                    }
                    break;
                }
            }
            if (!updateflag){
                insertList.add(nh);
            }
            //每40条或最后不足40条时执行一次批量插入
            if ((i+1)%40 == 0 || newlist.size()-(i+1) == 0){
                if(updateList.size()>0){
                    iifHrmDepartmentService.updateSelBatch(updateList);
                    log.info("【MIS->INFACE】更新【部门】:【"+ JsonUtil.toJson(updateList)+"】");
                }
                if(insertList.size()>0){
                    iifHrmDepartmentService.insertBatch(insertList);
                    log.info("【MIS->INFACE】新增【部门】:【"+ JsonUtil.toJson(insertList)+"】");
                }
                insertList.clear();
                updateList.clear();
            }
        }
        return "success";
    }

    public String misToInfaceUser(){
        List<IFHrmResource> newlist = iHrmResourceService.checkAllWithIm();
        List<IFHrmResource> oldlist = iifHrmResourceService.checkAllWithIm();

        ArrayList<IFHrmResource> insertList = new ArrayList<>();
        ArrayList<IFHrmResourceWithIM> updateList = new ArrayList<>();

        for (int i=0;i<newlist.size();i++){
            IFHrmResource nhr = newlist.get(i);
            boolean updateflag = false;
            for (IFHrmResource ohr:oldlist){
                if (nhr.getId().equals(ohr.getId())){
                    updateflag = true;
                    if (!EqualsUtil.domainEquals(nhr,ohr)){
                        IFHrmResourceWithIM im = new IFHrmResourceWithIM();
                        try {
                            ClassUtil.Copy(nhr,im);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        im.setImsyncflag(1);
                        updateList.add(im);
                    }
                    break;
                }
            }
            if (!updateflag){
                insertList.add(nhr);
            }
            if ((i+1)%20 == 0 || newlist.size()-(i+1) == 0){
                if (updateList.size()>0){
                    iifHrmResourceService.updateSelBatch(updateList);
                    log.info("【MIS->INFACE】更新【用户】:【"+ JsonUtil.toJson(updateList)+"】");
                }
                if (insertList.size()>0){
                    iifHrmResourceService.insertBatch(insertList);
                    log.info("【MIS->INFACE】新增【用户】:【"+ JsonUtil.toJson(insertList)+"】");
                }
                insertList.clear();
                updateList.clear();
            }
        }

        return "success";
    }

    public String infaceToImDep(){
        //INFACE->IM
        List<IFHrmDepartmentWithIM> list = iifHrmDepartmentService.selectAllNeedInit();

        for (IFHrmDepartmentWithIM hd:list){
            IMCompdept idp = new IMCompdept();
            idp.setDeptname(hd.getDepartmentname());
            idp.setPid(0);
            idp.setCompid(hd.getSubcompanyid1());
            idp.setUpdateTime(new Date());
            idp.setSortmanager(hd.getShoworder());
            iimCompdeptService.insertSelective(idp);
            IFHrmDepartmentWithIM ihd = new IFHrmDepartmentWithIM();
            ihd.setId(hd.getId());
            ihd.setImid(idp.getId());
            ihd.setImcreatetime(new Date());
            ihd.setImsyncflag(1);
            iifHrmDepartmentService.updateSyncFlag(ihd);
            log.info("【INFACE->IM】新增【部门】:【"+ JsonUtil.toJson(idp)+"】");
        }

        List<IFHrmDepartmentWithCustom> updates = iifHrmDepartmentService.selectAllNeedUpdate();

        for (IFHrmDepartmentWithCustom hdc:updates){
            IMCompdept idp = new IMCompdept();
            idp.setId(hdc.getImid());
            idp.setDeptname(hdc.getDepartmentname());
            idp.setPid(hdc.getImpid());
            idp.setCompid(hdc.getSubcompanyid1());
            idp.setUpdateTime(new Date());
            idp.setSortmanager(hdc.getShoworder());
            iimCompdeptService.updateByPrimaryKeySelective(idp);
            IFHrmDepartmentWithCustom ihd = new IFHrmDepartmentWithCustom();
            ihd.setId(hdc.getId());
            ihd.setImmodifytime(new Date());
            ihd.setImsyncflag(2);
            iifHrmDepartmentService.updateSyncFlag(ihd);
            log.info("【INFACE->IM】更新【部门】:【"+ JsonUtil.toJson(idp)+"】");

        }

        return "success";
    }

    public String infaceToImUser(){
        List<IFHrmResourceWithCustom> list = iifHrmResourceService.selectAllNeedInit();

        for (IFHrmResourceWithCustom hrc:list){
            IMUser iu = new IMUser();
            iu.setUsercode(hrc.getLoginid());
            iu.setPassword(hrc.getPassword());
            iu.setName(hrc.getLastname());
            if ("1".equals(hrc.getSex())){
                iu.setSex("0");
            }else {
                iu.setSex("1");
            }
            iu.setTel(hrc.getTelephone());
            iu.setPhone(hrc.getMobile());
            iu.setEmail(hrc.getEmail());
            iu.setCompid(hrc.getSubcompanyid1());
            iu.setForbidden(0);
            iu.setUpdateTime(new Date());
            iu.setUserType(2);
            iimUserService.insertSelective(iu);

            IMDeptuser idu = new IMDeptuser();
            idu.setUserid(iu.getId());
            idu.setDeptid(hrc.getImdid());
            idu.setUpdateTime(new Date());
            idu.setUsercode(hrc.getLoginid());
            iimDeptuserService.insertSelective(idu);

            IFHrmResourceWithIM ihr = new IFHrmResourceWithIM();
            ihr.setId(hrc.getId());
            ihr.setImid(iu.getId());
            ihr.setImsyncflag(2);
            ihr.setImcreatetime(new Date());
            iifHrmResourceService.updateSyncFlag(ihr);
            log.info("【INFACE->IM】新增【用户】:【"+ JsonUtil.toJson(iu)+"】");
            log.info("【INFACE->IM】新增【用户部门映射】:【"+ JsonUtil.toJson(idu)+"】");
        }

        List<IFHrmResourceWithCustom> updates = iifHrmResourceService.selectAllNeedUpdate();
        for (IFHrmResourceWithCustom hrc:updates){
            IMUser iu = new IMUser();
            iu.setId(hrc.getImid());
            iu.setName(hrc.getLastname());
            if ("1".equals(hrc.getSex())){
                iu.setSex("0");
            }else {
                iu.setSex("1");
            }
            iu.setTel(hrc.getTelephone());
            iu.setPhone(hrc.getMobile());
            iu.setEmail(hrc.getEmail());
            iu.setCompid(hrc.getSubcompanyid1());
            iu.setUserType(2);
            switch (hrc.getStatus()){
                case 0:
                    iu.setUsercode(hrc.getLoginid());
                    iu.setPassword(hrc.getPassword());
                    iu.setForbidden(0);
                    break;
                case 1:
                    iu.setUsercode(hrc.getLoginid());
                    iu.setPassword(hrc.getPassword());
                    iu.setForbidden(0);
                    break;
                case 2:
                    iu.setUsercode(hrc.getLoginid());
                    iu.setPassword(hrc.getPassword());
                    iu.setForbidden(0);
                    break;
                case 3:
                    iu.setUsercode(hrc.getLoginid());
                    iu.setPassword(hrc.getPassword());
                    iu.setForbidden(0);
                    break;
                default:
                    iu.setForbidden(1);
            }
            iu.setUpdateTime(new Date());
            iimUserService.updateByPrimaryKeySelective(iu);

            IMDeptuser idu = new IMDeptuser();
            idu.setUserid(hrc.getImid());
            idu.setDeptid(hrc.getImdid());
            idu.setUpdateTime(new Date());
            idu.setUsercode(hrc.getLoginid());
            iimDeptuserService.deleteByPrimaryKey(idu);
            iimDeptuserService.insertSelective(idu);

            IFHrmResourceWithIM ihr = new IFHrmResourceWithIM();
            ihr.setId(hrc.getId());
            ihr.setImsyncflag(2);
            ihr.setImmodifytime(new Date());
            iifHrmResourceService.updateSyncFlag(ihr);
            log.info("【INFACE->IM】更新【用户】:【"+ JsonUtil.toJson(iu)+"】");
            log.info("【INFACE->IM】更新【用户部门映射】:【"+ JsonUtil.toJson(idu)+"】");
        }

        return JsonUtil.toJson(list);
    }
}
