package com.aotu.fragment;

import java.lang.reflect.Field;

import com.aotu.Adapter.IntentionListAdapter;
import com.aotu.Adapter.WorkPageAdapter;
import com.aotu.Adapter.WorkWelfareGridAdapter;
import com.aotu.activity.CVListActivity;
import com.aotu.activity.CompanyInfoActivity;
import com.aotu.activity.LogonActivity;
import com.aotu.activity.MainActivityFragment;
import com.aotu.activity.PersonCurriculumVitaeActivity;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.CustomGridView;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.data.HomePageItemData;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.WorkItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeChildFragment  extends Fragment implements OnGestureListener{

	View mBaseView;
	Context mContext;
	TextView tx_graduate_text;
	TextView tx_title,tx_company,tx_salary_text,tx_city,tx_type_text,tx_update_text;
	LinearLayout l_tab_mid;
	Button bn_ok;
	CustomGridView mGridView;
	WorkWelfareGridAdapter mAdapter;
	
	HomePageItemData mHomePageItemData;
	String mCV_id ="";
	AlertDialog messageDialog;
	AlertDialog myDialog;
	
	 /**定义手势检测实例*/
    public static GestureDetector detector;
    /**做标签，记录当前是哪个fragment*/
    public int MARK=0;
    /**定义手势两点之间的最小距离*/
    final int DISTANT=50; 
    public HomePageFragment mHomePageFragment;
	KTAlertDialogOnClickListener mKTADialongleft = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			sendCV(true);
		}
		
	};
	
	KTAlertDialogOnClickListener mKTADialongright = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(mContext,PersonCurriculumVitaeActivity.class);
			intent.putExtra("type", 0);
			intent.putExtra("cv_id", "0");
			startActivity(intent);
		}
		
	};
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {	
		
			switch(msg.what)
			{
			case 0:{
				sendCV(false);
			}
			break;
			case 1:{
				showMessageDialog("提示","简历投递成功","确定",null,1);
			}
			break;
			
			}
			
		}
	};
	
	HomeChildFragment(HomePageItemData aHomePageItemData)
	{
		mHomePageItemData = aHomePageItemData;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (null != mBaseView) {
            ViewGroup parent = (ViewGroup) mBaseView.getParent();
            if (null != parent) {
                parent.removeView(mBaseView);
            }
        } else {
        	mContext = getActivity();
        	mBaseView = inflater.inflate(R.layout.fragment_home_page_child, null);
        	detector=new GestureDetector(mContext,this);
    		initView();
        }
	  return mBaseView;
	}
	
	public void ongoInfo(View v)
	{
		Intent intent = new Intent(mContext,CompanyInfoActivity.class);
		intent.putExtra("id", mHomePageItemData.id);
		intent.putExtra("cid", mHomePageItemData.cid);
		startActivity(intent);
	}
	
	private void initView()
	{
		l_tab_mid = (LinearLayout)mBaseView.findViewById(R.id.l_tab_mid);
		l_tab_mid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,CompanyInfoActivity.class);
				intent.putExtra("id", mHomePageItemData.id);
				intent.putExtra("cid", mHomePageItemData.cid);
				startActivity(intent);
			}
		});
		tx_graduate_text = (TextView)mBaseView.findViewById(R.id.tx_graduate_text);
		tx_title = (TextView)mBaseView.findViewById(R.id.title);
		tx_company = (TextView)mBaseView.findViewById(R.id.tx_company);
		tx_salary_text= (TextView)mBaseView.findViewById(R.id.tx_salary_text);
		tx_city= (TextView)mBaseView.findViewById(R.id.tx_city);
		tx_type_text= (TextView)mBaseView.findViewById(R.id.tx_type_text);
		tx_update_text= (TextView)mBaseView.findViewById(R.id.tx_update_text);
		bn_ok = (Button)mBaseView.findViewById(R.id.bn_ok);
		bn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//sendCV(false);
				if(AotugeApplication.getInstance().mIsNullAotuInfo)
				{
					Intent intent = new Intent(mContext,LogonActivity.class);
					startActivity(intent);
			    }
				else
				{
				    Intent intent = new Intent(mContext,CVListActivity.class);
				    startActivityForResult(intent, 5);
				}			
			}
		});
		mGridView = (CustomGridView)mBaseView.findViewById(R.id.grid_view); 
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		updateData();
	}
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
//		try {
//    	    Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
//    	    childFragmentManager.setAccessible(true);
//    	    childFragmentManager.set(this, null);
//
//    	} catch (NoSuchFieldException e) {
//    	    throw new RuntimeException(e);
//    	} catch (IllegalAccessException e) {
//    	    throw new RuntimeException(e);
//    	}
	}
	
	void updateData()
	{
		mAdapter = null;
		if(mAdapter!=null)
		{
		  mGridView.setAdapter(mAdapter);
		 // mAdapter.notifyDataSetChanged();
		}
		else
		{
			if(mHomePageItemData.welfare.size()>1)
			{
			 mAdapter = new WorkWelfareGridAdapter(mBaseView.getContext(),mHomePageItemData.welfare);
			}
			  mGridView.setAdapter(mAdapter);
			 // mAdapter.notifyDataSetChanged();
		}
		tx_title.setText(mHomePageItemData.title);
		tx_company.setText(mHomePageItemData.company);
		tx_graduate_text.setText(mHomePageItemData.graduate_text);
		tx_salary_text.setText(mHomePageItemData.salary_text);
		tx_city.setText(mHomePageItemData.location.get(0).name);
		tx_type_text.setText(mHomePageItemData.work_type_text);
		tx_update_text.setText(mHomePageItemData.update);
	}
	
	private void sendCV(boolean forbidden)
	{
		String url = NetUrlManager.getSendCvURL()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("cv_id", mCV_id);
		params.put("jd_id", String.valueOf(mHomePageItemData.id));
		if(forbidden)
		  params.put("forbidden", "1");
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.post(url,params,new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
			    NetReturnInfo info = JosnParser.getCVSend(content);
			    if(info.success)
			    {
			    	handler.sendEmptyMessage(1);
			    }
			    else
			    {
			    	switch(info.status)
			    	{
			    	case -1:
			    		showMessageDialog("提示",info.info,"确定",null,0);
			    		break;
			    	case -2:
			    		  showNotitleDialog(info.info,"强行投递","去完善",mKTADialongleft,mKTADialongright,"-2");
			    		break;
			    	case -3:
			    		  showNotitleDialog(info.info,"强行投递","去完善",mKTADialongleft,mKTADialongright,"-3");
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(requestCode)
		{
			case 5:
			{
				if(resultCode == -1)
				{
					mCV_id = data.getStringExtra("cv_id");
					handler.sendEmptyMessage(0);		
				}	
			}
			break;
			case 2:
			{
				   Intent intent = new Intent(mContext,CVListActivity.class);
				    startActivityForResult(intent, 1);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
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

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
