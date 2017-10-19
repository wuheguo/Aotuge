package com.aotu.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.aotu.Adapter.CVItemAdapter;
import com.aotu.Adapter.LanguageAdapter;
import com.aotu.Adapter.LanguageAdapter.LanguageInterface;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomListView;
import com.aotu.baseview.CustomPopupMenu;
import com.aotu.data.CVItem;
import com.aotu.data.LanguageItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.PersonAssociationItem;
import com.aotu.data.PersonAwardsItem;
import com.aotu.data.PersonEducation;
import com.aotu.data.PersonGrades;
import com.aotu.data.PersonInternshipItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;

/*
 * 语言能力
 */
public class PersonLanguagesActivity extends BaseActivity implements LanguageInterface{

	PersonGrades mPersonLanguages ;
	LanguageAdapter mAdapter;
	CustomListView mListView;
	Button bn_delete,bn_save;
	static final int LANGUAGE = 0;
	static final int LEVEL = 1;
	int CurLanguage;
	int CurLevel;
	int CurLanguagePos;
	int CurLevelPos;
	
	String CurLanguage1;
	String CurLanguage2;
	String CurLanguage3;
	String level1;
	String level2;
	String level3;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch(msg.what)
			{
			case 0:
			{
				if(mAdapter == null)
				{
				  mAdapter = new LanguageAdapter(PersonLanguagesActivity.this,mPersonLanguages.languages,PersonLanguagesActivity.this);
				  mListView.setAdapter(mAdapter);
				}
				mAdapter.notifyDataSetChanged();
			}
			break;
			}
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_languages);
		mPersonLanguages = AotugeApplication.getInstance().mPersonLanguages;
		int number = mPersonLanguages.languages.size();
		for(int i=0; i < 3-number; i++)
		{
			LanguageItem item = new LanguageItem();
			item.language = "";
			item.level = "";
			mPersonLanguages.languages.add(item);
		}
		initHeadView();
		initView();
		handler.sendEmptyMessage(0);
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
		switch(requestCode)
		{
		case LANGUAGE:
		{
			if(resultCode == RESULT_OK)
			{
				CurLanguage = data.getIntExtra("typeid", -1);
				String info = data.getStringExtra("typename");
				mPersonLanguages.languages.get(CurLanguagePos).language =info; 
				mPersonLanguages.languages.get(CurLanguagePos).level =""; 
				handler.sendEmptyMessage(0);	
			}
		}
		break;
		case LEVEL:
		{
			if(resultCode == RESULT_OK)
			{
				CurLevel = data.getIntExtra("typeid", -1);
				String info = data.getStringExtra("typename");
				mPersonLanguages.languages.get(CurLevelPos).level =info; 
				handler.sendEmptyMessage(0);
			}
		}
		break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void initHeadView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		RelativeLayout menuleft = (RelativeLayout) findViewById(R.id.left_menu_layout);
		menuleft.setVisibility(View.INVISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText("语言选择");
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}
	
	private void initView()
	{
		mListView = (CustomListView)findViewById(R.id.list_view);
		mListView.setCanLoadMore(false);
		mListView.setCanRefresh(false);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		
		bn_delete = (Button)findViewById(R.id.bn_delete);
		bn_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonLanguagesActivity.this,PersonCurriculumVitaeActivity.class);
				
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});
		
		bn_save = (Button)findViewById(R.id.bn_save);
		bn_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AotugeApplication.getInstance().mPersonLanguages = mPersonLanguages;
                Intent intent = new Intent(PersonLanguagesActivity.this,PersonCurriculumVitaeActivity.class);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
	}

	@Override
	public void onlanguageClick(int pos) {
		// TODO Auto-generated method stub
		CurLanguagePos = pos;
		Intent intent = new Intent(PersonLanguagesActivity.this, CustomPopupMenu.class);
		intent.putExtra("type", 9);
		startActivityForResult(intent, LANGUAGE);
	}

	@Override
	public void onlevelClick(int pos) {
		// TODO Auto-generated method stub
		  if(mPersonLanguages.languages.get(pos).language.length()>0)
		  {
		   CurLevelPos = pos;
		   Intent intent = new Intent(PersonLanguagesActivity.this, CustomPopupMenu.class);
		   intent.putExtra("type", 10);
		   intent.putExtra("childid", CurLanguage);
		   startActivityForResult(intent, LEVEL);
		  }
		
	}
}
