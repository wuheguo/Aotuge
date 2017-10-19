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




import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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

public class HomePageFragmentnew extends Fragment implements OnGestureListener  {
	Context mContext;
	View mBaseView;
	ViewPager viewPager;
	
	ViewPager mPager;
	ArrayList<Fragment> fragmentList;
	ArrayList<String> mTableStringList = new ArrayList<String>();
	MyFragmentPagerAdapter mPagerAdapter;
	MyOnTouchListener myOnTouchListener = new MyOnTouchListener()
	{

		@Override
		public boolean onTouch(MotionEvent ev) {
			// TODO Auto-generated method stub
		return gestureDetector.onTouchEvent(ev);
		}
		
	};
	private GestureDetector gestureDetector = null;
	
	List<HomePageItemData> mItemArray = new ArrayList<HomePageItemData>();
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if(mPagerAdapter == null)
			{
				 for (int i = 0; i < mItemArray.size(); i++) {
//						 Fragment fragment = new HomeChildFragment(mItemArray.get(i));
//						 fragmentList.add(fragment);
//						 mTableStringList.add("抢票区");
				}
			   mPagerAdapter = new MyFragmentPagerAdapter(getFragmentManager(), fragmentList);
			   mPager.setAdapter(mPagerAdapter);
			}
			mPager.setCurrentItem(0);
			
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
        	mBaseView = inflater.inflate(R.layout.fragment_main_page_new, null);
    		initView();
    		gestureDetector = new GestureDetector(this);
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
	
	public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		ArrayList<Fragment> list;

		public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
			super(fm);
			this.list = list;

		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mTableStringList.get(position % list.size());
		}

		@Override
		public Fragment getItem(int arg0) {
			Bundle args = new Bundle();
			args.putString("arg", mTableStringList.get(arg0));
			list.get(arg0).setArguments(args);
			return list.get(arg0);
		}
		
	}
	
	private void initViewPager() {
		fragmentList = new ArrayList<Fragment>();
		mPager = (ViewPager) mBaseView.findViewById(R.id.viewpager);
		
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				switch(arg0)
				{
				case 0 :
				{
					
				}
					break;
				case 1:
				{
					
				}
					break;
				case 2:
					break;
				case 3:
					break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	void getHomePageData()
	{
		String url = NetUrlManager.getHomePage();
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url,new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getHomePageItemData(content,mItemArray);
				if(info.success)
					handler.sendEmptyMessage(1);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				
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
		if(arg1.getX()>arg0.getX()+30){
			Toast.makeText(mContext,"向左滑",Toast.LENGTH_SHORT).show();
		}else if(arg0.getX()>arg1.getX()+30) {
			Toast.makeText(mContext,"向右滑",Toast.LENGTH_SHORT).show();
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

	
	
}
