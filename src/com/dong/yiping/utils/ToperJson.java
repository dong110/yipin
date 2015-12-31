package com.dong.yiping.utils;

import org.json.JSONObject;

import com.dong.yiping.Constant;
import com.dong.yiping.bean.BannerListBean;
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
	public  void toperStart(int type,String result){
		switch (type) {
		case Constant.TOPER_TYPE_LOGIN:
			toperLoginJson(result);
			break;
			
		case Constant.TOPER_TYPE_BANNERLIST:
			toperBannerList(result);
			
			break;
		}
	}
	
	/**
	 * 解析轮播图
	 * @param result
	 */
	private void toperBannerList(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.getInt("status");
			if(status == 0){
				BannerListBean bean = gson.fromJson(result, BannerListBean.class);
				Message msg = handler.obtainMessage();
				msg.obj = bean;
				msg.what = Constant.HANDLER_BANNERLIST;
				handler.sendMessage(msg);
			}
			if(status == 1){
				handler.sendEmptyMessage(Constant.HANDLER_BANNERLIST);
			}
			
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.HANDLER_BANNERLIST);
			
			e.printStackTrace();
			
		}
	}
	/**
	 * 解析登录结构Json
	 * @param result
	 */
	private  void toperLoginJson(String result){
		try {
			JSONObject jsonObject = new JSONObject(result);
			boolean status = jsonObject.getBoolean("status");
			
			if(status){
				UserBean bean = gson.fromJson(result, UserBean.class);
				Message msg = handler.obtainMessage();
				msg.what = Constant.NET_SUCCESS;
				msg.obj = bean;
				handler.sendMessage(msg);
			}else{
				handler.sendEmptyMessage(Constant.NET_ERROR);
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.NET_ERROR);
			e.printStackTrace();
		}
		
		
	} 
	
}
