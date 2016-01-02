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
		
		LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
		View view = mLayoutInflater.inflate(R.layout.pop_dialog, null);
		view.setBackgroundColor(Color.parseColor("#60000000"));
		dg = new PopupWindow(view, LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,true);
		
		/*int popWinWidth = MainActivity.this.getWindowManager()
				.getDefaultDisplay().getWidth();
		int popWinHeight = MainActivity.this.getWindowManager()
				.getDefaultDisplay().getHeight();*/
		dg.setWidth(DisplayUtils.getScreenW(mContext));
		dg.setHeight(DisplayUtils.getScreenH(mContext));
		dg.setBackgroundDrawable(new BitmapDrawable());
		dg.setFocusable(true);
		dg.showAtLocation(view.getRootView(), Gravity.CENTER, 0,0);
		ListView listview = (ListView) view.findViewById(R.id.pop_listview);
		PopListViewAdapter adapter = new PopListViewAdapter(mContext,listStr);
		listview.setAdapter(adapter);
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