package com.dong.yiping.bean;

public class OneFragmentJobBean {
	
	private String job;
	private String group;
	private String salary;
	private String area;
	
	public OneFragmentJobBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OneFragmentJobBean(String job, String group, String salary,
			String area) {
		super();
		this.job = job;
		this.group = group;
		this.salary = salary;
		this.area = area;
	}

	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	
}
