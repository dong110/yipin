package com.dong.yiping.bean;

import java.util.ArrayList;

public class DictListBean {

	private ArrayList<DictBean> list;
	
	public class DictBean{
         private String code;//"QZYC",
         private String name;// "求职意向"
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	private int status;
	private int total;
	public ArrayList<DictBean> getList() {
		return list;
	}
	public void setList(ArrayList<DictBean> list) {
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
