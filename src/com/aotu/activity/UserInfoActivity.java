package com.aotu.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomPopupMenu;
import com.aotu.data.NetReturnInfo;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.aotu.utils.FileManager;
import com.auto.aotuge.R;

/*
 * 个人信息编辑
 */
public class UserInfoActivity extends BaseActivity implements OnClickListener{
	

	static final int SEX = 1;
	static final int SCHOOL = 2;
	static final int SALARY = 3;
	static final String IMAGE_UNSPECIFIED = "image/*";
	static final int NONE = 0;
	static final int PHOTOHRAPH = 4;// 拍照
	static final int PHOTOZOOM = 5; // 缩放
	static final int PHOTORESOULT = 6;// 结果
	private String mImageFilePath = "";
    EditText ed_name,ed_code,ed_major;
    RelativeLayout rl_sex,rl_salary,rl_school,rl_cv;
    TextView tx_sex,tx_salary,tx_school,tx_cv;
    int sex = 1,salary,school,cv;
    Button save;
    ImageView user_head_im;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		
		initHeadView();
		initView();
		getUserinfo();
		//setUserinfo();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(requestCode)
		{
		case PHOTOHRAPH:
		{
			if (resultCode == NONE)
				return;
			// 拍照
			if (requestCode == PHOTOHRAPH) {
				// 设置文件保存路径这里放在跟目录下
				File picture = new File(Environment.getExternalStorageDirectory()
						+ "/temp.jpg");
				startPhotoZoom(Uri.fromFile(picture));
			}

		
			if (data == null)
				return;

		
		}
		break;
		case PHOTORESOULT:
		{
			if (resultCode == NONE)
				return;
			if (data == null)
				return;

			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				String imageFilename = FileManager.getDate(2)
						+ FileManager.getRandomFileName() + ".jpg";
				File poho = new File(FileManager.getInstance()
						.getDownloadImagePath() + imageFilename);
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(poho);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				photo.compress(Bitmap.CompressFormat.JPEG, 100, fos);

				File picture = new File(
						Environment.getExternalStorageDirectory() + "/temp.jpg");
				if (picture.exists()) {
					picture.delete();
				}

				File olderCar = new File(mImageFilePath);
				if (olderCar.exists()) {
					olderCar.delete();
				}
				mImageFilePath = FileManager.getInstance()
						.getDownloadImagePath() + imageFilename;
				BitmapDrawable drawable = new BitmapDrawable(photo);
				user_head_im.setBackgroundDrawable(drawable);
				sendUserHead(mImageFilePath);
			}
		
		}
		break;
		case SEX:
		{
			if(resultCode == RESULT_OK)
			{
				sex = data.getIntExtra("typeid", -1);
				String info = data.getStringExtra("typename");
				tx_sex.setText(info);
			}
		}
		break;
		case SCHOOL:
		{
			if(resultCode == RESULT_OK)
			{
				school = data.getIntExtra("code", -1);
				String info = data.getStringExtra("name");
				tx_school.setText(info);
			}
		}
		break;
		case SALARY:
		{
			if(resultCode == RESULT_OK)
			{
				salary = data.getIntExtra("code", -1);
				String info = data.getStringExtra("name");
				tx_salary.setText(info);
			}
		}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onBack(View v) {
		// TODO Auto-generated method stub
		Intent resultIntent = new Intent();
		UserInfoActivity.this.setResult(RESULT_CANCELED, resultIntent);
	
		super.onBack(v);
	}
	private void initView()
	{
		mImageFilePath = FileManager.getInstance().getDownloadImagePath()
				+ "head.jpg";
		 ed_name = (EditText)findViewById(R.id.ed_name);
		 ed_code = (EditText)findViewById(R.id.ed_code);
		 ed_major = (EditText)findViewById(R.id.ed_major);
		 rl_sex = (RelativeLayout)findViewById(R.id.rl_sex);
		 rl_sex.setOnClickListener(this);
		
		 
		 rl_salary = (RelativeLayout)findViewById(R.id.rl_salary);
		 rl_salary.setOnClickListener(this);
		 
		 rl_school = (RelativeLayout)findViewById(R.id.rl_school);
		 rl_school.setOnClickListener(this);
		 
//		 rl_cv = (RelativeLayout)findViewById(R.id.rl_cv);
//		 rl_cv.setOnClickListener(this);
		 
		 save = (Button)findViewById(R.id.bn_save);
		 save.setOnClickListener(this);
		 
		 tx_sex = (TextView)findViewById(R.id.tx_sex);
		 tx_salary= (TextView)findViewById(R.id.tx_salary);
		 tx_school= (TextView)findViewById(R.id.tx_school);
//		 tx_cv= (TextView)findViewById(R.id.tx_cv);	 
		 user_head_im = (ImageView)findViewById(R.id.user_head_im);
		 user_head_im.setOnClickListener(this);
	}
	
	private void initHeadView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		RelativeLayout menuleft = (RelativeLayout) findViewById(R.id.left_menu_layout);
		menuleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText("个人信息");
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.rl_sex:
		{
			Intent intent = new Intent(UserInfoActivity.this, CustomPopupMenu.class);
			intent.putExtra("type", 0);
			startActivityForResult(intent, SEX);
		}
		break;
		case R.id.rl_salary:
			{
				Intent intent = new Intent(UserInfoActivity.this, WorkPlaceActivity.class);
				intent.putExtra("type", 1);
				startActivityForResult(intent,SALARY);
			}
			break;
		
		
		case R.id.rl_school:
		{
			Intent intent = new Intent(UserInfoActivity.this, SchoolActivity.class);
			startActivityForResult(intent, SCHOOL);
		}
		break;
		case R.id.bn_save:
		{
			sendInfoToService();
		}
		break;
		case R.id.user_head_im:
		{
			onPhoto();
		}
		break;
		}
	}
	
	void onPhoto()
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				Environment.getExternalStorageDirectory(), "temp.jpg")));
		startActivityForResult(intent, PHOTOHRAPH);
	}
	
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		//  aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 13);
		intent.putExtra("aspectY", 10);
		//  outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 292);
		intent.putExtra("outputY", 226);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
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
					NetReturnInfo info = JosnParser.getSignIn(content);
					if(info.status==0)
					{
						
					}
					else
					{
						showMessageDialog("提示", info.info, "确定", null,0);
					}
				}

				@Override
				public void onFailure(Throwable error, String content) {
					super.onFailure(error, content);
					showMessageDialog("提示", "服务器忙请稍后再试", "确定", null,0);
				}
			});
		
	}
	
	private void sendInfoToService()
	{
		String url = NetUrlManager.getUserInfoSetUrl()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("platform", "android");
		RequestParams params = new RequestParams();
		params.put("name", ed_name.getText().toString());
		params.put("major", ed_major.getText().toString());
		params.put("gender", String.valueOf(sex));
		params.put("idcard", ed_code.getText().toString());
		params.put("native", String.valueOf(salary));
		params.put("school", String.valueOf(school));	
	
		client.post(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getSetUserInfo(content);
				if(info.status==0)
				{
					Intent resultIntent = new Intent();
					UserInfoActivity.this.setResult(RESULT_OK, resultIntent);
					finish();
					//showMessageDialog("提示", "修改成功", "确定", null,0);
				}
				else
				{
					showMessageDialog("提示", info.info, "确定", null,0);
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showMessageDialog("提示", "服务器忙请稍后再试", "确定", null,0);
			}
		});
	}
	
	public void setUserinfo()
	{
		 ed_code.setText(AotugeApplication.getInstance().mAotuInfo.idcard);
		 ed_major.setText(AotugeApplication.getInstance().mAotuInfo.major);
		 ed_name.setText(AotugeApplication.getInstance().mAotuInfo.name);
		 tx_sex.setText(AotugeApplication.getInstance().mAotuInfo.gender_text);
		 tx_salary.setText(AotugeApplication.getInstance().mAotuInfo.native_text);
		 tx_school.setText(AotugeApplication.getInstance().mAotuInfo.school_text);
		 
		 sex = Integer.valueOf(AotugeApplication.getInstance().mAotuInfo.gender);
		 school = Integer.valueOf(AotugeApplication.getInstance().mAotuInfo.school);
		 salary = Integer.valueOf(AotugeApplication.getInstance().mAotuInfo.usernative);

	}

	
	void getUserinfo()
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
				{
					setUserinfo();
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				
			}
		});
	}

}
