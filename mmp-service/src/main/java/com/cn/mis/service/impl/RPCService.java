package com.cn.mis.service.impl;

import com.caucho.hessian.client.HessianProxyFactory;
import com.qding.brick.remote.biz.IBizRemoteService;
import com.qding.brick.struts.request.BizRemoteRequest;
import com.qding.brick.struts.response.BizRemoteResponse;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.net.MalformedURLException;

/**
 * Created by yuejia on 2017/2/27.
 */
@Log4j
@Data
public class RPCService {
    private String propertyInfo_rpc_url="";

    private IBizRemoteService iBizRemoteService;

    public void init(){
        HessianProxyFactory factory = new HessianProxyFactory();
        try{
            factory.setChunkedPost(false);
            factory.setOverloadEnabled(true);
//            factory.setHessian2Reply(false);
//            factory.setHessian2Request(false);
            iBizRemoteService = (IBizRemoteService) factory.create(IBizRemoteService.class, propertyInfo_rpc_url);
        }catch (MalformedURLException e){
            log.error("[BrickRPCService init error]" + e);
        }
    }

    /**
     * 新增物业社区
     * @param request
     * @return
     */
    public BizRemoteResponse insert(BizRemoteRequest request){
        return iBizRemoteService.insert(request);
    }

}
