package com.dong.yiping.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTools {
	
	private static String result = "";

	/**
	 *
	 * 把输入流转换成字符串
	 * @param is
	 * @return
	 */
	public static String readStream(InputStream is){
		
		try {
			byte[] buffer = new byte[1024];
			int len = -1;
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			while( (len = is.read(buffer)) != -1){
				baos.write(buffer, 0, len);
			}
			
			result  = new String(baos.toByteArray());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}

}
