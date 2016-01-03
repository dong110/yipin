package com.dong.yiping.utils;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.KeyEvent.DispatcherState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.dong.yiping.R;
import com.dong.yiping.activity.MainActivity;
import com.dong.yiping.adapter.PopListViewAdapter;

public class PopUtil {
	
	private Context mContext;
	private PopupWindow dg;
	
	public  PopUtil(Context mContext){
		this.mContext = mContext;
	}
	
	public void createPop(List<String> listStr){
		System.out.println(listStr.size()+"====");
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
		View view = mLayoutInflater.inflate(R.layout.pop_dialog, null);
		PopListViewAdapter adapter = new PopListViewAdapter(mContext, listStr);
		ListView listView = (ListView) view.findViewById(R.id.pop_listview);
		listView.setAdapter(adapter);
		
		//自适配长、框设置
		dg = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		dg.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg));
		dg.setOutsideTouchable(true);
		dg.setAnimationStyle(android.R.style.Animation_Dialog);
		dg.update();
		dg.setTouchable(true);
		dg.setFocusable(true);
		dg.showAtLocation(view, Gravity.CENTER, 0,0);
	}
	public void createDialogTwo(List<String> listStr){
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
		View view = mLayoutInflater.inflate(R.layout.dialog, null);
		final PopupWindow dg = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		dg.setWidth(DisplayUtils.getScreenW(mContext));
		dg.setHeight(DisplayUtils.getScreenH(mContext));

		dg.setFocusable(true);
		//setPopWindow();
		try {
			dg.showAtLocation(view.getRootView(), Gravity.CENTER_HORIZONTAL, 0,
					0);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		ListView list_diglo = (ListView) view.findViewById(R.id.pop_listview);
		PopListViewAdapter adapter = new PopListViewAdapter(mContext,listStr);
		list_diglo.setAdapter(adapter);
		
	}
	public void hidePop(){
		if(dg!=null && dg.isShowing()){
			dg.dismiss();
			dg = null;
		}
	}
	
}
