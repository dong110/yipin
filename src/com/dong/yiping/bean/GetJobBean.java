package com.dong.yiping.bean;

import java.util.ArrayList;

/**
 * 我要求职对象
 * @author dong
 *
 */
public class GetJobBean {
	
	private ArrayList<GetJob> list;
	
	public class GetJob{
		private String anble;
		private String cards;
		private String createtime;
		private String email;
		private String gscontent;
		private int id;
		private String intention;
		private String lange;
		private String name;
		private String quxian;
		private String resumetime;
		private String sheng;
		private String shi;
		private int status;
		private String subdate;
		private String tel;
		private String updatetime;
		private int userid;
		private String working;
		public String getAnble() {
			return anble;
		}
		public void setAnble(String anble) {
			this.anble = anble;
		}
		public String getCards() {
			return cards;
		}
		public void setCards(String cards) {
			this.cards = cards;
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
		public String getGscontent() {
			return gscontent;
		}
		public void setGscontent(String gscontent) {
			this.gscontent = gscontent;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getIntention() {
			return intention;
		}
		public void setIntention(String intention) {
			this.intention = intention;
		}
		public String getLange() {
			return lange;
		}
		public void setLange(String lange) {
			this.lange = lange;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getQuxian() {
			return quxian;
		}
		public void setQuxian(String quxian) {
			this.quxian = quxian;
		}
		public String getResumetime() {
			return resumetime;
		}
		public void setResumetime(String resumetime) {
			this.resumetime = resumetime;
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
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getSubdate() {
			return subdate;
		}
		public void setSubdate(String subdate) {
			this.subdate = subdate;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getUpdatetime() {
			return updatetime;
		}
		public void setUpdatetime(String updatetime) {
			this.updatetime = updatetime;
		}
		public int getUserid() {
			return userid;
		}
		public void setUserid(int userid) {
			this.userid = userid;
		}
		public String getWorking() {
			return working;
		}
		public void setWorking(String working) {
			this.working = working;
		}
		
	}
	
	private int status;
	private int total;
	public ArrayList<GetJob> getList() {
		return list;
	}
	public void setList(ArrayList<GetJob> list) {
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
