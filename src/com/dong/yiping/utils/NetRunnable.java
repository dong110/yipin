package com.dong.yiping.utils;

import java.util.Map;

import com.dong.yiping.Constant;

import android.os.Handler;
import android.os.Message;

public class NetRunnable implements Runnable{

	private Handler mHandler;
	private String url;
	private String result;
	private Map<String, String> paramMap;
	private ToperJson toperJson;
	private int type;
	public NetRunnable(Handler mHandler, String url, Map<String, String> paramMap,int type) {
		this.mHandler = mHandler;
		this.url = url;
		this.paramMap = paramMap;
		this.type = type;
		toperJson = new ToperJson(mHandler);
	}
	public NetRunnable(Handler mHandler, String url,int type) {
		this.mHandler = mHandler;
		this.url = url;
		this.type = type;
		toperJson = new ToperJson(mHandler);
	}
	@Override
	public void run() {
		if(paramMap != null && paramMap.size() != 0){
			result = NetUtil.sendPost(url, paramMap);
		}else{
			result = NetUtil.doGet(url);
		}
		if(result == null){
			mHandler.sendEmptyMessage(Constant.NET_ERROR);
		}else{
			toperJson.toperStart(type,result);
		}
	}

}
