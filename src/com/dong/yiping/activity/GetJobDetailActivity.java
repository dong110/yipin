package com.dong.yiping.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.bean.GetJobBean.GetJob;
import com.dong.yiping.bean.GetZhaopinBean.ZhaoPin;
import com.dong.yiping.bean.JobDetailBean;
import com.dong.yiping.bean.UserBean;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.SPUtil;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;

public class GetJobDetailActivity extends BaseActivity{


	private TextView tv_title_center;
	private ImageView iv_title_left;
	private ImageView iv_title_right;

	private ImageView iv_resume_icon;
	private TextView tv_resume_invite;
	private TextView tv_resume_collect;
	private TextView tv_birthday;
	private TextView user_name;
	private TextView tv_intention;
	private TextView working;
	private TextView subdate;
	private TextView lange;
	private TextView able;
	private int type;//用户类型
	private Context mContext;
	private JobDetailBean jobbean;
	private Handler mHandler = new Handler(){
		

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.HANDLER_TYPE_GETJOB_DETAIL:
				jobbean = (JobDetailBean) msg.obj;
				//获取到工作信息后获取个人信息
				String user_info = Constant.HOST + Constant.USER_INFO;
				Map<String, String> mapUser = new HashMap<String, String>();
				mapUser.put("id", ""+jobbean.getUserid());
				ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, user_info, mapUser, Constant.TOPER_TYPE_GET_USERINFO));
				
				break;
			case Constant.HANDLER_TYPE_GET_USERINFO:
				UserBean bean = (UserBean) msg.obj;
				changeUserInfo(bean);
				changeJobInfo(jobbean);
				break;
			case Constant.HANDLER_COLLECTJOB:
				//收藏简历和收藏工作数据类型一样
				int status = (Integer) msg.obj;
				if(status==0){
					ToastUtil.showToast(mContext, "收藏简历成功");
				}
				if(status==1){
					ToastUtil.showToast(mContext, "收藏简历失败");
				}
				break;
			case Constant.APPLYJOB_FAIL:
				ToastUtil.showToast(mContext, "邀请面试失败");
				break;
			case Constant.APPLYJOB_SUCCESS:
				ToastUtil.showToast(mContext, "要前面试成功");
				break;
			}
		}

	};
	private ZhaoPin zhaoPin;
	private GetJob getJob;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 当前页面从右往左进入
		overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
		setContentView(R.layout.activity_getjobdetail);
		mContext = this;
		getIntentData();
		initView();
		initData();
	}

	private void getIntentData() {
		zhaoPin = (ZhaoPin) getIntent().getSerializableExtra("ZhaoPin");
		
	}

	private void initData() {
		//http://123.57.75.34:8080/users/api/userInfo?id=8
		//http://123.57.75.34:8080/users/api/resumeSimple?id=8
		
		int zhaoPinId = -1;
		if(zhaoPin!=null){
			zhaoPinId = zhaoPin.getId();
		}
		String get_job = Constant.HOST + Constant.GET_JOB_DETAIL+zhaoPinId;
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, get_job, Constant.TOPER_TYPE_GETJOB_DETAIL));
		
	}

	private void initView() {
		type = SPUtil.getInt(mContext, "type", -1);
		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);
		iv_title_right = $(R.id.iv_title_right, true);
		tv_title_center.setText("求职详情");
		tv_birthday= $(R.id.tv_birthday, true);
		iv_resume_icon = $(R.id.iv_resume_icon);
		user_name = $(R.id.user_name);
		tv_intention = $(R.id.tv_intention);
		working = $(R.id.working);
		subdate = $(R.id.subdate);
		lange = $(R.id.lange);
		able = $(R.id.able);
		tv_resume_invite = $(R.id.tv_resume_invite, true);
		tv_resume_collect = $(R.id.tv_resume_collect, true);
	}

	/**
	 * 修改用户信息
	 * @param bean
	 */
	private void changeUserInfo(UserBean bean) {
		if(bean!=null){
			user_name.setText(bean.getObj().getUserInfo().getName());
			tv_birthday.setText(bean.getObj().getUserInfo().getBirthday());
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
			lange.setText("语言水平："+jobbean.getLange());
			able.setText("工作能力："+jobbean.getAnble());
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
					ToastUtil.showToast(mContext, "非个人用户权限");
				}
				if(type==1){
					//企业用户
					InvateMeet();
					
				}
			}else{
				ToastUtil.showToast(mContext, "参数错误");
			}
			break;

		case R.id.tv_resume_collect:// 收藏简历
			if(type!=-1){
				if(type ==0){
					//个人用户
					ToastUtil.showToast(mContext, "非个人用户权限");
				}
				if(type==1){
					//企业用户
					collectResume();
					
				}
			}else{
				ToastUtil.showToast(mContext, "参数错误");
			}
			break;
		}
	}
	/**
	 * 收藏简历
	 */
	private void collectResume() {
		String url = Constant.HOST + Constant.COLLECT;
		Map<String, String> paramMap = new HashMap<String, String>();
		if(zhaoPin!=null){
			paramMap.put("id", zhaoPin.getId()+"");//招聘ID
		}
		paramMap.put("type", "0");//0 投简历 1邀面试
		paramMap.put("userid", jobbean.getUserid()+"");//用户id
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, url,paramMap,Constant.TOPER_TYPE_COLLECTJOB));
	}

	/**
	 * 邀请面试
	 */
	private void InvateMeet() {
		//String url = Constant.HOST + "/noticeUpdate?resumeId="++"&type=1&recruitId=1";
		String resumeId="1";
		String url = Constant.HOST + Constant.APPLYJOB;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("resumeId", zhaoPin.getId()+"");//简历Id
		paramMap.put("type", "0");//0 简历 1面试
		if(zhaoPin!=null){
			paramMap.put("recruitId", zhaoPin.getId()+"");
		}
		
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, url,Constant.TOPER_TYPE_APPLYJOB));
	}
}
