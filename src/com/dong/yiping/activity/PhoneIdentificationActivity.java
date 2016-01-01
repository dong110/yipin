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

public class PhoneIdentificationActivity extends BaseActivity {

	private TextView tv_title_center;
	private ImageView iv_title_left;
	private ImageView iv_title_right;

	private EditText et_phone_phone;
	private EditText et_phone_code;

	private TextView tv_phone_submit;
	private TextView tv_phone_getcode;
	
	private String phoneNum;
	private String codeString;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 当前页面从右往左进入
		overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
		mContext = this;
		setContentView(R.layout.activity_phone_identification);

		initTitleBar();
		initView();
	}

	private void initTitleBar() {
		tv_title_center = $(R.id.tv_title_center);
		iv_title_left = $(R.id.iv_title_left, true);
		iv_title_right = $(R.id.iv_title_right, true);

		tv_title_center.setText("手机号认证");

	}

	private void initView() {
		et_phone_code = $(R.id.et_phone_code);
		et_phone_phone = $(R.id.et_phone_phone);

		tv_phone_getcode = $(R.id.tv_phone_getcode, true);
		tv_phone_submit = $(R.id.tv_phone_submit, true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			this.finish();
			// 当前页面向右退出
			overridePendingTransition(R.anim.left_to_center,
					R.anim.center_to_right);
			break;

		case R.id.iv_title_right:
			// 设置
			// TODO
			break;

		case R.id.tv_phone_getcode:
			//获取验证码
			
			getCode();
			break;
		case R.id.tv_phone_submit:
			// 手机号认证提交
			phoneSubmit();
			break;

		}
	}

	private void getCode() {
		
	}

	private void phoneSubmit() {
		phoneNum = et_phone_phone.getText().toString().trim();
		codeString = et_phone_code.getText().toString().trim();

		if (TextUtils.isEmpty(phoneNum)) {
			ToastUtil.showToast(this, "手机号不能为空");
			return;
		} else if (TextUtils.isEmpty(codeString)) {
			ToastUtil.showToast(this, "验证码不能为空");
			return;
		} 

		//提交手机号验证
		String url = Constant.HOST + Constant.PHONEINDENTIFICATION;
		Map<String, String> paramMap = new HashMap<String, String>();
		// TODO
		paramMap.put("userId", "1");
		paramMap.put("pwd", "xx");

		ThreadPoolManager.getInstance().addTask(
				new NetRunnable(mHandler, url, paramMap,
						Constant.TOPER_TYPE_MODIFYPWD));

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.NET_ERROR:
				ToastUtil.showToast(mContext, "手机号验证失败！");
				break;
			case Constant.NET_SUCCESS:
				ToastUtil.showToast(mContext, "手机号验证成功！");

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
