package com.dong.yiping.activity;

import java.util.HashMap;
import java.util.Map;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.banner.BannerBaseView;
import com.dong.yiping.banner.MainBannerView;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.JobDetailInfo;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;

import roboguice.inject.InjectView;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
	private TextView tv_jobmessage_subdate;
	private TextView tv_jobmessage_workdate;
	private TextView tv_jobmessage_welfare;
	private TextView tv_jobmessage_content;
	
	private TextView tv_jobmessage_applyjob;
	private TextView tv_jobmessage_collect;

	private Context mContext;
	private String jobId;

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
				tv_jobmessage_jobname.setText(bean.job);
				tv_jobmessage_salary.setText(bean.wage);
				tv_jobmessage_subdate.setText(bean.subdate);
				tv_jobmessage_welfare.setText(bean.welfare);
				tv_jobmessage_workdate.setText(bean.workdate);
				tv_jobmessage_content.setText(bean.content);
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
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 当前页面从右往左进入
		overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

		setContentView(R.layout.activity_job_message);
		mContext = this;
		initView();
		initData();
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
		tv_jobmessage_subdate = $(R.id.tv_jobmessage_subdate);
		tv_jobmessage_workdate = $(R.id.tv_jobmessage_workdate);
		tv_jobmessage_welfare = $(R.id.tv_jobmessage_welfare);
		tv_jobmessage_content = $(R.id.tv_jobmessage_content);
		
		tv_jobmessage_applyjob = $(R.id.tv_jobmessage_applyjob, true);
		tv_jobmessage_collect = $(R.id.tv_jobmessage_collect, true);
	}
	
	

	public void initData() {

		jobId = "5";
		// http://123.57.75.34:8080/users/api/recruitSimple?id=5

		String url = Constant.HOST + Constant.COMPANY_INFO + jobId;

		ThreadPoolManager.getInstance()
				.addTask(
						new NetRunnable(mHandler, url,
								Constant.TOPER_TYPE_COMPANYINFO));

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


		case R.id.tv_jobmessage_applyjob:// 申请职位

			applyJob();
			break;

		case R.id.tv_jobmessage_collect:// 收藏职位

			collectJob();
			break;
		}
	}



	private void collectJob() {
		// TODO Auto-generated method stub
		
	}



	public void applyJob() {

		//简历ID
		//TODO
		String resumeId="1";
		String url = Constant.HOST + Constant.APPLYJOB;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("resumeId", resumeId);
		paramMap.put("type", "0");//0 投简历 1邀面试
		paramMap.put("recruitId", jobId);
		ThreadPoolManager.getInstance()
				.addTask(
						new NetRunnable(mHandler, url,
								Constant.TOPER_TYPE_APPLYJOB));

	}
}
