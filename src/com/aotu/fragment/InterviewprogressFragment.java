package com.aotu.fragment;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aotu.data.InterviewProgressItem;
import com.auto.aotuge.R;

public class InterviewprogressFragment  extends Fragment{
	Context mContext;
	View mBaseView;
	
	List<InterviewProgressItem> mprogress;
	
	int mstatus;
	LayoutInflater inflater;
	ImageView im_circle,im_circle1,im_circle2,im_circle3,im_circle4;
	TextView tx_line,tx_line1,tx_line2,tx_line3,tx_line4;
    TextView tx_interview_tab1,tx_interview_tab2,tx_interview_tab3,tx_interview_tab4,tx_interview_tab5;
    TextView tx_interview_tab6,tx_interview_tab7,tx_interview_tab8,tx_interview_tab9,tx_interview_tab10;
	public InterviewprogressFragment(List<InterviewProgressItem>progress,int status)
	{
		mprogress = progress;
		mstatus = status;
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
        	mBaseView = inflater.inflate(R.layout.fragment_interview_details_progress1, null);
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
		im_circle = (ImageView)mBaseView.findViewById(R.id.im_circle);
		tx_line = (TextView)mBaseView.findViewById(R.id.tx_line);
		tx_interview_tab1= (TextView)mBaseView.findViewById(R.id.tx_interview_tab1);
		tx_interview_tab2= (TextView)mBaseView.findViewById(R.id.tx_interview_tab2);
		
		im_circle1 = (ImageView)mBaseView.findViewById(R.id.im_circle1);
		tx_line1 = (TextView)mBaseView.findViewById(R.id.tx_line1);
		tx_interview_tab3= (TextView)mBaseView.findViewById(R.id.tx_interview_tab3);
		tx_interview_tab4= (TextView)mBaseView.findViewById(R.id.tx_interview_tab4);
		
		im_circle2 = (ImageView)mBaseView.findViewById(R.id.im_circle2);
		tx_line2 = (TextView)mBaseView.findViewById(R.id.tx_line2);
		tx_interview_tab5= (TextView)mBaseView.findViewById(R.id.tx_interview_tab5);
		tx_interview_tab6= (TextView)mBaseView.findViewById(R.id.tx_interview_tab6);
		
		im_circle3 = (ImageView)mBaseView.findViewById(R.id.im_circle3);
		tx_line3 = (TextView)mBaseView.findViewById(R.id.tx_line3);
		tx_interview_tab7= (TextView)mBaseView.findViewById(R.id.tx_interview_tab7);
		tx_interview_tab8= (TextView)mBaseView.findViewById(R.id.tx_interview_tab8);
		
		im_circle4 = (ImageView)mBaseView.findViewById(R.id.im_circle4);
		tx_line4 = (TextView)mBaseView.findViewById(R.id.tx_line4);
		tx_interview_tab9= (TextView)mBaseView.findViewById(R.id.tx_interview_tab9);
		tx_interview_tab10= (TextView)mBaseView.findViewById(R.id.tx_interview_tab10);
	}
	
	void setViewData()
	{
		for(int i = 0 ; i <mprogress.size();i++)
		{
			InterviewProgressItem item = mprogress.get(i);
			if(i==0)
			{
			tx_interview_tab1.setText(item.name);
			tx_interview_tab2.setText(item.time);
			
			}
			else if(i==1)
			{
			tx_interview_tab3.setText(item.name);
			tx_interview_tab4.setText(item.time);
			setLineAndImage(tx_line1,im_circle1,item.code);
			
			}
			else if(i==2)
			{
			tx_interview_tab5.setText(item.name);
			tx_interview_tab6.setText(item.time);
			setLineAndImage(tx_line2,im_circle2,item.code);
			}
			else if(i==3)
			{
			tx_interview_tab7.setText(item.name);
			tx_interview_tab8.setText(item.time);
			setLineAndImage(tx_line3,im_circle3,item.code);
			}
			else if(i==4)
			{
			tx_interview_tab9.setText(item.name);
			tx_interview_tab10.setText(item.time);
			setLineAndImage(tx_line4,im_circle4,item.code);
			}
			
		}
	}
	
	void setLineAndImage(TextView line, ImageView circle,int code)
	{
		if(code < mstatus)
		{
			line.setTextColor(mContext.getResources().getColor(R.color.font_right_color));
			circle.setBackground(mContext.getResources().getDrawable(R.drawable.interview_circle_1));
		}
		else if(code == mstatus)
		{
			line.setTextColor(mContext.getResources().getColor(R.color.devide_line));
			circle.setBackground(mContext.getResources().getDrawable(R.drawable.interview_circle_1));
		}
		else
		{
			line.setTextColor(mContext.getResources().getColor(R.color.devide_line));
			circle.setBackground(mContext.getResources().getDrawable(R.drawable.interview_circle_2));
		}
		
	}
	
	
	
	
	
	
}
