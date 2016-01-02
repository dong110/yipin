package com.dong.yiping.bean;

import java.util.ArrayList;

public class HangYeBean {
	//{"list":[{"code":"HANGYE_1","name":"商业"}],"status":0,"total":1}
	
	
	private ArrayList<HangYe> list;
	public class HangYe{
		private String code;
		private String name;
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
	public ArrayList<HangYe> getList() {
		return list;
	}
	public void setList(ArrayList<HangYe> list) {
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
