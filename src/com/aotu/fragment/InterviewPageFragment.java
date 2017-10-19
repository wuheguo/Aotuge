package com.aotu.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.aotu.Adapter.InterviewAdapter;
import com.aotu.Adapter.InterviewAdapter.InterviewInterface;
import com.aotu.activity.InterviewCalendarViewActivity;
import com.aotu.activity.InterviewDetailsCompleteActivity;
import com.aotu.activity.InterviewDetailsCurCompleteActivity;
import com.aotu.activity.InterviewDetailsPreCompleteActivity;
import com.aotu.activity.InterviewScoreActivity;
import com.aotu.activity.MainActivityFragment;
import com.aotu.activity.SetIntentionActivity;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.CustomListView;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.baseview.CustomListView.OnLoadMoreListener;
import com.aotu.baseview.CustomListView.OnRefreshListener;
import com.aotu.data.InterviewTimeItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;

public class InterviewPageFragment  extends Fragment implements OnClickListener, InterviewInterface{
	Context mContext;
	View mBaseView;
	List<InterviewTimeItem> mleftItmeArray = new ArrayList<InterviewTimeItem>();
	List<InterviewTimeItem> mmidItmeArray = new ArrayList<InterviewTimeItem>();
	List<InterviewTimeItem> mrightItmeArray = new ArrayList<InterviewTimeItem>();
	List<InterviewTimeItem> mright2ItmeArray = new ArrayList<InterviewTimeItem>();
	CustomListView mListView;
	InterviewAdapter mleftInterviewAdapter;
	InterviewAdapter mmidInterviewAdapter;
	InterviewAdapter mrightInterviewAdapter;
	InterviewAdapter mright2InterviewAdapter;
	TextView tv_tab_left;
	TextView tv_tab_center;
	TextView tv_tab_right;
	TextView tx_tab_right1;
	TextView lastTab;
	int itemtype = 0;
	
	int mPage = 1;
	int mPage1 = 1;
	int mPage2= 1;
	int mPage3 = 1;
	
	AlertDialog messageDialog;
	AlertDialog myDialog;
	RelativeLayout rl_no_date;
	
	TextView tv_no_work;
	boolean isGetData = false;
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
			switch (msg.what) {
			case 0: {
				
				mListView.onRefreshComplete();
				mListView.onLoadMoreComplete();
				if(itemtype == 0)
				{
					if(mleftInterviewAdapter == null)
						mleftInterviewAdapter = new InterviewAdapter(mContext,mleftItmeArray,InterviewPageFragment.this);
					mListView.setAdapter(mleftInterviewAdapter);
					mleftInterviewAdapter.notifyDataSetChanged();
					if(mleftItmeArray.size()==0)
					{
						mListView.setVisibility(View.INVISIBLE);
						rl_no_date.setVisibility(View.VISIBLE);
					}
					else
					{
						rl_no_date.setVisibility(View.INVISIBLE);
						mListView.setVisibility(View.VISIBLE);
						mleftInterviewAdapter.notifyDataSetChanged();
					}
				}
				else if(itemtype == 1)
				{
					if(mmidInterviewAdapter == null)
						mmidInterviewAdapter = new InterviewAdapter(mContext,mmidItmeArray,InterviewPageFragment.this);
					mListView.setAdapter(mmidInterviewAdapter);
					mmidInterviewAdapter.notifyDataSetChanged();
					if(mmidItmeArray.size()==0)
					{
						mListView.setVisibility(View.INVISIBLE);
						rl_no_date.setVisibility(View.VISIBLE);
					}
					else
					{
						rl_no_date.setVisibility(View.INVISIBLE);
						mListView.setVisibility(View.VISIBLE);
						mmidInterviewAdapter.notifyDataSetChanged();
					}
					  
				}
				else if(itemtype == 2)
				{
					if(mrightInterviewAdapter == null)
						mrightInterviewAdapter = new InterviewAdapter(mContext,mrightItmeArray,InterviewPageFragment.this);
					mListView.setAdapter(mrightInterviewAdapter);
					
					if(mrightItmeArray.size()==0)
					{
						mListView.setVisibility(View.INVISIBLE);
						rl_no_date.setVisibility(View.VISIBLE);
					}
					else
					{
						rl_no_date.setVisibility(View.INVISIBLE);
						mListView.setVisibility(View.VISIBLE);
						mrightInterviewAdapter.notifyDataSetChanged();
					}
				}
				else if(itemtype == 3)
				{
					if(mright2InterviewAdapter == null)
						mright2InterviewAdapter = new InterviewAdapter(mContext,mright2ItmeArray,InterviewPageFragment.this);
					mListView.setAdapter(mright2InterviewAdapter);
					
					if(mright2ItmeArray.size()==0)
					{
						mListView.setVisibility(View.INVISIBLE);
						rl_no_date.setVisibility(View.VISIBLE);
					}
					else
					{
						rl_no_date.setVisibility(View.INVISIBLE);
						mListView.setVisibility(View.VISIBLE);
						mright2InterviewAdapter.notifyDataSetChanged();
					}
				}
				
				
			}
			break;
			}
		}
	};
	public InterviewPageFragment()
	{
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (null != mBaseView) {
            ViewGroup parent = (ViewGroup) mBaseView.getParent();
            if (null != parent) {
                parent.removeView(mBaseView);
                onRefreshItems();
            }
        } else {
        	mContext = getActivity();
        	mBaseView = inflater.inflate(R.layout.fragment_interview_page, null);
    		initView();
    	
        }
	  Log.e("fragmer", "secondFragment onCreateView");
	  return mBaseView;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
	}
	
	private void initHeaderView() {
		RelativeLayout btnleft = (RelativeLayout) mBaseView.findViewById(R.id.back_layout);
		btnleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) mBaseView.findViewById(R.id.tv_title_bar_text);
		titleText.setText("我的面试");
		RelativeLayout btnright = (RelativeLayout) mBaseView.findViewById(R.id.right_layout);
		btnright.setVisibility(View.VISIBLE);
		btnright.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,InterviewCalendarViewActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void initView()
	{
		initHeaderView();
		tv_tab_left = (TextView)mBaseView.findViewById(R.id.tx_tab_left);
		tv_tab_left.setOnClickListener(this);
		tv_tab_center = (TextView)mBaseView.findViewById(R.id.tx_tab_center);
		tv_tab_center.setOnClickListener(this);
		tv_tab_right = (TextView)mBaseView.findViewById(R.id.tx_tab_right);
		tv_tab_right.setOnClickListener(this);
		tx_tab_right1 = (TextView)mBaseView.findViewById(R.id.tx_tab_right1);
		tx_tab_right1.setOnClickListener(this);
		mListView = (CustomListView)mBaseView.findViewById(R.id.list_view);
		mListView.setCanRefresh(false);
		mListView.setCanLoadMore(true);
		mListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
			
				onRefreshItems();
			}
		});
		mListView.setOnLoadListener(new OnLoadMoreListener() {
			
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
			
				onLoadMoreItems();
			}
		});
		rl_no_date = (RelativeLayout)mBaseView.findViewById(R.id.rl_no_date);
		rl_no_date.setVisibility(View.INVISIBLE);
		tv_no_work = (TextView)mBaseView.findViewById(R.id.tv_no_work);
		tv_no_work.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onRefreshItems();
			}
		});
		setCurrentTabBg(tv_tab_left);
		onRefreshItems();
	}
	
	private void onRefreshItems()
	{
		if(!isGetData)
		{
			isGetData = true;
		if(itemtype==0)
		{
			  mPage = 1;
			  mleftItmeArray.clear();
			  getLeftData();
		}
		else if(itemtype == 1)
		{
			    mPage1 =1;
				 mmidItmeArray.clear();
				 getMidData();
		}
		else if(itemtype == 2)
		{
			 mPage2 =1;
				mrightItmeArray.clear();
				getRightData();
		}
		else if(itemtype == 3)
		{
			 mPage3 =1;
				mright2ItmeArray.clear();
				getRight2Data();
		}
		}
	}
	
	private void onLoadMoreItems()
	{
		if(!isGetData)
		{
		isGetData = true;
		if(itemtype==0)
		{
			 mPage++;
			  getLeftData();
		}
		else if(itemtype == 1)
		{
			 mPage1++;
		     getMidData();
		}
		else if(itemtype == 2)
		{
			 mPage2++;
			getRightData();
		}
		else if(itemtype == 3)
		{
			 mPage3++;
			getRight2Data();
		}
		}
	
	}
	
	boolean setCurrentTabBg(TextView mCurrentTab){
		boolean isChange = true;
		if(!isGetData)
		{
		    mCurrentTab.setSelected(true);
		    if(mCurrentTab!=lastTab)
			 {
				  if (lastTab == null) {
				        lastTab = mCurrentTab;
			      }else {
			            lastTab.setSelected(false);
			      }
				  lastTab = mCurrentTab;
				  
			  }
		    else
		    {
		    	isChange = false;
		    }
		}
		else
		{
			 isChange = false;
			 mCurrentTab.setSelected(false);
			 lastTab.setSelected(true);
		}
		return isChange;
	}
	
	void getItemData()
	{
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.tx_tab_left:
		{
			if(setCurrentTabBg((TextView)arg0))
			{
				itemtype = 0;
				onRefreshItems();
			}
			    
		}
		break;
		case R.id.tx_tab_center:
		{
			if(setCurrentTabBg((TextView)arg0))
			{
				itemtype = 1;
				onRefreshItems();
			}
		
		}
		break;
		case R.id.tx_tab_right:
		{
			if(setCurrentTabBg((TextView)arg0))
			{
				itemtype = 2;
				onRefreshItems();
			}
			
		}
		break;
		case R.id.tx_tab_right1:
		{
			if(setCurrentTabBg((TextView)arg0))
			{
				itemtype = 3;
				onRefreshItems();
			}
			
		}
		break;
		}
	}
	
	private void getLeftData()
	{
		itemtype = 0;
		sendGetInterView("invitation");
	}
	
	private void getMidData()
	{
		itemtype = 1;
		sendGetInterView("waiting");
	}
	
	private void getRightData()
	{
		itemtype = 2;
		sendGetInterView("completed");
	}
	
	private void getRight2Data()
	{
		itemtype = 3;
		sendGetInterView("sent");
	}
	
	private void sendGetInterView(String type)
	{
		String url = NetUrlManager.getInterview(type)+"&token="+AotugeApplication.getInstance().mAotuInfo.token;
		
		        if(itemtype == 0)
				 url +="&page="+String.valueOf(mPage);   
				else if(itemtype == 1)
					 url +="&page="+String.valueOf(mPage1);  
				else if(itemtype == 2)
					 url +="&page="+String.valueOf(mPage2);  
				else if(itemtype == 3)
					 url +="&page="+String.valueOf(mPage3); 
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.get(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				isGetData = false;
				super.onSuccess(content);
				NetReturnInfo info = null;
				if(itemtype == 0)
				      info = JosnParser.getInterviews(content,mleftItmeArray,0);
				else if(itemtype == 1)
					  info = JosnParser.getInterviews(content,mmidItmeArray,1);
				else if(itemtype == 2)
					  info = JosnParser.getInterviews(content,mrightItmeArray,2);
				else if(itemtype == 3)
					  info = JosnParser.getInterviews(content,mright2ItmeArray,3);
				if(info.success)
				{
					handler.sendEmptyMessage(0); 
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				isGetData = false;
			}
		});
	
	}

	@Override
	public void onBnClick(int pos) {
		// TODO Auto-generated method stub
		if(itemtype ==0)
		{
			InterviewTimeItem item = mleftItmeArray.get(pos);
			sendInterviewAction(String.valueOf(item.id),"accept");
		}
		else if(itemtype ==1)
		{
			InterviewTimeItem item = mmidItmeArray.get(pos);
			sendInterviewAction(String.valueOf(item.id),"cancel");
		}
		else if(itemtype ==2)
		{
			/*Intent intent = new Intent(mContext,InterviewScoreActivity.class);
			InterviewTimeItem item = mrightItmeArray.get(pos);
			intent.putExtra("company", item.company);
			intent.putExtra("title", item.title);
			intent.putExtra("interview_time", item.interview_time);
			intent.putExtra("id", item.id);
			startActivity(intent);*/
			InterviewTimeItem item = mrightItmeArray.get(pos);
			if(item.status == 20)
			{
				sendInterviewAction(String.valueOf(item.id),"accept");
			}
			else if(item.status == 30)
			{
				sendInterviewAction(String.valueOf(item.id),"cancel");
			}
			else if(item.status == 150)
			{
				sendInterviewAction(String.valueOf(item.id),"absent");
			}
			
		}
		else if(itemtype ==3)
		{
			InterviewTimeItem item = mright2ItmeArray.get(pos);
			if(item.status == 20)
			{
				sendInterviewAction(String.valueOf(item.id),"accept");
			}
			else if(item.status == 30)
			{
				sendInterviewAction(String.valueOf(item.id),"cancel");
			}
			else if(item.status == 150)
			{
				sendInterviewAction(String.valueOf(item.id),"absent");
			}
			/*Intent intent = new Intent(mContext,InterviewScoreActivity.class);
			InterviewTimeItem item = mrightItmeArray.get(pos);
			intent.putExtra("company", item.company);
			intent.putExtra("title", item.title);
			intent.putExtra("interview_time", item.interview_time);
			intent.putExtra("id", item.id);
			startActivity(intent);*/
			
		}
		
	}

	@Override
	public void onInfoClick(int pos) {
		// TODO Auto-generated method stub
		if(itemtype ==0)
		{
		/*	InterviewTimeItem item = mleftItmeArray.get(pos);
			Intent intent = new Intent(mContext,InterviewDetailsPreCompleteActivity.class);
			intent.putExtra("id", item.id);
			startActivity(intent);*/
			InterviewTimeItem item = mleftItmeArray.get(pos);
			Intent intent = new Intent(mContext,InterviewDetailsCompleteActivity.class);
			intent.putExtra("id", item.id);
			startActivity(intent);
			
		}
		else if(itemtype ==1)
		{
			InterviewTimeItem item = mmidItmeArray.get(pos);
			Intent intent = new Intent(mContext,InterviewDetailsCompleteActivity.class);
			intent.putExtra("id", item.id);
			startActivity(intent);
			/*InterviewTimeItem item = mmidItmeArray.get(pos);
			Intent intent = new Intent(mContext,InterviewDetailsCurCompleteActivity.class);
			intent.putExtra("id", item.id);
			startActivity(intent);*/
		}
		else if(itemtype ==2)
		{
			InterviewTimeItem item = mrightItmeArray.get(pos);
			Intent intent = new Intent(mContext,InterviewDetailsCompleteActivity.class);
			intent.putExtra("id", item.id);
			startActivity(intent);
		}
		else if(itemtype ==3)
		{
			InterviewTimeItem item = mright2ItmeArray.get(pos);
			Intent intent = new Intent(mContext,InterviewDetailsCompleteActivity.class);
			intent.putExtra("id", item.id);
			startActivity(intent);
		}
		}
	

	void sendInterviewAction(String id, String action) {
		String url = NetUrlManager.getInterViewAction(id, action);
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = null;
				info = JosnParser.getInterviewAction(content);
				 if(info.success)
					 onRefreshItems();
					showMessageDialog("提示", info.info, "确定", null,0);
				
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);

			}
		});
	}
	
	public void showMessageDialog(String title, String message,String rightButtonStr,
			final KTAlertDialogOnClickListener rightListener,int state) {
		if (messageDialog != null)
			messageDialog.dismiss();
		messageDialog = null;
		messageDialog = new AlertDialog.Builder(mContext).create();
		
		messageDialog.setCancelable(false);
		messageDialog.show();
		
		messageDialog.getWindow().setContentView(R.layout.message_dialog);
		TextView dialogtitle = (TextView) messageDialog.getWindow().findViewById(
				R.id.alertdialog_title);
		TextView dialogmessage = (TextView) messageDialog.getWindow().findViewById(
				R.id.alertdialog_message);
		Button rightButton = (Button) messageDialog.getWindow().findViewById(
				R.id.alertdialog_NegativeButton);
		dialogtitle.setText(title);
		dialogmessage.setText(message);
		rightButton.setText(rightButtonStr);
		rightButton.setTag(String.valueOf(state));
		rightButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rightListener != null)
					rightListener.onClick(messageDialog, v);
				messageDialog.dismiss();
			}
		});
		
		
	}
	
	public void showNotitleDialog(String message,
			String leftButtonStr, String rightButtonStr,
			final KTAlertDialogOnClickListener leftListener,
			final KTAlertDialogOnClickListener rightListener,String index) {
		if (myDialog != null)
			myDialog.dismiss();
		myDialog = null;
		myDialog = new AlertDialog.Builder(mContext).create();
		myDialog.setCancelable(false);
		myDialog.show();
		myDialog.getWindow().setContentView(R.layout.dialog_no_title);
		TextView dialogmessage = (TextView) myDialog.getWindow().findViewById(
				R.id.alertdialog_message);
		Button leftButton = (Button) myDialog.getWindow().findViewById(
				R.id.alertdialog_PositiveButton);
		Button rightButton = (Button) myDialog.getWindow().findViewById(
				R.id.alertdialog_NegativeButton);
		dialogmessage.setText(message);
		leftButton.setText(leftButtonStr);
		leftButton.setTag(index);
		rightButton.setText(rightButtonStr);
		rightButton.setTag(index);
		myDialog.getWindow().findViewById(R.id.alertdialog_PositiveButton)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (leftListener != null)
							leftListener.onClick(myDialog, v);
						myDialog.dismiss();
					}
				});

		rightButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rightListener != null)
					rightListener.onClick(myDialog, v);
				myDialog.dismiss();
			}
		});
	}


}
