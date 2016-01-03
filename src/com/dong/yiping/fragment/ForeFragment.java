package com.dong.yiping.fragment;

import roboguice.inject.InjectView;

import com.dong.yiping.R;
import com.dong.yiping.activity.ComCollectListActivity;
import com.dong.yiping.activity.CompanyInfoActivity;
import com.dong.yiping.activity.ModifyPwdActivity;
import com.dong.yiping.activity.MyResumesActivity;
import com.dong.yiping.activity.PhoneIdentificationActivity;
import com.dong.yiping.activity.ResumeActivity;
import com.dong.yiping.activity.UserCollectListActivity;
import com.dong.yiping.activity.UserHistoryActivity;
import com.dong.yiping.activity.UserShenQingActivity;
import com.dong.yiping.utils.LogUtil;
import com.dong.yiping.utils.SPUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ForeFragment extends BaseFragment implements OnClickListener{
	
	@InjectView(R.id.myResume) TextView myResume;
	@InjectView(R.id.tv_fragment_jilu) TextView tv_fragment_jilu;
	@InjectView(R.id.tv_collection) TextView tv_collection;
	@InjectView(R.id.tv_mine_modifypwd) TextView modify_pwd;
	@InjectView(R.id.tv_mine_phone) TextView phone;
	@InjectView(R.id.tv_fragmentfore_username) TextView tv_fragmentfore_username;
	@InjectView(R.id.tv_history) TextView tv_history;
	
	private TextView tv_title_center;
	private LinearLayout ll_title_center;
	private Intent mIntent;
	private Context mContext;
	private int Type;
	private String username ; 
	
	
	
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_fore, null);
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
		tv_collection.setOnClickListener(this);
		myResume.setOnClickListener(this);
		modify_pwd.setOnClickListener(this);
		phone.setOnClickListener(this);
		tv_fragment_jilu.setOnClickListener(this);
		tv_history.setOnClickListener(this);
	}

	private void initData() {
		
	}

	private void initView() {
		
		username = SPUtil.getString(mContext,"username","");
		tv_fragmentfore_username.setText(username);
		LogUtil.i("username====",username + "");
		Type = SPUtil.getInt(mContext, "type", 0);
		LogUtil.i("type====",Type + "");
		if(Type == 0){//学生用户
			myResume.setText("我的简历");
			tv_fragment_jilu.setText("职位申请记录");
			 
		}else{//公司用户
			myResume.setText("公司信息");
			tv_fragment_jilu.setText("面试邀请记录");
			
		}
	}
	

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.myResume:
			if(Type == 0){//学生用户
				mIntent = new Intent(mContext,MyResumesActivity.class);
				
			}else{//公司用户
				mIntent = new Intent(mContext,CompanyInfoActivity.class);
			}
			
			mContext.startActivity(mIntent);
			break;

		case R.id.tv_mine_modifypwd:
			mIntent = new Intent(mContext,ModifyPwdActivity.class);
			mContext.startActivity(mIntent);
			break;
			
		case R.id.tv_mine_phone:
			mIntent = new Intent(mContext,PhoneIdentificationActivity.class);
			mContext.startActivity(mIntent);
			break;
		case R.id.tv_collection:
			if(Type == 0){//学生用户
				mIntent = new Intent(mContext,UserCollectListActivity.class);
				
			}else{//公司用户
				mIntent = new Intent(mContext,ComCollectListActivity.class);
			}
			mContext.startActivity(mIntent);
			break;
		case R.id.tv_fragment_jilu:
			if(Type == 0){//学生用户
				mIntent = new Intent(mContext,UserShenQingActivity.class);
				
			}else{//公司用户
			}
			mContext.startActivity(mIntent);
			break;
		case R.id.tv_history:
			if(Type == 0){//学生用户
				mIntent = new Intent(mContext,UserHistoryActivity.class);
				
			}else{//公司用户
			}
			mContext.startActivity(mIntent);
			break;
		}
		
	}
	
}
