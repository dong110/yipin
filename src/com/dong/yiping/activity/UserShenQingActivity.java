package com.dong.yiping.activity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.adapter.TwoFragmentAdapter;
import com.dong.yiping.adapter.YaoQingAdapter;
import com.dong.yiping.adapter.ZhiWeiShenQingAdapter;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.GetJobBean.GetJob;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.view.LJListView;
import com.dong.yiping.view.LJListView.IXListViewListener;

import roboguice.inject.InjectView;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserShenQingActivity extends BaseActivity implements IXListViewListener{
	
	@InjectView(R.id.bt_shenqing) Button bt_shenqing;
	@InjectView(R.id.bt_yaoqing) Button bt_yaoqing;
	@InjectView(R.id.listview) LJListView listview;
	@InjectView(R.id.tv_title_center) TextView tv_title_center;
	@InjectView(R.id.iv_title_left) ImageView iv_title_left;
	@Inject Context mContext;
	
	private boolean isShenQing= true;
	
	private YaoQingAdapter adapter;
	
	private List<GetJob> listGetJob;
	private boolean isRefush = true;
	private int total;//总的条数
	private int totalPages=0;//总的页数
	private int currentPage = 1;;//当前是第几页
	private int currentNum=0;//当前第几条
	private int pagerNum=10;//每页的条数
	
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
				
			case Constant.NET_ERROR:
				//网络错误
				ToastUtil.showToast(mContext, "网络错误！");
				listview.stopRefresh();
				listview.stopLoadMore();
				
				break;
			}
		};
	};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhiweishenqing);
		initView();
		initData();
	}

	private void initView() {
		tv_title_center.setText("职位申请记录");
		iv_title_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		listGetJob = new ArrayList<GetJobBean.GetJob>();
		adapter = new YaoQingAdapter(mContext, listGetJob);
		listview.setAdapter(adapter);
		
		listview.setPullLoadEnable(false,""); //如果不想让脚标显示数据可以mListView.setPullLoadEnable(false,null)或者mListView.setPullLoadEnable(false,"")
		listview.setPullRefreshEnable(true);
		listview.setPullLoadEnable(false, "加载完成");
		listview.setIsAnimation(true); 
		listview.setXListViewListener(this);
		bt_shenqing.setSelected(true);
		
		bt_shenqing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				bt_shenqing.setSelected(true);
				bt_yaoqing.setSelected(false);
				isShenQing = true;
				initData();
			}
		});
		
		bt_yaoqing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				bt_shenqing.setSelected(false);
				bt_yaoqing.setSelected(true);
				isShenQing = false;
				initData();
			}
		});
		
	}
	/**
	 * 刷新Listview数据
	 * @param listGetJob
	 */
	private void notifyAdapter(List<GetJob> listGetJob) {
		adapter.notifyList(listGetJob);
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
	 * 解析刷新数据
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
	 * 解析加载数据
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
	
	private void initData() {
		//http://123.57.75.34:8080/users/api/inviteList?userId=2&currentNum=0&pageNum=5
		//http://123.57.75.34:8080/users/api/inviteList?userId=2&currentNum=0&pageNum=5&isrecruit=1
		String url = getLoadUrl(currentNum,pagerNum);
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, url, Constant.TOPER_TYPE_GETJOB));
		
	}
	private String getLoadUrl(int currentNum,int pageNum){
		String url = Constant.HOST+"/inviteList?userId=2&currentNum="+currentNum+"&pageNum="+pagerNum;
		if(!isShenQing){
			url = Constant.HOST+"/inviteList?userId=2&currentNum="+currentNum+"&pageNum="+pagerNum+"&isrecruit=1";
		}
		return url;
	}

	@Override
	public void onRefresh() {
		initData();
	}

	@Override
	public void onLoadMore() {
		getLoadData();
		
	}

	private void getLoadData() {
			isRefush = false;
			currentPage++;
			currentNum = (currentPage-1)*pagerNum;
			String url = getLoadUrl(currentNum, pagerNum);
			ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GETJOB));
	}
	
}