package com.dong.yiping.activity;

import java.util.ArrayList;
import java.util.List;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.adapter.ResumeListAdapter;
import com.dong.yiping.bean.GetZhaopinBean;
import com.dong.yiping.bean.GetZhaopinBean.ZhaoPin;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.view.LJListView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MyResumesActivity extends BaseActivity {

	private TextView tv_title_center;
	private ImageView iv_title_left;

	private TextView tv_myresumes_addresume;
	private LJListView lv_myresumes_listview;
	private Context mContext;
	private ResumeListAdapter adapter;
	private List<ZhaoPin> resumeList;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.HANDLER_RESUME_LIST:
				GetZhaopinBean bean = (GetZhaopinBean) msg.obj;//简历列表和招聘列表一样
				resumeList = bean.getList();
				adapter.notyfyList(resumeList);
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
		String url = Constant.HOST + Constant.GET_Resume_List + "7";
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, url, Constant.TOPER_TYPE_GET_RESUME_LIST));
	}

	public void initTitleBar() {
		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);

		tv_title_center.setText("个人简历");

	}

	public void initView() {
		tv_myresumes_addresume = $(R.id.tv_myresumes_addresume, true);
		lv_myresumes_listview = $(R.id.lv_myresumes_listview);
		lv_myresumes_listview.setPullLoadEnable(false,""); //如果不想让脚标显示数据可以mListView.setPullLoadEnable(false,null)或者mListView.setPullLoadEnable(false,"")
		lv_myresumes_listview.setPullRefreshEnable(false);
		
		resumeList = new ArrayList<GetZhaopinBean.ZhaoPin>();
		
		lv_myresumes_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int i,
					long arg3) {
				if(i>0){
					i--;
				}
				Intent intent = new Intent(MyResumesActivity.this,ResumeActivity.class);
				intent.putExtra("ID", resumeList.get(i).getId());
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
}
