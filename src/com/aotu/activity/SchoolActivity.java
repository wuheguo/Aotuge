package com.aotu.activity;
/*
 * 学校
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.aotu.Adapter.SchoolAdapter;
import com.aotu.Adapter.SchoolAdapter.SchoolInterface;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomListView;
import com.aotu.baseview.CustomPopupMenu;
import com.aotu.data.CityItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.SchoolItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.auto.aotuge.R;


public class SchoolActivity extends BaseActivity implements SchoolInterface{
    static String Title = "院校选择";
	CustomListView mListView;
	SchoolAdapter mAdapter;
	List<SchoolItem> mData = new ArrayList<SchoolItem>();
	CityItem mCityItem = new CityItem();
	SchoolItem mSchoolItem = null;
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
				  mAdapter = new SchoolAdapter(SchoolActivity.this,mData,SchoolActivity.this);
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
		setContentView(R.layout.activity_school);
		initHeadView();
		initView();
		//getCityData();
		mCityItem.name = "北京";
		mCityItem.code = 110000;
		getSchoolData(mCityItem);
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
		mListView = (CustomListView)findViewById(R.id.list_view);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if((int)arg3 != 0)
				{
				Intent resultIntent = new Intent();
				SchoolItem item = mData.get(arg2-1);
				resultIntent.putExtra("code", item.code);
				resultIntent.putExtra("name", item.name);
				SchoolActivity.this.setResult(RESULT_OK, resultIntent);
				finish();
				}
				else
				{
					onChange();
				}
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

	@Override
	public void onChange() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SchoolActivity.this, CustomPopupMenu.class);
		intent.putExtra("type", 3);
		startActivityForResult(intent, 1);
	}
	
	
	
	private void getSchoolData(CityItem cityitem)
	{
		mData.clear();
		SchoolItem item = new SchoolItem();
		item.code = cityitem.code;
		item.name = cityitem.name;
		mData.add(item);
		
		String url = NetUrlManager.getSchoolUrl(String.valueOf(cityitem.code));
		AsyncHttpClient client = new AsyncHttpClient();

		client.get(url,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getSchoolonCity(content,mData);
				if(info.success)
					handler.sendEmptyMessage(1);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) 
		{
			
					if(resultCode==RESULT_OK)
					{
					String name = data.getStringExtra("typename");
					mCityItem.name = name;
					int pos = data.getIntExtra("typeid",0);
					mCityItem.code =AotugeApplication.getInstance().mCityItemList.get(pos).code ;
					getSchoolData(mCityItem);
					}
				
			
		}
	}
	
	
	

}
