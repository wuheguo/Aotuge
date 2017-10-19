package com.aotu.activity;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.PersonAwardsItem;
import com.aotu.data.PersonInternshipItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;
/*
 * 获奖经历
 */
public class PersonAwardsActivity extends BaseActivity implements OnClickListener{
	
	PersonAwardsItem mPersonAwardsItem = new PersonAwardsItem();
	EditText mEt_company,mEt_workinternship_info;
	TextView mEt_title;
	Button mbn_delete,mbn_save;
	
	int mtype;
	int mpos;
	
	int mDateType;
	String cvid ="";
	Calendar calendar = Calendar.getInstance();
	DatePickerDialog.OnDateSetListener dateListener =  
		    new DatePickerDialog.OnDateSetListener() { 
				@Override
				public void onDateSet(DatePicker datePicker,  
		                int year, int month, int dayOfMonth) {
					// TODO Auto-generated method stub
					
						String info = String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
						mEt_title.setText(info);
				
				} 
		    }; 
		    
	DatePickerDialog dialog;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_awards);
		mtype = getIntent().getIntExtra("type", 0);
		initHeadView();
		initView();
		if(mtype==0)
		{
			
		}
		else if(mtype == 1)
		{
			mpos = getIntent().getIntExtra("pos", 0);
			mPersonAwardsItem = (PersonAwardsItem)getIntent().getSerializableExtra("item");
			setControlInfo();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
		PersonAwardsActivity.this.setResult(RESULT_CANCELED, resultIntent);
		super.onBack(v);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void initView()
	{
		dialog = new DatePickerDialog(this, 
                dateListener, 
                calendar.get(Calendar.YEAR), 
                calendar.get(Calendar.MONTH), 
                calendar.get(Calendar.DAY_OF_MONTH)); 
		mEt_company = (EditText)findViewById(R.id.et_company);
		mEt_title = (TextView)findViewById(R.id.et_title);
		mEt_title.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.show();
			}
		});
		mEt_workinternship_info = (EditText)findViewById(R.id.et_workinternship_info);
	
		mbn_delete = (Button)findViewById(R.id.bn_delete);
		mbn_delete.setOnClickListener(this);
		mbn_save = (Button)findViewById(R.id.bn_save);
		mbn_save.setOnClickListener(this);
		
	}
	
	private void initHeadView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		RelativeLayout menuleft = (RelativeLayout) findViewById(R.id.left_menu_layout);
		menuleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText("个人简历");
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.bn_delete:
		{
			Intent resultIntent = new Intent();
			resultIntent.putExtra("type", mtype);
			if(mtype == 1)
			{
			resultIntent.putExtra("delete",1);
			resultIntent.putExtra("pos", mpos);
			resultIntent.putExtra("item", mPersonAwardsItem);
			PersonAwardsActivity.this.setResult(RESULT_OK, resultIntent);
			}
			else
			{
				PersonAwardsActivity.this.setResult(RESULT_CANCELED, resultIntent);
			}
		
			finish();
		}
		break;
		case R.id.bn_save:
		{
			//sendInfoToService();
			
			getControlInfo();
			Intent resultIntent = new Intent();
			resultIntent.putExtra("type", mtype);
			if(mtype == 1)
			{
			resultIntent.putExtra("delete", 0);
			resultIntent.putExtra("pos", mpos);
			}
			resultIntent.putExtra("item", mPersonAwardsItem);
			PersonAwardsActivity.this.setResult(RESULT_OK, resultIntent);
			finish();
		}
		break;
		}
	}
	
	private void getControlInfo()
	{
		mPersonAwardsItem.award= mEt_company.getText().toString();//公司／实验室
		mPersonAwardsItem.date= mEt_title.getText().toString();//职务
		mPersonAwardsItem.description = mEt_workinternship_info.getText().toString();
	
	}
	
	private void setControlInfo()
	{
		mEt_company.setText(mPersonAwardsItem.award);//公司／实验室
		mEt_title.setText(mPersonAwardsItem.date);//职务
		mEt_workinternship_info.setText(mPersonAwardsItem.description);
	}
}
