package com.dong.yiping.adapter;

import java.util.List;

import com.dong.yiping.R;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.GetZhaopinBean;
import com.dong.yiping.bean.OneFragmentJobBean;
import com.dong.yiping.bean.StarCompanyBean;
import com.dong.yiping.bean.StarStudentBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class OneFragmentAdapter extends BaseAdapter{
	private GetJobBean getJobBean;
	private GetZhaopinBean getZhaopin;
	private StarCompanyBean starCompanyBean;
	private StarStudentBean starStudentBean;
	
	private int TYPE_ONE = 0;
	private int type_two = 1;
	private int type_three = 2;
	private int type_fore = 3;
	private int type_five = 4;
	
	private Context mContext;
	
	public OneFragmentAdapter(Context mContext, GetJobBean getJobBean, GetZhaopinBean getZhaopin, StarCompanyBean starCompanyBean, StarStudentBean starStudentBean) {
		this.getJobBean = getJobBean;
		this.getZhaopin = getZhaopin;
		this.starCompanyBean = starCompanyBean;
		this.starStudentBean = starStudentBean;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getJobBean.getList().size()+starCompanyBean.getList().size()+starStudentBean.getList().size()+3;
	}
	
	@Override
	public int getItemViewType(int position) {
		if(position == 0){
			
			return TYPE_ONE;
			
		}else if(position<getJobBean.getList().size()+1){
			
			return type_two;
			
		}else if(position==getJobBean.getList().size()+1){
			
			return type_three;
			
		}else if(position==getJobBean.getList().size()+2){
			
			return type_fore;
			
		}else if(position<getJobBean.getList().size()+starCompanyBean.getList().size()+2){
			
			return type_two;
			
		}else if(position==getJobBean.getList().size()+starCompanyBean.getList().size()+2){
			return type_five;
		}else if(position<getJobBean.getList().size()+starCompanyBean.getList().size()+starStudentBean.getList().size()+3){
			
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
