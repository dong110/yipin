package com.dong.yiping.fragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboFragment;
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
import com.dong.yiping.adapter.TwoFragmentAdapter;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.GetJobBean.GetJob;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.view.LJListView;
import com.dong.yiping.view.LJListView.IXListViewListener;

public class TwoFragment extends RoboFragment implements IXListViewListener{
	
	@InjectView(R.id.lv_listview) LJListView lv_listview; 
	
	private TextView tv_title_center;
	private LinearLayout ll_title_center;
	private TwoFragmentAdapter adapter;
	private Context mContext;
	private List<GetJob> listGetJob;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.HANDLER_TYPE_GETJOB:
				GetJobBean getJobBean = (GetJobBean) msg.obj;
				for(GetJob job : getJobBean.getList()){
					listGetJob.add(job);
				}
				notifyAdapter(listGetJob);
				break;
				
			default:
				break;
			}
			
		};
	};
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_two, null);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		initView();
		initData();
		
		
	}
	private void initData() {
		String url = Constant.HOST+getLoadUrl(0, 10);
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GETJOB));
		
	}

	private void initView() {
		listGetJob = new ArrayList<GetJobBean.GetJob>();
		adapter = new TwoFragmentAdapter(mContext,listGetJob);
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
				// TODO Auto-generated method stub
				System.out.println(arg0 + "------" + arg2);
			}
		});
	}
	private void notifyAdapter(List<GetJob> listGetJob) {
		adapter.notifyList(listGetJob);
	}
	private String getLoadUrl(int currentNum,int pageNum){
		String str = "/recruitList?status=1&currentNum="+currentNum+"&pageNum="+pageNum;
		return str;
	}
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
