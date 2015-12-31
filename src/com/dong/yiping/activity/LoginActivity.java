package com.dong.yiping.activity;

import java.util.HashMap;
import java.util.Map;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.bean.UserBean;
import com.dong.yiping.utils.LoadingUtil;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;
import com.google.inject.Inject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

public class LoginActivity extends BaseActivity implements OnClickListener{

	@InjectView(R.id.tv_login_regist) TextView tv_login_regist;
	@InjectView(R.id.tv_login_login) TextView tv_login_login;
	@InjectView(R.id.et_login_username) EditText et_login_username;
	@InjectView(R.id.et_login_pwd) EditText et_login_pwd;
	
	@Inject Context mContext;
	@InjectExtra(value="url",optional=true) String url; 
	private LoadingUtil util;
	private Intent mIntent;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.NET_ERROR:
				util.hideDialog();
				ToastUtil.showToast(mContext, "登录失败！");
				break;
			case Constant.NET_SUCCESS:
				UserBean bean = (UserBean) msg.obj;
				util.hideDialog();
				ToastUtil.showToast(mContext, "登录成功！");
				mIntent = new Intent(mContext,MainActivity.class);
				startActivity(mIntent);
				finish();
				break;
			}
			
		};
	};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		
	}

	private void initView() {
		tv_login_regist.setOnClickListener(this);
		tv_login_login.setOnClickListener(this);
		util = new LoadingUtil(mContext);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_login_regist:
			mIntent = new Intent(mContext,RegistActivity.class);
			startActivity(mIntent);
			finish();
			break;

		case R.id.tv_login_login:
			/*mIntent = new Intent(mContext,MainActivity.class);
			startActivity(mIntent);
			finish();*/
			loginMethod();
			break;
		}
		
	}

	private void loginMethod() {
		util.showDialog();
		String username = et_login_username.getText().toString().trim();
		String password = et_login_pwd.getText().toString().trim();
		if(username == null || TextUtils.isEmpty(username)){
			ToastUtil.showToast(mContext, "用户名不能为空！");
			return;
		}
		if(password == null || TextUtils.isEmpty(password)){
			ToastUtil.showToast(mContext, "密码不能为空！");
			return;
		}
		String url = Constant.HOST+Constant.LOGIN;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("pwd", password);
		paramMap.put("username", username);
		
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,paramMap));
		
	}
}
