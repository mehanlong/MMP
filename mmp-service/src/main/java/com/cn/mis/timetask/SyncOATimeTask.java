package com.cn.mis.timetask;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.domain.entity.OperateProject;
import com.cn.mis.domain.entity.OperateProperty;
import com.cn.mis.service.IOperateProjectService;
import com.cn.mis.service.IOperatePropertyService;
import com.cn.mis.service.impl.RPCService;
import com.qding.framework.common.util.SpringContextUtils;
import org.springframework.stereotype.Component;

import com.qding.brick.enums.BizTypeEnum;
import com.qding.brick.pojo.biz.Project;
import com.qding.brick.pojo.biz.PropertyInfo;
import com.qding.brick.pojo.biz.Region;
import com.qding.brick.remote.biz.RegionRemote;
import com.qding.brick.struts.request.BizRemoteRequest;
import com.qding.brick.struts.response.BizRemoteResponse;
import com.qding.brick.struts.response.RegionResponse;
import com.qding.framework.common.constants.HttpStatus;

import lombok.extern.log4j.Log4j;
@Log4j
@Component("syncOATimeTask")
public class SyncOATimeTask {
	@Resource
	private RPCService brickRPCService;
	@Resource
	private IOperatePropertyService operatePropertyService;
	@Resource
	private IOperateProjectService operateProjectService;
	
	public void run() {
		System.out.println("【SyncOATimeTask】start");
		insertProperty();
		insertProject();
		updatePoject();
	}
	
	
	
	private String insertProperty(){
		List<OperateProperty> list = operatePropertyService.selectBySql();
		for(OperateProperty prop:list){
			BizRemoteRequest request = new BizRemoteRequest();
	        request.setBizType(BizTypeEnum.Property); //必填，BizTypeEnum.Property(物业公司)  BizTypeEnum.Project(社区)
	        PropertyInfo propertyInfo = new PropertyInfo();
	        propertyInfo.setFullName(prop.getAccountName()); //必填，物业公司全名
	        propertyInfo.setShortName(prop.getAccountName()); //必填，物业公司短名
	        propertyInfo.setContactor(prop.getManager_name()); //非必填，联系人
	        propertyInfo.setMobile(prop.getManager_phone());//非必填，联系人电话
	        request.setCreateUser(prop.getLastname());//必填，操作人
	        request.setPropertyInfo(propertyInfo);
	        BizRemoteResponse response = brickRPCService.insert(request);
			log.info("【物业MIS->BOSS】新增数据:"+prop.getAccountName()+" ,response="+response);
			if(response.getReturnInfo().getCode()==HttpStatus.OK.getStatusCode()){
				if(response.getPropertyInfo()!=null){
					prop.setBoss_id(response.getPropertyInfo().getId());
					operatePropertyService.returnWriteBossIdByAccountName(prop);
				}
			} else if(response.getReturnInfo().getCode()==HttpStatus.BAD_REQUEST.getStatusCode()){
				if(response.getPropertyInfo() != null){
					prop.setBoss_id(response.getPropertyInfo().getId());
					operatePropertyService.returnWriteBossIdByAccountName(prop);
				}
			}
		}
		return "success";
	}

	private String insertProject(){
		List<OperateProject> list = operateProjectService.selectBySql();
		
		for(OperateProject proj:list){
			RegionResponse regionResponse = brickRPCService.getRegionByName(proj.getCity());
			Region region = regionResponse.getRegion();
			BizRemoteRequest request = new BizRemoteRequest();
	        request.setBizType(BizTypeEnum.Project);//必填，BizTypeEnum.Property(物业公司)  BizTypeEnum.Project(社区)
	        Project project = new Project();
	        project.setName(proj.getCommunity());//必填，社区名
	        project.setPropInfoId(proj.getUpcBossId());//必填，社区所属物业公司
//	        if(proj.getTotal_households()!= null){
//				project.setPersonNumber(proj.getTotal_households());
//	        }
	        if(region != null){
	        	project.setRegionId(region.getId());//必填，社区所属城市
	        	request.setCreateUser(proj.getLastname());//必填，操作人
	 	        request.setProject(project);
	 	        BizRemoteResponse response = brickRPCService.insert(request);
	 			log.info("【社区MIS->BOSS】新增数据:"+proj.getCommunity()+" ,response=" + response);
	 			if (response.getReturnInfo().getCode() == HttpStatus.OK
	 					.getStatusCode()) {
	 				if(response.getProject()!=null){
	 					proj.setUcBossId(response.getProject().getId()+"");
	 					operateProjectService.returnWriteBossIdByCommunity(proj);
	 				}
	 			} else if(response.getReturnInfo().getCode() == HttpStatus.BAD_REQUEST.getStatusCode()){
	 				if(response.getProject()!=null){
	 					proj.setUcBossId(response.getProject().getId()+"");
	 					operateProjectService.returnWriteBossIdByCommunity(proj);
	 				}
	 			}
	        } else{
	        	log.info("【社区MIS->BOSS】新增失败:"+proj.getCommunity()+" ,check= \"所属城市为空\"");
	        }
		}
		return "success";
	}

	private String updatePoject(){
		List<OperateProject> list = operateProjectService.selectUpadteBySql();
		for(OperateProject proj:list){
			BizRemoteRequest request = new BizRemoteRequest();
			request.setBizType(BizTypeEnum.Project);//必填，BizTypeEnum.Property(物业公司)  BizTypeEnum.Project(社区)
			Project project = new Project();
			project.setId(Long.valueOf(proj.getUcBossId()));//社区BossId
			project.setPropInfoId(proj.getUpcBossId());//必填，社区所属物业公司ID
//			if(proj.getTotal_households()!= null){
//				project.setPersonNumber(proj.getTotal_households());
//			}
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
				operateProjectService.returnWriteUpdateFlag0ByCommunity(proj);
			}
		}
		return "success";
	}
}
