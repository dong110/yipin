package com.dong.yiping.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.adapter.ComCollectListAdapter;
import com.dong.yiping.adapter.ThreeFragmentAdapter;
import com.dong.yiping.adapter.UserCollectListAdapter;
import com.dong.yiping.bean.GetZhaopinBean;
import com.dong.yiping.bean.GetZhaopinBean.ZhaoPin;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.view.LJListView;
import com.dong.yiping.view.LJListView.IXListViewListener;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ComCollectListActivity extends BaseActivity implements IXListViewListener{
	
	@InjectView(R.id.listview) LJListView listview;
	
	private ComCollectListAdapter comAdapter;
	private List<ZhaoPin> listZhaopin;
	private Context mContext;
	private boolean isRefush = true;
	private int total;//总的条数
	private int totalPages=0;//总的页数
	private int currentPage = 1;;//当前是第几页
	private int currentNum=0;//当前第几条
	private int pagerNum=10;//每页的条数
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.HANDLER_TYPE_GETZHAOPIN:
				GetZhaopinBean getZhaopin = (GetZhaopinBean) msg.obj;
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
			
		}

	};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collectionlist);
		initView();
		initData();
	}

	private void initView() {
		listZhaopin = new ArrayList<ZhaoPin>();
		comAdapter = new ComCollectListAdapter(mContext,listZhaopin);
		listview.setAdapter(comAdapter);
		listview.setPullLoadEnable(false,""); //如果不想让脚标显示数据可以mListView.setPullLoadEnable(false,null)或者mListView.setPullLoadEnable(false,"")
		listview.setPullRefreshEnable(true);
		//listview.setPullLoadEnable(false, "加载完成");
		listview.setIsAnimation(true); 
		listview.setXListViewListener(this);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println(arg0 + "------" + arg2);
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
		String url = Constant.HOST+getLoadUrl(currentNum, pagerNum);
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GETZHAOPIN));
		
	}
	private String getLoadUrl(int currentNum,int pageNum){
		String str = "/resumeList?status=1&currentNum="+currentNum+"&pageNum="+pageNum;
		return str;
	}

	private void notifyAdapter(List<ZhaoPin> listZhaopin) {
		comAdapter.notyfyList(listZhaopin);
		
	};
	
	/**
	 * 解析加载数据
	 * @param getZhaopin
	 */
	private void resolveLoadData(GetZhaopinBean getZhaopin) {
		if(getZhaopin!=null && getZhaopin.getList()!=null &&getZhaopin.getList().size()>0){
			for(ZhaoPin zhaoPin : getZhaopin.getList()){
				listZhaopin.add(zhaoPin);
			}
			comAdapter.addList(listZhaopin);
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
	 * 解析刷新数据
	 * @param getZhaopin
	 */
	private void resolveRefushData(GetZhaopinBean getZhaopin) {
		if(getZhaopin!=null && getZhaopin.getList()!=null &&getZhaopin.getList().size()>0){
			total = getZhaopin.getTotal();
			listZhaopin.clear();
			for(ZhaoPin zhaoPin : getZhaopin.getList()){
				listZhaopin.add(zhaoPin);
			}
			notifyAdapter(listZhaopin);
			setListView(total);
		}
		
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
	
	private void getLoadData() {
		isRefush = false;
		currentPage++;
		currentNum = (currentPage-1)*pagerNum;
		String url = Constant.HOST + getLoadUrl(currentNum, pagerNum);
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GETZHAOPIN));
		
	}
	
	@Override
	public void onRefresh() {
		initData();
	}

	@Override
	public void onLoadMore() {
		getLoadData();
		
	}
}
