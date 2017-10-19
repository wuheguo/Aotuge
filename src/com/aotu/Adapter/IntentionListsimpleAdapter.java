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

public class IntentionListsimpleAdapter extends BaseAdapter {
	// http://test.honglei.net/admin/?tmp=interfaces
	private List<IntentionItem> mData;
	private Context mContext;
	private LayoutInflater mInflater;
	private IntentionListsimpleInterface mInterface;

	public interface IntentionListsimpleInterface {
		public void onDelete(int pos);
	}

	public IntentionListsimpleAdapter(Context context, List<IntentionItem> data,
			IntentionListsimpleInterface aInterface) {
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
			convertView = mInflater.inflate(R.layout.item_intention_info_simple, null);
			viewHolder.mInfo = (TextView) convertView
					.findViewById(R.id.tx_info1);
//			viewHolder.bn_delete = (Button)convertView.findViewById(R.id.bn_delete);
//			viewHolder.bn_delete.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					mInterface.onDelete(position);
//				}
//			});
			convertView.setTag(viewHolder);

		}
		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.mInfo.setText(itemData.name);
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
