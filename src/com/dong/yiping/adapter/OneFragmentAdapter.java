package com.dong.yiping.adapter;

import java.util.List;

import com.dong.yiping.R;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.GetJobBean.GetJob;
import com.dong.yiping.bean.GetZhaopinBean.Zhaopin;
import com.dong.yiping.bean.GetZhaopinBean;
import com.dong.yiping.bean.OneFragmentJobBean;
import com.dong.yiping.bean.StarCompanyBean;
import com.dong.yiping.bean.StarCompanyBean.StarCom;
import com.dong.yiping.bean.StarStudentBean;
import com.dong.yiping.bean.StarStudentBean.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class OneFragmentAdapter extends BaseAdapter{
	private GetJobBean getJobBean;
	private GetZhaopinBean getZhaopin;
	private StarCompanyBean starCompanyBean;
	private StarStudentBean starStudentBean;
	public boolean isGetJob = true;
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
		if(isGetJob){
			return getJobBean.getList().size()+starCompanyBean.getList().size()+starStudentBean.getList().size()+3;
		}else{
			return getZhaopin.getList().size()+starCompanyBean.getList().size()+starStudentBean.getList().size()+3;
		}
		
	}
	
	@Override
	public int getItemViewType(int position) {
		if(isGetJob){
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
		}else{
			if(position == 0){
				
				return TYPE_ONE;
				
			}else if(position<getZhaopin.getList().size()+1){
				
				return type_two;
				
			}else if(position==getZhaopin.getList().size()+1){
				
				return type_three;
				
			}else if(position==getZhaopin.getList().size()+2){
				
				return type_fore;
				
			}else if(position<getZhaopin.getList().size()+starCompanyBean.getList().size()+2){
				
				return type_two;
				
			}else if(position==getZhaopin.getList().size()+starCompanyBean.getList().size()+2){
				return type_five;
			}else if(position<getZhaopin.getList().size()+starCompanyBean.getList().size()+starStudentBean.getList().size()+3){
				
				return type_two;
				
			}
		}
		
		
		return 0;
	}
	
	@Override
	public int getViewTypeCount() {
		return 5;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderTwo viewHolderTwo=null;
		if(convertView == null){
			switch (getItemViewType(position)) {
			case 0:
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragmentone_one, null);
				final Button bt_getJob = (Button) convertView.findViewById(R.id.bt_getJob);
				final Button get_Zhaopin = (Button) convertView.findViewById(R.id.get_Zhaopin);
				bt_getJob.setSelected(true);
				
				bt_getJob.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						get_Zhaopin.setSelected(false);
						bt_getJob.setSelected(true);
						isGetJob = true;
						notifyDataSetChanged();
					}
				});
				
				get_Zhaopin.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						get_Zhaopin.setSelected(true);
						bt_getJob.setSelected(false);
						isGetJob = false;
						notifyDataSetChanged();
					}
				});
				break;
			case 1:
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragmentone_two, null);
				viewHolderTwo = new ViewHolderTwo();
				viewHolderTwo.tv_arrer = (TextView) convertView.findViewById(R.id.tv_arrer);
				viewHolderTwo.tv_cp_name = (TextView) convertView.findViewById(R.id.tv_cp_name);
				viewHolderTwo.tv_group_name = (TextView) convertView.findViewById(R.id.tv_group_name);
				viewHolderTwo.tv_salary = (TextView) convertView.findViewById(R.id.tv_salary);
				convertView.setTag(viewHolderTwo);
				break;
			case 2:
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragmentone_three, null);
				TextView tv_textview = (TextView) convertView.findViewById(R.id.tv_textview);
				if(isGetJob){
					tv_textview.setText("更多求职");
				}else{
					tv_textview.setText("更多招聘");
				}
				
				break;
			case 3:
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragmentone_fore, null);
				break;
			case 4:
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragmentone_five, null);
				break;
			}
		}else{
			switch (getItemViewType(position)) {
			case 0:
				
				break;
			case 1:
				viewHolderTwo = (ViewHolderTwo) convertView.getTag();
				break;
			case 2:
				TextView tv_textview = (TextView) convertView.findViewById(R.id.tv_textview);
				if(isGetJob){
					tv_textview.setText("更多求职");
				}else{
					tv_textview.setText("更多招聘");
				}
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			}
		}
		if(isGetJob){
			//显示内容
			if(position == 0){
			}else if(position<getJobBean.getList().size()+1){
				//我要求职
				GetJob getJob = getJobBean.getList().get(position-1);
				
				viewHolderTwo.tv_cp_name.setVisibility(View.VISIBLE);
				viewHolderTwo.tv_salary.setVisibility(View.VISIBLE);
				
				viewHolderTwo.tv_cp_name.setText(getJob.getName());
				viewHolderTwo.tv_salary.setText(getJob.getSubdate());
				
				viewHolderTwo.tv_group_name.setText(getJob.getIntention());
				viewHolderTwo.tv_arrer.setText(getJob.getQuxian());
				
			}else if(position==getJobBean.getList().size()+1){
			}else if(position==getJobBean.getList().size()+2){
			}else if(position<getJobBean.getList().size()+starCompanyBean.getList().size()+2){
				//明星企业
				StarCom starCom = starCompanyBean.getList().get(position-getJobBean.getList().size()-3);
				
				viewHolderTwo.tv_cp_name.setVisibility(View.GONE);
				viewHolderTwo.tv_salary.setVisibility(View.GONE);
				
				viewHolderTwo.tv_group_name.setText(starCom.getName());
				viewHolderTwo.tv_arrer.setText(starCom.getQuxian());
			}else if(position==getJobBean.getList().size()+starCompanyBean.getList().size()+2){
			}else if(position<getJobBean.getList().size()+starCompanyBean.getList().size()+starStudentBean.getList().size()+3){
				//明星学生
				
				Student student = starStudentBean.getList().get(position-getJobBean.getList().size()-starCompanyBean.getList().size()-3);
				
				viewHolderTwo.tv_cp_name.setVisibility(View.GONE);
				viewHolderTwo.tv_salary.setVisibility(View.GONE);
				
				viewHolderTwo.tv_group_name.setText(student.getName());
				viewHolderTwo.tv_arrer.setText(student.getQuxian());
				
				
			}
		}else{
			//显示内容
			if(position == 0){
			}else if(position<getZhaopin.getList().size()+1){
				//我要招聘
				Zhaopin zhaopin = getZhaopin.getList().get(position-1);
				
				viewHolderTwo.tv_cp_name.setVisibility(View.VISIBLE);
				viewHolderTwo.tv_salary.setVisibility(View.VISIBLE);
				
				viewHolderTwo.tv_cp_name.setText(zhaopin.getJob());
				viewHolderTwo.tv_salary.setText(zhaopin.getWage());
				
				viewHolderTwo.tv_group_name.setText(zhaopin.getConpany());
				viewHolderTwo.tv_arrer.setText(zhaopin.getQuxian());
				
			}else if(position==getZhaopin.getList().size()+1){
			}else if(position==getZhaopin.getList().size()+2){
			}else if(position<getZhaopin.getList().size()+starCompanyBean.getList().size()+2){
				//明星企业
				StarCom starCom = starCompanyBean.getList().get(position-getZhaopin.getList().size()-3);
				
				viewHolderTwo.tv_cp_name.setVisibility(View.GONE);
				viewHolderTwo.tv_salary.setVisibility(View.GONE);
				
				viewHolderTwo.tv_group_name.setText(starCom.getName());
				viewHolderTwo.tv_arrer.setText(starCom.getQuxian());
			}else if(position==getZhaopin.getList().size()+starCompanyBean.getList().size()+2){
			}else if(position<getZhaopin.getList().size()+starCompanyBean.getList().size()+starStudentBean.getList().size()+3){
				//明星学生
				
				Student student = starStudentBean.getList().get(position-getZhaopin.getList().size()-starCompanyBean.getList().size()-3);
				
				viewHolderTwo.tv_cp_name.setVisibility(View.GONE);
				viewHolderTwo.tv_salary.setVisibility(View.GONE);
				
				viewHolderTwo.tv_group_name.setText(student.getName());
				viewHolderTwo.tv_arrer.setText(student.getQuxian());
				
				
			}
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

	
	static class ViewHolderTwo{
		TextView tv_cp_name;
		TextView tv_group_name;
		TextView tv_salary;
		TextView tv_arrer;
	}
	
}
