package com.aotu.Adapter;

import java.util.ArrayList;
import java.util.List;

import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.CustomGridView;
import com.aotu.data.CityItem;
import com.aotu.data.HomePageItemData;
import com.aotu.data.DirectionGridItem;
import com.aotu.data.DirectionsItem;
import com.aotu.data.ProvinceCityItem;
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

public class WorkPlaceAdapter extends BaseAdapter {

	
	private Context mContext;
	private LayoutInflater mInflater;
    private WorkPlaceInterface mWorkPlaceInterface;
    List<ProvinceCityItem> mCityItemList = new ArrayList<ProvinceCityItem>();
	public interface WorkPlaceInterface {
		public void onSeletcWorkPlace(CityItem item);
		public void onDeleteWorkPlace(CityItem item);
		public void onShowMore(int pos);
		public void onChange(int pos);
	}
	public WorkPlaceAdapter(Context context,WorkPlaceInterface aWorkPlaceInterface,List<ProvinceCityItem> CityItemList) {
		this.mContext = context;
	
		mInflater = LayoutInflater.from(mContext);
		mWorkPlaceInterface = aWorkPlaceInterface;
		mCityItemList = CityItemList;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCityItemList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mCityItemList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		ProvinceCityItem itemData = mCityItemList.get(position);
		return itemData.mType;
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ProvinceCityItem itemData = mCityItemList.get(position);
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
						mWorkPlaceInterface.onChange(Integer.valueOf(pos));
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
							mWorkPlaceInterface.onShowMore(Integer.valueOf(pos));
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
				tabViewHolder.mSelcetInfo.setText(itemData.provinve);
				if( itemData.mCityItemList.size()>0)
					itemData.mIsShowGridview = true;
				else
					itemData.mIsShowGridview = false;
				if(itemData.mIsShowGridview)
				{
					 tabViewHolder.mGridView.setVisibility(View.VISIBLE);
				     tabViewHolder.mGridView.setAdapter(new WorkPlaceGridAdapter(mContext,itemData.provinve, itemData.mCityItemList,mWorkPlaceInterface));
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
				viewHolder.mName.setText(itemData.provinve);
				if(itemData.mIsShowGridview)
				{
				   viewHolder.mGridView.setVisibility(View.VISIBLE);
				   viewHolder.mGridView.setAdapter(new WorkPlaceGridAdapter(mContext,itemData.provinve, itemData.mCityItemList,mWorkPlaceInterface));
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
