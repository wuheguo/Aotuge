package com.aotu.Adapter;

import java.util.ArrayList;
import java.util.List;

import com.aotu.baseview.CustomGridView;
import com.aotu.data.HomePageItemData;
import com.auto.aotuge.R;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePageAdapter extends BaseAdapter{

	private List<HomePageItemData> mData;
	private Context mContext;
	private LayoutInflater mInflater;

	
	public HomePageAdapter(Context context, List<HomePageItemData> data) {
		this.mContext = context;
		this.mData = data;
		mInflater = LayoutInflater.from(mContext);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 1;
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		HomePageItemData itemData = mData.get(position);
		int messageType = getItemViewType(position);
		FristViewHolder fristViewHolder = null;
		
	
		if (convertView == null) {
			fristViewHolder = new FristViewHolder();
			convertView = mInflater.inflate(R.layout.item_fragment_home, null);
			fristViewHolder.titleText = (CustomGridView)convertView.findViewById(R.id.grid_view);
			convertView.setTag(fristViewHolder);
		}

	    fristViewHolder =(FristViewHolder) convertView.getTag();
	    List<String> aData = new ArrayList<String>();
	    aData.add("你好");
	    aData.add("他好");
	    aData.add("你好");
	    aData.add("他好");
	    aData.add("你好");
	    aData.add("他好");
	    aData.add("你好");
	    aData.add("他好");
	    aData.add("你好");
	    aData.add("他好");
	    aData.add("你好");
	    aData.add("他好");
	    fristViewHolder.titleText.setAdapter(new TimeGridAdapter(mContext,aData));
	    
		return convertView;

	}
	
	class FristViewHolder {
		private CustomGridView titleText;
		private TimeGridAdapter adapter;
	}

}
