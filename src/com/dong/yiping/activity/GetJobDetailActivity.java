package com.dong.yiping.activity;

import com.dong.yiping.R;
import com.dong.yiping.banner.BannerBaseView;
import com.dong.yiping.banner.MainBannerView;
import com.dong.yiping.bean.GetJobBean;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GetJobDetailActivity extends BaseActivity{


	private TextView tv_title_center;
	private ImageView iv_title_left;
	private ImageView iv_title_right;

	private ImageView iv_resume_icon;
	private TextView tv_resume_invite;
	private TextView tv_resume_collect;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 当前页面从右往左进入
		overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

		setContentView(R.layout.activity_getjobdetail);
		initView();
	}

	private void initView() {
		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);
		iv_title_right = $(R.id.iv_title_right, true);
		tv_title_center.setText("求职详情");

		//
		iv_resume_icon = $(R.id.iv_resume_icon);
		
		
		tv_resume_invite = $(R.id.tv_resume_invite, true);
		tv_resume_collect = $(R.id.tv_resume_collect, true);
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

		case R.id.iv_title_right:
			// 设置
			// TODO
			break;

		case R.id.tv_resume_invite:// 邀请面试

			break;

		case R.id.tv_resume_collect:// 收藏简历

			break;
		}
	}
}
