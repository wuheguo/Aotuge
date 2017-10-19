package com.aotu.activity;
/*
 * 职位意向选择
 */

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aotu.Adapter.PositionAdapter;
import com.aotu.Adapter.PositionGridAdapter;
import com.aotu.Adapter.WorkPlaceGridAdapter;
import com.aotu.Adapter.PositionAdapter.PositionInterface;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomGridView;
import com.aotu.baseview.CustomListView;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.DirectionGridItem;
import com.aotu.data.DirectionsItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.auto.aotuge.R;

public class DirectionsActivity extends BaseActivity implements PositionInterface{

	static String Title = "职位选择";
	CustomListView mListView;
	PositionAdapter mAdapter;
	List<DirectionsItem> mData = new LinkedList<DirectionsItem>();
    
	TextView mSelcetInfo;
	RelativeLayout mNext;
	CustomGridView mGridView;
	private Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch(msg.what)
				{
				case 0:
				{
					if(mAdapter == null)
					{
					mAdapter = new PositionAdapter(DirectionsActivity.this,mData,DirectionsActivity.this);
					mListView.setAdapter(mAdapter);
					}
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
		getPositionData();
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

	@Override
	public void onBack(View v) {
		// TODO Auto-generated method stub
		Intent resultIntent = new Intent();
	    DirectionsActivity.this.setResult(RESULT_CANCELED, resultIntent);
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
	
	public void setViewData()
	{

		mSelcetInfo.setText(mData.get(0).Name);
		
		//if(mData.get(0).mIsShowGridview)
		{
			 mGridView.setVisibility(View.VISIBLE);
		     mGridView.setAdapter(new PositionGridAdapter(this,mData.get(0).mGridItemList,this));
		}
//		else
//			mGridView.setVisibility(View.GONE);
		
		
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
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				Intent resultIntent = new Intent();
				if(mData.size() == 0)
				{
					 DirectionsActivity.this.setResult(RESULT_CANCELED, resultIntent);
				}
				else if(mData.get(0).mGridItemList.size() == 0)
				{
					 DirectionsActivity.this.setResult(RESULT_CANCELED, resultIntent);
				}
				else
				{
					String name = "";
					String code = "{array:[";
					for(int i = 0; i < mData.get(0).mGridItemList.size() ;i++)
					{
						if(i==mData.get(0).mGridItemList.size() -1)
						{
							name+=mData.get(0).mGridItemList.get(i).Name;
						    code+=String.valueOf(mData.get(0).mGridItemList.get(i).Code);
						}
						else
						{
						   name+=mData.get(0).mGridItemList.get(i).Name+",";
						   code+=String.valueOf(mData.get(0).mGridItemList.get(i).Code)+",";
						}
					}
					code += "]}";
				    resultIntent.putExtra("name", name);
				    resultIntent.putExtra("code", code);
				    DirectionsActivity.this.setResult(RESULT_OK, resultIntent);
				}
				 DirectionsActivity.this.finish(); 
			
			}
		});
	}
			
		
	@Override
	public void onSeletcPosition(DirectionGridItem item) {
		// TODO Auto-generated method stub
	    if(mData.get(0).mGridItemList.size()<3)
	    {
	    	boolean isexit = false;
	    	for(int i =0; i < mData.get(0).mGridItemList.size();i++)
	    	{
	    		DirectionGridItem aitem = mData.get(0).mGridItemList.get(i);
	    		if(aitem.Code == item.Code)
	    			isexit = true;
	    	}
	    	if(!isexit)
	    	{
				DirectionGridItem additem = new DirectionGridItem();
				//additem.Name = item.ParentName+"-"+item.Name;
				additem.Name = item.Name;
				additem.Type = 0;
				additem.Code = item.Code;
			
				mData.get(0).mGridItemList.add(additem);
				mData.get(0).mIsShowGridview = true;
				mData.get(0).Name = "已选择了"+String.valueOf(mData.get(0).mGridItemList.size())+"个职位";
				handler.sendEmptyMessage(0);
	    	}
	    }
	    else
	    {
	    	 showMessageDialog("提示", "最多只能申请3个职位", "确定", null,0);
	    }
	}

	@Override
	public void onDeletePosition(DirectionGridItem item) {
		// TODO Auto-generated method stub
		mData.get(0).mGridItemList.remove(item);
		mData.get(0).Name = "已选择了"+String.valueOf(mData.get(0).mGridItemList.size())+"个职位";
		if(mData.get(0).mGridItemList.size() == 0)
			mData.get(0).mIsShowGridview = false;
		handler.sendEmptyMessage(0);
	}

	@Override
	public void onShowMore(int pos) {
		// TODO Auto-generated method stub
		mData.get(pos).mIsShowGridview = !mData.get(pos).mIsShowGridview;
		handler.sendEmptyMessage(0);
	}

	@Override
	public void onChange(int pos) {
		// TODO Auto-generated method stub
		
	}
	
	private void getPositionData()
	{
		
		String url = NetUrlManager.getPostionUrl();
		AsyncHttpClient client = new AsyncHttpClient();

		client.get(url,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getPosition(content,mData);
				if(info.success)
				{
					
					mData.get(0).mIsShowGridview = false;
					handler.sendEmptyMessage(0);
				}
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
