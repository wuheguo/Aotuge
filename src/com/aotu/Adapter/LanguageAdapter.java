package com.aotu.Adapter;
import java.util.List;
import com.aotu.data.InterviewConflictItem;
import com.aotu.data.LanguageItem;
import com.auto.aotuge.R;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class LanguageAdapter extends BaseAdapter {
	private List<LanguageItem> mData;
	private Context mContext;
	private LayoutInflater mInflater;
	private LanguageInterface mInterface;

	public interface LanguageInterface {
		public void onlanguageClick(int pos);

		public void onlevelClick(int pos);
	}

	public LanguageAdapter(Context context,
			List<LanguageItem> data,
			LanguageInterface aInterface) {
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

		LanguageItem itemData = mData.get(position);
		int messageType = getItemViewType(position);
		String pos = String.valueOf(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater
					.inflate(R.layout.item_curriculum_vitae_languages, null);
			viewHolder.tx_language_number= (TextView) convertView
					.findViewById(R.id.tx_languager_number);
			viewHolder.tx_language = (TextView) convertView
					.findViewById(R.id.tx_language);
			viewHolder.tx_language.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String pos = (String) arg0.getTag();
					mInterface.onlanguageClick(Integer.valueOf(pos));
				}
			});
			viewHolder.tx_level = (TextView) convertView
					.findViewById(R.id.tx_level);
            viewHolder.tx_level.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String pos = (String) arg0.getTag();
					mInterface.onlevelClick(Integer.valueOf(pos));
				}
			});
			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.tx_language_number.setText("语言"+String.valueOf(position+1));
		viewHolder.tx_language.setText(itemData.language);
		viewHolder.tx_language.setTag(pos);
		viewHolder.tx_level.setText(itemData.level);
		viewHolder.tx_level.setTag(pos);
		return convertView;

	}

	class ViewHolder {
		public TextView tx_language_number;
		public TextView tx_language;
		public TextView tx_level;
	}

}
