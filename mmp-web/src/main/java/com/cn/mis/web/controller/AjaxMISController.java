package com.cn.mis.web.controller;

import com.cn.mis.domain.bean.resp.BaseResp;
import com.cn.mis.domain.entity.mis.FM144WithBLOBs;
import com.cn.mis.domain.entity.mis.FM146WithBLOBs;
import com.cn.mis.domain.entity.mis.HrmResource;
import com.cn.mis.domain.entity.mis.UFPermanaWithBLOBs;
import com.cn.mis.service.mis.IHrmResourceService;
import com.cn.mis.service.mis.ajax.IFM144Service;
import com.cn.mis.service.mis.ajax.IFM146Service;
import com.cn.mis.service.mis.ajax.IUFPermanaService;
import com.cn.mis.utils.json.JsonUtil;
import com.cn.mis.utils.pinyin.Pinyin4jUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yuejia on 2017/4/26.
 */
@Controller
@RequestMapping("ajax")
public class AjaxMISController {
    @Resource
    IFM144Service ifm144Service;

    @Resource
    IFM146Service ifm146Service;

    @Resource
    IUFPermanaService iufPermanaService;

    @Resource
    IHrmResourceService iHrmResourceService;

    @RequestMapping("ufmana/{assessor}/{performance}")
    @ResponseBody
    private String queryUfmana(HttpServletRequest req, @PathVariable("assessor")int assessor, @PathVariable("performance")String performance){

        UFPermanaWithBLOBs ufPermanaWithBLOBs = new UFPermanaWithBLOBs();
        ufPermanaWithBLOBs.setAssessor(assessor);

        if (performance != null && !performance.equals("")){
            ufPermanaWithBLOBs.setPerformance(performance);
        }
        List<UFPermanaWithBLOBs> list = iufPermanaService.selectByParames(ufPermanaWithBLOBs);

        boolean flag = false;
        if (list.size()>1){
            UFPermanaWithBLOBs max = new UFPermanaWithBLOBs();
            max.setId(0);
            for (int i=0;i<list.size();i++){
                if (list.get(i).getId()>max.getId()){
                    max = list.get(i);
                }
            }
            if (max.getPerformanceScore() == null || max.getPerformanceScore().equals("")){
                flag = true;
            }
        } else if (list.size() == 1){
            if (list.get(0).getPerformanceScore() == null || list.get(0).equals("")){
                flag = true;
            }
        } else {
            flag = true;
        }
        BaseResp baseResp = new BaseResp();
        if (flag){
            baseResp.setSuccess(true);
            baseResp.setStatusCode(400);
            baseResp.setErro("");
        } else {
            baseResp.setSuccess(false);
            baseResp.setStatusCode(500);
            baseResp.setErro("");
        }
        return JsonUtil.toJson(baseResp);
    }

    @RequestMapping("fm144/{employee}/{performance}")
    @ResponseBody
    public String queryFm144(@PathVariable("employee")int employee,@PathVariable("performance")String performance){
        FM144WithBLOBs fm144WithBLOBs = new FM144WithBLOBs();
        fm144WithBLOBs.setEmployee(employee);
        if (performance != null && !performance.equals("")){
            fm144WithBLOBs.setPerformance(performance);
        }
        List<FM144WithBLOBs> list = ifm144Service.selectByParames(fm144WithBLOBs);
        boolean flag = false;
        if (list.size() == 0){
            flag = true;
        } else {
            flag =false;
        }
        BaseResp baseResp = new BaseResp();
        if (flag){
            baseResp.setSuccess(true);
            baseResp.setStatusCode(400);
            baseResp.setErro("");
        } else {
            baseResp.setSuccess(false);
            baseResp.setStatusCode(500);
            baseResp.setErro("");
        }
        return JsonUtil.toJson(baseResp);
    }

    @RequestMapping("fm146/{employee}/{performance}")
    @ResponseBody
    public String queryFm146(@PathVariable("employee")int employee,@PathVariable("performance")String performance){
        FM146WithBLOBs fm146WithBLOBs = new FM146WithBLOBs();
        fm146WithBLOBs.setEmployee(employee);
        if (performance != null && !performance.equals("")){
            fm146WithBLOBs.setPerformance(performance);
        }
        List<FM146WithBLOBs> list = ifm146Service.selectByParames(fm146WithBLOBs);
        boolean flag = false;
        if (list.size() == 0){
            flag = true;
        } else {
            flag =false;
        }
        BaseResp baseResp = new BaseResp();
        if (flag){
            baseResp.setSuccess(true);
            baseResp.setStatusCode(400);
            baseResp.setErro("");
        } else {
            baseResp.setSuccess(false);
            baseResp.setStatusCode(500);
            baseResp.setErro("");
        }
        return JsonUtil.toJson(baseResp);
    }

    @RequestMapping("checkName/{name}")
    @ResponseBody
    public String checkName(@PathVariable String name){
        HrmResource req = new HrmResource();
        String tmpName = name.replaceAll("[0-9]*","").replaceAll("\\(*\\)*","").replaceAll("[\\uff08]", "").replaceAll("[\\uff09]","").replaceAll("[\\u5b9e][\\u4e60][\\u751f]","");
        String pinyin = Pinyin4jUtil.converterToSpell(tmpName);
        String[] duoyin = pinyin.split(",");
        if (duoyin.length>1){
            req.setEmail(duoyin[0].replace("/","")+"@qding.me");
        } else {
            req.setEmail(pinyin.replace("/","")+"@qding.me");
        }
        List<HrmResource> list = iHrmResourceService.selectByEmail(req);
        int i = 1;
        while (list.size() > 0){
            if (i<10) {
                if (duoyin.length>1){
                    req.setEmail(duoyin[0].replace("/","")+"0"+i+"@qding.me");
                } else {
                    req.setEmail(pinyin.replace("/","")+"0"+i+"@qding.me");
                }
            } else {
                if (duoyin.length>1){
                    req.setEmail(duoyin[0]+i+"@qding.me");
                } else {
                    req.setEmail(pinyin+i+"@qding.me");
                }
            }
            list = iHrmResourceService.selectByEmail(req);
            i++;
        }
        return req.getEmail().replace("@qding.me","");
    }
}
