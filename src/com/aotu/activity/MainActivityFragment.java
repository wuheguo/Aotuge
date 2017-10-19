package com.aotu.activity;
/*
 * 主界面
 */
import java.util.ArrayList;
import java.util.List;

import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseFragment;
import com.aotu.baseview.BaseFragmentActivity;
import com.aotu.data.InterviewProgressItem;
import com.aotu.fragment.HomePageFragment;
import com.aotu.fragment.InterviewPageFragment;
import com.aotu.fragment.InterviewprogressFragment;
import com.aotu.fragment.MyPageFragment;
import com.aotu.fragment.WorkPageFragment;

import com.aotu.uploadphoto.PictureConstants;
import com.aotu.utils.DisplayUtil;
import com.auto.aotuge.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;


import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;



public class MainActivityFragment extends BaseFragmentActivity implements OnGestureListener {
	
	ArrayList<Fragment> fragmentList;
	RadioButton mMainBn,mWorkBn,mInterviewBn,mMyBn,mShuangxuan;
	RadioButton currentButton;
	
	FragmentManager fragmentManager = getFragmentManager();
	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	int index = 0;
	 /**定义手势检测实例*/
    public static GestureDetector detector;
    /**做标签，记录当前是哪个fragment*/
    public int MARK=0;
    /**定义手势两点之间的最小距离*/
    final int DISTANT=50; 
    
    @Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
    	if(index==0)
    	{
    	for (MyOnTouchListener listener : onTouchListeners) {
            listener.onTouch(ev);
        }
    	}
		return super.dispatchTouchEvent(ev);
	}

	private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(
            20);
   
    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }

    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
	 case PictureConstants.MODIFY_HEAD_IMAGE: // 调用媒体库
		 if(resultCode==RESULT_OK)
		 {
		 MyPageFragment myfragment =(MyPageFragment) fragmentList.get(3);
			
       
         String path = data.getStringExtra("dataPath");
         myfragment.setHeadData(path);
		 }
         break;
		}
	
		super.onActivityResult(requestCode, resultCode, data);
	}

	List<InterviewProgressItem> mprogress = new ArrayList<InterviewProgressItem>();
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
				
				index = 0;
				switchFragment(fragmentList.get(0));
			}

				break;
			case 1: {
				index = 1;
				switchFragment(fragmentList.get(1));
			}
				break;
			case 2: {
				index = 2;
				switchFragment(fragmentList.get(2));
			}
				break;
			case 3: {
				index = 4;
				switchFragment(fragmentList.get(3));
			}
				break;
			case 4: {
				index = 5;
				switchFragment(fragmentList.get(4));
			}
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.activity_main_fragment);
		initView();
		detector=new GestureDetector(this,this);
		setButton(mMainBn);
		fragmentTransaction.add(R.id.fragment, fragmentList.get(0));
        fragmentTransaction.commit();
		
	}
	
	void switchFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(fragment.isVisible())
        {
        	
        }
        else
        {
        fragmentTransaction.replace(R.id.fragment,fragment);
        fragmentTransaction.commit();
        }
    }


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	private void initView() {
		initViewPager();
		mMainBn=(RadioButton) findViewById(R.id.buttom_main);
		mMainBn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			     setButton(mMainBn);
			    
			     handler.sendEmptyMessage(0);
			}
		});
		mWorkBn=(RadioButton) findViewById(R.id.buttom_work);
		mWorkBn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if(AotugeApplication.getInstance().mIsNullAotuInfo)
				{
					setButton(currentButton);
					Intent intent = new Intent(MainActivityFragment.this,SearchWorkActivity.class);		
					startActivity(intent);
			    }
				else
				{
			    setButton(mWorkBn);
			  
			    handler.sendEmptyMessage(1);
				}
				
				
			}
		});
		mInterviewBn=(RadioButton) findViewById(R.id.buttom_interview);
		mInterviewBn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(AotugeApplication.getInstance().mIsNullAotuInfo)
				{
					setButton(currentButton);
					Intent intent = new Intent(MainActivityFragment.this,LogonActivity.class);
					startActivity(intent);
			    }
				else
				{
			     setButton(mInterviewBn);
			     handler.sendEmptyMessage(2);
				}
			}
		});
		
		mMyBn=(RadioButton) findViewById(R.id.buttom_my);
		mMyBn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(AotugeApplication.getInstance().mIsNullAotuInfo)
				{
					setButton(currentButton);
					Intent intent = new Intent(MainActivityFragment.this,LogonActivity.class);
					startActivity(intent);
			    }
				else
				{
			     setButton(mMyBn);
			     handler.sendEmptyMessage(3);
				}
			}
		});
		
		mShuangxuan=(RadioButton) findViewById(R.id.buttom_suangxuanview);
		mShuangxuan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(AotugeApplication.getInstance().mIsNullAotuInfo)
				{
					setButton(currentButton);
					Intent intent = new Intent(MainActivityFragment.this,LogonActivity.class);
					startActivity(intent);
			    }
				else
				{
			     setButton(mShuangxuan);
			     handler.sendEmptyMessage(4);
				}
			}
		});
		
		
	}

	private void initViewPager() {
		
		
		
		
		
		
		fragmentList = new ArrayList<Fragment>();
	
		Fragment HomeFragment = new HomePageFragment();
		
		fragmentList.add(HomeFragment);
		
		
		Fragment workfragment = new WorkPageFragment();
		fragmentList.add(workfragment);
	
		
		Fragment interviewFragment = new InterviewPageFragment();
		fragmentList.add(interviewFragment);
		
		Fragment myfragment = new MyPageFragment();
		fragmentList.add(myfragment);
		
		ShuangXuanListFragment fragment1 = new ShuangXuanListFragment();
		fragmentList.add(fragment1);
	}
	
	private void setButton(RadioButton v){
		if(currentButton!=null&&currentButton.getId()!=v.getId()){
			currentButton.setChecked(false);
		}
		v.setChecked(true);
		currentButton=v;
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		 if(currentButton == mMainBn)
		 {
			
		 }
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		FragmentTransaction ft=getFragmentManager().beginTransaction();
        FragmentTransaction ft1=getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();  
        int tmpMark=1;
		// TODO Auto-generated method stub
		 if(MARK==0)
	        {
	            if(arg0.getX()>arg1.getX()+DISTANT)
	            {
	            	tmpMark=1;
	      	        bundle.putString("pageNo", "1");  
	               
	            	
	             
	                MARK=tmpMark;
	            }
	            else
	            {

	            }
	            
	        }
	        //当是Fragment1的时候
	        else if (MARK>=1 && MARK<fragmentList.size()-1)
	        {
	            if(arg0.getX()>arg1.getX()+DISTANT)
	            {
	            	tmpMark=MARK+1;
	      	        bundle.putString("pageNo", ""+(tmpMark+1));  
	              
	                MARK=tmpMark;
	            }
	            else if(arg1.getX()>arg0.getX()+DISTANT)
	            {
	            	tmpMark=MARK-1;
	      	       
	                MARK=tmpMark;
	            }
	            else
	            {

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
	
	public void onGotoHomeFragment()
	{
		mMainBn.performClick();
	}
	
	public void onLogonView()
	{
		AotugeApplication.getInstance().mIsNullAotuInfo = true;
		AotugeApplication.getInstance().deleteUserInfo();
		mMainBn.performClick();
		Intent intent = new Intent(MainActivityFragment.this, LogonActivity.class);
		startActivity(intent);
		
	}
	

}
