package com.dong.yiping.bean;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BannerListBean implements Serializable{
	
	private static final long serialVersionUID = 2125923916220553986L;
	
	private ArrayList<BannerList> list;
	
	public class BannerList implements Serializable{
		private static final long serialVersionUID = -8430926184130665344L;
		private int id;
		private String pic;
		private String url;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getPic() {
			return pic;
		}
		public void setPic(String pic) {
			this.pic = pic;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
	}
	
	private int status;
	private int total;
	
	public ArrayList<BannerList> getList() {
		return list;
	}
	public void setList(ArrayList<BannerList> list) {
		this.list = list;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}
