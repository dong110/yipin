package com.dong.yiping.activity;

import com.dong.yiping.R;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class CompanyInfoActivity extends BaseActivity {

	private TextView tv_title_center;
	private ImageView iv_title_left;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 当前页面从右往左进入
		overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

		setContentView(R.layout.activity_companyinfo);
		initTitleBar();
	}

	private void initTitleBar() {
		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);

		tv_title_center.setText("企业信息");

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

		}
	}
}
