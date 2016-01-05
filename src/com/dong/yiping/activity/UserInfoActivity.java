package com.dong.yiping.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dong.yiping.MyApplication;
import com.dong.yiping.R;
import com.dong.yiping.bean.UserBean;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.view.datepicker.TimeDialog;

public class UserInfoActivity extends BaseActivity {

	private TextView tv_title_center;
	private ImageView iv_title_left;

	private TextView tv_resume_birthday;
	private TextView tv_resume_xiugai;
	private TextView tv_resume_baocun;
	
	private EditText et_username;
	private EditText et_pinjia;
	private EditText et_intedsy;
	private EditText working;
	private EditText launager;
	private LinearLayout ll_upload_img;
	private Context mContext;
	private TimeDialog timeDialog;
	private Intent intent;
	private UserBean userBean;//简历和招聘对象一样的
	private boolean isChange=false;
	
	private TimeDialog.CustomTimeListener customTimeListener = new TimeDialog.CustomTimeListener() {
		@Override
		public void setTime(String time) {
			tv_resume_birthday.setText(time);
			timeDialog.dismiss();
		}
	};

	

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			
			}
		};
	};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 当前页面从右往左进入
		overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
		setContentView(R.layout.activity_userinfo);
		mContext = this;
		getIntentData();
		initView();
		setListener();
	}

	private void getIntentData() {
		userBean = MyApplication.getApplication().getUserBean();
		
	}

	private void initView() {
		timeDialog = new TimeDialog(this, customTimeListener);
		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);
		
		tv_resume_birthday = $(R.id.tv_resume_birthday, true);
		tv_resume_xiugai = $(R.id.tv_resume_xiugai, true);
		tv_resume_baocun = $(R.id.tv_resume_baocun, true);
		
		et_username = $(R.id.et_username);
		et_pinjia = $(R.id.et_pinjia);
		et_intedsy = $(R.id.et_intedsy);
		working = $(R.id.working);
		launager = $(R.id.launager);
		ll_upload_img = $(R.id.ll_upload_img,true);
		tv_title_center.setText("个人信息");
		if(userBean != null){
			et_username.setText(userBean.getObj().getUsername());
			tv_resume_birthday.setText(userBean.getObj().getUserInfo().getBirthday());
			et_pinjia.setText(userBean.getObj().getUserInfo().getContent());
		}
		
	}
	
	private void setListener() {
		if(isChange){
			et_username.setClickable(true);
			et_pinjia.setClickable(true);
			et_intedsy.setClickable(true);
			working.setClickable(true);
			launager.setClickable(true);
			tv_resume_birthday.setClickable(true);
		}else{
			et_username.setClickable(false);
			et_pinjia.setClickable(false);
			et_intedsy.setClickable(false);
			working.setClickable(false);
			launager.setClickable(false);
			tv_resume_birthday.setClickable(false);
		}
		
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			this.finish();
			// 当前页面向右退出
			overridePendingTransition(R.anim.left_to_center,
					R.anim.center_to_right);
			break;
		case R.id.tv_resume_birthday:
			timeDialog.show();

			break;
			
		case R.id.tv_resume_baocun://保存信息
			ToastUtil.showToast(mContext, "保存信息");
			break;
		case R.id.tv_resume_xiugai://修改信息
			ToastUtil.showToast(mContext, "修改信息");
			break;
		}
	}
}