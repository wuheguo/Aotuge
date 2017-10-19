package com.aotu.activity;
/*
 * 注册
 */
import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.protocol.ClientContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.DirectionsItem;
import com.aotu.data.SchoolItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;

public class RegisterActivity extends BaseActivity implements OnClickListener{
	static String Title = "新用户注册";
	static final int SCHOOL = 1;
	static final int INTENTION = 4;
	static final int CITY = 2;
	static final int GOMAIN_OR_USERINFO = 3;
	
	static final int ERROE = 4;
	
    EditText mEd_password,mEd_name;
    TextView mTx_Call_kefu,mTx_Logon,mTx_Position,mTx_School,tx_city;
    Button mBn_Register,mBn_ClearPassword,mBn_Clearname;
    SchoolItem mSchoolItem = new SchoolItem();
  
    
    List<String> Directions = new ArrayList<String>();
    List<String> Citys = new ArrayList<String>();
    
    String mPassword ="";
    String mName ="";
    private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
		  switch(msg.what)
		  {
		   default:
			   break;
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
			if (mEd_password.getText().toString() != null
					&& !mEd_password.getText().toString().equals("")) {
				mBn_ClearPassword.setVisibility(View.VISIBLE);
			} else {
				mBn_ClearPassword.setVisibility(View.INVISIBLE);
			}
		}
	};

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
			if (mEd_name.getText().toString() != null
					&& !mEd_name.getText().toString().equals("")) {
				mBn_Clearname.setVisibility(View.VISIBLE);
			} else {
				mBn_Clearname.setVisibility(View.INVISIBLE);
			}
		}
	};
	
	KTAlertDialogOnClickListener mKTADialongleft = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			String sState = (String)button.getTag();
			int state = Integer.valueOf(sState);
			switch(state)
			{
				case GOMAIN_OR_USERINFO:
				{
					
				    Intent resultIntent = new Intent();
					RegisterActivity.this.setResult(RESULT_OK, resultIntent);
					finish();
					
					//Intent intent = new Intent(RegisterActivity.this, MainActivityFragment.class);
					//startActivity(intent);
				}
				break;
			}
		}
		
	};
	
	KTAlertDialogOnClickListener mKTADialongright = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			String sState = (String)button.getTag();
			int state = Integer.valueOf(sState);
			switch(state)
			{
				case GOMAIN_OR_USERINFO:
				{
					Intent intent = new Intent(RegisterActivity.this, UserInfoActivity.class);
					
					startActivityForResult(intent,GOMAIN_OR_USERINFO);
					
				}
				break;
				case 14:
				{
					Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+AotugeApplication.getInstance().mBaseDataItem.hotline));  
		            startActivity(intent); 
				}
				break;
			}
		}
		
	};

	@Override
	public void onBack(View v) {
		// TODO Auto-generated method stub
		Intent resultIntent = new Intent();
		this.setResult(RESULT_CANCELED, resultIntent);
		super.onBack(v);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initHeadView();
		initView();
	
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

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	private void initView()
	{
		mEd_password = (EditText)findViewById(R.id.ed_password);
		mEd_password.addTextChangedListener(mPasswordTextWatcher);
		mEd_name = (EditText)findViewById(R.id.ed_name);
		mEd_name.addTextChangedListener(mNameTextWatcher);
		
		mTx_Call_kefu = (TextView)findViewById(R.id.tx_call_kefu);
		mTx_Call_kefu.setOnClickListener(this);
		
		mTx_Logon = (TextView)findViewById(R.id.tx_logon);
		mTx_Logon.setOnClickListener(this);
		
		mTx_School = (TextView)findViewById(R.id.tx_school);
		mTx_School.setOnClickListener(this);
		
		mTx_Position = (TextView)findViewById(R.id.tx_position);
		mTx_Position.setOnClickListener(this);
		
		mBn_ClearPassword = (Button)findViewById(R.id.bn_password_clear);
		mBn_ClearPassword.setOnClickListener(this);
		
		mBn_Clearname = (Button)findViewById(R.id.bn_name_clear);
		mBn_Clearname.setOnClickListener(this);
		
		mBn_Register = (Button)findViewById(R.id.bn_register);
		mBn_Register.setOnClickListener(this);	
		
		tx_city = (TextView)findViewById(R.id.tx_city);
		tx_city.setOnClickListener(this);
	}
	
	private void initHeadView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.INVISIBLE);
		RelativeLayout menuleft = (RelativeLayout) findViewById(R.id.left_menu_layout);
		menuleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText(Title);
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}
	
	private void activateLogonButton() {
		mBn_Register.setEnabled(false);
		if (mEd_name.getText().toString() != null
				&& !mEd_name.getText().toString().equals("")) {
			if (mEd_password.getText().toString() != null
					&& !mEd_password.getText().toString().equals("")) {
				mBn_Register.setEnabled(true);
			}
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.tx_call_kefu:
		{
			showNotitleDialog("是否联系客服:"+AotugeApplication.getInstance().mBaseDataItem.hotline, "取消", "拨打", mKTADialongleft, mKTADialongright, "14");
		}
			break;
		case R.id.tx_logon:
		{
			Intent intent = new Intent(RegisterActivity.this, LogonActivity.class);
			startActivity(intent);
			finish();
		}
			break;
		case R.id.tx_school:
		{
			Intent intent = new Intent(RegisterActivity.this, SchoolActivity.class);
			startActivityForResult(intent, SCHOOL);
			
		}
			break;
		case R.id.tx_position:
		{
			Intent intent = new Intent(RegisterActivity.this,DirectionsActivity.class);
			startActivityForResult(intent, INTENTION);
		}
		break;
		case R.id.tx_city:
		{
			Intent intent = new Intent(RegisterActivity.this,WorkPlaceActivity.class);
			intent.putExtra("type", 0);
			startActivityForResult(intent, CITY);
		}
		break;
		
		case R.id.bn_register:
		{
			int lenght = mEd_password.getText().toString().length();
			if(lenght>=8)
				sendRegisterInfo();
			else
				Toast.makeText(RegisterActivity.this, "密码长度小于8位", Toast.LENGTH_SHORT).show();
			
		}
		break;
		case R.id.bn_password_clear:
		{
			mEd_password.setText("");
		}
		break;
		case R.id.bn_name_clear:
		{
			mEd_name.setText("");
		}
		break;
		}
	}
	
	
	
	private void sendRegisterInfo()
	{
        String password = mEd_password.getText().toString();
        String name = mEd_name.getText().toString();
        mName = name;
		String url = NetUrlManager.getUserInfoSetUrl()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("platform", "android");
		RequestParams params = new RequestParams();
		params.put("password",  password);
		params.put("name",  name);
		params.put("school",  String.valueOf(mSchoolItem.code));
		
		for(int i = 0; i <Directions.size();i++)
		{
			String tab = "intention[position]["+String.valueOf(i)+"]";
			params.put(tab, Directions.get(i));
		}
		for(int i = 0; i <Citys.size();i++)
		{
			String tab = "intention[cities]["+String.valueOf(i)+"]";
			params.put(tab, Citys.get(i));
		}
		
		
		client.post(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getRegister(content);
				
				if(info.success)
				{
					 showNotitleDialog("注册成功", "去首页", "完善资料", mKTADialongleft, mKTADialongright, String.valueOf(GOMAIN_OR_USERINFO));
				}
				else
				{
				     showMessageDialog("提示", info.info, "确定", mKTADialongright,2);
				 
				}
				
				
				
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showMessageDialog("提示", "服务器忙请稍后再试", "确定", null,0);
			}
		});
	
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(requestCode)
		{
		case SCHOOL:
		{
			if(resultCode == RESULT_OK)
			{
				String name = data.getStringExtra("name");
				int code = data.getIntExtra("code", 0);
				mSchoolItem.name = name;
				mSchoolItem.code = code;
				mTx_School.setText(name);
			}
		}	
		break;
		case INTENTION:
		{
			if(resultCode == RESULT_OK)
			{
				String name = data.getStringExtra("name");
				String code = data.getStringExtra("code");
				 JosnParser.getDataArray(code,Directions);
				 mTx_Position.setText(name);
				
				mTx_Position.setText(name);
			}
		}
		break;
		case CITY:
		{
			if(resultCode==RESULT_OK)
			{
				String name = data.getStringExtra("name");
				String code = data.getStringExtra("code");
				JosnParser.getDataArray(code,Citys);
				tx_city.setText(name);
			}
		}
		break;
		case GOMAIN_OR_USERINFO:
		{
			if(resultCode==RESULT_OK)
			{
				   Intent resultIntent = new Intent();
					RegisterActivity.this.setResult(RESULT_OK, resultIntent);
					finish();
			}
		}
		break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
