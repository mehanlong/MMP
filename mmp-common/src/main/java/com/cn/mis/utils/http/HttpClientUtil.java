package com.cn.mis.utils.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 封装了一些采用HttpClient发送HTTP请求的方法
 * 
 * @author magenm
 * 
 */
@SuppressWarnings("deprecation")
public class HttpClientUtil {

	private static Logger log = Logger.getLogger(HttpClientUtil.class);
	
	private static HttpClient client = MyClient.createDefaultClient();
	
//	httpGet.setHeader("Bearer", "b216c1cef3c87f3b88324f68fc9920e3c7d17f4e341b87c3d05de620b783c794");
	private HttpClientUtil() {
	}

	/**
	 * 发送HTTP_GET请求
	 * 该方法会自动关闭连接,释放资源
	 * @param reqURL  请求地址(含参数)
	 * @param decodeCharset  解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendGetRequest(String reqURL, String decodeCharset) {
		String responseContent = null; // 响应内容
//      HttpClient httpClient = new DefaultHttpClient();
//		HttpClient httpClient = QDClient.createDefaultClient();
		HttpGet httpGet = new HttpGet(reqURL); // 创建org.apache.http.client.methods.HttpGet
		
		HttpEntity entity = null;
		try {
			HttpResponse response = client.execute(httpGet); // 执行GET请求
			System.out.println(response);
			entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);				
			}
		} catch (Exception e) {
			log.error("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下", e);
		} finally {
//			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
		    // Consume response content
		    try {
                EntityUtils.consume(entity);
            } catch (IOException ex) {
                log.warn("net io excepiton", ex);
            } 
		}
		return responseContent;
	}
    public static JSONObject sendGetRequest4Json(String reqURL, String decodeCharset) {
	    String responseContent = sendGetRequest(reqURL,decodeCharset); // 响应内容
	    JSONObject jo = null;
	    if (StringUtils.isNotBlank(responseContent)){
	        try {
	            jo = JSON.parseObject(responseContent);
            } catch ( Exception e) {
                e.printStackTrace();
            }
	    }
	    return jo;
	}
    public static JSONObject sendGetRequest4Json(String reqURL) {
        return sendGetRequest4Json(reqURL,null);
    }

	/**
	 * 发送HTTPS_GET请求
	 * 该方法会自动关闭连接,释放资源
	 * 该方法会自动对
     * <code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL 请求地址（含参数）
	 * @param decodeCharset  解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendGetSSLRequest(String reqURL, String decodeCharset) {
		String responseContent = "";
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpClient httpClient = QDClient.createDefaultClient();
		HttpEntity entity = null;
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] { xtm }, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			client.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

			HttpGet httpGet = new HttpGet(reqURL);

			
			HttpResponse response = client.execute(httpGet);
			entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);
			}
		} catch (Exception e) {
			log.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
		} finally {
//			httpClient.getConnectionManager().shutdown();
		 // Consume response content
            try {
                EntityUtils.consume(entity);
            } catch (IOException ex) {
                log.warn("net io excepiton", ex);
            } 
		}
		return responseContent;
	}

	/**
	 * http post
	 * 
	 * @param url
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String httpPost(String url, Map<String, String> params,
			String encoding) throws Exception {
//		DefaultHttpClient httpClient = new DefaultHttpClient();
//	    HttpClient httpClient = QDClient.createDefaultClient();
		System.out.println("url:" + url);
		HttpPost post = new HttpPost(url);
		List<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			postData.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
			System.out.println(entry.getValue());
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postData,
				encoding);
		post.setEntity(entity);
		HttpResponse response = client.execute(post);

		return getContent(response, encoding);
	}

	public static String getContent(HttpResponse res, String encoding)
			throws Exception {
		HttpEntity ent = res.getEntity();
		String result = IOUtils.toString(ent.getContent(), encoding);
		EntityUtils.consume(ent);
		return result;
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * 该方法为
     * <code>sendPostRequest(String,String,boolean,String,String)</code>
	 *      的简化方法
	 * 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * 当
     * <code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][
	 *      ]等特殊字符进行<code>URLEncoder.encode(string,"UTF-8")</code>
	 * @param isEncoder 用于指明请求数据是否需要UTF-8编码,true为需要
	 */
	public static String sendPostRequest(String reqURL, String sendData,
			boolean isEncoder) {
		return sendPostRequest(reqURL, sendData, isEncoder, null, null);
	}

	/**
	 * 发送HTTP_POST请求
	 * 该方法会自动关闭连接,释放资源
	 * 当
     * <code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][
	 *      ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL
	 *            请求地址
	 * @param sendData
	 *            请求参数,若有多个参数则应拼接成param11=value11¶m22=value22¶m33=value33的形式后,
	 *            传入该参数中
	 * @param isEncoder
	 *            请求数据是否需要encodeCharset编码,true为需要
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL, String sendData,
			boolean isEncoder, String encodeCharset, String decodeCharset) {
		String responseContent = null;
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpClient httpClient = QDClient.createDefaultClient();
		HttpPost httpPost = new HttpPost(reqURL);
		HttpEntity entity = null;
		// httpPost.setHeader(HTTP.CONTENT_TYPE,
		// "application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.setHeader(HTTP.CONTENT_TYPE,
				"application/x-www-form-urlencoded");
		try {
			if (isEncoder) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				for (String str : sendData.split("&")) {
					formParams.add(new BasicNameValuePair(str.substring(0,
							str.indexOf("=")),
							str.substring(str.indexOf("=") + 1)));
				}
				httpPost.setEntity(new StringEntity(URLEncodedUtils.format(
						formParams, encodeCharset == null ? "UTF-8"
								: encodeCharset)));
			} else {
				httpPost.setEntity(new StringEntity(sendData));
			}

			HttpResponse response = client.execute(httpPost);
			entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);
			}
		} catch (Exception e) {
			log.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
		} finally {
//			httpClient.getConnectionManager().shutdown();
		    try {
                EntityUtils.consume(entity);
            } catch (IOException ex) {
                log.warn("net io excepiton", ex);
            } 
		}
		return responseContent;
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * 该方法会自动关闭连接,释放资源
	 * 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL,
			Map<String, String> params, String encodeCharset,
			String decodeCharset) {
		String responseContent = null;
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpClient httpClient = QDClient.createDefaultClient();
		HttpEntity entity = null;
		HttpPost httpPost = new HttpPost(reqURL);
		List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 创建参数队列
		for (Map.Entry<String, String> entry : params.entrySet()) {
			formParams.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(formParams,
					encodeCharset == null ? "UTF-8" : encodeCharset));

			HttpResponse response = client.execute(httpPost);
			entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);
				
			}
		} catch (Exception e) {
			log.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
		} finally {
//			httpClient.getConnectionManager().shutdown();
			try {
                EntityUtils.consume(entity);
            } catch (IOException ex) {
                log.warn("net io excepiton", ex);
            }
		}
		return responseContent;
	}

	/**
	 * 发送HTTPS_POST请求
	 * 
	 * 该方法为<code>sendPostSSLRequest(String,Map<String,String>,String,String)</code>
	 *      方法的简化方法
	 * 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,"UTF-8")</code>
	 */
	public static String sendPostSSLRequest(String reqURL, Map<String, String> params) {
		return sendPostSSLRequest(reqURL, params, null, null);
	}

	/**
	 * 发送HTTPS_POST请求
	 * 
	 * 该方法会自动关闭连接,释放资源
	 * 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostSSLRequest(String reqURL, Map<String, String> params, String encodeCharset,
			String decodeCharset) {
		String responseContent = "";
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpClient httpClient = QDClient.createDefaultClient();
        HttpEntity entity = null;
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] { xtm }, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			client.getConnectionManager().getSchemeRegistry()
					.register(new Scheme("https", 443, socketFactory));

			HttpPost httpPost = new HttpPost(reqURL);
			if (null != params) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					formParams.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(formParams,
						encodeCharset == null ? "UTF-8" : encodeCharset));
			}
			HttpResponse response = client.execute(httpPost);
			entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);
				
			}
		} catch (Exception e) {
			log.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
		} finally {
//			httpClient.getConnectionManager().shutdown();
		    try {
                EntityUtils.consume(entity);
            } catch (IOException ex) {
                log.warn("net io excepiton", ex);
            };
		}
		return responseContent;
	}
	
	public static String sendPostSSLRequest(String reqURL, String param) {	    
	    return sendPostSSLRequest(reqURL, param, null, null);
	}
	
	public static String sendPostSSLRequest(String reqURL, String param, String encodeCharset,
            String decodeCharset) {
        String responseContent = "";
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpClient httpClient = QDClient.createDefaultClient();
        HttpEntity entity = null;
        X509TrustManager xtm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[] { xtm }, null);
            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx,
                    SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.getConnectionManager().getSchemeRegistry()
                    .register(new Scheme("https", 443, socketFactory));

            HttpPost httpPost = new HttpPost(reqURL);
            if (StringUtils.isNotEmpty(param)) {
                httpPost.setEntity(new StringEntity(param,"UTF-8"));
            }
            
            HttpResponse response = client.execute(httpPost);
            entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity,
                        decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            log.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
        } finally {
//            httpClient.getConnectionManager().shutdown();
            try {
                EntityUtils.consume(entity);
            } catch (IOException ex) {
                log.warn("net io excepiton", ex);
            };
        }
        return responseContent;
    }

	/**
	 * 发送HTTP_POST请求
	 * 
	 * 若发送的<code>params</code>中含有中文,记得按照双方约定的字符集将中文
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            发送到远程主机的正文数据,其数据类型为<code>java.util.Map<String, String></code>
	 * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>
	 *         若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
	 */
	public static String sendPostRequestByJava(String reqURL,
			Map<String, String> params) {
		StringBuilder sendData = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sendData.append(entry.getKey()).append("=")
					.append(entry.getValue()).append("&");
		}
		if (sendData.length() > 0 && sendData.toString().endsWith("&")) {
			sendData.setLength(sendData.length() - 1); // 删除最后一个&符号
		}
		return sendPostRequestByJava(reqURL, sendData.toString());
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * 若发送的<code>sendData</code>中含有中文,记得按照双方约定的字符集将中文
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
	 * @param reqURL
	 *            请求地址
	 * @param sendData
	 *            发送到远程主机的正文数据
	 * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>
	 *         若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
	 */
	public static String sendPostRequestByJava(String reqURL, String sendData) {
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null; // 写
		InputStream in = null; // 读
		int httpStatusCode = 0; // 远程主机响应的HTTP状态码
		try {
			URL sendUrl = new URL(reqURL);
			httpURLConnection = (HttpURLConnection) sendUrl.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true); // 指示应用程序要将数据写入URL连接,其值默认为false
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setConnectTimeout(30000); // 30秒连接超时
			httpURLConnection.setReadTimeout(30000); // 30秒读取超时

			out = httpURLConnection.getOutputStream();
			out.write(sendData.toString().getBytes("UTF-8"));

			// 清空缓冲区,发送数据
			out.flush();

			// 获取HTTP状态码
			httpStatusCode = httpURLConnection.getResponseCode();

			in = httpURLConnection.getInputStream();
			byte[] byteDatas = new byte[in.available()];
			in.read(byteDatas);
			return new String(byteDatas);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "Failed`" + httpStatusCode;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					log.error("关闭输出流时发生异常,堆栈信息如下", e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					log.error("关闭输入流时发生异常,堆栈信息如下", e);
				}
			}
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
		}
	}

	/**
	 * 
	 * @param uri
	 * @param host
	 * @param authorization
	 * @return
	 */
	public static String httpPostData(String host, String uri,
			String authorization, String data) {
//	    HttpClient httpClient = QDClient.createDefaultClient();
        HttpEntity entity = null;
		try {
//			HttpClient httpclient = new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost("http://" + host + uri);
			// 添加http头信息
			httppost.addHeader("Host", host);
			httppost.addHeader("Authorization", authorization);
			/* httppost.addHeader("Date", DateUtils.fromatRFC1123(new Date())); */

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("data", data));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = client.execute(httppost);
			// 检验状态码，如果成功接收数据
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				String rev = EntityUtils.toString(response.getEntity());
				return rev;
			}
			entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (Exception ex) {
			log.warn("io occurs exception", ex);
		}  finally {
		    try {
                EntityUtils.consume(entity);
            } catch (IOException ex) {
                log.warn("net io excepiton", ex);
            }; 
		}
		return "exception";
	}
	
	

	public static String postFile(File file, String url, String partName) {

		try {
//			HttpClient httpclient = new DefaultHttpClient();
//			HttpClient httpClient = QDClient.createDefaultClient();
			HttpPost filePost = new HttpPost(url);

			MultipartEntity reqEntity = new MultipartEntity();
			FileBody fileBody = new FileBody(file);
			reqEntity.addPart(partName, fileBody);

			// 设置请求
			filePost.setEntity(reqEntity);

			HttpResponse response = client.execute(filePost);
			int code = response.getStatusLine().getStatusCode();
			String rev = EntityUtils.toString(response.getEntity());
			log.info(code + " resulit is  " + rev);
			return rev;

		} catch (Exception ex) {
		    log.warn("io occurs exception", ex);
		}

		return null;
	}

	public static String sendGetRequest2(String reqURL, String decodeCharset) {
		String responseContent = ""; // 响应内容
//		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
//		HttpClient httpClient = QDClient.createDefaultClient();
        HttpEntity entity = null;
		HttpGet httpGet = new HttpGet(reqURL); // 创建org.apache.http.client.methods.HttpGet
		try {
			HttpResponse response = client.execute(httpGet); // 执行GET请求
			System.out.println(response);
			entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				// 取得输入流，并使用Reader读取
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent()));
				String lines = null;
				while ((lines = reader.readLine()) != null) {
					responseContent += lines;
				}
				reader.close();
			}
		} catch (Exception e) {
			log.error("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下", e);
		} finally {
//			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
		    try {
                EntityUtils.consume(entity);
            } catch (IOException ex) {
                log.warn("net io excepiton", ex);
            };
		}
		return responseContent;
	}

	public static String readContentFromGet(String url) throws IOException {
		// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
		URL getUrl = new URL(url);
		// 根据拼凑的URL，打开连接，URL.openConnection函数会根据 URL的类型，
		// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		// 进行连接，但是实际上get request要在下一句的 connection.getInputStream()函数中才会真正发到
		// 服务器
		connection.connect();
		// 取得输入流，并使用Reader读取
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String lines = null, jsonStr = "";
		while ((lines = reader.readLine()) != null) {
			jsonStr += lines;
		}

		reader.close();
		// 断开连接
		connection.disconnect();
		System.out.println(jsonStr);
		return jsonStr;
	}
	
	/** 
     * 发送get请求 
     * @param url       链接地址 
     * @param charset   字符编码，若为null则默认utf-8 
     * @return 
     */  
    public static String doGet(String url,String charset){  
        if(null == charset){  
            charset = "utf-8";
        }  
        HttpClient httpClient = null;  
        HttpGet httpGet= null;  
        String result = null;  
          
        try {  
            httpClient = new SSLClient();  
            httpGet = new HttpGet(url);  
              
            HttpResponse response = httpClient.execute(httpGet);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
        return result;  
    }  
    
	public static String sendCrmSSLPostRequest(String reqURL, HashMap<String,String> paramMap, String authorization,String encodeCharset,
            String decodeCharset) {
        String responseContent = "";
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpClient httpClient = QDClient.createDefaultClient();
        HttpEntity entity = null;
        X509TrustManager xtm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[] { xtm }, null);
            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx,
                    SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.getConnectionManager().getSchemeRegistry()
                    .register(new Scheme("https", 443, socketFactory));

            HttpPost httpPost = new HttpPost(reqURL);
            httpPost.addHeader("Authorization", authorization);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : paramMap.entrySet()){
            	params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//            if (StringUtils.isNotEmpty(param)) {
//                httpPost.setEntity(new StringEntity(param,"UTF-8"));
//            }
            
            HttpResponse response = client.execute(httpPost);
            entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity,
                        decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            log.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
        } finally {
//            httpClient.getConnectionManager().shutdown();
            try {
                EntityUtils.consume(entity);
            } catch (IOException ex) {
                log.warn("net io excepiton", ex);
            };
        }
        return responseContent;
    }
    
	public static String sendCrmSSLGetReqset(String reqURL, String decodeCharset,String authorization){
    	String responseContent = "";
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpClient httpClient = QDClient.createDefaultClient();
		HttpEntity entity = null;
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] { xtm }, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			client.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

			HttpGet httpGet = new HttpGet(reqURL);
			httpGet.addHeader("Authorization", authorization);
			
			HttpResponse response = client.execute(httpGet);
			entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);
			}
		} catch (Exception e) {
			log.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
		} finally {
//			httpClient.getConnectionManager().shutdown();
		 // Consume response content
            try {
                EntityUtils.consume(entity);
            } catch (IOException ex) {
                log.warn("net io excepiton", ex);
            } 
		}
		return responseContent;
    } 

	public static void main(String[] args) {
		File file = new File("d:/data/test.jpg");
		HttpClientUtil
				.postFile(
						file,
						"http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE",
						"media");
	}

}
