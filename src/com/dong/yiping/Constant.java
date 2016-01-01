package com.dong.yiping;

public class Constant {

	public static final int NET_ERROR = 0;
	public static final int NET_SUCCESS = 1;
	
	public static final int TOPER_TYPE_LOGIN = 100;
	public static final int TOPER_TYPE_BANNERLIST = 101;
	public static final int TOPER_TYPE_GETJOB = 102;
	public static final int TOPER_TYPE_GETZHAOPIN = 103;
	public static final int TOPER_TYPE_STARCOM = 104;
	public static final int TOPER_TYPE_STARTSTU = 105;
	public static final int TOPER_TYPE_REGIST = 106;
	public static final int TOPER_TYPE_FINDPWD = 107;
<<<<<<< HEAD
	public static final int TOPER_TYPE_MODIFYPWD = 108;
=======
>>>>>>> origin/master
	
	public static final int HANDLER_BANNERLIST = 120;
	public static final int HANDLER_TYPE_GETJOB = 121;
	public static final int HANDLER_TYPE_GETZHAOPIN = 122;
	public static final int HANDLER_TYPE_STARCOM = 123;
	public static final int HANDLER_TYPE_STARTSTU = 124;
	
	public static final String HOST = "http://123.57.75.34:8080/users/api";
	
	public static final String LOGIN = "/userLogin";//登录
	public static final String REGIST = "/register";//注册
	public static final String FINDPWD = "/";//找回密码
	public static final String MODIFYPWD = "/userPwdUpdate";//修改密码
	
	public static final String GET_JOB = "/resumeList?status=1&currentNum=0&pageNum=5";//我要求职
	public static final String GET_ZHAOPING = "/recruitList?userId=1&status=1&currentNum=0&pageNum=5";//我要招聘
	public static final String BANNERLIST = "/bannerList";//轮播图
	public static final String STAR_COMPANY = "/companyUserList?recommend=1&currentNum=0&pageNum=5";//明星企业
	public static final String STAR_STUDENT = "/studentUserList?recommend=1&currentNum=0&pageNum=5";//明星学生
	public static final String GET_ALL_ZHAOPING = "/recruitList?status=1&currentNum=0&pageNum=5";//所有招聘
}
