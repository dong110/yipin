package com.dong.yiping.utils;

import org.json.JSONObject;

import com.dong.yiping.Constant;
import com.dong.yiping.bean.BannerListBean;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.GetZhaopinBean;
import com.dong.yiping.bean.StarCompanyBean;
import com.dong.yiping.bean.StarStudentBean;
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
		case Constant.TOPER_TYPE_GETJOB:
			toperGetJob(result);
			break;
		case Constant.TOPER_TYPE_GETZHAOPIN:
			toperGetZhaopin(result);
			break;
		case Constant.TOPER_TYPE_STARCOM:
			toperStarCompany(result);
			break;
		case Constant.TOPER_TYPE_STARTSTU:
			toperStarStudent(result);
			break;
		}
	}
	/**
	 * 解析明星学生
	 * @param result
	 */
	private void toperStarStudent(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.getInt("status");
			if(status == 0){
				StarStudentBean bean = gson.fromJson(result, StarStudentBean.class);
				Message msg = handler.obtainMessage();
				msg.obj = bean;
				msg.what = Constant.HANDLER_TYPE_STARTSTU;
				handler.sendMessage(msg);
			}
			if(status == 1){
				handler.sendEmptyMessage(Constant.HANDLER_TYPE_STARTSTU);
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.HANDLER_TYPE_STARTSTU);
			e.printStackTrace();
			
		}
		
	}
	/**
	 * 解析明星企业
	 * @param result
	 */
	private void toperStarCompany(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.getInt("status");
			if(status == 0){
				StarCompanyBean bean = gson.fromJson(result, StarCompanyBean.class);
				Message msg = handler.obtainMessage();
				msg.obj = bean;
				msg.what = Constant.HANDLER_TYPE_STARCOM;
				handler.sendMessage(msg);
			}
			if(status == 1){
				handler.sendEmptyMessage(Constant.HANDLER_TYPE_STARCOM);
			}
			
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.HANDLER_TYPE_STARCOM);
			
			e.printStackTrace();
			
		}
	}
	/**
	 * 解析我要招聘
	 * @param result
	 */
	private void toperGetZhaopin(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.getInt("status");
			if(status == 0){
				GetZhaopinBean bean = gson.fromJson(result, GetZhaopinBean.class);
				Message msg = handler.obtainMessage();
				msg.obj = bean;
				msg.what = Constant.HANDLER_TYPE_GETZHAOPIN;
				handler.sendMessage(msg);
			}
			if(status == 1){
				handler.sendEmptyMessage(Constant.HANDLER_TYPE_GETZHAOPIN);
			}
			
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.HANDLER_TYPE_GETZHAOPIN);
			
			e.printStackTrace();
			
		}
		
	}
	/**
	 * 解析我要求职
	 * @param result
	 */
	private void toperGetJob(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.getInt("status");
			if(status == 0){
				GetJobBean bean = gson.fromJson(result, GetJobBean.class);
				Message msg = handler.obtainMessage();
				msg.obj = bean;
				msg.what = Constant.HANDLER_TYPE_GETJOB;
				handler.sendMessage(msg);
			}
			if(status == 1){
				handler.sendEmptyMessage(Constant.HANDLER_TYPE_GETJOB);
			}
			
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.HANDLER_TYPE_GETJOB);
			
			e.printStackTrace();
			
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
