package com.aotu.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aotu.Adapter.CurriculumVitaeAdapter;
import com.aotu.Adapter.CurriculumVitaeAdapter.CurriculumVitaeInterface;
import com.aotu.Adapter.SchoolAdapter;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomListView;
import com.aotu.baseview.CustomPopupMenu;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.baseview.CustomListView.OnLoadMoreListener;
import com.aotu.baseview.CustomListView.OnRefreshListener;
import com.aotu.data.CityItem;
import com.aotu.data.CurriculumVitaeItem;
import com.aotu.data.LanguageItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.PersonAssociationItem;
import com.aotu.data.PersonAwardsItem;
import com.aotu.data.PersonContactWayItem;
import com.aotu.data.PersonEducation;
import com.aotu.data.PersonGrades;
import com.aotu.data.PersonInfoBase;
import com.aotu.data.PersonInternshipItem;
import com.aotu.data.PersonUserInfoItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;

public class PersonCurriculumVitaeActivity extends BaseActivity implements CurriculumVitaeInterface{

	static String Title = "个人简历";
	CustomListView mListView;
	CurriculumVitaeAdapter mAdapter;
	List<PersonInfoBase> mData = new ArrayList<PersonInfoBase>();
	PersonInfoBase mPersonInfoBase = null;
	
	CurriculumVitaeItem mCurriculumVitaeItem = new CurriculumVitaeItem();
	static final int EDUCATION = 0;
	static final int INTERNSHIP = 1;
	static final int ASSOCIATION = 2;
	static final int AWARDS = 3;
	static final int SEX = 4;
	static final int SALARY = 5;
	static final int LANGUAGE = 6;
	static final int GRADE = 7;
	static final int COMPUTER = 8;
	DatePickerDialog dialog;
	Calendar calendar = Calendar.getInstance();
	int mDateType=0;
	int sex;
	int salary ;
	String cv_id;
	int titeltype = 0;
	Button bn_add;
	EditText ed_cv_title;
	String title;
	boolean isSend = false;
	TextWatcher mNameTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			mCurriculumVitaeItem.title = ed_cv_title.getText().toString();
		}
	};
	KTAlertDialogOnClickListener mKTADialongright = new KTAlertDialogOnClickListener() {

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			int type = Integer.valueOf((String)button.getTag());
			switch(type)
			{
			case 0:
				finish();
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			}
		}

	};
	DatePickerDialog.OnDateSetListener dateListener =  
		    new DatePickerDialog.OnDateSetListener() { 
				@Override
				public void onDateSet(DatePicker datePicker,  
		                int year, int month, int dayOfMonth) {
					// TODO Auto-generated method stub
						String info = String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
						mCurriculumVitaeItem.personUserInfoItem.birthdate = info;
						//PersonUserInfoItem personUserInfoItem =(PersonUserInfoItem) mData.get(0);
						//personUserInfoItem.birthdate = info;
					    handler.sendEmptyMessage(0);
				} 
		    }; 
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
				UpdateListData();
				if(mAdapter==null&&mData.size()>0)
				{
					mAdapter = new CurriculumVitaeAdapter(PersonCurriculumVitaeActivity.this,mData,PersonCurriculumVitaeActivity.this);
					mListView.setAdapter(mAdapter);
				}
				if(mData.size()>0)
				mAdapter.notifyDataSetChanged();
			}
			break;
			case 1:
			{
				
			}
			break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_curriculum_vitae);
		cv_id = getIntent().getStringExtra("cv_id");
		titeltype = getIntent().getIntExtra("type", 0);
		getCurriculumVitae();
		initView();
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(requestCode)
		{
		case EDUCATION:
		{
			if(resultCode==RESULT_OK)
			{
				PersonEducation aEducationExperienceItem =(PersonEducation) data.getSerializableExtra("item");
				int type = data.getIntExtra("type", 0);
				if(type ==0)
				{
				PersonEducation aEducation = new PersonEducation();
				aEducation.school_text = aEducationExperienceItem.school_text;
				aEducation.school_code = aEducationExperienceItem.school_code;
				aEducation.date_end = aEducationExperienceItem.date_end;
				aEducation.date_start = aEducationExperienceItem.date_start;
				aEducation.department = aEducationExperienceItem.department;
			    aEducation.graduate = aEducationExperienceItem.graduate;
			    aEducation.graduate_code =aEducationExperienceItem.graduate_code;
			    aEducation.major = aEducationExperienceItem.major;
				aEducation.description = aEducationExperienceItem.description;
				aEducation.type = 3;
				mCurriculumVitaeItem.educations.add(aEducation);
				}
				else 
				{
					int pos = data.getIntExtra("pos", 0);
					int delete = data.getIntExtra("delete", 0);
					if(delete == 0)
					{
					PersonEducation aEducation =(PersonEducation) mCurriculumVitaeItem.educations.get(pos);
					aEducation.school_text = aEducationExperienceItem.school_text;
					aEducation.school_code = aEducationExperienceItem.school_code;
					aEducation.date_end = aEducationExperienceItem.date_end;
					aEducation.date_start = aEducationExperienceItem.date_start;
					aEducation.department = aEducationExperienceItem.department;
				    aEducation.graduate = aEducationExperienceItem.graduate;
					aEducation.description = aEducationExperienceItem.description;
					aEducation.type = 3;
					}
					else
					{
						mCurriculumVitaeItem.educations.remove(pos);
					}
				}
				handler.sendEmptyMessage(0);
			}
		}
		break;
		case INTERNSHIP:
		{

			if(resultCode==RESULT_OK)
			{
				
				PersonInternshipItem aPersonSocialItem =(PersonInternshipItem) data.getSerializableExtra("item");
				int type = data.getIntExtra("type", 0);
				if(type ==0)
				{	
					PersonInternshipItem personSocialItem = new PersonInternshipItem();
					personSocialItem.company=aPersonSocialItem.company;//公司／实验室
					personSocialItem.title=aPersonSocialItem.title;//职务
					personSocialItem.date_start=aPersonSocialItem.date_start;//开始时间，年月
					personSocialItem.date_end=aPersonSocialItem.date_end;// 结束时间，年月
					personSocialItem.description=aPersonSocialItem.description;//其他说明
					personSocialItem.type = 4;
					mCurriculumVitaeItem.internships.add(personSocialItem);	
				}
				else 
				{
					int pos = data.getIntExtra("pos", 0);
					int delete = data.getIntExtra("delete", 0);
					if(delete == 0)
					{
					PersonInternshipItem personSocialItem =(PersonInternshipItem) mCurriculumVitaeItem.internships.get(pos);
					personSocialItem.company=aPersonSocialItem.company;//公司／实验室
					personSocialItem.title=aPersonSocialItem.title;//职务
					personSocialItem.date_start=aPersonSocialItem.date_start;//开始时间，年月
					personSocialItem.date_end=aPersonSocialItem.date_end;// 结束时间，年月
					personSocialItem.description=aPersonSocialItem.description;//其他说明
					personSocialItem.type = 4;
					}
					else
					{
						mCurriculumVitaeItem.internships.remove(pos);
					}
				}
				handler.sendEmptyMessage(0);
			}
		
		}
		break;
		case ASSOCIATION:
		{

			if(resultCode==RESULT_OK)
			{
			PersonAssociationItem aPersonSocialItem =(PersonAssociationItem) data.getSerializableExtra("item");
			int type = data.getIntExtra("type", 0);
			if(type ==0)
			{
				
			PersonAssociationItem personSocialItem = new PersonAssociationItem();
			personSocialItem.society=aPersonSocialItem.society;//公司／实验室
			personSocialItem.title=aPersonSocialItem.title;//职务
			personSocialItem.date_start=aPersonSocialItem.date_start;//开始时间，年月
			personSocialItem.date_end=aPersonSocialItem.date_end;// 结束时间，年月
			personSocialItem.description=aPersonSocialItem.description;//其他说明
			personSocialItem.type = 5;
			
			
			
			mCurriculumVitaeItem.associations.add(personSocialItem);
			
			}
			else 
			{
				int delete = data.getIntExtra("delete", 0);
				int pos = data.getIntExtra("pos", 0);
				if(delete == 0)
				{
				
				PersonAssociationItem personSocialItem =(PersonAssociationItem) mCurriculumVitaeItem.associations.get(pos);
				personSocialItem.society=aPersonSocialItem.society;//公司／实验室
				personSocialItem.title=aPersonSocialItem.title;//职务
				personSocialItem.date_start=aPersonSocialItem.date_start;//开始时间，年月
				personSocialItem.date_end=aPersonSocialItem.date_end;// 结束时间，年月
				personSocialItem.description=aPersonSocialItem.description;//其他说明
				personSocialItem.type = 5;
				}
				else
				{
					mCurriculumVitaeItem.associations.remove(pos);
				}
			}
			handler.sendEmptyMessage(0);
			}
		
		}
		break;
		case AWARDS:
		{
			if(resultCode==RESULT_OK)
			{
			PersonAwardsItem aPersonAwardsItem =(PersonAwardsItem) data.getSerializableExtra("item");
			int type = data.getIntExtra("type", 0);
			if(type ==0)
			{
			PersonAwardsItem personAwardsItem = new PersonAwardsItem();
			personAwardsItem.award = aPersonAwardsItem.award;//奖项
			personAwardsItem.date = aPersonAwardsItem.date;//获奖时间
			personAwardsItem.description = aPersonAwardsItem.description;//其他说明
			personAwardsItem.type = 6;
			mCurriculumVitaeItem.awards.add(personAwardsItem);
			}
			else 
			{
				int pos = data.getIntExtra("pos", 0);
				int delete = data.getIntExtra("delete", 0);
				if(delete==0)
				{
				PersonAwardsItem personAwardsItem =(PersonAwardsItem) mCurriculumVitaeItem.awards.get(pos);
				personAwardsItem.award = aPersonAwardsItem.award;//奖项
				personAwardsItem.date = aPersonAwardsItem.date;//获奖时间
				personAwardsItem.description = aPersonAwardsItem.description;//其他说明
				personAwardsItem.type = 6;
				}
				else
				{
					mCurriculumVitaeItem.awards.remove(pos);
				}
			}
			handler.sendEmptyMessage(0);
		
			}
			
		}
		break;
		
		case SEX:
		{
			if(resultCode == RESULT_OK)
			{
				sex = data.getIntExtra("typeid", -1);
				String info = data.getStringExtra("typename");
				
				mCurriculumVitaeItem.personUserInfoItem.gender = String.valueOf(sex); 
				mCurriculumVitaeItem.personUserInfoItem.gender_text =info;
				handler.sendEmptyMessage(0);
			}
		}
		break;
		case SALARY:
		{
			if(resultCode == RESULT_OK)
			{
			salary = data.getIntExtra("code", -1);
			String info = data.getStringExtra("name");
			
			mCurriculumVitaeItem.personUserInfoItem.nativecode = String.valueOf(salary); 
			mCurriculumVitaeItem.personUserInfoItem.native_text =info;
			handler.sendEmptyMessage(0);
			}
		}
		break;
		case LANGUAGE:
		{
			if(resultCode == RESULT_OK)
			{
				mCurriculumVitaeItem.personGrades.languages = AotugeApplication.getInstance().mPersonLanguages.languages;
				handler.sendEmptyMessage(0);
			}
		}
		break;
		case GRADE:
		{
			if(resultCode == RESULT_OK)
			{
				int sex = data.getIntExtra("typeid", -1);
				String info = data.getStringExtra("typename");
				
				mCurriculumVitaeItem.personGrades.grade = info;
				handler.sendEmptyMessage(0);
			}
		}
		break;
		case COMPUTER:
		{
			if(resultCode == RESULT_OK)
			{
				int id = data.getIntExtra("typeid", -1);
				String info = data.getStringExtra("typename");
				mCurriculumVitaeItem.personGrades.computer = info;
				handler.sendEmptyMessage(0);
			}
		}
		break;
		
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void initHeaderView() {
		RelativeLayout btnleft = (RelativeLayout)findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		TextView titleText = (TextView)findViewById(R.id.tv_title_bar_text);
		if(titeltype==1)
			titleText.setText("个人资料");
		else
		titleText.setText(Title);
		RelativeLayout btnright = (RelativeLayout)findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
		
	}
	
	private void initView()
	{
		dialog = new DatePickerDialog(this, 
                dateListener, 
                calendar.get(Calendar.YEAR), 
                calendar.get(Calendar.MONTH), 
                calendar.get(Calendar.DAY_OF_MONTH)); 
		initHeaderView();
		ed_cv_title = (EditText)findViewById(R.id.ed_cv_title);
		ed_cv_title.addTextChangedListener(mNameTextWatcher);
		mListView = (CustomListView)findViewById(R.id.list_view);
		mListView.setCanRefresh(false);
		mListView.setCanLoadMore(false);
		bn_add = (Button)findViewById(R.id.bn_add);
		bn_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sendInfoToService();
			}
		});
		
	}
	
	private void getCurriculumVitae()
	{
		getCVInfo();
	}
	
	private int getItemPos(int type)
	{
		int pos = 0;
		if(type == 1)
		{
			pos = 3 + mCurriculumVitaeItem.educations.size();
		}
		else if(type == 2)
		{
			pos = 3 + mCurriculumVitaeItem.educations.size();
			pos +=mCurriculumVitaeItem.internships.size();
		}
		else if(type == 3)
		{
			pos = 3 + mCurriculumVitaeItem.educations.size();
			pos +=mCurriculumVitaeItem.internships.size(); 
			pos +=mCurriculumVitaeItem.associations.size();
		}
		else if(type == 4)   
		{
			pos = 3 + mCurriculumVitaeItem.educations.size();
			pos +=mCurriculumVitaeItem.internships.size(); 
			pos +=mCurriculumVitaeItem.associations.size();
			pos +=mCurriculumVitaeItem.awards.size();
		}
		return pos;
	}

	@Override
	public void onChange(PersonEducation item) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PersonCurriculumVitaeActivity.this,PersonEducationActivity.class);
		intent.putExtra("item", item);
		intent.putExtra("type", 1);
		int pos = mCurriculumVitaeItem.educations.indexOf(item);
		intent.putExtra("pos", pos);
		startActivityForResult(intent, EDUCATION);
	}

	@Override
	public void onAddPersonEducation() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PersonCurriculumVitaeActivity.this,PersonEducationActivity.class);
		intent.putExtra("type", 0);
		startActivityForResult(intent,EDUCATION);
	}

	@Override
	public void onChange(PersonAwardsItem item) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PersonCurriculumVitaeActivity.this,PersonAwardsActivity.class);
		intent.putExtra("type", 1);
		int pos = mCurriculumVitaeItem.awards.indexOf(item);
		intent.putExtra("item", item);
		intent.putExtra("pos", pos);
		startActivityForResult(intent, AWARDS);
	}

	@Override
	public void onAddAwards() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PersonCurriculumVitaeActivity.this,PersonAwardsActivity.class);
		intent.putExtra("type", 0);
		startActivityForResult(intent, AWARDS);
	}

	@Override
	public void onSex() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PersonCurriculumVitaeActivity.this, CustomPopupMenu.class);
		intent.putExtra("type", 0);
		startActivityForResult(intent, SEX);
	}

	@Override
	public void onBirthday() {
		// TODO Auto-generated method stub
		mDateType = 1;
		dialog.show();
	}

	@Override
	public void onNative() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PersonCurriculumVitaeActivity.this, WorkPlaceActivity.class);
		intent.putExtra("type", 1);
		startActivityForResult(intent,SALARY);
	}
	
	void getCVInfo()
	{
		String url = NetUrlManager.getCVInfoURL(cv_id)+"&token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		client.get(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getCVInfo(content, mCurriculumVitaeItem);
				if(info.success)
				{
					handler.sendEmptyMessage(0);
				
				}
				else
				    showMessageDialog("提示", info.info, "确定", null,1);
				
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
		if(!isSend)
		{
		isSend = true;
		String title = ed_cv_title.getText().toString();
		if(title==null||title.length()==0)
		{
			showMessageDialog("提示", "请填写简历标题", "确定", null,0);
		}
		else
		{
		String url = NetUrlManager.getSaveCVURL()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("platform", "android");
		RequestParams params = new RequestParams();
		if(!cv_id.equals("0"))
		{
			params.put("id",cv_id );
			url+="&id="+ cv_id;
		}
		sendUserinfo(params);
		sendEducation(params);
		sendAssociation(params);
		sendinternships(params);
		sendawards(params);
		client.post(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				isSend = false;
				NetReturnInfo info = JosnParser.getPersonCurriculumVitae(content);
				if(info.success)
					showMessageDialog("提示", info.info, "确定",mKTADialongright ,0);
				else
				{
					showMessageDialog("提示", info.info, "确定", null,1);
//					switch(info.status)
//					{
//					case -1:
//					{
//						showMessageDialog("提示", info.info, "确定", null,1);
//					}
//						break;
//					case -11:
//					{
//						showMessageDialog("提示", info.info, "确定", null,2);
//					}
//						break;
//					case -999://参数错误
//					{
//						showMessageDialog("提示", info.info, "确定", null,3);
//					}
//						break;
//					}
				}
				
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showMessageDialog("提示", "服务器忙请稍后再试", "确定", null,0);
				isSend = false;
			}
		});
		}
		}
		else
		{
			
		}
	}
	
	private void sendEducation(RequestParams params)
	{
		for(int i = 0; i < mCurriculumVitaeItem.educations.size();i++)
		{
			PersonEducation personEducation  = mCurriculumVitaeItem.educations.get(i);
			String tab1 = "education["+String.valueOf(i)+"][school]";
			String tab2 = "education["+String.valueOf(i)+"][school_code]";
			String tab3 = "education["+String.valueOf(i)+"][date_start]";
			String tab4 = "education["+String.valueOf(i)+"][date_end]";
			String tab5 = "education["+String.valueOf(i)+"][department]";
			String tab6 = "education["+String.valueOf(i)+"][major]";
			String tab7 = "education["+String.valueOf(i)+"][description]";
			String tab8 = "education["+String.valueOf(i)+"][graduate]";
			String tab9 = "education["+String.valueOf(i)+"][graduate_code]";
			
			String school_text = personEducation.school_text;// 学校
			String school_code = personEducation.school_code;// 学校
			String date_start = personEducation.date_start ;// 入学时间，只到年份
			String date_end = personEducation.date_end ;// 入学时间，只到年份
			String department = personEducation.department;//学院
			String major = personEducation.major;//专业
			String graduate = personEducation.graduate;//学历
			String graduate_code = personEducation.graduate_code;//学历编号
			String description = personEducation.description;//其他说明 
			params.put(tab1,school_text );
			params.put(tab2, school_code);
			params.put(tab3,date_start );
			params.put(tab4, date_end);
			params.put(tab5, department);
			params.put(tab6, major);
			params.put(tab7, description);
			params.put(tab8, graduate);
			params.put(tab9, graduate_code);		
		}
		
	}
	
	private void sendAssociation(RequestParams params)
	{
		
		for(int i = 0; i < mCurriculumVitaeItem.associations.size();i++)
		{
			PersonAssociationItem personSocialItem = mCurriculumVitaeItem.associations.get(i);
			String tab1 = "association["+String.valueOf(i)+"][society]";
			String tab2 = "association["+String.valueOf(i)+"][title]";
			String tab3 = "association["+String.valueOf(i)+"][date_start]";
			String tab4 = "association["+String.valueOf(i)+"][date_end]";
			String tab5 = "association["+String.valueOf(i)+"][description]";	
			params.put(tab1,personSocialItem.society );
			params.put(tab2, personSocialItem.title);
			params.put(tab3, personSocialItem.date_start);
			params.put(tab4,personSocialItem.date_end );
			params.put(tab5, personSocialItem.description);
		}
	}
	
	private void sendawards(RequestParams params)
	{
		for(int i = 0; i < mCurriculumVitaeItem.awards.size();i++)
		{
			PersonAwardsItem personAwardsItem = mCurriculumVitaeItem.awards.get(i);
			String tab1 = "awards["+String.valueOf(i)+"][award]";
			String tab2 = "awards["+String.valueOf(i)+"][date]";
		
			String tab3 = "awards["+String.valueOf(i)+"][description]";	
			params.put(tab1,personAwardsItem.award );
			params.put(tab2, personAwardsItem.date);
			params.put(tab3, personAwardsItem.description);
		
		}
	}
	
	private void sendinternships(RequestParams params)
	{
		for(int i = 0; i < mCurriculumVitaeItem.internships.size();i++)
		{
			PersonInternshipItem personSocialItem = mCurriculumVitaeItem.internships.get(i);
			String tab1 = "internship["+String.valueOf(i)+"][company]";
			String tab2 = "internship["+String.valueOf(i)+"][title]";
			String tab3 = "internship["+String.valueOf(i)+"][date_start]";
			String tab4 = "internship["+String.valueOf(i)+"][date_end]";
			String tab5 = "internship["+String.valueOf(i)+"][description]";	
			params.put(tab1,personSocialItem.company );
			params.put(tab2, personSocialItem.title);
			params.put(tab3, personSocialItem.date_start);
			params.put(tab4,personSocialItem.date_end );
			params.put(tab5, personSocialItem.description);
		}
	}
	
	private void sendUserinfo(RequestParams params)
	{
		String title = ed_cv_title.getText().toString();
		if(title.length()==0)
		   params.put("title", "简历1");
		else
			params.put("title", title);	
	
		params.put("name",mCurriculumVitaeItem.personUserInfoItem.name );//姓名	
		params.put("birthdate", mCurriculumVitaeItem.personUserInfoItem.birthdate);//生日	
		params.put("gender", mCurriculumVitaeItem.personUserInfoItem.gender);//性别
		params.put("native",mCurriculumVitaeItem.personUserInfoItem.nativecode);//籍贯
		params.put("gender_text", mCurriculumVitaeItem.personUserInfoItem.gender_text);//性别
		params.put("native_text",mCurriculumVitaeItem.personUserInfoItem.native_text);//籍贯
		 
		//params.put("school", "");//毕业院校
		params.put("email", mCurriculumVitaeItem.personContactWayItem.email);//邮箱
		params.put("mobile", mCurriculumVitaeItem.personContactWayItem.mobile);//手机号
		params.put("grade", mCurriculumVitaeItem.personGrades.grade);//成绩 
		params.put("computer", mCurriculumVitaeItem.personGrades.computer);//成绩
		for(int i = 0 ; i < mCurriculumVitaeItem.personGrades.languages.size();i++)
		{
			
			LanguageItem languageItem  = mCurriculumVitaeItem.personGrades.languages.get(i);
			if(languageItem.language.length() >0)
			{
			String tab1 = "languages["+String.valueOf(i)+"][language]";
			String tab2 = "languages["+String.valueOf(i)+"][level]";
			params.put(tab1, languageItem.language);
			params.put(tab2, languageItem.level);
			}
		}     
	}

	@Override
	public void onChangeInternships(PersonInternshipItem item) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PersonCurriculumVitaeActivity.this,PersonInternshipActivity.class);
		intent.putExtra("item", item);
		intent.putExtra("type", 1);
		int pos = mCurriculumVitaeItem.internships.indexOf(item);
		intent.putExtra("pos", pos);
		startActivityForResult(intent, INTERNSHIP);
	}

	@Override
	public void onAddInternships() {
		// TODO Auto-generated method stub
	
		Intent intent = new Intent(PersonCurriculumVitaeActivity.this,PersonInternshipActivity.class);
		intent.putExtra("type", 0);
		startActivityForResult(intent, INTERNSHIP);
		
	
		
	}

	@Override
	public void onChangeAssociations(PersonAssociationItem item) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PersonCurriculumVitaeActivity.this,PersonAssociationActivity.class);
		intent.putExtra("item", item);
		intent.putExtra("type", 1);
		int pos = mCurriculumVitaeItem.associations.indexOf(item);
		intent.putExtra("pos", pos);
		startActivityForResult(intent, ASSOCIATION);
	}

	@Override
	public void onAddAssociations() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PersonCurriculumVitaeActivity.this,PersonAssociationActivity.class);
		intent.putExtra("type", 0);
		startActivityForResult(intent, ASSOCIATION);
	}

	@Override
	public void onAddLanguage() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this,PersonLanguagesActivity.class);
		AotugeApplication.getInstance().mPersonLanguages = mCurriculumVitaeItem.personGrades;

		startActivityForResult(intent, LANGUAGE);
	}

	@Override
	public void onGrade() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PersonCurriculumVitaeActivity.this, CustomPopupMenu.class);
		intent.putExtra("type", 7);
		startActivityForResult(intent, GRADE);
	}
	
	private void UpdateListData()
	{
		mData.clear();
		ed_cv_title.setText(mCurriculumVitaeItem.title);
		title = mCurriculumVitaeItem.title;
		mCurriculumVitaeItem.personUserInfoItem.type = 0;
		mData.add(mCurriculumVitaeItem.personUserInfoItem);
		mCurriculumVitaeItem.personContactWayItem.type = 1;
		mData.add(mCurriculumVitaeItem.personContactWayItem);
		mCurriculumVitaeItem.personGrades.type = 2;
		mData.add(mCurriculumVitaeItem.personGrades);
		if(mCurriculumVitaeItem.educations.size() == 0)
		{
		PersonInfoBase educationtab = new PersonInfoBase();
		educationtab.type = 7;
		mData.add(educationtab);
		}
		 for(int i = 0; i < mCurriculumVitaeItem.educations.size();i++)
	    {
			if(i==0)
				mCurriculumVitaeItem.educations.get(i).isTop = true;
	    	mCurriculumVitaeItem.educations.get(i).type = 3;
	    	mData.add(mCurriculumVitaeItem.educations.get(i));
	    }
		 
	   if(mCurriculumVitaeItem.internships.size()==0)
	   {
	     PersonInfoBase internshiptab = new PersonInfoBase();
	     internshiptab.type = 8;
	     mData.add(internshiptab);
	   }
	   for(int i = 0; i < mCurriculumVitaeItem.internships.size();i++)
	    {
		   if(i==0)
			   mCurriculumVitaeItem.internships.get(i).isTop  =true;
	    	mCurriculumVitaeItem.internships.get(i).type  =4;
	    	mData.add(mCurriculumVitaeItem.internships.get(i));
	    }
	    if(mCurriculumVitaeItem.associations.size()==0)
	    {
        PersonInfoBase associationstab = new PersonInfoBase();
        associationstab.type = 9;
  		mData.add(associationstab);
	    }
  	    for(int i = 0; i < mCurriculumVitaeItem.associations.size();i++)
  	    {
  	    	if(i==0)
			   mCurriculumVitaeItem.associations.get(i).isTop  =true;
  	    	mCurriculumVitaeItem.associations.get(i).type = 5;
  	    	mData.add(mCurriculumVitaeItem.associations.get(i));
  	    }
  	    if(mCurriculumVitaeItem.awards.size()==0)
  	    {
  	    PersonInfoBase awardstab = new PersonInfoBase();
  	    awardstab.type = 10;
  		mData.add(awardstab);
  	    }
  	    for(int i = 0; i < mCurriculumVitaeItem.awards.size();i++)
  	    {
  	       if(i==0)
 			   mCurriculumVitaeItem.awards.get(i).isTop  =true;
  	    	mCurriculumVitaeItem.awards.get(i).type = 6;
  	    	mData.add(mCurriculumVitaeItem.awards.get(i));
  	    }  
	}

	@Override
	public void onComputer() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PersonCurriculumVitaeActivity.this, CustomPopupMenu.class);
		intent.putExtra("type", 8);
		startActivityForResult(intent, COMPUTER);
	}

	@Override
	public void onChangeName(String name) {
		// TODO Auto-generated method stub
		mCurriculumVitaeItem.personUserInfoItem.name = name;
	}

	@Override
	public void onChangeEmail(String email) {
		// TODO Auto-generated method stub
		mCurriculumVitaeItem.personContactWayItem.email = email;
	}

	@Override
	public void onChangeMobile(String moblie) {
		// TODO Auto-generated method stub
		mCurriculumVitaeItem.personContactWayItem.mobile = moblie;
	}


}
