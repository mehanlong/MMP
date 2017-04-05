package com.cn.mis.web.controller;

import com.cn.mis.domain.entity.OperateProject;
import com.cn.mis.service.IOperateProjectService;
import com.cn.mis.service.impl.RPCService;
import com.qding.brick.enums.BizTypeEnum;
import com.qding.brick.pojo.biz.Project;
import com.qding.brick.struts.request.BizRemoteRequest;
import com.qding.brick.struts.response.BizRemoteResponse;
import com.qding.framework.common.constants.HttpStatus;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuejia on 2017/3/2.
 */
@Controller
@Log4j
@RequestMapping("/operate/bossdata")
public class OperateBOSSDataController {

    @Resource
    private RPCService brickRPCService;

    @Resource
    private IOperateProjectService iOperateProjectService;

    @RequestMapping("/updateproject")
    @ResponseBody
    private String updatePoject(){
        List<OperateProject> list = iOperateProjectService.selectUpadteBySql();
        for(OperateProject proj:list){
            BizRemoteRequest request = new BizRemoteRequest();
            request.setBizType(BizTypeEnum.Project);//必填，BizTypeEnum.Property(物业公司)  BizTypeEnum.Project(社区)
            Project project = new Project();
            project.setId(Long.valueOf(proj.getUcBossId()));//社区BossId
            project.setPropInfoId(proj.getUpcBossId());//必填，社区所属物业公司ID
            if(proj.getTotal_households()!= null){
                project.setLiveNumber(proj.getTotal_households());//入驻总户数
            }
            if (proj.getUpdateAt() != null){
                project.setUpdateAt(proj.getUpdateAt().getTime());//操作时间
            }
            project.setUpdateBy(proj.getLastname());//必填，操作人
            request.setCreateUser(proj.getLastname());//必填，操作人
            //操作时间
            request.setProject(project);
            BizRemoteResponse response = brickRPCService.updateBase(request);
            log.info("更新社区:"+proj.getCommunity()+" ,response=" + response);
            if (response.getReturnInfo().getCode() == HttpStatus.OK
                    .getStatusCode()) {
                iOperateProjectService.returnWriteUpdateFlag0ByCommunity(proj);
            }
        }
        return "success";
    }

}
