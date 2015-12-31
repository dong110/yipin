package com.dong.yiping.fragment;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

import com.dong.yiping.R;
import com.dong.yiping.adapter.TwoFragmentAdapter;

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

public class TwoFragment extends RoboFragment{
	
	@InjectView(R.id.lv_listview) ListView lv_listview; 
	private TextView tv_title_center;
	private LinearLayout ll_title_center;
	private TwoFragmentAdapter adapter;
	private Context mContext;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_two, null);
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
		adapter = new TwoFragmentAdapter(mContext);
		lv_listview.setAdapter(adapter);
	}
}
