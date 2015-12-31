package com.dong.yiping.fragment;

import roboguice.inject.InjectView;

import com.dong.yiping.R;
import com.dong.yiping.adapter.ThreeFragmentAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ThreeFragment extends BaseFragment{

	@InjectView(R.id.listview) ListView listview;
	private TextView tv_title_center;
	private LinearLayout ll_title_center;
	private ThreeFragmentAdapter adapter;
	private Context mContext;
	
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_three, null);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		//initView();
		init();
	}
	private void initView() {
		tv_title_center = (TextView) getActivity().findViewById(R.id.tv_title_center);
		ll_title_center = (LinearLayout) getActivity().findViewById(R.id.ll_title_center);
		
		tv_title_center.setVisibility(View.GONE);
		ll_title_center.setVisibility(View.VISIBLE);
	}
	private void init() {
		adapter = new ThreeFragmentAdapter(mContext);
		listview.setAdapter(adapter);
		
	}
}
