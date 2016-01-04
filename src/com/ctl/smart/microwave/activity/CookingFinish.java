package com.ctl.smart.microwave.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import com.ctl.smart.microwave.utils.TextViewUtils;
import android.content.BroadcastReceiver;
import android.view.WindowManager;

public class CookingFinish extends AbActivity {
	private String name = "";
	private ServiceReceivier mServiceReceivier;
	@AbIocView(id = R.id.statu)
	TextView statu;
	@AbIocView(id = R.id.temperature)
	TextView temperature;
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
										CookingFinish.this, MainActivity.class);
								StartActivityUtil.clearActivity();
							}
						});
		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
			String content=bundle.getString("content");
			if (!TextUtils.isEmpty(content)) {
				statu.setText(content+"");
				remind.setVisibility(View.INVISIBLE);
			}
		}
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);
        mServiceReceivier = new ServiceReceivier();
   	    IntentFilter mIntentFilter = new IntentFilter();
   	    mIntentFilter.addAction("com.ctl.smatrt.microwave.stautsinfo");
        sendBroadcastToService();
		sendStopMicroToService();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//initSize();
	}
	public void initSize(){
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
     private void sendBroadcastToService()
     {
        Intent localIntent = new Intent("com.ctl.smatrt.microwave.temperaturereq");
        sendBroadcast(localIntent);
     }
   private void sendStopMicroToService()
   {
	  Intent localIntent = new Intent("com.ctl.smatrt.microwave.stopmicrowave");
	  sendBroadcast(localIntent);
   }
     private class ServiceReceivier extends BroadcastReceiver
     {
        private ServiceReceivier()
        {
        }
        public void onReceive(Context paramContext, Intent paramIntent)
        {
     	   String str1 = paramIntent.getAction();
		   Bundle bundle = null;
           if ("com.ctl.smatrt.microwave.stautsinfo".equals(str1))
           {
                bundle=paramIntent.getExtras();
				int tem=bundle.getInt("temperature");
				temperature.setText(Integer.toString(tem)+"\u2103");
           }
     	 }
      }
}
