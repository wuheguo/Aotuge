package com.aotu.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.aotu.Adapter.IntentionListAdapter;
import com.aotu.Adapter.WorkPageAdapter;
import com.aotu.activity.AboutActivity;
import com.aotu.activity.CVListActivity;
import com.aotu.activity.ChangePasswordGetCodeActivity;
import com.aotu.activity.EntranceTicketActivity;
import com.aotu.activity.H5Activity;
import com.aotu.activity.MainActivityFragment;
import com.aotu.activity.ReconciliationsActivity;

import com.aotu.activity.TiXianActivity;
import com.aotu.activity.UserInfoActivity;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.data.NetReturnInfo;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.aotu.uploadphoto.PictureConstants;
import com.aotu.uploadphoto.UploadPhotoAgentActivity;
import com.aotu.utils.DisplayUtil;
import com.aotu.utils.FileManager;
import com.auto.aotuge.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyPageFragment extends Fragment implements OnClickListener {

	Context mContext;
	View mBaseView;
	ImageView user_head_im;
	TextView tx_name,tx_money;
	Button bn_tixian,bn_exit;
	RelativeLayout rl_myinfo1,rl_myinfo2,rl_myinfo3,rl_myinfo4,rl_myinfo5,rl_myinfo6,rl_myinfo7;
	
	DisplayImageOptions options;
	String imagepath="";
	
	 static final int Change_Password = 1;
	 static final int UserInfoActivity = 2;
	 boolean isGotoLogn = false;
	KTAlertDialogOnClickListener mKTADialongleft = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			
		/*	Intent intent = new Intent(mContext,LogonActivity.class);
			startActivity(intent);
			MyPageFragment.this.getActivity().finish();*/
			//android.os.Process.killProcess(android.os.Process.myPid());    //获取PID 
			//System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
		}
		
	};
	
	KTAlertDialogOnClickListener mKTADialongright = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			String sState = (String)button.getTag();
			int state = Integer.valueOf(sState);
		    if(state==14)
		    {
					Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+AotugeApplication.getInstance().mBaseDataItem.hotline));  
		            startActivity(intent);
		    }
		    else
		    {
			AotugeApplication.getInstance().mIsNullAotuInfo = true;
			AotugeApplication.getInstance().deleteUserInfo();
			MainActivityFragment mainActivityFragment = (MainActivityFragment)getActivity();
			mainActivityFragment.onGotoHomeFragment();
		    }
		}
		
	};
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			
		
			switch(msg.what)
			{
			case 0:{
				setUserinfo();
			}
			break;
			case 1:
			{
				  user_head_im.setImageBitmap(DisplayUtil.getRoundedCornerBitmap(DisplayUtil.getLoacalBitmap(imagepath)));
			}
		    break;
			}
			
		}
	};
	

	public MyPageFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (null != mBaseView) {
			ViewGroup parent = (ViewGroup) mBaseView.getParent();
			if (null != parent) {
				parent.removeView(mBaseView);
			}
		} else {
			mContext = getActivity();
			mBaseView = inflater.inflate(R.layout.fragment_my_page, null);
			initView();
			initHeaderView();
			isGotoLogn = false;
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
		if(!isGotoLogn)
		getUserinfo();
	}

	private void initView() {
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.user1)
		.showImageForEmptyUri(R.drawable.user1)
		.showImageOnFail(R.drawable.user1).cacheInMemory(true)
		.cacheOnDisk(true)

		.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
		user_head_im = (ImageView)mBaseView.findViewById(R.id.user_head_im);
		user_head_im.setOnClickListener(this);
		tx_name = (TextView)mBaseView.findViewById(R.id.tx_name);
		tx_money = (TextView)mBaseView.findViewById(R.id.tx_money);
		bn_tixian = (Button)mBaseView.findViewById(R.id.bn_tixian);
		bn_tixian.setOnClickListener(this);
		
		rl_myinfo1 = (RelativeLayout)mBaseView.findViewById(R.id.rl_myinfo1);
		rl_myinfo1.setOnClickListener(this);
		rl_myinfo2= (RelativeLayout)mBaseView.findViewById(R.id.rl_myinfo2);
		rl_myinfo2.setOnClickListener(this);
		rl_myinfo3= (RelativeLayout)mBaseView.findViewById(R.id.rl_myinfo3);
		rl_myinfo3.setOnClickListener(this);
		rl_myinfo4= (RelativeLayout)mBaseView.findViewById(R.id.rl_myinfo4);
		rl_myinfo4.setOnClickListener(this);
		rl_myinfo5= (RelativeLayout)mBaseView.findViewById(R.id.rl_myinfo5);
		rl_myinfo5.setOnClickListener(this);
		rl_myinfo6= (RelativeLayout)mBaseView.findViewById(R.id.rl_myinfo6);
		rl_myinfo6.setOnClickListener(this);
		
		rl_myinfo7= (RelativeLayout)mBaseView.findViewById(R.id.rl_myinfo7);
		rl_myinfo7.setOnClickListener(this);
		bn_exit= (Button)mBaseView.findViewById(R.id.bn_exit);
		bn_exit.setOnClickListener(this);
	}

	private void initHeaderView() {
		RelativeLayout btnleft = (RelativeLayout) mBaseView.findViewById(R.id.back_layout);
		btnleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) mBaseView.findViewById(R.id.tv_title_bar_text);
		titleText.setText("我的");
		RelativeLayout btnright = (RelativeLayout) mBaseView.findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
		btnright.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.user_head_im:
		{
			//Intent intent = new Intent(mContext,UserInfoActivity.class);
			//startActivity(intent);
			showDialog();
		}
		break;
		case R.id.bn_tixian:
		{
			Intent intent = new Intent(mContext,TiXianActivity.class);
			intent.putExtra("balance",String.valueOf(AotugeApplication.getInstance().mAotuInfo.balance));
			startActivity(intent);
		}
		break;
		case R.id.rl_myinfo1:
		{
//			Intent intent = new Intent(mContext,MyMessageActivity.class);
//			startActivity(intent);
			Intent intent = new Intent(mContext,UserInfoActivity.class);
			
			startActivityForResult(intent,UserInfoActivity);
		}
		break;
		case R.id.rl_myinfo2:
		{
			Intent intent = new Intent(mContext,CVListActivity.class);
			intent.putExtra("type", 1);
			startActivity(intent);
		}
		break;
		case R.id.rl_myinfo3:
		{
			Intent intent = new Intent(mContext,ReconciliationsActivity.class);
			startActivity(intent);
			
		}
		break;
		case R.id.rl_myinfo4:
		{
			Intent intent = new Intent(mContext,ChangePasswordGetCodeActivity.class);
			startActivityForResult(intent, Change_Password);
			
		}
		break;
		case R.id.rl_myinfo5:
		{
			Intent intent = new Intent(mContext, H5Activity.class);
			intent.putExtra("url", AotugeApplication.getInstance().mBaseDataItem.about);
			startActivity(intent);
		}
		break;
		case R.id.rl_myinfo6:
		{
			/*Intent intent = new Intent(mContext,AboutActivity.class);
			startActivity(intent);*/
			/*Intent intent = new Intent(mContext, H5Activity.class);
			intent.putExtra("url", AotugeApplication.getInstance().mBaseDataItem.feedback);
			startActivity(intent);*/
			showNotitleDialog("是否联系客服:"+AotugeApplication.getInstance().mBaseDataItem.hotline, "取消", "拨打", mKTADialongleft, mKTADialongright, "14");
			
		}
		break;
		case R.id.bn_exit:
		{
			showNotitleDialog("你是否确定要退出","取消","确定",mKTADialongleft,mKTADialongright,"12");
			
			
		}
		break;
		case R.id.rl_myinfo7:
		{
			Intent intent = new Intent(mContext,EntranceTicketActivity.class);
			startActivity(intent);
		}
		break;
		}
	}
	
	void getUserinfo()
	{
		if(AotugeApplication.getInstance().mAotuInfo.token!=null)
		{
		String url = NetUrlManager.getUserInfoSetUrl()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getUserinfo(content);
				if(info.success)
					handler.sendEmptyMessage(0);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				
			}
		});
		}
	}
	
	private void setUserinfo()
	{
		
		tx_name.setText(AotugeApplication.getInstance().mAotuInfo.name);
		tx_money.setText(String.valueOf(AotugeApplication.getInstance().mAotuInfo.balance+"元"));
		if (AotugeApplication.getInstance().mAotuInfo.face != null && AotugeApplication.getInstance().mAotuInfo.face.length() > 0) {
			
			
			user_head_im.setScaleType(ScaleType.CENTER_CROP);
			ImageLoader.getInstance().displayImage(AotugeApplication.getInstance().mAotuInfo.face,
					user_head_im,options);
		}
	}
	
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	AlertDialog messageDialog;
	AlertDialog myDialog;
	File tempFile = new File(Environment.getExternalStorageDirectory(),getPhotoFileName());
	String mImageFilePath = FileManager.getInstance().getDownloadImagePath()
			+ "head.jpg";
	//提示对话框方法
	private void showDialog() {
	new AlertDialog.Builder(mContext)
	.setTitle("头像设置")
	.setPositiveButton("拍照", new DialogInterface.OnClickListener() {

	@Override
	public void onClick(DialogInterface dialog, int which) {
	// TODO Auto-generated method stub
	dialog.dismiss();
	
	 UploadPhotoAgentActivity.Entrance.modifyHeadFromCamera(MyPageFragment.this.getActivity());
	}
	})
	.setNegativeButton("相册", new DialogInterface.OnClickListener() {

	@Override
	public void onClick(DialogInterface dialog, int which) {
	// TODO Auto-generated method stub
	dialog.dismiss();
    UploadPhotoAgentActivity.Entrance.modifyHeadFromPhone(MyPageFragment.this.getActivity());
   
	}
	}).show();
	}

	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case Change_Password:
		{
			if(resultCode==-1)
			{
				isGotoLogn = true;
				MainActivityFragment mainActivityFragment = (MainActivityFragment)getActivity();
				mainActivityFragment.onLogonView();
			}
			else
			{
				isGotoLogn = false;
			}
		}
		break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	void sendUserHead(String mSaveFile)
	{
		File file = new File(mSaveFile);
		RequestParams params = null;
		params = new RequestParams();
		try {
			params.put("image", file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String url = NetUrlManager.getHeadImage()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;;
		AsyncHttpClient client = new AsyncHttpClient();
			
			client.post(url,params,new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String content) {
					// TODO Auto-generated method stub
					super.onSuccess(content);
					NetReturnInfo info = JosnParser.getUserHead(content);
					if(info.status==0)
					{
						showMessageDialog("提示", "修改成功", "确定", null,0);
						handler.sendEmptyMessage(1);
					}
					else
					{
						showMessageDialog("提示", info.info, "确定", null,0);
					}
				}

				@Override
				public void onFailure(Throwable error, String content) {
					super.onFailure(error, content);
					//showMessageDialog("提示", "服务器忙请稍后再试", "确定", null,0);
				}
			});
		
	}

	// 使用系统当前日期加以调整作为照片的名称
	private String getPhotoFileName() {
	Date date = new Date(System.currentTimeMillis());
	SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
	return dateFormat.format(date) + ".jpg";
	}
	
	public void showMessageDialog(String title, String message,String rightButtonStr,
			final KTAlertDialogOnClickListener rightListener,int state) {
		if (messageDialog != null)
			messageDialog.dismiss();
		messageDialog = null;
		messageDialog = new AlertDialog.Builder(mContext).create();
		
		messageDialog.setCancelable(false);
		messageDialog.show();
		
		messageDialog.getWindow().setContentView(R.layout.message_dialog);
		TextView dialogtitle = (TextView) messageDialog.getWindow().findViewById(
				R.id.alertdialog_title);
		TextView dialogmessage = (TextView) messageDialog.getWindow().findViewById(
				R.id.alertdialog_message);
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
	
	public void showNotitleDialog(String message,
			String leftButtonStr, String rightButtonStr,
			final KTAlertDialogOnClickListener leftListener,
			final KTAlertDialogOnClickListener rightListener,String index) {
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
	
	public void setHeadData(String path)
	{
		imagepath = path;
	
	  sendUserHead(path);
	}

}
