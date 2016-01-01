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
		private String conpany;
		private String content;
		private String createtime;
		private String gscontent;
	    private int id;
	    private String  job;
	    private String quxian;
	    private String recruittime;
	    private String sheng;
	    private String shi;
	    private int status;
	    private String subdate;
	    private String updatetime;
	    private int userid;
	    private String wage;
	    private String welfare;
	    private String workdate;
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
		public String getJob() {
			return job;
		}
		public void setJob(String job) {
			this.job = job;
		}
		public String getQuxian() {
			return quxian;
		}
		public void setQuxian(String quxian) {
			this.quxian = quxian;
		}
		public String getRecruittime() {
			return recruittime;
		}
		public void setRecruittime(String recruittime) {
			this.recruittime = recruittime;
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
		public String getWage() {
			return wage;
		}
		public void setWage(String wage) {
			this.wage = wage;
		}
		public String getWelfare() {
			return welfare;
		}
		public void setWelfare(String welfare) {
			this.welfare = welfare;
		}
		public String getWorkdate() {
			return workdate;
		}
		public void setWorkdate(String workdate) {
			this.workdate = workdate;
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
