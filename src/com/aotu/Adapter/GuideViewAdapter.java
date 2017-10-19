package com.aotu.Adapter;

import java.util.ArrayList;
import java.util.List;

import com.aotu.activity.GuideViewActivity;
import com.aotu.data.SplashImageItem;



import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GuideViewAdapter extends PagerAdapter {

	private List<SplashImageItem> mPaths;
	private Context mContext;
	public GuideViewActivity imguideView;

	public GuideViewAdapter(Context aContext) {
		mContext = aContext;
	}

	public void UpdateImageList(List<SplashImageItem> paths) {
		mPaths = paths;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mPaths == null) {
			return 0;
		}
		return mPaths.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub

		ImageView iv = new ImageView(mContext);
		try {
			iv.setBackgroundResource(mPaths.get(position).mImageID);
			if (position == mPaths.size() - 1)
				iv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (imguideView != null)
							imguideView.goHome();
					}
				});
		} catch (OutOfMemoryError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		((ViewPager) container).addView(iv, 0);
		return iv;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == (View) arg1;
	}
}
