package com.aotu.Adapter;

import java.util.List;
import com.aotu.data.CVItem;
import com.auto.aotuge.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CVItemAdapter extends BaseAdapter{

	private List<CVItem> mData;
	private Context mContext;
	private LayoutInflater mInflater;
	CVInterface mCVInterface;
	public interface CVInterface {
		public void onDelete(int pos);
	}
	
	public CVItemAdapter(Context context, List<CVItem> data,CVInterface aCVInterface) {
		this.mContext = context;
		this.mData = data;
		mInflater = LayoutInflater.from(mContext);
		mCVInterface = aCVInterface;
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

		CVItem itemData = mData.get(position);
		
		FristViewHolder fristViewHolder = null;
		
	
		if (convertView == null) {
			fristViewHolder = new FristViewHolder();
			convertView = mInflater.inflate(R.layout.item_my_cv_item, null);
			fristViewHolder.title = (TextView)convertView.findViewById(R.id.tx_cv_name);
			fristViewHolder.delete = (Button)convertView.findViewById(R.id.bn_delete);
			fristViewHolder.delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String pos =(String) arg0.getTag();
					mCVInterface.onDelete(Integer.valueOf(pos));
				}
			});
			convertView.setTag(fristViewHolder);
		}
		fristViewHolder = (FristViewHolder)convertView.getTag();
		fristViewHolder.title.setText(itemData.title);
		fristViewHolder.delete.setTag(String.valueOf(position));
		return convertView;

	}
	
	class FristViewHolder {
		
		private TextView title;
		private Button delete;
	}

}
