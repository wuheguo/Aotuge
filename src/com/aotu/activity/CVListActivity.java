package com.aotu.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aotu.Adapter.CVItemAdapter;
import com.aotu.Adapter.CVItemAdapter.CVInterface;
import com.aotu.Adapter.PositionAdapter;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomListView;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.data.CVItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;

public class CVListActivity extends BaseActivity implements CVInterface{

	List<CVItem> mItemList = new ArrayList<CVItem>();
	CVItemAdapter mAdapter;
	CustomListView mListView;
	RelativeLayout mRl_noCV;
	
	Button bn_no_CV,bn_add;
	int DeletePos = 0;
	int type = 0;
	
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
			CVItem item  = mItemList.get(DeletePos);
			 
			deleteCVItem(item);
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
				  mAdapter = new CVItemAdapter(CVListActivity.this,mItemList,CVListActivity.this);
				  mListView.setAdapter(mAdapter);
				}
				if(mItemList.size()>0)
				{
					bn_add.setVisibility(View.VISIBLE);
					mRl_noCV.setVisibility(View.GONE);
				    mAdapter.notifyDataSetChanged();
				}
				else
				{
					mRl_noCV.setVisibility(View.VISIBLE);
					bn_add.setVisibility(View.GONE);
				}
					
			}
			break;
			case 1:
			{
				mItemList.remove(DeletePos);
				handler.sendEmptyMessage(0);
			}
			break;
			}
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cv_list);
		type = getIntent().getIntExtra("type", 0);
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
		getCVItem();
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
		titleText.setText("简历列表");
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
				// TODO Auto-generated method stub
				if(type==0)
				{
				CVItem item  = mItemList.get((int) arg3);
				Intent  resultIntent = new Intent();
				resultIntent.putExtra("cv_id", item.id);
				resultIntent.putExtra("cv_title", item.title);
				CVListActivity.this.setResult(RESULT_OK, resultIntent);
				finish();
				}
				else if(type == 1)
				{
					Intent intent = new Intent(CVListActivity.this,PersonCurriculumVitaeActivity.class);
					CVItem item  = mItemList.get((int) arg3);
					intent.putExtra("cv_id", item.id);
					intent.putExtra("type", 0);
					startActivity(intent);
				}
			}
		});
		
		mRl_noCV = (RelativeLayout)findViewById(R.id.rl_no_cv);
		mRl_noCV.setVisibility(View.GONE);
		
		bn_no_CV = (Button)findViewById(R.id.bn_no_cv);
		bn_no_CV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CVListActivity.this,PersonCurriculumVitaeActivity.class);
				intent.putExtra("type", 0);
				intent.putExtra("cv_id", "0");
				startActivity(intent);
			}
		});
		
		bn_add = (Button)findViewById(R.id.bn_add);
		bn_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
                Intent intent = new Intent(CVListActivity.this,PersonCurriculumVitaeActivity.class);
				intent.putExtra("cv_id", "0");
				intent.putExtra("type", 0);
				startActivity(intent);
			}
		});
		
	}
	
	void getCVItem()
	{
		String url = NetUrlManager.getCVList()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.get(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				
				NetReturnInfo info = JosnParser.getCVItem(content,mItemList);
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

	@Override
	public void onDelete(int pos) {
		// TODO Auto-generated method stub
		DeletePos = pos;
		showNotitleDialog("是否确定删除","取消","确定",mKTADialongleft,mKTADialongright,"12");	
	}
	
	void deleteCVItem(CVItem item)
	{
		String url = NetUrlManager.getDeleteCV(item.id)+"&token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
       
		client.get(url,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				
				NetReturnInfo info = JosnParser.deleteCVItem(content);
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
}
