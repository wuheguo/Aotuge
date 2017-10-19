package com.aotu.fragment;


import com.aotu.Adapter.CompanyStoreGridAdapter;
import com.aotu.Adapter.WorkWelfareGridAdapter;
import com.aotu.activity.CompanyInfoActivity;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.CustomGridView;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.PositionInfoItem;
import com.aotu.data.Positioninfo;
import com.aotu.data.WorkItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PositioninfoFragment extends Fragment  {

	CompanyInfoActivity mContext;
	View mBaseView;
	
	
	TextView company_info;
	
	TextView tx_company,tx_salary,tx_company_property,tx_graduate,tx_location,tx_work_type,tx_update;
	
	CustomGridView mGridView;
	WorkWelfareGridAdapter mAdapter;
	Positioninfo mPositionInfoItem = new Positioninfo();
	Button bn_join;
	String  mCid;
	public PositioninfoFragment(String cid)
	{
		mCid = cid;
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 0)
			{
				mGridView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
			}
			else if(msg.what == 1)
			{
				if(mPositionInfoItem.welfare.size()>1)
				{
				if(mAdapter==null)
				   mAdapter = new WorkWelfareGridAdapter(mBaseView.getContext(),mPositionInfoItem.welfare);
				mGridView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
				}
				initViewData();
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
                getPositionInfo(mCid);
            }
        } else {
        	mContext = (CompanyInfoActivity)getActivity();
        	mBaseView = inflater.inflate(R.layout.fragment_interview_company_position_details, null);
    		initView();
    		getPositionInfo(mCid);
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
	
	private void initView()
	{
		
		tx_company = (TextView)mBaseView.findViewById(R.id.tx_company);
		tx_salary = (TextView)mBaseView.findViewById(R.id.tx_salary);
		tx_graduate = (TextView)mBaseView.findViewById(R.id.tx_graduate);
		tx_company_property = (TextView)mBaseView.findViewById(R.id.tx_company_property);
		tx_location = (TextView)mBaseView.findViewById(R.id.tx_location);
		tx_work_type = (TextView)mBaseView.findViewById(R.id.tx_work_type);
		tx_update = (TextView)mBaseView.findViewById(R.id.tx_update);
		
		mGridView = (CustomGridView)mBaseView.findViewById(R.id.grid_view); 
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
  
		company_info = (TextView)mBaseView.findViewById(R.id.company_info);
		
		bn_join = (Button)mBaseView.findViewById(R.id.bn_join);
		bn_join.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mContext.onJoin();
			}
		});
		
	}
	
	void initViewData()
	{
		tx_company.setText(mPositionInfoItem.company);
		tx_salary.setText(mPositionInfoItem.salary_text);
		tx_graduate.setText(mPositionInfoItem.graduate_text);
		tx_company_property.setText(mPositionInfoItem.title);
		tx_location.setText(mPositionInfoItem.location.get(0).name);
		tx_work_type.setText(mPositionInfoItem.work_type_text);
		tx_update.setText(mPositionInfoItem.update_text);
		
		company_info.setText(mPositionInfoItem.requirements);
	}
	
	private void getPositionInfo(String id)
	{
		String url = NetUrlManager.getPositionInfo(mCid) +"&token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.get(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = null;
			    info = JosnParser.getPositioninfo(content, mPositionInfoItem);
			   
				if(info.success)
				{
					handler.sendEmptyMessage(1);
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				
			}
		});
	
	}
	
	
	
}
