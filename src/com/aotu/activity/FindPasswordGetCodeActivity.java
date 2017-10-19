package com.aotu.activity;
/*
 * 找回密码
 */
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.aotu.data.NetReturnInfo;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;


public class FindPasswordGetCodeActivity extends BaseActivity implements OnClickListener{
	
	static final int INTERVAL_SEND_CODE = 1000 * 60;// 发送验证码时间间隔,1分钟
	
    EditText mEd_phone_number,mEd_Code,mEd_new_password;
    TextView mTx_Call_kefu,mTx_Logon;
    Button mBn_getcode,mBn_Next,mBn_ClearPhonenumber,mBn_ClearNewPassword;
    
    TimeCount time;
    
   
	
    TextWatcher mPhonenumberTextWatcher = new TextWatcher() {

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
			if (mEd_phone_number.getText().toString() != null
					&& !mEd_phone_number.getText().toString().equals("")) {
				mBn_ClearPhonenumber.setVisibility(View.VISIBLE);

			} else {
				mBn_ClearPhonenumber.setVisibility(View.INVISIBLE);
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
				if (mEd_new_password.getText().toString() != null
						&& !mEd_new_password.getText().toString().equals("")) {
					mBn_ClearNewPassword.setVisibility(View.VISIBLE);

				} else {
					mBn_ClearNewPassword.setVisibility(View.INVISIBLE);
				}
			}
		};

	TextWatcher mCodeTextWatcher = new TextWatcher() {

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
			if (mEd_Code.getText().toString() != null
					&& !mEd_Code.getText().toString().equals("")) {
				
			} else {
				
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
			String sState = (String)button.getTag();
			int state = Integer.valueOf(sState);
			switch(state)
			{
				case 0:
				{
					
				}
				break;
				case 1:
				{
					AotugeApplication.getInstance().mIsNullAotuInfo = true;
					AotugeApplication.getInstance().deleteUserInfo();
					
					Intent intent = new Intent(FindPasswordGetCodeActivity.this, LogonActivity.class);
					startActivity(intent);
					finish();
				}
				break;
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_password);
		initHeadView();
		initView();
		time = new TimeCount(INTERVAL_SEND_CODE, 1000);
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
		mEd_phone_number = (EditText)findViewById(R.id.ed_phone_number);
		mEd_phone_number.addTextChangedListener(mPhonenumberTextWatcher);
		
		mBn_ClearPhonenumber = (Button)findViewById(R.id.bn_phone_number_clear);
		mBn_ClearPhonenumber.setOnClickListener(this);
		
		mEd_new_password = (EditText)findViewById(R.id.ed_password);
		mEd_new_password.addTextChangedListener(mPasswordTextWatcher);
		
		mBn_ClearNewPassword = (Button)findViewById(R.id.bn_password_clear);
		mBn_ClearNewPassword.setOnClickListener(this);
		
		
		mEd_Code = (EditText)findViewById(R.id.ed_code);
		mEd_Code.addTextChangedListener(mCodeTextWatcher);
		
		mTx_Call_kefu = (TextView)findViewById(R.id.tx_call_kefu);
		mTx_Call_kefu.setOnClickListener(this);
		
		mTx_Logon = (TextView)findViewById(R.id.tx_logon);
		mTx_Logon.setOnClickListener(this);
		
		mBn_getcode = (Button)findViewById(R.id.bn_get_code);
		mBn_getcode.setOnClickListener(this);
		
		mBn_Next = (Button)findViewById(R.id.bn_next);
		mBn_Next.setOnClickListener(this);
	}
	
	private void initHeadView() {
		String Title = "找回密码";
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		RelativeLayout menuleft = (RelativeLayout) findViewById(R.id.left_menu_layout);
		menuleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText(Title);
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}
	
	private void activateLogonButton() {
		mBn_Next.setEnabled(false);
		if (mEd_phone_number.getText().toString() != null
				&& !mEd_phone_number.getText().toString().equals("")) {
			if (mEd_Code.getText().toString() != null
					&& !mEd_Code.getText().toString().equals("")) {
				if(mEd_new_password.getText().toString()!=null&&!mEd_new_password.getText().toString().equals(""))
				mBn_Next.setEnabled(true);
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
			   Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +AotugeApplication.getInstance().mBaseDataItem.hotline));
              
               startActivity(intent);
		}
			break;
		case R.id.tx_logon:
		{
			finish();
		}
			break;
		case R.id.bn_get_code:
		{
			if (mEd_phone_number.getText().toString() != null
					&& !mEd_phone_number.getText().toString().equals(""))
			{
			String mobile = mEd_phone_number.getText().toString();
			onSendGetCode(mobile);
			time.start();
			}
			else
			{
			 showMessageDialog("提示", "服务器忙请稍后再试", "确定", null,0);
			}
		}
		break;
		case R.id.bn_next:
		{
			int lenght = mEd_new_password.getText().toString().length();
			if(lenght>=8)
			    onSendGetNewPassword();
			else
				Toast.makeText(FindPasswordGetCodeActivity.this, "密码长度小于8位", Toast.LENGTH_SHORT).show();
		}
		break;
		case R.id.bn_phone_number_clear:
		{
			mEd_phone_number.setText("");
		}
		break;
		case R.id.bn_password_clear:
		{
			mEd_new_password.setText("");
		}
		break;
		}
	}
	
	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mBn_getcode.setEnabled(false);
			mBn_getcode.setText(millisUntilFinished / 1000 + "秒后重发");
		}

		@Override
		public void onFinish() {
			mBn_getcode.setText("重新获取验证码");
			mBn_getcode.setEnabled(true);

		}
	}
	
	
	private void onSendGetNewPassword()
	{
		String url = NetUrlManager.getFindPaddwordUrl();
		AsyncHttpClient client = new AsyncHttpClient();
		String password = mEd_new_password.getText().toString();
		String mobile = mEd_phone_number.getText().toString();
		String code = mEd_Code.getText().toString();
		RequestParams params = new RequestParams();
		params.put("password", password);
		params.put("mobile", mobile);
		params.put("code", code);
		
		client.post(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getuserresetpwd(content);
				showMessageDialog("提示", info.info, "确定", mKTADialongright,1);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showMessageDialog("提示", "服务器忙请稍后再试", "确定", null,0);
			}
		});
	}
	
	
	
	private void onSendGetCode(String mobile)
	{
		String url = NetUrlManager.getCodeUrl(mobile);
		AsyncHttpClient client = new AsyncHttpClient();
		
		
		client.get(url,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getCodeData(content);
				if(!info.success)
				{
				   showMessageDialog("提示", info.info, "确定",null,0);
				}
				else
				{
					/*AotugeApplication.getInstance().mIsNullAotuInfo = true;
					AotugeApplication.getInstance().deleteUserInfo();
					
					Intent intent = new Intent(FindPasswordGetCodeActivity.this, LogonActivity.class);
					startActivity(intent);
					finish();*/
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showMessageDialog("提示", "服务器忙请稍后再试", "确定", null,0);
			}
		});
	}
	

}
