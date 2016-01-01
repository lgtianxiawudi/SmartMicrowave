package com.ctl.smart.microwave.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ctl.smart.microwave.R;

public class HeadUtil {
	private TextView title;
	
	private TextView time;


	public HeadUtil(Activity context,boolean timeVisible) {
		title = (TextView) context.findViewById(R.id.title);
		if (timeVisible) {
			time=(TextView)context.findViewById(R.id.time);
			if (time!=null) {
				time.setVisibility(View.VISIBLE);
			}
			if (title!=null) {
				title.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
			}
		}
		
		TextViewUtils.setTitleSize(title);
	}
	public HeadUtil(Activity context) {
		title = (TextView) context.findViewById(R.id.title);
		time=(TextView)context.findViewById(R.id.time);
		if (time!=null) {
			time.setVisibility(View.GONE);
		}
		TextViewUtils.setTitleSize(title);
	}

	

	public HeadUtil setTitleName(String titleString){
		if (title!=null) {
			title.setText(titleString.replace("\n", ""));
		}
		return this;
	}

}
