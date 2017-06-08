package com.cn.mis.timetask;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.cn.mis.domain.entity.mis.OperateProject;
import com.cn.mis.domain.entity.mis.OperateProjectConnect;
import com.cn.mis.domain.entity.mis.OperateProperty;
import com.cn.mis.service.mis.IOperateProjectService;
import com.cn.mis.service.mis.IOperatePropertyService;
import com.cn.mis.service.mis.impl.RPCService;
import com.qding.brick.pojo.biz.ProjectConnect;
import org.springframework.stereotype.Component;

import com.qding.brick.enums.BizTypeEnum;
import com.qding.brick.pojo.biz.Project;
import com.qding.brick.pojo.biz.PropertyInfo;
import com.qding.brick.pojo.biz.Region;
import com.qding.brick.struts.request.BizRemoteRequest;
import com.qding.brick.struts.response.BizRemoteResponse;
import com.qding.brick.struts.response.RegionResponse;
import com.qding.framework.common.constants.HttpStatus;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		insertProjectConnect();
		updatePoject();
		updateProjectConnect();
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
			BizRemoteRequest request = new BizRemoteRequest();
			request.setBizType(BizTypeEnum.Project);//必填，BizTypeEnum.Property(物业公司)  BizTypeEnum.Project(社区)
			Project project = new Project();
			project.setName(proj.getCommunity());//必填，社区名
			project.setPropInfoId(proj.getUpcBossId());//必填，社区所属物业公司
			if (proj.getWhether_to_open_the_newspaper_repair() == null){
				project.setIsFix(0);
			} else {
				if (proj.getWhether_to_open_the_newspaper_repair() == 1){
					project.setIsFix(1);//是否开启报事报修
				} else {
					project.setIsFix(0);//是否开启报事报修
				}
			}

			project.setAddress(proj.getAddress());//所在街道地址
			if (proj.getWhether_to_open_access_control() == null){
				project.setGateType(0);
			} else {
				if (proj.getWhether_to_open_access_control() == 1){
					project.setGateType(2);
				} else {
					project.setGateType(0);
				}
			}
			if (proj.getBoss_provincial_id() != null && proj.getBoss_region_id() != null && proj.getBoss_district_id() != null){
				project.setProvinceId(Long.valueOf(proj.getBoss_provincial_id()));//社区所属省
				project.setRegionId(Long.valueOf(proj.getBoss_region_id()));//必填，社区所属城市
				project.setDistrictId(Long.valueOf(proj.getBoss_district_id()));//社区所属区
			}
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
			} else if(response.getReturnInfo().getCode() == HttpStatus.BAD_REQUEST.getStatusCode()) {
				if (response.getProject() != null) {
					proj.setUcBossId(response.getProject().getId() + "");
					operateProjectService.returnWriteBossIdByCommunity(proj);
				}
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
			if (proj.getWhether_to_open_the_newspaper_repair() == null){
				if (proj.getWhether_to_open_the_newspaper_repair() == 1){
					project.setIsFix(1);//是否开启报事报修
				} else {
					project.setIsFix(0);//是否开启报事报修
				}
			} else {
				project.setIsFix(0);//是否开启报事报修
			}

			project.setAddress(proj.getAddress());//所在街道地址
			if (proj.getWhether_to_open_access_control() == null){
				project.setGateType(0);
			} else {
				if (proj.getWhether_to_open_access_control() == 1){
					project.setGateType(2);
				} else {
					project.setGateType(0);
				}
			}

			if (proj.getBoss_provincial_id() != null && proj.getBoss_region_id() != null && proj.getBoss_district_id() != null){
				project.setProvinceId(Long.valueOf(proj.getBoss_provincial_id()));//社区所属省
				project.setRegionId(Long.valueOf(proj.getBoss_region_id()));//必填，社区所属城市
				project.setDistrictId(Long.valueOf(proj.getBoss_district_id()));//社区所属区
			}
			if (proj.getUpdateAt() != null){
				project.setUpdateAt(proj.getUpdateAt().getTime());//操作时间
			}
			project.setUpdateBy(proj.getLastname());//必填，操作人
			request.setCreateUser(proj.getLastname());//必填，操作人
			//操作时间
			request.setProject(project);
			BizRemoteResponse response = brickRPCService.updateProjectBaseData(request);
			log.info("更新社区:"+proj.getCommunity()+" ,response=" + response);
			if (response.getReturnInfo().getCode() == HttpStatus.OK
					.getStatusCode()) {
				operateProjectService.returnWriteUpdateFlag0ByCommunity(proj);
			}
		}
		return "success";
	}

	private String insertProjectConnect(){
		List<OperateProjectConnect> list = operateProjectService.SelectSerivceCenterAddressInit();
		for (OperateProjectConnect connect:list){
			//社区新增完成后添加社区服务中心地址
			BizRemoteRequest requestConnect = new BizRemoteRequest();
			requestConnect.setBizType(BizTypeEnum.ProjectConnect);
			Project project = new Project();
			List<ProjectConnect> centeraddress = new ArrayList<>();
			ProjectConnect projectConnect = new ProjectConnect();
			projectConnect.setProjectId(Long.valueOf(connect.getUcBossId()));
			projectConnect.setName(connect.getCommunity());
			projectConnect.setConnectType(1);
			projectConnect.setAddress(connect.getService_center_address().replace(" ","").replace("&nbsp;",""));
			projectConnect.setPhone1(connect.getService_phone());
			centeraddress.add(projectConnect);
			project.setConnects(centeraddress);
			requestConnect.setProjectConnect(projectConnect);
			requestConnect.setCreateUser(connect.getLastname());
			BizRemoteResponse response = brickRPCService.insert(requestConnect);
			log.info("【社区MIS->BOSS】新增服务中心地址:"+connect.getCommunity()+" ,response=" + response);
			if (response.getReturnInfo().getCode() == HttpStatus.OK
					.getStatusCode()) {
				if(response.getProjectConnect()!=null){
					connect.setBoss_service_center_address_id(response.getProjectConnect().getId());
					connect.setBoss_service_center_address_flage(2);
					operateProjectService.returnWriteBossIdByConnect(connect);
				}
			} else if(response.getReturnInfo().getCode() == HttpStatus.BAD_REQUEST.getStatusCode()) {
				if (response.getProject() != null) {
					connect.setBoss_service_center_address_id(response.getProjectConnect().getId());
					connect.setBoss_service_center_address_flage(1);
					operateProjectService.returnWriteBossIdByConnect(connect);
				}
			}
		}
		return "success";
	}

	private String updateProjectConnect(){
		List<OperateProjectConnect> list = operateProjectService.SelectSerivceCenterAddressUpdate();
		for (OperateProjectConnect connect:list){
			//社区新增完成后添加社区服务中心地址
			BizRemoteRequest requestConnect = new BizRemoteRequest();
			requestConnect.setBizType(BizTypeEnum.ProjectConnect);
			Project project = new Project();
			List<ProjectConnect> centeraddress = new ArrayList<>();
			ProjectConnect projectConnect = new ProjectConnect();
			projectConnect.setId(connect.getBoss_service_center_address_id());
			projectConnect.setProjectId(Long.valueOf(connect.getUcBossId()));
			projectConnect.setName(connect.getCommunity());
			projectConnect.setConnectType(1);
			projectConnect.setAddress(connect.getService_center_address());
			projectConnect.setPhone1(connect.getService_phone());
			centeraddress.add(projectConnect);
			project.setConnects(centeraddress);
			requestConnect.setProjectConnect(projectConnect);
			requestConnect.setCreateUser(connect.getLastname());
			BizRemoteResponse response = brickRPCService.updateProjectConnect(requestConnect);
			log.info("【社区MIS->BOSS】更新服务中心地址:"+connect.getCommunity()+" ,response=" + response);
			if (response.getReturnInfo().getCode() == HttpStatus.OK
					.getStatusCode()) {
				connect.setBoss_service_center_address_flage(2);
				operateProjectService.returnWriteBossIdByConnect(connect);
			}
		}
		return "success";
	}
}
