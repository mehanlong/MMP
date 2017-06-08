package com.cn.mis.webservice;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yuejia on 2017/5/17.
 */
public class SOAPService {
    public String soaptest(String EMPLID,String EMPL_RCD,String EFFDT,String EFFSEQ,String DEPTID,String JOBCODE,String POSITION_NBR,String SUPERVISOR_ID,String ACTION,String ACTION_REASON,String LOCATION,String COMPANY){
        StringBuilder psresult = new StringBuilder();
        try {
            String xmlStr = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:si=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/SI_UPDATE_JOBDATA_REQUEST.V1\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <si:Update__CompIntfc__CI_GST_JOB_DATA>\n" +
                    "         <si:KEYPROP_EMPLID>"+EMPLID+"</si:KEYPROP_EMPLID>\n" +           //员工ID
                    "         <si:KEYPROP_EMPL_RCD>"+EMPL_RCD+"</si:KEYPROP_EMPL_RCD>\n" +             //雇佣记录编号（默认填0即可）
                    "         <si:COLL_JOB>\n" +
                    "            <si:KEYPROP_EFFDT>"+EFFDT+"</si:KEYPROP_EFFDT>\n" +       //转正日期
                    "            <si:KEYPROP_EFFSEQ>"+EFFSEQ+"</si:KEYPROP_EFFSEQ>\n" +              //生效日期序号
                    "            <si:PROP_DEPTID>"+DEPTID+"</si:PROP_DEPTID>\n" +                    //部门编码
                    "            <si:PROP_JOBCODE>"+JOBCODE+"</si:PROP_JOBCODE>\n" +                  //职务代码
                    "            <si:PROP_POSITION_NBR>"+POSITION_NBR+"</si:PROP_ POSITION_NBR>\n" +       //职位
                    "            <si:PROP_SUPERVISOR_ID>"+SUPERVISOR_ID+"</si:PROP_SUPERVISOR_ID>\n" +      //直属上级ID
                    "            <si:PROP_ACTION>"+ACTION+"</si:PROP_ACTION>\n" +                    //操作
                    "            <si:PROP_ACTION_REASON>"+ACTION_REASON+"</si:PROP_ACTION_REASON>\n" +    //转正原因
                    "            <si:PROP_LOCATION>"+LOCATION+"</si:PROP_LOCATION>\n" +                //办公地点
                    "            <si:PROP_COMPANY>"+COMPANY+"</si:PROP_COMPANY>\n" +                  //公司ID
                    "         </si:COLL_JOB>\n" +
                    "      </si:Update__CompIntfc__CI_GST_JOB_DATA>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";
            String urlStr = "http://10.37.253.14:8010/PSIGW/PeopleSoftServiceListeningConnector/PSFT_HR/CI_CI_JOB_DATA.1.wsdl";
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("SOAPAction", "CI_CI_JOB_DATA_UD.V2");
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
