package com.cn.mis.web.controller;

import com.cn.mis.domain.entity.DTOneWithBLOBs;
import com.cn.mis.domain.entity.DTThree;
import com.cn.mis.domain.entity.FMTtF;
import com.cn.mis.domain.entity.FMTtFWithBLOBs;
import com.cn.mis.service.IDTOneService;
import com.cn.mis.service.IDTThreeService;
import com.cn.mis.service.IFMTtFService;
import lombok.extern.log4j.Log4j;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by yuejia on 2017/2/21.
 */
@Log4j
@Controller
@RequestMapping("/test")
public class TestController {
    @Resource
    private IDTOneService idtOneService;

    @Resource
    private IDTThreeService idtThreeService;

    @Resource
    private IFMTtFService ifmTtFService;

    @RequestMapping("/DTone")
    @ResponseBody
    private String dtone(){
        ArrayList<DTOneWithBLOBs> list = idtOneService.selectAll();

        for (DTOneWithBLOBs dtone:list){
            String value = dtone.getContractAmount();
            String[] values = value.split(",");
            BigDecimal tmp = null;
            if (values.length == 3){
                tmp = BigDecimal.valueOf(Double.valueOf(values[0])*1000000+Double.valueOf(values[1])*1000+Double.valueOf(values[2]));
            }else if (values.length == 2){
                tmp = BigDecimal.valueOf(Double.valueOf(values[0])*1000+Double.valueOf(values[1]));
            } else if (values.length == 1){
                tmp = BigDecimal.valueOf(Double.valueOf(values[0]));
            }
            DTOneWithBLOBs dtTmp = new DTOneWithBLOBs();
            dtTmp.setId(dtone.getId());
            dtTmp.setContractAmount1(tmp);
            idtOneService.updateByPrimaryKeySelective(dtTmp);
            log.info("updateDTOne:"+dtone.getId()+" amount1:"+tmp);
        }
        return "success";
    }
    @RequestMapping("/DTthree")
    @ResponseBody
    private String dtthree(){
        ArrayList<DTThree> list = idtThreeService.selectBySql();
        for (DTThree dtthree:list){
            String value = dtthree.getAmount();
            String[] values = value.split(",");
            BigDecimal tmp = null;
            if (values.length == 3){
                tmp = BigDecimal.valueOf(Double.valueOf(values[0])*1000000+Double.valueOf(values[1])*1000+Double.valueOf(values[2]));
            }else if (values.length == 2){
                tmp = BigDecimal.valueOf(Double.valueOf(values[0])*1000+Double.valueOf(values[1]));
            } else if (values.length == 1){
                tmp = BigDecimal.valueOf(Double.valueOf(values[0]));
            }
            DTThree dtThree = new DTThree();
            dtThree.setId(dtthree.getId());
            dtThree.setPayAmount(tmp);
            idtThreeService.updateByPrimaryKeySelective(dtThree);
            log.info("updateDTThree:"+dtthree.getId()+" payAmount:"+tmp);
        }
        return "success";
    }
    @RequestMapping("/FMTtF")
    @ResponseBody
    private String fmttf(){
        ArrayList<FMTtFWithBLOBs> list = ifmTtFService.selectBySql();
        for (FMTtFWithBLOBs fmttf:list){
            String value = fmttf.getContractAmount();
            String[] values = value.split(",");
            BigDecimal tmp = null;
            if (values.length == 3){
                tmp = BigDecimal.valueOf(Double.valueOf(values[0])*1000000+Double.valueOf(values[1])*1000+Double.valueOf(values[2]));
            }else if (values.length == 2){
                tmp = BigDecimal.valueOf(Double.valueOf(values[0])*1000+Double.valueOf(values[1]));
            } else if (values.length == 1){
                tmp = BigDecimal.valueOf(Double.valueOf(values[0]));
            }
            FMTtFWithBLOBs fmTtFWithBLOBs = new FMTtFWithBLOBs();
            fmTtFWithBLOBs.setId(fmttf.getId());
            fmTtFWithBLOBs.setContractAmount1(tmp);
            ifmTtFService.updateByPrimaryKeySelective(fmTtFWithBLOBs);
            log.info("updateFMTtF:"+fmttf.getId()+" contractAmount1:"+tmp);
        }
        return "success";
    }
}
