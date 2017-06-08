package com.cn.mis.web.controller;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;
import com.cn.mis.domain.entity.mis.*;
import com.cn.mis.service.mis.*;
import com.cn.mis.service.mis.impl.RPCService;
import com.cn.mis.utils.date.DateStyle;
import com.cn.mis.utils.date.DateUtil;
import com.cn.mis.utils.equals.EqualsUtil;
import com.cn.mis.utils.ftp.FtpUtil;
import com.cn.mis.utils.json.JsonUtil;
import com.cn.mis.utils.zip.ZipUtil;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;
import com.qding.brick.com.qding.brick.constants.contract.mis.MISContractConstants;
import com.qding.brick.enums.BizTypeEnum;
import com.qding.brick.pojo.biz.Project;
import com.qding.brick.pojo.biz.ProjectConnect;
import com.qding.brick.remote.biz.IBizRemoteService;
import com.qding.brick.remote.contract.mis.MISContractRemote;
import com.qding.brick.struts.entity.mis.CategoryInfo;
import com.qding.brick.struts.entity.mis.PropInfo;
import com.qding.brick.struts.entity.mis.SelfProviderType;
import com.qding.brick.struts.entity.mis.SupplierContractSettleInfo;
import com.qding.brick.struts.request.BizRemoteRequest;
import com.qding.brick.struts.request.mis.*;
import com.qding.brick.struts.response.BizRemoteResponse;
import com.qding.brick.struts.response.mis.*;
import com.qding.framework.common.constants.HttpStatus;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.Json;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private IOperateProjectService operateProjectService;

    @Resource
    private IUFVendorService iufVendorService;

    @Resource
    private IUFVendorDT1Service iufVendorDT1Service;

    @Resource
    private IUFCategoryService iufCategoryService;

    @Resource
    private IUFContractAService iufContractAService;

    private MISContractRemote misContractRemote;

    private String ftp_host;
    private String ftp_username;
    private String ftp_password;

    private String qiniu_bucketname;
    private String qiniu_accesskey;
    private String qiniu_secretkey;

    @PostConstruct
    private void init(){
        try {
            HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
            hessianProxyFactory.setOverloadEnabled(false);
            hessianProxyFactory.setChunkedPost(false);
            misContractRemote = (MISContractRemote) hessianProxyFactory.create(MISContractRemote.class,
                    "http://devboss.qdingnet.com/brickadmin/remote/v0/MISContract");

            Properties propFile1 = new Properties();
            InputStream instream1 = getClass().getResourceAsStream("/ftp.properties");
            propFile1.load(instream1);
            ftp_host = propFile1.getProperty("ftp.host");
            ftp_username = propFile1.getProperty("ftp.username");
            ftp_password = propFile1.getProperty("ftp.password");

            Properties qiniuFile = new Properties();
            InputStream instream2 = getClass().getResourceAsStream("/qiniu.properties");
            qiniuFile.load(instream2);
            qiniu_bucketname = qiniuFile.getProperty("bucketName");
            qiniu_accesskey = qiniuFile.getProperty("accessKey");
            qiniu_secretkey = qiniuFile.getProperty("secretKey");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("insertProject")
    @ResponseBody
    private String insertProject(){
        List<OperateProject> list = operateProjectService.selectBySql();
        for(OperateProject proj:list){
            BizRemoteRequest request = new BizRemoteRequest();
            request.setBizType(BizTypeEnum.Project);//必填，BizTypeEnum.Property(物业公司)  BizTypeEnum.Project(社区)
            Project project = new Project();
            project.setName(proj.getCommunity());//必填，社区名
            project.setPropInfoId(proj.getUpcBossId());//必填，社区所属物业公司
            if (proj.getWhether_to_open_the_newspaper_repair() == 1){
                project.setIsFix(1);//是否开启报事报修
            } else {
                project.setIsFix(0);//是否开启报事报修
            }
            project.setAddress(proj.getAddress());//所在街道地址
            if (proj.getWhether_to_open_access_control() == 1){
                project.setGateType(2);
            } else {
                project.setGateType(0);
            }
            project.setProvinceId(Long.valueOf(proj.getBoss_provincial_id()));//社区所属省
            project.setRegionId(Long.valueOf(proj.getBoss_region_id()));//必填，社区所属城市
            project.setDistrictId(Long.valueOf(proj.getBoss_district_id()));//社区所属区
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

    @RequestMapping("updateProject")
    @ResponseBody
    private String updatePoject(){
        List<OperateProject> list = operateProjectService.selectUpadteBySql();
        for(OperateProject proj:list){
            BizRemoteRequest request = new BizRemoteRequest();
            request.setBizType(BizTypeEnum.Project);//必填，BizTypeEnum.Property(物业公司)  BizTypeEnum.Project(社区)
            Project project = new Project();
            project.setId(Long.valueOf(proj.getUcBossId()));//社区BossId
            project.setPropInfoId(proj.getUpcBossId());//必填，社区所属物业公司ID

            if (proj.getWhether_to_open_the_newspaper_repair() == 1){
                project.setIsFix(1);//是否开启报事报修
            } else {
                project.setIsFix(0);//是否开启报事报修
            }
            project.setAddress(proj.getAddress());//所在街道地址
            if (proj.getWhether_to_open_access_control() == 1){
                project.setGateType(2);
            } else {
                project.setGateType(0);
            }
            project.setProvinceId(Long.valueOf(proj.getBoss_provincial_id()));//社区所属省
            project.setRegionId(Long.valueOf(proj.getBoss_region_id()));//必填，社区所属城市
            project.setDistrictId(Long.valueOf(proj.getBoss_district_id()));//社区所属区
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

    @RequestMapping("insertProjectConnect")
    @ResponseBody
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
            projectConnect.setAddress(connect.getService_center_address());
            projectConnect.setPhone1(connect.getService_phone());
            centeraddress.add(projectConnect);
            project.setConnects(centeraddress);
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

    @RequestMapping("updateProjectConnect")
    @ResponseBody
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

    @RequestMapping("getreqtoken")
    @ResponseBody
    public String getRequestToken() {
        try {
            GetRequestTokenResponse requestTokenResponse = misContractRemote.getRequestToken(new GetRequestTokenRequest());
            System.out.println("requestTokenResponse:"+requestTokenResponse.getRequestToken());
            return requestTokenResponse.getRequestToken();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "false";
    }

    @RequestMapping("suppliercheck/{fullname}/{mobile}")
    @ResponseBody
    public String registerSupplierCheck(@PathVariable String fullname,@PathVariable String mobile){
        RegisterSupplierCheckRequest req = new RegisterSupplierCheckRequest();
        req.setFullName(fullname);
        req.setMobile(mobile);
        RegisterSupplierCheckResponse resp = misContractRemote.registerSupplierCheck(req);
        if (resp.getReturnInfo().isSuccess()){
            return "false";
        }
        return "true";
    }


    @RequestMapping("addVendor")
    @ResponseBody
    public String insertVendor(){
        List<UFVendorWithResource> list = iufVendorService.selectAllNeedInit();
        for (int i=0;i<list.size();i++){
            UFVendorWithResource ufVendor = list.get(i);
//            if (checkVendor(ufVendor)){
                RegisterSupplierRequest registerSupplierRequest = new RegisterSupplierRequest();
                registerSupplierRequest.setRequestToken(getRequestToken());
                if (ufVendor.getVendorAttr() == 0){
                    registerSupplierRequest.setSupplierScope(0);//供方属性
                    List<Long> ids = new ArrayList<>();
                    ids.add(224l);
                    registerSupplierRequest.setPropIdList(ids);//供方类型
                } else {
                    registerSupplierRequest.setSupplierScope(1);//供方属性
                    registerSupplierRequest.setSelfProviderType(Integer.valueOf(ufVendor.getVendorType())+1);//供方类型
                }
                registerSupplierRequest.setFullName(ufVendor.getVendor());//供方全称
                registerSupplierRequest.setShortName(ufVendor.getShortName());//供方简称
                registerSupplierRequest.setAddress(ufVendor.getAddress());//供方所在地

                String[] docid = ufVendor.getPhoto().split(",");
                String s = ufVendor.getVendorComment();
                List<String> commentids = new ArrayList<>();
                List<String> photoids = new ArrayList<>();
                Pattern patc = Pattern.compile("fileid=[0-9]*");
                Matcher matc = patc.matcher(s);
                while (matc.find()){
                    String[] sp = matc.group(0).split("=");
                    commentids.add(sp[1]);
                }
                for (String photo:docid){
                    photoids.add(photo);
                }
                List<ImageFiled> imageFileds = new ArrayList<>();
                List<ImageFiled> commentImgList = new ArrayList<>();
                if (commentids.size()>0){
                    commentImgList.addAll(iufVendorService.SelImageFiledComment(commentids));
                    imageFileds.addAll(commentImgList);
                }
                List<ImageFiled> photoImgList = new ArrayList<>();
                if (photoids.size()>0){
                    photoImgList.addAll(iufVendorService.SelImageFiledPhoto(photoids));
                    imageFileds.addAll(photoImgList);
                }
                if (imageFileds.size()>0){
                    String localPath = "/Users/yuejia/Downloads";
                    List<String> commentname = new ArrayList<>();
                    List<String> photoname = new ArrayList<>();
                    for (ImageFiled imageFiled:commentImgList){
                        Pattern pat = Pattern.compile("[0-9A-Za-z_]*.zip$");
                        Matcher mat = pat.matcher(imageFiled.getFilerealpath().replace("-","_"));
                        boolean rs = mat.find();
                        String filename = null;
                        if (rs = true){
                            filename = mat.group(0).replace("_","-");
                        }
                        String tmp = imageFiled.getFilerealpath().replace("E:\\WEAVER\\ecology\\filesystem\\","").replace(filename,"").replace("\\","/");
                        String imgname = imageFiled.getImagefilename();
                        String[] imgspl = imgname.split("\\.");
                        String type = imgspl[imgspl.length-1];
                        downloadFormFTP(localPath,tmp,filename.replace(".zip",""),type);
                        commentname.add(filename.replace(".zip",""));
                    }
                    for (ImageFiled imageFiled:photoImgList){
                        Pattern pat = Pattern.compile("[0-9A-Za-z_]*.zip$");
                        Matcher mat = pat.matcher(imageFiled.getFilerealpath().replace("-","_"));
                        boolean rs = mat.find();
                        String filename = null;
                        if (rs = true){
                            filename = mat.group(0).replace("_","-");
                        }
                        String tmp = imageFiled.getFilerealpath().replace("E:\\WEAVER\\ecology\\filesystem\\","").replace(filename,"").replace("\\","/");
                        String type = imageFiled.getImagefiletype().replace("image/","");
                        downloadFormFTP(localPath,tmp,filename.replace(".zip",""),type);
                        photoname.add(filename.replace(".zip",""));
                    }
                    Pattern p = Pattern.compile("<\\s*img\\s+([^>]+)\\s*>");
                    Matcher m = p.matcher(ufVendor.getVendorComment());
                    List<String> imgtags = new ArrayList<>();
                    while (m.find()){
                        imgtags.add(m.group(0));
                    }
                    String comm = ufVendor.getVendorComment();
                    for (int j=0;j<imgtags.size();j++){
                        comm = comm.replace(imgtags.get(j),"<img src=\"http://img1.qdingnet.com/"+commentname.get(j)+"\" alt=\"\"/>");
                    }
                    if (commentImgList.size()>0 && photoImgList.size()>0){
                        registerSupplierRequest.setLicense(comm+" "+"http://img1.qdingnet.com/"+photoname.get(i));//证照信息
                    } else if (commentImgList.size()>0 && photoImgList.size() == 0){
                        registerSupplierRequest.setLicense(comm);//证照信息
                    } else if (commentImgList.size()==0 && photoImgList.size()>0){
                        registerSupplierRequest.setLicense(ufVendor.getVendorComment()+" "+"http://img1.qdingnet.com/"+photoname.get(i));//证照信息
                    }
                } else {
                    registerSupplierRequest.setLicense(ufVendor.getVendorComment());
                }
                log.info(JsonUtil.toJson(registerSupplierRequest.getLicense()));
                registerSupplierRequest.setBdName(ufVendor.getOur_contract_name());//BD的真实姓名
                registerSupplierRequest.setBdMobile(ufVendor.getOurMoble());//BD手机号
                registerSupplierRequest.setBdEmail(ufVendor.getBdEmail());//BD邮箱地址
                registerSupplierRequest.setEmail(ufVendor.getVendorEmail());//供方邮箱
                registerSupplierRequest.setMobile("15912341234");//手机 全局唯一、作为登录账户使用
                registerSupplierRequest.setContactor(ufVendor.getPersonInCharge());//联系人
                if (ufVendor.getWhetherTheMargin() == 1){
                    registerSupplierRequest.setEnableDeposit(true);//是否开启保证金
                    registerSupplierRequest.setTotalAmount(ufVendor.getMarginAmount());//保证金金额，设定保证金时必填
                    registerSupplierRequest.setExpiryStartDate(DateUtil.stringToLong(ufVendor.getValidityStartDate(),"yyyy-MM-dd HH:mm:ss"));//有效期开始时间，设定保证金时必填
                    registerSupplierRequest.setExpiryEndDate(DateUtil.stringToLong(ufVendor.getValidityEndDate(),"yyyy-MM-dd HH:mm:ss"));//有效期结束时间，设定保证金时必填
                }
                registerSupplierRequest.setChargeByName(ufVendor.getPersonInCharge());//负责人
                OptUser optUser = new OptUser();
                optUser.setOptUserId(ufVendor.getModedatacreater()+"mis");
                optUser.setOptUserName(ufVendor.getCreater_name()+"mis");
                registerSupplierRequest.setOptUser(optUser);
                RegisterSupplierResponse registerSupplierResponse = misContractRemote.registerSupplier(registerSupplierRequest);
                if (registerSupplierResponse.getReturnInfo().isSuccess()){
                    ufVendor.setBossId(registerSupplierResponse.getId());
                    ufVendor.setBossFlag(2);
                    iufVendorService.updateBossReturnData(ufVendor);
                    log.info("【MIS->BOSS】新增【Vendor】:"+ JsonUtil.toJson(ufVendor));
                } else {
                    log.error(JsonUtil.toJson(registerSupplierRequest));
                    log.error(JsonUtil.toJson(registerSupplierResponse.getReturnInfo()));
                }
            }
//        }
        return JsonUtil.toJson(list);
    }

    private void downloadFormFTP(String localpath,String ftppath,String filename,String type){
        try{
            File file = new File(localpath+"/"+filename+".zip");
            if(file.exists()){
                file.delete();
            }
            FtpUtil.downloadFtpFile(ftp_host, ftp_username, ftp_password, 21, ftppath, localpath, filename+".zip");
            ZipUtil.unzip(localpath+"/"+filename+".zip");
            uploadFormLocal(localpath+"/"+filename,filename);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void uploadFormLocal(String localFilePath,String key){
        Auth auth = Auth.create(qiniu_accesskey, qiniu_secretkey);
        String upToken = auth.uploadToken(qiniu_bucketname,key);
        UploadManager uploadManager = new UploadManager();
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    public boolean checkVendor(UFVendorWithResource record){
        boolean flage = true;
        if (record.getVendorAttr() == null){
            log.error("供方属性vendorattr为空");
            flage = false;
        } else{
            if (record.getVendorAttr() == 1){
                if (StringUtil.isEmpty(record.getVendorType())){
                    log.error("供方类型vendortype为空");
                    flage = false;
                }
            }
        }
        if (StringUtil.isEmpty(record.getVendor())){
            log.error("供方全称vendor为空");
            flage = false;
        }
        if (StringUtil.isEmpty(record.getShortName())){
            log.error("供方简称shortname为空");
        }
        if (StringUtil.isEmpty(record.getAddress())){
            log.error("供方所在地address为空");
            flage = false;
        }
        if (StringUtil.isEmpty(record.getVendorComment())){
            log.error("证照描述vendorcomment为空");
            flage = false;
        }
        if (StringUtil.isEmpty(record.getPhoto())){
            log.error("证照图片地址photo为空");
            flage = false;
        }
        if (StringUtil.isEmpty(record.getOur_contract_name())){
            log.error("BD真实姓名lastname为空");
            flage = false;
        }
        if (StringUtil.isEmpty(record.getOurMoble())){
            log.error("BD手机号ourmoble为空");
            flage = false;
        }
        if (StringUtil.isEmpty(record.getBdEmail())){
            log.error("BD邮箱地址bdemail为空");
            flage = false;
        }
        if (StringUtil.isEmpty(record.getVendorEmail())){
            log.error("供方邮箱vendoremail为空");
            flage = false;
        }
        if (StringUtil.isEmpty(record.getBusinessMoble())){
            log.error("手机号businessmoble为空");
            flage = false;
        }
        if (StringUtil.isEmpty(record.getPersonInCharge())){
            log.error("联系人personincharge为空");
            flage = false;
        }
        if (record.getWhetherTheMargin() == null){
            log.error("是否开启保证金whetherthemargin为空");
            flage = false;
        } else{
            if (record.getWhetherTheMargin() == 1){
                if (record.getMarginAmount() == null){
                    log.error("保证金金额marginamount为空");
                    flage = false;
                }
                if (StringUtil.isEmpty(record.getValidityStartDate())){
                    log.error("有效期开始时间validitystartdate为空");
                    flage = false;
                }
                if (StringUtil.isEmpty(record.getValidityEndDate())){
                    log.error("有效期开始时间validityenddate为空");
                    flage = false;
                }
            }
        }
        if (!flage){
            log.error(JsonUtil.toJson(record));
        }
        return flage;
    }

    @RequestMapping("updateVendor")
    public void updateVendor(){
        List<UFVendorWithResource> list = iufVendorService.selectAllNeedUpdate();
        for (int i=0;i<list.size();i++){
            UFVendorWithResource ufVendor = list.get(i);
            UpdateSupplierRequest updateSupplierRequest = new UpdateSupplierRequest();
            updateSupplierRequest.setRequestToken(getRequestToken());
            updateSupplierRequest.setSupplierId(ufVendor.getBossId());
            if (ufVendor.getVendorAttr() == 0){
                updateSupplierRequest.setSupplierScope(0);//供方属性
                List<Long> ids = new ArrayList<>();
                ids.add(224l);
                updateSupplierRequest.setPropIdList(ids);//供方类型
            } else {
                updateSupplierRequest.setSupplierScope(1);//供方属性
                updateSupplierRequest.setSelfProviderType(Integer.valueOf(ufVendor.getVendorType())+1);//供方类型
            }
            updateSupplierRequest.setFullName(ufVendor.getVendor());//供方全称
            updateSupplierRequest.setShortName(ufVendor.getShortName());//供方简称
            updateSupplierRequest.setAddress(ufVendor.getAddress());//供方所在地
            updateSupplierRequest.setLicense(ufVendor.getVendorComment()+" "+ufVendor.getPhoto());//证照信息
            updateSupplierRequest.setBdName(ufVendor.getOur_contract_name());//BD的真实姓名
            updateSupplierRequest.setBdMobile(ufVendor.getOurMoble());//BD手机号
            updateSupplierRequest.setBdEmail(ufVendor.getBdEmail());//BD邮箱地址
            updateSupplierRequest.setEmail(ufVendor.getVendorEmail());//供方邮箱
            updateSupplierRequest.setMobile(ufVendor.getBusinessMoble());//手机 全局唯一、作为登录账户使用
            updateSupplierRequest.setContactor(ufVendor.getPersonInCharge());//联系人
            if (ufVendor.getWhetherTheMargin() == 1){
                updateSupplierRequest.setEnableDeposit(true);//是否开启保证金
                updateSupplierRequest.setTotalAmount(ufVendor.getMarginAmount());//保证金金额，设定保证金时必填
                updateSupplierRequest.setExpiryStartDate(DateUtil.stringToLong(ufVendor.getValidityStartDate(),"yyyy-MM-dd HH:mm:ss"));//有效期开始时间，设定保证金时必填
                updateSupplierRequest.setExpiryEndDate(DateUtil.stringToLong(ufVendor.getValidityEndDate(),"yyyy-MM-dd HH:mm:ss"));//有效期结束时间，设定保证金时必填
            }
            updateSupplierRequest.setChargeByName(ufVendor.getPersonInCharge());//负责人
            OptUser optUser = new OptUser();
            optUser.setOptUserId(ufVendor.getModedatacreater()+"mis");
            optUser.setOptUserName(ufVendor.getCreater_name()+"mis");
            updateSupplierRequest.setOptUser(optUser);
            UpdateSupplierResponse updateSupplierResponse = misContractRemote.updateSupplier(updateSupplierRequest);
            if (updateSupplierResponse.getReturnInfo().isSuccess()){
                ufVendor.setBossFlag(2);
                iufVendorService.updateBossReturnData(ufVendor);
                log.info("【MIS->BOSS】更新【Vendor】:"+ JsonUtil.toJson(ufVendor));
            } else {
                log.error(JsonUtil.toJson(updateSupplierRequest));
                log.error(JsonUtil.toJson(updateSupplierResponse));
            }
        }

    }

    @RequestMapping("addbankaccount")
    private void insertBankAccount(){
        List<UFVendorDT1WithResource> list = iufVendorDT1Service.selectAllNeedInit();
        for (int i=0;i<list.size();i++){
            UFVendorDT1WithResource record = list.get(i);
            AddBankAccountRequest addBankAccountRequest = new AddBankAccountRequest();
            addBankAccountRequest.setRequestToken(getRequestToken());
            addBankAccountRequest.setType(record.getBankType());
            addBankAccountRequest.setSupplierId(record.getBossmainid());
            addBankAccountRequest.setAccountName(record.getAccountname());
            addBankAccountRequest.setBankName(record.getBank());
            addBankAccountRequest.setAccountCode(record.getAccount());
            AddBankAccountResponse addBankAccountResponse = misContractRemote.addBankAccount(addBankAccountRequest);
            if (addBankAccountResponse.getReturnInfo().isSuccess()){
                record.setBossId(addBankAccountResponse.getId());
                record.setUpdateFlag(2);
                iufVendorDT1Service.updateBossReturnData(record);
                log.info("【MIS->BOSS】新增【BankAccount】:"+ JsonUtil.toJson(record));
            }
        }
    }

    @RequestMapping("updatebankaccount")
    private void updateBankAccount(){
        List<UFVendorDT1WithResource> list = iufVendorDT1Service.selectAllNeedUpdate();
        for (int i=0;i<list.size();i++){
            UFVendorDT1WithResource record = list.get(i);
            UpdateBankAccountRequest updateBankAccountRequest = new UpdateBankAccountRequest();
            updateBankAccountRequest.setRequestToken(getRequestToken());
            updateBankAccountRequest.setId(record.getBossId());
            updateBankAccountRequest.setAccountName(record.getAccountname());
            updateBankAccountRequest.setBankName(record.getBank());
            updateBankAccountRequest.setAccountCode(record.getAccount());
            UpdateBankAccountResponse updateBankAccountResponse = misContractRemote.updateBankAccount(updateBankAccountRequest);
            if (updateBankAccountResponse.getReturnInfo().isSuccess()){
                record.setUpdateFlag(2);
                iufVendorDT1Service.updateBossReturnData(record);
                log.info("【MIS->BOSS】更新【BankAccount】:"+ JsonUtil.toJson(record));
            }
        }
    }

    @RequestMapping("addSupplierContract")
    @ResponseBody
    private String addSupplierContract(){
        List<UFContractAWithVendor> list = iufContractAService.selectAllNeedInit();
        for (UFContractAWithVendor record :list){
            AddSupplierContractRequest request = new AddSupplierContractRequest();
            request.setRequestToken(getRequestToken());
            OptUser optUser = new OptUser();
            optUser.setOptUserId(record.getModedatacreater()+"");
            optUser.setOptUserName(record.getCreatername());
            request.setOptUser(optUser);
            request.setSupplierId(Long.valueOf(record.getParty_b()));
            request.setCodeOA(record.getContract_code());
            request.setComment(record.getComment());
            request.setEndDate(DateUtil.stringToLong(record.getContract_end_date(),"yyyy-MM-dd"));
            request.setStartDate(DateUtil.stringToLong(record.getContract_start_date(),"yyyy-MM-dd"));
            request.setSignDate(DateUtil.stringToLong(record.getSign_date(),"yyyy-MM-dd"));
            request.setFromto(DateUtil.formatDateTime(record.getContract_start_date(),DateStyle.MM_DD)+""+
            DateUtil.formatDateTime(record.getContract_end_date(),DateStyle.MM_DD));
            request.setPayPeriod(record.getSettlement_cycle());
            request.setPayType(record.getSettlement_type());
            request.setSupplierBankAccountId(record.getBankid());
            request.setSupplierContactor(record.getFinancial_contact());
            request.setSupplierContactorMobile(record.getFinancial_moble());
            request.setSupplierEmail(record.getVendor_email());
            request.setSupplierMobile(record.getBusiness_moble());
            request.setSupplierPerson(record.getPerson_in_charge());
            request.setSupplierSignUser(record.getPerson_in_charge());
            request.setSettleInfoList(getSettleInfoList(record.getId()));
            AddSupplierContractResponse response = misContractRemote.addSupplierContract(request);
            if (response.getReturnInfo().isSuccess()){
                log.info("【MIS->BOSS】新增【SupplierContract】"+JsonUtil.toJson(record));
                record.setBoss_contractcode(response.getCode());
                record.setUpdate_flag(2);
                iufContractAService.updateBossReturnData(record);
            } else {
                log.error(JsonUtil.toJson(request));
                log.error(JsonUtil.toJson(response));
            }
        }
        return JsonUtil.toJson(list);
   }

    //添加供方和同信息
    public void addSupplierSettleInfo(UFContractAWithVendor record) {
        AddSupplierSettleInfoRequest request = new AddSupplierSettleInfoRequest();
        request.setRequestToken(getRequestToken());
        OptUser optUser = new OptUser();
        optUser.setOptUserId(record.getModedatacreater()+"");
        optUser.setOptUserName(record.getCreatername());
        request.setOptUser(optUser);
        request.setContractCode(record.getBoss_contractcode());
        request.setSettleInfoList(getSettleInfoList(record.getId()));
        AddSupplierSettleInfoResponse response = misContractRemote.addSupplierSettleInfo(request);
        if (response.getReturnInfo().isSuccess()){
            log.info("【MIS->BOSS】新增【SupplierSettleInfo】"+JsonUtil.toJson(record));
        } else {
            log.error(JsonUtil.toJson(request));
            log.error(JsonUtil.toJson(response));
        }
    }

    @RequestMapping("updateSupplierContract")
    @ResponseBody
    public String updateSupplierContract() {
        List<UFContractAWithVendor> list = iufContractAService.selectAllNeedUpdate();
        for (UFContractAWithVendor record : list){
            boolean flag1 = true;
            boolean flag2 = true;
            boolean flag3 = true;
            //UpdateSupplierContractRequest
            UpdateSupplierContractRequest request = new UpdateSupplierContractRequest();
            request.setRequestToken(getRequestToken());
            OptUser optUser = new OptUser();
            optUser.setOptUserId(record.getModedatacreater()+"");
            optUser.setOptUserName(record.getCreatername());
            request.setOptUser(optUser);
            request.setContractCode(record.getBoss_contractcode()+"");
            request.setSupplierId(record.getBoss_id());
            request.setCodeOA(record.getContract_code());
            request.setSettleInfoList(getSettleInfoList(record.getId()));
            UpdateSupplierContractResponse response = misContractRemote.updateSupplierContract(request);
            if (response.getReturnInfo().isSuccess()){

            } else {
                log.error(JsonUtil.toJson(request));
                log.error(JsonUtil.toJson(response));
                flag1 = false;
            }
            //DelaySupplierContractRequest
            DelaySupplierContractRequest request2 = new DelaySupplierContractRequest();
            request2.setRequestToken(getRequestToken());
            OptUser optUser2 = new OptUser();
            optUser2.setOptUserId(record.getModedatacreater()+"");
            optUser2.setOptUserName(record.getCreatername());
            request2.setOptUser(optUser);
            request2.setContractCode(record.getBoss_contractcode()+"");
            request2.setDelayEndDateTo(DateUtil.stringToLong(record.getContract_end_date(),"yyyy-MM-dd"));
            DelaySupplierContractResponse response2 = misContractRemote.delaySupplierContract(request2);
            if (response2.getReturnInfo().isSuccess()){

            } else {
                log.error(JsonUtil.toJson(request2));
                log.error(JsonUtil.toJson(response2));
                flag2 = false;
            }
            //AddSupplierSettleInfo
            AddSupplierSettleInfoRequest request3 = new AddSupplierSettleInfoRequest();
            request3.setRequestToken(getRequestToken());
            OptUser optUser3 = new OptUser();
            optUser3.setOptUserId(record.getModedatacreater()+"");
            optUser3.setOptUserName(record.getCreatername());
            request3.setOptUser(optUser);
            request3.setContractCode(record.getBoss_contractcode()+"");
            request3.setSettleInfoList(getSettleInfoList(record.getId()));
            AddSupplierSettleInfoResponse response3 = misContractRemote.addSupplierSettleInfo(request3);
            if (response3.getReturnInfo().isSuccess()){

            } else {
                log.error(JsonUtil.toJson(request3));
                log.error(JsonUtil.toJson(response3));
                flag3 = false;
            }
            if (flag1 && flag2 && flag3){
                log.info("【MIS->BOSS】更新【SupplierContract】"+JsonUtil.toJson(record));
                record.setUpdate_flag(2);
                iufContractAService.updateBossReturnData(record);
            }

        }
        return JsonUtil.toJson(list);
    }



    private List<SupplierContractSettleInfo> getSettleInfoList(Integer mainid){
        List<UFCategory> categoryList = iufCategoryService.selectAll();
        List<SupplierContractSettleInfo> settleInfoList = Lists.newArrayList();
        List<UFCategoryWithContractA> listC = iufContractAService.selectCategoryByMainId(mainid);
        for (UFCategoryWithContractA category:listC){
            SupplierContractSettleInfo e = new SupplierContractSettleInfo();
            e.setCategoryId3(Long.valueOf(category.getThird_category()));
            for (int i=0;i<categoryList.size();i++){
                UFCategory thrCate = categoryList.get(i);
                String secId;
                if (thrCate.getCategory_bossid().equals(category.getThird_category())){
                    secId = thrCate.getFid();
                    e.setCategoryId2(Long.valueOf(secId));
                    String rootId;
                    for (int j=0;j<categoryList.size();j++){
                        UFCategory secCate = categoryList.get(j);
                        if (secCate.getCategory_bossid().equals(secId)){
                            rootId = secCate.getFid();
                            e.setCategoryId1(Long.valueOf(rootId));
                            break;
                        }
                    }
                }
            }
            e.setCommission(category.getCategory_rate().doubleValue());
            settleInfoList.add(e);
        }
        return settleInfoList;
    }

    @RequestMapping("getcategorylist")
    @ResponseBody
    private String queryCategoryList(){
        List<CategoryInfo> lastlist = new ArrayList<>();
        List<CategoryInfo> rootlist = new ArrayList<>();
        GetCategoryListRequest req = new GetCategoryListRequest();
        GetCategoryListResponse resp = misContractRemote.getCategoryList(req);
        rootlist = resp.getCategoryInfoList();
        lastlist.addAll(rootlist);
        lastlist = getcategory(rootlist,lastlist);

        List<UFCategory> insertList = new ArrayList<>();
        List<UFCategory> updateList = new ArrayList<>();
        List<UFCategory> tmpList = iufCategoryService.selectAll();
        List<UFCategory> checkList = new ArrayList<>();
        for (CategoryInfo categoryInfo:lastlist){
            UFCategory ufCategory = new UFCategory();
            ufCategory.setCategory_bossid(categoryInfo.getId()+"");
            ufCategory.setCategory_leve(categoryInfo.getPath().split("-").length);
            ufCategory.setCategory_name(categoryInfo.getName());
            ufCategory.setFid(categoryInfo.getFid()+"");
            String names[] = categoryInfo.getPath().split("-");
            if (names.length == 3){
                ufCategory.setTop_category(names[0]);
                ufCategory.setSecond_category(names[1]);
            } else if (names.length == 2){
                ufCategory.setTop_category(names[0]);
            }
            ufCategory.setCategory_all(categoryInfo.getPath());
            checkList.add(ufCategory);
        }

        for (int i=0;i<checkList.size();i++){
            UFCategory ufCategory = checkList.get(i);
            boolean updateflage = false;
            for (UFCategory tmpCategory:tmpList){
                if (tmpCategory.getCategory_bossid().equals(ufCategory.getCategory_bossid())){
                    updateflage = true;
                    if (!EqualsUtil.domainEquals(tmpCategory,ufCategory)){
                        updateList.add(ufCategory);
                    }
                }
            }
            if (!updateflage){
                insertList.add(ufCategory);
            }
            if ((i+1)%40 == 0 || checkList.size()-(i+1) == 0){
                if (updateList.size()>0){
                    iufCategoryService.updateBatch(updateList);
                }
                if (insertList.size()>0){
                    iufCategoryService.insertBatch(insertList);
                }
                updateList.clear();
                insertList.clear();
            }
        }

        return JsonUtil.toJson(lastlist);
    }

    @RequestMapping("categoryTest/{fid}")
    @ResponseBody
    private String categoryTest(@PathVariable Long fid){
        GetCategoryListRequest req = new GetCategoryListRequest();
        req.setFid(fid);
        GetCategoryListResponse resp = misContractRemote.getCategoryList(req);
        return JsonUtil.toJson(resp);
    }

    private List<CategoryInfo> getcategory(List<CategoryInfo> list,List<CategoryInfo> tmplist){
        if (list != null && list.size()>0){
            for (CategoryInfo categoryInfo:list){
                GetCategoryListRequest req = new GetCategoryListRequest();
                req.setFid(categoryInfo.getId());
                GetCategoryListResponse resp = misContractRemote.getCategoryList(req);
                if (resp.getCategoryInfoList() != null && resp.getCategoryInfoList().size()>0){
                    tmplist.addAll(resp.getCategoryInfoList());
                    log.info("categoryList:"+JsonUtil.toJson(resp.getCategoryInfoList()));
                    getcategory(resp.getCategoryInfoList(),tmplist);
                }
            }
        }
        return tmplist;
    }

    @RequestMapping("getproplist")
    @ResponseBody
    private String getPropList(){
        try {
            GetSelfProviderTypeRequest getSelfProviderTypeRequest = new GetSelfProviderTypeRequest();
            GetSelfProviderTypeResponse resp = misContractRemote.getSelfProviderTypeList(getSelfProviderTypeRequest);
            for (SelfProviderType selfProviderType:resp.getSelfProviderTypeList()){
                System.out.println("id:"+selfProviderType.getType()+",name:"+selfProviderType.getName());
            }
            return resp.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "false";
    }

    @RequestMapping("getselfprovidertypelist")
    @ResponseBody
    private String getSelfProviderTypeList(){
        try {
            GetPropListRequest getPropListRequest = new GetPropListRequest();
            GetPropListResponse proresp = misContractRemote.getPropList(getPropListRequest);
            for (PropInfo propInfo:proresp.getPropInfoList()){
                System.out.println("id:"+propInfo.getId()+",name:"+propInfo.getName());
            }
            return proresp.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "false";
    }
}
