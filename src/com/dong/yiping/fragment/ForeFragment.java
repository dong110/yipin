package com.dong.yiping.fragment;

import roboguice.inject.InjectView;

import com.dong.yiping.R;
import com.dong.yiping.activity.CompanyInfoActivity;
import com.dong.yiping.activity.ModifyPwdActivity;
import com.dong.yiping.activity.PhoneIdentificationActivity;
import com.dong.yiping.activity.ResumeActivity;

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
	
	@InjectView(R.id.tv_mine_modifypwd) TextView modify_pwd;
	@InjectView(R.id.tv_mine_phone) TextView phone;
	
	private TextView tv_title_center;
	private LinearLayout ll_title_center;
	private Intent mIntent;
	private Context mContext;
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
		initData();
	}
	
	private void initData() {
		
	}

	private void initView() {
		myResume.setOnClickListener(this);
		modify_pwd.setOnClickListener(this);
		phone.setOnClickListener(this);
		
	}
	

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.myResume:
			mIntent = new Intent(mContext,CompanyInfoActivity.class);
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
		default:
			break;
		}
		
	}
	
}
