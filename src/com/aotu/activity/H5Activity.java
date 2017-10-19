package com.aotu.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.aotu.baseview.BaseActivity;
import com.auto.aotuge.R;






import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class H5Activity extends BaseActivity implements OnClickListener {
	private WebView contentWebView;
	
	private String url;
	private String title;
	private int mType;
	
	private String mID;
	
	private String mPostData="";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_readme);

		initData();
		initView();
	}

	private void initData() {
		Intent intent = getIntent();
		title = intent.getStringExtra("title");
		url = intent.getStringExtra("url");
	
		mType = getIntent().getIntExtra("type", 0);
		
		
		mPostData = intent.getStringExtra("post");
	}

	private void initView() {
		initHeaderView();
		contentWebView = (WebView) findViewById(R.id.webview);
		// 启用javascript
		contentWebView.getSettings().setJavaScriptEnabled(true);
//		contentWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//		contentWebView.getSettings().setLoadWithOverviewMode(true);
		
//		contentWebView.getSettings().setUseWideViewPort(true); 
//		contentWebView.getSettings().setLoadWithOverviewMode(true); 
//		contentWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 

		
		contentWebView.loadUrl(url);
		
		// 添加js交互接口类，并起别名 imagelistner
		contentWebView.addJavascriptInterface(this, "H5Activity");
		contentWebView.setWebViewClient(new MyWebViewClient());

	}

	private void initHeaderView() {

		RelativeLayout btnleft = (RelativeLayout) findViewById(R.id.back_layout);
		
			btnleft.setVisibility(View.VISIBLE);

		TextView titleText = (TextView) findViewById(R.id.tv_title_bar_text);
		titleText.setText(title);
		RelativeLayout btnright = (RelativeLayout)findViewById(R.id.right_layout);
		btnright.setVisibility(View.INVISIBLE);
		
	}

	public void onBack(View v) {
//		if (contentWebView.canGoBack()) {
//			contentWebView.goBack();// 返回前一个页面
//		} else
			finish();
	}

	// 监听
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {

			// view.getSettings().setJavaScriptEnabled(true);

			super.onPageFinished(view, url);
			// html加载完成之后，添加监听图片的点击js函数
			// addImageClickListner();
			

		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// view.getSettings().setJavaScriptEnabled(true);

			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
		

			super.onReceivedError(view, errorCode, description, failingUrl);

		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		default:
			break;
		}
	}

	private void sendDeleteOrderRequest() {
		
	}

	public void onPayCommpleyFunction() {
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK && contentWebView.canGoBack()) {
			contentWebView.goBack();// 返回前一个页面
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
