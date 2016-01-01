package com.dong.yiping.bean;

import java.util.ArrayList;

public class StarCompanyBean {

	private ArrayList<StarCom> list;
	
	public class StarCom{
		private String birthday;
		private String cards;
		private String conpany;
		private String content;
		private String createtime;
		private String email;
        private int id;
        private String industry;
        private String name;
        private String numbers;
        private String quxian;
        private int recommend;
        private String sheng;
        private String shi;
        private String tel;
        private int telstatus;
        private String updatetime;
		public String getBirthday() {
			return birthday;
		}
		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
		public String getCards() {
			return cards;
		}
		public void setCards(String cards) {
			this.cards = cards;
		}
		public String getConpany() {
			return conpany;
		}
		public void setConpany(String conpany) {
			this.conpany = conpany;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getCreatetime() {
			return createtime;
		}
		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getIndustry() {
			return industry;
		}
		public void setIndustry(String industry) {
			this.industry = industry;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNumbers() {
			return numbers;
		}
		public void setNumbers(String numbers) {
			this.numbers = numbers;
		}
		public String getQuxian() {
			return quxian;
		}
		public void setQuxian(String quxian) {
			this.quxian = quxian;
		}
		public int getRecommend() {
			return recommend;
		}
		public void setRecommend(int recommend) {
			this.recommend = recommend;
		}
		public String getSheng() {
			return sheng;
		}
		public void setSheng(String sheng) {
			this.sheng = sheng;
		}
		public String getShi() {
			return shi;
		}
		public void setShi(String shi) {
			this.shi = shi;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public int getTelstatus() {
			return telstatus;
		}
		public void setTelstatus(int telstatus) {
			this.telstatus = telstatus;
		}
		public String getUpdatetime() {
			return updatetime;
		}
		public void setUpdatetime(String updatetime) {
			this.updatetime = updatetime;
		}
        
        
	}
	
	private int status;
	private int total;
	public ArrayList<StarCom> getList() {
		return list;
	}
	public void setList(ArrayList<StarCom> list) {
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
