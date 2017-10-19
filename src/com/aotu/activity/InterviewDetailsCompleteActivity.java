package com.aotu.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aotu.Adapter.InterviewConflictCurAdapter;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.BaseFragmentActivity;
import com.aotu.baseview.CustomListView;
import com.aotu.data.InterviewConflictItem;
import com.aotu.data.InterviewInfoItem;
import com.aotu.data.InterviewProgressItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.fragment.InterviewnoticeFragment;
import com.aotu.fragment.InterviewprogressFragment;
import com.aotu.fragment.InterviewprogressListFragment;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;

public class InterviewDetailsCompleteActivity extends BaseFragmentActivity implements OnClickListener{

	String ID;
	InterviewInfoItem mInterviewInfoItem = new InterviewInfoItem();

	TextView tv_tab_left, tv_tab_right;
	TextView tx_info1, tx_info2, tx_info3, tx_info4, tx_info5;// 公司名称、应聘岗位、面试时间、地址

	
	TextView lastTab;

	InterviewprogressListFragment mInterviewprogressFragment;
	InterviewnoticeFragment mInterviewnoticeFragment;

	FragmentManager fragmentManager = getSupportFragmentManager();
	FragmentTransaction fragmentTransaction = fragmentManager
			.beginTransaction();
    Button bn_ok,bn_reject,bn_chang_timer,bn_accept,bn_cancel,bn_join_in;
    
    
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
				updateView();
			}
				break;
			case 1: {
				
			}
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interview_details_complete);
		int id = getIntent().getIntExtra("id", 0);
		ID = String.valueOf(id);
		initView();
		setCurrentTabBg(tv_tab_left);
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
		
		bn_reject= (Button)findViewById(R.id.bn_reject);
		bn_reject.setOnClickListener(this);
		bn_chang_timer= (Button)findViewById(R.id.bn_chang_timer);
		bn_chang_timer.setOnClickListener(this);
		bn_accept= (Button)findViewById(R.id.bn_accept);
		bn_accept.setOnClickListener(this);
		bn_cancel= (Button)findViewById(R.id.bn_cancel);
		bn_cancel.setOnClickListener(this);
		bn_join_in= (Button)findViewById(R.id.bn_join_in);
		bn_join_in.setOnClickListener(this);
		bn_ok = (Button)findViewById(R.id.bn_ok);
		bn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(InterviewDetailsCompleteActivity.this,InterviewScoreActivity.class);
				
				String company = mInterviewInfoItem.company; // string 公司名称
				String title = "应聘岗位：" + mInterviewInfoItem.title; // string 职位名称
				String interview_time = "面试时间：" + mInterviewInfoItem.interview_time; // string
				intent.putExtra("company", company);
				intent.putExtra("title", title);
				intent.putExtra("interview_time", interview_time);
				intent.putExtra("id", mInterviewInfoItem.id);
				startActivity(intent);
			}
		});
		initData();
		setCurrentTabBg(tv_tab_left);

	}

	void initHeaderView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText("面试详情");
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}
	
	void initData()
	{
		
		
		
	}
	void updateView() {
		
		
		int id = mInterviewInfoItem.id; // int 面试ID
		int cid = mInterviewInfoItem.cid; // int 公司ID

		String company = mInterviewInfoItem.company; // string 公司名称
		String title = "应聘岗位：" + mInterviewInfoItem.title; // string 职位名称
		String address ="面试地址："+ mInterviewInfoItem.address; // string 公司地址
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

		mInterviewprogressFragment = new InterviewprogressListFragment(progress,
				status);
		mInterviewnoticeFragment = new InterviewnoticeFragment(tips);
		
		switchFragment(mInterviewprogressFragment);
		  LinearLayout  rl_bn = (LinearLayout) findViewById(R.id.rl_bn);
		bn_reject.setVisibility(View.GONE);
		bn_chang_timer.setVisibility(View.GONE);
		bn_accept.setVisibility(View.GONE);
		bn_cancel.setVisibility(View.GONE);
		bn_join_in.setVisibility(View.GONE);
		if(Integer.valueOf(mInterviewInfoItem.status)==20)
		{
			rl_bn.setVisibility(View.VISIBLE);
			bn_reject.setVisibility(View.VISIBLE);
			bn_chang_timer.setVisibility(View.VISIBLE);
			bn_accept.setVisibility(View.VISIBLE);
			
		}
		else if(Integer.valueOf(mInterviewInfoItem.status)==30)
		{
			rl_bn.setVisibility(View.VISIBLE);
			bn_cancel.setVisibility(View.VISIBLE);
			
			
		}
		else if(Integer.valueOf(mInterviewInfoItem.status)==150)
		{
			rl_bn.setVisibility(View.VISIBLE);
			bn_join_in.setVisibility(View.VISIBLE);
		}
		

	}
	
	void switchFragment(Fragment fragment) {
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.fragment, fragment);
		fragmentTransaction.commit();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
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
			
		case R.id.bn_reject:
		{
			sendInterviewAction(String.valueOf(mInterviewInfoItem.id), "refuse");
		}
		break;
		case R.id.bn_chang_timer:
		{
			sendInterviewAction(String.valueOf(mInterviewInfoItem.id), "reschedule");
		}
		break;
		case R.id.bn_accept:
		{
			sendInterviewAction(String.valueOf(mInterviewInfoItem.id), "accept");
		}
		break;
		case R.id.bn_cancel:
		{
			sendInterviewAction(String.valueOf(mInterviewInfoItem.id), "cancel");
		}
		break;
		case R.id.bn_join_in:
		{
			sendInterviewAction(String.valueOf(mInterviewInfoItem.id), "complain");
		}
		break;
			
		
		}
	}

	void getInterviewDetails() {
		String url = NetUrlManager.getInterViewDetails(ID);
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
				showMessageDialog("提示", info.info, "确定", null, 1);
				if (info.success) {
              
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);

			}
		});
	}


}
