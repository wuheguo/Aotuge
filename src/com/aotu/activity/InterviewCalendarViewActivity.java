package com.aotu.activity;

import java.util.ArrayList;
import java.util.List;

import com.aotu.Adapter.InterviewAdapter;
import com.aotu.Adapter.InterviewAdapter.InterviewInterface;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomListView;
import com.aotu.data.InterviewCalendarItem;
import com.aotu.data.InterviewTimeItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.fragment.InterviewPageFragment;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;
import com.custom.calendar.CalendarCard;
import com.custom.calendar.CalendarCard.OnCellClickListener;
import com.custom.calendar.CalendarViewAdapter;
import com.custom.calendar.CustomDate;
import com.custom.calendar.DateUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InterviewCalendarViewActivity extends BaseActivity implements OnClickListener, OnCellClickListener, InterviewInterface{  
    private ViewPager mViewPager;  
    private int mCurrentIndex = 498;  
    private CalendarCard[] mShowViews;  
    private CalendarViewAdapter<CalendarCard> adapter;  
    private SildeDirection mDirection = SildeDirection.NO_SILDE;  
    InterviewCalendarItem mInterviewCalendarItem = new InterviewCalendarItem();
	List<InterviewCalendarItem> mItmeArray = new ArrayList<InterviewCalendarItem>();
	
	
	CustomDate mCustomDate;
	CustomListView mListView;
	int Curindex = 0;
    enum SildeDirection {  
        RIGHT, LEFT, NO_SILDE;  
    }  
      
    private ImageButton preImgBtn, nextImgBtn;  
    private TextView monthText,tx_no_data;  
    private int month;
    private int year;
    private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
			        if(mItmeArray.size()>0)
			        {
					InterviewAdapter aInterviewAdapter = new InterviewAdapter(InterviewCalendarViewActivity.this,mItmeArray.get(Curindex).mInterviewTimeItems,InterviewCalendarViewActivity.this);
					
					if(mItmeArray.get(Curindex).mInterviewTimeItems.size()==0)
					{
						tx_no_data.setVisibility(View.VISIBLE);
					}
					else
					{
						mListView.setAdapter(aInterviewAdapter);
						aInterviewAdapter.notifyDataSetChanged();
						tx_no_data.setVisibility(View.INVISIBLE);
					}
						
					
			        }
			}
			break;
			}
		}
	};
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.activity_custom_calendar_view);  
        initHeaderView();
        tx_no_data = (TextView)findViewById(R.id.tx_no_data);
        tx_no_data.setVisibility(View.GONE);
        mViewPager = (ViewPager) this.findViewById(R.id.vp_calendar);  
        preImgBtn = (ImageButton) this.findViewById(R.id.btnPreMonth);  
        nextImgBtn = (ImageButton) this.findViewById(R.id.btnNextMonth);  
        monthText = (TextView) this.findViewById(R.id.tvCurrentMonth);  
      
        preImgBtn.setOnClickListener(this);  
        nextImgBtn.setOnClickListener(this);  
        mListView = (CustomListView)findViewById(R.id.list_view);
        mListView.setCanLoadMore(false);
        mListView.setCanRefresh(false);
          
        CalendarCard[] views = new CalendarCard[3];  
        for (int i = 0; i < 3; i++) {  
            views[i] = new CalendarCard(this, this);  
        }  
        adapter = new CalendarViewAdapter<CalendarCard>(views);  
        setViewPager();  
  
      
        mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				InterviewTimeItem item = mItmeArray.get(Curindex).mInterviewTimeItems.get((int)arg3);
				Intent intent = new Intent(InterviewCalendarViewActivity.this,InterviewDetailsCompleteActivity.class);
				intent.putExtra("id", item.id);
				startActivity(intent);
			}
		});
       
        month = DateUtil.getMonth();
        year = DateUtil.getYear();
    } 
    
	private void initHeaderView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText("我的面试");
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}
  
    private void setViewPager() {  
        mViewPager.setAdapter(adapter);  
        mViewPager.setCurrentItem(498);  
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {  
              
            @Override  
            public void onPageSelected(int position) {  
                measureDirection(position);  
                updateCalendarView(position);                 
            }  
              
            @Override  
            public void onPageScrolled(int arg0, float arg1, int arg2) {  
                  
            }  
              
            @Override  
            public void onPageScrollStateChanged(int arg0) {  
                  
            }  
        });  
    }  
  
  
 
  
    /** 
     * 计算方向 
     *  
     * @param arg0 
     */  
    private void measureDirection(int arg0) {  
  
        if (arg0 > mCurrentIndex) {  
            mDirection = SildeDirection.RIGHT;  
  
        } else if (arg0 < mCurrentIndex) {  
            mDirection = SildeDirection.LEFT;  
        }  
        mCurrentIndex = arg0;  
    }  
  
    // 更新日历视图  
    private void updateCalendarView(int arg0) {  
        mShowViews = adapter.getAllItems();  
        if (mDirection == SildeDirection.RIGHT) {  
            mShowViews[arg0 % mShowViews.length].rightSlide();  
        } else if (mDirection == SildeDirection.LEFT) {  
            mShowViews[arg0 % mShowViews.length].leftSlide();  
        }  
        mDirection = SildeDirection.NO_SILDE;  
    }

	@Override
	public void clickDate(CustomDate date) {
		// TODO Auto-generated method stub
	   tx_no_data.setVisibility(View.GONE);
	   if(mCustomDate == null)
	   {
		  mCustomDate = AotugeApplication.getInstance().mCurdate;
		  for(int i = 0; i <mItmeArray.size();i++)
			{
			   String month = String.format("%02d", mCustomDate.getMonth());
			   String day = String.format("%02d", mCustomDate.getDay());
			   String Date = String.valueOf(mCustomDate.getYear())+"-"+month+"-"+day;
			   if(Date.equals(mItmeArray.get(i).name))
			   {
				Curindex = i;	
			   }
			}
			handler.sendEmptyMessage(0);
	   }
	   else
	   {
		   if(mCustomDate.getMonth()==AotugeApplication.getInstance().mCurdate.getMonth())
		   {
			   mCustomDate =date;
			   for(int i = 0; i <mItmeArray.size();i++)
				{
				   String month = String.format("%02d", mCustomDate.getMonth());
				   String day = String.format("%02d", mCustomDate.getDay());
				   String Date = String.valueOf(mCustomDate.getYear())+"-"+month+"-"+day;
					
				   if(Date.equals(mItmeArray.get(i).name))
				   {
					Curindex = i;	
				   }
				}
				handler.sendEmptyMessage(0);
				
		   }
		 
	   }
		
		
	}

	@Override
	public void changeDate(CustomDate date) {
		// TODO Auto-generated method stub
		  String sdate1 = String.valueOf(date.year) +"年-"+String.valueOf(date.month) +"月";
		  monthText.setText(sdate1);
	        if(date.getYear() == year)
	        {
	        	if(date.getMonth() != month)
	        	{
	        		 String sdate = String.valueOf(date.year) +"-"+String.valueOf(date.month);
					 onSendGetData(sdate);
					 year = date.year;
					 month = date.month;
	        	}
	        }
	        else 
	        {
	        	 String sdate = String.valueOf(date.year) +"-"+String.valueOf(date.month);
				 onSendGetData(sdate);
				 year = date.year;
				 month = date.month;
	        }
		   

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		 switch (arg0.getId()) {  
	        case R.id.btnPreMonth:  
	            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);  
	            break;  
	        case R.id.btnNextMonth:  
	            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);  
	            break;  
	        default:  
	            break;  
	        }  
	}  
	
	
	private void onSendGetData(String date)
	{
		String url = NetUrlManager.getInterviewDate(date)+"&token=" +AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
	
		client.get(url,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getCanlendarInterviews(content,mItmeArray);
				if(info.success)
				{
					handler.sendEmptyMessage(0);
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				
			}
		});
	}

	@Override
	public void onBnClick(int pos) {
		// TODO Auto-generated method stub
	
		InterviewTimeItem item = mItmeArray.get(Curindex).mInterviewTimeItems.get(pos);
		if(item.status == 20)
		{
			sendInterviewAction(String.valueOf(item.id),"accept");
		}
		else if(item.status == 30)
		{
			sendInterviewAction(String.valueOf(item.id),"cancel");
		}
		else if(item.status == 150)
		{
			sendInterviewAction(String.valueOf(item.id),"absent");
		}
	}

	@Override
	public void onInfoClick(int pos) {
		// TODO Auto-generated method stub
		InterviewTimeItem item = mItmeArray.get(Curindex).mInterviewTimeItems.get(pos);
		Intent intent = new Intent(this,InterviewDetailsCompleteActivity.class);
		intent.putExtra("id", item.id);
		startActivity(intent);
	}
	
	void sendInterviewAction(String id, String action) {
		String url = NetUrlManager.getInterViewAction(id, action);
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = null;
				info = JosnParser.getInterviewAction(content);
				
				showMessageDialog("提示", info.info, "确定", null,0);
				
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);

			}
		});
	}
  
  
      
}  
