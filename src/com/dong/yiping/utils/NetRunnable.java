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
	public NetRunnable(Handler mHandler, String url, Map<String, String> paramMap) {
		this.mHandler = mHandler;
		this.url = url;
		this.paramMap = paramMap;
	}
	public NetRunnable(Handler mHandler, String url) {
		this.mHandler = mHandler;
		this.url = url;
	}
	@Override
	public void run() {
		if(paramMap != null || paramMap.size() != 0){
			result = NetUtil.sendPost(url, paramMap);
		}else{
			result = NetUtil.doGet(url);
		}
		if(result == null){
			mHandler.sendEmptyMessage(Constant.NET_ERROR);
		}else{
			Message msg = mHandler.obtainMessage();
			msg.what = Constant.NET_SUCCESS;
			msg.obj = result;
			mHandler.sendMessage(msg);
		}
	}

}
