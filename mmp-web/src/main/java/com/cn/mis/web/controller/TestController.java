package com.cn.mis.web.controller;

import com.alibaba.dubbo.common.json.JSONObject;
import com.cn.mis.domain.bean.resp.BaseResult;
import com.cn.mis.domain.bean.resp.ListResult;
import com.cn.mis.domain.entity.*;
import com.cn.mis.service.*;
import com.cn.mis.utils.email.EMailProperties;
import com.cn.mis.utils.email.EmailSender;
import com.cn.mis.utils.json.JsonUtil;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.qding.framework.common.util.MD5Util;
import com.qiniu.util.Json;
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
import java.math.BigDecimal;
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

        for (UFCContractWithBLOBs tmp:list1){
            if (tmp.getContractAmount1() == null || tmp.getContractAmount1().equals("")){
                if (tmp.getContractAmount() != null && !tmp.getContractAmount().equals("")){
                    String[] spl = tmp.getContractAmount().split(",");
                    Double dd = 0.0;
                    if (spl.length == 3){
                        dd += Double.valueOf(spl[0])*1000000;
                        dd += Double.valueOf(spl[1])*1000;
                        dd += Double.valueOf(spl[2]);

                    }
                    if (spl.length == 2){
                        dd += Double.valueOf(spl[0])*1000;
                        dd += Double.valueOf(spl[1]);
                    }

                    if (spl.length == 1){
                        dd += Double.valueOf(spl[0]);
                    }
                    tmp.setContractAmount1(new BigDecimal(dd+""));
                    iufContractService.updateByPrimaryKeySelective(tmp);
                }
            }
        }
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


}
