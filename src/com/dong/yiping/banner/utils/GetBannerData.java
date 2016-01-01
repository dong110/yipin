package com.dong.yiping.banner.utils;

import com.dong.yiping.banner.bean.BaseBannerBean;
import com.dong.yiping.bean.BannerListBean;
import com.dong.yiping.bean.BannerListBean.BannerList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/28 0028.
 */
public class GetBannerData {
    public static List<BaseBannerBean> getBannerData(BannerListBean bannerListBean){
        List<BaseBannerBean> list = new ArrayList<BaseBannerBean>();
        for(BannerList banner:bannerListBean.getList()){
        	list.add(new BaseBannerBean(banner.getPic()));
        }
        
        return list;
    }
}
