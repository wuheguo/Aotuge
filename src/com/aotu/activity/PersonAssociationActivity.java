package com.aotu.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import com.aotu.baseview.MonPickerDialog;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.PersonAssociationItem;
import com.aotu.data.PersonEducation;
import com.aotu.data.PersonInternshipItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;
/*
 * 社团经历
 */
public class PersonAssociationActivity extends BaseActivity implements OnClickListener{
	
	PersonAssociationItem mSocialexExperienceItem = new PersonAssociationItem();
    EditText mEt_company,mEt_title;
	
	int start_dateyear,end_dateyear;
	int start_datemoth,end_datemoth;
	EditText mEt_workinternship_info;
	
	TextView mEt_date_start,mEt_date_end;
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
					if(mDateType == 1)
					{
						String info = String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
						mEt_date_start.setText(info);
					}
					else if(mDateType == 2)
					{
						String info = String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
						mEt_date_end.setText(info);
					}
				} 
		    }; 
		    
	DatePickerDialog dialog;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_association);
		mtype = getIntent().getIntExtra("type", 0);
		initHeadView();
		initView();
		if(mtype==0)
		{
			
		}
		else if(mtype == 1)
		{
			mpos = getIntent().getIntExtra("pos", 0);
			mSocialexExperienceItem = (PersonAssociationItem)getIntent().getSerializableExtra("item");
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
		this.setResult(RESULT_CANCELED, resultIntent);
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
		mEt_title = (EditText)findViewById(R.id.et_title);
		mEt_date_start = (TextView)findViewById(R.id.et_date_start);
		mEt_date_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mDateType = 1;
				showMonPicker();
			}
		});
		mEt_date_end = (TextView)findViewById(R.id.et_date_end);
		mEt_date_end.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mDateType = 2;
				showMonPicker();
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
			/*Intent resultIntent = new Intent();
			PersonAssociationActivity.this.setResult(RESULT_CANCELED, resultIntent);
			finish();*/
			
			Intent resultIntent = new Intent();
			resultIntent.putExtra("type", mtype);
			if(mtype == 1)
			{
			resultIntent.putExtra("pos", mpos);
			resultIntent.putExtra("delete", 1);
			resultIntent.putExtra("item", mSocialexExperienceItem);
			PersonAssociationActivity.this.setResult(RESULT_OK, resultIntent);
			}
			else
			{
				this.setResult(RESULT_CANCELED, resultIntent);	
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
			resultIntent.putExtra("pos", mpos);
			resultIntent.putExtra("delete", 0);
			}
			resultIntent.putExtra("item", mSocialexExperienceItem);
			PersonAssociationActivity.this.setResult(RESULT_OK, resultIntent);
			finish();
		}
		break;
		}
	}
	
	private void getControlInfo()
	{
		mSocialexExperienceItem.society= mEt_company.getText().toString();//公司／实验室
		mSocialexExperienceItem.title= mEt_title.getText().toString();//职务
		mSocialexExperienceItem.date_start= mEt_date_start.getText().toString();//开始时间，年月
		mSocialexExperienceItem.date_end= mEt_date_end.getText().toString();// 结束时间，年月
		mSocialexExperienceItem.description= mEt_workinternship_info.getText().toString();//其他说明
	}
	
	private void setControlInfo()
	{
		mEt_company.setText(mSocialexExperienceItem.society);//公司／实验室
		mEt_title.setText(mSocialexExperienceItem.title);//职务
		mEt_date_start.setText(mSocialexExperienceItem.date_start);//开始时间，年月
		mEt_date_end.setText(mSocialexExperienceItem.date_end);// 结束时间，年月
		mEt_workinternship_info.setText(mSocialexExperienceItem.description);//其他说明
	}
	
	public void showMonPicker() {
		final Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(strToDate("yyyy-MM", "2015-09"));
		new MonPickerDialog(this,new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
						localCalendar.set(1, year);
						localCalendar.set(2, monthOfYear);
						if(mDateType == 1)
						{
							String info = String.valueOf(year)+"-"+String.valueOf(monthOfYear+1);
							mEt_date_start.setText(info);
							start_dateyear = year;
							start_datemoth = monthOfYear;
						}
						else if(mDateType == 2)
						{
							if(year > start_dateyear)
							{
								String info = String.valueOf(year)+"-"+String.valueOf(monthOfYear+1);
								mEt_date_end.setText(info);
							}
							else
							{
								if(year == start_dateyear)
								{
									 if(monthOfYear >= start_datemoth)
									 {
									   String info = String.valueOf(year)+"-"+String.valueOf(monthOfYear+1);
									   mEt_date_end.setText(info);
									 }
									 else
									 {
										 showMessageDialog("提示", "结束时间不能早于开始时间", "确定", null, 1);
									 }
								}
								else
								{
								showMessageDialog("提示", "结束时间不能早于开始时间", "确定", null, 1);
								}
							}
							
						}
					}
				}, localCalendar.get(1), localCalendar.get(2),localCalendar.get(5),0).show();
	}
	
	public static Date strToDate(String style, String date) {  
	    SimpleDateFormat formatter = new SimpleDateFormat(style);  
	    try {  
	        return formatter.parse(date);  
	    } catch (ParseException e) {  
	        e.printStackTrace();  
	        return new Date();  
	    }  
	}  
	  
	public static String dateToStr(String style, Date date) {  
	    SimpleDateFormat formatter = new SimpleDateFormat(style);  
	    return formatter.format(date);  
	}  
}
