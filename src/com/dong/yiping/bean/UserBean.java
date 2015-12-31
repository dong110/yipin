package com.dong.yiping.bean;

import java.util.ArrayList;

public class UserBean {
	private String createtime;
	private int id;
	private ArrayList<Pic> pic;
	
	private class Pic{
		private int id;
		private int isdefault;
		private String original;
		private int picId;
		private String thumbnail;
		private int userid;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getIsdefault() {
			return isdefault;
		}
		public void setIsdefault(int isdefault) {
			this.isdefault = isdefault;
		}
		public String getOriginal() {
			return original;
		}
		public void setOriginal(String original) {
			this.original = original;
		}
		public int getPicId() {
			return picId;
		}
		public void setPicId(int picId) {
			this.picId = picId;
		}
		public String getThumbnail() {
			return thumbnail;
		}
		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}
		public int getUserid() {
			return userid;
		}
		public void setUserid(int userid) {
			this.userid = userid;
		}
		
	}
	
	private String pwd;
	private int status;
	private int type;
	private String updatetime;
	private UserInfo userInfo;
	
	private class UserInfo{
		private String birthday;
		private String cards;
		private String conpany;
		private String content;
		private String createtime;
		private String email;
		private int id;
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
		
	};
	
	private String userRoleList;
	private String username;
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<Pic> getPic() {
		return pic;
	}
	public void setPic(ArrayList<Pic> pic) {
		this.pic = pic;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getUserRoleList() {
		return userRoleList;
	}
	public void setUserRoleList(String userRoleList) {
		this.userRoleList = userRoleList;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
