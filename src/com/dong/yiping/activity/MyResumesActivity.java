package com.dong.yiping.activity;

import java.util.ArrayList;
import java.util.List;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.adapter.ResumeListAdapter;
import com.dong.yiping.bean.GetZhaopinBean;
import com.dong.yiping.bean.GetZhaopinBean.ZhaoPin;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.SPUtil;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.view.LJListView;
import com.dong.yiping.view.LJListView.IXListViewListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MyResumesActivity extends BaseActivity implements IXListViewListener {

	private TextView tv_title_center;
	private ImageView iv_title_left;
	
	private boolean isRefush = true;
	private int total;//总的条数
	private int totalPages=0;//总的页数
	private int currentPage = 1;;//当前是第几页
	private int currentNum=0;//当前第几条
	private int pageNum=10;//每页的条数
	
	private TextView tv_myresumes_addresume;
	private LJListView lv_myresumes_listview;
	private Context mContext;
	private ResumeListAdapter adapter;
	private List<ZhaoPin> resumeList;
	
	private Handler mHandler = new Handler(){
		@SuppressWarnings("unused")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.HANDLER_RESUME_LIST:
				//简历列表和招聘列表一样
				GetZhaopinBean bean = (GetZhaopinBean) msg.obj;
				if(bean==null){
					ToastUtil.showToast(mContext, "没有更多数据");
				}
				if(isRefush){
					resolveRefushData(bean);
				}else{
					resolveLoadData(bean);
				}
				break;
			default:
				break;
			}
		};
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 当前页面从右往左进入
		overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
		setContentView(R.layout.activity_myresumes);
		mContext = this;
		initTitleBar();
		initView();
		initData();
	}

	private void initData() {
		//http://123.57.75.34:8080/users/api/resumeList?userId=7
		//String url = Constant.HOST + Constant.GET_Resume_List + SPUtil.getInt(mContext, "id", -1);
		isRefush = true;
		currentPage = 1;
		currentNum = 0;
		pageNum = 10;
		totalPages=0;
		String url = Constant.HOST + getLoadUrl(currentNum, pageNum);
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, url, Constant.TOPER_TYPE_GET_RESUME_LIST));
	}
	private String getLoadUrl(int currentNum,int pageNum){
		String str = "/resumeList?userid="+SPUtil.getInt(mContext, "id", -1)+"&status=1&currentNum="+currentNum+"&pageNum="+pageNum;
		return str;
	}
	/**
	 * 解析刷新数据
	 * @param getZhaopin
	 */
	private void resolveRefushData(GetZhaopinBean getZhaopin) {
		if(getZhaopin!=null && getZhaopin.getList()!=null &&getZhaopin.getList().size()>0){
			total = getZhaopin.getTotal();
			resumeList.clear();
			for(ZhaoPin zhaoPin : getZhaopin.getList()){
				resumeList.add(zhaoPin);
			}
			notifyAdapter(resumeList);
			setListView(total);
		}else{
			total = 0;
			resumeList.clear();
			notifyAdapter(resumeList);
			setListView(total);
		}
		
	}
	/**
	 * 解析加载数据
	 * @param getZhaopin
	 */
	private void resolveLoadData(GetZhaopinBean getZhaopin) {
		if(getZhaopin!=null && getZhaopin.getList()!=null &&getZhaopin.getList().size()>0){
			for(ZhaoPin zhaoPin : getZhaopin.getList()){
				resumeList.add(zhaoPin);
			}
			adapter.addList(resumeList);
			lv_myresumes_listview.stopRefresh();
			lv_myresumes_listview.stopLoadMore();
			lv_myresumes_listview.setRefreshTime("刚刚");
			if(currentPage < totalPages){
				lv_myresumes_listview.setPullLoadEnable(true,"加载更多");
			}else{
				lv_myresumes_listview.setPullLoadEnable(false,"没有更多数据");
			}
			
			
		}else{
			
			lv_myresumes_listview.stopRefresh();
			lv_myresumes_listview.stopLoadMore();
			lv_myresumes_listview.setRefreshTime("刚刚");
			lv_myresumes_listview.setPullLoadEnable(false, "没有更多数据");
		}
		
	}
	private void notifyAdapter(List<ZhaoPin> listZhaopin) {
		adapter.notyfyList(listZhaopin);
		
	};
	private void setListView(int total) {
		if(total<=10){
			lv_myresumes_listview.stopRefresh();
			lv_myresumes_listview.stopLoadMore();
			lv_myresumes_listview.setRefreshTime("刚刚");
			lv_myresumes_listview.setPullLoadEnable(false, "");
		}else{
			totalPages = total/pageNum +1;
			
			lv_myresumes_listview.stopRefresh();
			lv_myresumes_listview.stopLoadMore();
			lv_myresumes_listview.setRefreshTime("刚刚");
			lv_myresumes_listview.setPullLoadEnable(true,"加载更多");
		}
		
	};
	
	public void initTitleBar() {
		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);

		tv_title_center.setText("个人简历");

	}

	public void initView() {
		tv_myresumes_addresume = $(R.id.tv_myresumes_addresume, true);
		lv_myresumes_listview = $(R.id.lv_myresumes_listview);
		lv_myresumes_listview.setPullLoadEnable(false,""); //如果不想让脚标显示数据可以mListView.setPullLoadEnable(false,null)或者mListView.setPullLoadEnable(false,"")
		lv_myresumes_listview.setPullRefreshEnable(true);
		//lv_listview.setPullLoadEnable(false, "加载完成");
		lv_myresumes_listview.setIsAnimation(true); 
		lv_myresumes_listview.setXListViewListener(this);
		
		resumeList = new ArrayList<GetZhaopinBean.ZhaoPin>();
		
		lv_myresumes_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int i,
					long arg3) {
				if(i>0){
					i--;
				}
				Intent intent = new Intent(MyResumesActivity.this,ResumeActivity.class);
				intent.putExtra("resumeBean", resumeList.get(i));
				startActivity(intent);
			}
		});
		adapter = new ResumeListAdapter(mContext, resumeList);
		lv_myresumes_listview.setAdapter(adapter);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			this.finish();
			// 当前页面向右退出
			overridePendingTransition(R.anim.left_to_center,
					R.anim.center_to_right);
			break;

		case R.id.tv_myresumes_addresume:
			// 添加简历
			
			startToActivity(ResumeActivity.class);
			break;

		}
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
		currentNum = (currentPage-1)*pageNum;
		String url = Constant.HOST + getLoadUrl(currentNum, pageNum);
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GETZHAOPIN));
		
	}
}
