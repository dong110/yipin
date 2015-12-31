package com.dong.yiping.activity;

import com.dong.yiping.R;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class RegistActivity extends BaseActivity {



	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 当前页面从右往左进入
		overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
		mContext = this;
		setContentView(R.layout.activity_regist);

		initTitleBar();
		initView();
	}

	private void initView() {
		
	}

	private void initTitleBar() {

	}
}
