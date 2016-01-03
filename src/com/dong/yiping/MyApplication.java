package com.dong.yiping;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.dong.yiping.bean.BannerListBean;
import com.dong.yiping.bean.DictListBean;
import com.dong.yiping.bean.DictListBean.DictBean;

/**
 * Created by huan on 2015/10/27.
 */
public class MyApplication extends Application {
	public static MyApplication myApplication;
	private static final String TAG = MyApplication.class.getName();
	public  DictListBean dictBean;
	public BannerListBean bannerListBean;
	
	/**
	 * 全局管理activity，方便退出
	 */
	private static List<Activity> activityList = new ArrayList<Activity>();

	public static MyApplication getApplication() {
		return myApplication;
	}
	
	public void setbannerListBean(BannerListBean bannerListBean){
		this.bannerListBean = bannerListBean;
	}
	public BannerListBean getBannerListBean( ){
		return bannerListBean;
	}
	public  void setDictBean(DictListBean dictBean){
		this.dictBean = dictBean;
	}
	
	public DictListBean getDictBean(){
		return dictBean;
	}
	
	@Override
	public void onCreate() {
		myApplication = this;
		super.onCreate();

		// 捕获没有捕获的异常
		// Thread.setDefaultUncaughtExceptionHandler(new
		// MyUncaughtExceptionHandler());

	}

	// 捕获处理器
	private class MyUncaughtExceptionHandler implements
			Thread.UncaughtExceptionHandler {

		@Override
		public void uncaughtException(Thread thread, Throwable ex) {

			try {
				// 把错误日志保存到sdcard
				File file = new File(Environment.getExternalStorageDirectory(),
						"carch.txt");
				PrintWriter err = new PrintWriter(file);
				ex.printStackTrace(err);
				err.flush();
				err.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			// 早死早超生
			android.os.Process.killProcess(android.os.Process.myPid());

		}
	}

	// 资源耗尽的时候执行
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	// 添加activity的引用
	public static void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 退出应用的时候 清空所有的activity
	public static void clearActivity() {
		for (Activity activity : activityList) {
			if (activity != null) {
				activity.finish();
			}
		}
		activityList.clear();
		// System.exit(0);
	}

	// 及时释放activity的引用
	public static void removeActivity(Activity activity) {
		if (activity != null) {
			activityList.remove(activity);
			activity = null;
		}
	}

}
