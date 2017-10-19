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
import android.widget.ImageView;
import android.widget.TextView;




public class WorkWelfareGridAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> mData;
	public int mType;
	
	
	public WorkWelfareGridAdapter(Context context, List<String> aData) {
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
		String item = mData.get(arg0);
		if (convertView == null) {
	    	convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item_work_welfare, null);
			viewHolder = new ViewHolder();
			viewHolder.mViewText = (TextView) convertView.findViewById(R.id.tx_welfare);
			viewHolder.im_score = (ImageView)convertView.findViewById(R.id.im_score);
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		if(item!=null&&item.length()>0)
		{
			viewHolder.im_score.setVisibility(View.VISIBLE);
		}
		else
		{
			viewHolder.im_score.setVisibility(View.INVISIBLE);
		}
		viewHolder.mViewText.setText(item);
		
		return convertView;
	}

	class ViewHolder {
		public TextView mViewText;
		public ImageView im_score;
	}

}
