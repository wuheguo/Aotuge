package com.aotu.activity;
/*
 * 评价界面
 */

import java.util.LinkedList;
import java.util.List;

import com.aotu.Adapter.GuideViewAdapter;
import com.aotu.Adapter.InterviewScoreAdapter;
import com.aotu.Adapter.InterviewScoreAdapter.InterviewscoreInterface;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomListView;
import com.aotu.data.InterviewInfoItem;
import com.aotu.data.Interviewscore;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.SplashImageItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InterviewScoreActivity extends BaseActivity implements InterviewscoreInterface, OnClickListener {
	
	InterviewScoreAdapter mAdapter;
	CustomListView list_view;
	List<Interviewscore> mItemArray = new LinkedList<Interviewscore>();
	
	ImageView im_company,im_total1,im_total2,im_total3,im_total4,im_total5;
	TextView tx_company_name,tx_position,tx_timer;
	TextView tx_total_score;
	Button bn_submit;
	//InterviewInfoItem mInterviewInfoItem = new InterviewInfoItem();
	int total = 1;
	int item1 = 0;
	
	String company ; // string 公司名称
	String title ; // string 职位名称
	String interview_time ; // string
	int id;
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
			if(mAdapter == null)
				mAdapter = new InterviewScoreAdapter(InterviewScoreActivity.this,mItemArray,InterviewScoreActivity.this);
			list_view.setAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
			}
			break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interview_score);
		Intent intent = getIntent();
		company = intent.getStringExtra("company");
		title = intent.getStringExtra("title");
		interview_time = intent.getStringExtra("interview_time");
		id = intent.getIntExtra("id", -1);
		
		initView();
		getScoreData();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private void initHeadView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		RelativeLayout menuleft = (RelativeLayout) findViewById(R.id.left_menu_layout);
		menuleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText("面试评价");
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}

	private void initView() {
		initHeadView();
		list_view = (CustomListView)findViewById(R.id.list_view);
		list_view.setCanLoadMore(false);
		list_view.setCanRefresh(false);
		
		
		im_company = (ImageView)findViewById(R.id.im_company);
		
		tx_company_name= (TextView)findViewById(R.id.tx_company_name);
		tx_position= (TextView)findViewById(R.id.tx_position);
		tx_timer= (TextView)findViewById(R.id.tx_timer);
		
		im_total1= (ImageView)findViewById(R.id.im_total1);
		
		im_total2= (ImageView)findViewById(R.id.im_total2);
		im_total3= (ImageView)findViewById(R.id.im_total3);
		im_total4= (ImageView)findViewById(R.id.im_total4);
		im_total5= (ImageView)findViewById(R.id.im_total5);
		
		bn_submit = (Button)findViewById(R.id.bn_submit);
		bn_submit.setOnClickListener(this);
		
		tx_company_name.setText(company);
		tx_position.setText(title);
		tx_timer.setText(interview_time);
	}

	

	@Override
	public void onImClick(int pos, int type) {
		// TODO Auto-generated method stub
		mItemArray.get(pos).pos = type;
		handler.sendEmptyMessage(0);
	}
	
	public void getScoreData()
	{
		String url = NetUrlManager.getInterviewScore();
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.get(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getInterviewScore(content,mItemArray);
				if(info.success)
					handler.sendEmptyMessage(0);
				else
				    showMessageDialog("提示", info.info, "确定", null,1);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showMessageDialog("提示", "服务器忙请稍后再试", "确定", null,0);
			}

		});
	}
	
	void onSubmitScore()
	{
		String url = NetUrlManager.getInterviewSumbitScore();
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", String.valueOf(id));
		params.put("total", String.valueOf(total));
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getSubmitScore(content);
				if(info.success)
					handler.sendEmptyMessage(0);
				else
				    showMessageDialog("提示", info.info, "确定", null,1);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showMessageDialog("提示", "服务器忙请稍后再试", "确定", null,0);
			}

		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.bn_submit:
		{
			onSubmitScore();
		}
		break;
		}
	}
	
	public void onTotal1(View arg0)
	{
		total =1;
		changexingxing();
	}
	
	public void onTotal2(View arg0)
	{
		total =2;
		changexingxing();
	}
	
	public void onTotal3(View arg0)
	{
		total =3;
		changexingxing();
	}
	
	public void onTotal4(View arg0)
	{
		total =4;
		changexingxing();
	}
	public void onTotal5(View arg0)
	{
		total =5;
		changexingxing();
	}
	
	private void changexingxing()
	{
		im_total1.setBackground(getResources().getDrawable(R.drawable.xing_big2));
		im_total2.setBackground(getResources().getDrawable(R.drawable.xing_big2));
		im_total3.setBackground(getResources().getDrawable(R.drawable.xing_big2));
		im_total4.setBackground(getResources().getDrawable(R.drawable.xing_big2));
		im_total5.setBackground(getResources().getDrawable(R.drawable.xing_big2));
		if(total==1)
		{
			im_total1.setBackground(getResources().getDrawable(R.drawable.xing_big1));
		}
		else if(total==2)
		{
			im_total1.setBackground(getResources().getDrawable(R.drawable.xing_big1));
			im_total2.setBackground(getResources().getDrawable(R.drawable.xing_big1));
		}
		
		else if(total==3)
		{
			im_total1.setBackground(getResources().getDrawable(R.drawable.xing_big1));
			im_total2.setBackground(getResources().getDrawable(R.drawable.xing_big1));
			im_total3.setBackground(getResources().getDrawable(R.drawable.xing_big1));
		}
		else if(total==4)
		{
			im_total1.setBackground(getResources().getDrawable(R.drawable.xing_big1));
			im_total2.setBackground(getResources().getDrawable(R.drawable.xing_big1));
			im_total3.setBackground(getResources().getDrawable(R.drawable.xing_big1));
			im_total4.setBackground(getResources().getDrawable(R.drawable.xing_big1));
			
		}
		else if(total==5)
		{
			im_total1.setBackground(getResources().getDrawable(R.drawable.xing_big1));
			im_total2.setBackground(getResources().getDrawable(R.drawable.xing_big1));
			im_total3.setBackground(getResources().getDrawable(R.drawable.xing_big1));
			im_total4.setBackground(getResources().getDrawable(R.drawable.xing_big1));
			im_total5.setBackground(getResources().getDrawable(R.drawable.xing_big1));
		}
	}


	
}
