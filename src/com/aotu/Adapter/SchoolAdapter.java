package com.aotu.Adapter;


import java.util.List;


import com.aotu.data.SchoolItem;
import com.auto.aotuge.R;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;

import android.widget.RelativeLayout;
import android.widget.TextView;

public class SchoolAdapter extends BaseAdapter{
//http://test.honglei.net/admin/?tmp=interfaces
	private List<SchoolItem> mData;
	private Context mContext;
	private LayoutInflater mInflater;
    private SchoolInterface mInterface;
	public interface SchoolInterface {
		public void onChange();
	}
	public SchoolAdapter(Context context, List<SchoolItem> data,SchoolInterface aInterface) {
		this.mContext = context;
		this.mData = data;
		mInflater = LayoutInflater.from(mContext);
		mInterface = aInterface;
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
		SchoolItem itemData = mData.get(position);
		return itemData.mType;
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		SchoolItem itemData = mData.get(position);
		int messageType = getItemViewType(position);
		TabViewHolder tabViewHolder = null;
		ViewHolder   viewHolder = null;
	
		if (convertView == null) {
			switch(messageType)
			{
				case 0:
				{
				  tabViewHolder = new TabViewHolder();
				  convertView = mInflater.inflate(R.layout.item_school_tab, null);
				  tabViewHolder.mName = (TextView)convertView.findViewById(R.id.tx_city);
				  tabViewHolder.mNext = (RelativeLayout)convertView.findViewById(R.id.rl_next);
				  tabViewHolder.mNext.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mInterface.onChange();
					}
				});
				  convertView.setTag(tabViewHolder);
				}
				break;
				case 1:
				{
					viewHolder = new ViewHolder();
					convertView = mInflater.inflate(R.layout.item_school_content, null);
					viewHolder.mName = (TextView)convertView.findViewById(R.id.tx_city);
					convertView.setTag(viewHolder);
					
				}
				break;
			}
		}

		switch(messageType)
		{
			case 0:
			{
				tabViewHolder =(TabViewHolder) convertView.getTag();
				tabViewHolder.mName.setText(itemData.name);
				
			}
			break;
			case 1:
			{
				viewHolder = (ViewHolder) convertView.getTag();	
				viewHolder.mName.setText(itemData.name);
			}
		}
		
	   
	    
		return convertView;

	}
	
	class TabViewHolder {
	   public TextView mName;
	   public RelativeLayout mNext;
		
	}
	
	class ViewHolder {
	  public TextView mName;
	}

}
