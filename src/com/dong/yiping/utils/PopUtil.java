package com.dong.yiping.utils;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dong.yiping.R;
import com.dong.yiping.adapter.PopListViewAdapter;

public class PopUtil {
	
	private Context mContext;
	private PopupWindow dg;
	
	public  PopUtil(Context mContext){
		this.mContext = mContext;
	}
	
	public void createPop(List<String> listStr){
		PopListViewAdapter adapter = new PopListViewAdapter(mContext,listStr);
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
		View view = mLayoutInflater.inflate(R.layout.pop_dialog, null);
		ListView listview = (ListView) view.findViewById(R.id.pop_listview);
		listview.setAdapter(adapter);
		dg = new PopupWindow(view, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
		dg.setBackgroundDrawable(new BitmapDrawable());
		dg.setFocusable(true);
		dg.showAtLocation(view.getRootView(), Gravity.CENTER, 0,0);
	}
	
	public void hidePop(){
		if(dg!=null && dg.isShowing()){
			dg.dismiss();
			dg = null;
		}
	}
	
}
