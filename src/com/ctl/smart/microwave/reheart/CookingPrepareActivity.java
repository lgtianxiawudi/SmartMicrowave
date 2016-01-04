package com.ctl.smart.microwave.reheart;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.utils.BottomUtilOne;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import com.ctl.smart.microwave.utils.StringUtil;
import com.ctl.smart.microwave.utils.TextViewUtils;
import android.view.WindowManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.ctl.smart.microwave.utils.TimeInit;
import android.util.Log;

public class CookingPrepareActivity extends AbActivity {
	String name="";
	@AbIocView(id=R.id.secondtitle)
	private TextView secondtitle;
	@AbIocView(id=R.id.deadtime)
	private TextView deadtime;
	
	private MyCountDownTimer timer;
	
	private BottomUtilOne bottomUtilThree;
    private String temperatureStr;

	private int time = 0;
	private Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.deodorize);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		bundle=getIntent().getExtras();
		name=bundle.getString("title");
		temperatureStr = bundle.getString("temperature");

		bottomUtilThree = new BottomUtilOne(this)
		.setBackListener();
		HeadUtil headUtil=new HeadUtil(this,true).setTitleName(name);
		secondtitle.setText(R.string.rehart_starting);

	    sendReheatBroadcastToService("4",0,temperatureStr);

		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		timer=new MyCountDownTimer(5*60*1000, 1000);
		timer.start();
		initSize();
	}
	private void initSize(){
		TextViewUtils.setCommonSize(secondtitle);
		TextViewUtils.setCommonSize(deadtime);
	}
	
	private class MyCountDownTimer  extends CountDownTimer{

		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			deadtime.setText(StringUtil.getSpannStringAbountSize(millisUntilFinished));
		}
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			StartActivityUtil.startActivityForResult(CookingPrepareActivity.this, PreperHearFinish.class,bundle);
		}
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (timer!=null) {
			timer.cancel();
		}
		if (bottomUtilThree!=null) {
			bottomUtilThree.setOnDestory();
		}
	}

    protected void onStop()
   {
       Log.e("lwxdebug", "CookingPrepareActivity onStop");
	   sendStopMicroToService();
       super.onStop();
   }


    private void sendReheatBroadcastToService(String type , int time , String temperatureStr )
    {
       Intent localIntent = new Intent("com.ctl.smatrt.microwave.SETMACROWAVEON");
	   Bundle sendbudle= new Bundle();
	   Log.e("lwxdebug", " type="+type);
	   Log.e("lwxdebug", " time="+time);
	   Log.e("lwxdebug", " temperatureStr="+temperatureStr);
	   sendbudle.putString("type","4");
	   sendbudle.putInt("time",time);
	   sendbudle.putString("temperatureStr",temperatureStr);
	   localIntent.putExtras(sendbudle);
       sendBroadcast(localIntent);
    }


   private void sendStopMicroToService()
   {
	  Intent localIntent = new Intent("com.ctl.smatrt.microwave.stopmicrowave");
	  sendBroadcast(localIntent);
   }
	
}
