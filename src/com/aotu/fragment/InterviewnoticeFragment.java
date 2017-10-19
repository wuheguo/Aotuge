package com.aotu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.auto.aotuge.R;

public class InterviewnoticeFragment  extends Fragment{
	Context mContext;
	View mBaseView;
	
	LayoutInflater inflater;
	String tip;
    TextView tx_interview_tab1,tx_interview_tab2,tx_interview_tab3,tx_interview_tab4,tx_interview_tab5;
	public InterviewnoticeFragment(String info)
	{
		tip = info;
		
	}

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
        	mBaseView = inflater.inflate(R.layout.fragment_interview_details_notice, null);
        	inflater = LayoutInflater.from(mContext);
    		initView();
        }
	  Log.e("fragmer", "secondFragment onCreateView");
	  setViewData();
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
	
	void initView()
	{
		tx_interview_tab1= (TextView)mBaseView.findViewById(R.id.tx_interview_tab1);
		tx_interview_tab2= (TextView)mBaseView.findViewById(R.id.tx_interview_tab2);
		tx_interview_tab3= (TextView)mBaseView.findViewById(R.id.tx_interview_tab3);
		tx_interview_tab4= (TextView)mBaseView.findViewById(R.id.tx_interview_tab4);
		tx_interview_tab5= (TextView)mBaseView.findViewById(R.id.tx_interview_tab5);
		
	}
	
	void setViewData()
	{
		tx_interview_tab1.setText(tip);
		
	}
	
	
	
	
	
	
}
