package com.aotu.baseview;

import java.util.LinkedList;
import java.util.List;

import com.aotu.aotuge.AotugeApplication;
import com.aotu.data.BaseChildItem;
import com.aotu.data.BaseDataItem;
import com.aotu.data.BaseGrade;
import com.aotu.data.BaseGradeLanguages;
import com.aotu.data.BaseItem;
import com.aotu.data.Ways;
import com.auto.aotuge.R;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class CustomPopupMenu extends Activity{

	 private Button btn_cancel;  
	 private LinearLayout layout;
	 private List<PopupMeunItem> mItmeArray = new LinkedList<PopupMeunItem>();
	 private PopupMenuAdaper mAdapter;
	 private ListView mListView;
	 public CustomPopupMenuInterface  mInterface;
	 
	 private int mType;
	 private int mChildid;
	 public interface CustomPopupMenuInterface {
			public void onMenuItem(int pos);
		}
	 
	 private Handler handler = new Handler() 
		{
			public void handleMessage(android.os.Message msg) {
				mAdapter.notifyDataSetChanged();
			}
			
		};
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_pop_menu);
		mType = getIntent().getIntExtra("type", 0);
		mChildid = getIntent().getIntExtra("childid", 0);
		initView();
					
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	private void initView()
	{
		 initListView();
		 btn_cancel = (Button) this.findViewById(R.id.btn_cancel);  
		 btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent data = new Intent();
				setResult(RESULT_CANCELED, data);
				finish();
			}
		});
         
//	     layout=(LinearLayout)findViewById(R.id.pop_layout); 
//	     layout.setOnClickListener(new OnClickListener() {  
//				@Override
//				public void onClick(View arg0) {
//					
//				
//				}  
//	        });  
	}
	
	private void initListView()
	{
        mListView = (ListView)findViewById(R.id.home_page_list);
		mAdapter = new PopupMenuAdaper(this,mItmeArray);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				int menuid = mItmeArray.get((int)arg3).mMeunID;
				if(mInterface !=null)
				mInterface.onMenuItem(menuid);
				Intent data = new Intent();
				data.putExtra("typeid",menuid );
				data.putExtra("typename", mItmeArray.get((int) arg3).mMeunText);
				setResult(RESULT_OK, data);
				finish();
				
			}
		});
		InitpopupMenuData();
	}
	

	private void InitpopupMenuData()
	{
		switch(mType)
		{
		case 0://性别
		{
			for(int i = 0; i < AotugeApplication.getInstance().mBaseDataItem.gender.size();i++)
			{
				BaseChildItem basedataItem = AotugeApplication.getInstance().mBaseDataItem.gender.get(i);
				PopupMeunItem item = new PopupMeunItem();
				item.mMeunID = basedataItem.code;
				item.mMeunText =basedataItem.name;
				mItmeArray.add(item);
			}
		}
			break;
		case 1:// 薪资范围
		{
			for(int i = 0; i < AotugeApplication.getInstance().mBaseDataItem.salaries.size();i++)
			{
				BaseChildItem basedataItem = AotugeApplication.getInstance().mBaseDataItem.salaries.get(i);
				PopupMeunItem item = new PopupMeunItem();
				item.mMeunID = basedataItem.code;
				item.mMeunText =basedataItem.name;
				mItmeArray.add(item);
			}
		}
			break;
		case 2:// 学历
		{
			for(int i = 0; i < AotugeApplication.getInstance().mBaseDataItem.graduate.size();i++)
			{
				BaseChildItem basedataItem = AotugeApplication.getInstance().mBaseDataItem.graduate.get(i);
				PopupMeunItem item = new PopupMeunItem();
				item.mMeunID = basedataItem.code;
				item.mMeunText =basedataItem.name;
				mItmeArray.add(item);
			}
		}
			break;
		case 3:
		{
			for(int i = 1; i < AotugeApplication.getInstance().mCityItemList.size();i++)
			{
				PopupMeunItem item = new PopupMeunItem();
				item.mMeunID = i;
				item.mMeunText = AotugeApplication.getInstance().mCityItemList.get(i).provinve;
				mItmeArray.add(item);
			}
		}
		break;
		case 4:// 工作类型
		{
			for(int i = 0; i < AotugeApplication.getInstance().mBaseDataItem.work_type.size();i++)
			{
				BaseChildItem basedataItem = AotugeApplication.getInstance().mBaseDataItem.work_type.get(i);
				PopupMeunItem item = new PopupMeunItem();
				item.mMeunID = basedataItem.code;
				item.mMeunText =basedataItem.name;
				mItmeArray.add(item);
			}
		}
			break;
		case 5:// 支付类型
		{
			for(int i = 0; i < AotugeApplication.getInstance().mTixianInfo.Ways.size();i++)
			{
				Ways basedataItem = AotugeApplication.getInstance().mTixianInfo.Ways.get(i);
				PopupMeunItem item = new PopupMeunItem();
				item.mMeunID = i;
				item.mMeunText =basedataItem.name;
				mItmeArray.add(item);
			}
		}
			break;
		case 7:// 成绩
		{
			for(int i = 0; i < AotugeApplication.getInstance().mBaseGrade.grade.size();i++)
			{
				BaseItem basedataItem = AotugeApplication.getInstance().mBaseGrade.grade.get(i);
				PopupMeunItem item = new PopupMeunItem();
				item.mMeunID = i;
				item.mMeunText =basedataItem.name;
				mItmeArray.add(item);
			}
		}
			break;
		case 8:// 计算机
		{
			for(int i = 0; i < AotugeApplication.getInstance().mBaseGrade.computer.size();i++)
			{
				BaseItem basedataItem = AotugeApplication.getInstance().mBaseGrade.computer.get(i);
				PopupMeunItem item = new PopupMeunItem();
				item.mMeunID = i;
				item.mMeunText =basedataItem.name;
				mItmeArray.add(item);
			}
		}
			break;
		case 9:// 语言
		{
			for(int i = 0; i < AotugeApplication.getInstance().mBaseGrade.languages.size();i++)
			{
				BaseGradeLanguages basedataItem = AotugeApplication.getInstance().mBaseGrade.languages.get(i);
				PopupMeunItem item = new PopupMeunItem();
				item.mMeunID = i;
				item.mMeunText =basedataItem.lang;
				mItmeArray.add(item);
			}
		}
		break;
		case 10:// 语言等级
		{
			for(int i = 0; i < AotugeApplication.getInstance().mBaseGrade.languages.get(mChildid).level.size();i++)
			{
				PopupMeunItem item = new PopupMeunItem();
				item.mMeunID = i;
				item.mMeunText =AotugeApplication.getInstance().mBaseGrade.languages.get(mChildid).level.get(i);
				mItmeArray.add(item);
			}
		}
		break;
		
		}
		handler.sendEmptyMessage(0);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		 finish(); 
		 return true;
		//return super.onTouchEvent(event);
	}

    

}
