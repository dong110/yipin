package com.dong.yiping.adapter;

import java.util.List;

import com.dong.yiping.R;
import com.dong.yiping.bean.OneFragmentJobBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class OneFragmentAdapter extends BaseAdapter{
	private List<OneFragmentJobBean> listJob;
	private List<OneFragmentJobBean> listZhaoPin;
	private List<OneFragmentJobBean> listGroup;
	private List<OneFragmentJobBean> listStudent;
	
	private int TYPE_ONE = 0;
	private int type_two = 1;
	private int type_three = 2;
	private int type_fore = 3;
	private int type_five = 4;
	
	private Context mContext;
	
	public OneFragmentAdapter(Context mContext, List<OneFragmentJobBean> listJob, List<OneFragmentJobBean> listZhaoPin, List<OneFragmentJobBean> listGroup, List<OneFragmentJobBean> listStudent) {
		this.listJob = listJob;
		this.listZhaoPin = listZhaoPin;
		this.listGroup = listGroup;
		this.listStudent = listStudent;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listJob.size()+listGroup.size()+listStudent.size()+4;
	}
	
	@Override
	public int getItemViewType(int position) {
		if(position == 0){
			return TYPE_ONE;
		}
		if(position>=1 && position<listJob.size()+1){
			return type_two;
		}
		if(position==listJob.size()+1){
			return type_three;
		}
		if(position==listJob.size()+2){
			return type_fore;
		}
		if(position>listJob.size()+2 && position<listJob.size()+listGroup.size()+2){
			return type_two;
		}
		if(position==listJob.size()+listGroup.size()+2){
			return type_five;
		}
		if(position>listJob.size()+listGroup.size()+2 && position<listJob.size()+listGroup.size()+listStudent.size()+4){
			return type_two;
		}
		
		return 0;
	}
	
	@Override
	public int getViewTypeCount() {
		return 5;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		switch (getItemViewType(position)) {
		case 0:
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragmentone_one, null);
			
			break;
		case 1:
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragmentone_two, null);
			
			break;
		case 2:
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragmentone_three, null);
			
			break;
		case 3:
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragmentone_fore, null);
			break;
		case 4:
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragmentone_five, null);
			break;
		}
		
		return convertView;
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
