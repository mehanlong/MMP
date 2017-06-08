package com.cn.mis.timetask;

import com.cn.mis.common.Common;
import com.cn.mis.domain.entity.mis.HrmResourceWithDepartment;
import com.cn.mis.domain.enums.Sex;
import com.cn.mis.domain.enums.Valid;
import com.cn.mis.service.mis.IHrmResourceService;
import corp.openapicalls.contract.employee.Authentication;
import corp.openapicalls.contract.employee.AuthenticationInfoList;
import corp.openapicalls.contract.employee.AuthenticationInfoListResponse;
import corp.openapicalls.contract.employee.AuthenticationListRequst;
import corp.openapicalls.contract.ordercontroller.ticket.OrderSearchTicketRequest;
import corp.openapicalls.contract.ordercontroller.ticket.OrderSearchTicketResponse;
import corp.openapicalls.service.employee.MultipleEmployeeSyncService;
import corp.openapicalls.service.orderinfo.OrderInfoTicketService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuejia on 2017/3/23.
 */
@Log4j
@Component("xcAccountTimeTask")
public class XCAccountTimeTask {
    @Resource
    private IHrmResourceService iHrmResourceService;

    private OrderSearchTicketResponse orderSearchTicketResponse;

    private AuthenticationListRequst authenticationListRequst = new AuthenticationListRequst();

    public void run(){
        System.out.println("【XCAccountTimeTask】start");
        MultipleEmployeeSync();
    }

    private String MultipleEmployeeSync(){
        orderSearchTicketResponse = getEmployeeSyncTicket(Common.appKey,Common.appSecurity,Common.Version);
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
                authentication.setDept1("千丁互联");
                authentication.setDept2(tmpdata.getDepartmentid()+"");
                authentication.setDept3(tmpdata.getDepartmentname());
                if (tmpdata.getName() != null) {
                    authentication.setCostCenter(tmpdata.getName().trim());
                }
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
}