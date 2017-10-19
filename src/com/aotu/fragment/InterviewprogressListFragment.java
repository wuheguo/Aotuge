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

import com.aotu.Adapter.InterviewProgressListAdapter;
import com.aotu.baseview.CustomListView;
import com.aotu.data.InterviewProgressItem;
import com.auto.aotuge.R;

public class InterviewprogressListFragment  extends Fragment{
	Context mContext;
	View mBaseView;
	
	List<InterviewProgressItem> mprogress;
	InterviewProgressListAdapter mInterviewProgressListAdapter;
	int mstatus;
	LayoutInflater inflater;
	
	CustomListView mlist_view;
	
	public InterviewprogressListFragment(List<InterviewProgressItem>progress,int status)
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
        	mBaseView = inflater.inflate(R.layout.fragment_interview_details_progress, null);
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
		mlist_view = (CustomListView)mBaseView.findViewById(R.id.list_view);
		mlist_view.setCanLoadMore(false);
		mlist_view.setCanRefresh(false);
		mInterviewProgressListAdapter = new InterviewProgressListAdapter(mContext,mprogress);
		mlist_view.setAdapter(mInterviewProgressListAdapter);
		mInterviewProgressListAdapter.notifyDataSetChanged();
	}
	
	void setViewData()
	{
		for(int i = 0 ; i <mprogress.size();i++)
		{
			
		}
	}
}
