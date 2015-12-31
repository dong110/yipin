package com.dong.yiping.fragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dong.yiping.R;
import com.dong.yiping.activity.GetJobDetailActivity;
import com.dong.yiping.adapter.OneFragmentAdapter;
import com.dong.yiping.bean.ADBean;
import com.dong.yiping.bean.OneFragmentJobBean;
import com.dong.yiping.utils.TuTu;

public class OneFragment extends RoboFragment{

	@InjectView(R.id.lv_listview) ListView lv_listview;
	/*轮播图相关*/
	private ViewPager ad_viewPage;
	private TextView tv_msg;
	private LinearLayout ll_dian;
	private List<ADBean> listADbeans;
	private TuTu tu;
	private String[] des = { "1111111", "22222222", "3333333", "4444444444","55555555555" };
	private String[] urls = { "http://a.hiphotos.baidu.com/image/pic/item/0bd162d9f2d3572ce98282e18e13632762d0c3af.jpg",
			"http://d.hiphotos.baidu.com/image/pic/item/1b4c510fd9f9d72aebede7a1d62a2834359bbb85.jpg",
			"http://h.hiphotos.baidu.com/image/pic/item/91ef76c6a7efce1be2f4f15cad51f3deb58f654c.jpg",
			"http://h.hiphotos.baidu.com/image/w%3D230/sign=3e9ec55457fbb2fb342b5f117f4b2043/e850352ac65c1038343303cbb0119313b07e896e.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/d53f8794a4c27d1e3625e52d18d5ad6edcc438dc.jpg" };
	private OneFragmentAdapter adapter;
	private List<OneFragmentJobBean> listJob;
	private List<OneFragmentJobBean> listZhaoPin;
	private List<OneFragmentJobBean> listGroup;
	private List<OneFragmentJobBean> listStudent;
	private View view_hand;
	private TextView tv_title_center;
	private LinearLayout ll_title_center;
	
	private Context mContext;
	
	
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_one, null);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		//initView();
		initData();
		init();
		initAD();
	}

	private void initView() {
		tv_title_center = (TextView) getActivity().findViewById(R.id.tv_title_center);
		ll_title_center = (LinearLayout) getActivity().findViewById(R.id.ll_title_center);
		
		tv_title_center.setVisibility(View.VISIBLE);
		ll_title_center.setVisibility(View.GONE);
		tv_title_center.setText("主页");
	}

	private void initAD() {
		listADbeans = new ArrayList<ADBean>();
		for(int i =0;i<5;i++){
			ADBean bean = new ADBean();
			bean.setAdName(des[i]);
			bean.setId(i+"");
			bean.setImgUrl(urls[i]);
			//bean.setImgPath(ids[i]);
			listADbeans.add(bean);
		}
		tu = new TuTu(ad_viewPage, tv_msg, ll_dian, mContext, listADbeans);
		tu.startViewPager(4000);//动态设置滑动间隔，并且开启轮播图
		
	}

	private void initData() {
		listJob = new ArrayList<OneFragmentJobBean>();
		for(int i=0;i<5;i++){
			OneFragmentJobBean bean = new OneFragmentJobBean("医院代表--实习生","中日友好医院","2000-3000/月","朝阳区");
			listJob.add(bean);
		}
		listZhaoPin = new ArrayList<OneFragmentJobBean>();
		for(int i=0;i<5;i++){
			OneFragmentJobBean bean = new OneFragmentJobBean("医院代表--实习生","中日友好医院","2000-3000/月","朝阳区");
			listZhaoPin.add(bean);
		}
		listGroup = new ArrayList<OneFragmentJobBean>();
		for(int i=0;i<5;i++){
			OneFragmentJobBean bean = new OneFragmentJobBean("医院代表--实习生","中日友好医院","2000-3000/月","朝阳区");
			listGroup.add(bean);
		}
		listStudent = new ArrayList<OneFragmentJobBean>();
		for(int i=0;i<5;i++){
			OneFragmentJobBean bean = new OneFragmentJobBean("医院代表--实习生","中日友好医院","2000-3000/月","朝阳区");
			listStudent.add(bean);
		}
	}

	private void init() {
		view_hand = LayoutInflater.from(mContext).inflate(R.layout.ad_layout, null);
		ad_viewPage = (ViewPager)view_hand.findViewById(R.id.ad_viewPage);
		tv_msg = (TextView)view_hand.findViewById(R.id.tv_msg);
		ll_dian = (LinearLayout)view_hand.findViewById(R.id.ll_dian);
		
		lv_listview.addHeaderView(view_hand);
		adapter = new OneFragmentAdapter(mContext,listJob,listZhaoPin,listGroup,listStudent);
		lv_listview.setAdapter(adapter);
		lv_listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(position == 0 || position == listJob.size()+1 || position == listJob.size()+2 || position == listJob.size()+listGroup.size()+2){
					
				}else{
					mContext.startActivity(new Intent(mContext,GetJobDetailActivity.class));
				}
			}
		});
	}
	
}
