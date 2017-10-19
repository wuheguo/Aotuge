package com.aotu.Adapter;
import java.util.List;
import com.aotu.data.InterviewConflictItem;
import com.aotu.data.InterviewProgressItem;
import com.auto.aotuge.R;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class InterviewProgressAdapter extends BaseAdapter {
	private List<InterviewProgressItem> mData;
	private Context mContext;
	private LayoutInflater mInflater;
	
	public InterviewProgressAdapter(Context context,
			List<InterviewProgressItem> data) {
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
		InterviewProgressItem itemData = mData.get(position);
		return itemData.type;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		InterviewProgressItem itemData = mData.get(position);
		int messageType = getItemViewType(position);
		ViewHolder viewHolder = null;

		if (convertView == null) {

			viewHolder = new ViewHolder();
			switch(messageType)
			{
			case 0:
				convertView = mInflater.inflate(R.layout.item_interview_details1, null);
				break;
			case 1:
				convertView = mInflater.inflate(R.layout.item_interview_details2, null);
				break;
			case 2:
				convertView = mInflater.inflate(R.layout.item_interview_details3, null);
				break;
			}
			viewHolder.tx_interview_tab1 = (TextView) convertView
					.findViewById(R.id.tx_interview_tab1);
			viewHolder.tx_interview_tab2 = (TextView) convertView
					.findViewById(R.id.tx_interview_tab2);
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.tx_interview_tab1.setText(itemData.name);
		viewHolder.tx_interview_tab1.setText(itemData.time);
		return convertView;

	}

	class ViewHolder {
		public TextView tx_interview_tab1;
		public TextView tx_interview_tab2;

		

	}

}
