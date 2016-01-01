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

public class JobMessageActivity extends BaseActivity {

	private RelativeLayout bannerContent;
	private BannerBaseView banner;
	private GetJobBean getJobBean;

	private TextView tv_title_center;
	private ImageView iv_title_left;
	private ImageView iv_title_right;

	private TextView tv_jobmessage_jobname;
	private TextView tv_jobmessage_salary;
	private TextView tv_jobmessage_applyjob;
	private TextView tv_jobmessage_collect;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 当前页面从右往左进入
		overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

		setContentView(R.layout.activity_job_message);
		initView();
	}

	private void initView() {
		tv_title_center = $(R.id.tv_title_center);
		tv_title_center.setText("招聘信息");
		iv_title_left = $(R.id.iv_title_left, true);
		iv_title_right = $(R.id.iv_title_right, true);

		// 轮播图
		bannerContent = $(R.id.banner_cont);
		banner = new MainBannerView(this);
		bannerContent.addView(banner);

		//
		tv_jobmessage_jobname = $(R.id.tv_jobmessage_jobname);
		tv_jobmessage_salary = $(R.id.tv_jobmessage_salary);
		tv_jobmessage_applyjob = $(R.id.tv_jobmessage_applyjob, true);
		tv_jobmessage_collect = $(R.id.tv_jobmessage_collect, true);
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

		case R.id.tv_jobmessage_applyjob:// 申请职位

			break;

		case R.id.tv_jobmessage_collect:// 收藏职位

			break;
		}
	}
}
