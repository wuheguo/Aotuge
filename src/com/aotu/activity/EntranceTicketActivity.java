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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

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

public class EntranceTicketActivity  extends BaseActivity{


	List<EntranceTicket> mItemList = new ArrayList<EntranceTicket>();
	EntranceTicketAdapter mAdapter;
	CustomListView mListView;

	
	
	
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
			switch(msg.what)
			{
			case 0:
			{
				if(mAdapter == null)
				{
				  mAdapter = new EntranceTicketAdapter(EntranceTicketActivity.this,mItemList);
				  mListView.setAdapter(mAdapter);
				}
			    mAdapter.notifyDataSetChanged();	
			}
			break;
			}
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entrance_ticket_list);
	
		initHeadView();
		initView();
		getItem();
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
		mListView = (CustomListView)findViewById(R.id.list_view);
		mListView.setCanLoadMore(false);
		mListView.setCanRefresh(false);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(EntranceTicketActivity.this,EntranceTicketImageActivity.class);
				String url = mItemList.get((int)arg3).qr;
				intent.putExtra("url", url);
				startActivity(intent);
			}
		});
		
		
		
	}
	
	void getItem()
	{
		String url = NetUrlManager.getTicketURL()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.get(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				
				NetReturnInfo info = JosnParser.getTicket(content,mItemList);
				if(info.success)
					handler.sendEmptyMessage(0);
				else
				    showMessageDialog("提示", info.info, "确定", null,1);
				
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showMessageDialog("提示", "服务器忙请稍后再试", "确定", null,0);
			}
		});
	
	}
}
