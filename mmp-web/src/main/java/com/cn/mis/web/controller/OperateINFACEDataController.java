package com.cn.mis.web.controller;

import com.cn.mis.domain.entity.inface.IFHrmDepartment;
import com.cn.mis.domain.entity.inface.IFHrmDepartmentWithIM;
import com.cn.mis.domain.entity.inface.IFHrmResource;
import com.cn.mis.domain.entity.inface.IFHrmResourceWithIM;
import com.cn.mis.domain.entity.mis.HrmResource;
import com.cn.mis.service.inface.IIFHrmDepartmentService;
import com.cn.mis.service.inface.IIFHrmResourceService;
import com.cn.mis.service.mis.IHrmDepartmentService;
import com.cn.mis.service.mis.IHrmResourceService;
import com.cn.mis.utils.equals.EqualsUtil;
import com.cn.mis.utils.json.JsonUtil;
import com.cn.mis.utils.object.ClassUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuejia on 2017/4/13.
 */
@RequestMapping("operate/inface")
@Controller
@Log4j
public class OperateINFACEDataController {
    @Resource
    private IIFHrmResourceService iifHrmResourceService;

    @Resource
    private IIFHrmDepartmentService iifHrmDepartmentService;

    @Resource
    private IHrmResourceService iHrmResourceService;

    @Resource
    private IHrmDepartmentService iHrmDepartmentService;

    @RequestMapping("syncdep")
    @ResponseBody
    public String syncDep(){
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

    @RequestMapping("syncuser")
    @ResponseBody
    public String syncUser(){
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
}
