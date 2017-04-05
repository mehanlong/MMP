package com.cn.mis.timetask;

import com.cn.mis.common.Common;
import com.cn.mis.domain.bean.pojo.XCQueryList;
import com.cn.mis.domain.bean.pojo.XCQueryPojo;
import com.cn.mis.domain.bean.req.XCAuth;
import com.cn.mis.domain.bean.req.XCQueryReq;
import com.cn.mis.domain.bean.resp.XCQueryResp;
import com.cn.mis.domain.entity.*;
import com.cn.mis.service.IXCQueryServcie;
import com.cn.mis.utils.date.DateStyle;
import com.cn.mis.utils.date.DateUtil;
import com.cn.mis.utils.http.HttpClientUtil;
import com.cn.mis.utils.json.JsonUtil;
import com.google.gson.reflect.TypeToken;
import corp.openapicalls.contract.Authentification;
import corp.openapicalls.contract.ticket.TicketResponse;
import corp.openapicalls.service.ticket.CorpTicketService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by yuejia on 2017/3/23.
 */
@Log4j
@Component("xcFlightOrderSettlementsTimeTask")
public class XCFlightOrderSettlementsTimeTask {
    @Resource
    private IXCQueryServcie xcQueryServcie;

    public void run(){
        System.out.println("【XCFlightOrderSettlementsTimeTask】start");
        GetCorpAccountFlightOrderSettlements();
    }

    private String GetCorpAccountFlightOrderSettlements(){
        TicketResponse ticketResponse= CorpTicketService.getOrderAuditTicket(Common.appKey, Common.appSecurity, Common.Version);
        Authentification authInfo=new Authentification(Common.appKey, ticketResponse.getTicket());
        XCAuth xcAuth = new XCAuth();
        xcAuth.setAppKey(authInfo.getAppKey());
        xcAuth.setTicket(authInfo.getTicket());
        XCQueryReq xcQueryReq = new XCQueryReq();
        xcQueryReq.setAuth(xcAuth);
        xcQueryReq.setAccountID("22749");
        Date today = new Date();
        Date from = DateUtil.addMonth(today,-1);
        Date to = DateUtil.addDay(today,-1);
        xcQueryReq.setDateFrom(DateUtil.DateToString(from, DateStyle.YYYY_MM_DD));//DateUtil.DateToString(DateUtil.addDay(new Date(),-1),DateStyle.YYYY_MM_DD));
        xcQueryReq.setDateTo(DateUtil.DateToString(to, DateStyle.YYYY_MM_DD));//DateUtil.DateToString(DateUtil.addDay(new Date(),-1),DateStyle.YYYY_MM_DD));

        String url = "https://www.corporatetravel.ctrip.com/switchapi/FlightOrderSettlement/GetCorpAccountFlightOrderSettlements?type=json";
        HashMap<String ,String> params = new HashMap<>();
        params.put("json", JsonUtil.toJson(xcQueryReq));

        String resp = HttpClientUtil.sendXCSSLPostRequest(url,JsonUtil.toJson(xcQueryReq));
        String tmpResp = resp.replaceAll("\"Class\"","\"DClass\"");
        XCQueryResp xcQueryResp = (XCQueryResp) JsonUtil.fromJson(new TypeToken<XCQueryResp>(){}.getType(),tmpResp);
        if (xcQueryResp.getStatus().isSuccess()){
            for (XCQueryPojo pojo:xcQueryResp.getFlightOrderAccountSettlementList()){
                for (XCQueryList tmp:pojo.getOrderSettlementList()){
                    FOSettlementBaseInfo foSettlementBaseInfo = tmp.getOrderSettlementBaseInfo();

                    OFInfo ofInfo = tmp.getOrderFlightInfo();
                    FOBaseInfo foBaseInfo = tmp.getOrderBaseInfo();
                    FOPassenger foPassenger = tmp.getOrderPassengerInfo();
                    FORefundInfo foRefundInfo = tmp.getOrderRefundInfo();
                    FORebookInfo foRebookInfo = tmp.getOrderRebookInfo();

                    Integer record = foSettlementBaseInfo.getRecordID();
                    ofInfo.setRecordID(record);
                    foBaseInfo.setRecordID(record);
                    foPassenger.setRecordID(record);

                    xcQueryServcie.insertSelectiveInfo(tmp.getOrderFlightInfo());
                    xcQueryServcie.insertSelectivePassenger(tmp.getOrderPassengerInfo());
                    xcQueryServcie.insertSelectiveBaseInfo(tmp.getOrderBaseInfo());
                    xcQueryServcie.insertSelectiveSettlementBaseInfo(tmp.getOrderSettlementBaseInfo());

                    if (foRefundInfo != null){
                        foRefundInfo.setRecordID(record);
                        xcQueryServcie.insertSelectiveRefundInfo(tmp.getOrderRefundInfo());
                    }
                    if (foRebookInfo != null){
                        foRebookInfo.setRecordID(record);
                        xcQueryServcie.insertSelectiveRebookInfo(tmp.getOrderRebookInfo());
                    }

                }
            }
        }
        return tmpResp;
    }
}
