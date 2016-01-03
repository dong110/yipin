package com.dong.yiping.fragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.activity.CompanyInfoActivity;
import com.dong.yiping.activity.GetJobDetailActivity;
import com.dong.yiping.activity.JobMessageActivity;
import com.dong.yiping.adapter.OneFragmentAdapter;
import com.dong.yiping.banner.BannerBaseView;
import com.dong.yiping.banner.MainBannerView;
import com.dong.yiping.banner.bean.BaseBannerBean;
import com.dong.yiping.banner.utils.GetBannerData;
import com.dong.yiping.bean.ADBean;
import com.dong.yiping.bean.BannerListBean;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.GetZhaopinBean;
import com.dong.yiping.bean.StarCompanyBean;
import com.dong.yiping.bean.StarStudentBean;
import com.dong.yiping.utils.LoadingUtil;
import com.dong.yiping.utils.LogUtil;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.ThreadPoolManager;

public class OneFragment extends RoboFragment{
	
	private static String TAG = "OneFragment";
	
	@InjectView(R.id.lv_listview) ListView lv_listview;
	
	private OneFragmentAdapter adapter;
	private RelativeLayout bannerContent;
	private BannerBaseView banner;
	private GetJobBean getJobBean;
	private GetZhaopinBean getZhaopin;
	private StarCompanyBean starCompanyBean;
	private StarStudentBean starStudentBean;
	private BannerListBean bannerListBean;
	private View view_hand;
	private TextView tv_title_center;
	private LinearLayout ll_title_center;
	private LoadingUtil loadingUtil;
	private Context mContext;
	private int netNum =0;
	private Intent mIntent;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.HANDLER_BANNERLIST:
				bannerListBean = (BannerListBean) msg.obj;
				if(bannerListBean == null){
					bannerListBean = new BannerListBean();
				}
				LogUtil.i(TAG, bannerListBean.getList().size()+"轮播图");
				banner.update(GetBannerData.getBannerData(bannerListBean),bannerListBean);
				setAdapter(++netNum);
				break;
			case Constant.HANDLER_TYPE_GETJOB:
				getJobBean = (GetJobBean) msg.obj;
				if(getJobBean == null){
					getJobBean = new GetJobBean();
					getJobBean.setList(new ArrayList<GetJobBean.GetJob>());
				}
				LogUtil.i(TAG, getJobBean.getList().size()+"工作");
				setAdapter(++netNum);
				break;
			case Constant.HANDLER_TYPE_GETZHAOPIN:
				getZhaopin = (GetZhaopinBean) msg.obj;
				if(getZhaopin == null){
					getZhaopin = new GetZhaopinBean();
					getZhaopin.setList(new ArrayList<GetZhaopinBean.ZhaoPin>());
				}
				LogUtil.i(TAG, getZhaopin.getList().size()+"招聘");
				setAdapter(++netNum);
				break;
			case Constant.HANDLER_TYPE_STARCOM:
				starCompanyBean = (StarCompanyBean) msg.obj;
				if(starCompanyBean == null){
					starCompanyBean = new StarCompanyBean();
					starCompanyBean.setList(new ArrayList<StarCompanyBean.StarCom>());
				}
				//LogUtil.i(TAG, starCompanyBean.getList().size()+"明星企业");
				setAdapter(++netNum);
				break;
			case Constant.HANDLER_TYPE_STARTSTU:
				starStudentBean = (StarStudentBean) msg.obj;
				if(starStudentBean == null){
					starStudentBean = new StarStudentBean();
					starStudentBean.setList(new ArrayList<StarStudentBean.Student>());
				}
				//LogUtil.i(TAG, starStudentBean.getList().size()+"明星学生");
				setAdapter(++netNum);
				break;
			}
		};
	};

	
	
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_one, null);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		initView();
		initData();
		init();
	}

	private void initView() {
		loadingUtil = new LoadingUtil(mContext);
	}


	private void initData() {
		loadingUtil.showDialog();
		String bannerList = Constant.HOST+Constant.BANNERLIST;
		String getJob = Constant.HOST+Constant.GET_JOB;
		String getZhaopin = Constant.HOST+Constant.GET_ZHAOPING;
		String starCom = Constant.HOST+Constant.STAR_COMPANY;
		String starStu = Constant.HOST+Constant.STAR_STUDENT;
		
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,bannerList,Constant.TOPER_TYPE_BANNERLIST));
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,getJob,Constant.TOPER_TYPE_GETJOB));
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,getZhaopin,Constant.TOPER_TYPE_GETZHAOPIN));
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,starCom,Constant.TOPER_TYPE_STARCOM));
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,starStu,Constant.TOPER_TYPE_STARTSTU));
		
		
		
	}
	
	private synchronized  void setAdapter(int netNum){
		LogUtil.i(TAG, netNum+"netNum");
		if(netNum == 5){
			loadingUtil.hideDialog();
			adapter = new OneFragmentAdapter(mContext,getJobBean,getZhaopin,starCompanyBean,starStudentBean);
			lv_listview.setAdapter(adapter);
		}
		
	}
	
	private void init() {
		view_hand = LayoutInflater.from(mContext).inflate(R.layout.ad_layout, null);
		bannerContent = (RelativeLayout) view_hand.findViewById(R.id.banner_cont);
		banner = new MainBannerView(getActivity());
		bannerContent.addView(banner);
		lv_listview.addHeaderView(view_hand);
		
		lv_listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(position>0){
					position--;
				}
				if(adapter.isGetJob){
					if(position == 0){
					}else if(position > 0 && position<getJobBean.getList().size()+1){
						mIntent = new Intent(mContext,JobMessageActivity.class);
						mIntent.putExtra("bannerListBean", bannerListBean);
						mIntent.putExtra("getJob",getJobBean.getList().get(position-1));
						
						startActivity(mIntent);
					}else if(position==getJobBean.getList().size()+1){
					}else if(position==getJobBean.getList().size()+2){
					}else if(position>getJobBean.getList().size()+2 && position<getJobBean.getList().size()+starCompanyBean.getList().size()+2){
						
						/*mIntent = new Intent(mContext,CompanyInfoActivity.class);
						startActivity(mIntent);*/
						
					}else if(position==getJobBean.getList().size()+starCompanyBean.getList().size()+2){
					}else if(position>getJobBean.getList().size()+starCompanyBean.getList().size()+2 && position<getJobBean.getList().size()+starCompanyBean.getList().size()+starStudentBean.getList().size()+3){
						/*mIntent = new Intent(mContext,GetJobDetailActivity.class);
						startActivity(mIntent);*/
						
					}
				}else{
					if(position == 0){
					}else if(position > 0 && position<getZhaopin.getList().size()+1){
						mIntent = new Intent(mContext,GetJobDetailActivity.class);
						startActivity(mIntent);
						
					}else if(position==getZhaopin.getList().size()+1){
					}else if(position==getZhaopin.getList().size()+2){
					}else if(position>getZhaopin.getList().size()+2 && position<getZhaopin.getList().size()+starCompanyBean.getList().size()+2){
						/*mIntent = new Intent(mContext,CompanyInfoActivity.class);
						startActivity(mIntent);*/
					}else if(position==getZhaopin.getList().size()+starCompanyBean.getList().size()+2){
					}else if(position>getZhaopin.getList().size()+starCompanyBean.getList().size()+2 && position<getZhaopin.getList().size()+starCompanyBean.getList().size()+starStudentBean.getList().size()+3){
						/*mIntent = new Intent(mContext,GetJobDetailActivity.class);
						startActivity(mIntent);*/
					}
					
				}
			}
		});
	}
	
	
}
