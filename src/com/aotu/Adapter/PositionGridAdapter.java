package com.aotu.Adapter;

import java.util.List;

import com.aotu.Adapter.PositionAdapter.PositionInterface;
import com.aotu.data.DirectionGridItem;
import com.auto.aotuge.R;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;




public class PositionGridAdapter extends BaseAdapter {

	private Context mContext;
	private List<DirectionGridItem> mData;
	public int mType;
	private PositionInterface mPositionInterface;
	
	public PositionGridAdapter(Context context, List<DirectionGridItem> aData,PositionInterface aPositionInterface) {
		mContext = context;
		mData = aData;
		mPositionInterface = aPositionInterface;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return mData.get(position).Type;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		DirectionGridItem item = mData.get(arg0);
		int type = getItemViewType(arg0);
		if (convertView == null) {
			if(type==0)
			{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item_position_tab, null);
			viewHolder = new ViewHolder();
			viewHolder.mViewText = (TextView) convertView.findViewById(R.id.tx_position);
			viewHolder.mDelete = (Button)convertView.findViewById(R.id.bn_delete);
		
			viewHolder.mDelete.setOnClickListener(new OnClickListener() {	
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					DirectionGridItem item =(DirectionGridItem) arg0.getTag();
					mPositionInterface.onDeletePosition(item);
				
				}
			});
			convertView.setTag(viewHolder);
			}
			else if(type==1)
			{
				convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item_position_content, null);
				viewHolder = new ViewHolder();
				viewHolder.mViewText = (TextView) convertView.findViewById(R.id.tx_position);
				viewHolder.mViewText.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						DirectionGridItem item =(DirectionGridItem) arg0.getTag();
						mPositionInterface.onSeletcPosition(item);
					}
				});
				convertView.setTag(viewHolder);
			}
		}
		viewHolder = (ViewHolder) convertView.getTag();
		if(type==0)
		{
		viewHolder.mViewText.setText(item.Name);
		viewHolder.mDelete.setTag(item);
		}
		else if(type ==1)
		{
		viewHolder.mViewText.setText(item.Name);
		viewHolder.mViewText.setTag(item);
		}
		return convertView;
	}

	class ViewHolder {
		public TextView mViewText;
		public Button  mDelete;
	}

}
