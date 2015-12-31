package com.dong.yiping.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {

	public static void showToast(Context mContext,String str){
		Toast toast = Toast.makeText(mContext, str, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	};
}
