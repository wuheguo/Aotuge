package com.aotu.fragment;



import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.aotu.Adapter.HomePageAdapter;
import com.aotu.activity.MainActivityFragment;

import com.aotu.activity.MainActivityFragment.MyOnTouchListener;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.AsyncTaskBase;
import com.aotu.baseview.BaseFragment;
import com.aotu.baseview.CustomListView;
import com.aotu.baseview.LoadingView;
import com.aotu.baseview.CustomListView.OnLoadMoreListener;
import com.aotu.baseview.CustomListView.OnRefreshListener;
import com.aotu.data.HomePageItemData;
import com.aotu.data.NetReturnInfo;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageFragment extends Fragment implements OnGestureListener  {
	Context mContext;
	View mBaseView;
	
	TimeCount time;
	boolean isChange = true;
	boolean isGetDate = false;
	MyOnTouchListener myOnTouchListener = new MyOnTouchListener()
	{

		@Override
		public boolean onTouch(MotionEvent ev) {
			// TODO Auto-generated method stub
		return gestureDetector.onTouchEvent(ev);
		}
		
	};
	private GestureDetector gestureDetector = null;
	int index = 0;
	List<HomePageItemData> mItemArray = new ArrayList<HomePageItemData>();
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			

				 if(mItemArray.size()>0)
				 {
				
				           index = 0;
				           FragmentManager fm = getFragmentManager();
				           FragmentTransaction fragmentTransaction = fm.beginTransaction();
				           HomeChildFragment se =  new HomeChildFragment(mItemArray.get(0));
				           se.mHomePageFragment = HomePageFragment.this;
				    	   fragmentTransaction.replace(R.id.fragment_place, se);
				    	   fragmentTransaction.commit(); 	
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
            isChange = true;
        } else {
        	mContext = getActivity();
        	mBaseView = inflater.inflate(R.layout.fragment_main_page, null);
    		initView();
    		gestureDetector = new GestureDetector(this);
    		isChange = true;
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
		((MainActivityFragment)getActivity()).registerMyOnTouchListener(myOnTouchListener);
		initHeaderView();
		initViewPager();
		getHomePageData();
	}
	
	private void initHeaderView() {
		RelativeLayout btnleft = (RelativeLayout) mBaseView.findViewById(R.id.back_layout);
		btnleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) mBaseView.findViewById(R.id.tv_title_bar_text);
		titleText.setText("首页");
		RelativeLayout btnright = (RelativeLayout) mBaseView.findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}
	
	
	
	private void initViewPager() {
		
		
	}
	
	void getHomePageData()
	{
		if(!isGetDate)
		{
			isGetDate = true;
		String url = NetUrlManager.getHomePage();
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url,new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				isGetDate = false;
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getHomePageItemData(content,mItemArray);
				if(info.success)
					handler.sendEmptyMessage(1);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				isGetDate = false;
			}
		});
		}
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	static final int INTERVAL_SEND_CODE = 1000 * 1;// 发送验证码时间间隔,1秒
	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
	
		if(isChange&&mItemArray.size()>0)
		{
			if(time == null)
				time = new TimeCount(INTERVAL_SEND_CODE, 500);
			time.start();
			isChange = false;
			if(arg1.getX()>arg0.getX()+30){
				if(mItemArray.size() == 3)
				{
					getHomePageData();
				}
				
				FragmentManager fm = getFragmentManager();
				
			    FragmentTransaction fragmentTransaction = fm.beginTransaction();
			   
			  
			    HomeChildFragment se =  new HomeChildFragment(mItemArray.get(0));
		        se.mHomePageFragment = HomePageFragment.this;
			   
			    fragmentTransaction.setCustomAnimations(
			    	     R.animator.fragment_enter,
			    	     R.animator.fragment_right_exit,
			    	     R.animator.fragment_pop_left_enter,
			    	     R.animator.fragment_pop_left_exit);
			    	 
			    	    fragmentTransaction.replace(R.id.fragment_place, se);
			    	   
			    	    fragmentTransaction.commit();
			    	  
			            index++;
						
			            mItemArray.remove(0);   
			}
			else if(arg0.getX()>arg1.getX()+30)
			{
				if(mItemArray.size() == 3)
				{
					getHomePageData();
				}
				FragmentManager fm = getFragmentManager();
				
			    FragmentTransaction fragmentTransaction = fm.beginTransaction();
			    HomeChildFragment se =  new HomeChildFragment(mItemArray.get(0));
		        se.mHomePageFragment = HomePageFragment.this;
			    
			    fragmentTransaction.setCustomAnimations(
			    	     R.animator.fragment_enter,
			    	     R.animator.fragment_left_exit,
			    	     R.animator.fragment_pop_left_enter,
			    	     R.animator.fragment_pop_left_exit);
			    	 
			    	    fragmentTransaction.replace(R.id.fragment_place, se);
			    	   
			    	    fragmentTransaction.commit();
			    	   
			            mItemArray.remove(0);   
			}
		}
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
	
	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			
		}

		@Override
		public void onFinish() {
			
			isChange = true;
		}
	}

	public void onSendCv()
	{
		
	}
	
	
}
