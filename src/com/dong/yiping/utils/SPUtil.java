package com.dong.yiping.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.dong.yiping.bean.UserBean;
import com.dong.yiping.bean.StarStudentBean.Student;

public class SPUtil {

	private static final String SP_NAME = "config";
	private static SharedPreferences sp;

	public static void saveBoolean(Context context, String key, boolean value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		sp.edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		return sp.getBoolean(key, defValue);
	}

	public static void saveString(Context context, String key, String value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		sp.edit().putString(key, value).commit();
	}

	public static String getString(Context context, String key, String defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		return sp.getString(key, defValue);
	}

	public static void saveInt(Context context, String key, int value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		sp.edit().putInt(key, value).commit();
	}

	public static int getInt(Context context, String key, int defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		return sp.getInt(key, defValue);
	}
	/**
	 * 保存用户信息
	 * @param context
	 * @param bean
	 */
	public static void saveUser(Context context,UserBean bean) {
		if (sp == null){
			sp = context.getSharedPreferences(SP_NAME, 0);
		}
		Editor editor = sp.edit();
		editor.putInt("id", bean.getObj().getId());
		editor.putString("username", bean.getObj().getUsername());
		editor.putString("password", bean.getObj().getPwd());
		editor.putInt("type", bean.getObj().getType());
		editor.commit();
	}
	public static void clearUser(Context context) {
		if (sp == null){
			sp = context.getSharedPreferences(SP_NAME, 0);
		}
		
		Editor editor = sp.edit();
		editor.putInt("id", -1);
		editor.putString("username", "");
		editor.putString("password", "");
		editor.putInt("type", -1);
		editor.commit();
	}
}
