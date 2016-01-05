package com.dong.yiping.banner.bean;

/**
 * Created by Administrator on 2015/8/28 0028.
 */
public class BaseBannerBean {
    private String url;
    private String path;
    
    public  BaseBannerBean(String url,String path){
        this.url =  url;
        this.path = path;
    }
    public  BaseBannerBean(){

    }

    public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
