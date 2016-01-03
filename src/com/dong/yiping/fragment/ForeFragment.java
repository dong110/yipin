package com.dong.yiping.fragment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import roboguice.inject.InjectView;

import com.dong.yiping.R;
import com.dong.yiping.activity.ComCollectListActivity;
import com.dong.yiping.activity.CompanyInfoActivity;
import com.dong.yiping.activity.ModifyPwdActivity;
import com.dong.yiping.activity.MyResumesActivity;
import com.dong.yiping.activity.PhoneIdentificationActivity;
import com.dong.yiping.activity.ResumeActivity;
import com.dong.yiping.activity.UserCollectListActivity;
import com.dong.yiping.activity.UserHistoryActivity;
import com.dong.yiping.activity.UserShenQingActivity;
import com.dong.yiping.utils.LogUtil;
import com.dong.yiping.utils.SPUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ForeFragment extends BaseFragment implements OnClickListener {

	@InjectView(R.id.myResume)
	TextView myResume;
	@InjectView(R.id.tv_fragment_jilu)
	TextView tv_fragment_jilu;
	@InjectView(R.id.tv_collection)
	TextView tv_collection;
	@InjectView(R.id.tv_mine_modifypwd)
	TextView modify_pwd;
	@InjectView(R.id.tv_mine_phone)
	TextView phone;
	@InjectView(R.id.tv_fragmentfore_username)
	TextView tv_fragmentfore_username;
	@InjectView(R.id.tv_history)
	TextView tv_history;
	@InjectView(R.id.iv_fragmentfore_icon)
	ImageView iv_fragmentfore_icon;

	private TextView tv_title_center;
	private LinearLayout ll_title_center;
	private Intent mIntent;
	private Context mContext;
	private int Type;
	private String username;

	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_fore, null);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		initView();
		setListener();
		initData();
	}

	private void setListener() {
		tv_collection.setOnClickListener(this);
		myResume.setOnClickListener(this);
		modify_pwd.setOnClickListener(this);
		phone.setOnClickListener(this);
		tv_fragment_jilu.setOnClickListener(this);
		tv_history.setOnClickListener(this);

		iv_fragmentfore_icon.setOnClickListener(this);
	}

	private void initData() {

	}

	private void initView() {
		initImageSelect();
		username = SPUtil.getString(mContext, "username", "");
		tv_fragmentfore_username.setText(username);
		LogUtil.i("username====", username + "");
		Type = SPUtil.getInt(mContext, "type", 0);
		LogUtil.i("type====", Type + "");
		if (Type == 0) {// 学生用户
			myResume.setText("我的简历");
			tv_fragment_jilu.setText("职位申请记录");

		} else {// 公司用户
			myResume.setText("公司信息");
			tv_fragment_jilu.setText("面试邀请记录");

		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.myResume:
			if (Type == 0) {// 学生用户
				mIntent = new Intent(mContext, MyResumesActivity.class);

			} else {// 公司用户
				mIntent = new Intent(mContext, CompanyInfoActivity.class);
			}

			mContext.startActivity(mIntent);
			break;

		case R.id.tv_mine_modifypwd:
			mIntent = new Intent(mContext, ModifyPwdActivity.class);
			mContext.startActivity(mIntent);
			break;

		case R.id.tv_mine_phone:
			mIntent = new Intent(mContext, PhoneIdentificationActivity.class);
			mContext.startActivity(mIntent);
			break;
		case R.id.tv_collection:
			if (Type == 0) {// 学生用户
				mIntent = new Intent(mContext, UserCollectListActivity.class);

			} else {// 公司用户
				mIntent = new Intent(mContext, ComCollectListActivity.class);
			}
			mContext.startActivity(mIntent);
			break;
		case R.id.tv_fragment_jilu:
			if (Type == 0) {// 学生用户
				mIntent = new Intent(mContext, UserShenQingActivity.class);

			} else {// 公司用户
			}
			mContext.startActivity(mIntent);
			break;
		case R.id.tv_history:
			if (Type == 0) {// 学生用户
				mIntent = new Intent(mContext, UserHistoryActivity.class);

			} else {// 公司用户
			}
			mContext.startActivity(mIntent);
			break;

		// 以下是修改头像中的点击事件
		case R.id.iv_fragmentfore_icon:
			  showCameraPopWindow();
			break;
		case R.id.tv_camera:

			mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
					Environment.getExternalStorageDirectory(), "temp.jpg")));
			startActivityForResult(mIntent, PHOTOHRAPH);

			camera_pop_window.dismiss();
			break;

		case R.id.tv_album:
			mIntent = new Intent(Intent.ACTION_PICK, null);
			mIntent.setDataAndType(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					IMAGE_UNSPECIFIED);

			startActivityForResult(mIntent, PHOTOZOOM);

			camera_pop_window.dismiss();

		case R.id.tv_cancel:
			camera_pop_window.dismiss();
			break;
		}

	}

	// ================================以下是修改头像代码============================

	// 修改头像所需参数数据
	// -----选择图片-------
	private PopupWindow camera_pop_window;
	private View camera_pop_view;
	private LayoutInflater mInflater;
	private Context context;
	private TextView tv_camera, tv_album, tv_cancel;

	private void initImageSelect() {
		// -选择图片------
		mInflater = LayoutInflater.from(mContext);
		camera_pop_view = mInflater.inflate(R.layout.camera_option_pop, null);
		tv_camera = (TextView) camera_pop_view.findViewById(R.id.tv_camera);
		tv_album = (TextView) camera_pop_view.findViewById(R.id.tv_album);
		tv_cancel = (TextView) camera_pop_view.findViewById(R.id.tv_cancel);
		tv_camera.setOnClickListener(this);
		tv_album.setOnClickListener(this);
		tv_cancel.setOnClickListener(this);

	}

	// -------------------弹出选择图片对话框--------------------------------

	private void showCameraPopWindow() {
		if (camera_pop_window == null) {
			initCameraPopWindow();
		}
		if (camera_pop_window.isShowing()) {
			camera_pop_window.dismiss();
		} else {
			camera_pop_window.showAtLocation(camera_pop_view, Gravity.BOTTOM,
					0, 0);
			camera_pop_window.update();
		}
		backgroundAlpha(0.5f);

		// 添加pop窗口关闭事件
		camera_pop_window.setOnDismissListener(new poponDismissListener());
	}

	// 选择图片Pop
	private void initCameraPopWindow() {
		camera_pop_window = new PopupWindow(camera_pop_view,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		camera_pop_window.setAnimationStyle(R.style.pop_ani);
		ColorDrawable c = new ColorDrawable();
		camera_pop_window.setBackgroundDrawable(c);
		camera_pop_window
				.setOnDismissListener(new PopupWindow.OnDismissListener() {

					@Override
					public void onDismiss() {

					}
				});

	}

	/**
	 * 弹出的popWin关闭的事件，主要是为了将背景透明度改回来
	 * 
	 * @author cg
	 */
	class poponDismissListener implements PopupWindow.OnDismissListener {

		@Override
		public void onDismiss() {
			// TODO Auto-generated method stub
			// Log.v("List_noteTypeActivity:", "我是关闭事件");
			backgroundAlpha(1f);
		}

	}

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 */
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getActivity().getWindow()
				.getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		getActivity().getWindow().setAttributes(lp);
	}

	//
	private String uploadFile = Environment.getDataDirectory()
			+ "/data/com.taihe.eggshell/temp.jpg";
	private PopupWindow pw;
	private Bitmap lastPhoto = null;
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	private FileInputStream fStream;
	private String fString;

	/**
	 * ********************* 从相机或者本地选图片到ImageView的method *********
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == NONE)

			return;

		// 拍照

		if (requestCode == PHOTOHRAPH) {

			// 设置文件保存路径这里放在跟目录下

			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/temp.jpg");
			startPhotoZoom(Uri.fromFile(picture));

		}

		if (data == null)

			return;

		// 读取相册缩放图片

		if (requestCode == PHOTOZOOM) {
			startPhotoZoom(data.getData());

		}

		// 处理结果

		if (requestCode == PHOTORESOULT) {

			Bundle extras = data.getExtras();

			if (extras != null) {

				lastPhoto = extras.getParcelable("data");
				extras.getParcelable("data");

				ByteArrayOutputStream stream = new ByteArrayOutputStream();// 字节数组输出
				lastPhoto.compress(Bitmap.CompressFormat.JPEG, 75, stream);//
				FileOutputStream fos = null;
				BufferedOutputStream bos = null;

				// **************将截取后的图片保存到SD卡的temp.jpg文件
				byte[] byteArray = stream.toByteArray();// 字节数组输出流转换成字节数组

				File file = new File(Environment.getExternalStorageDirectory()
						+ "/eggkerImage.JPEG");
				String filePath = Environment.getExternalStorageDirectory()
						+ "/eggkerImage.JPEG";

				// 将字节数组写入到刚创建的图片文件
				try {
					fos = new FileOutputStream(file);
					bos = new BufferedOutputStream(fos);
					bos.write(byteArray);

					if (stream != null)
						stream.close();
					if (bos != null)
						bos.close();
					if (fos != null)
						fos.close();

				} catch (Exception e) {
					e.printStackTrace();
				}

				// 用户头像图片显示==================================================
				// circleiv_mine_icon.setImageBitmap(lastPhoto);

				String ImageString = getPstr(filePath);
				// 上传用户图片
				upLoadImage(ImageString);

			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	// 上传头像
	// TODO
	private void upLoadImage(String ImageString) {

	}

	// 将头像转换成Base64编码
	public String getPstr(String pathname) {

		try {
			FileInputStream fileInputStream = new FileInputStream(pathname);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			int i;
			// 转化为字节数组流
			while ((i = fileInputStream.read()) != -1) {
				byteArrayOutputStream.write(i);
			}
			fileInputStream.close();
			// 把文件存在一个字节数组中
			byte[] buff = byteArrayOutputStream.toByteArray();
			byteArrayOutputStream.close();
			// 将图片的字节数组进行BASE64编码

			String pstr = new String(
					Base64.encodeToString(buff, Base64.DEFAULT));
			return pstr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// 图片裁剪
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");

		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);

		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例

		intent.putExtra("aspectX", 1);

		intent.putExtra("aspectY", 1);

		// outputX outputY 是裁剪图片宽

		intent.putExtra("outputX", 200);

		intent.putExtra("outputY", 200);

		intent.putExtra("return-data", true);

		startActivityForResult(intent, PHOTORESOULT);

	}
}
