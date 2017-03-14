package com.cn.mis.utils.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by yuejia on 2017/2/24.
 * mail.from=mis@qding.me
 mail.host=smtp.exmail.qq.com
 mail.account=mis@qding.me
 mail.password=Qd@2014
 mail.smtp.auth=true
 mail.smtp.timeout=25000
 mail.port=465
 mail.ssl=true
 mail.protocol=smtp
 mail.tolist=956019571@qq.com
 */
public class EMailProperties {
    private String from;        //发件地址
    private String host;        //服务器地址
    private String account;		//登录用户名
    private String pass;       	//登录密码
    private boolean auth;
    private int timeout;
    private String port;       	//端口
    private boolean ssl;
    private String protocol; 	//协议
    private String toListStr;   //收件人列表

    private String content;         //邮件内容
    private String company;         //邮件显示发件人
    private String subject;         //标题
    private Date sendDate;          //邮件发送时间
    HashMap<String,String> header;  //头文件

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getToListStr() {
        return toListStr;
    }

    public void setToListStr(String toListStr) {
        this.toListStr = toListStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }
}