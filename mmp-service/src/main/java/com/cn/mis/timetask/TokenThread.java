package com.cn.mis.timetask;

import java.util.HashMap;

import com.cn.mis.domain.bean.TokenInfo;
import com.cn.mis.utils.http.HttpClientUtil;
import com.cn.mis.utils.json.JsonUtil;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j;

@Log4j
public class TokenThread implements Runnable {
	public static String grant_type = "password";  
    public static String username = "menghu@qding.me";  
    public static String password = "1qaz2wsxzNSA1qak";
	//线上
	public static String client_id = "871b2e1cde6aaadec8f23f31ecd5d0d6";
	public static String client_secret = "72370e4b2d815ca19a8b546df5bbaf19";
	public static String redirect_uri = "http://10.37.253.9";
	//测试
//    public static String client_id = "491f469ea43fb6e0ad9a4f2b57d014e1";  
//    public static String client_secret = "e8d9d9789698ac61024802672a4ac776";
//    public static String redirect_uri = "http://127.0.0.1"; 
    
    public static TokenInfo tonkenInfo = null;

	@Override
	public void run() {
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
