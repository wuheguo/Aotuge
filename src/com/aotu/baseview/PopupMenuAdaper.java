package com.aotu.baseview;

import java.util.List;

import com.auto.aotuge.R;





import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PopupMenuAdaper extends BaseAdapter{

	private List<PopupMeunItem> mData;
	private Context mContext;
	private LayoutInflater mInflater;
	
	
	public PopupMenuAdaper(Context context, List<PopupMeunItem> data) {
		this.mContext = context;
		this.mData = data;
		mInflater = LayoutInflater.from(mContext);
	}
	
	public int getCount() {
		return mData.size();
	}

	public Object getItem(int position) {
		return mData.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		PopupMeunItem itemData = mData.get(position);
		ViewHolder viewHolder = null;
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.pop_menu_item, null);
			viewHolder = new ViewHolder();
			viewHolder.MenuText = (TextView)convertView.findViewById(R.id.menu_item_tx);
			convertView.setTag(viewHolder);
		}
		
		viewHolder = (ViewHolder)convertView.getTag();
		viewHolder.MenuText.setText(itemData.mMeunText);
		return convertView;
	}
	
	class ViewHolder {
		private TextView MenuText;
	}

}
