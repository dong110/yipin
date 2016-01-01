package com.dong.yiping.banner;

import com.dong.yiping.bean.BannerListBean;

import android.view.View;

public interface BannerViewBehavior {
	
	public void update(Object object,BannerListBean bannerListBean);
	
	public View getView();
}
