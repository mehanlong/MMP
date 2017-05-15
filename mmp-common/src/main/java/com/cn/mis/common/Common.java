package com.cn.mis.common;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yuejia on 2017/3/16.
 */
@Log4j
@Component("common")
public class Common {
    //携程
    public static String appKey;
    public static String appSecurity;
    public static String CorporationID;
    public static String Version;

    //腾讯企业邮箱
    public static String tx_corpid;
    public static String tx_abcorpsecret;
    public static String tx_ssocorpsecret;
    public static String tx_recorpsecret;

    @PostConstruct
    private void init(){
        log.info("初始化【Common】，执行init()方法读取配置文件");
        try {
            Properties propFile = new Properties();
            InputStream instream = getClass().getResourceAsStream("/xiecheng.properties");
            propFile.load(instream);
            appKey = propFile.getProperty("xiecheng.appKey");
            appSecurity = propFile.getProperty("xiecheng.appSecurity");
            CorporationID = propFile.getProperty("xiecheng.CorporationID");
            Version = propFile.getProperty("xiecheng.Version");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            log.error("加载携程属性文件失败");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("加载携程属性文件失败");
        }

        try {
            Properties propFile = new Properties();
            InputStream instream = getClass().getResourceAsStream("/token.properties");
            propFile.load(instream);
            tx_corpid = propFile.getProperty("tx.crop.id");
            tx_abcorpsecret = propFile.getProperty("tx.crop.addressbook.secret");
            tx_ssocorpsecret = propFile.getProperty("tx.crop.sso.secret");
            tx_recorpsecret = propFile.getProperty("tx.crop.remind.secret");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            log.error("加载腾讯邮箱属性文件失败");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("加载腾讯邮箱属性文件失败");
        }
    }
}
