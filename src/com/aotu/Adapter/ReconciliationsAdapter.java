package com.aotu.Adapter;


import java.util.List;


import com.aotu.data.ReconciliationsItem;

import com.auto.aotuge.R;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.TextView;
/*
 * 对账单
 */
public class ReconciliationsAdapter extends BaseAdapter{
//http://test.honglei.net/admin/?tmp=interfaces
	private List<ReconciliationsItem> mData;
	private Context mContext;
	private LayoutInflater mInflater;
   

	public ReconciliationsAdapter(Context context, List<ReconciliationsItem> data) {
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
		ReconciliationsItem itemData = mData.get(position);
		return itemData.mType;
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ReconciliationsItem itemData = mData.get(position);
		int messageType = getItemViewType(position);
		TabViewHolder tabViewHolder = null;
		
		if (convertView == null) {
		tabViewHolder = new TabViewHolder();
		convertView = mInflater.inflate(R.layout.item_reconciliations, null);
		tabViewHolder.tx_timer = (TextView)convertView.findViewById(R.id.tx_timer);
		tabViewHolder.mName = (TextView)convertView.findViewById(R.id.tx_message);
		tabViewHolder.mMoney = (TextView)convertView.findViewById(R.id.tx_money);
		convertView.setTag(tabViewHolder);
		}
		tabViewHolder =(TabViewHolder) convertView.getTag();
		tabViewHolder.mName.setText(itemData.title);
		switch(messageType)
		{
			case 0:
			{
				tabViewHolder.mMoney.setTextColor(mContext.getResources().getColor(R.color.font_strong_color));
				tabViewHolder.mMoney.setText(String.valueOf(itemData.amount)+"元");
				tabViewHolder.tx_timer.setText(itemData.time);
			}
			break;
			case 1:
			{
				tabViewHolder.mMoney.setTextColor(mContext.getResources().getColor(R.color.font_right_color));
				tabViewHolder.mMoney.setText("+"+String.valueOf(itemData.amount)+"元");
				tabViewHolder.tx_timer.setText(itemData.time);
			}
			break;
			
		}
		
	   
	    
		return convertView;

	}
	
	class TabViewHolder {
	   public TextView mName;
	   public TextView mMoney;
	   public TextView tx_timer;
	 
		
	}
	
	

}
