package com.cn.mis.timetask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import com.cn.mis.domain.bean.TokenInfo;
import com.cn.mis.utils.http.HttpClientUtil;
import com.cn.mis.utils.json.JsonUtil;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Log4j
public class TokenThread implements Runnable {

	private String grant_type;
	private String username;
	private String password;
	private String client_id;
	private String client_secret;
	private String redirect_uri;

	public static TokenInfo tonkenInfo = null;

	public void init(){
		log.info("初始化【TokenThread】，执行init()方法读取配置文件");
		try {
			Properties propFile = new Properties();
			InputStream instream = getClass().getResourceAsStream("/token.properties");
			propFile.load(instream);
			grant_type = propFile.getProperty("crm.grant_type");
			username = propFile.getProperty("crm.username");
			password = propFile.getProperty("crm.password");
			client_id = propFile.getProperty("crm.client_id");
			client_secret = propFile.getProperty("crm.client_secret");
			redirect_uri = propFile.getProperty("crm.redirect_uri");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			log.error("加载属性文件失败");
		} catch (IOException e) {
			e.printStackTrace();
			log.error("加载属性文件失败");
		}
	}

	@Override
	public void run() {
		init();
		while (true) {
			try {
				tonkenInfo = getToken();
				if (null != tonkenInfo) {
					log.info("获取access_token成功，有效时长秒{"+24*60*60*1000+"} token:{"+tonkenInfo.getAccess_token()+"}");
					Thread.sleep(23*60*60*1000);
				} else {
					// 如果access_token为null，60秒后再获取
					log.info("获取access_token失败，60s后重试");
					Thread.sleep(60 * 1000);
				}
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		
	}
	
	public TokenInfo getToken(){
		HashMap<String,String> tokenParams = new HashMap<String,String>();
		tokenParams.put("grant_type", grant_type);
		tokenParams.put("client_id", client_id);
		tokenParams.put("client_secret", client_secret);
		tokenParams.put("redirect_uri", redirect_uri);
		tokenParams.put("username", username);
		tokenParams.put("password", password);
		String token = HttpClientUtil.sendPostSSLRequest("https://api.xiaoshouyi.com/oauth2/token.action", tokenParams);
		TokenInfo tokenInfo = (TokenInfo) JsonUtil.fromJson(new TypeToken<TokenInfo>(){}.getType(),token);
		return tokenInfo;
   }
}
