package com.dong.yiping.adapter;

import java.util.List;

import com.dong.yiping.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PopListViewAdapter extends BaseAdapter{
	
	private List<String> listStr;
	private Context mContext;
	public PopListViewAdapter(Context mContext, List<String> listStr) {
		this.listStr = listStr;
	}

	@Override
	public int getCount() {
			return listStr.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pop_listview, null);
		}
		TextView textview = (TextView) convertView.findViewById(R.id.tv_pop_item);
		textview.setText(listStr.get(position));
		return convertView;
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
