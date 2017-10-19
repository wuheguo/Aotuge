package com.aotu.Adapter;

import java.util.List;
import com.aotu.data.SearchWorkResult;
import com.auto.aotuge.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SearchWorkResultAdapter extends BaseAdapter{

	private List<SearchWorkResult> mData;
	private Context mContext;
	private LayoutInflater mInflater;
    private SearchWorkResultInterface mInterface;
	public interface SearchWorkResultInterface {
		public void onChange();
		public void onSelect(int pos);
	}
	public SearchWorkResultAdapter(Context context, List<SearchWorkResult> data,SearchWorkResultInterface aInterface) {
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
		SearchWorkResult itemData = mData.get(position);
		return itemData.mType;
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		SearchWorkResult itemData = mData.get(position);
		int messageType = getItemViewType(position);
		TabViewHolder tabViewHolder = null;
		ViewHolder   viewHolder = null;
	
		if (convertView == null) {
			switch(messageType)
			{
				case 0:
				{
				  tabViewHolder = new TabViewHolder();
				  convertView = mInflater.inflate(R.layout.item_search_work_result_tab, null);
				  tabViewHolder.mName = (TextView)convertView.findViewById(R.id.tx_tab);
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
				  convertView = mInflater.inflate(R.layout.item_search_work_result, null);
				  viewHolder.checkBox  = (CheckBox)convertView.findViewById(R.id.checkBox);
				  viewHolder.salary= (TextView)convertView.findViewById(R.id.tx_salary);
				  viewHolder.timer= (TextView)convertView.findViewById(R.id.tx_timer);
				  viewHolder.position= (TextView)convertView.findViewById(R.id.tx_position);
				  viewHolder.info= (TextView)convertView.findViewById(R.id.tx_info);
				  viewHolder.company= (TextView)convertView.findViewById(R.id.tx_company);
				  viewHolder.checkBox.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						int pos = Integer.valueOf((String)arg0.getTag());
						mInterface.onSelect(pos);
					}
				});
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
				String info =  itemData.city + "  "+itemData.name;
				tabViewHolder.mName.setText(info);
			}
			break;
			case 1:
			{
				viewHolder = (ViewHolder) convertView.getTag();	
				viewHolder.checkBox.setChecked(itemData.checkBox);
				viewHolder.checkBox.setTag(String.valueOf(position));
				viewHolder.salary.setText(itemData.salary);
				viewHolder.timer.setText(itemData.timer);
				viewHolder.position.setText(itemData.position);
				viewHolder.info.setText(itemData.info);
				viewHolder.company.setText(itemData.company);
			}
			break;
		}
		return convertView;

	}
	
	class TabViewHolder {
	   public TextView mName;
	   public RelativeLayout mNext;
		
	}
	
	class ViewHolder {
	    public CheckBox  checkBox;
		public TextView  salary;
		public TextView  timer;
		public TextView  position;
		public TextView  info;
		public TextView  company;
	}

}
