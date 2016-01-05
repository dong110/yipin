package com.dong.yiping.activity;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.bean.JobDetailInfo;
import com.dong.yiping.bean.GetZhaopinBean.ZhaoPin;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.view.datepicker.TimeDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.test.TouchUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResumeActivity extends BaseActivity {

	private TextView tv_title_center;
	private ImageView iv_title_left;

	private TextView tv_resume_birthday;
	private TextView tv_resume_publish;
	
	private EditText et_username;
	private EditText et_pinjia;
	private EditText et_intedsy;
	private EditText working;
	private EditText launager;
	private LinearLayout ll_upload_img;
	
	private Context mContext;
	
	private TimeDialog timeDialog;
	private Intent intent;
	private String resumeId;
	private ZhaoPin resumen;//简历和招聘对象一样的
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
			case Constant.NET_ERROR:
				ToastUtil.showToast(mContext, "获取数据失败！");
				break;
			case Constant.NET_NULL:
				ToastUtil.showToast(mContext, "职位详情数据为空！");
				break;
			case Constant.NET_SUCCESS:
				JobDetailInfo bean = (JobDetailInfo) msg.obj;

				//填充页面数据
				break;
				
			case Constant.APPLYJOB_FAIL:
				ToastUtil.showToast(mContext, "职位申请失败");
				break;
			case Constant.APPLYJOB_SUCCESS:
				ToastUtil.showToast(mContext, "职位申请成功");
				break;
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
		setContentView(R.layout.activity_resume);
		mContext = this;
		getIntentData();
		initView();
		initData();
	}



	private void getIntentData() {
		resumen = (ZhaoPin) getIntent().getSerializableExtra("resumeBean");
		
		
		
	}



	private void initView() {
		timeDialog = new TimeDialog(this, customTimeListener);
		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);
		
		tv_resume_birthday = $(R.id.tv_resume_birthday, true);
		tv_resume_publish = $(R.id.tv_resume_publish, true);
		
		et_username = $(R.id.et_username);
		et_pinjia = $(R.id.et_pinjia);
		et_intedsy = $(R.id.et_intedsy);
		working = $(R.id.working);
		launager = $(R.id.launager);
		ll_upload_img = $(R.id.ll_upload_img,true);
		
		if(resumen != null){
			et_username.setText(resumen.getName());
			et_intedsy.setText(resumen.getIntention());
			launager.setText(resumen.getLange());
			working.setText(resumen.getWorking());
			tv_title_center.setText("修改简历");
		}else{
			tv_title_center.setText("添加简历");
		}
		
	}
	
	private void setView(){
		
	}
	
	private void initData() {
		intent = getIntent();
		resumeId = intent.getStringExtra("ID");
		if(resumeId!=null){

			String url = Constant.HOST + Constant.COMPANY_INFO + resumeId;
			ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, url,Constant.TOPER_TYPE_COMPANYINFO));

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
			
		case R.id.tv_resume_publish://发布简历
			
			ToastUtil.showToast(mContext, "发布简历");
			break;

		}
	}
}
