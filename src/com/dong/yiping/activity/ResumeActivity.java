package com.dong.yiping.activity;

import com.dong.yiping.R;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.view.datepicker.TimeDialog;

import android.content.Context;
import android.os.Bundle;
import android.test.TouchUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ResumeActivity extends BaseActivity {

	private TextView tv_title_center;
	private ImageView iv_title_left;

	private TextView tv_resume_birthday;

	private TextView tv_resume_publish;
	private Context mContext;
	
	private TimeDialog timeDialog;

	private TimeDialog.CustomTimeListener customTimeListener = new TimeDialog.CustomTimeListener() {
		@Override
		public void setTime(String time) {
			tv_resume_birthday.setText(time);
			timeDialog.dismiss();
		}
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
		initView();
	}

	private void initView() {
		timeDialog = new TimeDialog(this, customTimeListener);

		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);

		tv_title_center.setText("个人简历");

		tv_resume_birthday = $(R.id.tv_resume_birthday, true);
		tv_resume_publish = $(R.id.tv_resume_publish, true);
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
