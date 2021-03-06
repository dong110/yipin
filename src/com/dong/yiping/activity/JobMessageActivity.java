package com.dong.yiping.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.adapter.PopListViewAdapter;
import com.dong.yiping.adapter.ResumeListAdapter;
import com.dong.yiping.banner.BannerBaseView;
import com.dong.yiping.banner.MainBannerView;
import com.dong.yiping.banner.utils.GetBannerData;
import com.dong.yiping.bean.BannerListBean;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.GetZhaopinBean;
import com.dong.yiping.bean.GetJobBean.GetJob;
import com.dong.yiping.bean.GetZhaopinBean.ZhaoPin;
import com.dong.yiping.bean.JobDetailInfo;
import com.dong.yiping.utils.DisplayUtils;
import com.dong.yiping.utils.LoadingUtil;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.SPUtil;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;

import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class JobMessageActivity extends BaseActivity {

	private RelativeLayout bannerContent;
	private BannerBaseView banner;
	private GetJob getJob;

	private TextView tv_title_center;
	private ImageView iv_title_left;
	private ImageView iv_title_right;

	private TextView tv_jobmessage_jobname;
	private TextView tv_jobmessage_salary;
	private TextView tv_jobmessage_subdate;
	private TextView tv_jobmessage_workdate;
	private TextView tv_jobmessage_welfare;
	private TextView tv_jobmessage_content;
	private TextView tv_company;
	private TextView tv_jobmessage_applyjob;
	private TextView tv_jobmessage_collect;

	private BannerListBean bannerListBean;
	private List<ZhaoPin> resumeList;
	private Context mContext;
	private String jobId;
	private LoadingUtil loadingUtil;
	private ResumeListAdapter adapter;
	
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
				
				
				break;
				
			case Constant.APPLYJOB_FAIL:
				ToastUtil.showToast(mContext, "职位申请失败");
				break;
			case Constant.APPLYJOB_SUCCESS:
				ToastUtil.showToast(mContext, "职位申请成功");
				break;
			case Constant.HANDLER_COLLECTJOB:
				int status = (Integer) msg.obj;
				if(status==0){
					ToastUtil.showToast(mContext, "收藏职位成功");
				}
				if(status==1){
					ToastUtil.showToast(mContext, "收藏职位失败");
				}
				break;
			case Constant.HANDLER_RESUME_LIST:
				GetZhaopinBean jianli = (GetZhaopinBean) msg.obj;//简历列表和招聘列表一样
				loadingUtil.hideDialog();
				if(jianli!=null){
					resumeList = jianli.getList();
					
					adapter = new ResumeListAdapter(mContext, resumeList);
					createPop(mContext);
				}else{
					ToastUtil.showToast(mContext, "您的简历列表为空");
				}
				
				break;
				}
			loadingUtil.hideDialog();
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
		getIntentData();
		initView();
		//initData();
	}



	private void getIntentData() {
		bannerListBean = (BannerListBean) getIntent().getSerializableExtra("bannerListBean");
		getJob = (GetJob) getIntent().getSerializableExtra("getJob");
	}



	private void initView() {
		
		
		tv_title_center = $(R.id.tv_title_center);
		tv_title_center.setText("招聘信息");
		iv_title_left = $(R.id.iv_title_left, true);
		iv_title_right = $(R.id.iv_title_right, true);
		loadingUtil = new LoadingUtil(mContext);
		// 轮播图
		bannerContent = $(R.id.banner_cont);
		banner = new MainBannerView(this);
		bannerContent.addView(banner);
		if(bannerListBean!=null){
			banner.update(GetBannerData.getBannerData(bannerListBean),bannerListBean);
		}
		//
		tv_jobmessage_jobname = $(R.id.tv_jobmessage_jobname);
		tv_jobmessage_salary = $(R.id.tv_jobmessage_salary);
		tv_jobmessage_subdate = $(R.id.tv_jobmessage_subdate);
		tv_jobmessage_workdate = $(R.id.tv_jobmessage_workdate);
		tv_jobmessage_welfare = $(R.id.tv_jobmessage_welfare);
		tv_jobmessage_content = $(R.id.tv_jobmessage_content);
		tv_company = $(R.id.tv_company);
		
		tv_jobmessage_applyjob = $(R.id.tv_jobmessage_applyjob, true);
		tv_jobmessage_collect = $(R.id.tv_jobmessage_collect, true);
		if(getJob != null){
			tv_jobmessage_jobname.setText(getJob.getJob());
			tv_jobmessage_salary.setText(getJob.getWage());
			tv_jobmessage_subdate.setText(getJob.getSubdate());
			tv_jobmessage_welfare.setText(getJob.getWelfare());
			tv_jobmessage_workdate.setText(getJob.getWorkdate());
			tv_jobmessage_content.setText(getJob.getContent());
			tv_company.setText(getJob.getConpany());
		}
		
	}
	
	

	public void initData() {
		
		jobId = "5";
		// http://123.57.75.34:8080/users/api/recruitSimple?id=5
		String url = Constant.HOST + Constant.COMPANY_INFO +getJob.getId() ;
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, url,Constant.TOPER_TYPE_COMPANYINFO));
		//loadingUtil.showDialog();
	}
	@Override
	public void onClick(View v) {
		int type = SPUtil.getInt(mContext, "type", -1);
		switch (v.getId()) {
		case R.id.iv_title_left:
			this.finish();
			// 当前页面向右退出
			overridePendingTransition(R.anim.left_to_center,R.anim.center_to_right);
			break;
			
		case R.id.tv_jobmessage_applyjob:// 申请职位
			if(type==0){
				loadingUtil.showDialog();
				getResumeList();
			}
			if(type==1){
				ToastUtil.showToast(mContext, "非企业用户权限");
			}
			break;

		case R.id.tv_jobmessage_collect:// 收藏职位
			if(type==0){
				collectJob();
			}
			if(type==1){
				ToastUtil.showToast(mContext, "非企业用户权限");
			}
			
			break;
			
		}
	}
	//获取简历列表
	private void getResumeList(){
		String url = Constant.HOST + Constant.GET_Resume_List + SPUtil.getInt(mContext, "id", -1)+"&status=1";
		//String url = Constant.HOST + Constant.GET_Resume_List + SPUtil.getInt(mContext, "id", -1);
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, url, Constant.TOPER_TYPE_GET_RESUME_LIST));
	}
	
	public void createPop(Context mContext){
		
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
		View view = mLayoutInflater.inflate(R.layout.pop_dialog, null);
		//自适配长、框设置
		final PopupWindow dg = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		dg.setHeight(DisplayUtils.getScreenH(mContext)/2);
		ListView listView = (ListView) view.findViewById(R.id.pop_listview);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				applyJob(resumeList.get(position).getId());
				dg.dismiss();
			}
		});
		
		dg.setBackgroundDrawable(new BitmapDrawable());
		dg.setOutsideTouchable(true);
		dg.setAnimationStyle(android.R.style.Animation_Dialog);
		dg.update();
		dg.setTouchable(true);
		dg.setFocusable(true);
		dg.showAtLocation(view, Gravity.CENTER, 0,0);
		backgroundAlpha(0.5f);
		dg.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				backgroundAlpha(1f);
				
			}
		});
	}
	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 */
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
				.getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		((Activity) mContext).getWindow().setAttributes(lp);
	}
	private void collectJob() {
		///api/collectionUpdate?id=1&userid=1&type=0  type 0 简历收藏 1招聘抽藏
		String url = Constant.HOST + Constant.COLLECT;
		Map<String, String> paramMap = new HashMap<String, String>();
		if(getJob!=null){
			paramMap.put("id", getJob.getId()+"");//招聘ID
		}
		paramMap.put("type", "1");//0 简历 1面试
		paramMap.put("userid", SPUtil.getInt(mContext, "id", -1)+"");//用户id
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, url,paramMap,Constant.TOPER_TYPE_COLLECTJOB));
	}

	public void applyJob(int i) {
		//简历ID
		//TODO
		String resumeId="1";
		String url = Constant.HOST + Constant.APPLYJOB;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("resumeId", i+"");
		paramMap.put("type", "1");//0 简历 1面试
		if(getJob!=null){
			paramMap.put("recruitId", getJob.getId()+"");
		}
		
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, url,Constant.TOPER_TYPE_APPLYJOB));
	}
}
