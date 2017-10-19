package com.aotu.activity;

import java.util.ArrayList;
import java.util.LinkedList;
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

import com.aotu.Adapter.SearchWorkResultAdapter;
import com.aotu.Adapter.SearchWorkResultAdapter.SearchWorkResultInterface;
import com.aotu.Adapter.WorkPageAdapter;
import com.aotu.Adapter.WorkPageAdapter.WorkPageInterface;
import com.aotu.Adapter.WorkPlaceAdapter;
import com.aotu.Adapter.WorkPlaceAdapter.WorkPlaceInterface;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomListView;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.baseview.CustomListView.OnLoadMoreListener;
import com.aotu.baseview.CustomListView.OnRefreshListener;
import com.aotu.data.CityItem;
import com.aotu.data.DirectionsItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.SearchWorkResult;
import com.aotu.data.WorkItem;
import com.aotu.fragment.WorkPageFragment;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;

public class SearchWorkResultActivity extends BaseActivity implements SearchWorkResultInterface, WorkPageInterface{

	static String Title = "找工作";
	
	ArrayList<String> Citys ;
	ArrayList<String> Directions;
	String salaryID ;
	TextView mTabName;
	RelativeLayout mTabNext;
	String workTypeID;
	List<WorkItem> mWorkItems = new ArrayList<WorkItem>();   
	CustomListView mListView;
	WorkPageAdapter mAdapter;
	
    int mPage = 1;
    final static int SEARCH = 1;
    final static int CVID = 2;
    RelativeLayout rl_no_work;
    String mCV_id;
    Button bn_add;
	private Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				mListView.onLoadMoreComplete();
				mListView.onRefreshComplete();
				switch(msg.what)
				{
				case 0:
				{
					if(mAdapter == null)
					{
					mAdapter = new WorkPageAdapter(SearchWorkResultActivity.this, mWorkItems, SearchWorkResultActivity.this);
					mListView.setAdapter(mAdapter);
					}
					mAdapter.notifyDataSetChanged();
					if(mWorkItems.size()>0)
					{
					rl_no_work.setVisibility(View.INVISIBLE);
					}
					else
					{
					rl_no_work.setVisibility(View.VISIBLE);
					}
				}
				break;
				case 1:
				{
					rl_no_work.setVisibility(View.VISIBLE);
				}
				break;
				case 2: {
					sendCV(false);
				}
					break;
				case 7: {
					showMessageDialog("提示", "简历投递成功", "确定", null, 1);
				}
					break;
				
				}
				
			}
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_work_result);
		initHeadView();
		initView();
		getNetData();
		
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
		mListView.setCanRefresh(true);
		mListView.setCanLoadMore(true);
		mListView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				  mPage=1;
				  mWorkItems.clear();
				  searchWork();	
			}
		});
		mListView.setOnLoadListener(new OnLoadMoreListener() {
			
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				mPage++;
				  searchWork();	
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchWorkResultActivity.this, CompanyInfoActivity.class);
				WorkItem item = mWorkItems.get((int) arg3);
				intent.putExtra("cid", item.cid);
				intent.putExtra("id", item.id);

				startActivity(intent);
			}
		});
		rl_no_work = (RelativeLayout)findViewById(R.id.rl_no_work);
		rl_no_work.setVisibility(View.INVISIBLE);
		bn_add = (Button)findViewById(R.id.bn_add);
		bn_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mWorkItems.size() > 0) {
					Intent intent = new Intent(SearchWorkResultActivity.this,
							CVListActivity.class);
					startActivityForResult(intent, CVID);
				}
			}
		});
		bn_add.setVisibility(View.GONE);
	}
	
	private void initHeadView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		RelativeLayout menuleft = (RelativeLayout) findViewById(R.id.left_menu_layout);
		menuleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText(Title);
		
		RelativeLayout btnright = (RelativeLayout)findViewById(R.id.right_layout);
		btnright.setVisibility(View.VISIBLE);
		btnright.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*Intent intent = new Intent(SearchWorkResultActivity.this, SearchWorkActivity.class);
				intent.putExtra("type", 1);
				startActivityForResult(intent,SEARCH);*/
				
				Intent intent = new Intent(SearchWorkResultActivity.this, SearchWorkActivity.class);
				
				startActivity(intent);
				finish();
			}
		});
	}

	
	
	private void getNetData()
	{
		  Bundle bundle=getIntent().getExtras();
		  if(bundle != null)
		  {
		  Citys = bundle.getStringArrayList("Citys");
		  Directions = bundle.getStringArrayList("Directions");
		  salaryID = bundle.getString("salaryID");
		  
		  workTypeID = bundle.getString("workTypeID");
		  searchWork();	
		  }
	}
	
	@Override
	public void onChange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSelect(int pos) {
		// TODO Auto-generated method stub
		if (pos > 0)
			bn_add.setVisibility(View.VISIBLE);
		else
			bn_add.setVisibility(View.GONE);
	}
	
	
	void searchWork()
	{
		String url = NetUrlManager.getSearchWork()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;
		url+="&page="+String.valueOf(mPage);
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		for(int i = 0; i <Citys.size();i++)
		{
			String tab = "cities["+String.valueOf(i)+"]";
			params.put(tab, Citys.get(i));
		}
		
		for(int i = 0; i <Directions.size();i++)
		{
			String tab = "position["+String.valueOf(i)+"]";
			params.put(tab, Directions.get(i));
		}

		params.put("salary",salaryID );
		params.put("workType",workTypeID );
		client.post(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
			
				//bn_add.setVisibility(View.GONE);
				NetReturnInfo info = JosnParser.getWorkList(content, mWorkItems);
				if(info.success)
					handler.sendEmptyMessage(0);
				else
				{
					switch(info.status)
					{
					case -1://搜索失败
					{
						
					}
						break;
					case -2://搜索无结果
					{
						handler.sendEmptyMessage(1);
					}
						break;
					case -11://参数错误
					{
						
					}
						break;
					}
				}
				
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(requestCode)
		{
			case SEARCH:
			{
				if(resultCode==-1)
				{
				  Bundle bundle=data.getExtras();
				  Citys = bundle.getStringArrayList("Citys");
				  Directions = bundle.getStringArrayList("Directions");
				  salaryID = bundle.getString("salaryID");
				  
				  workTypeID = bundle.getString("workTypeID");
				  mPage=1;
				  mWorkItems.clear();
				  searchWork();	
				 
				}
			}
			break;
			case CVID: {
				if (resultCode == -1) {
					mCV_id = data.getStringExtra("cv_id");
					handler.sendEmptyMessage(2);
				}
			}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onCheck(int pos) {
		// TODO Auto-generated method stub
		if (pos > 0)
			bn_add.setVisibility(View.VISIBLE);
		else
			bn_add.setVisibility(View.GONE);
	}
	
	private void sendCV(boolean forbidden) {
		String url = NetUrlManager.getSendCvURL() + "?token="
				+ AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		String jd_id = "";
		for (int i = 0; i < mWorkItems.size(); i++) {
			if (mWorkItems.get(i).isSelect) {
				String tab = "jd_id[" + String.valueOf(i) + "]";
				jd_id = String.valueOf(mWorkItems.get(i).id);
				params.put(tab, jd_id);
			}
		}
		params.put("cv_id", mCV_id);
		if (forbidden)
			params.put("forbidden", "1");
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getCVSend(content);
				if (info.success) {
					showMessageDialog("提示", "简历投递成功", "确定", null, 1);
				} else {
					switch (info.status) {
					case -1:
						showMessageDialog("提示", info.info, "确定", null, 0);
						break;
					case -2:
						showNotitleDialog(info.info, "强行投递", "去完善",
								mKTADialongleft, mKTADialongright, "-2");
						break;
					case -3:
						showNotitleDialog(info.info, "强行投递", "去完善",
								mKTADialongleft, mKTADialongright, "-3");
						break;

					}

				}

			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);

			}
		});

	}
	
	KTAlertDialogOnClickListener mKTADialongleft = new KTAlertDialogOnClickListener() {

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			sendCV(true);
		}

	};

	KTAlertDialogOnClickListener mKTADialongright = new KTAlertDialogOnClickListener() {

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(SearchWorkResultActivity.this,
					PersonCurriculumVitaeActivity.class);
			intent.putExtra("type", 0);
			intent.putExtra("cv_id", "0");
			startActivity(intent);
		}

	};
	
	
}
