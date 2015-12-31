package com.dong.yiping.utils;

import android.text.TextUtils;
import android.util.Log;

public class LogUtil {
	
	private static boolean isShow = true;//为true打印所有日志，为false则不打印日志
	
	public static void i(String TAG,String msg){
		if(isShow && msg != null && !TextUtils.isEmpty(msg)){
			Log.i(TAG, msg);
		}
	}
	
	public static void d(String TAG,String msg){
		if(isShow&& msg != null && !TextUtils.isEmpty(msg)){
			Log.d(TAG, msg);
		}
	}
	
	public static void e(String TAG,String msg){
		if(isShow&& msg != null && !TextUtils.isEmpty(msg)){
			Log.e(TAG, msg);
		}
	}
}
