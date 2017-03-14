package com.cn.mis.timetask;

import com.cn.mis.domain.bean.TokenInfo;
import com.cn.mis.utils.http.HttpClientUtil;
import com.cn.mis.utils.json.JsonUtil;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by yuejia on 2017/3/14.
 */
@Log4j
public class XCTicketThread implements Runnable{
    public static String appKey;
    public static String appSecurity;
    public static String CorporationID;

    public static String ticket;
    private void init(){
        log.info("初始化【XCTicketThread】，执行init()方法读取配置文件");
        try {
            Properties propFile = new Properties();
            InputStream instream = getClass().getResourceAsStream("/token.properties");
            propFile.load(instream);
            appKey = propFile.getProperty("xiecheng.appKey");
            appSecurity = propFile.getProperty("xiecheng.appSecurity");
            CorporationID = propFile.getProperty("xiecheng.CorporationID");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            log.error("加载属性文件失败");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("加载属性文件失败");
        }
    }

    @Override
    public void run() {
        init();
        while (true) {
            try {
                ticket = getTicket();
                if (null != ticket && !"".equals(ticket)) {
                    log.info("获取携程ticket成功，有效时长秒{"+2*60*60*1000+"} ticket:{"+ticket+"}");
                    Thread.sleep(60*1000);
                } else {
                    // 如果access_token为null，60秒后再获取
                    log.info("获取携程ticket失败，60s后重试");
                    Thread.sleep(2 * 60 * 60 * 1000);
                }
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }

    }

    public String getTicket(){

        HashMap<String,String> tokenParams = new HashMap<String,String>();
        tokenParams.put("appKey",appKey);
        tokenParams.put("appSecurity",appSecurity);
        String tmpticket = HttpClientUtil.sendPostSSLRequest("https://www.corporatetravel.ctrip.com/SwitchAPI/Order/Ticket", tokenParams);
        return tmpticket;
    }
}
