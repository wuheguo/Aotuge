package com.aotu.activity;

import java.util.ArrayList;
import java.util.List;

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

import com.aotu.Adapter.ReconciliationsAdapter;
import com.aotu.Adapter.SchoolAdapter;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomListView;
import com.aotu.data.CityItem;
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
 * 账单列表
 */
public class ReconciliationsActivity extends BaseActivity{

	
	static String Title = "账单列表";
	CustomListView mListView;
	ReconciliationsAdapter mAdapter;
	List<ReconciliationsItem> mData = new ArrayList<ReconciliationsItem>();
	TextView tx_money;
	Button bn_tixian;
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
				  mAdapter = new ReconciliationsAdapter(ReconciliationsActivity.this,mData);
				  mListView.setAdapter(mAdapter);
				}
				if(mData.size()>0)
				tx_money.setText(mData.get(0).balance+"元");
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
		getReconciliations();
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
		bn_tixian = (Button)findViewById(R.id.bn_tixian);
		bn_tixian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ReconciliationsActivity.this,TiXianActivity.class);
				if(mData.size()>0)
				   intent.putExtra("balance",mData.get(0).balance);
				
				startActivity(intent);
			}
		});
		tx_money = (TextView)findViewById(R.id.tx_money);
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
		String url = NetUrlManager.getReconciliations()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;;
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);

		client.post(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getReconciliations(content,mData);
				if(info.success)
				{
					handler.sendEmptyMessage(1);
					tx_money.setText(info.info);
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
