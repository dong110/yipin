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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.activity.MainActivity;
import com.dong.yiping.adapter.TwoFragmentAdapter;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.GetJobBean.GetJob;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.view.LJListView;
import com.dong.yiping.view.LJListView.IXListViewListener;
import com.dong.yiping.view.datepicker.TimeDialog;

public class TwoFragment extends RoboFragment implements IXListViewListener, OnClickListener{
	
	@InjectView(R.id.lv_listview) LJListView lv_listview; 
	@InjectView(R.id.ll_time) LinearLayout ll_time; 
	@InjectView(R.id.tv_fragmenttwo_time) TextView publishTime;
	
	private TextView tv_title_center;
	private LinearLayout ll_title_center;
	private TwoFragmentAdapter adapter;
	private Context mContext;
	private List<GetJob> listGetJob;
	private boolean isRefush = true;
	private int total;//总的条数
	private int totalPages=0;//总的页数
	private int currentPage = 1;;//当前是第几页
	private int currentNum=0;//当前第几条
	private int pagerNum=10;//每页的条数
	
	private TimeDialog timeDialog;

    private TimeDialog.CustomTimeListener customTimeListener = new TimeDialog.CustomTimeListener() {
        @Override
        public void setTime(String time) {
        	publishTime.setText(time);
            timeDialog.dismiss();
        }
    };
    
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
				lv_listview.stopRefresh();
				lv_listview.stopLoadMore();
				
				break;
			}
			
		}

		
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
		setListener();
		initData();
		
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
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GETJOB));
		
	}
	private void setListener() {
		ll_time.setOnClickListener(this);
		
	}
	private void initView() {
		
		timeDialog = new TimeDialog(mContext,customTimeListener);
		
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
				System.out.println(arg0 + "------" + arg2);
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
	
	private String getLoadUrl(int currentNum,int pageNum){
		String str = "/recruitList?status=1&currentNum="+currentNum+"&pageNum="+pageNum;
		return str;
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_time:
			   timeDialog.show();
			break;
		default:
			break;
		}
		
	}
}
