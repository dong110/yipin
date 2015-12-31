package com.dong.yiping.activity;

import com.dong.yiping.R;
import com.dong.yiping.utils.ToastUtil;

import android.os.Bundle;
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
	
	private TextView tv_regist_type;//企业名、用户名
	private EditText et_regist_typename;
	private EditText et_regist_card_num;
	
	private EditText et_regist_pwd;
	private EditText et_regist_pwd_confirm;
	private EditText et_regist_phonenum;
	private EditText et_regist_email;
	private EditText et_regist_address;
	private EditText et_regist_stunum;
	
	private LinearLayout ll_regist_school;
	private LinearLayout ll_regist_address;
	
	private TextView tv_regist_regist_confirm;
	
	private boolean isStu = true;

	private String typeName;
	private String cardNum;
	private String pwd;
	private String pwdComfirm;
	private String phoneNum;
	private String email;
	private String address;
	private String stuNum;
	
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
		iv_title_left = $(R.id.iv_title_left,true);
		iv_title_right = $(R.id.iv_title_right,true);
		
		tv_title_center.setText("注册");
		
	}
	
	private void initView() {
		rl_regist_stu = $(R.id.rl_regist_stu,true);
		rl_regist_qiye = $(R.id.rl_regist_qiye,true);
		iv_regist_stu = $(R.id.iv_regist_stu);
		iv_regist_qiye = $(R.id.iv_regist_qiye);
		
		tv_regist_type = $(R.id.tv_regist_type);
		et_regist_typename = $(R.id.et_regist_typename);
		et_regist_card_num = $(R.id.et_regist_card_num);
		
		et_regist_pwd = $(R.id.et_regist_pwd);
		et_regist_pwd_confirm = $(R.id.et_regist_pwd_confirm);
		et_regist_phonenum = $(R.id.et_regist_phonenum);
		et_regist_email = $(R.id.et_regist_email);
		et_regist_address = $(R.id.et_regist_address);
		et_regist_stunum = $(R.id.et_regist_stunum);
		
		ll_regist_school = $(R.id.ll_regist_school);
		ll_regist_address = $(R.id.ll_regist_address);
		
		tv_regist_regist_confirm = $(R.id.tv_regist_regist_confirm,true);
	}

	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.rl_regist_stu:
		case R.id.rl_regist_qiye:
			if(isStu){
				iv_regist_stu.setImageResource(R.drawable.common_checkbox_unselect);
				iv_regist_qiye.setImageResource(R.drawable.common_checkbox_seleted);
				isStu = false;
				tv_regist_type.setText("企业名称");
				ll_regist_address.setVisibility(View.VISIBLE);
				ll_regist_school.setVisibility(View.GONE);
			}else{
				iv_regist_stu.setImageResource(R.drawable.common_checkbox_seleted);
				iv_regist_qiye.setImageResource(R.drawable.common_checkbox_unselect);
				isStu = true;
				tv_regist_type.setText("用户名");
				ll_regist_address.setVisibility(View.GONE);
				ll_regist_school.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.iv_title_left:
			startToActivity(LoginActivity.class);
			this.finish();
			break;
			
		case R.id.iv_title_right:
			//设置
			//TODO
			break;
			
		case R.id.tv_regist_regist_confirm:
			//确认注册
			//获取注册信息
			regist_check();
			break;
		}
	}
	
	
	//获取注册信息
    private void regist_check() {
        typeName = et_regist_typename.getText().toString().trim();
        cardNum = et_regist_card_num.getText().toString().trim();
        pwd = et_regist_pwd.getText().toString().trim();
        pwdComfirm = et_regist_pwd_confirm.getText().toString().trim();
        phoneNum = et_regist_phonenum.getText().toString().trim();
        email = et_regist_email.getText().toString().trim();
        address = et_regist_address.getText().toString().trim();
        stuNum = et_regist_stunum.getText().toString().trim();
        if (TextUtils.isEmpty(typeName)) {
            ToastUtil.showToast(this, "用户名不能为空");
        } else if(TextUtils.isEmpty(cardNum)) {
            ToastUtil.showToast(this, "身份证号不能为空");
        } 


    }
}
