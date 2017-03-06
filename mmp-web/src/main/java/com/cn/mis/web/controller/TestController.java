package com.cn.mis.web.controller;

import com.cn.mis.domain.entity.User;
import com.cn.mis.service.IUserService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by yuejia on 2017/3/2.
 */
@Controller
@Log4j
@RequestMapping("/test")
public class TestController {
    @Resource
    private IUserService iUserService;

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
}
