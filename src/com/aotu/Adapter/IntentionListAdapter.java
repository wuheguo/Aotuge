package com.aotu.Adapter;


import java.util.List;


import com.aotu.data.IntentionItem;

import com.auto.aotuge.R;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class IntentionListAdapter extends BaseAdapter {
	// http://test.honglei.net/admin/?tmp=interfaces
	private List<IntentionItem> mData;
	private Context mContext;
	private LayoutInflater mInflater;
	private IntentionInterface mInterface;

	public interface IntentionInterface {
		public void onDelete(int pos);
	}

	public IntentionListAdapter(Context context, List<IntentionItem> data,
			IntentionInterface aInterface) {
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

		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		IntentionItem itemData = mData.get(position);
		ViewHolder viewHolder = null;

		if (convertView == null) {

			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_intention_info, null);
			viewHolder.mInfo = (TextView) convertView
					.findViewById(R.id.tx_info1);
			viewHolder.mInfo1 = (TextView) convertView
					.findViewById(R.id.tx_info2);
			viewHolder.mInfo2 = (TextView) convertView
					.findViewById(R.id.tx_info3);
			viewHolder.mInfo3 = (TextView) convertView
					.findViewById(R.id.tx_info4);
			viewHolder.bn_delete = (Button)convertView.findViewById(R.id.bn_delete);
			viewHolder.bn_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					mInterface.onDelete(position);
				}
			});
			convertView.setTag(viewHolder);

		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.mInfo.setText(itemData.name);
		String workposition = "";
		for(int i = 0; i <itemData.position.size();i++)
		{
			workposition += itemData.position.get(i).name;
			if(i != itemData.position.size() -1)
				workposition += ",";
				
		}
		viewHolder.mInfo1.setText(workposition);
		String city = "工作地点:";
		for(int i = 0; i < itemData.cities.size() ; i++)
		{
			if(i==itemData.cities.size()-1)
			    city += itemData.cities.get(i).name;
			else
				city += itemData.cities.get(i).name+"、";	
		}
		viewHolder.mInfo2.setText(city);
		
		String pos = "期望薪金:"+itemData.salary_text;
		
		viewHolder.mInfo3.setText(pos);

		return convertView;

	}

	class ViewHolder {
		public TextView mInfo;
		public TextView mInfo1;
		public TextView mInfo2;
		public TextView mInfo3;
		public Button bn_delete;
	}

}
