package com.dong.yiping.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dong.yiping.R;

public class LoadingUtil {
	
	private Context mContext;
	private PopupWindow dg;
	private String str;
	public LoadingUtil(Context mContext) {
		this.mContext = mContext;
	}
	
	public void showDialog(){
		createDialog();
	}
	
	public void showDialog(String str){
		this.str = str;
		createDialog();
	}
	
	private void createDialog(){

		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
		View view = mLayoutInflater.inflate(R.layout.dialog, null);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		if(str!=null){
			tv_title.setText(str);
		}
		
		if(dg == null){
			dg = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
		}
		dg.setFocusable(false);
		
		dg.showAtLocation(view.getRootView(), Gravity.CENTER, 0,0);
	}
	
	public void hideDialog(){
		try {
			if(dg !=null && dg.isShowing()){
				dg.dismiss();
				dg =null;
			}
			if(str != null){
				str = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
