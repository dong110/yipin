package com.dong.yiping.activity;

import java.io.Serializable;

import roboguice.activity.RoboFragmentActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

import com.dong.yiping.MyApplication;
import com.dong.yiping.utils.ToastUtil;

public class BaseActivity extends RoboFragmentActivity implements OnClickListener {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	

public static final String DATA = "data";
private long[] times = { 0, 0 };
/**
 * 全局的context
 */
protected Context mContext;


// /////////////////////////////////////////查找子view\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

/**
 * findviewbyid
 * 
 * @param resId
 *            view 资源id
 * @param <T>
 *            view的类型
 * @return 指定类型的view 默认是不添加点击事件的
 */
public <T extends View> T $(int resId) {
	return $(resId, false);
}

/**
 * findviewbyid
 * 
 * @param resId
 * @param addListener
 *            是否要添加点击事件
 * @param <T>
 * @return
 */
public <T extends View> T $(int resId, boolean addListener) {
	T t = (T) (this.findViewById(resId));
	if (addListener)
		t.setOnClickListener(this);
	return t;
}

/**
 * 查找 子view 默认是不添加点击事件的
 * 
 * @param view
 *            从哪个view身上查找view
 * @param resId
 * @param <T>
 * @return 指定类型的view
 */

public <T extends View> T $(View view, int resId) {
	return $(view, resId, false);
}

/**
 * 查找 子view 添加点击事件的
 * 
 * @param resId
 * @param addListener
 *            是否要添加点击事件
 * @param <T>
 * @return
 */
public <T extends View> T $(View view, int resId, boolean addListener) {
	T t = (T) view.findViewById(resId);
	if (addListener)
		t.setOnClickListener(this);
	return t;
}

// /////////////////////////////////////////工具相关\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

// 控制 字体变大
@Override
public Resources getResources() {
	Resources res = super.getResources();
	Configuration configuration = new Configuration();
	configuration.setToDefaults();
	res.updateConfiguration(configuration, res.getDisplayMetrics());
	return res;
}

@Override
public void onBackPressed() {
	back();
}

protected void hideSoftInput() {
	InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
	if (im.isActive()) {
		// 如果开启
		// im.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		// 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
		try {
			im.hideSoftInputFromWindow(this.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {

		}
	}
}

private void back() {
	if (this.getClass().getSimpleName().equalsIgnoreCase("mainactivity")) {
		System.arraycopy(times, 0, times, 1, 1);
		times[0] = System.currentTimeMillis();
		// 退出app
		if ((times[0] - times[1]) > 3000) {
			ToastUtil.showToast(this, "再点击一次退出");
		} else {
			quitApp();
		}
	} else if (this.getClass().getSimpleName()
			.equalsIgnoreCase("SplashActivity")) {
		quitApp();
	} else {
		super.onBackPressed();
		MyApplication.removeActivity(this);
	}
}

/**
 * 填充布局
 */
public View inflate(int resId) {
	return View.inflate(mContext, resId, null);
}

/**
 * 安全退出app
 */
public void quitApp() {
	MyApplication.clearActivity();
}

/**
 * 跳转Activity
 */
protected void startToActivity(Class c) {
	startActivity(new Intent(this, c));
}

/**
 * 跳转Activity
 */
protected void startToActivity(Class c, String str) {
	Intent intent = new Intent(this, c);
	intent.putExtra(DATA, str);
	startActivity(intent);
}

/**
 * 跳转Activity
 */
protected void startToActivity(Class c, Serializable s) {
	Intent intent = new Intent(this, c);
	intent.putExtra(DATA, s);
	startActivity(intent);
}

@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	
}
}