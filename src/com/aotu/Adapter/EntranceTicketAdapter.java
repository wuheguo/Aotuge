package com.aotu.Adapter;

import java.util.List;
import com.aotu.data.CVItem;
import com.aotu.data.EntranceTicket;
import com.aotu.data.ShuangXuanItem;
import com.auto.aotuge.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class EntranceTicketAdapter extends BaseAdapter{

	private List<EntranceTicket> mData;
	private Context mContext;
	private LayoutInflater mInflater;
	
	
	public EntranceTicketAdapter(Context context, List<EntranceTicket> data) {
		this.mContext = context;
		this.mData = data;
		mInflater = LayoutInflater.from(mContext);
		
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
		return 1;
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		EntranceTicket itemData = mData.get(position);
		
		FristViewHolder fristViewHolder = null;
		
	
		if (convertView == null) {
			fristViewHolder = new FristViewHolder();
			convertView = mInflater.inflate(R.layout.item_suang_xuan, null);
			fristViewHolder.tx_info1 = (TextView)convertView.findViewById(R.id.tx_info1);
			fristViewHolder.tx_info2 = (TextView)convertView.findViewById(R.id.tx_info2);
			fristViewHolder.tx_info3 = (TextView)convertView.findViewById(R.id.tx_info3);
			fristViewHolder.tx_info4 = (TextView)convertView.findViewById(R.id.tx_info4);
			
			convertView.setTag(fristViewHolder);
		}
		fristViewHolder = (FristViewHolder)convertView.getTag();
		fristViewHolder.tx_info1.setText(itemData.name);
		fristViewHolder.tx_info2.setText("位置："+itemData.province+" "+itemData.city);
		fristViewHolder.tx_info3.setText("时间："+itemData.time);
		fristViewHolder.tx_info4.setText("地址："+itemData.address);
		return convertView;

	}
	
	class FristViewHolder {
		
		private TextView tx_info1;
		private TextView tx_info2;
		private TextView tx_info3;
		private TextView tx_info4;
	}

}
