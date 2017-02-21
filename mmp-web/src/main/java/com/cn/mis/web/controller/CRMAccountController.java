package com.cn.mis.web.controller;

import com.cn.mis.service.ICRMAccountService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by yuejia on 2017/2/14.
 */
@Controller
@RequestMapping("/account")
public class CRMAccountController {
    @Resource
    private ICRMAccountService crmAccountService;

    @RequestMapping("/getTop30")
    @ResponseBody
    private String getTop30(){
        return "success";
    }
}
