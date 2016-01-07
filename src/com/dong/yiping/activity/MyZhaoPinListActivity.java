package com.dong.yiping.activity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.adapter.TwoFragmentAdapter;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.GetJobBean.GetJob;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.SPUtil;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.view.LJListView;
import com.dong.yiping.view.LJListView.IXListViewListener;

import roboguice.inject.InjectView;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyZhaoPinListActivity extends BaseActivity implements IXListViewListener{
	
	@InjectView(R.id.iv_title_left) ImageView iv_title_left;
	@InjectView(R.id.tv_title_center) TextView tv_title_center;
	@InjectView(R.id.listview) LJListView listview;
	@InjectView(R.id.bt_fabuzhaopin) Button bt_fabuzhaopin;
	@Inject Context mContext;
	private List<GetJob> listGetJob;
	private TwoFragmentAdapter adapter;//和我要招聘列表一样
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.HANDLER_TYPE_GETJOB:
				GetJobBean getJobBean = (GetJobBean) msg.obj;
				listGetJob.clear();
				for(GetJob getJob:getJobBean.getList()){
					listGetJob.add(getJob);
				}
				adapter.notifyList(listGetJob);
				setListview();
				break;

			default:
				break;
			}
		};
	};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myzhaopinlist);
		initView();
		initData();
	}

	private void initView() {
		
		iv_title_left.setOnClickListener(this);
		tv_title_center.setText("招聘列表");
		listGetJob = new ArrayList<GetJobBean.GetJob>();
		adapter = new TwoFragmentAdapter(mContext,listGetJob);
		listview.setAdapter(adapter);
		listview.setPullLoadEnable(false,""); //如果不想让脚标显示数据可以mListView.setPullLoadEnable(false,null)或者mListView.setPullLoadEnable(false,"")
		listview.setPullRefreshEnable(true);
		//lv_listview.setPullLoadEnable(false, "加载完成");
		listview.setIsAnimation(true); 
		listview.setXListViewListener(this);
	}

	private void initData() {
		String url = Constant.HOST+Constant.RECRUITLIST+getUrlData();
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, url, Constant.TOPER_TYPE_GETJOB));
	}
	
	private String getUrlData(){
		return "userid="+SPUtil.getInt(mContext, "id", -1)+"&status=1&currentNum=0&pageNum=5";
	}

	@Override
	public void onRefresh() {
		initData();
	}

	private void setListview() {
		listview.stopLoadMore(); //如果不想让脚标显示数据可以mListView.setPullLoadEnable(false,null)或者mListView.setPullLoadEnable(false,"")
		listview.stopRefresh();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			finish();
			break;

		default:
			break;
		}
	}
}
