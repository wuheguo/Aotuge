package com.aotu.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aotu.Adapter.CVItemAdapter;
import com.aotu.Adapter.SuangXuanItemAdapter;
import com.aotu.Adapter.CVItemAdapter.CVInterface;
import com.aotu.Adapter.PositionAdapter;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.BaseFragment;
import com.aotu.baseview.CustomListView;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.data.CVItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.ShuangXuanItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;

public class ShuangXuanListFragment extends Fragment {

	List<ShuangXuanItem> mItemList = new ArrayList<ShuangXuanItem>();
	SuangXuanItemAdapter mAdapter;
	CustomListView mListView;
	Context mContext;
	View mBaseView;
	
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
			switch(msg.what)
			{
			case 0:
			{
				if(mAdapter == null)
				{
				  mAdapter = new SuangXuanItemAdapter(mContext,mItemList);
				  mListView.setAdapter(mAdapter);
				}
				mAdapter.notifyDataSetChanged();
					
			}
			break;
		
			}
			
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (null != mBaseView) {
            ViewGroup parent = (ViewGroup) mBaseView.getParent();
            if (null != parent) {
                parent.removeView(mBaseView);
               
            }
        } else {
        	mContext = getActivity();
        	mBaseView = inflater.inflate(R.layout.fragment_suang_xuan, null);
    		initView();
    		getItem();
        }
	  Log.e("fragmer", "secondFragment onCreateView");
	  return mBaseView;
	}



	
	private void initHeadView() {
		RelativeLayout btnleft = (RelativeLayout) mBaseView.findViewById(R.id.back_layout);
		btnleft.setVisibility(View.INVISIBLE);
		RelativeLayout menuleft = (RelativeLayout) mBaseView.findViewById(R.id.left_menu_layout);
		menuleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) mBaseView.findViewById(R.id.tv_title_bar_text);
		titleText.setText("双选会");
		RelativeLayout btnright = (RelativeLayout) mBaseView.findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}
	
	private void initView()
	{
		initHeadView();
		mListView = (CustomListView)mBaseView.findViewById(R.id.list_view);
		mListView.setCanLoadMore(false);
		mListView.setCanRefresh(false);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {}
		});
		
		
		
	}
	
	void getItem()
	{
		String url = NetUrlManager.getSuanxuanURL()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.get(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				
				NetReturnInfo info = JosnParser.getShuangXuan(content,mItemList);
				if(info.success)
					handler.sendEmptyMessage(0);
				else
				{
					
				}
				
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				
			}
		});
	
	}


}
