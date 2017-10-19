package com.aotu.activity;

import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aotu.Adapter.InterviewAdapter;
import com.aotu.Adapter.InterviewConflictAdapter;
import com.aotu.Adapter.InterviewConflictAdapter.InterviewConflictInterface;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.BaseFragmentActivity;
import com.aotu.baseview.CustomListView;
import com.aotu.baseview.BaseFragmentActivity.KTAlertDialogOnClickListener;
import com.aotu.data.InterviewConflictItem;
import com.aotu.data.InterviewInfoItem;
import com.aotu.data.InterviewProgressItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.fragment.InterviewPageFragment;
import com.aotu.fragment.InterviewnoticeFragment;
import com.aotu.fragment.InterviewprogressFragment;
import com.aotu.fragment.InterviewprogressListFragment;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;

public class InterviewDetailsPreCompleteActivity extends BaseFragmentActivity
		implements OnClickListener, InterviewConflictInterface {

	String ID;
	InterviewInfoItem mInterviewInfoItem = new InterviewInfoItem();

	TextView tv_tab_left, tv_tab_right,lastTab;
	TextView tx_info1, tx_info2, tx_info3, tx_info4, tx_info5,tx_ad;// 公司名称、应聘岗位、面试时间、地址

	RelativeLayout right_layout;
	CustomListView list_view;
	InterviewConflictAdapter mAdapter;
	boolean isShowList = true;

	InterviewprogressListFragment mInterviewprogressFragment;
	InterviewnoticeFragment mInterviewnoticeFragment;

	FragmentManager fragmentManager = getSupportFragmentManager();
	FragmentTransaction fragmentTransaction = fragmentManager
			.beginTransaction();
	
	Button bn_left,bn_mid,bn_right;
	
	KTAlertDialogOnClickListener mKTADialongleft = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			finish();
		}
		
	};

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
				updateView();
			}
				break;
			case 1: {
				if (!isShowList) {
					list_view.setVisibility(View.GONE);
				} else {
					list_view.setVisibility(View.VISIBLE);
					list_view.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
				}
			}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interview_details_pre);
		int id = getIntent().getIntExtra("id", 0);
		ID = String.valueOf(id);
		initView();
		getInterviewDetails();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	void getInterviewDetails() {
		String url = NetUrlManager.getInterViewDetails(ID)+"&token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = null;
				info = JosnParser.getInterviewInfo(content, mInterviewInfoItem);
				if (info.success) {
					handler.sendEmptyMessage(0);
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);

			}
		});
	}

	void sendInterviewAction(String id, String action) {
		String url = NetUrlManager.getInterViewAction(id, action);
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = null;
				info = JosnParser.getInterviewAction(content);
				if(info.success)
				{
					
					showMessageDialog("提示", info.info, "确定", mKTADialongleft,0);
				}
				else
					showMessageDialog("提示", info.info, "确定", mKTADialongleft,0);
				
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);

			}
		});
	}

	void initView() {
		initHeaderView();
		tv_tab_right = (TextView) findViewById(R.id.tv_tab_right);
		tv_tab_right.setOnClickListener(this);
		tv_tab_left = (TextView) findViewById(R.id.tv_tab_left);
		tv_tab_left.setOnClickListener(this);

		tx_info1 = (TextView) findViewById(R.id.tx_info1);
		tx_info2 = (TextView) findViewById(R.id.tx_info2);
		tx_info3 = (TextView) findViewById(R.id.tx_info3);
		tx_info4 = (TextView) findViewById(R.id.tx_info4);
		tx_info5 = (TextView) findViewById(R.id.tx_info5);

		right_layout = (RelativeLayout) findViewById(R.id.tab_right_layout);
		right_layout.setOnClickListener(this);
		tx_ad = (TextView)findViewById(R.id.tx_ad);
		list_view = (CustomListView)findViewById(R.id.list_view);
		list_view.setCanLoadMore(false);
		list_view.setCanRefresh(false);
		
		 bn_left = (Button) findViewById(R.id.bn_left);
		 bn_left.setOnClickListener(this);
		 bn_mid= (Button) findViewById(R.id.bn_mid);
		 bn_mid.setOnClickListener(this);
		 bn_right= (Button) findViewById(R.id.bn_okacc);
		 bn_right.setOnClickListener(this);
		initData();
		setCurrentTabBg(tv_tab_left);

	}

	void initHeaderView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText("约面详情");
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}
	


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id = arg0.getId();
		switch (arg0.getId()) {
		case R.id.tv_tab_right: {
			setCurrentTabBg((TextView)arg0);
			switchFragment(mInterviewnoticeFragment);
		}
			break;
		case R.id.tv_tab_left: {
			setCurrentTabBg((TextView)arg0);
			switchFragment(mInterviewprogressFragment);
		}
			break;
		case R.id.tab_right_layout: {
			isShowList = !isShowList;
			handler.sendEmptyMessage(1);
		}
			break;
		case R.id.bn_left:
		{
			
		
				sendInterviewAction(String.valueOf(mInterviewInfoItem.id), "refuse");

		}
		break;
		case R.id.bn_mid:
		{
			sendInterviewAction(String.valueOf(mInterviewInfoItem.id), "reschedule");
			
		}
		break;
		case R.id.bn_okacc:
		{
			sendInterviewAction(String.valueOf(mInterviewInfoItem.id), "accept");
		}
		break;
		}
	}

	void initData()
	{
		/*mInterviewInfoItem.id  = 0; // int 面试ID
		mInterviewInfoItem.cid  = 0; // int 公司ID
		mInterviewInfoItem.company = "美团"; // string 公司名称
		mInterviewInfoItem.title ="产品经理"; // string 职位名称
		mInterviewInfoItem.address ="朝阳朝阳"; // string 公司地址
		mInterviewInfoItem.tips=""; // string 注意事项
		mInterviewInfoItem.interview_time = "2015-09-14 14:00"; // string 面试时间
		mInterviewInfoItem.status = 20; // int 状态码
		
		InterviewProgressItem item = new InterviewProgressItem();
		item.code = 10;
		item.name = "面试";
		item.time = "2015-09-12 14:00";
		mInterviewInfoItem.progress.add(item);
		InterviewProgressItem item1 = new InterviewProgressItem();
		item1.code = 20;
		item1.name = "面试";
		item1.time = "2015-09-12 14:00";
		mInterviewInfoItem.progress.add(item1);
		
		InterviewProgressItem item2 = new InterviewProgressItem();
		item2.code = 40;
		item2.name = "面试";
		item2.time = "2015-09-12 14:00";
		mInterviewInfoItem.progress.add(item2);
		
		InterviewProgressItem item3 = new InterviewProgressItem();
		item3.code = 60;
		item3.name = "面试";
		item3.time = "2015-09-12 14:00";
		mInterviewInfoItem.progress.add(item3);
		
		InterviewProgressItem item4 = new InterviewProgressItem();
		item4.code = 80;
		item4.name = "面试";
		item4.time = "2015-09-12 14:00";
		mInterviewInfoItem.progress.add(item4);
		
		InterviewProgressItem item5 = new InterviewProgressItem();
		item5.code = 100;
		item5.name = "面试";
		item5.time = "2015-09-12 14:00";
		mInterviewInfoItem.progress.add(item5);
		
		
		mInterviewInfoItem.longitude = 43432423; // number 经度
		mInterviewInfoItem.latitude = 432423; // number 纬度
		
		InterviewConflictItem item6 = new InterviewConflictItem();
		item6.id = 9; // int 面试ID
		item6.company = "美团"; // string 公司名称
		item6.title = "产品经理"; // string 职位名称
		item6.interview_time = "2015-09-12 13:00";// string 面试时间
		mInterviewInfoItem.conflict.add(item6);
		updateView();*/
		
	}
	void updateView() {
		
		
		int id = mInterviewInfoItem.id; // int 面试ID
		int cid = mInterviewInfoItem.cid; // int 公司ID

		String company = mInterviewInfoItem.company; // string 公司名称
		String title = "应聘岗位：" + mInterviewInfoItem.title; // string 职位名称
		String address = "面试地址："+mInterviewInfoItem.address; // string 公司地址
		String tips = mInterviewInfoItem.tips; // string 注意事项
		String interview_time = "面试时间：" + mInterviewInfoItem.interview_time; // string
																			// 面试时间
		int status = mInterviewInfoItem.status; // int 状态码
		List<InterviewProgressItem> progress = mInterviewInfoItem.progress;
		long longitude = mInterviewInfoItem.longitude; // number 经度
		long latitude = mInterviewInfoItem.latitude; // number 纬度
		List<InterviewConflictItem> conflict = mInterviewInfoItem.conflict;

		tx_info1.setText(company);
		tx_info2.setText(title);
		tx_info3.setText(interview_time);
		tx_info5.setText(address);
		tx_ad.setText(mInterviewInfoItem.ad);
		mInterviewprogressFragment = new InterviewprogressListFragment(progress,
				status);
		mInterviewnoticeFragment = new InterviewnoticeFragment(tips);
		if (conflict.size() > 0) {
			if (mAdapter == null)
				mAdapter = new InterviewConflictAdapter(this,
						mInterviewInfoItem.conflict, this);
			handler.sendEmptyMessage(1);
		}
		switchFragment(mInterviewprogressFragment);

	}

	@Override
	public void onBnClick(int pos, int type) {
		// TODO Auto-generated method stub
		InterviewConflictItem item = mInterviewInfoItem.conflict.get(pos);
		if (type == 0) {
			sendInterviewAction(String.valueOf(item.id), "refuse");
		} else if (type == 1) {
			sendInterviewAction(String.valueOf(item.id), "reschedule");
		} else if (type == 2) {
			sendInterviewAction(String.valueOf(item.id), "accept");
		}
	}

	@Override
	public void onInfoClick(int pos) {
		// TODO Auto-generated method stub

	}

	void switchFragment(Fragment fragment) {
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.fragment, fragment);
		fragmentTransaction.commit();
	}
	
	void setCurrentTabBg(TextView mCurrentTab){
	    mCurrentTab.setSelected(true);
	    if(mCurrentTab!=lastTab)
	    {
	    if (lastTab == null) {
	        lastTab = mCurrentTab;
        }else {
            lastTab.setSelected(false);
        }
	    
	    lastTab = mCurrentTab;
	    }
	}

}
