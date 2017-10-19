package com.aotu.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.aotu.Adapter.MyMessageAdapter;
import com.aotu.Adapter.ReconciliationsAdapter;
import com.aotu.Adapter.SchoolAdapter;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomListView;
import com.aotu.data.CityItem;
import com.aotu.data.MyMessageItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.ReconciliationsItem;
import com.aotu.data.SchoolItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;
/*
 * 个人信息
 */
public class MyMessageActivity extends BaseActivity{

	
	static String Title = "个人信息";
	CustomListView mListView;
	MyMessageAdapter mAdapter;
	List<MyMessageItem> mData = new ArrayList<MyMessageItem>();
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
			mAdapter.notifyDataSetChanged();
			}
			break;
			case 1:{
				if(mAdapter == null)
				{
				  mAdapter = new MyMessageAdapter(MyMessageActivity.this,mData);
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
		setContentView(R.layout.activity_reconciliations);
		initHeadView();
		initView();
		setMessage();
		//getReconciliations();
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
	
	private void initView()
	{
		mListView = (CustomListView)findViewById(R.id.list_view);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				
			}
		});
		mListView.setCanRefresh(false);
		mListView.setCanLoadMore(false);
	}
	
	private void initHeadView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		RelativeLayout menuleft = (RelativeLayout) findViewById(R.id.left_menu_layout);
		menuleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText(Title);
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}
	
	private void getReconciliations()
	{
		mData.clear();
		String url = NetUrlManager.getReconciliations();
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);

		client.post(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
//				NetReturnInfo info = JosnParser.getSchoolonCity(content,mData);
//				if(info.success)
//					handler.sendEmptyMessage(1);
//				else
//				    showMessageDialog("提示", info.info, "确定", null,1);
				
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showMessageDialog("提示", "服务器忙请稍后再试", "确定", null,0);
			}
		});
	}
	
	private void setMessage()
	{
		MyMessageItem item = new MyMessageItem();
		item.messag = "我的信心 我的信息 我的信心";
		item.date = "2015-08-09   15:32:23";
		
		mData.add(item);
		MyMessageItem item1 = new MyMessageItem();
		item1.messag = "我的信心 我的信息 我的信心";
		item1.date = "2015-08-09   15:32:23";
		
		mData.add(item1);
		handler.sendEmptyMessage(1);
	}

}
