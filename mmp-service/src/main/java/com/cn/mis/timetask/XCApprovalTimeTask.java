package com.cn.mis.timetask;

import com.cn.mis.common.Common;
import com.cn.mis.domain.entity.mis.XCEmail;
import com.cn.mis.service.mis.IXCEmailService;
import com.cn.mis.utils.date.DateStyle;
import com.cn.mis.utils.date.DateUtil;
import com.cn.mis.utils.json.JsonUtil;
import corp.openapicalls.contract.Authentification;
import corp.openapicalls.contract.setapproval.request.*;
import corp.openapicalls.contract.setapproval.response.SetApprovalResponse;
import corp.openapicalls.contract.ticket.TicketResponse;
import corp.openapicalls.service.setapproval.SetApprovalService;
import corp.openapicalls.service.ticket.CorpTicketService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yuejia on 2017/3/23.
 */
@Log4j
@Component("xcApprovalTimeTask")
public class XCApprovalTimeTask {
    @Resource
    private IXCEmailService xcEmailSrevice;

    public void run(){
        System.out.println("【XCApprovalTimeTask】start");
        setApproval();
    }

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
                setApprovalRequest.setExpiredTime(DateUtil.DateToString(xcEmail.getDestinationDate(), DateStyle.YYYY_MM_DD));//审批单失效时间（一般与ReturnEndDate相同）
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
}
