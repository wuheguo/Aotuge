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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aotu.Adapter.IntentionListAdapter;
import com.aotu.Adapter.IntentionListAdapter.IntentionInterface;
import com.aotu.Adapter.MyMessageAdapter;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomListView;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.data.IntentionItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;

public class IntentionList extends BaseActivity implements IntentionInterface{

	RelativeLayout rl_next;
	List<IntentionItem> mItemArray = new ArrayList<IntentionItem>();
	IntentionListAdapter mAdapter;
	CustomListView mListView;
    int mDeletePos;
    
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
			IntentionItem item =  mItemArray.get(mDeletePos);
			sendDeleteIntention(String.valueOf(item.id));
			//mItemArray.remove(mDeletePos);
			//handler.sendEmptyMessage(0);
		}
		
	};
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
				  mAdapter = new IntentionListAdapter(IntentionList.this,mItemArray,IntentionList.this);
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
		setContentView(R.layout.activity_intention);
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
		
		getIntentions();
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	private void initView()
	{
		initHeaderView();
		mListView = (CustomListView) findViewById(R.id.list_view);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				AotugeApplication.getInstance().mIntentionItem = mItemArray.get((int)arg3);
				Intent intent = new Intent(IntentionList.this, SetIntentionActivity.class);
				intent.putExtra("type", 0);
				startActivity(intent);
			}
		});
		mListView.setCanRefresh(false);
		mListView.setCanLoadMore(false);
	}
	
	private void initHeaderView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText("求职意向");
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
		
	}
	
	void getIntentions()
	{
		String url = NetUrlManager.getIntentionList()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();

		client.get(url,new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				
				NetReturnInfo info = JosnParser.getIntention(content,mItemArray);
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
	public void onDelete(int pos) {
		// TODO Auto-generated method stub
		
		mDeletePos = pos;
		showNotitleDialog("是否确定删除该项目", "取消","确定", mKTADialongleft,mKTADialongright,"4");
		
	}
	
	public void onAdd(View v)
	{
		Intent intent = new Intent(IntentionList.this, SetIntentionActivity.class);
		intent.putExtra("type", 1);
		startActivity(intent);
	}
	
	private void sendDeleteIntention(String id)
	{
		String url = NetUrlManager.getDeleteIntention(id)+"&token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getDeleteintention(content);
				if(info.success)
				{
					mItemArray.remove(mDeletePos);
					handler.sendEmptyMessage(0);
					//showMessageDialog("提示", info.info, "确定", mKTADialongright,3);
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
