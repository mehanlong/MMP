package com.cn.mis.utils.email;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.log4j.Log4j;

import javax.mail.*;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by yuejia on 2017/2/21.
 */
@Log4j
public class EmailSender {
    private Transport transport;
    private TransportListener transportListener;

    public EmailSender(TransportListener transportListener){
        this.transportListener = transportListener;
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

    public String send(EMailProperties eMail){
        Properties prop = new Properties();
        //协议
        prop.setProperty("mail.transport.protocol", eMail.getProtocol());
        //服务器
        prop.setProperty("mail.smtp.host", eMail.getHost());
        //端口
        prop.setProperty("mail.smtp.port", eMail.getPort());
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
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        //
        Session session = Session.getDefaultInstance(prop, new MyAuthenricator(eMail.getAccount(), eMail.getPass()));
        session.setDebug(true);
        String[] toList = eMail.getToListStr().split(",");
        try {
            transport = session.getTransport();
            transport.connect(eMail.getHost(),Integer.valueOf(eMail.getPort()),eMail.getAccount(),eMail.getPass());
            transport.addTransportListener(transportListener);
            for(int i=0;i<toList.length;i++){
                String to = toList[i];
                MimeMessage mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(eMail.getFrom(),eMail.getCompany()));
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setSubject(eMail.getSubject());
                mimeMessage.setSentDate(eMail.getSendDate());
                for (Map.Entry<String, String> entry : eMail.getHeader().entrySet()){
                    mimeMessage.setHeader(entry.getKey(),entry.getValue());
                }
                mimeMessage.setText(eMail.getContent());
                mimeMessage.saveChanges();
                transport.sendMessage(mimeMessage,mimeMessage.getRecipients(Message.RecipientType.TO));
                log.info("发送邮件至:"+to+",邮件内容:"+eMail.getContent());
            }
            transport.close();
        } catch (NoSuchProviderException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
