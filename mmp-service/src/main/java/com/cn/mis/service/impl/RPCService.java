package com.cn.mis.service.impl;

import com.caucho.hessian.client.HessianProxyFactory;
import com.qding.brick.remote.biz.IBizRemoteService;
import com.qding.brick.remote.biz.RegionRemote;
import com.qding.brick.struts.request.BizRemoteRequest;
import com.qding.brick.struts.response.BizRemoteResponse;
import com.qding.brick.struts.response.RegionResponse;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Properties;

/**
 * Created by yuejia on 2017/2/27.
 */
@Log4j
@Data
@Component
@Transactional
public class RPCService {
    private String propertyInfo_rpc_url="";
    private String regionremote_url="";

    private IBizRemoteService iBizRemoteService;

    private RegionRemote regionRemote;

    @PostConstruct
    public void init(){
        log.info("初始化【RPCService】，执行init()方法读取配置文件");
        HessianProxyFactory factory = new HessianProxyFactory();
        try{
            factory.setChunkedPost(false);
            factory.setOverloadEnabled(true);
            Properties propFile = new Properties();
            InputStream instream = getClass().getResourceAsStream("/rpc.properties");
            propFile.load(instream);
            propertyInfo_rpc_url = propFile.getProperty("propertyInfo.rpc.url");
            regionremote_url = propFile.getProperty("regionremote.url");
//            factory.setHessian2Reply(false);
//            factory.setHessian2Request(false);
            iBizRemoteService = (IBizRemoteService) factory.create(IBizRemoteService.class, propertyInfo_rpc_url);
            regionRemote = (RegionRemote) factory.create(RegionRemote.class,regionremote_url);
        }catch (MalformedURLException e){
            log.error("[BrickRPCService init error]" + e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param request
     * @return
     */

    public BizRemoteResponse insert(BizRemoteRequest request){
        return iBizRemoteService.insert(request);
    }

    public BizRemoteResponse updateBase(BizRemoteRequest request) { return iBizRemoteService.updateProjectBaseData(request);}

    public RegionResponse getRegionByName(String s){
        return regionRemote.getRegionByName(s);
    }

}
