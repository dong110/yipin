package com.dong.yiping.fragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;
import android.content.Context;
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
import com.dong.yiping.adapter.ThreeFragmentAdapter;
import com.dong.yiping.bean.GetZhaopinBean;
import com.dong.yiping.bean.GetZhaopinBean.ZhaoPin;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.view.LJListView;
import com.dong.yiping.view.LJListView.IXListViewListener;

public class ThreeFragment extends BaseFragment implements IXListViewListener{

	@InjectView(R.id.listview) LJListView lv_listview;
	private TextView tv_title_center;
	private LinearLayout ll_title_center;
	private ThreeFragmentAdapter adapter;
	private List<ZhaoPin> listZhaopin;
	private Context mContext;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.HANDLER_TYPE_GETZHAOPIN:
				GetZhaopinBean getZhaopin = (GetZhaopinBean) msg.obj;
				for(ZhaoPin zhaopin : getZhaopin.getList()){
					listZhaopin.add(zhaopin);
				}
				notifyAdapter(listZhaopin);
				break;
				
			default:
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
		initData();
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
	private void initData() {
		String url = Constant.HOST+getLoadUrl(0, 10);
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GETZHAOPIN));
		
	}
	private String getLoadUrl(int currentNum,int pageNum){
		String str = "/resumeList?status=1&currentNum="+currentNum+"&pageNum="+pageNum;
		return str;
	}

	private void notifyAdapter(List<ZhaoPin> listZhaopin) {
		adapter.notyfyList(listZhaopin);
		
	};
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				lv_listview.stopRefresh();
				lv_listview.stopLoadMore();
				lv_listview.setRefreshTime("刚刚");
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				lv_listview.stopRefresh();
				lv_listview.stopLoadMore();
				lv_listview.setRefreshTime("刚刚");
				lv_listview.setPullLoadEnable(false, null);
			}
		}, 2000);
		
	}
}
