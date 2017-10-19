package com.aotu.activity;
/*
 * 登陆
 */
import java.util.ArrayList;
import java.util.Calendar;

import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomPopupMenu;
import com.aotu.data.AotuInfo;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.PersonAssociationItem;
import com.aotu.data.PersonAwardsItem;
import com.aotu.data.PersonEducation;
import com.aotu.data.PersonInternshipItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LogonActivity extends BaseActivity {

	EditText EditTextName, EditTextPassword;
	Button ButtonClearName, ButtonClearPassword, ButtonLogon;
	TextView TXResetPassword,mTx_Register;
	AotuInfo mAkuaitingInfo = new AotuInfo();

	String mPassword;
	String versionName = "";
	int versioncode;
	
	final int CHECK_CODE = 0x1; 
	final int LONG_DURATION = 5000;
	final int SHORT_DURATION = 1200;
	
	
	
	TextWatcher mNameTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			activateLogonButton();
			if (EditTextName.getText().toString() != null
					&& !EditTextName.getText().toString().equals("")) {
				ButtonClearName.setVisibility(View.VISIBLE);

			} else {
				ButtonClearName.setVisibility(View.INVISIBLE);
			}
		}
	};

	TextWatcher mPasswordTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			activateLogonButton();
			if (EditTextPassword.getText().toString() != null
					&& !EditTextPassword.getText().toString().equals("")) {
				ButtonClearPassword.setVisibility(View.VISIBLE);
			} else {
				ButtonClearPassword.setVisibility(View.INVISIBLE);
			}
		}
	};

	KTAlertDialogOnClickListener mKTADialongleft = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	KTAlertDialogOnClickListener mKTADialongright = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
				Intent intent = new Intent(LogonActivity.this, MainActivityFragment.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				finish();
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
		setContentView(R.layout.activity_logon);
		initView();
		
		getAppVersionName(this);
	
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private void initView() {
		initHeaderView();
		EditTextName = (EditText) findViewById(R.id.ed_logon_username);
		EditTextName.addTextChangedListener(mNameTextWatcher);
		EditTextPassword = (EditText) findViewById(R.id.ed_logon_password);
		EditTextPassword.addTextChangedListener(mPasswordTextWatcher);
		ButtonClearName = (Button) findViewById(R.id.bn_logon_username_clear);
		ButtonClearName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditTextName.setText("");
			}
		});
		ButtonClearPassword = (Button) findViewById(R.id.bn_logon_password_clear);
		ButtonClearPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditTextPassword.setText("");
			}
		});
		ButtonLogon = (Button) findViewById(R.id.bn_logon);

		ButtonLogon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sendLogonRequest();
			}
		});

		TXResetPassword = (TextView) findViewById(R.id.tx_reset_password);
		TXResetPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LogonActivity.this, FindPasswordGetCodeActivity.class);
				startActivity(intent);
				
			}
		});
		
		mTx_Register = (TextView)findViewById(R.id.tx_register);
		mTx_Register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LogonActivity.this, RegisterGetCodeActivity.class);
				startActivityForResult(intent, 1);
				
			}
		});

	}

	private void initHeaderView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText("登录");
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}

	private void activateLogonButton() {
		ButtonLogon.setEnabled(false);
		if (EditTextPassword.getText().toString() != null
				&& !EditTextPassword.getText().toString().equals("")) {
			if (EditTextName.getText().toString() != null
					&& !EditTextName.getText().toString().equals("")) {
				ButtonLogon.setEnabled(true);
			}
		}

	}

	private void sendLogonRequest() {
		sendSignin();
	}
	
	private void sendSignin()
	{
        String password = EditTextPassword.getText().toString();
        String name = EditTextName.getText().toString();
		String url = NetUrlManager.getUserSignin();
		AsyncHttpClient client = new AsyncHttpClient();
		
		RequestParams params = new RequestParams();
		params.put("password", password);
		params.put("user", name);
		
		client.post(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getSignIn(content);
				if(info.status==0)
				{
					handler.sendEmptyMessage(0);
				}
				else
				{
					showMessageDialog("提示", info.info, "确定", null,0);
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showMessageDialog("提示", "服务器忙请稍后再试", "确定", null,0);
			}
		});
	
	}


	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
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

	public static int compareVersion(String version1, String version2) {
		if (version1.equals(version2)) {
			return 0;
		}
		String[] version1Array = version1.split("//.");
		String[] version2Array = version2.split("//.");
		int index = 0;
		int minLen = Math.min(version1Array.length, version2Array.length);
		int diff = 0;
		while (index < minLen
				&& (diff = Integer.parseInt(version1Array[index])
						- Integer.parseInt(version2Array[index])) == 0) {
			index++;
		}
		if (diff == 0) {
			for (int i = index; i < version1Array.length; i++) {
				if (Integer.parseInt(version1Array[i]) > 0) {
					return 1;
				}
			}
			for (int i = index; i < version2Array.length; i++) {
				if (Integer.parseInt(version2Array[i]) > 0) {
					return -1;
				}
			}
			return 0;
		} else {
			return diff > 0 ? 1 : -1;
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
			 Intent intent = new Intent(LogonActivity.this,MainActivityFragment.class);
			 intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			 startActivity(intent);
			 finish();
			return false; 
			} else if(keyCode == KeyEvent.KEYCODE_MENU) {
		
			
			return false;
			} else if(keyCode == KeyEvent.KEYCODE_HOME) {
		
			return false;
			}
		return super.onKeyDown(keyCode, event);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		switch(requestCode)
		{
		case 1:
		{
			if(resultCode==RESULT_OK)
			{
				Intent intent = new Intent(LogonActivity.this, MainActivityFragment.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				finish();
			}
		}
		break;
	
	
	
		
	
		
		}
		super.onActivityResult(requestCode, resultCode, data);
	
	}
	
	/*public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
		dialog(); 
		return false; 
		} else if(keyCode == KeyEvent.KEYCODE_MENU) {
		// rl.setVisibility(View.VISIBLE);
		Toast.makeText(TestActivity.this, "Menu", Toast.LENGTH_SHORT).show();
		return false;
		} else if(keyCode == KeyEvent.KEYCODE_HOME) {
		//由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
		Toast.makeText(TestActivity.this, "Home", Toast.LENGTH_SHORT).show();
		return false;
		}
		return super.onKeyDown(keyCode, event);
		}*/
	
	
	
	
}
