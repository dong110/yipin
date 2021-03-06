package com.dong.yiping.fragment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dong.yiping.Constant;
import com.dong.yiping.MyApplication;
import com.dong.yiping.R;
import com.dong.yiping.activity.ComCollectListActivity;
import com.dong.yiping.activity.CompanyInfoActivity;
import com.dong.yiping.activity.LoginActivity;
import com.dong.yiping.activity.MainActivity;
import com.dong.yiping.activity.ModifyPwdActivity;
import com.dong.yiping.activity.MyResumesActivity;
import com.dong.yiping.activity.MyZhaoPinListActivity;
import com.dong.yiping.activity.PhoneIdentificationActivity;
import com.dong.yiping.activity.UserCollectListActivity;
import com.dong.yiping.activity.UserHistoryActivity;
import com.dong.yiping.activity.UserInfoActivity;
import com.dong.yiping.activity.UserShenQingActivity;
import com.dong.yiping.bean.UserBean.Obj.Pic;
import com.dong.yiping.utils.LoadingUtil;
import com.dong.yiping.utils.LogUtil;
import com.dong.yiping.utils.MultipartEntityExt;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.SPUtil;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.utils.UpdateNickAndPortrait;
import com.squareup.picasso.Picasso;

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
	@InjectView(R.id.login_out)
	Button login_out;
	@InjectView(R.id.myInfo)
	TextView myInfo;
	
	
	
	private TextView tv_title_center;
	private LinearLayout ll_title_center;
	private Intent mIntent;
	private Context mContext;
	private int Type;
	private String username;
	private LoadingUtil loadingUtil;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.HANDLER_TYPE_SAVE_USERICON:
				int status = (Integer) msg.obj;
				if(status == 0){
					ToastUtil.showToast(mContext, "上传图片成功");
					iv_fragmentfore_icon.setImageBitmap(lastPhoto);
				}else{
					ToastUtil.showToast(mContext, "上传图片失败");
				}
				break;
			default:
				break;
			}
			loadingUtil.hideDialog();
		};
	};
	
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
		login_out.setOnClickListener(this);
		iv_fragmentfore_icon.setOnClickListener(this);
		myInfo.setOnClickListener(this);
	}

	private void initData() {

	}

	private void initView() {
		initImageSelect();
		loadingUtil = new LoadingUtil(mContext);
		List<Pic> listPic= MyApplication.getApplication().getUserBean().getObj().getPic();
		if(listPic!= null && listPic.size()>0){
			String img_url = MyApplication.getApplication().getUserBean().getObj().getPic().get(0).getOriginal();
			Picasso.with(mContext).load(img_url).into(iv_fragmentfore_icon);
		}
		
		tv_fragmentfore_username.setText(MyApplication.getApplication().getUserBean().getObj().getUserInfo().getName());
		LogUtil.i("username====", username + "");
		Type = SPUtil.getInt(mContext, "type", 0);
		if (Type == 0) {// 学生用户
			myInfo.setText("我的信息");
			myResume.setText("我的简历");
			tv_fragment_jilu.setText("职位申请记录");
			
		} else {// 公司用户
			myResume.setText("我的招聘");
			tv_fragment_jilu.setText("面试邀请记录");
			myInfo.setText("公司信息");
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.myInfo:
			if (Type == 0) {// 学生用户
				mIntent = new Intent(mContext,UserInfoActivity.class);
				startActivity(mIntent);
			}
			if(Type == 1){
				mIntent = new Intent(mContext,CompanyInfoActivity.class);
				startActivity(mIntent);
			}
			
			break;
		case R.id.myResume:
			if (Type == 0) {// 学生用户
				mIntent = new Intent(mContext, MyResumesActivity.class);
				
			} else {// 公司用户
				mIntent = new Intent(mContext, MyZhaoPinListActivity.class);
			}
			if(mIntent !=null){
				mContext.startActivity(mIntent);
			}
			
			break;

		case R.id.tv_mine_modifypwd:
			mIntent = new Intent(mContext, ModifyPwdActivity.class);
			if(mIntent !=null){
				mContext.startActivity(mIntent);
			}
			break;

		case R.id.tv_mine_phone:
			mIntent = new Intent(mContext, PhoneIdentificationActivity.class);
			if(mIntent !=null){
				mContext.startActivity(mIntent);
			}
			break;
		case R.id.tv_collection:
			mIntent = new Intent(mContext, UserCollectListActivity.class);
			if (Type == 0) {// 学生用户
			} else {// 公司用户
				//mIntent = new Intent(mContext, ComCollectListActivity.class);
			}
			if(mIntent !=null){
				mContext.startActivity(mIntent);
			}
			break;
		case R.id.tv_fragment_jilu:
			if (Type == 0) {// 学生用户
			} else {// 公司用户
			}
			mIntent = new Intent(mContext, UserShenQingActivity.class);
			mIntent.putExtra("type", Type);
			if(mIntent !=null){
				mContext.startActivity(mIntent);
			}
			break;
		case R.id.tv_history:
			mIntent = new Intent(mContext, UserHistoryActivity.class);
			if (Type == 0) {// 学生用户

			} else {// 公司用户
			}
			if(mIntent !=null){
				mContext.startActivity(mIntent);
			}
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
		case R.id.login_out:
			SPUtil.clearUser(mContext);
			mIntent = new Intent(mContext,LoginActivity.class);
			startActivity(mIntent);
			getActivity().finish();
			
			break;
		
		}
		
		mIntent =null;

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
						+ "/yipin.jpg");
				String filePath = Environment.getExternalStorageDirectory()
						+ "/yipin.jpg";
				
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
				
				
				//String ImageString = getPstr(filePath);
				// 上传用户图片
				loadingUtil.showDialog();
				upLoadImage(file);
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	// 上传头像
	// TODO
	private void upLoadImage(final File file) {
		new Thread(){
			public void run() {
				try {
					String actionUrl = Constant.UPLOAD_FILE;
					Map<String, File> files = new HashMap<String, File>();
					files.put("file", file);
					String result = UpdateNickAndPortrait.post(actionUrl, null, files);
					JSONObject js= new JSONObject(result);
					//String status = js.getString("status");
					String fileName = js.getString("fileName");
					if(fileName!=null && !TextUtils.isEmpty(fileName)){
						String url = Constant.HOST + Constant.UPLOAD_USER_IMG;
						Map<String, String> paramsMap = new HashMap<String, String>();
						paramsMap.put("userid", SPUtil.getInt(mContext, "id", -1)+"");
						paramsMap.put("isdefault", "1");
						paramsMap.put("thumbnail", "1");
						paramsMap.put("original", fileName);
						ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler, url, paramsMap, Constant.TOPER_TYPE_SAVE_USERICON));
					}
					
				} catch (Exception e) {
					mHandler.sendEmptyMessage(0);
					loadingUtil.hideDialog();
					e.printStackTrace();
				}
				
			};
		}.start();
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
