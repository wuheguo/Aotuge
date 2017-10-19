package com.aotu.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;

import com.aotu.Adapter.CVItemAdapter;
import com.aotu.Adapter.EntranceTicketAdapter;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomListView;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.data.CVItem;
import com.aotu.data.EntranceTicket;
import com.aotu.data.NetReturnInfo;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class EntranceTicketImageActivity  extends BaseActivity{


	ImageView im_er_wei;
	String url;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch(msg.what)
			{
			case 0:
			{
				
			}
			break;
			}
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entrance_ticket_image);
	    url = getIntent().getStringExtra("url")+"&&token="+AotugeApplication.getInstance().mAotuInfo.token;
		initHeadView();
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
	
	private void initHeadView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		RelativeLayout menuleft = (RelativeLayout) findViewById(R.id.left_menu_layout);
		menuleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText("我的门票");
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}
	
	private void initView()
	{
		im_er_wei = (ImageView)findViewById(R.id.im_er_wei);
        if (url!= null && url.length() > 0) {
			
        	im_er_wei.setScaleType(ScaleType.CENTER_CROP);
			ImageLoader.getInstance().displayImage(url,
					im_er_wei);
		}
	}
	
	
}
