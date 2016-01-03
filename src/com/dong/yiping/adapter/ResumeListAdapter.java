package com.dong.yiping.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dong.yiping.R;
import com.dong.yiping.bean.GetZhaopinBean.ZhaoPin;

public class ResumeListAdapter extends BaseAdapter{

	private Context mContext;
	private List<ZhaoPin> listZhaopin;
	public ResumeListAdapter(Context mContext, List<ZhaoPin> listZhaopin) {
		this.mContext = mContext;
		this.listZhaopin = listZhaopin;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listZhaopin.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderTwo viewHolderTwo=null;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragmentone_two, null);
			viewHolderTwo = new ViewHolderTwo();
			viewHolderTwo.tv_arrer = (TextView) convertView.findViewById(R.id.tv_arrer);
			viewHolderTwo.tv_cp_name = (TextView) convertView.findViewById(R.id.tv_cp_name);
			viewHolderTwo.tv_group_name = (TextView) convertView.findViewById(R.id.tv_group_name);
			viewHolderTwo.tv_salary = (TextView) convertView.findViewById(R.id.tv_salary);
			convertView.setTag(viewHolderTwo);
		}else{
			viewHolderTwo = (ViewHolderTwo) convertView.getTag();
		}
		
		ZhaoPin zhaopin = listZhaopin.get(position);
		
		viewHolderTwo.tv_cp_name.setText(zhaopin.getName());
		viewHolderTwo.tv_salary.setText(zhaopin.getSubdate());
		
		viewHolderTwo.tv_group_name.setText(zhaopin.getIntention());
		viewHolderTwo.tv_arrer.setText(zhaopin.getQuxian());
		
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
	static class ViewHolderTwo{
		TextView tv_cp_name;
		TextView tv_group_name;
		TextView tv_salary;
		TextView tv_arrer;
	}
	
	public void addList(List<ZhaoPin> listZhaopin){
		this.listZhaopin = listZhaopin;
		notifyDataSetChanged();
		
	}
	
	public void notyfyList(List<ZhaoPin> listZhaopin){
		this.listZhaopin = listZhaopin;
		notifyDataSetChanged();
	}

}
