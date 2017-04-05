package com.cn.mis.web.controller;

import com.cn.mis.common.Common;
import com.cn.mis.domain.bean.pojo.XCQueryList;
import com.cn.mis.domain.bean.pojo.XCQueryPojo;
import com.cn.mis.domain.bean.req.XCAuth;
import com.cn.mis.domain.bean.req.XCQueryReq;
import com.cn.mis.domain.bean.resp.XCQueryResp;
import com.cn.mis.domain.entity.*;
import com.cn.mis.domain.enums.Sex;
import com.cn.mis.domain.enums.Valid;
import com.cn.mis.service.IHrmResourceService;
import com.cn.mis.service.IXCEmailService;
import com.cn.mis.service.IXCQueryServcie;
import com.cn.mis.utils.date.DateStyle;
import com.cn.mis.utils.date.DateUtil;
import com.cn.mis.utils.http.HttpClientUtil;
import com.cn.mis.utils.json.JsonUtil;
import com.cn.mis.utils.pinyin.Pinyin4jUtil;
import com.google.gson.reflect.TypeToken;
import corp.openapicalls.contract.Authentification;
import corp.openapicalls.contract.employee.Authentication;
import corp.openapicalls.contract.employee.AuthenticationInfoList;
import corp.openapicalls.contract.employee.AuthenticationInfoListResponse;
import corp.openapicalls.contract.employee.AuthenticationListRequst;
import corp.openapicalls.contract.ordercontroller.ticket.OrderSearchTicketRequest;
import corp.openapicalls.contract.ordercontroller.ticket.OrderSearchTicketResponse;
import corp.openapicalls.contract.setapproval.request.*;
import corp.openapicalls.contract.setapproval.response.SetApprovalResponse;
import corp.openapicalls.contract.ticket.TicketResponse;
import corp.openapicalls.service.employee.MultipleEmployeeSyncService;
import corp.openapicalls.service.orderinfo.OrderInfoTicketService;
import corp.openapicalls.service.setapproval.SetApprovalService;
import corp.openapicalls.service.ticket.CorpTicketService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuejia on 2017/3/14.
 */
@Log4j
@Controller
@RequestMapping("/operate/ctrip")
public class OperateCtripDataController {

    @Resource
    private IHrmResourceService iHrmResourceService;

    @Resource
    private IXCEmailService xcEmailSrevice;

    @Resource
    private IXCQueryServcie xcQueryServcie;

    private OrderSearchTicketResponse orderSearchTicketResponse;

    private AuthenticationListRequst authenticationListRequst = new AuthenticationListRequst();
    //人事接口测试
    @RequestMapping("/testMES")
    @ResponseBody
    private String MultipleEmployeeSync(){
        if (orderSearchTicketResponse == null){
            orderSearchTicketResponse = getEmployeeSyncTicket(Common.appKey,Common.appSecurity,Common.Version);
        }
        if(orderSearchTicketResponse!=null&&orderSearchTicketResponse.getStatus()!=null&&orderSearchTicketResponse.getStatus().getSuccess()){
            authenticationListRequst.setAppkey(Common.appKey);
            authenticationListRequst.setCorporationID(Common.CorporationID);
            authenticationListRequst.setTicket(orderSearchTicketResponse.getTicket());

            List<HrmResourceWithDepartment> list = iHrmResourceService.selectAllWithDepartment();
            List<Integer> ids = new ArrayList<>();
            List<AuthenticationInfoList> authenticationInfoLists = new ArrayList<>();
            MultipleEmployeeSyncService multipleEmployeeSyncService = new MultipleEmployeeSyncService();
            for (int i=0;i<list.size();i++){
                HrmResourceWithDepartment tmpdata = list.get(i);
                AuthenticationInfoList tmpinfo = new AuthenticationInfoList();
                Authentication authentication = new Authentication();
                String tmpName = tmpdata.getLastname().replaceAll("[0-9]*","").replaceAll("\\(*\\)*","").replaceAll("[\\uff08]", "").replaceAll("[\\uff09]","").replaceAll("[\\u5b9e][\\u4e60][\\u751f]","");
                authentication.setName(tmpName);

//                String pinyin = Pinyin4jUtil.converterToSpell(tmpName);
//                String[] duoyin = pinyin.split(",");
//                if (duoyin.length>1){
//                    authentication.setName_Pinyin(duoyin[0]);
//                } else {
//                    authentication.setName_Pinyin(pinyin);
//                }

                authentication.setDept1("千丁互联");
                authentication.setDept2(tmpdata.getDepartmentid()+"");
                authentication.setDept3(tmpdata.getDepartmentname());
                authentication.setCostCenter(tmpdata.getName());
                authentication.setCostCenter2("差旅费");
                authentication.setCostCenter3(tmpdata.getLocationname());
                authentication.setEmployeeID(tmpdata.getId()+"");
                authentication.setEmail(tmpdata.getEmail());
                authentication.setIsSendEMail(true);
                authentication.setGender(Sex.getName(tmpdata.getSex()));
                authentication.setValid(Valid.getName(tmpdata.getStatus()+""));
                if (tmpdata.getSubAccountName() != null && !tmpdata.getSubAccountName().equals("")){
                    authentication.setSubAccountName(tmpdata.getSubAccountName());
                } else {
                    authentication.setSubAccountName("longhu_千丁互联_提前审批授权");
                }
                tmpinfo.setAuthentication(authentication);
                ids.add(tmpdata.getId());
                authenticationInfoLists.add(tmpinfo);
                if ((i+1)%20 == 0 || list.size()-(i+2)<=0){
                    authenticationListRequst.setAuthenticationInfoList(authenticationInfoLists);
                    AuthenticationInfoListResponse authenticationInfoListResponse = multipleEmployeeSyncService.MultipleEmployeeSync(authenticationListRequst);
                    if (authenticationInfoListResponse != null){
                        log.info("批量同步人事信息:"+authenticationInfoListResponse);
                        if (authenticationInfoListResponse.getResult().equals("Success")){
                            iHrmResourceService.updateBath(ids);
                        }
                        ids.clear();
                        authenticationInfoLists.clear();

                    }
                }
            }
        }

        return "success";
    }

    public static OrderSearchTicketResponse getEmployeeSyncTicket(String appKey, String appSecurity, String version)
    {
        OrderSearchTicketRequest ticketRequest=new OrderSearchTicketRequest(appKey, appSecurity, version);
        OrderInfoTicketService orderInfoTicketService=new OrderInfoTicketService();
        return orderInfoTicketService.GetTicket(ticketRequest);
    }

    //提前审批测试
    @RequestMapping("/testAPR")
    @ResponseBody
    private String setApproval(){
        TicketResponse ticketResponse= CorpTicketService.getOrderAuditTicket(Common.appKey, Common.appSecurity, Common.Version);
        if(ticketResponse!=null&&ticketResponse.getStatus()!=null&&ticketResponse.getStatus().getSuccess()) {
            List<XCEmail> list = xcEmailSrevice.selectAll();
            for (XCEmail xcEmail : list){
                SetApprovalService setapprovalService=new SetApprovalService();
                SetApprovalServiceRequest setApprovalServiceRequest=new SetApprovalServiceRequest();
                SetApprovalRequest setApprovalRequest=new SetApprovalRequest();

                ArrayList<FlightEndorsementDetail> flightEndorsementDetails=new ArrayList<>();
                FlightEndorsementDetail flightEndorsementDetail=new FlightEndorsementDetail();
                flightEndorsementDetail.setFlightWay(FlightWayType.RoundTrip);//往返
                flightEndorsementDetail.setDepartBeginDate(xcEmail.getDepartureDate());//第一程开始日期
                flightEndorsementDetail.setDepartEndDate(xcEmail.getDestinationDate());//最后一程结束日期
                flightEndorsementDetail.setReturnBeginDate(xcEmail.getDepartureDate());//第一程开始日期
                flightEndorsementDetail.setReturnEndDate(xcEmail.getDestinationDate());//最后一程结束日期
                flightEndorsementDetail.setProductType(ProductType.DomesticFlight);//代表国内机票

                ArrayList<String> fromcities = new ArrayList<>();
                ArrayList<String> tocities = new ArrayList<>();

                String tmpfromcities = xcEmail.getDpartureCity().replace("，",",");
                String tmptocities = xcEmail.getDestinationCity().replace("，",",");

                String[] fromarray = tmpfromcities.split(",");
                String[] toarray = tmptocities.split(",");

                for (String city:fromarray){
                    fromcities.add(city);
                }
                for (String city:toarray){
                    tocities.add(city);
                }

                flightEndorsementDetail.setFromCities(fromcities);
                flightEndorsementDetail.setToCities(tocities);
                flightEndorsementDetails.add(flightEndorsementDetail);

                Authentification authInfo=new Authentification(Common.appKey, ticketResponse.getTicket());
                setApprovalRequest.setApprovalNumber(xcEmail.getId()+"");//申请单号
                setApprovalRequest.setStatus(1);//审批单状态（0为无效，1为有效。）
                setApprovalRequest.setEmployeeID(xcEmail.getBookingAgent());//员工编号
//                setApprovalRequest.setEmployeeID("QDHL001");//测试用员工编号
                setApprovalRequest.setFlightEndorsementDetails(flightEndorsementDetails);//List<FlightEndorsementDetail>
                setApprovalRequest.setExpiredTime(DateUtil.DateToString(xcEmail.getDestinationDate(),DateStyle.YYYY_MM_DD));//审批单失效时间（一般与ReturnEndDate相同）
                setApprovalRequest.setAuth(authInfo);//Authentification
                setApprovalServiceRequest.setRequest(setApprovalRequest);
                SetApprovalResponse setApprovalResponse=setapprovalService.SetApproval(setApprovalServiceRequest);
                if(setApprovalResponse!=null&&setApprovalResponse.getStatus()!=null) {
                    log.info("事前审批:"+ JsonUtil.toJson(setApprovalResponse));
                    if (setApprovalResponse.getStatus().getSuccess()){
                        xcEmail.setProcessFlag((byte) 1);
                        xcEmail.setProcessTime(new Date());
                        xcEmailSrevice.updateByPrimaryKeySelective(xcEmail);
                    }
                }
            }
        }
        return "success";
    }

    //结算明细
    @RequestMapping("/testQuery")
    @ResponseBody
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
        params.put("json",JsonUtil.toJson(xcQueryReq));

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
