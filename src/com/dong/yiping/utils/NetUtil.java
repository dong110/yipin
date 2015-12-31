package com.dong.yiping.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

public class NetUtil {
	
	private static String TAG = "NetUtil";
	
	public static String sendPost(String path, Map<String, String> paramsMap) {
		
			InputStream is = null;
			DataOutputStream out = null;
			HttpURLConnection connection = null;
			try {// Post请求的url，与get不同的是不需要带参数
		        URL postUrl = new URL(path);
		        // 打开连接
		        connection = (HttpURLConnection) postUrl.openConnection();
		        
		        // 设置是否向connection输出，因为这个是post请求，参数要放在
		        // http正文内，因此需要设为true
		        connection.setDoOutput(true);
		        // Read from the connection. Default is true.
		        connection.setDoInput(true);
		        // 默认是 GET方式
		        connection.setRequestMethod("POST");
		      
		        // Post 请求不能使用缓存
		        connection.setUseCaches(false);
		      
		        connection.setInstanceFollowRedirects(true);
		      
		        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
		        // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
		        // 进行编码
		        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
		        // 要注意的是connection.getOutputStream会隐含的进行connect。
		        connection.connect();
		        out = new DataOutputStream(connection
		                .getOutputStream());
		        // The URL-encoded contend
		        // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
		        String content ="";
		        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
		        for (String key : paramsMap.keySet()) {
		        	content += key+"="+paramsMap.get(key)+"&";
		        	
		        }
		        if(content != null){
		        	content = content.substring(0, content.length()-1);
			        out.writeBytes(content);
		        }
		        
		        out.flush();
		        
		        
		        int code = connection.getResponseCode();
		        
	            if (code == 200) {
	                is = connection.getInputStream();
	                String result = StreamTools.readStream(is);
	                LogUtil.i(TAG, result);
	                
	                return result;
	            } else {
	                //服务返回数据失败
	                return null;
	            }
	            
	            
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally{
				try {
					if(out!=null){
						out.close();
					}
					if(is != null){
						is.close();
					}
					if(connection != null){
						connection.disconnect();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
	  }
	
	 public static String doGet(String path){
		 HttpURLConnection conn=null;
		 InputStream is = null;
	        try {

	            // Install the all-trusting trust manager
	            // 注意这部分一定要
	            // 1、创建URL对象、打开HTTP类型的连接；
	            URL url = new URL(path);
	            conn = (HttpURLConnection) url
	                    .openConnection();


	            // 2、设置请求头信息：GET；

	            conn.setRequestMethod("GET");
	            // 设置连接的超时时间
	            conn.setConnectTimeout(3000);

	            // 3、获取服务器端返回的响应数据，二进制的输入流；

	            int code = conn.getResponseCode();
	            if (code == 200) {
	                is = conn.getInputStream();
	                String result = StreamTools.readStream(is);
	                LogUtil.i(TAG, result);
	                return result;
	            } else {
	                //服务返回数据失败
	                return null;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            //联网失败
	            return null;
	        } finally{
	        	try {
					if(is !=null){
						is.close();
					}
					if(conn!=null){
						conn.disconnect();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
	        }
	    }
}
