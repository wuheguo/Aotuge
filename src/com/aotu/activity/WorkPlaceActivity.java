package com.aotu.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aotu.Adapter.WorkPlaceAdapter;
import com.aotu.Adapter.WorkPlaceGridAdapter;
import com.aotu.Adapter.WorkPlaceAdapter.WorkPlaceInterface;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomGridView;
import com.aotu.baseview.CustomListView;
import com.aotu.data.CityItem;
import com.aotu.data.ProvinceCityItem;
import com.aotu.data.SchoolItem;
import com.auto.aotuge.R;

public class WorkPlaceActivity extends BaseActivity implements WorkPlaceInterface{

	static String Title = "城市选择";
	CustomListView mListView;
	WorkPlaceAdapter mAdapter;
	List<ProvinceCityItem> mCityItemList = new ArrayList<ProvinceCityItem>();
	int type = 0;	
	TextView mSelcetInfo;
	RelativeLayout mNext;
	CustomGridView mGridView;
    LinearLayout l_tab;
	private Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch(msg.what)
				{
				case 0:
				{
					if(mAdapter == null)
					{
					mAdapter = new WorkPlaceAdapter(WorkPlaceActivity.this,WorkPlaceActivity.this,mCityItemList);
					mListView.setAdapter(mAdapter);
					}
					if(type == 0)
					setViewData();
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
		setContentView(R.layout.activity_workplace);
		type = getIntent().getIntExtra("type", 0);
		if(type == 1)
		{
		l_tab = (LinearLayout)findViewById(R.id.l_tab);
		l_tab.setVisibility(View.GONE);
		}
		initHeadView();
		initView();
		setListData();
		handler.sendEmptyMessage(0);
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
		Intent resultIntent = new Intent();
		WorkPlaceActivity.this.setResult(RESULT_CANCELED, resultIntent);
		super.onBack(v);
	}
	
	private void initView()
	{
		mListView = (CustomListView)findViewById(R.id.list_view);
		mListView.setCanRefresh(false);
		mListView.setCanLoadMore(false);	
		
		mSelcetInfo = (TextView)findViewById(R.id.tx_selcet_info);
		
		mGridView = (CustomGridView)findViewById(R.id.grid_view);
		mGridView.setVisibility(View.GONE);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
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
		
		Button bn_right = (Button) findViewById(R.id.bn_right);
		bn_right.setVisibility(View.VISIBLE);
		bn_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent resultIntent = new Intent();
				if(mCityItemList.get(0).mCityItemList.size() == 0)
				{
					WorkPlaceActivity.this.setResult(RESULT_CANCELED, resultIntent);
				}
				else
				{
					String name = "";
					String code = "{array:[";
					for(int i = 0; i < mCityItemList.get(0).mCityItemList.size() ;i++)
					{
						if(i==mCityItemList.get(0).mCityItemList.size() -1)
						{
							name+=mCityItemList.get(0).mCityItemList.get(i).name;
						    code+=String.valueOf(mCityItemList.get(0).mCityItemList.get(i).code);
						}
						else
						{
						   name+=mCityItemList.get(0).mCityItemList.get(i).name+",";
						   code+=String.valueOf(mCityItemList.get(0).mCityItemList.get(i).code)+",";
						}
					}
					code += "]}";
				    resultIntent.putExtra("name", name);
				    resultIntent.putExtra("code", code);
				    WorkPlaceActivity.this.setResult(RESULT_OK, resultIntent);
				}
				 WorkPlaceActivity.this.finish();
			}
		});
	}

	
	@Override
	public void onShowMore(int pos) {
		// TODO Auto-generated method stub
		mCityItemList.get(pos).mIsShowGridview = !mCityItemList.get(pos).mIsShowGridview;
		handler.sendEmptyMessage(0);
	}

	@Override
	public void onChange(int pos) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void onSeletcWorkPlace(CityItem item) {
		// TODO Auto-generated method stub
		
		
		
		
		
		if(type == 1)
		{
			Intent resultIntent = new Intent();
			resultIntent.putExtra("code", item.code);
			resultIntent.putExtra("name", item.name);
			WorkPlaceActivity.this.setResult(RESULT_OK, resultIntent);
			finish();
		}
		else
		{
			if(mCityItemList.get(0).mCityItemList.size()<3)
			{
			    boolean isexit = false;
			    for(int i = 0; i < mCityItemList.get(0).mCityItemList.size();i++)
			    {
			    	if(mCityItemList.get(0).mCityItemList.get(i).code == item.code)
			    		isexit = true;
			    }
		    
			    if(!isexit)
			    {
				CityItem additem = new CityItem();
				additem.name = item.name;
				additem.Type = 0;
				additem.code = item.code;
				
			    mCityItemList.get(0).mCityItemList.add(additem);
			    mCityItemList.get(0).provinve = "已选择了"+String.valueOf(mCityItemList.get(0).mCityItemList.size())+"个城市";
				handler.sendEmptyMessage(0);
			    }
			   
			}
			 else
			    {
			    	 showMessageDialog("提示", "最多只能选择3个城市", "确定", null,0);
			    }
		}
	}

	@Override
	public void onDeleteWorkPlace(CityItem item) {
		// TODO Auto-generated method stub
		 mCityItemList.get(0).mCityItemList.remove(item);
		 mCityItemList.get(0).provinve = "已选择了"+String.valueOf(mCityItemList.get(0).mCityItemList.size())+"个职位";
		 handler.sendEmptyMessage(0);
	}
	
	private void setListData()
	{
		AotugeApplication.getInstance().mCityItemList.get(0).mCityItemList.clear();
		AotugeApplication.getInstance().mCityItemList.get(0).provinve="";
		if(type == 1)
		{
			//ProvinceCityItem itemData1 = new ProvinceCityItem();
			//mCityItemList.add(itemData1);
			int i =1;
			for(; i < AotugeApplication.getInstance().mCityItemList.size();i++)
			{
			ProvinceCityItem itemData = AotugeApplication.getInstance().mCityItemList.get(i);
			mCityItemList.add(itemData);
			}
		
		}
		else
		{
			AotugeApplication.getInstance().mCityItemList.get(0).provinve="您可以选3个城市";
			int i =0;
			for(; i < AotugeApplication.getInstance().mCityItemList.size();i++)
			{
			ProvinceCityItem itemData = AotugeApplication.getInstance().mCityItemList.get(i);
			mCityItemList.add(itemData);
			}
		}
	}
	
	public void setViewData()
	{
		boolean mIsShowGridview = false;
		mSelcetInfo.setText(mCityItemList.get(0).provinve);
		if( mCityItemList.get(0).mCityItemList.size()>0)
			mIsShowGridview = true;
		else
			mIsShowGridview = false;
		if(mIsShowGridview)
		{
			 mGridView.setVisibility(View.VISIBLE);
		     mGridView.setAdapter(new WorkPlaceGridAdapter(this, "", mCityItemList.get(0).mCityItemList,this));
		}
		else
			mGridView.setVisibility(View.GONE);
		
	}

}
