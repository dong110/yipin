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

public class ModifyPwdActivity extends BaseActivity {

	private TextView tv_title_center;
	private ImageView iv_title_left;
	private ImageView iv_title_right;

	private EditText et_regist_typename;
	private EditText et_regist_pwd;
	private EditText et_regist_pwd_confirm;

	private TextView tv_regist_regist_confirm;

	private boolean isStu = true;

	private String typeName;
	private String pwd;
	private String pwdComfirm;

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

		tv_title_center.setText("找回密码");

	}

	private void initView() {
		et_regist_typename = $(R.id.et_regist_typename);

		et_regist_pwd = $(R.id.et_regist_pwd);
		et_regist_pwd_confirm = $(R.id.et_regist_pwd_confirm);

		tv_regist_regist_confirm = $(R.id.tv_regist_regist_confirm, true);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			startToActivity(LoginActivity.class);
			this.finish();
			break;

		case R.id.iv_title_right:
			// 设置
			// TODO
			break;

		case R.id.tv_regist_regist_confirm:
			// 找回密码
			findPwdMethod();
			break;

		}
	}

	private void findPwdMethod() {
		typeName = et_regist_typename.getText().toString().trim();
		pwd = et_regist_pwd.getText().toString().trim();
		pwdComfirm = et_regist_pwd_confirm.getText().toString().trim();

		if (TextUtils.isEmpty(typeName)) {
			ToastUtil.showToast(this, "用户名不能为空");
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
		}

		String url = Constant.HOST + Constant.FINDPWD;
		Map<String, String> paramMap = new HashMap<String, String>();
		// userId??
		// TODO
		paramMap.put("userId", "1");
		paramMap.put("pwd", pwd);

		ThreadPoolManager.getInstance().addTask(
				new NetRunnable(mHandler, url, paramMap,
						Constant.TOPER_TYPE_REGIST));

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.NET_ERROR:
				ToastUtil.showToast(mContext, "密码修改失败！");
				break;
			case Constant.NET_SUCCESS:
				ToastUtil.showToast(mContext, "密码修改成功！");

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
