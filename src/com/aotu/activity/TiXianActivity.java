package com.aotu.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomPopupMenu;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.data.NetReturnInfo;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.aotu.utils.FileManager;
import com.auto.aotuge.R;

public class TiXianActivity extends BaseActivity{

	EditText ed_amount,ed_account,ed_location;
	String amount,account,location;
	Button bn_save;
	RelativeLayout rl_way;
	static final int WAY = 1;
	TextView tx_way,tx_money;
	String way;
	String money;
	
	KTAlertDialogOnClickListener rightListener = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			finish();
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ti_xian);
		sendGetTixianInfo();
		money = "可提现金额:"+getIntent().getStringExtra("balance");
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

	@Override
	public void onBack(View v) {
		// TODO Auto-generated method stub
		super.onBack(v);
	}
	
	private void initView()
	{
		initHeadView();
		ed_amount = (EditText)findViewById(R.id.ed_amount);
		ed_account = (EditText)findViewById(R.id.ed_account);
		ed_location = (EditText)findViewById(R.id.ed_location);
		bn_save = (Button)findViewById(R.id.bn_save);
		bn_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				amount = ed_amount.getText().toString();
				account = ed_account.getText().toString();
				location = ed_location.getText().toString();
				sendTixianToService();
			}
		});
		rl_way = (RelativeLayout)findViewById(R.id.rl_way);
		rl_way.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TiXianActivity.this, CustomPopupMenu.class);
				intent.putExtra("type", 5);
				startActivityForResult(intent, WAY);
			}
		});
		tx_way = (TextView)findViewById(R.id.tx_way);
		tx_money = (TextView)findViewById(R.id.tx_money);
		tx_money.setText(money+"元");
		
	}
	private void initHeadView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		RelativeLayout menuleft = (RelativeLayout) findViewById(R.id.left_menu_layout);
		menuleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText("提取现金");
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}
	
	
	private void sendTixianToService()
	{
		String url = NetUrlManager.getTixianUrl()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("amount", amount);
		params.put("account", account);
		params.put("name", location);
		params.put("way", way);
		
	
		client.post(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getTixian(content);
				if(info.success)
				{
					
					showMessageDialog("提示", info.info, "确定", rightListener,0);
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
	
	private void sendGetTixianInfo()
	{
		String url = NetUrlManager.getTixianInfoUrl()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		
		
	
		client.get(url,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getTixianInfo(content);
				if(info.status==0)
				{
					ed_location.setText(AotugeApplication.getInstance().mTixianInfo.name);
					ed_account.setText(AotugeApplication.getInstance().mTixianInfo.account);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(requestCode)
		{
		
		case WAY:
		{
			if(resultCode == RESULT_OK)
			{
				int pos = data.getIntExtra("typeid",0);
				way = AotugeApplication.getInstance().mTixianInfo.Ways.get(pos).code;
				String info = data.getStringExtra("typename");
				tx_way.setText(info);
			}
		}
		break;
		
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
