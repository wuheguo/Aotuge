package com.aotu.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomPopupMenu;
import com.aotu.baseview.DoubleDatePickerDialog;
import com.aotu.baseview.MonPickerDialog;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.PersonEducation;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;

/*
 * 教育经历
 */
public class PersonEducationActivity extends BaseActivity implements OnClickListener{

	static final int SALARY = 1;
	static final int SCHOOL = 2;
	TextView et_company,et_education,et_start_date_school,et_end_date_school;
	int start_date,end_date;
	EditText met_title,et_specialty;
	EditText met_workinternship_info;
	Button  mbn_delete,mbn_save;
	PersonEducation mEducationExperienceItem = new PersonEducation();
	Calendar c = null;
	int mtype;
	int mpos;
	
	int mDateType;
	
	int school;
	String salaryID;
	Calendar calendar = Calendar.getInstance();
	
	String cvid = "";
	DatePickerDialog.OnDateSetListener dateListener =  
		    new DatePickerDialog.OnDateSetListener() { 
				@Override
				public void onDateSet(DatePicker datePicker,  
		                int year, int month, int dayOfMonth) {
					// TODO Auto-generated method stub
					if(mDateType == 1)
					{
						String info = String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
						et_start_date_school.setText(info);
					}
					else if(mDateType == 2)
					{
						String info = String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
						et_end_date_school.setText(info);
					}
				} 
		    }; 
		    
	DatePickerDialog dialog;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_education);
		mtype = getIntent().getIntExtra("type", 0);
		dialog = new DatePickerDialog(this, 
                dateListener, 
                calendar.get(Calendar.YEAR), 
                calendar.get(Calendar.MONTH), 
                calendar.get(Calendar.DAY_OF_MONTH)); 
		
		if(mtype==0)
		{
			
		}
		else if(mtype == 1)
		{
			mpos = getIntent().getIntExtra("pos", 0);
			mEducationExperienceItem = (PersonEducation)getIntent().getSerializableExtra("item");
			
		}
		initHeadView();
		initView();
		if(mtype == 1)
			setViewData();
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
		PersonEducationActivity.this.setResult(RESULT_CANCELED, resultIntent);
		
		super.onBack(v);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode)
		{
			case SCHOOL:
			{
				if(resultCode == RESULT_OK)
				{
					school = data.getIntExtra("code", -1);
					String info = data.getStringExtra("name");
					et_company.setText(info);
				}
			}
			break;
			case SALARY:
			{
				if(resultCode==RESULT_OK)
				{
					String name = data.getStringExtra("typename");
					salaryID = String.valueOf(data.getIntExtra("typeid",0));
					et_education.setText(name);
				}	
			}
			break;
		}
	}
	
	private void initView()
	{
		et_company = (TextView)findViewById(R.id.et_company);
		et_company.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonEducationActivity.this, SchoolActivity.class);
				startActivityForResult(intent, SCHOOL);
			}
		});
		met_title = (EditText)findViewById(R.id.et_title);
		et_specialty = (EditText)findViewById(R.id.et_specialty);
		et_education = (TextView)findViewById(R.id.et_education);
		et_education.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonEducationActivity.this,CustomPopupMenu.class);
				intent.putExtra("type", 2);
				startActivityForResult(intent, SALARY);
			}
		});
		et_start_date_school = (TextView)findViewById(R.id.et_start_date_school);
		et_start_date_school.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mDateType = 1;				
				showMonPicker();
				
				//dialog.show();
			}
		});
		et_end_date_school = (TextView)findViewById(R.id.et_end_date_school);
		et_end_date_school.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mDateType = 2;
				showMonPicker();
				//dialog.show();
			}
		});
		met_workinternship_info = (EditText)findViewById(R.id.et_workinternship_info);
		
		mbn_delete = (Button)findViewById(R.id.bn_delete);
		mbn_delete.setOnClickListener(this);
		mbn_save = (Button)findViewById(R.id.bn_save);
		mbn_save.setOnClickListener(this);
		
	}
	
	private void setViewData()
	{
		et_company.setText(mEducationExperienceItem.school_text);// 学校
		et_start_date_school.setText(mEducationExperienceItem.date_start);// 入学时间，只到年份
		et_end_date_school.setText(mEducationExperienceItem.date_end);
		met_title.setText(mEducationExperienceItem.department);//学院
		et_specialty.setText(mEducationExperienceItem.major) ;//专业
		et_education.setText(mEducationExperienceItem.graduate) ;//学历
		met_workinternship_info.setText(mEducationExperienceItem.description);//其他说明 
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
				resultIntent.putExtra("delete", 1);
				resultIntent.putExtra("pos", mpos);
				resultIntent.putExtra("item", mEducationExperienceItem);
				PersonEducationActivity.this.setResult(RESULT_OK, resultIntent);
				}
				else
				{
					PersonEducationActivity.this.setResult(RESULT_CANCELED, resultIntent);	
				}
			
				finish();
			}
			break;
			case R.id.bn_save:
			{
				getControlInfo();
				Intent resultIntent = new Intent();
				resultIntent.putExtra("type", mtype);
				if(mtype == 1)
				{
				resultIntent.putExtra("delete", 0);
				resultIntent.putExtra("pos", mpos);
				}
				resultIntent.putExtra("item", mEducationExperienceItem);
				PersonEducationActivity.this.setResult(RESULT_OK, resultIntent);
				finish();
			}
			break;
		}
		
	}
	
	private void getControlInfo()
	{
		mEducationExperienceItem.school_text = et_company.getText().toString();// 学校
		mEducationExperienceItem.school_code = String.valueOf(school);
		mEducationExperienceItem.date_start = et_start_date_school.getText().toString();// 入学时间，只到年份
		mEducationExperienceItem.date_end = et_end_date_school.getText().toString();
		mEducationExperienceItem.department = met_title.getText().toString();//学院
		mEducationExperienceItem.major = et_specialty.getText().toString();//专业
		mEducationExperienceItem.graduate = et_education.getText().toString();//学历
		mEducationExperienceItem.graduate_code = String.valueOf(salaryID);//学历
		mEducationExperienceItem.description = met_workinternship_info.getText().toString();//其他说明 
	}
	
	private void showdate()
	{
		Calendar c = Calendar.getInstance();
		// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
		new DoubleDatePickerDialog(PersonEducationActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
					int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
					int endDayOfMonth) {
				String textString = String.format("开始时间：%d-%d-%d\n结束时间：%d-%d-%d\n", startYear,
						startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
				
			}
		}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
	
	}
	
	public void showMonPicker() {
		
		final Calendar localCalendar = Calendar.getInstance();
		if(mDateType == 1)
		{
			if(et_start_date_school.getText().toString().length()==4)
		       localCalendar.setTime(strToDate("yyyy", et_start_date_school.getText().toString()));
			else
			  localCalendar.setTime(strToDate("yyyy", "2015"));
		}
		else if(mDateType == 2)
		{
			if(et_end_date_school.getText().toString().length()==4)
			       localCalendar.setTime(strToDate("yyyy", et_end_date_school.getText().toString()));
				else
				  localCalendar.setTime(strToDate("yyyy", "2015"));
		}
		new MonPickerDialog(this,new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
						localCalendar.set(1, year);
						localCalendar.set(2, monthOfYear);
						if(mDateType == 1)
						{
							String info = String.valueOf(year);
							et_start_date_school.setText(info);
							start_date = year;
						}
						else if(mDateType == 2)
						{
							if(year >= start_date)
							{
							String info = String.valueOf(year);
							et_end_date_school.setText(info);
							
							}
							else
							{
								showMessageDialog("提示", "毕业时间不能早于入校时间", "确定", null, 9);
							}
							
						}
					}
				}, localCalendar.get(1), localCalendar.get(2),localCalendar.get(5),1).show();
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
