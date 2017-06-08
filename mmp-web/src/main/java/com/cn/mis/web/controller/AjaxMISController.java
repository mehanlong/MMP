package com.cn.mis.web.controller;

import com.caucho.hessian.client.HessianProxyFactory;
import com.cn.mis.domain.bean.resp.BaseResp;
import com.cn.mis.domain.entity.mis.*;
import com.cn.mis.domain.entity.mis.ajax.QD201706001DT1;
import com.cn.mis.domain.entity.mis.ajax.QD201706001DT2;
import com.cn.mis.service.mis.IHrmCityService;
import com.cn.mis.service.mis.IHrmResourceService;
import com.cn.mis.service.mis.ajax.IAjaxService;
import com.cn.mis.service.mis.ajax.IFM144Service;
import com.cn.mis.service.mis.ajax.IFM146Service;
import com.cn.mis.service.mis.ajax.IUFPermanaService;
import com.cn.mis.utils.date.DateStyle;
import com.cn.mis.utils.date.DateUtil;
import com.cn.mis.utils.excel.ExportExcelUtil;
import com.cn.mis.utils.json.JsonUtil;
import com.cn.mis.utils.pinyin.Pinyin4jUtil;
import com.qding.brick.remote.biz.RegionRemote;
import com.qding.brick.remote.contract.mis.MISContractRemote;
import com.qding.brick.struts.request.RegionRequest;
import com.qding.brick.struts.response.RegionResponse;
import com.qding.brick.struts.response.mis.RegisterSupplierCheckRequest;
import com.qding.brick.struts.response.mis.RegisterSupplierCheckResponse;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

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

    @Resource
    IAjaxService iAjaxService;

    @Resource
    IHrmCityService iHrmCityService;

    RegionRemote regionRemote;

    private MISContractRemote misContractRemote;

    @PostConstruct
    private void init(){
        try {
            HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
            hessianProxyFactory.setOverloadEnabled(false);
            hessianProxyFactory.setChunkedPost(false);
            regionRemote = (RegionRemote) hessianProxyFactory.create(RegionRemote.class,
                    "http://devboss.qdingnet.com/brickadmin/remote/v0/regionRemote");
            misContractRemote = (MISContractRemote) hessianProxyFactory.create(MISContractRemote.class,
                    "http://devboss.qdingnet.com/brickadmin/remote/v0/MISContract");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
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

    //
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

    //
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

    //入职流程根据姓名生成账号邮箱
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

    @RequestMapping("getMacCount")
    @ResponseBody
    public String getMacCount(){
        int res = iAjaxService.getMacCount();
        return res+"";
    }

    //获得BOSS的省
    @RequestMapping("provincial")
    @ResponseBody
    public String getProvincial(){
        RegionResponse resp = regionRemote.getProvinceList();
        return JsonUtil.toJson(resp.getProvinceList());
    }

    @RequestMapping("region/{id}")
    @ResponseBody
    public String getRegion(@PathVariable Long id){
        RegionRequest req = new RegionRequest();
        req.setProvinceId(id);
        RegionResponse resp = regionRemote.getRegionListByProvinceId(req);
        return JsonUtil.toJson(resp.getRegionList());
    }

    @RequestMapping("district/{id}")
    @ResponseBody
    public String getDistrict(@PathVariable Long id){
        RegionRequest req = new RegionRequest();
        req.setRegionId(id);
        RegionResponse resp = regionRemote.getDistirctListByRegionId(req);
        return JsonUtil.toJson(resp.getDistrictList());
    }

    @RequestMapping("regionName/{misname}")
    @ResponseBody
    public String getMISRegion(@PathVariable String misname){
        List<HrmCity> list = iHrmCityService.selectByName(misname);
        if (list.size()>0){
            return list.get(0).getId()+"";
        } else {
            return "";
        }
    }

    @RequestMapping("getDT1/{orderno}")
    @ResponseBody
    public String getQD201706001DT1(HttpServletRequest request,HttpServletResponse response,@PathVariable String orderno){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        ExportExcelUtil eeu = new ExportExcelUtil(wb, sheet);

        String[] titles = {"orderno","sn","mac","order_flag"};
        int colNum = titles.length;
        int rowNum = 0;
        eeu.createExcelRow(wb, sheet, rowNum, -1, colNum, "QD201706001DT1");
        rowNum++;
        eeu.createColumnHeader(sheet, rowNum, 300, titles);
        rowNum++;
        List<QD201706001DT1> list = iAjaxService.Sel_QD201706001DT1(orderno);
        String[][] columnData = new String[list.size()][titles.length];
        for (int i=0;i<list.size();i++){
            columnData[i][0] = list.get(i).getOrderno();
            columnData[i][1] = list.get(i).getSn();
            columnData[i][2] = list.get(i).getMac();
            columnData[i][3] = list.get(i).getOrder_flag()+"";
        }
        sheet = eeu.createColumnData(sheet, rowNum,  columnData, list.size());
        String path = request.getRealPath("/")+ new Date().getTime()+".xls";
        eeu.exportExcel(path);
        download(path,response);
        return "";
    }

    @RequestMapping("getDT2/{orderno}")
    @ResponseBody
    private String getQD201706001DT2(HttpServletRequest request,HttpServletResponse response,@PathVariable String orderno){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        ExportExcelUtil eeu = new ExportExcelUtil(wb, sheet);

        String[] titles = {"orderno","caseno","case_num"};
        int colNum = titles.length;
        int rowNum = 0;
        eeu.createExcelRow(wb, sheet, rowNum, -1, colNum, "QD201706001DT2");
        rowNum++;
        eeu.createColumnHeader(sheet, rowNum, 300, titles);
        rowNum++;
        List<QD201706001DT2> list = iAjaxService.Sel_QD201706001DT2(orderno);
        String[][] columnData = new String[list.size()][titles.length];
        for (int i=0;i<list.size();i++){
            columnData[i][0] = list.get(i).getOrderno();
            columnData[i][1] = list.get(i).getCaseno();
            columnData[i][2] = list.get(i).getCase_num()+"";
        }
        sheet = eeu.createColumnData(sheet, rowNum,  columnData, list.size()+2);
        String path = request.getRealPath("/")+ new Date().getTime()+".xls";
        eeu.exportExcel(path);
        download(path,response);
        return "";
    }

    private void download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(
                    response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("checkVendor/{fullname}/{mobile}")
    @ResponseBody
    public String checkVendor(@PathVariable String fullname,@PathVariable String mobile){
        RegisterSupplierCheckRequest req = new RegisterSupplierCheckRequest();
        req.setFullName(fullname);
        req.setMobile(mobile);
        RegisterSupplierCheckResponse resp = misContractRemote.registerSupplierCheck(req);
        if (resp.getReturnInfo().isSuccess()){
            return "false";
        }
        return "true";
    }
}
