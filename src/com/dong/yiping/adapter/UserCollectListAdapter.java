package com.dong.yiping.adapter;

import java.util.List;

import com.dong.yiping.R;
import com.dong.yiping.adapter.TwoFragmentAdapter.ViewHolderTwo;
import com.dong.yiping.bean.GetJobBean.GetJob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserCollectListAdapter extends BaseAdapter{

	private Context mContext;
	private List<GetJob> listGetJob;
	public UserCollectListAdapter(Context mContext, List<GetJob> listGetJob) {
		this.mContext = mContext;
		this.listGetJob = listGetJob;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listGetJob.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolderTwo viewHolderTwo = null;
		
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
		
		GetJob getJob = listGetJob.get(position);
		
		
		viewHolderTwo.tv_cp_name.setText(getJob.getJob());
		viewHolderTwo.tv_salary.setText(getJob.getWage());
		
		viewHolderTwo.tv_group_name.setText(getJob.getSubdate());
		viewHolderTwo.tv_arrer.setText(getJob.getWelfare());
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
	
	public void notifyList(List<GetJob> listGetJob){
		this.listGetJob = listGetJob;
		notifyDataSetChanged();
	}
	
	public void addList(List<GetJob> listGetJob){
		this.listGetJob = listGetJob;
		notifyDataSetChanged();
	}
	
	static class ViewHolderTwo{
		TextView tv_cp_name;
		TextView tv_group_name;
		TextView tv_salary;
		TextView tv_arrer;
	}
}
