package com.dong.yiping.activity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.dong.yiping.Constant;
import com.dong.yiping.MyApplication;
import com.dong.yiping.R;
import com.dong.yiping.adapter.ComHistoryAdapter;
import com.dong.yiping.adapter.ThreeFragmentAdapter;
import com.dong.yiping.adapter.UserCollectListAdapter;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.GetZhaopinBean;
import com.dong.yiping.bean.GetJobBean.GetJob;
import com.dong.yiping.bean.GetZhaopinBean.ZhaoPin;
import com.dong.yiping.utils.LoadingUtil;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.SPUtil;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.view.LJListView;
import com.dong.yiping.view.LJListView.IXListViewListener;
/**
 * 浏览历史和收藏夹数据结构一样
 * @author dong
 *
 */
public class UserHistoryActivity extends BaseActivity implements IXListViewListener{
	@InjectView(R.id.listview) LJListView listview;
	@Inject Context mContext;
	@InjectView(R.id.tv_title_center) TextView tv_title_center;
	@InjectView(R.id.iv_title_left) ImageView iv_title_left;
	
	private List<GetJob> listGetJob;
	private List<ZhaoPin> listZhaopin;
	private boolean isRefush = true;
	private int total;//总的条数
	private int totalPages=0;//总的页数
	private int currentPage = 1;;//当前是第几页
	private int currentNum=0;//当前第几条
	private int pagerNum=10;//每页的条数
	private LoadingUtil loadingUtil;
	private int type=-1;
	private UserCollectListAdapter adapter;//学生历史
	private ComHistoryAdapter ComAdapter;//企业历史
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.HANDLER_TYPE_GETJOB:
				GetJobBean getJobBean = (GetJobBean) msg.obj;
				if(isRefush){
					resolveRefushData(getJobBean);
				}else{
					resolveLoadData(getJobBean);
				}
				
				break;
			case Constant.HANDLER_TYPE_GETZHAOPIN:
				GetZhaopinBean getZhaopin = (GetZhaopinBean) msg.obj;
				if(getZhaopin==null){
					ToastUtil.showToast(mContext, "没有更多数据");
				}
				if(isRefush){
					resolveRefushData(getZhaopin);
				}else{
					resolveLoadData(getZhaopin);
				}
				break;
			case Constant.NET_ERROR:
				//网络错误
				ToastUtil.showToast(mContext, "网络错误！");
				listview.stopRefresh();
				listview.stopLoadMore();
				
				break;
			}
		};
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userhistory);
		initView();
		setLitener();
		initData();
	}

	private void setLitener() {
		iv_title_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
	}
	/**
	 * 初始化数据
	 */
	private void initData() {
		isRefush = true;
		currentPage = 1;
		currentNum = 0;
		pagerNum = 10;
		totalPages=0;
		
		String url = Constant.HOST+getLoadUrl(currentNum,pagerNum);
		if(type==0){
			ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GETJOB));
		}
		if(type==1){
			ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GETZHAOPIN));
		}
		
	}
	private void initView() {
		type = SPUtil.getInt(mContext, "type", -1);
		loadingUtil = new LoadingUtil(mContext);
		tv_title_center.setText("浏览历史");
		listGetJob = new ArrayList<GetJobBean.GetJob>();
		listZhaopin = new ArrayList<GetZhaopinBean.ZhaoPin>();
		adapter = new UserCollectListAdapter(mContext,listGetJob);
		ComAdapter = new ComHistoryAdapter(mContext, listZhaopin);
		if(type==0){
			listview.setAdapter(adapter);
		}
		if(type==1){
			listview.setAdapter(ComAdapter);
		}
		
		listview.setPullLoadEnable(false,""); //如果不想让脚标显示数据可以mListView.setPullLoadEnable(false,null)或者mListView.setPullLoadEnable(false,"")
		listview.setPullRefreshEnable(true);
		//listview.setPullLoadEnable(false, "加载完成");
		listview.setIsAnimation(true); 
		listview.setXListViewListener(this);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(position>0){
					position--;
				}
				if(type==0){
					Intent intent = new Intent(UserHistoryActivity.this,JobMessageActivity.class);
					intent.putExtra("bannerListBean", MyApplication.getApplication().getBannerListBean());
					intent.putExtra("getJob",listGetJob.get(position));
					startActivity(intent);
				}
				if(type==1){
					Intent mIntent = new Intent(mContext,GetJobDetailActivity.class);
					mIntent.putExtra("ZhaoPin", listZhaopin.get(position));
					startActivity(mIntent);
				}
				
			}
		});
	}
	//01-03 21:55:27.451: E/AndroidRuntime(23923): java.lang.RuntimeException: Unable to start activity ComponentInfo{com.dong.yiping/com.dong.yiping.activity.MainActivity}: android.view.WindowManager$BadTokenException: Unable to add window -- token null is not valid; is your activity running?

	/**
	 * 刷新Listview数据 学生用户数据
	 * @param listGetJob
	 */
	private void notifyAdapter(List<GetJob> listGetJob) {
		adapter.notifyList(listGetJob);
	}
	/**
	 * 刷新Listview数据 企业用户数据
	 * @param listGetJob
	 */
	private void notifyComAdapter(List<ZhaoPin> listzhaoPin) {
		ComAdapter.notyfyList(listZhaopin);
	}
	/**
	 * 解析刷新数据 学生用户数据
	 * @param getJobBean 
	 */
	private void resolveRefushData(GetJobBean getJobBean){
		if(getJobBean!=null && getJobBean.getList()!=null &&getJobBean.getList().size()>0){
			total = getJobBean.getTotal();
			listGetJob.clear();
			for(GetJob job : getJobBean.getList()){
				listGetJob.add(job);
			}
			notifyAdapter(listGetJob);
			setListView(total);
		}
		
	}
	
	/**
	 * 解析加载数据 学生用户数据
	 * @param getJobBean
	 */
	private void resolveLoadData(GetJobBean getJobBean){
		if(getJobBean!=null && getJobBean.getList()!=null &&getJobBean.getList().size()>0){
			for(GetJob job : getJobBean.getList()){
				listGetJob.add(job);
			}
			adapter.addList(listGetJob);
			listview.stopRefresh();
			listview.stopLoadMore();
			listview.setRefreshTime("刚刚");
			if(currentPage < totalPages){
				listview.setPullLoadEnable(true,"加载更多");
			}else{
				listview.setPullLoadEnable(false,"没有更多数据");
			}
			
			
		}else{
			
			listview.stopRefresh();
			listview.stopLoadMore();
			listview.setRefreshTime("刚刚");
			listview.setPullLoadEnable(false, null);
		}
	}
	/**
	 * 解析刷新数据  企业数据
	 * @param getJobBean 
	 */
	private void resolveRefushData(GetZhaopinBean getZhaopin){
		if(getZhaopin!=null && getZhaopin.getList()!=null &&getZhaopin.getList().size()>0){
			total = getZhaopin.getTotal();
			listZhaopin.clear();
			for(ZhaoPin job : getZhaopin.getList()){
				listZhaopin.add(job);
			}
			notifyComAdapter(listZhaopin);
			setListView(total);
		}
		
	}
	
	

	/**
	 * 解析加载数据  企业数据
	 * @param getJobBean
	 */
	private void resolveLoadData(GetZhaopinBean getZhaopin){
		if(getZhaopin!=null && getZhaopin.getList()!=null &&getZhaopin.getList().size()>0){
			for(ZhaoPin zhaoPin : getZhaopin.getList()){
				listZhaopin.add(zhaoPin);
			}
			ComAdapter.addList(listZhaopin);
			listview.stopRefresh();
			listview.stopLoadMore();
			listview.setRefreshTime("刚刚");
			if(currentPage < totalPages){
				listview.setPullLoadEnable(true,"加载更多");
			}else{
				listview.setPullLoadEnable(false,"没有更多数据");
			}
		}else{
			listview.stopRefresh();
			listview.stopLoadMore();
			listview.setRefreshTime("刚刚");
			listview.setPullLoadEnable(false, null);
		}
	}
	private String getLoadUrl(int currentNum,int pageNum){
		//http://123.57.75.34:8080/users/api/recruitHistroyList?userid=1&currentNum=0&pageNum=5
		///api/resumeHistroyList?userid=1&currentNum=0&pageNum=5
		if(type==0){
			String str = "/recruitHistroyList?userid="+SPUtil.getInt(mContext, "id", -1)+"&currentNum="+currentNum+"&pageNum="+pageNum;
			return str;
		}else if(type==1){
			//这个接口没有返回用户信息
			String str = "/resumeHistroyList?userid="+SPUtil.getInt(mContext, "id", -1)+"&currentNum="+currentNum+"&pageNum="+pageNum;
			//String str = "/resumeHistroy?userid="+SPUtil.getInt(mContext, "id", -1)+"&currentNum="+currentNum+"&pageNum="+pageNum;
			return str;
		}
		return null;
	}
	
	private void setListView(int total) {
		if(total<=10){
			listview.stopRefresh();
			listview.stopLoadMore();
			listview.setRefreshTime("刚刚");
			listview.setPullLoadEnable(false, "");
		}else{
			totalPages = total/pagerNum +1;
			
			listview.stopRefresh();
			listview.stopLoadMore();
			listview.setRefreshTime("刚刚");
			listview.setPullLoadEnable(true,"加载更多");
		}
		
	};
	
	/**
	 * 刷新数据
	 */
	@Override
	public void onRefresh() {
		initData();
	}

	/**
	 * 加载更多
	 */
	@Override
	public void onLoadMore() {
		getLoadData();
	}
	
	private void getLoadData() {
		isRefush = false;
		currentPage++;
		currentNum = (currentPage-1)*pagerNum;
		String url = Constant.HOST + getLoadUrl(currentNum, pagerNum);
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GETJOB));
	}
}
