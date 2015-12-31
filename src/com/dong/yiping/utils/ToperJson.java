package com.dong.yiping.utils;

import org.json.JSONObject;

import com.dong.yiping.Constant;
import com.dong.yiping.bean.UserBean;
import com.google.gson.Gson;

import android.os.Handler;
import android.os.Message;
/**
 * 解析Json工具类
 * @author dong
 *
 */
public class ToperJson {
	
	private Handler handler;
	private Gson gson;
	public ToperJson(Handler handler) {
		this.handler = handler;
		gson = new Gson();
	}
	/**
	 * 解析登录结构Json
	 * @param result
	 */
	public  void toperLoginJson(String result){
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.getInt("status");
			if(status == 0){
				handler.sendEmptyMessage(Constant.NET_ERROR);
			}
			if(status == 1){
				UserBean bean = gson.fromJson(result, UserBean.class);
				Message msg = handler.obtainMessage();
				msg.what = Constant.NET_SUCCESS;
				msg.obj = bean;
				handler.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	} 
	
}
