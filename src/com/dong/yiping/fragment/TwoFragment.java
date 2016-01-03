package com.dong.yiping.fragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
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
import com.dong.yiping.MyApplication;
import com.dong.yiping.R;
import com.dong.yiping.activity.JobMessageActivity;
import com.dong.yiping.activity.MainActivity;
import com.dong.yiping.adapter.TwoFragmentAdapter;
import com.dong.yiping.bean.DictListBean.DictBean;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.GetJobBean.GetJob;
import com.dong.yiping.bean.HangYeBean;
import com.dong.yiping.bean.HangYeBean.HangYe;
import com.dong.yiping.utils.LoadingUtil;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.PopUtil;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.view.LJListView;
import com.dong.yiping.view.LJListView.IXListViewListener;
import com.dong.yiping.view.datepicker.TimeDialog;

public class TwoFragment extends RoboFragment implements IXListViewListener, OnClickListener{
	
	@InjectView(R.id.lv_listview) LJListView lv_listview; 
	@InjectView(R.id.ll_time) LinearLayout ll_time; 
	@InjectView(R.id.tv_fragmenttwo_time) TextView publishTime;
	@InjectView(R.id.ll_area) LinearLayout ll_area;
	@InjectView(R.id.ll_hangye) LinearLayout ll_hangye;
	@InjectView(R.id.tv_serach) TextView tv_serach;
	@InjectView(R.id.tv_hangye) TextView tv_hangye;
	@InjectView(R.id.tv_area) TextView tv_area;
	
	Context mContext;
	
	private TextView tv_title_center;
	private LinearLayout ll_title_center;
	private TwoFragmentAdapter adapter;
	
	private List<GetJob> listGetJob;
	private boolean isRefush = true;
	private int total;//总的条数
	private int totalPages=0;//总的页数
	private int currentPage = 1;;//当前是第几页
	private int currentNum=0;//当前第几条
	private int pagerNum=10;//每页的条数
	
	private TimeDialog timeDialog;
	private PopUtil popUtil;
	private LoadingUtil loadingUtil;
	
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
			case Constant.HANDLER_TYPE_GETHANGYE:
				HangYeBean hangYeBean = (HangYeBean) msg.obj;
				List<String> listStr = new ArrayList<String>();
				for(HangYe hangYe:hangYeBean.getList()){
					listStr.add(hangYe.getName());
				}
				loadingUtil.hideDialog();
				popUtil.createPop(mContext,listStr);
				popUtil.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position,
							long arg3) {
						
					}
				});
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
		ll_hangye.setOnClickListener(this);
		ll_area.setOnClickListener(this);
		tv_serach.setOnClickListener(this);
		
		
		lv_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mContext.startActivity(new Intent(mContext,JobMessageActivity.class));
			}
		});
		
		
	}
	private void initView() {
		
		timeDialog = new TimeDialog(mContext,customTimeListener);
		popUtil = new PopUtil();
		loadingUtil = new LoadingUtil(mContext);
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
		case R.id.ll_hangye:
			loadingUtil.showDialog();
			getHangyeData();
			
			break;
		case R.id.ll_area:
			getArea();
			break;
		case R.id.tv_serach:
			
			break;
		}
	}

	private void getArea() {
		List<DictBean> listDictBean = MyApplication.getApplication().getDictBean().getList();
		String code = "";
		for(DictBean dictBean : listDictBean){
			if(dictBean.getCode().equals("SHENG")){
				code = dictBean.getCode();
			}
		}
		String url = Constant.HOST + Constant.GET_DICT_INFO+code;
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GET_SHENG));
		
	}

	private void getHangyeData() {
		List<DictBean> listDictBean = MyApplication.getApplication().getDictBean().getList();
		String code = "";
		for(DictBean dictBean : listDictBean){
			if(dictBean.getCode().equals("HANGYE")){
				code = dictBean.getCode();
			}
		}
		String url = Constant.HOST + Constant.GET_DICT_INFO+code;
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GETHANGYE));
	}
}
