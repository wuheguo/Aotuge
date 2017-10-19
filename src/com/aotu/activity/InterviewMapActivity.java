package com.aotu.activity;

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

public class InterviewMapActivity extends BaseActivity {

	TextView tx_company_name, tx_address;
	ImageView mMap, im_company;

	InterviewInfoItem mInterviewInfoItem = new InterviewInfoItem();

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {

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
		mInterviewInfoItem = (InterviewInfoItem) getIntent()
				.getSerializableExtra("item");
		initView();
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
		titleText.setText("公司位置");
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}

	private void initView() {
		initHeadView();
		im_company = (ImageView) findViewById(R.id.im_company);
		tx_company_name = (TextView) findViewById(R.id.tx_company_name);
		tx_address = (TextView) findViewById(R.id.tx_address);
		String company = mInterviewInfoItem.company;
		String title = mInterviewInfoItem.address;

		tx_company_name.setText(company);
		tx_address.setText(title);

	}
}
