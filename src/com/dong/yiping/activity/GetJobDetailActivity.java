package com.dong.yiping.activity;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Text;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.banner.BannerBaseView;
import com.dong.yiping.banner.MainBannerView;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.JobDetailBean;
import com.dong.yiping.bean.UserBean;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.SPUtil;
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

public class GetJobDetailActivity extends BaseActivity{


	private TextView tv_title_center;
	private ImageView iv_title_left;
	private ImageView iv_title_right;

	private ImageView iv_resume_icon;
	private TextView tv_resume_invite;
	private TextView tv_resume_collect;
	private TextView user_name;
	private TextView tv_intention;
	private TextView working;
	private TextView subdate;
	private int type;//用户类型
	private Context mContext;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.HANDLER_TYPE_GETJOB_DETAIL:
				JobDetailBean Jobbean = (JobDetailBean) msg.obj;
				changeJobInfo(Jobbean);
				break;
			case Constant.HANDLER_TYPE_GET_USERINFO:
				UserBean bean = (UserBean) msg.obj;
				changeUserInfo(bean);
				
				break;
			}
		}

	};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 当前页面从右往左进入
		overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
		setContentView(R.layout.activity_getjobdetail);
		mContext = this;
		initView();
		initData();
	}

	private void initData() {
		//http://123.57.75.34:8080/users/api/userInfo?id=8
		//http://123.57.75.34:8080/users/api/resumeSimple?id=8
		String user_info = Constant.HOST + Constant.USER_INFO;
		Map<String, String> mapUser = new HashMap<String, String>();
		mapUser.put("id", ""+SPUtil.getInt(mContext, "id", 1));
		String get_job = Constant.HOST + Constant.GET_JOB_DETAIL+"8";
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, get_job, Constant.TOPER_TYPE_GETJOB_DETAIL));
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, user_info, mapUser, Constant.TOPER_TYPE_GET_USERINFO));
	}

	private void initView() {
		type = SPUtil.getInt(mContext, "type", -1);
		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);
		iv_title_right = $(R.id.iv_title_right, true);
		tv_title_center.setText("求职详情");
		
		iv_resume_icon = $(R.id.iv_resume_icon);
		user_name = $(R.id.user_name);
		tv_intention = $(R.id.tv_intention);
		working = $(R.id.working);
		subdate = $(R.id.subdate);
		
		tv_resume_invite = $(R.id.tv_resume_invite, true);
		tv_resume_collect = $(R.id.tv_resume_collect, true);
	}

	/**
	 * 修改用户信息
	 * @param bean
	 */
	private void changeUserInfo(UserBean bean) {
		if(bean!=null){
			user_name.setText(bean.getObj().getUsername());
		}
		
		
	}
	/**
	 * 修改工作信息
	 * @param jobbean
	 */
	private void changeJobInfo(JobDetailBean jobbean) {
		if(jobbean!=null){
			tv_intention.setText("求职意向："+jobbean.getIntention());
			working.setText("工作经历："+jobbean.getWorking());
			subdate.setText("修改时间："+jobbean.getSubdate());
		}
		
	};
	
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
			if(type!=-1){
				if(type ==0){
					//个人用户
					ToastUtil.showToast(mContext, "个人用户没有权限");
				}
				if(type==1){
					//企业用户
					
					
				}
			}else{
				ToastUtil.showToast(mContext, "参数错误");
			}
			break;

		case R.id.tv_resume_collect:// 收藏简历

			break;
		}
	}
}
