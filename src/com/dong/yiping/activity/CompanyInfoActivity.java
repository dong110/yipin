package com.dong.yiping.activity;

import java.util.HashMap;
import java.util.Map;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.bean.JobDetailInfo;
import com.dong.yiping.bean.StarCompanyBean.StarCom;
import com.dong.yiping.bean.UserBean;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompanyInfoActivity extends BaseActivity {

	private TextView tv_title_center;
	private ImageView iv_title_left;
	private EditText et_company;
	private EditText et_tel;
	private EditText et_email;
	private EditText et_name;
	private EditText et_jieshao;
	
	private LinearLayout ll_buts;
	private String type;
	private StarCom starCom;
	private EditText et_jieshao_two;
	private Context mContext;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.NET_ERROR:
				ToastUtil.showToast(mContext, "获取公司详情失败！");
				break;
			case Constant.NET_NULL:
				ToastUtil.showToast(mContext, "职位公司数据为空！");
				break;
			case Constant.NET_SUCCESS:

				//填充页面数据

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
		setContentView(R.layout.activity_companyinfo);
		mContext = this;
		getIntentData();
		initView();
		initData();
	}

	private void getIntentData() {
		type = getIntent().getStringExtra("type");
		starCom = (StarCom) getIntent().getSerializableExtra("Company");
		
		
	}

	public void initView() {
		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);
		ll_buts = $(R.id.ll_buts);
		tv_title_center.setText("企业信息");
		
		et_company = $(R.id.et_company);
		et_tel = $(R.id.et_tel);
		et_email = $(R.id.et_email);
		et_name = $(R.id.et_name);
		et_jieshao = $(R.id.et_jieshao);
		et_jieshao_two = $(R.id.et_jieshao_two);
		if("oneFragment".equals(type)){
			ll_buts.setVisibility(View.GONE);
			et_company.setFocusable(false);
			et_company.setFocusableInTouchMode(false);
			
			et_tel.setFocusable(false);
			et_tel.setFocusableInTouchMode(false);
			
			et_email.setFocusable(false);
			et_email.setFocusableInTouchMode(false);
			
			et_name.setFocusable(false);
			et_name.setFocusableInTouchMode(false);
			
			et_jieshao.setFocusable(false);
			et_jieshao.setFocusableInTouchMode(false);
			
			et_jieshao_two.setFocusable(false);
			et_jieshao_two.setFocusableInTouchMode(false);
			
			et_jieshao.setVisibility(View.GONE);
			et_jieshao_two.setVisibility(View.VISIBLE);
		}
		if(starCom != null){
			et_company.setText(starCom.getConpany());
			et_tel.setText(starCom.getTel());
			et_email.setText(starCom.getEmail());
			et_name.setText(starCom.getName());
			et_jieshao_two.setText(starCom.getContent()); 
		}
	}

	public void initData() {
		


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

		}
	}
}
