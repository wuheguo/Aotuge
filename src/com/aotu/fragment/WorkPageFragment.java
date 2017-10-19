package com.aotu.fragment;

import java.util.ArrayList;
import java.util.List;

import com.aotu.Adapter.IntentionListAdapter;
import com.aotu.Adapter.IntentionListsimpleAdapter;
import com.aotu.Adapter.IntentionListsimpleAdapter.IntentionListsimpleInterface;
import com.aotu.Adapter.WorkPageAdapter;
import com.aotu.Adapter.IntentionListAdapter.IntentionInterface;
import com.aotu.Adapter.WorkPageAdapter.WorkPageInterface;
import com.aotu.activity.CVListActivity;
import com.aotu.activity.CompanyInfoActivity;
import com.aotu.activity.IntentionList;
import com.aotu.activity.LogonActivity;
import com.aotu.activity.PersonCurriculumVitaeActivity;
import com.aotu.activity.SearchWorkActivity;
import com.aotu.activity.SetIntentionActivity;
import com.aotu.activity.WorkPlaceActivity;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseFragment;
import com.aotu.baseview.CustomListView;
import com.aotu.baseview.LoadingView;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.baseview.CustomListView.OnLoadMoreListener;
import com.aotu.baseview.CustomListView.OnRefreshListener;
import com.aotu.data.IntentionItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.WorkItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WorkPageFragment extends Fragment implements OnClickListener {

	Context mContext;
	View mBaseView;
	CustomListView mintention_list_view;
	IntentionListsimpleAdapter intentionAdapter;
	boolean bShowIntention = false;
	RelativeLayout rl_add;
	LinearLayout l_intention_list;
	CustomListView mListView;
	List<WorkItem> mWorkItems = new ArrayList<WorkItem>();
	List<IntentionItem> mIntentionItems = new ArrayList<IntentionItem>();

	RelativeLayout mRl_noInterview;
	TextView tv_no_intention, tx_tab;
	Button bn_no_intention;
	
	
	RelativeLayout rl_no_work;

	WorkPageAdapter mAdapter = null;
	Button mBn_no_intention;
	int mPage = 1;

	RelativeLayout rl_next, rl_tab;

	IntentionItem currentIntentionItem = null;
	int DeletePos = -1;

	final static int SEARCH = 1;

	boolean isFriest = true;
	ArrayList<String> Citys;
	ArrayList<String> Directions;
	String salaryID;
	boolean Viewtype = true;
	String workTypeID;

	Button bn_add;
	String mCV_id = "";
	AlertDialog messageDialog;
	AlertDialog myDialog;

	WorkPageInterface mInterface = new WorkPageInterface() {

		@Override
		public void onCheck(int pos) {
			// TODO Auto-generated method stub
			if (pos > 0)
				bn_add.setVisibility(View.VISIBLE);
			else
				bn_add.setVisibility(View.GONE);
		}

	};

	KTAlertDialogOnClickListener mKTADialongleft = new KTAlertDialogOnClickListener() {

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			sendCV(true);
		}

	};

	KTAlertDialogOnClickListener mKTADialongright = new KTAlertDialogOnClickListener() {

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(mContext,
					PersonCurriculumVitaeActivity.class);
			intent.putExtra("type", 0);
			intent.putExtra("cv_id", "0");
			startActivity(intent);
		}

	};

	IntentionListsimpleInterface aInterface = new IntentionListsimpleInterface() {

		@Override
		public void onDelete(int pos) {
			// TODO Auto-generated method stub
			IntentionItem item = mIntentionItems.get(pos);
			DeletePos = pos;
			sendDeleteIntention(String.valueOf(item.id));
		}

	};
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			rl_tab.setVisibility(View.VISIBLE);

			mListView.onRefreshComplete();
			mListView.onLoadMoreComplete();

			switch (msg.what) {
			case 0: {

				if (mAdapter == null)
					mAdapter = new WorkPageAdapter(mContext, mWorkItems,
							mInterface);
				if (mWorkItems.size() > 0) 
				{
					mListView.setVisibility(View.VISIBLE);
					rl_no_work.setVisibility(View.INVISIBLE);
				}
				else
				{
					rl_no_work.setVisibility(View.VISIBLE);
					mListView.setVisibility(View.INVISIBLE);
				}
				
				mListView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
			
				
				
				

			}
				break;
			case 1: {
				  if (mAdapter == null)
					  mAdapter = new WorkPageAdapter(mContext, mWorkItems,
							  mInterface);
				    mListView.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
					if (mWorkItems.size() > 0) 
					{
						mListView.setVisibility(View.VISIBLE);
						rl_no_work.setVisibility(View.INVISIBLE);
					}
					else
					{
						rl_no_work.setVisibility(View.VISIBLE);
						mListView.setVisibility(View.INVISIBLE);
					}
				}

				break;
			
			case 3: {
				
				if (intentionAdapter == null) 
				{
					intentionAdapter = new IntentionListsimpleAdapter(mContext, mIntentionItems, aInterface);
					mintention_list_view.setAdapter(intentionAdapter);
				}
				intentionAdapter.notifyDataSetChanged();
				rl_add.setVisibility(View.VISIBLE);
				l_intention_list.setVisibility(View.GONE);
				if(mIntentionItems.size()>0)
				{
				    currentIntentionItem = mIntentionItems.get(0);
					if (mWorkItems.size() == 0 && isFriest) {
						isFriest = false;
						getWorkInfo();
					}
					tx_tab.setText(currentIntentionItem.name);
					mRl_noInterview.setVisibility(View.INVISIBLE);
				}
				else
				{
					tx_tab.setText("求职意向");
					mRl_noInterview.setVisibility(View.VISIBLE);
				}
			}
				break;
			case 4:
			{
				intentionAdapter.notifyDataSetChanged();
				mRl_noInterview.setVisibility(View.VISIBLE);
				rl_add.setVisibility(View.VISIBLE);
				l_intention_list.setVisibility(View.GONE);
			}
			break;
			case 6: {
				sendCV(false);
			}
				break;
			case 7: {
				showMessageDialog("提示", "简历投递成功", "确定", null, 1);
			}
				break;
			}

		}
	};

	public WorkPageFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (null != mBaseView) {
			ViewGroup parent = (ViewGroup) mBaseView.getParent();
			if (null != parent) {
				parent.removeView(mBaseView);
				rl_add.setVisibility(View.VISIBLE);
				l_intention_list.setVisibility(View.GONE);
				rl_tab.setVisibility(View.VISIBLE);
			}
		} else {
			mContext = getActivity();
			mBaseView = inflater.inflate(R.layout.fragment_work_page, null);
			initView();
		}
		Log.e("fragmer", "secondFragment onCreateView");
		return mBaseView;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getIntentionList();
	}

	private void initHeaderView() {
		RelativeLayout btnleft = (RelativeLayout) mBaseView
				.findViewById(R.id.back_layout);
		btnleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) mBaseView
				.findViewById(R.id.tv_title_bar_text);
		titleText.setText("找工作");
		RelativeLayout btnright = (RelativeLayout) mBaseView
				.findViewById(R.id.right_layout);
		btnright.setVisibility(View.VISIBLE);
		btnright.setOnClickListener(this);
	}

	private void initView() {
		initHeaderView();
		mintention_list_view = (CustomListView) mBaseView
				.findViewById(R.id.intention_list_view);
		mintention_list_view.setCanRefresh(false);
		mintention_list_view.setCanLoadMore(false);
		mintention_list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				currentIntentionItem = mIntentionItems.get((int) arg3);
				
				tx_tab.setText(currentIntentionItem.name);
				l_intention_list.setVisibility(View.GONE);
				ImageView arraw = (ImageView) mBaseView
						.findViewById(R.id.im_next);
				arraw.setBackground(mContext.getResources().getDrawable(
						R.drawable.arrows_down));
				mPage = 1;
				mWorkItems.clear();
				getWorkInfo();
			}

		});

		rl_tab = (RelativeLayout) mBaseView.findViewById(R.id.rl_tab);
		rl_add = (RelativeLayout) mBaseView.findViewById(R.id.rl_add);
		rl_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, IntentionList.class);
				startActivity(intent);
			}
		});
		l_intention_list = (LinearLayout) mBaseView
				.findViewById(R.id.l_intention_list);

		l_intention_list.setVisibility(View.GONE);
		mListView = (CustomListView) mBaseView.findViewById(R.id.list_view);
		mListView.setCanRefresh(false);
		mListView.setCanLoadMore(false);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, CompanyInfoActivity.class);
				WorkItem item = mWorkItems.get((int) arg3);
				intent.putExtra("cid", item.cid);
				intent.putExtra("id", item.id);

				startActivity(intent);
			}
		});

		
		 mListView.setOnRefreshListener(new OnRefreshListener()
		 {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				onRefreshItems();
			}
			 
		 }); 
		 
		 mListView.setOnLoadListener(new OnLoadMoreListener() {
			
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				onLoadMoreItems();
			}
		});
		
		 

		rl_next = (RelativeLayout) mBaseView.findViewById(R.id.rl_tab);
		rl_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				bShowIntention = !bShowIntention;
				if (bShowIntention) {
					ImageView arraw = (ImageView) mBaseView
							.findViewById(R.id.im_next);
					arraw.setBackground(mContext.getResources().getDrawable(
							R.drawable.arrows_up));
					l_intention_list.setVisibility(View.VISIBLE);
				} else {
					l_intention_list.setVisibility(View.GONE);
					ImageView arraw = (ImageView) mBaseView
							.findViewById(R.id.im_next);
					arraw.setBackground(mContext.getResources().getDrawable(
							R.drawable.arrows_down));
				}

				// TODO Auto-generated method stub
				// Intent intent = new Intent(mContext, IntentionList.class);
				// startActivity(intent);
			}
		});
		
		rl_no_work = (RelativeLayout) mBaseView
				.findViewById(R.id.rl_no_work);
		mRl_noInterview = (RelativeLayout) mBaseView
				.findViewById(R.id.rl_no_intention);
		tv_no_intention = (TextView) mBaseView
				.findViewById(R.id.tv_no_intention);
		tx_tab = (TextView) mBaseView.findViewById(R.id.tx_tab);
		mBn_no_intention = (Button) mBaseView
				.findViewById(R.id.bn_no_intention);
		mBn_no_intention.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						SetIntentionActivity.class);
				startActivity(intent);
			}
		});

		bn_add = (Button) mBaseView.findViewById(R.id.bn_add);
		bn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (AotugeApplication.getInstance().mIsNullAotuInfo) {
					Intent intent = new Intent(mContext, LogonActivity.class);
					startActivity(intent);
				} else {
					if (mWorkItems.size() > 0) {
						Intent intent = new Intent(mContext,
								CVListActivity.class);
						startActivityForResult(intent, 2);
					}
				}
			}
		});
		rl_no_work.setVisibility(View.INVISIBLE);
		mRl_noInterview.setVisibility(View.INVISIBLE);
		bn_add.setVisibility(View.GONE);
	}

	private void onRefreshItems() {
		mPage = 1;
		mWorkItems.clear();
		getWorkInfo();
	}

	private void onLoadMoreItems() {
		mPage++;
		getWorkInfo();
	}

	private void getWorkInfo() {
		getWorkItems();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.right_layout: {
			Intent intent = new Intent(WorkPageFragment.this.getActivity(),
					SearchWorkActivity.class);
			intent.putExtra("type", 0);
			mContext.startActivity(intent);
		}
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 2: {
			if (resultCode == -1) {
				mCV_id = data.getStringExtra("cv_id");
				handler.sendEmptyMessage(6);
			}
		}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void getWorkItems() {
		String url = NetUrlManager.getCompanyList(String
				.valueOf(currentIntentionItem.id))
				+ "&token="
				+ AotugeApplication.getInstance().mAotuInfo.token+"&page="+String.valueOf(mPage);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = null;
			
				info = JosnParser.getWorkList(content, mWorkItems);
				if (info.success) {
					handler.sendEmptyMessage(0);
				} else {
					switch (info.status) {
					case -1:// 搜索失败
					{

					}
						break;
					case -2:// 搜索无结果
					{
						handler.sendEmptyMessage(1);
					}
						break;
					case -11:// 参数错误
					{

					}
						break;
					}
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);

			}
		});

	}

	void getIntentionList() {
		String url = NetUrlManager.getIntentionList() + "?token="
				+ AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getIntention(content,
						mIntentionItems);
				if (info.success)
					  handler.sendEmptyMessage(3);
				else {
					switch (info.status) {
					case -2:
						handler.sendEmptyMessage(4);
						break;
					default:
						break;
					}
				}

			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);

			}
		});

	}

	public void showMessageDialog(String title, String message,
			String rightButtonStr,
			final KTAlertDialogOnClickListener rightListener, int state) {
		if (messageDialog != null)
			messageDialog.dismiss();
		messageDialog = null;
		messageDialog = new AlertDialog.Builder(mContext).create();

		messageDialog.setCancelable(false);
		messageDialog.show();

		messageDialog.getWindow().setContentView(R.layout.message_dialog);
		TextView dialogtitle = (TextView) messageDialog.getWindow()
				.findViewById(R.id.alertdialog_title);
		TextView dialogmessage = (TextView) messageDialog.getWindow()
				.findViewById(R.id.alertdialog_message);
		Button rightButton = (Button) messageDialog.getWindow().findViewById(
				R.id.alertdialog_NegativeButton);
		dialogtitle.setText(title);
		dialogmessage.setText(message);
		rightButton.setText(rightButtonStr);
		rightButton.setTag(String.valueOf(state));
		rightButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rightListener != null)
					rightListener.onClick(messageDialog, v);
				messageDialog.dismiss();
			}
		});

	}

	public void showNotitleDialog(String message, String leftButtonStr,
			String rightButtonStr,
			final KTAlertDialogOnClickListener leftListener,
			final KTAlertDialogOnClickListener rightListener, String index) {
		if (myDialog != null)
			myDialog.dismiss();
		myDialog = null;
		myDialog = new AlertDialog.Builder(mContext).create();
		myDialog.setCancelable(false);
		myDialog.show();
		myDialog.getWindow().setContentView(R.layout.dialog_no_title);
		TextView dialogmessage = (TextView) myDialog.getWindow().findViewById(
				R.id.alertdialog_message);
		Button leftButton = (Button) myDialog.getWindow().findViewById(
				R.id.alertdialog_PositiveButton);
		Button rightButton = (Button) myDialog.getWindow().findViewById(
				R.id.alertdialog_NegativeButton);
		dialogmessage.setText(message);
		leftButton.setText(leftButtonStr);
		leftButton.setTag(index);
		rightButton.setText(rightButtonStr);
		rightButton.setTag(index);
		myDialog.getWindow().findViewById(R.id.alertdialog_PositiveButton)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (leftListener != null)
							leftListener.onClick(myDialog, v);
						myDialog.dismiss();
					}
				});

		rightButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rightListener != null)
					rightListener.onClick(myDialog, v);
				myDialog.dismiss();
			}
		});
	}

	private void sendCV(boolean forbidden) {
		String url = NetUrlManager.getSendCvURL() + "?token="
				+ AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		String jd_id = "";
		for (int i = 0; i < mWorkItems.size(); i++) {
			if (mWorkItems.get(i).isSelect) {
				String tab = "jd_id[" + String.valueOf(i) + "]";
				jd_id = String.valueOf(mWorkItems.get(i).id);
				params.put(tab, jd_id);
			}
		}
		params.put("cv_id", mCV_id);
		if (forbidden)
			params.put("forbidden", "1");
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getCVSend(content);
				if (info.success) {
					handler.sendEmptyMessage(7);
				} else {
					switch (info.status) {
					case -1:
						showMessageDialog("提示", info.info, "确定", null, 0);
						break;
					case -2:
						showNotitleDialog(info.info, "强行投递", "去完善",
								mKTADialongleft, mKTADialongright, "-2");
						break;
					case -3:
						showNotitleDialog(info.info, "强行投递", "去完善",
								mKTADialongleft, mKTADialongright, "-3");
						break;

					}

				}

			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);

			}
		});

	}

	private void sendDeleteIntention(String id) {
		String url = NetUrlManager.getDeleteIntention(id) + "&token="
				+ AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getDeleteintention(content);
				if (info.success)
				{
					mIntentionItems.remove(DeletePos);
					handler.sendEmptyMessage(3);
				}
				else
					showMessageDialog("提示", info.info, "确定", null, 1);

			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showMessageDialog("提示", "服务器忙请稍后再试", "确定", null, 0);
			}
		});

	}

}
