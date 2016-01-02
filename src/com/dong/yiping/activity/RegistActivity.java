package com.dong.yiping.activity;

import java.util.HashMap;
import java.util.Map;

import com.dong.yiping.Constant;
import com.dong.yiping.R;
import com.dong.yiping.bean.UserBean;
import com.dong.yiping.utils.FormatUtils;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RegistActivity extends BaseActivity {

	private TextView tv_title_center;
	private ImageView iv_title_left;
	private ImageView iv_title_right;

	private RelativeLayout rl_regist_stu;
	private RelativeLayout rl_regist_qiye;
	private ImageView iv_regist_stu;
	private ImageView iv_regist_qiye;

	private TextView tv_regist_type;// 企业名、用户名
	private EditText et_regist_typename;
	private EditText et_regist_card_num;
	private EditText et_regist_name;
	
	private EditText et_regist_pwd;
	private EditText et_regist_pwd_confirm;
	private EditText et_regist_phonenum;
	private EditText et_regist_email;
	private EditText et_regist_address;
	private EditText et_regist_stunum;

	private LinearLayout ll_regist_school;
	private LinearLayout ll_regist_address;
	private LinearLayout ll_regist_stunum;
	
	private TextView tv_regist_regist_confirm;

	private TextView tv_regist_sheng;
	private TextView tv_regist_shi;
	private TextView tv_regist_qu;
	private RelativeLayout rl_regist_sheng;
	private RelativeLayout rl_regist_shi;
	private RelativeLayout rl_regist_qu;

	private boolean isStu = true;
	private Context mContext;
	private String typeName;
	private String name;
	private String cardNum;
	private String pwd;
	private String pwdComfirm;
	private String phoneNum;
	private String email;
	private String address;
	private String stuNum;
	private String sheng = "北京";
	private String shi = "北京";
	private String qu = "北京";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 当前页面从右往左进入
		overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
		mContext = this;
		setContentView(R.layout.activity_regist);

		initTitleBar();
		initView();
	}

	private void initTitleBar() {
		
		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);
		iv_title_right = $(R.id.iv_title_right, true);

		tv_title_center.setText("注册");

	}

	private void initView() {
		rl_regist_stu = $(R.id.rl_regist_stu, true);
		rl_regist_qiye = $(R.id.rl_regist_qiye, true);
		iv_regist_stu = $(R.id.iv_regist_stu);
		iv_regist_qiye = $(R.id.iv_regist_qiye);

		tv_regist_type = $(R.id.tv_regist_type);
		et_regist_typename = $(R.id.et_regist_typename);
		et_regist_card_num = $(R.id.et_regist_card_num);
		et_regist_name = $(R.id.et_regist_name);
		
		et_regist_pwd = $(R.id.et_regist_pwd);
		et_regist_pwd_confirm = $(R.id.et_regist_pwd_confirm);
		et_regist_phonenum = $(R.id.et_regist_phonenum);
		et_regist_email = $(R.id.et_regist_email);
		et_regist_address = $(R.id.et_regist_address);
		et_regist_stunum = $(R.id.et_regist_stunum);

		ll_regist_school = $(R.id.ll_regist_school);
		ll_regist_address = $(R.id.ll_regist_address);
		ll_regist_stunum = $(R.id.ll_regist_stunum);
		
		tv_regist_regist_confirm = $(R.id.tv_regist_regist_confirm, true);

		tv_regist_sheng = $(R.id.tv_regist_sheng);
		tv_regist_shi = $(R.id.tv_regist_shi);
		tv_regist_qu = $(R.id.tv_regist_qu);
		rl_regist_sheng = $(R.id.rl_regist_sheng, true);
		rl_regist_shi = $(R.id.rl_regist_shi, true);
		rl_regist_qu = $(R.id.rl_regist_qu, true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_regist_stu:
		case R.id.rl_regist_qiye:
			if (isStu) {
				iv_regist_stu
						.setImageResource(R.drawable.common_checkbox_unselect);
				iv_regist_qiye
						.setImageResource(R.drawable.common_checkbox_seleted);
				isStu = false;
				tv_regist_type.setText("企业名称");
				ll_regist_address.setVisibility(View.VISIBLE);
				ll_regist_school.setVisibility(View.GONE);
				tv_regist_regist_confirm.setBackgroundResource(R.drawable.index_title_bg);
				ll_regist_stunum.setVisibility(View.GONE);
			} else {
				iv_regist_stu
						.setImageResource(R.drawable.common_checkbox_seleted);
				iv_regist_qiye
						.setImageResource(R.drawable.common_checkbox_unselect);
				isStu = true;
				tv_regist_type.setText("用户名");
				ll_regist_address.setVisibility(View.GONE);
				ll_regist_school.setVisibility(View.VISIBLE);
				tv_regist_regist_confirm.setBackgroundResource(R.drawable.resume_btn);
				ll_regist_stunum.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.iv_title_left:
			startToActivity(LoginActivity.class);
			this.finish();
			// 当前页面向右退出
			overridePendingTransition(R.anim.left_to_center,
					R.anim.center_to_right);
			break;

		case R.id.iv_title_right:
			// 设置
			// TODO
			break;

		case R.id.tv_regist_regist_confirm:
			// 确认注册
			// 获取注册信息
			regist_check();
			break;
			
		case R.id.rl_regist_sheng:
			//选择省
			// TODO
			break;
			
		case R.id.rl_regist_shi:
			//选择市
			// TODO
			break;
		case R.id.rl_regist_qu:
			//选择区
			// TODO
			break;
		}
	}

	// 获取注册信息
	private void regist_check() {
		typeName = et_regist_typename.getText().toString().trim();
		name = et_regist_name.getText().toString().trim();
		cardNum = et_regist_card_num.getText().toString().trim();
		pwd = et_regist_pwd.getText().toString().trim();
		pwdComfirm = et_regist_pwd_confirm.getText().toString().trim();
		phoneNum = et_regist_phonenum.getText().toString().trim();
		email = et_regist_email.getText().toString().trim();
		address = et_regist_address.getText().toString().trim();
		stuNum = et_regist_stunum.getText().toString().trim();
		if (isStu && TextUtils.isEmpty(typeName)) {
			ToastUtil.showToast(this, "用户名不能为空");
			return;
		}else if (TextUtils.isEmpty(name)) {
			ToastUtil.showToast(this, "姓名不能为空");
			return;
		} else if (TextUtils.isEmpty(cardNum)) {
			ToastUtil.showToast(this, "身份证号不能为空");
			return;
		} else if (TextUtils.isEmpty(pwd)) {
			ToastUtil.showToast(this, "密码不能为空");
			return;
		} else if (TextUtils.isEmpty(pwdComfirm)) {
			ToastUtil.showToast(this, "确认密码不能为空");
			return;
		} else if (!pwd.equals(pwdComfirm)) {
			ToastUtil.showToast(this, "密码和确认密码不一致");
			return;
		} else if (TextUtils.isEmpty(phoneNum)) {
			ToastUtil.showToast(this, "手机号不能为空");
			return;
		} else if (!FormatUtils.isMobileNO(phoneNum)) {
			ToastUtil.showToast(this, "手机号格式不正确");
			return;
		}else if (!TextUtils.isEmpty(email)&&!FormatUtils.isEmail(email)) {
			ToastUtil.showToast(this, "邮箱格式不正确");
			return;
		} else if (!isStu && TextUtils.isEmpty(address)) {
			ToastUtil.showToast(this, "公司地址不能为空");
			return;
		} else if (isStu && TextUtils.isEmpty(sheng)) {
			ToastUtil.showToast(this, "院校地址省不能为空");
			return;
		} else if (isStu && TextUtils.isEmpty(shi)) {
			ToastUtil.showToast(this, "院校地址市不能为空");
			return;
		} else if (isStu && TextUtils.isEmpty(qu)) {
			ToastUtil.showToast(this, "院校地址区不能为空");
			return;
		}

		//regist
		registMethod();
	}
	

	private void registMethod() {
		String url = Constant.HOST+Constant.REGIST;
		Map<String, String> paramMap = new HashMap<String, String>();
		
		if(isStu){
			paramMap.put("username", typeName);
			paramMap.put("type","0");//0 学生 1 企业
		
		}else{
			paramMap.put("type","1");
			paramMap.put("company", typeName);
		}
		paramMap.put("pwd", pwd);
		paramMap.put("name", name);
		paramMap.put("cards", cardNum);
		paramMap.put("tel", phoneNum);
		paramMap.put("email", email);
		paramMap.put("sheng", sheng);
		paramMap.put("shi", shi);
		paramMap.put("quxian", qu);
		paramMap.put("numbers", stuNum);
		
		
		
		
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,paramMap,Constant.TOPER_TYPE_REGIST));
		
	}
	
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.NET_ERROR:
				ToastUtil.showToast(mContext, "注册失败！");
				break;
			case Constant.NET_SUCCESS:
				UserBean bean = (UserBean) msg.obj;
				ToastUtil.showToast(mContext, "注册成功！");
				
				startToActivity(LoginActivity.class);
				
				finish();
				
				  // 当前页面向右退出
                overridePendingTransition(R.anim.left_to_center,
                        R.anim.center_to_right);
				break;
			}
			
		};
	};
}
