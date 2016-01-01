package com.ctl.smart.microwave.utils;

import android.content.res.Configuration;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;

import com.ctl.smart.microwave.views.DigitalClock;

public class TextViewUtils {
	public static int FONT=20;//20-30
	
	private static final Configuration mCurConfig = new Configuration();
	
	public static void initFont(){
//		try {
//            mCurConfig.updateFrom(ActivityManagerNative.getDefault().getConfiguration());
//        } catch (RemoteException e) {
//            //Log.w("ZJG", "Unable to retrieve font size");
//        }
//        
//        float fontScale = mCurConfig.fontScale;
//        
//        if(fontScale == 0.85f){
//        	FONT = 20;
//        }else if(fontScale == 1.0f){
//        	FONT = 24;
//        }else if(fontScale == 1.15f){
//        	FONT = 26;
//        }else{
//        	FONT = 28;
//        }
	}
	
	
	public static void setCommonSize(TextView view){
		if (view==null) {
			return ;
		}
		view.setTextSize(TypedValue.COMPLEX_UNIT_DIP,FONT );
	}
	public static void setCommonSize(Button view){
		if (view==null) {
			return ;
		}
		view.setTextSize(TypedValue.COMPLEX_UNIT_DIP,FONT );
	}
	public static void setTitleSize(TextView view){
		if (view==null) {
			return ;
		}
		initFont();
		view.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(FONT*1.2));
	}
	public static void setTitleSize(DigitalClock view){
		if (view==null) {
			return ;
		}
		initFont();
		view.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(FONT*2));
	}
	public static void setHalfSize(TextView view){
		if (view==null) {
			return ;
		}
		view.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(FONT*0.5));
	}
	public static void setEightPercentSize(TextView view){
		if (view==null) {
			return ;
		}
		view.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(FONT*0.8));
	}
	public static void setSixtyPercentSize(TextView view){
		if (view==null) {
			return ;
		}
		view.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(FONT*0.6));
	}
}
