package com.aotu.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aotu.Adapter.SearchWorkResultAdapter;
import com.aotu.aotuge.AotugeApplication;
import com.aotu.baseview.BaseActivity;
import com.aotu.baseview.CustomPopupMenu;
import com.aotu.baseview.BaseActivity.KTAlertDialogOnClickListener;
import com.aotu.data.IntentionItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.net.RequestParams;
import com.auto.aotuge.R;

public class SetIntentionActivity extends BaseActivity implements OnClickListener{
	
	static final int INTENTION = 1;
	static final int CITY = 2;
	static final int SALARY = 3;
	static final int WORK_TYPE = 4;
	static final int CV = 5;
	
    EditText mEd_directions;
    RelativeLayout mR_intention,mR_city,mR_salary,mR_workType,mR_cv;
    TextView intention,city,salary,work_type,tx_info5;
    Button mSave;
    
    List<String> Directions = new ArrayList<String>();
    List<String> Citys = new ArrayList<String>();
    String salaryID="0";
    String workTypeID="0";
    String cvID = "";
    
    IntentionItem mIntentionItem;
    int type ;
    boolean isSend = false;
	KTAlertDialogOnClickListener mKTADialongleft = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	KTAlertDialogOnClickListener mKTADialongright = new KTAlertDialogOnClickListener()
	{

		@Override
		public void onClick(AlertDialog dialog, View button) {
			// TODO Auto-generated method stub
			finish();
		}
		
	};
	
    
    private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch(msg.what)
			{
			case 0:
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
		setContentView(R.layout.activity_set_intention);
		type = getIntent().getIntExtra("type", -1);
		if(type ==0)
			mIntentionItem = AotugeApplication.getInstance().mIntentionItem;
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
	public void onBack(View v) {
		// TODO Auto-generated method stub
		Intent resultIntent = new Intent();
		
		this.setResult(RESULT_CANCELED, resultIntent);
		
		super.onBack(v);
	}
	
//	city = (TextView)findViewById(R.id.tx_info2);
//	salary= (TextView)findViewById(R.id.tx_info3);
//	work_type= (TextView)findViewById(R.id.tx_info4);
//	
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
					Directions.clear();
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
					Citys.clear();//
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
			case CV:
			{
				if(resultCode==RESULT_OK)
				{
					String cv_title = data.getStringExtra("cv_title");
					cvID =	data.getStringExtra("cv_id");
					tx_info5.setText(cv_title);
				}	
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void initView()
	{
		initHeaderView();
		mEd_directions = (EditText)findViewById(R.id.ed_directions);
		
		mR_intention = (RelativeLayout)findViewById(R.id.r_intention);
		mR_intention.setOnClickListener(this);
		
		mR_city= (RelativeLayout)findViewById(R.id.r_city);
		mR_city.setOnClickListener(this);
		
		mR_salary= (RelativeLayout)findViewById(R.id.r_salary);
		mR_salary.setOnClickListener(this);
		
		mR_workType= (RelativeLayout)findViewById(R.id.r_workType);
		mR_workType.setOnClickListener(this);
		
		mR_cv = (RelativeLayout)findViewById(R.id.r_cv);
		mR_cv.setOnClickListener(this);
		
		mSave = (Button)findViewById(R.id.bn_save);
		mSave.setOnClickListener(this);
		intention = (TextView)findViewById(R.id.tx_info1);
		city = (TextView)findViewById(R.id.tx_info2);
		salary= (TextView)findViewById(R.id.tx_info3);
		work_type= (TextView)findViewById(R.id.tx_info4);
		tx_info5 = (TextView)findViewById(R.id.tx_info5);
		if(type == 0)
		initViewdata();
	}
	private void initHeaderView() {
		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		btnleft.setVisibility(View.VISIBLE);
		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText("求职方向");
		RelativeLayout btnright = (RelativeLayout) findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
			case R.id.r_intention:
			{
				Intent intent = new Intent(SetIntentionActivity.this,DirectionsActivity.class);
				startActivityForResult(intent, INTENTION);
			}
			break;
			case R.id.r_city:
			{
				Intent intent = new Intent(SetIntentionActivity.this,WorkPlaceActivity.class);
				intent.putExtra("type", 0);
				startActivityForResult(intent, CITY);
			}
			break;
			case R.id.r_salary:
			{
				Intent intent = new Intent(SetIntentionActivity.this,CustomPopupMenu.class);
				intent.putExtra("type", 1);
				startActivityForResult(intent, SALARY);
			}
			break;
			case R.id.r_workType:
			{
				Intent intent = new Intent(SetIntentionActivity.this,CustomPopupMenu.class);
				intent.putExtra("type", 4);
				startActivityForResult(intent, WORK_TYPE);
			}
			break;
			case R.id.r_cv:
			{
				Intent intent = new Intent(SetIntentionActivity.this,CVListActivity.class);
				startActivityForResult(intent, CV);
			}
			break;
			case R.id.bn_save:
			{
				String name = mEd_directions.getText().toString();
				if(name.length()==0)
				{
				  Toast.makeText(SetIntentionActivity.this, "求职方向名称不能为空", Toast.LENGTH_SHORT).show();
				  return;
				}
				
				if(Directions.size()==0)
				{
					 Toast.makeText(SetIntentionActivity.this, "请选择职位", Toast.LENGTH_SHORT).show();
					  return;
				}
				
				if(type ==0)
				{
					saveIntention(String.valueOf(mIntentionItem.id));
					
				}
				else
				saveIntention(null);
			}
			break;
		}
	}
	
	void saveIntention(String id)
	{
		if(!isSend)
		{
		isSend = true;
		String url = NetUrlManager.getAddIntention()+"?token="+AotugeApplication.getInstance().mAotuInfo.token;
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		url+="&id="+id;
		String name = mEd_directions.getText().toString();
		if(name.length()==0)
		{
		   params.put("name", "未命名");
		}
		else
			params.put("name", name);
		params.put("token", AotugeApplication.getInstance().mAotuInfo.token);
		for(int i = 0; i <Citys.size();i++)
		{
			String tab = "cities["+String.valueOf(i)+"]";
			params.put(tab, Citys.get(i));
		}
		
		for(int i = 0; i <Directions.size();i++)
		{
			String tab = "position["+String.valueOf(i)+"]";
			params.put(tab, Directions.get(i));
		}
		params.put("salary", salaryID);
		params.put("workType", workTypeID);
		if(!cvID.equals(""))
		params.put("cv_id", cvID);
		client.post(url,params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getAddintention(content);
				if(info.success)
				{
					showMessageDialog("提示", "提交成功", "确定", mKTADialongright,3);
				}
				else
				    showMessageDialog("提示", info.info, "确定", null,1);
				isSend = false;
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				isSend = false;
				showMessageDialog("提示", "服务器忙请稍后再试", "确定", null,0);
			}
		});
		}
		else
		{
			 Toast.makeText(SetIntentionActivity.this, "正在请求，请稍后", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void initViewdata()
	{
		mEd_directions.setText(mIntentionItem.name);
		String name = "";
		for(int i = 0; i < mIntentionItem.position.size();i++)
		{
			name += mIntentionItem.position.get(i).name;
			if(i!=mIntentionItem.position.size()-1)
			{
				name+=",";
			}
			Directions.add(String.valueOf(mIntentionItem.position.get(i).code));
		}
		String City= "";
		for(int i = 0; i < mIntentionItem.cities.size();i++)
		{
			City += mIntentionItem.cities.get(i).name;
			if(i!=mIntentionItem.cities.size()-1)
			{
				City+=",";
			}
			Citys.add(String.valueOf(mIntentionItem.cities.get(i).code));
		}
		intention.setText(name);
		city.setText(City);
		
		salaryID = String.valueOf(mIntentionItem.salary);
		salary.setText(mIntentionItem.salary_text);
		
		workTypeID = String.valueOf(mIntentionItem.work_type);
		work_type.setText(mIntentionItem.work_type_text);
		
		
	}

}
