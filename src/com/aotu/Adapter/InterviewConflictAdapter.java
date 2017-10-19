package com.aotu.Adapter;
import java.util.List;
import com.aotu.data.InterviewConflictItem;
import com.auto.aotuge.R;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class InterviewConflictAdapter extends BaseAdapter {
	private List<InterviewConflictItem> mData;
	private Context mContext;
	private LayoutInflater mInflater;
	private InterviewConflictInterface mInterface;

	public interface InterviewConflictInterface {
		public void onBnClick(int pos, int type);

		public void onInfoClick(int pos);
	}

	public InterviewConflictAdapter(Context context,
			List<InterviewConflictItem> data,
			InterviewConflictInterface aInterface) {
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

		InterviewConflictItem itemData = mData.get(position);
		int messageType = getItemViewType(position);
		String pos = String.valueOf(position);
		ViewHolder viewHolder = null;

		if (convertView == null) {

			viewHolder = new ViewHolder();
			convertView = mInflater
					.inflate(R.layout.item_interview_details, null);

			viewHolder.tx_info = (TextView) convertView
					.findViewById(R.id.tx_info);
			viewHolder.tx_info1 = (TextView) convertView
					.findViewById(R.id.tx_info1);
			viewHolder.bn_left = (Button) convertView
					.findViewById(R.id.bn_left);
			viewHolder.bn_left.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int pos = Integer.valueOf((String) arg0.getTag());
					mInterface.onBnClick(pos, 0);
				}
			});
			viewHolder.bn_mid = (Button) convertView.findViewById(R.id.bn_mid);
			viewHolder.bn_mid.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int pos = Integer.valueOf((String) arg0.getTag());
					mInterface.onBnClick(pos, 1);
				}
			});
			viewHolder.bn_right = (Button) convertView
					.findViewById(R.id.bn_right);
			viewHolder.bn_right.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int pos = Integer.valueOf((String) arg0.getTag());
					mInterface.onBnClick(pos, 2);
				}
			});

			convertView.setTag(viewHolder);

		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.tx_info.setText(itemData.company);
		String timer = "面试时间:" + itemData.interview_time;
		viewHolder.tx_info1.setText(timer);
		viewHolder.bn_left.setTag(pos);
		viewHolder.bn_mid.setTag(pos);
		viewHolder.bn_right.setTag(pos);

		return convertView;

	}

	class ViewHolder {
		public TextView tx_info;
		public TextView tx_info1;

		public Button bn_left;
		public Button bn_mid;
		public Button bn_right;

	}

}
