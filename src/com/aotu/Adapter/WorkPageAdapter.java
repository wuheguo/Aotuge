package com.aotu.Adapter;

import java.util.ArrayList;
import java.util.List;

import com.aotu.Adapter.IntentionListAdapter.IntentionInterface;
import com.aotu.baseview.CustomGridView;
import com.aotu.data.WorkItem;
import com.auto.aotuge.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class WorkPageAdapter extends BaseAdapter{

	List<WorkItem> mData;
	Context mContext;
	LayoutInflater mInflater;
	static int TYPE_COUNT = 1;
    int selectNumber = 0;
	private WorkPageInterface mInterface;

	public interface WorkPageInterface {
		public void onCheck(int pos);
	}
	public WorkPageAdapter(Context context, List<WorkItem> data,WorkPageInterface aInterface) {
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
		return mData.get(position).type;
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return TYPE_COUNT;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		WorkItem itemData = mData.get(position);
		String pos = String.valueOf(position);
		int messageType = getItemViewType(position);
		FristViewHolder fristViewHolder = null;
		
	
		if (convertView == null) {
			fristViewHolder = new FristViewHolder();
			convertView = mInflater.inflate(R.layout.item_work_item, null);
			fristViewHolder.l_im_check = (RelativeLayout)convertView.findViewById(R.id.l_im_check);
			fristViewHolder.l_im_check.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String pos = (String)arg0.getTag();
					int positem = Integer.valueOf(pos);
					mData.get(positem).isSelect = !mData.get(positem).isSelect;
					if(mData.get(positem).isSelect)
						selectNumber++;
					else
						selectNumber--;
					mInterface.onCheck(selectNumber);
					notifyDataSetChanged();
				}
			});
			fristViewHolder.im_check = (ImageView)convertView.findViewById(R.id.im_check);
			fristViewHolder.tx_company = (TextView)convertView.findViewById(R.id.tx_company);
			fristViewHolder.tx_salary = (TextView)convertView.findViewById(R.id.tx_salary);
		
			fristViewHolder.tx_company_property = (TextView)convertView.findViewById(R.id.tx_company_property);
			fristViewHolder.tx_graduate = (TextView)convertView.findViewById(R.id.tx_graduate);
			
			fristViewHolder.tx_location = (TextView)convertView.findViewById(R.id.tx_location);
			fristViewHolder.tx_work_type = (TextView)convertView.findViewById(R.id.tx_work_type);
			fristViewHolder.tx_update = (TextView)convertView.findViewById(R.id.tx_update);
		
			convertView.setTag(fristViewHolder);
		}

	    fristViewHolder =(FristViewHolder) convertView.getTag();
	    fristViewHolder.tx_company.setText(itemData.title);
		fristViewHolder.tx_salary.setText(itemData.salary_text) ;
		fristViewHolder.tx_company_property.setText(itemData.company);
		fristViewHolder.tx_graduate.setText(itemData.graduate_text);
		fristViewHolder.tx_location.setText(itemData.location.get(0).name);
		fristViewHolder.tx_work_type.setText(itemData.work_type_text);
		fristViewHolder.tx_update.setText(itemData.update);
		fristViewHolder.l_im_check.setTag(pos);
		if(itemData.isSelect)
		{
			fristViewHolder.im_check.setBackground(mContext.getResources().getDrawable(R.drawable.btn_bg_checked));
		}
		else
			fristViewHolder.im_check.setBackground(mContext.getResources().getDrawable(R.drawable.btn_bg_unchecked));
		return convertView;

	}
	
	class FristViewHolder {
		public RelativeLayout l_im_check;
		public ImageView im_check;
		
		public TextView tx_company;
		public TextView tx_salary;
		
		public TextView tx_company_property;
		public TextView tx_graduate;
		public TextView tx_location;
		public TextView tx_work_type;
		
		public TextView tx_update;
	}

}
