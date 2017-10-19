package com.aotu.Adapter;

import java.util.List;
import com.aotu.data.InterviewConflictItem;
import com.aotu.data.Interviewscore;
import com.auto.aotuge.R;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InterviewScoreAdapter extends BaseAdapter {
	private List<Interviewscore> mData;
	private Context mContext;
	private LayoutInflater mInflater;
	private InterviewscoreInterface mInterface;

	public interface InterviewscoreInterface {
		public void onImClick(int pos, int type);
	}

	public InterviewScoreAdapter(Context context, List<Interviewscore> data,
			InterviewscoreInterface aInterface) {
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
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Interviewscore itemData = mData.get(position);
		int messageType = getItemViewType(position);
		String pos = String.valueOf(position);

		ViewHolder viewHolder = null;

		if (convertView == null) {

			viewHolder = new ViewHolder();
			convertView = mInflater
					.inflate(R.layout.item_interview_score, null);
			viewHolder.tx_score = (TextView) convertView
					.findViewById(R.id.tx_score);
			viewHolder.im_score1 = (ImageView) convertView
					.findViewById(R.id.im_score1);

			viewHolder.rl_score1 = (RelativeLayout) convertView
					.findViewById(R.id.rl_score1);
			viewHolder.rl_score1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int pos = Integer.valueOf((String) arg0.getTag());
					mInterface.onImClick(pos, 1);
				}
			});

			viewHolder.im_score2 = (ImageView) convertView
					.findViewById(R.id.im_score2);
			viewHolder.rl_score2 = (RelativeLayout) convertView
					.findViewById(R.id.rl_score2);
			viewHolder.rl_score2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int pos = Integer.valueOf((String) arg0.getTag());
					mInterface.onImClick(pos, 2);
				}
			});

			viewHolder.im_score3 = (ImageView) convertView
					.findViewById(R.id.im_score3);
			viewHolder.rl_score3 = (RelativeLayout) convertView
					.findViewById(R.id.rl_score3);
			viewHolder.rl_score3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int pos = Integer.valueOf((String) arg0.getTag());
					mInterface.onImClick(pos, 3);
				}
			});

			viewHolder.im_score4 = (ImageView) convertView
					.findViewById(R.id.im_score4);
			viewHolder.rl_score4 = (RelativeLayout) convertView
					.findViewById(R.id.rl_score4);
			viewHolder.rl_score4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int pos = Integer.valueOf((String) arg0.getTag());
					mInterface.onImClick(pos, 4);
				}
			});

			viewHolder.im_score5 = (ImageView) convertView
					.findViewById(R.id.im_score5);
			viewHolder.rl_score5 = (RelativeLayout) convertView
					.findViewById(R.id.rl_score5);
			viewHolder.rl_score5.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int pos = Integer.valueOf((String) arg0.getTag());
					mInterface.onImClick(pos, 5);
				}
			});

			convertView.setTag(viewHolder);

		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.tx_score.setText(itemData.name);
		
		viewHolder.rl_score1.setTag(pos);
		viewHolder.rl_score2.setTag(pos);
		viewHolder.rl_score3.setTag(pos);
		viewHolder.rl_score4.setTag(pos);
		viewHolder.rl_score5.setTag(pos);
		viewHolder.im_score1.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big2));
		viewHolder.im_score2.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big2));
		viewHolder.im_score3.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big2));
		viewHolder.im_score4.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big2));
		viewHolder.im_score5.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big2));
		if(itemData.pos==1)
		{
			viewHolder.im_score1.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
		}
		else if(itemData.pos==2)
		{
			viewHolder.im_score1.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
			viewHolder.im_score2.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
		}
		
		else if(itemData.pos==3)
		{
			viewHolder.im_score1.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
			viewHolder.im_score2.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
			viewHolder.im_score3.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
		}
		else if(itemData.pos==4)
		{
			viewHolder.im_score1.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
			viewHolder.im_score2.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
			viewHolder.im_score3.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
			viewHolder.im_score4.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
		}
		else if(itemData.pos==5)
		{
			viewHolder.im_score1.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
			viewHolder.im_score2.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
			viewHolder.im_score3.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
			viewHolder.im_score4.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
			viewHolder.im_score5.setBackground(mContext.getResources().getDrawable(R.drawable.xing_big1));
		}
		
		return convertView;

	}

	class ViewHolder {
		public TextView tx_score;

		public RelativeLayout rl_score1;
		public RelativeLayout rl_score2;
		public RelativeLayout rl_score3;
		public RelativeLayout rl_score4;
		public RelativeLayout rl_score5;

		public ImageView im_score1;
		public ImageView im_score2;
		public ImageView im_score3;
		public ImageView im_score4;
		public ImageView im_score5;
	}

}
