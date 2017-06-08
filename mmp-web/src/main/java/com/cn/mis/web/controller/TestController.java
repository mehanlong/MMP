package com.cn.mis.web.controller;

import com.cn.mis.domain.bean.resp.BaseResult;
import com.cn.mis.domain.bean.resp.ListResult;
import com.cn.mis.domain.entity.mis.*;
import com.cn.mis.service.mis.*;
import com.cn.mis.utils.email.EMailProperties;
import com.cn.mis.utils.email.EmailSender;
import com.cn.mis.utils.json.JsonUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.qding.framework.common.util.MD5Util;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuejia on 2017/3/2.
 */
@Controller
@Log4j
@RequestMapping("/test")
public class TestController {
    @Resource
    private IUserService iUserService;

    @Resource
    private IHrmResourceService iHrmResourceService;

    @Resource
    private IRolePathUrlService iRolePathUrlService;

    @Resource
    private IUFContractService iufContractService;

    @Resource
    private IFM24Service ifm24Service;

    @Resource
    private IFM24DT1Service ifm24DT1Service;

    @RequestMapping("/login")
    @ResponseBody
    private Object login(HrmResource hrmResource){
        hrmResource.setPassword(MD5Util.md5(hrmResource.getPassword()));
        HrmResource hrmResource1 = iHrmResourceService.login(hrmResource);
        if (hrmResource1 != null){
            return BaseResult.ok("登陆成功",hrmResource1);
        } else {
            return BaseResult.fail("用户名或密码错误");
        }
    }

    @RequestMapping("/getTopMenu")
    @ResponseBody
    private Object getTopMenu(){
        List<RolePathUrl> list = iRolePathUrlService.getTopMenuByRole(1);
        return BaseResult.ok("获取成功",list);
    }

    @RequestMapping("/getChiledMenu/{pid}")
    @ResponseBody
    private Object getChiledMenu(@PathVariable int pid){
        List<RolePathUrl> list = iRolePathUrlService.getChiledMenuByRole(1,pid);
        return BaseResult.ok("获取成功",list);
    }

    @RequestMapping("/insert")
    @ResponseBody
    private String test(){
        User user = new User();
        user.setId(999);
        user.setDepartId(8);
        user.setEmail("956019571@qq.com");
        user.setName("jy");
        user.setPhone("15652155045");
        iUserService.insert(user);
        return "success";

    }

    @RequestMapping("/selectByPage")
    @ResponseBody
    private Object selectByPage(Map<String, Object> map, HttpServletRequest req, @RequestParam("page")int page,@RequestParam("rows")int rows){

        PageInfo<User> users = iUserService.selectByPage(page,rows);
        ListResult listResult = new ListResult();
        listResult.setRows(users.getList());
        listResult.setTotal(users.getTotal());
        String json = JsonUtil.toJson(listResult);
        return json;
    }

    @RequestMapping("/sendMail")
    private String sendMail(){

        EMailProperties properties = new EMailProperties();
        properties.setFrom("mis@qding.me");
        properties.setHost("smtp.exmail.qq.com");
        properties.setAccount("mis@qding.me");
        properties.setPass("Qd@2014");
        properties.setAuth(true);
        properties.setTimeout(25000);
        properties.setSsl(true);
        properties.setPort("465");
        properties.setProtocol("smtp");
        properties.setToListStr("956019571@qq.com");

        properties.setContent("中文乱码测试");
        properties.setCompany("测试");
        properties.setSubject("中文乱码测试");
        properties.setSendDate(new Date());

        HashMap<String,String> map = new HashMap<>();
        map.put("mailid","1");
        properties.setHeader(map);

        EmailSender emailSender = new EmailSender(new TransportListener() {
            @Override
            public void messageDelivered(TransportEvent transportEvent) {

            }

            @Override
            public void messageNotDelivered(TransportEvent transportEvent) {

            }

            @Override
            public void messagePartiallyDelivered(TransportEvent transportEvent) {
                Message message = transportEvent.getMessage();

                try {
                    String[] messageid = message.getHeader("mailid");
                    System.out.println("messageid:"+messageid);
                } catch (MessagingException e1) {
                    e1.printStackTrace();
                }
            }
        });
        emailSender.send(properties);
        return "success";
    }
    @RequestMapping("/chengdata")
    @ResponseBody
    private String chengdata(){
        List<FM24WithBLOBs> list = ifm24Service.selectAll();
        List<UFCContractWithBLOBs> list1 = iufContractService.selectAll();
        List<FM24DT1> list2 = ifm24DT1Service.selectAll();

        for (FM24DT1 fm:list2){
            if (StringUtil.isNotEmpty(fm.getProperty())){
                String tmp1 = fm.getProperty();
                BigDecimal property = BigDecimal.valueOf(Double.valueOf(tmp1));
                fm.setPropertyNew(property);
            }
            if (StringUtil.isNotEmpty(fm.getServiceAmount())){
                String tmp2 = fm.getServiceAmount().replaceAll("%","");
                BigDecimal serviceamount = BigDecimal.valueOf(Double.valueOf(tmp2));
                fm.setYearServiceAmountNew(serviceamount);
            }
            ifm24DT1Service.updateByPrimaryKeySelective(fm);
        }

//        for (UFCContractWithBLOBs tmp:list1){
//            if (tmp.getContractAmount1() == null || tmp.getContractAmount1().equals("")){
//                if (tmp.getContractAmount() != null && !tmp.getContractAmount().equals("")){
//                    String[] spl = tmp.getContractAmount().split(",");
//                    Double dd = 0.0;
//                    if (spl.length == 3){
//                        dd += Double.valueOf(spl[0])*1000000;
//                        dd += Double.valueOf(spl[1])*1000;
//                        dd += Double.valueOf(spl[2]);
//
//                    }
//                    if (spl.length == 2){
//                        dd += Double.valueOf(spl[0])*1000;
//                        dd += Double.valueOf(spl[1]);
//                    }
//
//                    if (spl.length == 1){
//                        dd += Double.valueOf(spl[0]);
//                    }
//                    tmp.setContractAmount1(new BigDecimal(dd+""));
//                    iufContractService.updateByPrimaryKeySelective(tmp);
//                }
//            }
//        }
//        for (FM24WithBLOBs fm24WithBLOBs: list){
//            if (fm24WithBLOBs.getContractAmount() != null && !fm24WithBLOBs.getContractAmount().equals("")){
//                String tmp = fm24WithBLOBs.getContractAmount();
//                String[] spl = tmp.split(",");
//                Double dd = 0.0;
//
//                if (spl.length == 3){
//                    dd += Double.valueOf(spl[0])*1000000;
//                    dd += Double.valueOf(spl[1])*1000;
//                    dd += Double.valueOf(spl[2]);
//
//                }
//                if (spl.length == 2){
//                    dd += Double.valueOf(spl[0])*1000;
//                    dd += Double.valueOf(spl[1]);
//                }
//
//                if (spl.length == 1){
//                    dd += Double.valueOf(spl[0]);
//                }
//
//
//
//                fm24WithBLOBs.setContractAmount1(new BigDecimal(dd +""));
//
//                ifm24Service.updateByPrimaryKeySelective(fm24WithBLOBs);
//            }
//
//        }
        return "success";

    }

    @RequestMapping("soap")
    @ResponseBody
    public String soap(){
        String resp = soaptest(
                "2009998",
                "0",
                "2016-06-08",
                "0",
                "",
                "",
                "",
                "",
                "PRC",
                "103",
                "",
                "");
        return resp;
    }

    public String soaptest(String EMPLID,String EMPL_RCD,String EFFDT,String EFFSEQ,String DEPTID,String JOBCODE,String POSITION_NBR,String SUPERVISOR_ID,String ACTION,String ACTION_REASON,String LOCATION,String COMPANY){
        StringBuilder psresult = new StringBuilder();
        try {
            String xmlStr = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m29=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/M297221.V1\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <m29:Update__CompIntfc__QD_CI_JOB_DATA>\n" +
                    "         <m29:KEYPROP_EMPLID>90000001</m29:KEYPROP_EMPLID>\n" +
                    "         <m29:KEYPROP_EMPL_RCD>0</m29:KEYPROP_EMPL_RCD>\n" +
                    "         <m29:COLL_JOB>\n" +
                    "            <m29:KEYPROP_EFFDT>2017-05-08</m29:KEYPROP_EFFDT>\n" +
                    "            <m29:KEYPROP_EFFSEQ>0</m29:KEYPROP_EFFSEQ>\n" +
                    "            <m29:PROP_DEPTID>900000051</m29:PROP_DEPTID>\n" +
                    "            <m29:PROP_JOBCODE>90003</m29:PROP_JOBCODE>\n" +
                    "            <m29:PROP_POSITION_NBR>90000006</m29:PROP_POSITION_NBR>\n" +
                    "            <m29:PROP_ACTION>XFR</m29:PROP_ACTION>\n" +
                    "            <m29:PROP_LOCATION></m29:PROP_LOCATION>\n" +
                    "            <m29:PROP_COMPANY></m29:PROP_COMPANY>\n" +
                    "      </m29:COLL_JOB>\n" +
                    "      </m29:Update__CompIntfc__QD_CI_JOB_DATA>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";
            String urlStr = "http://10.37.253.14:8010/PSIGW/PeopleSoftServiceListeningConnector/PSFT_HR/CI_QD_CI_JOB_DATA.1.wsdl";
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("SOAPAction", "CI_QD_CI_JOB_DATA_UP.V1");
            con.setRequestProperty("Encoding", "UTF-8");
            OutputStream reqStream = con.getOutputStream();
            reqStream.write(xmlStr.getBytes());
            InputStream resStream = con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    resStream,"utf-8"));
            String line = null;
            while ((line = in.readLine()) != null) {
                psresult.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(psresult.toString());
        String EncryStr=psresult.toString();
        return EncryStr;

    }


}
