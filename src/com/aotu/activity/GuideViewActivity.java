package com.aotu.activity;


import java.util.LinkedList;
import java.util.List;

import com.aotu.Adapter.GuideViewAdapter;
import com.aotu.baseview.BaseActivity;
import com.aotu.data.SplashImageItem;
import com.auto.aotuge.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;

public class GuideViewActivity extends BaseActivity implements
		OnPageChangeListener {
	ViewPager mImagePager;
	GuideViewAdapter mAdapter;
	List<SplashImageItem> mPaths = new LinkedList<SplashImageItem>();
	int mPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide_view);
		initViewData();
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

	private void initView() {
		mImagePager = (ViewPager) findViewById(R.id.splash_pager);
		mAdapter = new GuideViewAdapter(this);
		mAdapter.UpdateImageList(mPaths);
		mAdapter.imguideView = this;
		mImagePager.setAdapter(mAdapter);
		mImagePager.setOnPageChangeListener(this);
		mImagePager.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mPos == mPaths.size() - 1) {

				}
			}
		});
	}

	private void initViewData() {
		SplashImageItem item = new SplashImageItem();
		item.mImageID = R.drawable.step1;

		SplashImageItem item1 = new SplashImageItem();
		item1.mImageID = R.drawable.step2;

		SplashImageItem item2 = new SplashImageItem();
		item2.mImageID = R.drawable.step3;

		mPaths.add(item);
		mPaths.add(item1);
		mPaths.add(item2);

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		mPos = arg0;

	}

	public void goHome() {
		Intent intent = new Intent(GuideViewActivity.this, MainActivityFragment.class);
		startActivity(intent);
		finish();
	}
}
