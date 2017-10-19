package com.aotu.activity;



import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.data.CheckUpdate;
import com.aotu.data.NetReturnInfo;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.utils.UpdateManager;
import com.auto.aotuge.R;



import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;


public class SplashActivity extends BaseActivity {
	final int SPLASH_DISPLAY_LENGHT = 1*1000; // 延迟一秒
	String versionName;
	int versioncode;
	CheckUpdate mCheckUpdate = new CheckUpdate();
	UpdateManager mUpdateManager;
	KTAlertDialogOnClickListener mKTADialongleft = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			handler.sendEmptyMessageDelayed(0, SPLASH_DISPLAY_LENGHT);
		}
		
	};
	
	KTAlertDialogOnClickListener mKTADialongright = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			mUpdateManager.checkUpdateInfo(mCheckUpdate.url);
		}
		
	};
	
	
	
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:{
				getCityData();
			}
			break;
			case 1:{
				onSwitchView();
			}
			break;
			default:
				break;
			}
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		getAppVersionName(this);
		mUpdateManager = new UpdateManager(this);
		sendCheckUP();
		
		
	}

	private void onSwitchView() {
		if (AotugeApplication.getInstance().getIsFirst()) {
			AotugeApplication.getInstance().setIsFirst(false);
			Intent intet = new Intent(SplashActivity.this,
					GuideViewActivity.class);
			startActivity(intet);
			finish();
		} else {
			Intent intent = new Intent(SplashActivity.this, MainActivityFragment.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			finish();
		}
		
		/*Intent intent = new Intent(SplashActivity.this, MainActivityFragment.class);
		startActivity(intent);
		finish();*/
		/*if (AotugeApplication.getInstance().mAotuInfo.token.length()==0) {
			AotugeApplication.getInstance().setIsFirst(false);
			Intent intent = new Intent(SplashActivity.this, LogonActivity.class);
			startActivity(intent);
			finish();
		} else {
			Intent intent = new Intent(SplashActivity.this, MainActivityFragment.class);
			startActivity(intent);
			finish();
		}*/
		/*Intent intent = new Intent(SplashActivity.this, LogonActivity.class);
		startActivity(intent);
		finish();*/
	}

	

	public String getAppVersionName(Context context) {
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			versioncode = pi.versionCode;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	public NetworkInfo getActiveNetwork(Context context) {
		if (context == null)
			return null;
		ConnectivityManager mConnMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (mConnMgr == null)
			return null;
		NetworkInfo aActiveInfo = mConnMgr.getActiveNetworkInfo(); // 获取活动网络连接信息
		return aActiveInfo;
	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	private void getCityData()
	{
		String url = NetUrlManager.getPCDataUrl();
		AsyncHttpClient client = new AsyncHttpClient();

		client.get(url,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getProvinceCity(content);
				handler.sendEmptyMessage(1);
				
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				handler.sendEmptyMessage(1);
			}
		});
	}
	
	public void sendCheckUP()
	{
		String url = NetUrlManager.getCheckUpUrl()+"version="+String.valueOf(versioncode)+"&device=android";
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url,new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable error, String content) {
				handler.sendEmptyMessageDelayed(0, SPLASH_DISPLAY_LENGHT);
			}

			@Override
			public void onSuccess(String response) {
				JosnParser.getUpCheck(response,mCheckUpdate);
				
				if(mCheckUpdate.status==0)
				{
					showNotitleDialog("有新的版本,是否更新升级", "取消", "升级", mKTADialongleft, mKTADialongright, "1");
				}
				else if(mCheckUpdate.status == 1)
				{
					 mUpdateManager.checkUpdateInfo(mCheckUpdate.url);
				}
				else
				{
					handler.sendEmptyMessageDelayed(0, SPLASH_DISPLAY_LENGHT);
				}
				/*if(netitemdata.success.equals("200"))
				{

		               if( mCheckUpItem.type.equals("m"))
						{
		            	   showCustomDialog(2);
						  
						}
						else if(mCheckUpItem.type.equals("n"))
						{
							 showCustomDialog(1);
						}
						else
						{
							onSwitchView();	
						}
				}*/
			}
		});
		
	
	}
	 
	 
	 
}
