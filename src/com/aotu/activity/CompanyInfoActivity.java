package com.aotu.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseFragmentActivity;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.data.CompanyInfo;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.PositionInfoItem;
import com.aotu.data.WorkItem;
import com.aotu.fragment.CompanyinfoFragment;
import com.aotu.fragment.PositioninfoFragment;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;

public class CompanyInfoActivity extends BaseFragmentActivity{

	
	CompanyInfo mCompanyInfo = new CompanyInfo();
	WorkItem mPositionInfoItem = new WorkItem();
	TextView tv_tab_left, tv_tab_right;
	TextView lastTab;
	String Title = "招聘信息";
	
	int cid;
	int id;
	
	FragmentManager fragmentManager = getSupportFragmentManager();
	FragmentTransaction fragmentTransaction = fragmentManager
			.beginTransaction();
	
	KTAlertDialogOnClickListener mKTADialongleft = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			sendCV(true);
		}
		
	};
	
	KTAlertDialogOnClickListener mKTADialongright = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(CompanyInfoActivity.this,PersonCurriculumVitaeActivity.class);
			intent.putExtra("type", 0);
			intent.putExtra("cv_id", "0");
			startActivity(intent);
		}
		
	};
	
private Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch(msg.what)
				{
				case 0:
				{
					
					switchFragment(0);
				}
				break;
				case 1:
				{
					
					switchFragment(1);
				}
				break;
				case 2:
				{
			
					sendCV(false);
				}
				break;
				case 3:
				{
					showMessageDialog("提示","简历投递成功","确定",null,1);
				}
				break;
				}
			
			}
		};
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_info);
		cid = getIntent().getIntExtra("cid",-1);
		id = getIntent().getIntExtra("id",-1);
		initView();
		
		
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
	
	void initView()
	{
		initHeadVIew();
		tv_tab_left = (TextView)findViewById(R.id.tx_tab_left);
		tv_tab_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setCurrentTabBg(tv_tab_left);
				switchFragment(0);
				
			}
		});
		tv_tab_right = (TextView)findViewById(R.id.tx_tab_right);
		tv_tab_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setCurrentTabBg(tv_tab_right);
				switchFragment(1);
			
			}
		});
	
		tv_tab_left.performClick();
	}
	
	void initHeadVIew()
	{
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		RelativeLayout menuleft = (RelativeLayout) findViewById(R.id.left_menu_layout);
		menuleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText(Title);
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}
	
	void switchFragment(int index) {
		if(index == 1)
		{
		CompanyinfoFragment aCompanyinfoFragment = new CompanyinfoFragment(String.valueOf(cid));
		
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.fragment, aCompanyinfoFragment);
		fragmentTransaction.commit();
		}
		else
		{
			PositioninfoFragment mPositioninfoFragment =new PositioninfoFragment(String.valueOf(id));
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.replace(R.id.fragment, mPositioninfoFragment);
			fragmentTransaction.commit();
		}
	}
	
	
	
	
	
	public void onJoin()
	{
		Intent intent = new Intent(CompanyInfoActivity.this,CVListActivity.class);
		startActivityForResult(intent, 1);
		/*String url = NetUrlManager.getSendCvURL();
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.get(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = null;
			    info = JosnParser.getPositioninfo(content, mPositionInfoItem);
			  
				if(info.success)				{
					handler.sendEmptyMessage(0);
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				
			}
		});*/
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

	String mCV_id = "";
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(requestCode)
		{
			case 1:
			{
				if(resultCode == -1)
				{
					mCV_id = data.getStringExtra("cv_id");
					handler.sendEmptyMessage(2);
					
				}
				
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void sendCV(boolean forbidden)
	{
		String url = NetUrlManager.getSendCvURL()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("cv_id", mCV_id);
		params.put("jd_id", String.valueOf(mPositionInfoItem.id));
		if(forbidden)
		  params.put("forbidden", "1");
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.post(url,params,new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
			    NetReturnInfo info = JosnParser.getCVSend(content);
			    if(info.success)
			    {
			    	handler.sendEmptyMessage(1);
			    }
			    else
			    {
			    	switch(info.status)
			    	{
			    	case -1:
			    		showMessageDialog("提示",info.info,"确定",null,0);
			    		break;
			    	case -2:
			    		  showNotitleDialog(info.info,"强行投递","去完善",mKTADialongleft,mKTADialongright,"-2");
			    		break;
			    	case -3:
			    		  showNotitleDialog(info.info,"强行投递","去完善",mKTADialongleft,mKTADialongright,"-3");
			    		break;
			    
			    	}
			    	
			    	 
			    }
				
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				
			}
		});
	
	}


}
