package com.aotu.activity;

import java.util.ArrayList;
import java.util.List;

import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomGridView;
import com.aotu.baseview.CustomPopupMenu;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.WorkItem;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SearchWorkActivity extends BaseActivity implements OnClickListener {

	static final int INTENTION = 1;
	static final int CITY = 2;
	static final int SALARY = 3;
	static final int WORK_TYPE = 4;
	static final int CV = 5;
	RelativeLayout mR_intention, mR_cities, mR_salary, mR_workType;
	TextView intention, city, salary, work_type;
	Button mSearch;
	
	ArrayList<String> Directions = new ArrayList<String>();
	ArrayList<String> Citys = new ArrayList<String>();
	String salaryID = "0";
	String workTypeID="0";
    int page = 1;
    
    
   
    int type = 0;
    
    List<WorkItem> mWorkItems = new ArrayList<WorkItem>();
	KTAlertDialogOnClickListener mKTADialongleft = new KTAlertDialogOnClickListener() {

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub

		}

	};

	KTAlertDialogOnClickListener mKTADialongright = new KTAlertDialogOnClickListener() {

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub

		}

	};

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {

			}

				break;
			case 1: {

			}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_work);
		type = getIntent().getIntExtra("type", 0);
		
		initView();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private void initView() {
		initHeaderView();
		mR_intention = (RelativeLayout) findViewById(R.id.r_intention);
		mR_intention.setOnClickListener(this);
		mR_cities = (RelativeLayout) findViewById(R.id.r_cities);
		mR_cities.setOnClickListener(this);
		mR_salary = (RelativeLayout) findViewById(R.id.r_salary);
		mR_salary.setOnClickListener(this);
		mR_workType = (RelativeLayout) findViewById(R.id.r_workType);
		mR_workType.setOnClickListener(this);
		mSearch = (Button) findViewById(R.id.bn_search);
		mSearch.setOnClickListener(this);
		
		intention = (TextView)findViewById(R.id.tx_position); 
		city= (TextView)findViewById(R.id.tx_city); 
		salary= (TextView)findViewById(R.id.tx_salary); 
		work_type= (TextView)findViewById(R.id.tx_work_type); 
	}

	private void initHeaderView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText("找工作");
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {

		case R.id.r_cities: {
			Intent intent = new Intent(SearchWorkActivity.this,
					WorkPlaceActivity.class);
			intent.putExtra("type", 0);
			startActivityForResult(intent, CITY);
		}
			break;
		case R.id.r_intention: {
			Intent intent = new Intent(SearchWorkActivity.this,
					DirectionsActivity.class);
			startActivityForResult(intent, INTENTION);
		}
			break;
		
		case R.id.r_salary: {
			Intent intent = new Intent(SearchWorkActivity.this,
					CustomPopupMenu.class);
			intent.putExtra("type", 1);
			startActivityForResult(intent, SALARY);
		}
			break;
		case R.id.r_workType: {
			Intent intent = new Intent(SearchWorkActivity.this,
					CustomPopupMenu.class);
			intent.putExtra("type", 4);
			startActivityForResult(intent, WORK_TYPE);
		}
			break;
		case R.id.bn_search:
		{
			if(type==1)
			{
			Intent intent=new Intent();
           
			Bundle bundle=new Bundle();
	        bundle.putStringArrayList("Citys", (ArrayList<String>)Citys);
	        bundle.putStringArrayList("Directions", (ArrayList<String>)Directions);
	        bundle.putString("salaryID", salaryID);
	        bundle.putString("workTypeID", workTypeID);
	        bundle.putString("type", "search");
	        intent.putExtras(bundle);
	        SearchWorkActivity.this.setResult(RESULT_OK, intent);
	        finish();
			}
			else if(type==0)
			{
				    Intent intent=new Intent();
				    intent.setClass(SearchWorkActivity.this, SearchWorkResultActivity.class);
					Bundle bundle=new Bundle();
			        bundle.putStringArrayList("Citys", (ArrayList<String>)Citys);
			        bundle.putStringArrayList("Directions", (ArrayList<String>)Directions);
			        bundle.putString("salaryID", salaryID);
			        bundle.putString("workTypeID", workTypeID);
			        bundle.putString("type", "search");
			        intent.putExtras(bundle);
			        SearchWorkActivity.this.startActivity(intent);
			        finish();
				
			}
			
		}
		break;
		

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(requestCode)
		{
			case INTENTION:
			{
				if(resultCode==RESULT_OK)
				{
					String name = data.getStringExtra("name");
					String code = data.getStringExtra("code");
					 JosnParser.getDataArray(code,Directions);
					 intention.setText(name);
				}
			}
			break;
			case CITY:
			{
				if(resultCode==RESULT_OK)
				{
					String name = data.getStringExtra("name");
					String code = data.getStringExtra("code");
					JosnParser.getDataArray(code,Citys);
					city.setText(name);
					 
				}
			}
			break;
			case SALARY:
			{
				if(resultCode==RESULT_OK)
				{
					String name = data.getStringExtra("typename");
					salaryID = String.valueOf(data.getIntExtra("typeid",0));
					salary.setText(name);
				}	
			}
			break;
			case WORK_TYPE:
			{
				if(resultCode==RESULT_OK)
				{
					String name = data.getStringExtra("typename");
					workTypeID = String.valueOf(data.getIntExtra("typeid",0));
					work_type.setText(name);
					
				}	
			}
			break;
		
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	
	
}
