package com.aotu.baseview;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

public class MonPickerDialog extends DatePickerDialog {
	private int mtype;
    public MonPickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth, int type) {  
        super(context, callBack, year, monthOfYear, dayOfMonth);  
        
        mtype = type;
        if(type == 1)
        {
        this.setTitle(year + "年"); 
        ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(1).setVisibility(View.GONE);
        }
        else
        {
        	this.setTitle(year + "年" + (monthOfYear + 1) + "月");
        }
        ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);  
    }  
  
    @Override  
    public void onDateChanged(DatePicker view, int year, int month, int day) {  
        super.onDateChanged(view, year, month, day);  
        
        if(mtype==1)
            this.setTitle(year + "年");
        else
        	 this.setTitle(year + "年" + (month + 1) + "月");
    }  
  
}  
