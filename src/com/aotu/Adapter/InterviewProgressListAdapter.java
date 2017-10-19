package com.aotu.Adapter;

import java.util.List;
import com.aotu.data.CVItem;
import com.aotu.data.InterviewProgressItem;
import com.auto.aotuge.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InterviewProgressListAdapter extends BaseAdapter{

	private List<InterviewProgressItem> mData;
	private Context mContext;
	private LayoutInflater mInflater;

	
	public InterviewProgressListAdapter(Context context, List<InterviewProgressItem> data) {
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

		InterviewProgressItem itemData = mData.get(position);
		
		FristViewHolder fristViewHolder = null;
		
	
		if (convertView == null) {
			fristViewHolder = new FristViewHolder();
			convertView = mInflater.inflate(R.layout.item_pr, null);
			fristViewHolder.tx_interview_tab1 = (TextView)convertView.findViewById(R.id.tx_interview_tab1);
			fristViewHolder.tx_interview_tab2 = (TextView)convertView.findViewById(R.id.tx_interview_tab2);
			fristViewHolder.im_next = (ImageView)convertView.findViewById(R.id.im_circle);
			convertView.setTag(fristViewHolder);
		}
		fristViewHolder = (FristViewHolder)convertView.getTag();
		if(itemData.time.length()==0)
		{
			fristViewHolder.tx_interview_tab1.setTextColor(mContext.getResources().getColor(R.color.hit_color));
			fristViewHolder.im_next.setBackground(mContext.getResources().getDrawable(R.drawable.interview_circle_2));
		}
		else
		{
			fristViewHolder.im_next.setBackground(mContext.getResources().getDrawable(R.drawable.interview_circle_1));
			fristViewHolder.tx_interview_tab1.setTextColor(mContext.getResources().getColor(R.color.font_text_color));
		}
		fristViewHolder.tx_interview_tab1.setText(itemData.name);
		fristViewHolder.tx_interview_tab2.setText(itemData.time);
		return convertView;

	}
	
	class FristViewHolder {
		
		private TextView tx_interview_tab1;
		private TextView tx_interview_tab2;
		private ImageView im_next;
	}

}
