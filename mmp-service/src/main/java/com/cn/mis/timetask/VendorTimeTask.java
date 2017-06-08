package com.cn.mis.timetask;

import com.caucho.hessian.client.HessianProxyFactory;
import com.cn.mis.domain.entity.mis.*;
import com.cn.mis.service.mis.IUFCategoryService;
import com.cn.mis.service.mis.IUFContractAService;
import com.cn.mis.service.mis.IUFVendorDT1Service;
import com.cn.mis.service.mis.IUFVendorService;
import com.cn.mis.utils.date.DateStyle;
import com.cn.mis.utils.date.DateUtil;
import com.cn.mis.utils.equals.EqualsUtil;
import com.cn.mis.utils.ftp.FtpUtil;
import com.cn.mis.utils.json.JsonUtil;
import com.cn.mis.utils.zip.ZipUtil;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.qding.brick.remote.contract.mis.MISContractRemote;
import com.qding.brick.struts.entity.mis.CategoryInfo;
import com.qding.brick.struts.entity.mis.PropInfo;
import com.qding.brick.struts.entity.mis.SelfProviderType;
import com.qding.brick.struts.entity.mis.SupplierContractSettleInfo;
import com.qding.brick.struts.request.mis.*;
import com.qding.brick.struts.response.mis.*;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import javafx.scene.Parent;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuejia on 2017/5/31.
 */
@Log4j
@Component("vendorTimeTask")
public class VendorTimeTask {
    @Resource
    private IUFVendorService iufVendorService;

    @Resource
    private IUFVendorDT1Service iufVendorDT1Service;

    @Resource
    private IUFCategoryService iufCategoryService;

    @Resource
    private IUFContractAService iufContractAService;

    private MISContractRemote misContractRemote;

    private String miscontract_rpc_url="";

    private String ftp_host;
    private String ftp_username;
    private String ftp_password;

    private String qiniu_bucketname;
    private String qiniu_accesskey;
    private String qiniu_secretkey;

    @PostConstruct
    private void init(){
        try {
            Properties propFile = new Properties();
            InputStream instream = getClass().getResourceAsStream("/rpc.properties");
            propFile.load(instream);
            miscontract_rpc_url = propFile.getProperty("miscontract.rpc.url");
            HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
            hessianProxyFactory.setOverloadEnabled(false);
            hessianProxyFactory.setChunkedPost(false);
            misContractRemote = (MISContractRemote) hessianProxyFactory.create(MISContractRemote.class,
                    miscontract_rpc_url);

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
            log.info("【VendorTimeTask】初始化成功");
        } catch (Exception e) {
            log.info("【VendorTimeTask】初始化失败");
            e.printStackTrace();
        }
    }

    private void downloadFormFTP(String localpath,String ftppath,String filename,String type){
        try{
            File file = new File(localpath+"/"+filename+".zip");
            if(file.exists()){
                file.delete();
            }
            FtpUtil.downloadFtpFile(ftp_host, ftp_username, ftp_password, 21, ftppath, localpath, filename);
            ZipUtil.unzip(localpath+"/"+filename+".zip");
            uploadFormLocal(localpath+"/"+filename,filename+"."+type);
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

    public void run(){
        insertVendor();
        updateVendor();
        insertBankAccount();
        updateBankAccount();
        addSupplierContract();
        addSupplierSettleInfo();
        updateSupplierContract();
    }

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

    public String insertVendor(){
        List<UFVendorWithResource> list = iufVendorService.selectAllNeedInit();
        for (int i=0;i<list.size();i++){
            UFVendorWithResource ufVendor = list.get(i);
            if (checkVendor(ufVendor)){
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

                //===========================================
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
                        commentname.add(filename.replace(".zip","")+"."+type);
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
                        photoname.add(filename.replace(".zip","")+"."+type);
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
                        registerSupplierRequest.setLicense(ufVendor.getVendorComment()+" "+"http://img1.qdingnet.com/"+photoname.get(0));//证照信息
                    }
                } else {
                    registerSupplierRequest.setLicense(ufVendor.getVendorComment());
                }
                //===========================================
                registerSupplierRequest.setBdName(ufVendor.getOur_contract_name());//BD的真实姓名
                registerSupplierRequest.setBdMobile(ufVendor.getOurMoble());//BD手机号
                registerSupplierRequest.setBdEmail(ufVendor.getBdEmail());//BD邮箱地址
                registerSupplierRequest.setEmail(ufVendor.getVendorEmail());//供方邮箱
                registerSupplierRequest.setMobile(ufVendor.getBusinessMoble());//手机 全局唯一、作为登录账户使用
                registerSupplierRequest.setContactor(ufVendor.getPersonInCharge());//联系人
                if (ufVendor.getWhetherTheMargin() == 0){
                    registerSupplierRequest.setEnableDeposit(true);//是否开启保证金
                    registerSupplierRequest.setTotalAmount(ufVendor.getMarginAmount());//保证金金额，设定保证金时必填
                    registerSupplierRequest.setExpiryStartDate(DateUtil.stringToLong(ufVendor.getValidityStartDate(),"yyyy-MM-dd"));//有效期开始时间，设定保证金时必填
                    registerSupplierRequest.setExpiryEndDate(DateUtil.stringToLong(ufVendor.getValidityEndDate(),"yyyy-MM-dd"));//有效期结束时间，设定保证金时必填
                    registerSupplierRequest.setChargeByName(ufVendor.getPersonInCharge());//负责人
                }
                OptUser optUser = new OptUser();
                optUser.setOptUserId(ufVendor.getModedatacreater()+"");
                optUser.setOptUserName(ufVendor.getCreater_name()+"");
                registerSupplierRequest.setOptUser(optUser);
                RegisterSupplierResponse registerSupplierResponse = misContractRemote.registerSupplier(registerSupplierRequest);
                if (registerSupplierResponse.getReturnInfo().isSuccess()){
                    ufVendor.setBossId(registerSupplierResponse.getId());
                    ufVendor.setBossFlag(2);
                    iufVendorService.updateBossReturnData(ufVendor);
                    log.info("【MIS->BOSS】新增【Vendor】:"+ JsonUtil.toJson(ufVendor));
                } else {
                    log.error("【MIS->BOSS】新增【Vendor】:"+JsonUtil.toJson(registerSupplierRequest));
                    log.error("【MIS->BOSS】新增【Vendor】:"+JsonUtil.toJson(registerSupplierResponse.getReturnInfo()));
                }
            }
        }
        return JsonUtil.toJson(list);
    }

    public boolean checkVendor(UFVendorWithResource record){
        boolean flage = true;
        if (record.getVendorAttr() == null){
            flage = false;
        } else{
            if (record.getVendorAttr() == 1){
                if (StringUtil.isEmpty(record.getVendorType())){
                    flage = false;
                }
            }
        }
        if (StringUtil.isEmpty(record.getVendor())){
            flage = false;
        }
        if (StringUtil.isEmpty(record.getShortName())){
            flage = false;
        }
        if (StringUtil.isEmpty(record.getAddress())){
            flage = false;
        }
//        if (StringUtil.isEmpty(record.getOur_contract_name())){
//            flage = false;
//        }
//        if (StringUtil.isEmpty(record.getOurMoble())){
//            flage = false;
//        }
//        if (StringUtil.isEmpty(record.getBdEmail())){
//            flage = false;
//        }
//        if (StringUtil.isEmpty(record.getVendorEmail())){
//            flage = false;
//        }
        if (StringUtil.isEmpty(record.getBusinessMoble())){
            flage = false;
        }
        if (record.getWhetherTheMargin() == null){
            flage = false;
        } else{
            if (record.getWhetherTheMargin() == 0){
                if (record.getMarginAmount() == null){
                    flage = false;
                }
                if (StringUtil.isEmpty(record.getValidityStartDate())){
                    flage = false;
                }
                if (StringUtil.isEmpty(record.getValidityEndDate())){
                    flage = false;
                }
                if (StringUtil.isEmpty(record.getPersonInCharge())){
                    flage = false;
                }
            }
        }
        if (!flage){
            log.error("【VendorTimeTask->Vendor】必填项为空:"+JsonUtil.toJson(record));
        }
        return flage;
    }

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
            //===========================================
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
                    commentname.add(filename);
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
                    photoname.add(filename);
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
                    updateSupplierRequest.setLicense(comm+" "+"http://img1.qdingnet.com/"+photoname.get(i));//证照信息
                } else if (commentImgList.size()>0 && photoImgList.size() == 0){
                    updateSupplierRequest.setLicense(comm);//证照信息
                } else if (commentImgList.size()==0 && photoImgList.size()>0){
                    updateSupplierRequest.setLicense(ufVendor.getVendorComment()+" "+"http://img1.qdingnet.com/"+photoname.get(i));//证照信息
                }
            } else {
                updateSupplierRequest.setLicense(ufVendor.getVendorComment());
            }
            //===========================================
            updateSupplierRequest.setBdName(ufVendor.getOur_contract_name());//BD的真实姓名
            updateSupplierRequest.setBdMobile(ufVendor.getOurMoble());//BD手机号
            updateSupplierRequest.setBdEmail(ufVendor.getBdEmail());//BD邮箱地址
            updateSupplierRequest.setEmail(ufVendor.getVendorEmail());//供方邮箱
            updateSupplierRequest.setMobile(ufVendor.getBusinessMoble());//手机 全局唯一、作为登录账户使用
            updateSupplierRequest.setContactor(ufVendor.getPersonInCharge());//联系人
            if (ufVendor.getWhetherTheMargin() == 0){
                updateSupplierRequest.setEnableDeposit(true);//是否开启保证金
                updateSupplierRequest.setTotalAmount(ufVendor.getMarginAmount());//保证金金额，设定保证金时必填
                updateSupplierRequest.setExpiryStartDate(DateUtil.stringToLong(ufVendor.getValidityStartDate(),"yyyy-MM-dd HH:mm:ss"));//有效期开始时间，设定保证金时必填
                updateSupplierRequest.setExpiryEndDate(DateUtil.stringToLong(ufVendor.getValidityEndDate(),"yyyy-MM-dd HH:mm:ss"));//有效期结束时间，设定保证金时必填
            }
            updateSupplierRequest.setChargeByName(ufVendor.getPersonInCharge());//负责人
            OptUser optUser = new OptUser();
            optUser.setOptUserId(ufVendor.getModedatacreater()+"");
            optUser.setOptUserName(ufVendor.getCreater_name()+"");
            updateSupplierRequest.setOptUser(optUser);
            UpdateSupplierResponse updateSupplierResponse = misContractRemote.updateSupplier(updateSupplierRequest);
            if (updateSupplierResponse.getReturnInfo().isSuccess()){
                ufVendor.setBossFlag(2);
                iufVendorService.updateBossReturnData(ufVendor);
                log.info("【MIS->BOSS】更新【Vendor】:"+ JsonUtil.toJson(ufVendor));
            } else {
                log.error("【MIS->BOSS】更新【Vendor】:"+JsonUtil.toJson(updateSupplierRequest));
                log.error("【MIS->BOSS】更新【Vendor】:"+JsonUtil.toJson(updateSupplierResponse));
            }
        }

    }

    private void insertBankAccount(){
        List<UFVendorDT1WithResource> list = iufVendorDT1Service.selectAllNeedInit();
        for (int i=0;i<list.size();i++){
            UFVendorDT1WithResource record = list.get(i);
            if (checkBankAccount(record)){
                OptUser optUser = new OptUser();
                optUser.setOptUserId(record.getModedatacreater()+"");
                optUser.setOptUserName(record.getCreateBy());
                AddBankAccountRequest addBankAccountRequest = new AddBankAccountRequest();
                addBankAccountRequest.setRequestToken(getRequestToken());
                addBankAccountRequest.setType(1);
                addBankAccountRequest.setSupplierId(record.getBossmainid());
                addBankAccountRequest.setAccountName(record.getAccountname());
                addBankAccountRequest.setBankName(record.getBank());
                addBankAccountRequest.setAccountCode(record.getAccount());
                addBankAccountRequest.setOptUser(optUser);
                AddBankAccountResponse addBankAccountResponse = misContractRemote.addBankAccount(addBankAccountRequest);
                if (addBankAccountResponse.getReturnInfo().isSuccess()){
                    record.setBossId(addBankAccountResponse.getId());
                    record.setUpdateFlag(2);
                    iufVendorDT1Service.updateBossReturnData(record);
                    log.info("【MIS->BOSS】新增【BankAccount】:"+ JsonUtil.toJson(record));
                } else {
                    log.error("【MIS->BOSS】新增【BankAccount】:"+ JsonUtil.toJson(addBankAccountRequest));
                    log.error("【MIS->BOSS】新增【BankAccount】:"+ JsonUtil.toJson(addBankAccountResponse));
                }
            }
        }
    }

    private boolean checkBankAccount(UFVendorDT1WithResource record){
        boolean flag = true;
//        if (record.getBankType() == null){
//            flag = false;
//        }
        if (record.getBossmainid() == null){
            flag = false;
        }
        if (record.getAccountname() == null){
            flag = false;
        }
        if (record.getBank() == null){
            flag = false;
        }
        if (record.getAccount() == null){
            flag = false;
        }
        if (!flag){
            log.error("【VendorTimeTask->BankAccount】必填项为空:"+JsonUtil.toJson(record));
        }
        return flag;
    }

    private void updateBankAccount(){
        List<UFVendorDT1WithResource> list = iufVendorDT1Service.selectAllNeedUpdate();
        for (int i=0;i<list.size();i++){
            UFVendorDT1WithResource record = list.get(i);
            OptUser optUser = new OptUser();
            optUser.setOptUserId(record.getModedatacreater()+"");
            optUser.setOptUserName(record.getUpdateBy());
            UpdateBankAccountRequest updateBankAccountRequest = new UpdateBankAccountRequest();
            updateBankAccountRequest.setRequestToken(getRequestToken());
            updateBankAccountRequest.setId(record.getBossId());
            updateBankAccountRequest.setAccountName(record.getAccountname());
            updateBankAccountRequest.setBankName(record.getBank());
            updateBankAccountRequest.setAccountCode(record.getAccount());
            updateBankAccountRequest.setOptUser(optUser);
            UpdateBankAccountResponse updateBankAccountResponse = misContractRemote.updateBankAccount(updateBankAccountRequest);
            if (updateBankAccountResponse.getReturnInfo().isSuccess()){
                record.setUpdateFlag(2);
                iufVendorDT1Service.updateBossReturnData(record);
                log.info("【MIS->BOSS】更新【BankAccount】:"+ JsonUtil.toJson(record));
            } else {
                log.error("【MIS->BOSS】更新【BankAccount】:"+JsonUtil.toJson(updateBankAccountRequest));
                log.error("【MIS->BOSS】更新【BankAccount】:"+JsonUtil.toJson(updateBankAccountResponse));
            }
        }
    }

    private String addSupplierContract(){
        List<UFContractAWithVendor> list = iufContractAService.selectAllNeedInit();
        for (UFContractAWithVendor record :list){
            if (checkSupplierContract(record)){
                AddSupplierContractRequest request = new AddSupplierContractRequest();
                request.setRequestToken(getRequestToken());
                OptUser optUser = new OptUser();
                optUser.setOptUserId(record.getModedatacreater()+"");
                optUser.setOptUserName(record.getCreatername());
                request.setOptUser(optUser);
                request.setSupplierId(record.getBoss_id());
                request.setCodeOA(record.getContract_code());
                request.setComment(record.getComment());
                request.setEndDate(DateUtil.stringToLong(record.getContract_end_date(),"yyyy-MM-dd"));
                request.setStartDate(DateUtil.stringToLong(record.getContract_start_date(),"yyyy-MM-dd"));
                request.setSignDate(DateUtil.stringToLong(record.getSign_date(),"yyyy-MM-dd"));
                request.setFromto(record.getContract_start_date()+"-"+record.getContract_end_date());
                request.setPayPeriod(record.getSettlement_cycle());
                request.setPayType(record.getSettlement_type());
                request.setSupplierBankAccountId(record.getBankid());
                request.setSupplierContactor(record.getFinancial_contact());
                request.setSupplierContactorMobile(record.getFinancial_moble());
                request.setSupplierEmail(record.getVendor_email());
                request.setSupplierMobile(record.getBusiness_moble());
                request.setSupplierPerson(record.getPerson_in_charge());
                request.setSupplierSignUser(record.getPerson_in_charge());
                List<UFCategoryWithContractA> listC = getSettleInfoIds(record.getId());
                request.setSettleInfoList(getSettleInfoList(record.getId()));
                AddSupplierContractResponse response = misContractRemote.addSupplierContract(request);
                if (response.getReturnInfo().isSuccess()){
                    log.info("【MIS->BOSS】新增【SupplierContract】"+JsonUtil.toJson(record));
                    record.setBoss_contractcode(response.getCode());
                    record.setUpdate_flag(3);
                    iufContractAService.updateBossReturnData(record);
                    for (UFCategoryWithContractA category:listC){
                        iufContractAService.updateCategoryReturnData(category);
                    }
                } else {
                    log.error("【MIS->BOSS】新增【SupplierContract】"+JsonUtil.toJson(request));
                    log.error("【MIS->BOSS】新增【SupplierContract】"+JsonUtil.toJson(response));
                }
            }
        }
        return JsonUtil.toJson(list);
    }

    //添加供方和同信息
    public void addSupplierSettleInfo() {
        List<UFContractAWithVendor> list = iufContractAService.selectAllNeedAdd();
        for (UFContractAWithVendor record:list){
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
                log.info(JsonUtil.toJson(response));
                record.setUpdate_flag(2);
                iufContractAService.updateBossReturnData(record);
            } else {
                log.error(JsonUtil.toJson(request));
                log.error(JsonUtil.toJson(response));
            }
        }
    }

    private boolean checkSupplierContract(UFContractAWithVendor record){
        boolean flag = true;
        if (record.getModedatacreater() == null){
            flag = false;
        }
        if (record.getCreatername() == null && record.getModedatacreater() != 1){
            flag = false;
        }
        if (record.getParty_b() == null){
            flag = false;
        }
        if (record.getContract_code() == null){
            flag = false;
        }
        if (record.getContract_end_date() == null){
            flag = false;
        }
        if (record.getContract_start_date() == null){
            flag = false;
        }
        if (record.getSign_date() == null){
            flag = false;
        }
        if (record.getSettlement_cycle() == null){
            flag = false;
        }
        if (record.getSettlement_type() == null){
            flag = false;
        }
        if (record.getBankid() == null){
            flag = false;
        }
        if (record.getFinancial_contact() == null){
            flag = false;
        }
        if (record.getFinancial_moble() == null){
            flag = false;
        }
        if (record.getVendor_email() == null){
            flag = false;
        }
        if (record.getBusiness_moble() == null){
            flag = false;
        }
        if (record.getPerson_in_charge() == null){
            flag = false;
        }
        if (!flag){
            log.error("【VendorTimeTask->SupplierContract】必填项为空："+JsonUtil.toJson(record));
        }
        return flag;
    }

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
            request.setSupplierId(Long.valueOf(record.getParty_b()));
            request.setCodeOA(record.getContract_code());
            request.setSettleInfoList(getSettleInfoList(record.getId()));
            UpdateSupplierContractResponse response = misContractRemote.updateSupplierContract(request);
            if (response.getReturnInfo().isSuccess()){

            } else {
                log.error("【MIS->BOSS】更新【SupplierContract】"+JsonUtil.toJson(request));
                log.error("【MIS->BOSS】更新【SupplierContract】"+JsonUtil.toJson(response));
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
                log.error("【MIS->BOSS】更新【SupplierContract】"+JsonUtil.toJson(request2));
                log.error("【MIS->BOSS】更新【SupplierContract】"+JsonUtil.toJson(response2));
                flag2 = false;
            }
            //AddSupplierSettleInfo
//            AddSupplierSettleInfoRequest request3 = new AddSupplierSettleInfoRequest();
//            request3.setRequestToken(getRequestToken());
//            OptUser optUser3 = new OptUser();
//            optUser3.setOptUserId(record.getModedatacreater()+"");
//            optUser3.setOptUserName(record.getCreatername());
//            request3.setOptUser(optUser);
//            request3.setContractCode(record.getBoss_contractcode()+"");
//            request3.setSettleInfoList(getSettleInfoList(record.getId()));
//            AddSupplierSettleInfoResponse response3 = misContractRemote.addSupplierSettleInfo(request3);
//            if (response3.getReturnInfo().isSuccess()){
//
//            } else {
//                log.error("【MIS->BOSS】更新【SupplierContract】"+JsonUtil.toJson(request3));
//                log.error("【MIS->BOSS】更新【SupplierContract】"+JsonUtil.toJson(response3));
//                flag3 = false;
//            }
            if (flag1 && flag2 /*&& flag3*/){
                log.info("【MIS->BOSS】更新【SupplierContract】"+JsonUtil.toJson(record));
                record.setUpdate_flag(2);
                iufContractAService.updateBossReturnData(record);
            }

        }
        return JsonUtil.toJson(list);
    }
    private List<UFCategoryWithContractA> getSettleInfoIds(Integer mainid){
        List<UFCategory> categoryList = iufCategoryService.selectAll();
        List<SupplierContractSettleInfo> settleInfoList = Lists.newArrayList();
        List<UFCategoryWithContractA> listCN = iufContractAService.selectCategoryByMainIdNew(mainid);
        List<UFCategoryWithContractA> listCO = iufContractAService.selectCategoryByMainIdOld(mainid);
        List<UFCategoryWithContractA> listC = new ArrayList<>();
        for (UFCategoryWithContractA categoryN:listCN){
            boolean flag = true;
            for (UFCategoryWithContractA categoryO:listCO){
                if (categoryN.getThird_category().equals(categoryO.getThird_category())){
                    flag = false;
                }
            }
            if (flag){
                listC.add(categoryN);
            }
        }
        return listC;
    }
    private List<SupplierContractSettleInfo> getSettleInfoList(Integer mainid){
        List<UFCategory> categoryList = iufCategoryService.selectAll();
        List<SupplierContractSettleInfo> settleInfoList = Lists.newArrayList();
        List<UFCategoryWithContractA> listCN = iufContractAService.selectCategoryByMainIdNew(mainid);
        List<UFCategoryWithContractA> listCO = iufContractAService.selectCategoryByMainIdOld(mainid);
        List<UFCategoryWithContractA> listC = new ArrayList<>();
        for (UFCategoryWithContractA categoryN:listCN){
            boolean flag = true;
            for (UFCategoryWithContractA categoryO:listCO){
                if (categoryN.getThird_category().equals(categoryO.getThird_category())){
                    flag = false;
                }
            }
            if (flag){
                listC.add(categoryN);
            }
        }
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
}
