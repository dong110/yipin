package com.dong.yiping.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.bean.JobDetailInfo;
import com.dong.yiping.bean.GetZhaopinBean.ZhaoPin;
import com.dong.yiping.utils.LoadingUtil;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.SPUtil;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.view.datepicker.TimeDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.test.TouchUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResumeActivity extends BaseActivity {

	private TextView tv_title_center;
	private ImageView iv_title_left;
	private TextView tv_resume_birthday;
	private TextView tv_resume_fabu;
	private TextView tv_resume_baocun;
	private EditText et_username;
	private EditText et_pinjia;
	private EditText et_intedsy;
	private EditText et_working;
	private EditText et_launager;
	private LinearLayout ll_upload_img;
	
	private LoadingUtil loadingUtil;
	private Context mContext;
	private boolean isSave=true;
	private TimeDialog timeDialog;
	private Intent intent;
	private String resumeId;
	private ZhaoPin resumen;//简历和招聘对象一样的
	private TimeDialog.CustomTimeListener customTimeListener = new TimeDialog.CustomTimeListener() {
		@Override
		public void setTime(String time) {
			tv_resume_birthday.setText(time);
			timeDialog.dismiss();
		}
	};
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.HANDLER_TYPE_UPDATE_RESUME:
				int status = (Integer) msg.obj;
				if(status == 0){
					if(isSave){
						ToastUtil.showToast(mContext, "保存简历成功");
					}else{
						ToastUtil.showToast(mContext, "发布简历成功");
					}
					
				}
				if(status == 1){
					if(isSave){
						ToastUtil.showToast(mContext, "保存简历失败");
					}else{
						ToastUtil.showToast(mContext, "发布简历失败");
					}
					
				}
				break;
			case Constant.NET_ERROR:
				if(isSave){
					ToastUtil.showToast(mContext, "保存简历失败");
				}else{
					ToastUtil.showToast(mContext, "发布简历失败");
				}
				break;
			}
			
			loadingUtil.hideDialog();
		};
	};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 当前页面从右往左进入
		overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
		setContentView(R.layout.activity_resume);
		mContext = this;
		getIntentData();
		initView();
	}



	private void getIntentData() {
		resumen = (ZhaoPin) getIntent().getSerializableExtra("resumeBean");
		
		
	}



	private void initView() {
		loadingUtil = new LoadingUtil(mContext);
		timeDialog = new TimeDialog(this, customTimeListener);
		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);
		tv_resume_birthday = $(R.id.tv_resume_birthday, true);
		tv_resume_fabu = $(R.id.tv_resume_fabu, true);
		tv_resume_baocun =  $(R.id.tv_resume_baocun, true);
		et_username = $(R.id.et_username);
		et_pinjia = $(R.id.et_pinjia);
		et_intedsy = $(R.id.et_intedsy);
		et_working = $(R.id.working);
		et_launager = $(R.id.launager);
		ll_upload_img = $(R.id.ll_upload_img,true);
		
		if(resumen != null){
			et_username.setText(resumen.getName());
			et_intedsy.setText(resumen.getIntention());
			et_launager.setText(resumen.getLange());
			et_working.setText(resumen.getWorking());
			et_pinjia.setText(resumen.getAnble());
			tv_title_center.setText("修改简历");
		}else{
			tv_title_center.setText("添加简历");
		}
		
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

		case R.id.tv_resume_birthday:
			timeDialog.show();

			break;
		case R.id.tv_resume_fabu://发布简历
			isSave = false;
			resumeSave(1);
			break;
		case R.id.tv_resume_baocun://保存简历
			isSave = true;
			resumeSave(0);
			break;

		}
	}

	
	/**
	 * 保存简历
	 */
	private void resumeSave(int status) {
		loadingUtil.showDialog();
		String username = et_username.getText().toString().trim();
		String birthday = tv_resume_birthday.getText().toString().trim();
		String pinjia = et_pinjia.getText().toString().trim();
		String indety = et_intedsy.getText().toString().trim();
		String working = et_working.getText().toString().trim();
		String launager = et_launager.getText().toString().trim();
		
		String url = Constant.HOST + Constant.UPDATE_RESUME;
		Map<String, String> paramsMap = new HashMap<String, String>();
		String id = System.currentTimeMillis()+"";
		if(resumen!=null){
			id = resumen.getId()+"";
		}
		paramsMap.put("id",id );//简历ID
		paramsMap.put("intention", indety);//求职意向
		paramsMap.put("working", working);//工作经验
		paramsMap.put("lange", launager);//语言
		paramsMap.put("anble", pinjia);//能力
		if(status == 0){
			if(resumen!=null){
				status = resumen.getStatus();
			}
		}
		
		paramsMap.put("status", status+"");//0 未发布，1已发布
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String subdate = format.format(new Date());
		paramsMap.put("subdate", subdate);
		if(resumen !=null){
			subdate = resumen.getSubdate();
		}
		
		String userid = SPUtil.getInt(mContext, "id", -1)+"";
		if(resumen !=null){
			userid = resumen.getUserid()+"";
		}
		paramsMap.put("userid", userid);
		
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, url, paramsMap, Constant.TOPER_TYPE_UPDATE_RESUME));
		
	}
}
