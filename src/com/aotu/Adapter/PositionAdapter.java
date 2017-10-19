package com.aotu.Adapter;

import java.util.ArrayList;
import java.util.List;

import com.aotu.Adapter.WorkPlaceAdapter.TabViewHolder;
import com.aotu.baseview.CustomGridView;
import com.aotu.data.HomePageItemData;
import com.aotu.data.DirectionGridItem;
import com.aotu.data.DirectionsItem;
import com.aotu.data.SchoolItem;
import com.auto.aotuge.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PositionAdapter extends BaseAdapter {

	private List<DirectionsItem> mData;
	private Context mContext;
	private LayoutInflater mInflater;
    private PositionInterface mPositionInterface;
	public interface PositionInterface {
		public void onSeletcPosition(DirectionGridItem item);
		public void onDeletePosition(DirectionGridItem item);
		public void onShowMore(int pos);
		public void onChange(int pos);
	}
	public PositionAdapter(Context context, List<DirectionsItem> data,PositionInterface aPositionInterface) {
		this.mContext = context;
		this.mData = data;
		mInflater = LayoutInflater.from(mContext);
		mPositionInterface = aPositionInterface;
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
		DirectionsItem itemData = mData.get(position);
		return itemData.mType;
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		DirectionsItem itemData = mData.get(position);
		int messageType = getItemViewType(position);
		TabViewHolder tabViewHolder = null;
		ViewHolder   viewHolder = null;
	
		if (convertView == null) {
			switch(messageType)
			{
				case 0:
				{
				  tabViewHolder = new TabViewHolder();
				  convertView = mInflater.inflate(R.layout.item_workplace_tab, null);
				  tabViewHolder.mSelcetInfo = (TextView)convertView.findViewById(R.id.tx_selcet_info);
				  tabViewHolder.mGridView = (CustomGridView)convertView.findViewById(R.id.grid_view);
				 
				  tabViewHolder.mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
				  tabViewHolder.mNext = (RelativeLayout)convertView.findViewById(R.id.rl_next);
				  tabViewHolder.mNext.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String pos = (String)arg0.getTag();
						mPositionInterface.onChange(Integer.valueOf(pos));
					}
				});
				  convertView.setTag(tabViewHolder);
				}
				break;
				case 1:
				{
					viewHolder = new ViewHolder();
					convertView = mInflater.inflate(R.layout.item_workplace_body, null);
					viewHolder.mName = (TextView)convertView.findViewById(R.id.tx_name);
					
					viewHolder.mGridView = (CustomGridView)convertView.findViewById(R.id.grid_view);
					viewHolder.mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
					viewHolder.mNext = (RelativeLayout)convertView.findViewById(R.id.rl_next);
					viewHolder.mNext.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							String pos = (String)arg0.getTag();
							mPositionInterface.onShowMore(Integer.valueOf(pos));
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
				tabViewHolder.mSelcetInfo.setText(itemData.Name);
				if(itemData.mIsShowGridview)
				{
					 tabViewHolder.mGridView.setVisibility(View.VISIBLE);
				     tabViewHolder.mGridView.setAdapter(new PositionGridAdapter(mContext, itemData.mGridItemList,mPositionInterface));
				}
				else
					tabViewHolder.mGridView.setVisibility(View.GONE);
				tabViewHolder.mNext.setTag(String.valueOf(position));
				convertView.setVisibility(View.INVISIBLE);
			}
			break;
			case 1:
			{
				viewHolder = (ViewHolder) convertView.getTag();	
				viewHolder.mName.setText(itemData.Name);
				if(itemData.mIsShowGridview)
				{
				   viewHolder.mGridView.setVisibility(View.VISIBLE);
				   viewHolder.mGridView.setAdapter(new PositionGridAdapter(mContext, itemData.mGridItemList,mPositionInterface));
				}
				else
					viewHolder.mGridView.setVisibility(View.GONE);
				viewHolder.mNext.setTag(String.valueOf(position));
				
			}
			break;
		}
		
	   
	    
		return convertView;

	}
	
	class TabViewHolder {
	   public TextView mSelcetInfo;
	   public RelativeLayout mNext;
	   public CustomGridView mGridView;
	}
	
	class ViewHolder {
	  public TextView mName;
	  public RelativeLayout mNext;
	  public CustomGridView mGridView;
	}
}
