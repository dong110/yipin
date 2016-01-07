package com.dong.yiping.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.dong.yiping.Constant;
import com.dong.yiping.MyApplication;
import com.dong.yiping.R;
import com.dong.yiping.bean.StarStudentBean.Student;
import com.dong.yiping.bean.UserBean.Obj.Pic;
import com.dong.yiping.bean.UserBean;
import com.dong.yiping.utils.LoadingUtil;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.SPUtil;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.view.datepicker.TimeDialog;
import com.squareup.picasso.Picasso;

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
	private EditText et_pinjia_two;
	private ImageView iv_icon;
	private LinearLayout ll_buts;
	private LinearLayout ll_upload_img;
	private Context mContext;
	private TimeDialog timeDialog;
	private Intent intent;
	private UserBean userBean;//简历和招聘对象一样的
	private boolean isChange=true;
	private LoadingUtil loadingUtil;
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
			case Constant.HANDLER_TYPE_SAVE_USERINFO:
				int status = (Integer) msg.obj;
				if(status==0){
					ToastUtil.showToast(mContext, "保存用户信息成功");
				}
				if(status==1){
					ToastUtil.showToast(mContext, "保存用户信息失败");
				}
				break;
			case Constant.HANDLER_TYPE_GET_USERINFO:
				userBean = (UserBean) msg.obj;
				setView();
				break;
			}
			
			loadingUtil.hideDialog();
		};
	};
	private String type;
	private Student student;
	
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
		initData();
		initView();
		setListener();
	}

	private void getIntentData() {
		type = getIntent().getStringExtra("type");
		student = (Student) getIntent().getSerializableExtra("student");
		
		userBean = MyApplication.getApplication().getUserBean();
		
	}
	
	private void initData(){
		if(!"oneFragment".equals(type)){
			String user_info = Constant.HOST + Constant.USER_INFO;
			Map<String, String> mapUser = new HashMap<String, String>();
			mapUser.put("id", ""+SPUtil.getInt(mContext, "id", -1));
			ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, user_info, mapUser, Constant.TOPER_TYPE_GET_USERINFO));
		}
		
	}
	
	private void initView() {
		loadingUtil = new LoadingUtil(mContext);
		timeDialog = new TimeDialog(this, customTimeListener);
		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);
		
		tv_resume_birthday = $(R.id.tv_resume_birthday, true);
		tv_resume_baocun = $(R.id.tv_resume_baocun, true);
		ll_buts = $(R.id.ll_buts);
		et_username = $(R.id.et_username);
		et_pinjia = $(R.id.et_pinjia);
		et_intedsy = $(R.id.et_intedsy);
		working = $(R.id.working);
		launager = $(R.id.launager);
		ll_upload_img = $(R.id.ll_upload_img,true);
		tv_title_center.setText("个人信息");
		
		
	}
	private void setView(){
		if(userBean != null){
			et_username.setText(userBean.getObj().getUserInfo().getName());
			tv_resume_birthday.setText(userBean.getObj().getUserInfo().getBirthday());
			et_pinjia.setText(userBean.getObj().getUserInfo().getContent());
		}
		if("oneFragment".equals(type)){
			ll_buts.setVisibility(View.GONE);
			et_pinjia.setVisibility(View.GONE);
			et_pinjia_two.setVisibility(View.VISIBLE);
		}
		if(student!=null){
			et_username.setText(student.getName());
			tv_resume_birthday.setText(student.getBirthday());
			et_pinjia_two.setText(student.getContent());
		}
	}
	private void setListener() {
		if(isChange){
			et_username.setFocusable(true);
			et_username.setFocusableInTouchMode(true);
			
			et_pinjia.setFocusable(true);
			et_pinjia.setFocusableInTouchMode(true);
			
			et_intedsy.setFocusable(true);
			et_intedsy.setFocusableInTouchMode(true);
			
			working.setFocusable(true);
			working.setFocusableInTouchMode(true);
			
			launager.setFocusable(true);
			launager.setFocusableInTouchMode(true);
			tv_resume_birthday.setClickable(true);
		}else{
			et_username.setFocusable(false);
			et_username.setFocusableInTouchMode(false);
			
			et_pinjia.setFocusable(false);
			et_pinjia.setFocusableInTouchMode(false);
			
			et_intedsy.setFocusable(false);
			et_intedsy.setFocusableInTouchMode(false);
			
			working.setFocusable(false);
			working.setFocusableInTouchMode(false);
			
			launager.setFocusable(false);
			launager.setFocusableInTouchMode(false);
			
			et_pinjia_two.setFocusable(false);
			et_pinjia_two.setFocusableInTouchMode(false);
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
			loadingUtil.showDialog();
			saveUserInfo();
			break;
		}
	}
	/**
	 * 修改个人信息
	 */
	private void saveUserInfo() {
		String url = Constant.HOST + Constant.UPDATE_UNSER_INFO;
		String username = et_username.getText().toString().trim();
		String birthday = tv_resume_birthday.getText().toString().trim();
		String content = et_pinjia.getText().toString().trim();
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("id", SPUtil.getInt(mContext, "id", -1)+"");
		paramsMap.put("name", username);
		paramsMap.put("birthday", birthday);
		paramsMap.put("content", content);
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,paramsMap,Constant.TOPER_TYPE_SAVE_USERINFO));
		
		
	}
}