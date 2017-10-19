package com.aotu.baseview;




import com.auto.aotuge.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BaseActivity extends Activity{

	private AlertDialog myDialog;
	private AlertDialog messageDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
	
	public void onBack(View v) {
		finish();
	}
	
	
	
	public interface KTAlertDialogOnClickListener {
		public void onClick(AlertDialog dialog, View button);
	}
	public void showMessagePayMethodDialog(String title, String message,
			String leftButtonStr, String rightButtonStr,
			final KTAlertDialogOnClickListener leftListener,
			final KTAlertDialogOnClickListener rightListener,
			boolean onlyOneButton) {
		if (myDialog != null)
			myDialog.dismiss();
		myDialog = null;
		myDialog = new AlertDialog.Builder(this).create();
		myDialog.setCancelable(false);
		myDialog.show();

		myDialog.getWindow().setContentView(R.layout.alertdialog);
		TextView dialogtitle = (TextView) myDialog.getWindow().findViewById(
				R.id.alertdialog_title);
		TextView dialogmessage = (TextView) myDialog.getWindow().findViewById(
				R.id.alertdialog_message);
		Button leftButton = (Button) myDialog.getWindow().findViewById(
				R.id.alertdialog_PositiveButton);
		Button rightButton = (Button) myDialog.getWindow().findViewById(
				R.id.alertdialog_NegativeButton);
	

		dialogtitle.setText(title);
		dialogmessage.setText(message);
		leftButton.setText(leftButtonStr);
		rightButton.setText(rightButtonStr);

		myDialog.getWindow().findViewById(R.id.alertdialog_PositiveButton)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (leftListener != null)
							leftListener.onClick(myDialog, v);
						myDialog.dismiss();
					}
				});

		rightButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rightListener != null)
					rightListener.onClick(myDialog, v);
				myDialog.dismiss();
			}
		});

		if (onlyOneButton) {
			rightButton.setVisibility(View.GONE);
		
			myDialog.setCancelable(false);
		}

	}
	
	public void showMessageDialog(String title, String message,String rightButtonStr,
			final KTAlertDialogOnClickListener rightListener,int state) {
		if (messageDialog != null)
			messageDialog.dismiss();
		messageDialog = null;
		messageDialog = new AlertDialog.Builder(this).create();
		
		messageDialog.setCancelable(false);
		messageDialog.show();
		
		messageDialog.getWindow().setContentView(R.layout.message_dialog);
		TextView dialogtitle = (TextView) messageDialog.getWindow().findViewById(
				R.id.alertdialog_title);
		TextView dialogmessage = (TextView) messageDialog.getWindow().findViewById(
				R.id.alertdialog_message);
		Button rightButton = (Button) messageDialog.getWindow().findViewById(
				R.id.alertdialog_NegativeButton);
		dialogtitle.setText(title);
		dialogmessage.setText(message);
		rightButton.setText(rightButtonStr);
		rightButton.setTag(String.valueOf(state));
		rightButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rightListener != null)
					rightListener.onClick(messageDialog, v);
				messageDialog.dismiss();
			}
		});
		
		
	}
	
	
	public void showNotitleDialog(String message,
			String leftButtonStr, String rightButtonStr,
			final KTAlertDialogOnClickListener leftListener,
			final KTAlertDialogOnClickListener rightListener,String index) {
		if (myDialog != null)
			myDialog.dismiss();
		myDialog = null;
		myDialog = new AlertDialog.Builder(this).create();
		myDialog.setCancelable(false);
		myDialog.show();
		myDialog.getWindow().setContentView(R.layout.dialog_no_title);
		TextView dialogmessage = (TextView) myDialog.getWindow().findViewById(
				R.id.alertdialog_message);
		Button leftButton = (Button) myDialog.getWindow().findViewById(
				R.id.alertdialog_PositiveButton);
		Button rightButton = (Button) myDialog.getWindow().findViewById(
				R.id.alertdialog_NegativeButton);
		dialogmessage.setText(message);
		leftButton.setText(leftButtonStr);
		leftButton.setTag(index);
		rightButton.setText(rightButtonStr);
		rightButton.setTag(index);
		myDialog.getWindow().findViewById(R.id.alertdialog_PositiveButton)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (leftListener != null)
							leftListener.onClick(myDialog, v);
						myDialog.dismiss();
					}
				});

		rightButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rightListener != null)
					rightListener.onClick(myDialog, v);
				myDialog.dismiss();
			}
		});
	}
}
