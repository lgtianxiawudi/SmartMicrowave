package com.ctl.smart.microwave.activity;

import java.util.Map;

import com.ab.activity.AbActivity;
import com.ab.util.AbToastUtil;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.utils.BitmapUtil;
import com.ctl.smart.microwave.utils.BottomUtilThree;
import com.ctl.smart.microwave.utils.BottomUtilThree.CurrentPercent;
import com.ctl.smart.microwave.utils.BottomUtilThree.onChangeStaueListener;
import com.ctl.smart.microwave.utils.DataInit;
import com.ctl.smart.microwave.utils.ExcelUtil;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.TextViewUtils;
import com.ctl.smart.microwave.utils.TimeInit;
import com.ctl.smart.microwave.views.RefreshProgress;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.ctl.smart.microwave.utils.BottomUtilThree.MyCount;

public class CookingActivity extends AbActivity implements 
		onChangeStaueListener, CurrentPercent {
	private String name = "";
	private String imageid = "";
	private String temperatureStr;
	private String contion="";
	private int time = 0;
	private String type = "";
	private int cookingstatus=-1;

	@AbIocView(id = R.id.staue)
	private TextView statu;
	@AbIocView(id = R.id.temperature)
	private TextView temperature;
	@AbIocView(id = R.id.cooking_logo)
	private RefreshProgress cooking_logo;
	private ServiceReceivier mServiceReceivier;

	private BottomUtilThree bottomUtilThree;
    private PowerManager.WakeLock mWakeLock;

	private int statusID = -1;
	
	private boolean needAddWater = false;
	@AbIocView(id=R.id.add_water)
	private TextView add_water;
	
	private String needwater;
	private String needtem;
	private String autoPauseTime;
	@AbIocView(id=R.id.fanmian)
	private LinearLayout fanmian;
	private boolean isJieDong=false;
	@AbIocView(id=R.id.fmtitle)
	private TextView fmtitle;
	@AbIocView(id=R.id.fmjj)
	private TextView fmjj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.cooking);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		acquireWakeLock();

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			name = bundle.getString("title");
			imageid = bundle.getString("imageid");
			String timeStr=bundle.getString("time");
			Log.e("lwxdebug", "CookingActivity timeStr"+timeStr);
			Log.e("lwxdebug", "CookingActivity imageid"+imageid);
			time =TimeInit.timeStrToInt(timeStr);
			temperatureStr = bundle.getString("temperature");

			
			contion=bundle.getString("contion");
			Log.e("lwxdebug", "CookingActivity contion"+contion);

			Log.e("lwxdebug", "CookingActivity temperatureStr"+temperatureStr);

					Log.e("lwxdebug", "CookingActivity name"+name);
			
			
			statusID = bundle.getInt("status", -1);
			needwater=bundle.getString("needwater");
			autoPauseTime=bundle.getString("autoPauseTime");
			isJieDong=bundle.getBoolean("isJieDong");

			needtem=bundle.getString("constanttem");
			Log.e("lwxdebug", "CookingActivity autoPauseTime"+autoPauseTime);

			
			if (statusID == -1) {
				statusID = R.string.cook_starting;
			}
			
	        
			
			initTimeAndTem(imageid);


		    if (!TextUtils.isEmpty(temperatureStr)) {
				temperature.setText(temperatureStr.replace("\n", ""));

				if(needtem!=null)
				{
    				if(needtem.equals("1")==true)
    				{
    				   temperature.setVisibility(View.VISIBLE);
    				}
    				else
    				{
                        temperature.setVisibility(View.INVISIBLE);
    				}
				}
				
				//temperature.setVisibility(View.INVISIBLE);
				
			}
			
			if (time == 0) {
				time = 15 * 1000;
			}
			
			if (name.indexOf("蒸汽") != -1||"蒸汽".equals(needwater)) {
				
				needAddWater=true;
			}

		}
        /*
	   if(imageid.equals("quickmicro")==true)	
	   	{
	   	    type = type + 4;
            bottomUtilThree = new BottomUtilThree(this, bundle).setBackListener()
				.setSaveListener().setPlayListener(time, CookingFinish.class).setIsNeedAddWater(needAddWater).setAutoPlayListener( time,CookingFinish.class).setAutoPauseTime(autoPauseTime).setIsJieDong(isJieDong);
	   	}
	    else*/
			
		bottomUtilThree = new BottomUtilThree(this, bundle).setBackListener()
				.setSaveListener().setPlayListener(time, CookingFinish.class).setIsNeedAddWater(needAddWater).setAutoPauseTime(autoPauseTime).setIsJieDong(isJieDong);


		
		bottomUtilThree.setChangeStaue(this);
		bottomUtilThree.setCurrentPercent(this);

		if(imageid.equals("quickmicro")==true)
		{
           //onchangeStaue(BottomUtilThree.RUN);
           //bottomUtilThree.start();
           type = type + 1;
           bottomUtilThree.setAutoPlayListener( time,CookingFinish.class);
		}
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);


		mServiceReceivier = new ServiceReceivier();
	    IntentFilter mIntentFilter = new IntentFilter();
	    mIntentFilter.addAction("com.ctl.smatrt.microwave.dooropen");
		
		this.registerReceiver(mServiceReceivier, mIntentFilter);


		Log.e("lwxdebug", "CookingActivity");

		try {
			System.out.println(imageid);
			cooking_logo.setCirCleBitmap(BitmapUtil.readBitMap(this,
					DataInit.getDataByKey(imageid)));
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextViewUtils.setTitleSize(fmtitle);
		TextViewUtils.setSixtyPercentSize(fmjj);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// initSize();
	}

	public void initSize() {
		TextViewUtils.setTitleSize(temperature);
		TextViewUtils.setCommonSize(statu);
	}

	@Override
	public void onchangeStaue(int status) {
		// TODO Auto-generated method stub
		Log.e("lwxdebug", " onchangeStaue status="+status);

		
		cookingstatus=status;
		switch (status) {
		case BottomUtilThree.PAUSE: {
			statu.setText(R.string.cook_stopping);
			cooking_logo.stop();
		    sendLedToService("7RR\n");
			sendBroadcastPauseToService(type,time,temperatureStr);
		}
			break;
		case BottomUtilThree.RUN: {
			add_water.setVisibility(View.INVISIBLE);
			fanmian.setVisibility(View.INVISIBLE);
			statu.setText(statusID);
			cooking_logo.start();
			//onRun();
			sendLedToService("7BR\n");
		    sendBroadcastToService(type,time,temperatureStr);
		}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (bottomUtilThree != null) {
			bottomUtilThree.setOnDestory();
		}
         this.unregisterReceiver(mServiceReceivier);
		 mServiceReceivier = null;
        releaseWakeLock();
		
	}

	private void initTimeAndTem(String name) {
		Map<String, String> map = ExcelUtil.getDetailByKey(name);
		String typeSt = name;
		if (map != null) {
			typeSt = map.get("3");
		}

		if (typeSt.indexOf("微波") != -1) {
			type = type + 1;
		}
		if (typeSt.indexOf("烧烤") != -1) {
			type = type + 2;
		}
		if (typeSt.indexOf("蒸汽") != -1) {
			type = type + 3;
		}
		if (typeSt.indexOf("热风对流") != -1) {
			type = type + 4;
		}
      // typeSt = map.get("3");
       Log.e("lwxdebug", " typeSt="+typeSt);

		
	}


	
	private void initTem(String name) {
		String[] names = name.split(getString(R.string.point));
		String result = DataInit.getDataByKey(names[names.length - 1]);

	     Log.e("lwxdebug", " name="+name);

		 Log.e("lwxdebug", " result="+result);
		if (result != null) {
			String[] results = result.split("\\|");
			if (results.length == 3) {
				temperatureStr = results[1];
			}
			if (TextUtils.isEmpty(temperatureStr)
					|| temperatureStr.indexOf("?") == -1) {
				temperatureStr = "";
			}
			
		}

        
	    
		
		Log.e("lwxdebug", " temperatureStr="+temperatureStr);

	}

	

	

	private void onRun() {
		AbToastUtil.showToast(CookingActivity.this, type + ":" + time + ":"
				+ temperatureStr+":"+contion);

	}
	@Override
	public void getCurrentPercen(long millisUntilFinished, int percent) {
		// TODO Auto-generated method stub
		if (millisUntilFinished==2*60*1000&&needAddWater) {
				add_water.setVisibility(View.VISIBLE);
		}
		if (millisUntilFinished==time/3&&isJieDong) {
			fanmian.setVisibility(View.VISIBLE);
		}
	}
	
	private class MyCountDowntimer extends CountDownTimer{

		public MyCountDowntimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
			add_water.setVisibility(View.VISIBLE);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			add_water.setVisibility(View.GONE);
		}
		
	}
    private void sendBroadcastToService(String type , int time , String temperatureStr )
    {
       Intent localIntent = new Intent("com.ctl.smatrt.microwave.SETMACROWAVEON");
	   Bundle sendbudle= new Bundle();
	   Log.e("lwxdebug", " type="+type);
	      Log.e("lwxdebug", " time="+time);
		  	  Log.e("lwxdebug", " temperatureStr="+temperatureStr);
	   sendbudle.putString("type",type);
	   sendbudle.putInt("time",time);
	   sendbudle.putString("temperatureStr",temperatureStr);
	   localIntent.putExtras(sendbudle);
       sendBroadcast(localIntent);
    }

    private void sendBroadcastPauseToService(String type , int time , String temperatureStr )
    {
       Intent localIntent = new Intent("com.ctl.smatrt.microwave.SETMACROWAVEPAUSE");
	   Bundle sendbudle= new Bundle();
	   Log.e("lwxdebug", " type="+type);
	      Log.e("lwxdebug", " time="+time);
		  	  Log.e("lwxdebug", " temperatureStr="+temperatureStr);
	   sendbudle.putString("type",type);
	   sendbudle.putInt("time",time);
	   sendbudle.putString("temperatureStr",temperatureStr);
	   localIntent.putExtras(sendbudle);
       sendBroadcast(localIntent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

    Log.e("lwxdebug", "onKeyDown keyCode="+keyCode);
    
	
	switch(keyCode)
	{
      case KeyEvent.KEYCODE_1: //zengqi

	  
	  break;

      case KeyEvent.KEYCODE_2://weibobianp
   
	 
	  break;

	  case KeyEvent.KEYCODE_3:
	  	
	  break;

      case KeyEvent.KEYCODE_6:
	

	  break;

	  case KeyEvent.KEYCODE_5:
	   if(cookingstatus==BottomUtilThree.RUN)
	   	{
             bottomUtilThree.pause();
			 sendLedToService("4F5B\n");
	   	}
	    else
	    {
             bottomUtilThree.back();
			 sendLedToService("4O5O\n");
	    }
	
	  break;

      case KeyEvent.KEYCODE_4:
        {

	
	 
		       Log.e("lwxdebug", "keycode4 ="+type);

			   
		       Log.e("lwxdebug", "keycode41 ="+imageid.equals("quickmicro"));

			if(imageid.equals("quickmicro"))
			{
			   if(cookingstatus==BottomUtilThree.RUN)  
			   	{
                   bottomUtilThree.addTime(30*1000);
			   	}
			    else
			    {
                   bottomUtilThree.start();
			    }
			}
            else
            {
                if(time>0)
                {
   				   if(cookingstatus!=BottomUtilThree.RUN)
   				   {
                      bottomUtilThree.start();
   				   }
                }
            }
			
			//count.addTime(30*1000);
			//count.start();
			sendLedToService("4B5F\n");

			
      	}

	  break;
	  
	}
	
   // sendledBroadcastToService(keyCode,event);
	
	//event.startTracking();
	return super.onKeyDown(keyCode, event);
	
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
		   Log.e("lwxdebug", "ServiceReceivier getAction="+str1);

		   
           if ("com.ctl.smatrt.microwave.dooropen".equals(str1))
           {
              bottomUtilThree.pause(); 
           }
     	 }
      }
      private void releaseWakeLock(){
      	if(mWakeLock != null && mWakeLock.isHeld()){
      		mWakeLock.release();
      		mWakeLock = null;
      	}
      }
      
      private void acquireWakeLock(){
      	if(mWakeLock == null){
      		PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
      		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, this.getClass().getName());
      		mWakeLock.setReferenceCounted(false);
      	}
      	if(!mWakeLock.isHeld()){
      		mWakeLock.acquire();
      	}
      }

    private void sendLedToService(String code)
    {
    	Intent localIntent = new Intent("com.ctl.smatrt.microwave.setled");
       
    	Bundle sendbudle= new Bundle();
   
    	
    	sendbudle.putString("led",code);
    	localIntent.putExtras(sendbudle);
    	sendBroadcast(localIntent);
    }

	
}
