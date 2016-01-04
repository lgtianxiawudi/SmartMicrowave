package com.ctl.smart.microwave.deodorize;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.activity.MainActivity;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import com.ctl.smart.microwave.utils.TextViewUtils;
import android.content.Intent;
import android.view.WindowManager;

public class DeodorizeFinishActivity extends AbActivity {
	private String name = "";
	@AbIocView(id = R.id.statu)
	TextView statu;
	@AbIocView(id = R.id.remind)
	TextView remind;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.cookingfinish);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this)
				.setback_mainListener().setBackListener(
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								StartActivityUtil.startActivityFinish(
										DeodorizeFinishActivity.this,
										MainActivity.class);
								StartActivityUtil.clearActivity();
							}
						});
		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
			String content = bundle.getString("content");
			statu.setText(name + "已完毕...");
		}
		remind.setVisibility(View.GONE);
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);

		sendStopMicroToService();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initSize();
	}
	private void initSize(){
		TextViewUtils.setCommonSize(statu);
		TextViewUtils.setHalfSize(remind);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		StartActivityUtil.startActivityFinish(this, MainActivity.class);
		StartActivityUtil.clearActivity();
	}
	private void sendStopMicroToService()
	{
	   Intent localIntent = new Intent("com.ctl.smatrt.microwave.stopmicrowave");
	   sendBroadcast(localIntent);
	}

	
}
