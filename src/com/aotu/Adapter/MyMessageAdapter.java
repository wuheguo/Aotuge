package com.aotu.Adapter;
import java.util.List;
import com.aotu.data.MyMessageItem;
import com.auto.aotuge.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;
/*
 * 我的信息
 */
public class MyMessageAdapter extends BaseAdapter{
//http://test.honglei.net/admin/?tmp=interfaces
	private List<MyMessageItem> mData;
	private Context mContext;
	private LayoutInflater mInflater;
   

	public MyMessageAdapter(Context context, List<MyMessageItem> data) {
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
		MyMessageItem itemData = mData.get(position);
		return 0;
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		MyMessageItem itemData = mData.get(position);
		
		TabViewHolder tabViewHolder = null;
		
		if (convertView == null) {
		tabViewHolder = new TabViewHolder();
		convertView = mInflater.inflate(R.layout.item_my_message, null);
		tabViewHolder.mMessage = (TextView)convertView.findViewById(R.id.tx_message);
		tabViewHolder.mDate = (TextView)convertView.findViewById(R.id.tx_date);
		convertView.setTag(tabViewHolder);
		}
		tabViewHolder =(TabViewHolder) convertView.getTag();
		tabViewHolder.mMessage.setText(itemData.messag);
	
		tabViewHolder.mDate.setText(itemData.date);
			
		return convertView;

	}
	
	class TabViewHolder {
	   public TextView mMessage;
	   public TextView mDate;
	 
		
	}
	
	

}
