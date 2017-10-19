package com.aotu.Adapter;

import java.util.List;

import com.aotu.Adapter.PositionAdapter.PositionInterface;
import com.aotu.data.CompanyScoreitem;
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
import android.widget.ImageView;
import android.widget.TextView;




public class CompanyStoreGridAdapter extends BaseAdapter {

	private Context mContext;
	private List<CompanyScoreitem> mData;
	public int mType;
	
	
	public CompanyStoreGridAdapter(Context context, List<CompanyScoreitem> aData) {
		mContext = context;
		mData = aData;
		
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
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		CompanyScoreitem item = mData.get(arg0);
		int type = getItemViewType(arg0);
		if (convertView == null) {
			if(type==0)
			{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item_score, null);
			viewHolder = new ViewHolder();
			viewHolder.mViewText = (TextView) convertView.findViewById(R.id.tx_score);
			viewHolder.mImStore = (ImageView) convertView.findViewById(R.id.im_score);
			convertView.setTag(viewHolder);
			}
			
		}
		viewHolder = (ViewHolder) convertView.getTag();
		
		viewHolder.mViewText.setText(item.name);
		if(item.score == 1)
		{
			viewHolder.mImStore.setBackground(mContext.getResources().getDrawable(R.drawable.wu_jiao_xing_big1));
		}
		else if(item.score == 2)
		{
			viewHolder.mImStore.setBackground(mContext.getResources().getDrawable(R.drawable.wu_jiao_xing_big2));
		}
		else if(item.score == 3)
		{
			viewHolder.mImStore.setBackground(mContext.getResources().getDrawable(R.drawable.wu_jiao_xing_big3));
		}
		else if(item.score == 4)
		{
			viewHolder.mImStore.setBackground(mContext.getResources().getDrawable(R.drawable.wu_jiao_xing_big4));
		}
		else if(item.score == 5)
		{
			viewHolder.mImStore.setBackground(mContext.getResources().getDrawable(R.drawable.wu_jiao_xing_big5));
		}
	
		return convertView;
	}

	class ViewHolder {
		public TextView mViewText;
		public ImageView  mImStore;
	}

}
