package com.cn.mis.timetask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.cn.mis.domain.entity.XCEmail;
import com.cn.mis.service.IXCEmailService;
import com.cn.mis.utils.date.DateStyle;
import com.cn.mis.utils.date.DateUtil;
import org.springframework.stereotype.Component;

import com.sun.mail.util.MailSSLSocketFactory;

import lombok.extern.log4j.Log4j;
@Log4j
@Component("mailTimeTask")
public class MailTimeTask {
	private String account;		//登录用户名
    private String pass;       	//登录密码
    private String from;        //发件地址
    private String host;        //服务器地址
    private String port;       	//端口
    private String protocol; 	//协议
    private String toListStr;
	
	@Resource
	private IXCEmailService xcEmailSrevice;
	
	//用户名密码验证，需要实现抽象类Authenticator的抽象方法PasswordAuthentication
    static class MyAuthenricator extends Authenticator{  
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

	@PostConstruct
	private void init(){
		log.info("初始化【MailTimeTask】，执行init()方法读取配置文件");
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
			toListStr = propFile.getProperty("mail.tolist");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			log.error("加载属性文件失败");
		} catch (IOException e) {
			e.printStackTrace();
			log.error("加载属性文件失败");
		}

	}

    public void run() {
		System.out.println("【MailTimeTask】start");
		sendMail();
	}
	private String sendMail(){
		final List<XCEmail> list = xcEmailSrevice.selectAll();
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
			String[] toList = toListStr.split(",");
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
							for(XCEmail mail:list){
								if(messageid[0].equals(mail.getId()+"")){
									mail.setProcessFlag((byte) 0);
									xcEmailSrevice.updateByPrimaryKeySelective(mail);
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
							for(XCEmail mail:list){
								if(messageid[0].equals(mail.getId()+"")){
									mail.setProcessTime(new Date());
									xcEmailSrevice.updateByPrimaryKeySelective(mail);
									break;
								}
							}
						} catch (MessagingException e1) {
							e1.printStackTrace();
						}
					}
				});
				for(int i=0;i<toList.length;i++){
					String to = toList[i];
					for(XCEmail mail:list){
						if(mail.getProcessFlag() == null || mail.getProcessFlag() == 0){
							MimeMessage mimeMessage = new MimeMessage(session);
							mimeMessage.setFrom(new InternetAddress(from,mail.getCorpName()));
							mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
							mimeMessage.setSubject(mail.getBookingAgent()+"出差需要预定机票(详细信息见邮件正文)");
							mimeMessage.setSentDate(new Date());
							mimeMessage.setHeader("mailid", mail.getId()+"");
							String depDateTime = "";
							String destinationTime = "";
							if(mail.getAmOrPm() != null){
								depDateTime = mail.getAmOrPm()+"";
							}
							if(mail.getPmRoAm() != null){
								destinationTime = mail.getPmRoAm()+"";
							}

							String content = mail.getCorpName()+" "
									+mail.getPrimaryDepartment()+" "
									+mail.getAffiliationDepartment()+" "
									+mail.getName()+" "
									+mail.getBookingType()+" "
									+"乘机人 "+mail.getBookingAgent()+" "
									+"出发城市 "+mail.getDpartureCity()+" "
									+"到达城市 "+mail.getDestinationCity()+" "
									+"出发日期 "+ DateUtil.DateToString(mail.getDepartureDate(), DateStyle.YYYY_MM_DD)+" "+depDateTime+" "
									+"返回日期 "+DateUtil.DateToString(mail.getDestinationDate(), DateStyle.YYYY_MM_DD)+" "+destinationTime+" "
									+"邮件发送时间："+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);

							mimeMessage.setText(content);
							mail.setEmailTime(new Date());
							mimeMessage.saveChanges();
							transport.sendMessage(mimeMessage,mimeMessage.getRecipients(Message.RecipientType.TO));
							log.info("发送邮件至:"+to+",邮件内容:"+content);
							if(i+1 == toList.length){
								mail.setProcessFlag((byte)1);
							}
						}
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
			}
		}
		return "success";
	}
}
