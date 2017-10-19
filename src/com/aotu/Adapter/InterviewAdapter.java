package com.aotu.Adapter;


import java.util.List;


import com.aotu.data.InterviewTimeItem;

import com.auto.aotuge.R;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InterviewAdapter extends BaseAdapter{
//http://test.honglei.net/admin/?tmp=interfaces
	private List<InterviewTimeItem> mData;
	private Context mContext;
	private LayoutInflater mInflater;
    private InterviewInterface mInterface;
	public interface InterviewInterface {
		public void onBnClick(int pos);
		public void onInfoClick(int pos);
	}
	public InterviewAdapter(Context context, List<InterviewTimeItem> data,InterviewInterface aInterface) {
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
		InterviewTimeItem itemData = mData.get(position);
		return itemData.type;
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 4;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		InterviewTimeItem itemData = mData.get(position);
		int messageType = getItemViewType(position);
	    String pos = String.valueOf(position);
		ViewHolder   viewHolder = null;
	
		if (convertView == null) {
			switch(messageType)
			{
				case 0:
				{
					viewHolder = new ViewHolder();
					convertView = mInflater.inflate(R.layout.item_interview_left, null);
					viewHolder.tx_company = (TextView)convertView.findViewById(R.id.tx_company);
					viewHolder.tx_description = (TextView)convertView.findViewById(R.id.tx_description);
					viewHolder.tx_info = (TextView)convertView.findViewById(R.id.tx_info);
					viewHolder.bn_accept = (Button)convertView.findViewById(R.id.bn_accept);
					viewHolder.bn_accept.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							int pos = Integer.valueOf((String)arg0.getTag());
							mInterface.onBnClick(pos);
						}
					});
					viewHolder.r_info = (RelativeLayout)convertView.findViewById(R.id.rl_info);
                    viewHolder.r_info.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							int pos = Integer.valueOf((String)arg0.getTag());
							mInterface.onInfoClick(pos);
						}
					});
					
					convertView.setTag(viewHolder);
				}
				break;
				case 1:
				{
					viewHolder = new ViewHolder();
					convertView = mInflater.inflate(R.layout.item_interview_middle, null);
					viewHolder.tx_company = (TextView)convertView.findViewById(R.id.tx_company);
					viewHolder.tx_description = (TextView)convertView.findViewById(R.id.tx_description);
					viewHolder.tx_info = (TextView)convertView.findViewById(R.id.tx_info);
					viewHolder.tx_tip = (TextView)convertView.findViewById(R.id.tx_tip);
					viewHolder.bn_accept = (Button)convertView.findViewById(R.id.bn_accept);
                    viewHolder.bn_accept.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							int pos = Integer.valueOf((String)arg0.getTag());
							mInterface.onBnClick(pos);
						}
					});
                	viewHolder.r_info = (RelativeLayout)convertView.findViewById(R.id.rl_info);
                    viewHolder.r_info.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							int pos = Integer.valueOf((String)arg0.getTag());
							mInterface.onInfoClick(pos);
						}
					});
					convertView.setTag(viewHolder);
					
				}
				break;
				case 2:
				{
					viewHolder = new ViewHolder();
					convertView = mInflater.inflate(R.layout.item_interview_right, null);
					viewHolder.tx_company = (TextView)convertView.findViewById(R.id.tx_company);
					viewHolder.tx_description = (TextView)convertView.findViewById(R.id.tx_description);
					viewHolder.tx_info = (TextView)convertView.findViewById(R.id.tx_info);
					viewHolder.tx_tip = (TextView)convertView.findViewById(R.id.tx_tip);
					viewHolder.bn_accept = (Button)convertView.findViewById(R.id.bn_accept);
					viewHolder.bn_accept.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								int pos = Integer.valueOf((String)arg0.getTag());
								mInterface.onBnClick(pos);
							}
						});
					viewHolder.r_info = (RelativeLayout)convertView.findViewById(R.id.rl_info);
                    viewHolder.r_info.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							int pos = Integer.valueOf((String)arg0.getTag());
							mInterface.onInfoClick(pos);
						}
					});
					convertView.setTag(viewHolder);
					
				}
				break;
				case 3:
				{
					viewHolder = new ViewHolder();
					convertView = mInflater.inflate(R.layout.item_interview_right, null);
					viewHolder.tx_company = (TextView)convertView.findViewById(R.id.tx_company);
					viewHolder.tx_description = (TextView)convertView.findViewById(R.id.tx_description);
					viewHolder.tx_info = (TextView)convertView.findViewById(R.id.tx_info);
					viewHolder.tx_tip = (TextView)convertView.findViewById(R.id.tx_tip);
					viewHolder.bn_accept = (Button)convertView.findViewById(R.id.bn_accept);
					viewHolder.bn_accept.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								int pos = Integer.valueOf((String)arg0.getTag());
								mInterface.onBnClick(pos);
							}
						});
					viewHolder.r_info = (RelativeLayout)convertView.findViewById(R.id.rl_info);
                    viewHolder.r_info.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							int pos = Integer.valueOf((String)arg0.getTag());
							mInterface.onInfoClick(pos);
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
				viewHolder = (ViewHolder) convertView.getTag();	
				viewHolder.tx_company.setText(itemData.company);
				String title = "应聘岗位:"+itemData.title;
				viewHolder.tx_info.setText(title);
				String timer = "面试时间:"+itemData.interview_time;
				viewHolder.tx_description.setText(timer);
				//viewHolder.tx_tip.setText(itemData.tips);
				viewHolder.bn_accept.setTag(pos);
				viewHolder.r_info.setTag(pos);
			}
			break;
			case 1:
			{
				viewHolder = (ViewHolder) convertView.getTag();	
				viewHolder.tx_company.setText(itemData.company);
				String title = "应聘岗位:"+itemData.title;
				viewHolder.tx_info.setText(title);
				String timer = "面试时间:"+itemData.interview_time;
				
				viewHolder.tx_description.setText(timer);
				//viewHolder.tx_tip.setText(itemData.tips);	
				viewHolder.bn_accept.setTag(pos);
				viewHolder.r_info.setTag(pos);
				
			}
			break;
			case 2:
			{
				
				viewHolder = (ViewHolder) convertView.getTag();	
				viewHolder.tx_company.setText(itemData.company);
				String title = "应聘岗位:"+itemData.title;
				viewHolder.tx_info.setText(title);
				String timer = "面试时间:"+itemData.interview_time;
				viewHolder.bn_accept.setVisibility(View.VISIBLE);
				viewHolder.tx_description.setText(timer);
				
				
				viewHolder.tx_tip.setText(itemData.tips);
				if(itemData.status == 20)
				{
					viewHolder.bn_accept.setVisibility(View.VISIBLE);
					viewHolder.bn_accept.setText("接受邀请");
				}
				else if(itemData.status == 30)
				{
					viewHolder.bn_accept.setVisibility(View.VISIBLE);
					viewHolder.bn_accept.setText("取消面试");
				}
				else if(itemData.status == 150)
				{
					viewHolder.bn_accept.setVisibility(View.VISIBLE);
					viewHolder.bn_accept.setText("投诉");
				}
				else
				{
					viewHolder.bn_accept.setVisibility(View.INVISIBLE);
				}
				
				viewHolder.bn_accept.setTag(pos);
				viewHolder.r_info.setTag(pos);
				
			}
			break;
			case 3:
			{
				
				viewHolder = (ViewHolder) convertView.getTag();	
				viewHolder.tx_company.setText(itemData.company);
				String title = "应聘岗位:"+itemData.title;
				viewHolder.tx_info.setText(title);
				String timer = "投递时间:"+itemData.sent_time;
				
				viewHolder.tx_description.setText(timer);
				
				viewHolder.bn_accept.setVisibility(View.VISIBLE);
				viewHolder.tx_tip.setText(itemData.tips);
				if(itemData.status == 20)
				{
					viewHolder.bn_accept.setVisibility(View.VISIBLE);
					viewHolder.bn_accept.setText("接受邀请");
				}
				else if(itemData.status == 30)
				{
					viewHolder.bn_accept.setVisibility(View.VISIBLE);
					viewHolder.bn_accept.setText("取消面试");
				}
				else if(itemData.status == 150)
				{
					viewHolder.bn_accept.setVisibility(View.VISIBLE);
					viewHolder.bn_accept.setText("投诉");
				}
				else
				{
					viewHolder.bn_accept.setVisibility(View.INVISIBLE);
				}
				viewHolder.bn_accept.setTag(pos);
				viewHolder.r_info.setTag(pos);
				
			}
			break;
		}
		
	   
	    
		return convertView;

	}
	
	class ViewHolder {
	   public TextView tx_company;
	   public TextView tx_info;
	   public TextView tx_description;
	   public TextView tx_tip;
	   public Button bn_accept;
	   public RelativeLayout r_info;
	}
	
	

}
