package com.aotu.fragment;


import com.aotu.Adapter.CompanyStoreGridAdapter;
import com.aotu.Adapter.WorkWelfareGridAdapter;
import com.aotu.activity.CompanyInfoActivity;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.CustomGridView;
import com.aotu.data.CompanyInfo;
import com.aotu.data.NetReturnInfo;
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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CompanyinfoFragment extends Fragment  {

	CompanyInfoActivity mContext;
	View mBaseView;
	ImageView im_company,im_total;
	TextView tx_total_score;
	
	TextView company_info;
	
	CustomGridView mGridView;
	CompanyStoreGridAdapter mAdapter;
	
	CompanyInfo mCompanyInfo = new CompanyInfo();
	
	TextView tx_company,tx_company_property,company_property_text,company_size_text;
	String mCid;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch(msg.what)
			{
			case 0:
			{
				if(mCompanyInfo.scorearray.size()>1)
				{
					if(mAdapter == null)
					mAdapter = new CompanyStoreGridAdapter(mBaseView.getContext(),mCompanyInfo.scorearray);
					mGridView.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
				}
				initData();
			}
			break;
			case 1:
			{
				
				
			}
			break;
			case 2:
			{
				
			}
			break;
			}
		
		}
	};
	
	
	public CompanyinfoFragment(String cid)
	{
		mCid = cid;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (null != mBaseView) {
            ViewGroup parent = (ViewGroup) mBaseView.getParent();
            if (null != parent) {
                parent.removeView(mBaseView);
                getCompanyInfo(mCid);
            }
        } else {
        	mContext = (CompanyInfoActivity)getActivity();
        	mBaseView = inflater.inflate(R.layout.fragment_interview_company_details, null);
    		initView();
    		getCompanyInfo(mCid);
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
		tx_company_property = (TextView)mBaseView.findViewById(R.id.tx_company_property);
		company_property_text = (TextView)mBaseView.findViewById(R.id.tx_company_property_text);
		company_size_text = (TextView)mBaseView.findViewById(R.id.tx_company_size_text);
        im_total= (ImageView)mBaseView.findViewById(R.id.im_total);
		tx_total_score = (TextView)mBaseView.findViewById(R.id.tx_total_score);
		mGridView = (CustomGridView)mBaseView.findViewById(R.id.grid_view); 
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		company_info = (TextView)mBaseView.findViewById(R.id.company_info);
		
	
		
	}
	
	private void initData()
	{
		tx_company.setText(mCompanyInfo.company);
		company_property_text.setText(mCompanyInfo.company_property_text);
		company_size_text.setText(mCompanyInfo.company_size_text);
		company_info.setText(mCompanyInfo.introduce);
		tx_total_score.setText(String.valueOf(mCompanyInfo.score));
	}
	private void getCompanyInfo(String id)
	{
		String url = NetUrlManager.getCompanyInfo(id)+"&token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.get(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = null;
			    info = JosnParser.getCompanyinfo(content, mCompanyInfo);
			    
			    if(info.success)
			    	handler.sendEmptyMessage(0);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				
			}
		});
	
	}
}
