package com.dong.yiping.fragment;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.TextView;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.activity.GetJobDetailActivity;
import com.dong.yiping.adapter.ThreeFragmentAdapter;
import com.dong.yiping.bean.GetZhaopinBean;
import com.dong.yiping.bean.GetJobBean.GetJob;
import com.dong.yiping.bean.GetZhaopinBean.ZhaoPin;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.view.LJListView;
import com.dong.yiping.view.LJListView.IXListViewListener;

public class ThreeFragment extends BaseFragment implements IXListViewListener{

	@InjectView(R.id.listview) LJListView lv_listview;
	private TextView tv_title_center;
	private LinearLayout ll_title_center;
	private ThreeFragmentAdapter adapter;
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
				lv_listview.stopRefresh();
				lv_listview.stopLoadMore();
				
				break;
			}
			
		}

	};
	
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_three, null);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		initView();
		setListener();
		initData();
	}
	private void setListener() {
		lv_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(position>0){
					position--;
				}
				Intent mIntent = new Intent(mContext,GetJobDetailActivity.class);
				mIntent.putExtra("ZhaoPin", listZhaopin.get(position));
				startActivity(mIntent);
				
			}
		});
	}

	private void initView() {
		listZhaopin = new ArrayList<ZhaoPin>();
		adapter = new ThreeFragmentAdapter(mContext,listZhaopin);
		lv_listview.setAdapter(adapter);
		lv_listview.setPullLoadEnable(false,""); //如果不想让脚标显示数据可以mListView.setPullLoadEnable(false,null)或者mListView.setPullLoadEnable(false,"")
		lv_listview.setPullRefreshEnable(true);
		//lv_listview.setPullLoadEnable(false, "加载完成");
		lv_listview.setIsAnimation(true); 
		lv_listview.setXListViewListener(this);
		lv_listview.setOnItemClickListener(new OnItemClickListener() {
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
		adapter.notyfyList(listZhaopin);
		
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
			adapter.addList(listZhaopin);
			lv_listview.stopRefresh();
			lv_listview.stopLoadMore();
			lv_listview.setRefreshTime("刚刚");
			if(currentPage < totalPages){
				lv_listview.setPullLoadEnable(true,"加载更多");
			}else{
				lv_listview.setPullLoadEnable(false,"没有更多数据");
			}
			
			
		}else{
			
			lv_listview.stopRefresh();
			lv_listview.stopLoadMore();
			lv_listview.setRefreshTime("刚刚");
			lv_listview.setPullLoadEnable(false, null);
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
			lv_listview.stopRefresh();
			lv_listview.stopLoadMore();
			lv_listview.setRefreshTime("刚刚");
			lv_listview.setPullLoadEnable(false, "");
		}else{
			totalPages = total/pagerNum +1;
			
			lv_listview.stopRefresh();
			lv_listview.stopLoadMore();
			lv_listview.setRefreshTime("刚刚");
			lv_listview.setPullLoadEnable(true,"加载更多");
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
