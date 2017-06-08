package com.cn.mis.web.controller;

import com.cn.mis.utils.ftp.FtpUtil;
import com.cn.mis.utils.zip.ZipUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yuejia on 2017/6/5.
 */
@Controller
@RequestMapping("ftp")
public class FTPDownloadController {
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
            InputStream instream = getClass().getResourceAsStream("/ftp.properties");
            propFile.load(instream);
            ftp_host = propFile.getProperty("ftp.host");
            ftp_username = propFile.getProperty("ftp.username");
            ftp_password = propFile.getProperty("ftp.password");

            Properties qiniuFile = new Properties();
            InputStream instream2 = getClass().getResourceAsStream("/qiniu.properties");
            qiniuFile.load(instream2);
            qiniu_bucketname = qiniuFile.getProperty("bucketName");
            qiniu_accesskey = qiniuFile.getProperty("accessKey");
            qiniu_secretkey = qiniuFile.getProperty("secretKey");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("download")
    @ResponseBody
    private void downloadFormFTP(){
        try{
            String loclpath = "/Users/yuejia/Downloads";
            String ftppath = "201701/A/";
            String filename = "eb7dc2f3-2b7e-4780-8a0c-54613c191d16";
            FtpUtil.downloadFtpFile(ftp_host, ftp_username, ftp_password, 21, ftppath, loclpath, filename);
            ZipUtil.unzip(loclpath+"/"+filename+".zip");
            uploadFormLocal(loclpath+"/"+filename,filename);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void uploadFormLocal(String localFilePath,String key){
        Auth auth = Auth.create(qiniu_accesskey, qiniu_secretkey);
        String upToken = auth.uploadToken(qiniu_bucketname);
        UploadManager uploadManager = new UploadManager();
        try {
            Response response = uploadManager.put(localFilePath, null, upToken);
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
}
