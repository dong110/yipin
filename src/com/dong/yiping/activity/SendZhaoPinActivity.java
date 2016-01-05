package com.dong.yiping.activity;

import roboguice.inject.InjectView;

import com.dong.yiping.R;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SendZhaoPinActivity extends BaseActivity{
	
	@InjectView(R.id.tv_title_center) TextView tv_title_center;
	@InjectView(R.id.iv_title_left) ImageView iv_title_left;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendzhaopin);
		initView();
		setListener();
	}

	private void setListener() {
		iv_title_left.setOnClickListener(this);
		
	}

	private void initView() {
		tv_title_center.setText("发布招聘");
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			onBackPressed();
			break;

		default:
			break;
		}
	}
}
