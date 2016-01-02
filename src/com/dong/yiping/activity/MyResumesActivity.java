package com.dong.yiping.activity;

import com.dong.yiping.R;
import com.dong.yiping.view.LJListView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MyResumesActivity extends BaseActivity {

	private TextView tv_title_center;
	private ImageView iv_title_left;

	private TextView tv_myresumes_addresume;
	private LJListView lv_myresumes_listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 当前页面从右往左进入
		overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
		setContentView(R.layout.activity_myresumes);
		initTitleBar();
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}

	public void initTitleBar() {
		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);

		tv_title_center.setText("个人简历");

	}

	public void initView() {
		tv_myresumes_addresume = $(R.id.tv_myresumes_addresume, true);

		lv_myresumes_listview = $(R.id.lv_myresumes_listview);
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

		case R.id.tv_myresumes_addresume:
			// 添加简历
			
			startToActivity(ResumeActivity.class);
			break;

		}
	}
}
