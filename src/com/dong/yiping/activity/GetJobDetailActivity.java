package com.dong.yiping.activity;

import com.dong.yiping.R;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.TextView;

public class GetJobDetailActivity extends BaseActivity{

	@InjectView(R.id.tv_title_center) TextView tv_title_center;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getjobdetail);
		initView();
	}

	private void initView() {
		tv_title_center.setText("求职详情");
		
	}
}
