package com.dong.yiping.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.dong.yiping.Constant;
import com.dong.yiping.bean.BannerListBean;
import com.dong.yiping.bean.JobDetailInfo;
import com.dong.yiping.bean.DictListBean;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.GetZhaopinBean;
import com.dong.yiping.bean.HangYeBean;
import com.dong.yiping.bean.JobDetailBean;
import com.dong.yiping.bean.StarCompanyBean;
import com.dong.yiping.bean.StarStudentBean;
import com.dong.yiping.bean.UserBean;
import com.google.gson.Gson;

import android.os.Handler;
import android.os.Message;

/**
 * 解析Json工具类
 * 
 * @author dong
 * 
 */
public class ToperJson {

	private Handler handler;
	private Gson gson;

	public ToperJson(Handler handler) {
		this.handler = handler;
		gson = new Gson();
	}

	public void toperStart(int type, String result) {
		switch (type) {
		case Constant.TOPER_TYPE_LOGIN:
			toperLoginJson(result);
			break;

		case Constant.TOPER_TYPE_BANNERLIST:
			toperBannerList(result);

			break;
		case Constant.TOPER_TYPE_GETJOB:
			toperGetJob(result);
			break;
		case Constant.TOPER_TYPE_GETZHAOPIN:
			toperGetZhaopin(result);
			break;
		case Constant.TOPER_TYPE_STARCOM:
			toperStarCompany(result);
			break;
		case Constant.TOPER_TYPE_STARTSTU:
			toperStarStudent(result);
			break;
		case Constant.TOPER_TYPE_REGIST:
			LogUtil.i("注册成功返回的数据==", result);
			toperRegist(result);
			break;

		case Constant.TOPER_TYPE_MODIFYPWD:
			LogUtil.i("修改密码返回的数据==", result);
			toperModifyPwd(result);
			break;
		case Constant.TOPER_TYPE_GETDICT:
			toperDictList(result);
			break;
		case Constant.TOPER_TYPE_GETHANGYE:
			toperGetHangYe(result);
			break;
		case Constant.TOPER_TYPE_APPLYJOB:// 申请职位
			toperApplyJob(result);
			break;

		case Constant.TOPER_TYPE_COMPANYINFO:// 公司信息
			toperCompanyInfo(result);
			break;

		case Constant.TOPER_TYPE_GETJOB_DETAIL:
			toperGetJobDetail(result);
			break;
		case Constant.TOPER_TYPE_GET_USERINFO:
			toperGetUserInfo(result);
			break;
		}
	}


		

	// 申请职位
	private void toperApplyJob(String result) {
		LogUtil.i("APPLY====RESULT=====", result);
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.optInt("status");

			if (status == 0) {
				handler.sendEmptyMessage(Constant.APPLYJOB_SUCCESS);
			} else {
				handler.sendEmptyMessage(Constant.APPLYJOB_FAIL);
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.NET_ERROR);
			e.printStackTrace();
		}

	}

	// 公司信息
	private void toperCompanyInfo(String result) {

		try {
			if (result == null) {
				handler.sendEmptyMessage(Constant.NET_NULL);
			} else {
				JSONObject jsonObject = new JSONObject(result);

				JobDetailInfo companyInfo = gson.fromJson(result,
						JobDetailInfo.class);
				Message msg = handler.obtainMessage();
				msg.obj = companyInfo;
				msg.what = Constant.NET_SUCCESS;
				handler.sendMessage(msg);
			}

		} catch (JSONException e) {
			handler.sendEmptyMessage(Constant.NET_ERROR);
			e.printStackTrace();
		}

	}

		
	/**
	 * jiexi用户详情
	 * @param result
	 */
	private void toperGetUserInfo(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.getInt("status");
			
			if(status==0){
				UserBean bean = gson.fromJson(result, UserBean.class);
				Message msg = handler.obtainMessage();
				msg.what = Constant.HANDLER_TYPE_GET_USERINFO;
				msg.obj = bean;
				handler.sendMessage(msg);
			}else{
				handler.sendEmptyMessage(Constant.HANDLER_TYPE_GET_USERINFO);
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.HANDLER_TYPE_GET_USERINFO);
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 解析工作详情，求职详情
	 * @param result
	 */
	private void toperGetJobDetail(String result) {
		try {
				JSONObject jsonObject = new JSONObject(result);
			
				JobDetailBean bean = gson.fromJson(result, JobDetailBean.class);
				Message msg = handler.obtainMessage();
				msg.obj = bean;
				msg.what = Constant.HANDLER_TYPE_GETJOB_DETAIL;
				handler.sendMessage(msg);
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.HANDLER_TYPE_GETJOB_DETAIL);
			e.printStackTrace();
		}
		
	}
	private void toperGetHangYe(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.optInt("status");

			if (status == 0) {
				HangYeBean hangYeBean = gson.fromJson(result, HangYeBean.class);
				Message msg = handler.obtainMessage();
				msg.obj = hangYeBean;
				msg.what = Constant.HANDLER_TYPE_GETHANGYE;
				handler.sendMessage(msg);
			} else {
				handler.sendEmptyMessage(Constant.HANDLER_TYPE_GETHANGYE);
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.HANDLER_TYPE_GETHANGYE);
			e.printStackTrace();
		}

	}

	/**
	 * 解析数据字典
	 * 
	 * @param result
	 */
	private void toperDictList(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.optInt("status");

			if (status == 0) {
				DictListBean bean = gson.fromJson(result, DictListBean.class);
				Message msg = handler.obtainMessage();
				msg.obj = bean;
				msg.what = Constant.HANDLER_TYPE_GETDICT;
				handler.sendMessage(msg);
			} else {
				handler.sendEmptyMessage(Constant.HANDLER_TYPE_GETDICT);
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.HANDLER_TYPE_GETDICT);
			e.printStackTrace();
		}

	}

	/**
	 * 解析修改密码返回的数据
	 */
	private void toperModifyPwd(String result) {
		try {// {"messages":"修改密码成功","status":0}

			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.optInt("status");

			if (status == 0) {// 修改密码成功
				Message msg = handler.obtainMessage();
				msg.what = Constant.NET_SUCCESS;
				handler.sendMessage(msg);
			} else {
				handler.sendEmptyMessage(Constant.NET_ERROR);
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.NET_ERROR);
			e.printStackTrace();
		}

	}

	/**
	 * 解析注册返回的数据
	 */
	private void toperRegist(String result) {
		try {// {"messages":"注册成功","status":0}

			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.optInt("status");

			if (status == 0) {// 注册成功
				Message msg = handler.obtainMessage();
				msg.what = Constant.NET_SUCCESS;
				handler.sendMessage(msg);
			} else {
				handler.sendEmptyMessage(Constant.NET_ERROR);
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.NET_ERROR);
			e.printStackTrace();
		}

	}

	/**
	 * 解析明星学生
	 * 
	 * @param result
	 */
	private void toperStarStudent(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.getInt("status");
			if (status == 0) {
				StarStudentBean bean = gson.fromJson(result,
						StarStudentBean.class);
				Message msg = handler.obtainMessage();
				msg.obj = bean;
				msg.what = Constant.HANDLER_TYPE_STARTSTU;
				handler.sendMessage(msg);
			}
			if (status == 1) {
				handler.sendEmptyMessage(Constant.HANDLER_TYPE_STARTSTU);
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.HANDLER_TYPE_STARTSTU);
			e.printStackTrace();

		}

	}

	/**
	 * 解析明星企业
	 * 
	 * @param result
	 */
	private void toperStarCompany(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.getInt("status");
			if (status == 0) {
				StarCompanyBean bean = gson.fromJson(result,
						StarCompanyBean.class);
				Message msg = handler.obtainMessage();
				msg.obj = bean;
				msg.what = Constant.HANDLER_TYPE_STARCOM;
				handler.sendMessage(msg);
			}
			if (status == 1) {
				handler.sendEmptyMessage(Constant.HANDLER_TYPE_STARCOM);
			}

		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.HANDLER_TYPE_STARCOM);

			e.printStackTrace();

		}
	}

	/**
	 * 解析我要招聘
	 * 
	 * @param result
	 */
	private void toperGetZhaopin(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.getInt("status");
			if (status == 0) {
				GetZhaopinBean bean = gson.fromJson(result,
						GetZhaopinBean.class);
				Message msg = handler.obtainMessage();
				msg.obj = bean;
				msg.what = Constant.HANDLER_TYPE_GETZHAOPIN;
				handler.sendMessage(msg);
			}
			if (status == 1) {
				handler.sendEmptyMessage(Constant.HANDLER_TYPE_GETZHAOPIN);
			}

		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.HANDLER_TYPE_GETZHAOPIN);

			e.printStackTrace();

		}

	}

	/**
	 * 解析我要求职
	 * 
	 * @param result
	 */
	private void toperGetJob(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.getInt("status");
			if (status == 0) {
				GetJobBean bean = gson.fromJson(result, GetJobBean.class);
				Message msg = handler.obtainMessage();
				msg.obj = bean;
				msg.what = Constant.HANDLER_TYPE_GETJOB;
				handler.sendMessage(msg);
			}
			if (status == 1) {
				handler.sendEmptyMessage(Constant.HANDLER_TYPE_GETJOB);
			}

		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.HANDLER_TYPE_GETJOB);

			e.printStackTrace();

		}
	}

	/**
	 * 解析轮播图
	 * 
	 * @param result
	 */
	private void toperBannerList(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.getInt("status");
			if (status == 0) {
				BannerListBean bean = gson.fromJson(result,
						BannerListBean.class);
				Message msg = handler.obtainMessage();
				msg.obj = bean;
				msg.what = Constant.HANDLER_BANNERLIST;
				handler.sendMessage(msg);
			}
			if (status == 1) {
				handler.sendEmptyMessage(Constant.HANDLER_BANNERLIST);
			}

		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.HANDLER_BANNERLIST);

			e.printStackTrace();

		}
	}

	/**
	 * 解析登录结构Json
	 * 
	 * @param result
	 */
	private void toperLoginJson(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int status = jsonObject.getInt("status");

			if (status == 0) {
				UserBean bean = gson.fromJson(result, UserBean.class);
				Message msg = handler.obtainMessage();
				msg.what = Constant.NET_SUCCESS;
				msg.obj = bean;
				handler.sendMessage(msg);
			} else {
				handler.sendEmptyMessage(Constant.NET_ERROR);
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(Constant.NET_ERROR);
			e.printStackTrace();
		}

	}

}
