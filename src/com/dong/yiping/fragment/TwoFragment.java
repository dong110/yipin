package com.dong.yiping.fragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dong.yiping.Constant;
import com.dong.yiping.MyApplication;
import com.dong.yiping.R;
import com.dong.yiping.activity.JobMessageActivity;
import com.dong.yiping.activity.MainActivity;
import com.dong.yiping.activity.ResumeActivity;
import com.dong.yiping.adapter.TwoFragmentAdapter;
import com.dong.yiping.bean.DictListBean.DictBean;
import com.dong.yiping.bean.GetJobBean;
import com.dong.yiping.bean.GetJobBean.GetJob;
import com.dong.yiping.bean.HangYeBean;
import com.dong.yiping.bean.HangYeBean.HangYe;
import com.dong.yiping.utils.LoadingUtil;
import com.dong.yiping.utils.NetRunnable;
import com.dong.yiping.utils.PopUtil;
import com.dong.yiping.utils.SPUtil;
import com.dong.yiping.utils.ThreadPoolManager;
import com.dong.yiping.utils.ToastUtil;
import com.dong.yiping.view.LJListView;
import com.dong.yiping.view.LJListView.IXListViewListener;
import com.dong.yiping.view.datepicker.TimeDialog;

public class TwoFragment extends RoboFragment implements IXListViewListener, OnClickListener{
	@InjectView(R.id.tv_fabuQiuZhi) TextView tv_fabuQiuZhi;
	@InjectView(R.id.lv_listview) LJListView lv_listview; 
	@InjectView(R.id.ll_time) LinearLayout ll_time; 
	@InjectView(R.id.tv_fragmenttwo_time) TextView publishTime;
	@InjectView(R.id.ll_area) LinearLayout ll_area;
	@InjectView(R.id.ll_hangye) LinearLayout ll_hangye;
	@InjectView(R.id.tv_serach) TextView tv_serach;
	@InjectView(R.id.tv_hangye) TextView tv_hangye;
	@InjectView(R.id.tv_area) TextView tv_area;
	
	private EditText et_search;
	Context mContext;
	
	private TextView tv_title_center;
	private LinearLayout ll_title_center;
	private TwoFragmentAdapter adapter;
	
	private HangYeBean shengBean;
	private HangYeBean shiBean;
	private HangYeBean quBean;
	private String sheng = null;
	private String shi = null;
	private String shengCode = null;
	private String shiCode = null;
	private String quCode = null;
	private String qu = null;
	private String hangYeCode=null;
	private String hangYe=null;
	private int changeType = 1;//1选择省份，2选择市，3选择区县，4获取行业
	private HangYeBean hangYeBean;
	private List<GetJob> listGetJob;
	private boolean isRefush = true;
	private int total;//总的条数
	private int totalPages=0;//总的页数
	private int currentPage = 1;;//当前是第几页
	private int currentNum=0;//当前第几条
	private int pagerNum=10;//每页的条数
	private String sreach;//搜索关键词
	private TimeDialog timeDialog;
	private PopUtil popUtil;
	private LoadingUtil loadingUtil;
	private List<String> listStr;
	private String date;;
	
    private TimeDialog.CustomTimeListener customTimeListener = new TimeDialog.CustomTimeListener() {
		@Override
        public void setTime(String time) {
        	date = time;
        	publishTime.setText(time);
            timeDialog.dismiss();
        }
    };
    
	private Handler mHandler = new Handler(){
		

		public void handleMessage(android.os.Message msg) {
			loadingUtil.hideDialog();
			switch (msg.what) {
			case Constant.HANDLER_TYPE_GETJOB:
				GetJobBean getJobBean = (GetJobBean) msg.obj;
				if(getJobBean==null){
					ToastUtil.showToast(mContext, "没有更多数据");
				}
				if(isRefush){
					resolveRefushData(getJobBean);
				}else{
					resolveLoadData(getJobBean);
				}
				
				break;
				
			case Constant.NET_ERROR:
				//网络错误
				ToastUtil.showToast(mContext, "网络错误！");
				lv_listview.stopRefresh();
				lv_listview.stopLoadMore();
				
				break;
			case Constant.HANDLER_TYPE_GETHANGYE:
				hangYeBean = (HangYeBean) msg.obj;
				listStr = new ArrayList<String>();
				for(HangYe hangYe:hangYeBean.getList()){
					listStr.add(hangYe.getName());
				}
				loadingUtil.hideDialog();
				popUtil.createPop(listStr);
				
				
				break;
			case Constant.HANDLER_TYPE_GET_SHENG:
				if(changeType == 1){
					shengBean = (HangYeBean) msg.obj;
					if(shengBean==null){
						ToastUtil.showToast(mContext, "获取省份数据失败");
					}else{
						listStr = new ArrayList<String>();
						for(HangYe str:shengBean.getList()){
							listStr.add(str.getName());
						}
						popUtil.createPop(listStr);
					}
				}else if(changeType == 2){
					shiBean = (HangYeBean) msg.obj;
					if(shiBean==null){
						ToastUtil.showToast(mContext, "获取省份数据失败");
					}else{
						listStr = new ArrayList<String>();
						for(HangYe str:shiBean.getList()){
							listStr.add(str.getName());
						}
						popUtil.createPop(listStr);
					}
				}else if(changeType == 3){
					quBean = (HangYeBean) msg.obj;
					if(quBean==null){
						ToastUtil.showToast(mContext, "获取省份数据失败");
					}else{
						listStr = new ArrayList<String>();
						for(HangYe str:quBean.getList()){
							listStr.add(str.getName());
						}
						popUtil.createPop(listStr);
					}
				}
				
				
				break;
			}
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_two, null);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		initView();
		setListener();
		initData();
		
	}
	
	/**
	 * 初始化数据
	 */
	private void initData() {
		isRefush = true;
		currentPage = 1;
		currentNum = 0;
		pagerNum = 10;
		totalPages=0;
		
		String url = Constant.HOST+getLoadUrl(currentNum,pagerNum);
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GETJOB));
		
	}
	private void setListener() {
		ll_time.setOnClickListener(this);
		ll_hangye.setOnClickListener(this);
		ll_area.setOnClickListener(this);
		tv_serach.setOnClickListener(this);
		tv_fabuQiuZhi.setOnClickListener(this);
		
		
		
		lv_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(position>0){
					position--;
				}
				
				Intent intent = new Intent(mContext,JobMessageActivity.class);
				intent.putExtra("bannerListBean", MyApplication.getApplication().getBannerListBean());
				intent.putExtra("getJob",listGetJob.get(position));
				
				mContext.startActivity(intent);
			}
		});
		popUtil.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(changeType == 1){
					//省
					sheng = shengBean.getList().get(position).getName();
					shengCode = shengBean.getList().get(position).getCode();
					tv_area.setText(sheng);
					changeType =2;
					getArea(2);
				}else if(changeType == 2){
					//市
					shi = shiBean.getList().get(position).getName();
					shiCode = shiBean.getList().get(position).getCode();
					changeType =3;
					tv_area.setText(shi);
					getArea(3);
				}else if(changeType == 3){
					//区县
					qu = quBean.getList().get(position).getName();
					quCode = quBean.getList().get(position).getCode();
					tv_area.setText(qu);
					
				}else if(changeType == 4){
					//行业
					hangYe = hangYeBean.getList().get(position).getName();
					hangYeCode = hangYeBean.getList().get(position).getCode();
					tv_hangye.setText(hangYe);
					
					
				}
				
				popUtil.hidePop();
			}
		});
		et_search.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				sreach = et_search.getText().toString().trim();
				initData();
			}
		});
		
	}
	private void initView() {
		
		et_search = (EditText) getActivity().findViewById(R.id.et_search);
		
		timeDialog = new TimeDialog(mContext,customTimeListener);
		popUtil = new PopUtil(mContext);
		loadingUtil = new LoadingUtil(mContext);
		listGetJob = new ArrayList<GetJobBean.GetJob>();
		adapter = new TwoFragmentAdapter(mContext,listGetJob);
		lv_listview.setAdapter(adapter);
		
		lv_listview.setPullLoadEnable(false,""); //如果不想让脚标显示数据可以mListView.setPullLoadEnable(false,null)或者mListView.setPullLoadEnable(false,"")
		lv_listview.setPullRefreshEnable(true);
		//lv_listview.setPullLoadEnable(false, "加载完成");
		lv_listview.setIsAnimation(true); 
		lv_listview.setXListViewListener(this);
		
	}
	/**
	 * 刷新Listview数据
	 * @param listGetJob
	 */
	private void notifyAdapter(List<GetJob> listGetJob) {
		adapter.notifyList(listGetJob);
	}
	/**
	 * 解析刷新数据
	 * @param getJobBean 
	 */
	private void resolveRefushData(GetJobBean getJobBean){
		if(getJobBean!=null && getJobBean.getList()!=null &&getJobBean.getList().size()>0){
			total = getJobBean.getTotal();
			listGetJob.clear();
			for(GetJob job : getJobBean.getList()){
				listGetJob.add(job);
			}
			notifyAdapter(listGetJob);
			setListView(total);
		}else{
			total = 0;
			listGetJob.clear();
			notifyAdapter(listGetJob);
			setListView(total);
		}
		
	}
	
	/**
	 * 解析加载数据
	 * @param getJobBean
	 */
	private void resolveLoadData(GetJobBean getJobBean){
		if(getJobBean!=null && getJobBean.getList()!=null &&getJobBean.getList().size()>0){
			for(GetJob job : getJobBean.getList()){
				listGetJob.add(job);
			}
			adapter.addList(listGetJob);
			lv_listview.stopRefresh();
			lv_listview.stopLoadMore();
			lv_listview.setRefreshTime("刚刚");
			if(currentPage < totalPages){
				lv_listview.setPullLoadEnable(true,"加载更多");
			}else{
				lv_listview.setPullLoadEnable(false,"没有更多数据");
			}
			
			
		}else{
			lv_listview.stopRefresh();
			lv_listview.stopLoadMore();
			lv_listview.setRefreshTime("刚刚");
			lv_listview.setPullLoadEnable(false, "没有更多数据");
		}
		
		
		
	}
	
	private String getLoadUrl(int currentNum,int pageNum){
		
		
		String str = "/recruitList?status=1&currentNum="+currentNum+"&pageNum="+pageNum;
		if(sreach != null && !TextUtils.isEmpty(sreach)){
			str += "&company="+sreach;
		}
		
		if(sheng != null && !TextUtils.isEmpty(sheng)){
			str += "&sheng="+sheng;
		}
		if(shi != null && !TextUtils.isEmpty(shi)){
			str += "&shi="+shi;
		}
		if(qu != null && !TextUtils.isEmpty(qu)){
			str += "&quxian="+qu;
		}
		if(hangYe!= null && !TextUtils.isEmpty(hangYe)){
			str += "&industry="+hangYe;
		}
		if(date!= null && !TextUtils.isEmpty(date)){
			str += "&subdate="+date;
		}
		return str;
	}
	
	private void setListView(int total) {
		if(total<=10){
			lv_listview.stopRefresh();
			lv_listview.stopLoadMore();
			lv_listview.setRefreshTime("刚刚");
			lv_listview.setPullLoadEnable(false, "");
		}else{
			totalPages = total/pagerNum +1;
			
			lv_listview.stopRefresh();
			lv_listview.stopLoadMore();
			lv_listview.setRefreshTime("刚刚");
			lv_listview.setPullLoadEnable(true,"加载更多");
		}
		
	};
	
	/**
	 * 刷新数据
	 */
	@Override
	public void onRefresh() {
		initData();
	}

	/**
	 * 加载更多
	 */
	@Override
	public void onLoadMore() {
		getLoadData();
	}
	
	private void getLoadData() {
		isRefush = false;
		currentPage++;
		currentNum = (currentPage-1)*pagerNum;
		String url = Constant.HOST + getLoadUrl(currentNum, pagerNum);
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GETJOB));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_fabuQiuZhi:
			int type = SPUtil.getInt(mContext, "type", -1);
			if(type == 0){
				Intent mIntent = new Intent(mContext,ResumeActivity.class);
				startActivity(mIntent);
			}
			if(type == 1){
				ToastUtil.showToast(mContext, "非企业用户权限");
			}
			break;
		case R.id.ll_time:
			timeDialog.show();
			break;
		case R.id.ll_hangye:
			loadingUtil.showDialog();
			changeType =4;
			getHangyeData();
			
			break;
		case R.id.ll_area:
			loadingUtil.showDialog();
			changeType = 1;
			getArea(1);
			
			break;
		case R.id.tv_serach:
			loadingUtil.showDialog();
			initData();
			
			break;
		}
	}
	
	
	private void getArea(int i) {
		List<DictBean> listDictBean = MyApplication.getApplication().getDictBean().getList();
		/*String code = "";
		for(DictBean dictBean : listDictBean){
			if(dictBean.getCode().equals("SHENG")){
				code = dictBean.getCode();
			}
		}*/
		String url = Constant.HOST + Constant.GET_DICT_INFO;
		if(i == 1){
			url += "SHENG";
		}else if(i == 2){
			//选择市
			url += "SHI"+shengCode.substring(shengCode.length()-2, shengCode.length());
			
		}else if(i==3){
			//选择区县
			url += "QUXIAN"+shengCode.substring(shengCode.length()-2, shengCode.length()) + shiCode.substring(shiCode.length()-2, shiCode.length());
		}
		
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GET_SHENG));
		
	}

	private void getHangyeData() {
		List<DictBean> listDictBean = MyApplication.getApplication().getDictBean().getList();
		/*String code = "";
		for(DictBean dictBean : listDictBean){
			if(dictBean.getCode().equals("HANGYE")){
				code = dictBean.getCode();
			}
		}*/
		String url = Constant.HOST + Constant.GET_DICT_INFO+"HANGYE";
		ThreadPoolManager.getInstance().addTask(new NetRunnable(mHandler,url,Constant.TOPER_TYPE_GETHANGYE));
	}
}
