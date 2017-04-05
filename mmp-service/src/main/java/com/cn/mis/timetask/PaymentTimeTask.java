package com.cn.mis.timetask;

import com.cn.mis.domain.entity.QIPayment;
import com.cn.mis.service.IQIPaymentService;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by yuejia on 2017/3/29.
 */
@Component("paymentTimeTask")
@Log4j
public class PaymentTimeTask {
    @Resource
    IQIPaymentService iqiPaymentService;

    private String account;		//登录用户名
    private String pass;       	//登录密码
    private String from;        //发件地址
    private String host;        //服务器地址
    private String port;       	//端口
    private String protocol; 	//协议

    @PostConstruct
    private void init(){
        log.info("初始化【PaymentTimeTask】，执行init()方法读取配置文件");
        try {
            Properties propFile = new Properties();
            InputStream instream = getClass().getResourceAsStream("/mail.properties");
            propFile.load(instream);
            account = propFile.getProperty("mail.account");
            pass = propFile.getProperty("mail.password");
            from = propFile.getProperty("mail.from");
            host = propFile.getProperty("mail.host");
            port = propFile.getProperty("mail.port");
            protocol = propFile.getProperty("mail.protocol");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            log.error("加载属性文件失败");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("加载属性文件失败");
        }

    }

    public void run(){
        System.out.println("【PaymentTimeTask】start");
        sendMail();
    }


    //用户名密码验证，需要实现抽象类Authenticator的抽象方法PasswordAuthentication
    static class MyAuthenricator extends Authenticator {
        String u = null;
        String p = null;
        public MyAuthenricator(String u,String p){
            this.u=u;
            this.p=p;
        }
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(u,p);
        }
    }

    private String sendMail(){
        final List<QIPayment> list = iqiPaymentService.selectAllNeedSend();
        if (list.size()>0){
            if (protocol == null){
                init();
            }
            Properties prop = new Properties();
            //协议
            prop.setProperty("mail.transport.protocol", protocol);
            //服务器
            prop.setProperty("mail.smtp.host", host);
            //端口
            prop.setProperty("mail.smtp.port", port);
            //使用smtp身份验证
            prop.setProperty("mail.smtp.auth", "true");
            //使用SSL，企业邮箱必需！
            //开启安全协议
            MailSSLSocketFactory sf = null;
            try {
                sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
            } catch (GeneralSecurityException e1) {
                e1.printStackTrace();
                log.error("GeneralSecurityException");
            }
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);
            //
            Session session = Session.getDefaultInstance(prop, new MyAuthenricator(account, pass));
            session.setDebug(true);
            try {
                Transport transport = session.getTransport();
                transport.connect(host,Integer.valueOf(port),account,pass);
                transport.addTransportListener(new TransportListener() {

                    @Override
                    public void messagePartiallyDelivered(TransportEvent e) {
                    }

                    @Override
                    public void messageNotDelivered(TransportEvent e) {
                        Message message = e.getMessage();
                        try {
                            String[] messageid = message.getHeader("mailid");
                            for(QIPayment mail:list){
                                if(messageid[0].equals(mail.getRequestid()+"")){
                                    mail.setProcesscode(0);
                                    iqiPaymentService.updateByPrimaryKeySelective(mail);
                                    break;
                                }
                            }
                        } catch (MessagingException e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void messageDelivered(TransportEvent e) {
                        Message message = e.getMessage();

                        try {
                            String[] messageid = message.getHeader("mailid");
                            for(QIPayment mail:list){
                                if(messageid[0].equals(mail.getRequestid()+"")){
                                    mail.setProcesstime(new Date());
                                    iqiPaymentService.updateByPrimaryKeySelective(mail);
                                    break;
                                }
                            }
                        } catch (MessagingException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                for(QIPayment mail:list){
                    if(mail.getProcesscode() == null || mail.getProcesscode() == 0){

                        MimeMessage mimeMessage = new MimeMessage(session);
                        mimeMessage.setFrom(new InternetAddress(from,"OA邮件提醒"));
                        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getEmail()));
                        mimeMessage.setSubject("【"+mail.getContractCode()+"】【"+mail.getRequestnamenew()+"】已经支付成功");
                        mimeMessage.setSentDate(new Date());
                        mimeMessage.setHeader("mailid", mail.getRequestid()+"");
                        Multipart mainPart = new MimeMultipart();
                        MimeBodyPart messageBodyPart = new MimeBodyPart();//创建一个包含HTML内容的MimeBodyPart
                        String content = "【"+mail.getContractCode()+"】" +
                                "【"+mail.getRequestnamenew()+"】" +
                                "已经支付成功" +
                                "<a href=\"http://mis.qdingnet.com/workflow/request/ViewRequest.jsp?requestid="+mail.getRequestid()+"\">点击链接</a>";

                        messageBodyPart.setContent(content,"text/html; charset=utf-8");
                        mainPart.addBodyPart(messageBodyPart);

                        mimeMessage.setContent(mainPart);
                        mail.setProcesstime(new Date());
                        mimeMessage.saveChanges();
                        transport.sendMessage(mimeMessage,mimeMessage.getRecipients(Message.RecipientType.TO));
                        log.info("发送邮件至:"+mail.getEmail()+",邮件内容:"+content);
                        mail.setProcesscode(1);
                    }
                }
                transport.close();
            } catch (NoSuchProviderException e1) {
                e1.printStackTrace();
                log.error("NoSuchProviderException");
            } catch (MessagingException e) {
                e.printStackTrace();
                log.error("MessagingException");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                log.error("UnsupportedEncodingException");
            } catch (Exception e){
                e.printStackTrace();
                log.error("UnsupportedEncodingException");
            }
        }
        return "success";
    }
}
