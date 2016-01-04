package com.ctl.smart.microwave.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.utils.TextViewUtils;

public class ErrorActivity extends AbActivity {
	
	
	@AbIocView(id=R.id.title)
	private TextView title;
	
	@AbIocView(id=R.id.stitle)
	private TextView stitle;
	
	@AbIocView(id=R.id.scontent)
	private TextView scontent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.error);
		
		TextViewUtils.setTitleSize(title);
		TextViewUtils.setSixtyPercentSize(stitle);
		
		Bundle bundle=getIntent().getExtras();
		
		if (bundle!=null) {
			
			String code=bundle.getString("code");
			
			scontent.setText("-------------------"+code+"-------------------");
		}
		
	}

}
