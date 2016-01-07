package com.dong.yiping;

public class Constant {

	public static final int NET_ERROR = 0;
	public static final int NET_SUCCESS = 1;
	public static final int NET_NULL = 2;
	
	public static final int TOPER_TYPE_LOGIN = 100;
	public static final int TOPER_TYPE_BANNERLIST = 101;
	public static final int TOPER_TYPE_GETJOB = 102;
	public static final int TOPER_TYPE_GETZHAOPIN = 103;
	public static final int TOPER_TYPE_STARCOM = 104;
	public static final int TOPER_TYPE_STARTSTU = 105;
	public static final int TOPER_TYPE_REGIST = 106;
	public static final int TOPER_TYPE_FINDPWD = 107;
	public static final int TOPER_TYPE_MODIFYPWD = 108;
	public static final int TOPER_TYPE_GETDICT = 109;
	public static final int TOPER_TYPE_GETHANGYE = 110;
	public static final int TOPER_TYPE_GETJOB_DETAIL = 111;
	public static final int TOPER_TYPE_GET_USERINFO = 112;
	public static final int TOPER_TYPE_COMPANYINFO = 113;
	public static final int TOPER_TYPE_APPLYJOB = 114;
	public static final int TOPER_TYPE_GET_SHENG = 115;
	public static final int TOPER_TYPE_GET_RESUME_LIST = 116;
	public static final int TOPER_TYPE_COLLECTJOB = 117;
	public static final int TOPER_TYPE_ISUSERNAME = 118;
	public static final int TOPER_TYPE_UPDATE_RESUME = 119;
	public static final int TOPER_TYPE_SAVE_USERINFO = 10;
	public static final int TOPER_TYPE_SAVE_USERICON= 11;
	
	public static final int HANDLER_BANNERLIST = 120;
	public static final int HANDLER_TYPE_GETJOB = 121;
	public static final int HANDLER_TYPE_GETZHAOPIN = 122;
	public static final int HANDLER_TYPE_STARCOM = 123;
	public static final int HANDLER_TYPE_STARTSTU = 124;
	public static final int HANDLER_TYPE_GETDICT = 125;
	public static final int HANDLER_TYPE_GETHANGYE = 126;
	public static final int APPLYJOB_SUCCESS = 127;
	public static final int APPLYJOB_FAIL = 128;
	public static final int HANDLER_TYPE_GETJOB_DETAIL = 129;
	public static final int HANDLER_TYPE_GET_USERINFO = 131;
	public static final int HANDLER_TYPE_GET_SHENG = 132;
	public static final int HANDLER_REFESH_FINISH = 130;
	public static final int HANDLER_RESUME_LIST = 133;
	public static final int HANDLER_COLLECTJOB = 134;
	public static final int HANDLER_TYPE_ISUSERNAME = 135;
	public static final int HANDLER_TYPE_UPDATE_RESUME = 136;
	public static final int HANDLER_TYPE_SAVE_USERINFO = 137;
	public static final int HANDLER_TYPE_SAVE_USERICON= 138;
	
	public static final String HOST = "http://123.57.75.34:8082/users/api";
	
	public static final String LOGIN = "/userLogin";//登录
	public static final String REGIST = "/register";//注册
	public static final String IS_USERNAME = "/isUserName?username=";//判断用户名是否有重复
	public static final String FINDPWD = "/";//找回密码
	public static final String MODIFYPWD = "/userPwdUpdate";//修改密码
	public static final String PHONEINDENTIFICATION = "/";//手机号验证
	
	public static final String GET_JOB = "/recruitList?status=1&currentNum=0&pageNum=5";//我要求职
	public static final String GET_ZHAOPING = "/resumeList?status=1&currentNum=0&pageNum=5";//我要招聘
	public static final String BANNERLIST = "/bannerList";//轮播图
	public static final String STAR_COMPANY = "/companyUserList?recommend=1&currentNum=0&pageNum=5";//明星企业
	public static final String STAR_STUDENT = "/studentUserList?recommend=1&currentNum=0&pageNum=5";//明星学生
	public static final String GET_ALL_ZHAOPING = "/recruitList?status=1&currentNum=0&pageNum=5";//所有招聘
	
	public static final String GET_dictList = "/dictList?type=";//获取字典列表
	public static final String GET_DICT_INFO = "/dictList?type=1&code=";
	
	public static final String GET_Resume_List = "/resumeList?userid=";//简历列表
	
	public static final String USER_INFO = "/userInfo";//个人详细信息
	public static final String GET_JOB_DETAIL = "/resumeSimple?id=";//求职详情
	
	public static final String COMPANY_INFO = "/recruitSimple?id=";
	public static final String APPLYJOB = "/noticeUpdate";//申请接口
	public static final String COLLECT = "/collectionUpdate";//收藏接口
	public static final String UPLOAD_IMG = "/imageUpload";//上传图片
	public static final String UPLOAD_USER_IMG = "/infoPicUpdate";//保存用户头像
	public static final String UPDATE_RESUME = "/resumeUpdate";//修改简历
	public static final String UPLOAD_FILE = "http://123.57.75.34:8080/imageUpload";
	public static final String UPDATE_UNSER_INFO = "/userInfoUpdate";//跟新用户信息
}
