package com.ctl.smart.microwave.service;

import android.os.SystemClock;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.ctl.smart.microwave.protocol.ProtocolConsole;
import com.ctl.smart.microwave.activity.Application;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.util.Log;
import android.content.ComponentName;
import com.ctl.smart.microwave.serialport.SerialPort;
import android.content.Intent;
import java.security.InvalidParameterException;
import android.os.IBinder;
import android.os.Binder;
import android.os.Bundle;
import android.content.Intent;
import java.util.TimerTask;
import java.util.Timer;
import android.os.Handler;
import java.util.Arrays;
import android.os.PowerManager;


public class MicroWaveService extends Service
{

	private ServiceReceivier mServiceReceivier;
	private ProtocolConsole pConsole = null;
	protected Application mApplication;
	private InputStream mInputStream;
	protected OutputStream mOutputStream;
	private ReadThread mReadThread;
	protected SerialPort mSerialPort;
    private boolean mIsServiceInit = false;
	private final IBinder mBinder = new ServiceBinder();
	private Bundle mbuddle=null;
	private static final String ACTION_MSG_MICORWAVE_STATUS_INFO="com.ctl.smatrt.microwave.stautsinfo";
	private int tempe=0;
	private boolean temreach=false;
	private String type =null;
	private int time=0;
	private String temperature=null;
    private Timer mTimer = new Timer();
	private Timer mwTimer = new Timer();
    private boolean debug=false;
	private int TEST_TEP=0;
	private int ZFP_TEP=120;
    private int water_time=0;
	private boolean dryout=false;
	private static final int TEMP_INTERAL = 5; 
	private static final int RUN_TIME = 40;
    private int DRYFLAG=1;
    int elapseTime =0;// System.currentTimeMillis();
    long previewTime=0;
	private int dry_pre=0; 
	private PowerManager.WakeLock mWakeLock;
	private static  int door_status=1;
	private static boolean init=false;
	private static boolean steamopen=false;
	
	Handler mHandler = new Handler();
	public void initService( )
	{
	   mIsServiceInit=true;
	   pConsole = new ProtocolConsole(mOutputStream);
	}

	public IBinder onBind(Intent paramIntent)
    {
       return this.mBinder;
    }
    public void onCreate()
	{
   	  super.onCreate();
   	  /*
   	  HandlerThread localHandlerThread = new HandlerThread("TwoWayRadioServerThread");
   	  localHandlerThread.start();
   	  this.mServiceHandler = new ServiceHandler(localHandlerThread.getLooper());*/


       initSerialPort();
	  pConsole = new ProtocolConsole(mOutputStream);

	   Log.e("lwxdebug", "mOutputStream="+mOutputStream); 

   	  mServiceReceivier = new ServiceReceivier();
   	  IntentFilter mIntentFilter = new IntentFilter();
   	  mIntentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
      mIntentFilter.addAction("com.ctl.smatrt.microwave.SETMACROWAVEON");
      mIntentFilter.addAction("com.ctl.smatrt.microwave.temperaturereq");
	  mIntentFilter.addAction("com.ctl.smatrt.microwave.stopmicrowave");
	  mIntentFilter.addAction("com.ctl.smatrt.microwave.setled");
      mIntentFilter.addAction("com.ctl.smatrt.microwave.SETMACROWAVEPAUSE");

	  acquireWakeLock();

		init=true;

	

	    Log.e("lwxdebug", "mOutputStream1="+mOutputStream); 
   	  registerReceiver(mServiceReceivier, mIntentFilter);
	  
      setTemperatureTimer();
	  
	}


    public void onDestroy()
    {

       Log.e("lwxdebug", "MicroWaveService onDestroy i=");

       if (this.mServiceReceivier != null)
       {
          unregisterReceiver(this.mServiceReceivier);
          this.mServiceReceivier = null;
       }


      if (mReadThread != null)
      {
        
         mReadThread.interrupt();
      }
	
      mApplication.closeSerialPort();
      mSerialPort = null;
      releaseWakeLock();
	  
      super.onDestroy();

    }

	 private class ServiceReceivier extends BroadcastReceiver
	 {
		private ServiceReceivier()
		{
		}
	 
		public void onReceive(Context paramContext, Intent paramIntent)
		{
	
			String str1 = paramIntent.getAction();
			Log.e("lwxdebug", "ServiceReceivier getAction="+str1);
		
			if ("com.ctl.smatrt.microwave.SETMACROWAVEON".equals(str1))
			 {
			     mbuddle=paramIntent.getExtras();
				 type=mbuddle.getString("type");
				 temperature=mbuddle.getString("temperatureStr");
				 time=mbuddle.getInt("time");
				 
                 tempe =pConsole.gettemperature(temperature,type);
				 if(pConsole.issteam(type))
				 {
				    pConsole.work_start();
					steamopen=true;
                    pConsole.sewaterpumpon(type,false,true);
				 }
		          else
		         {
		           pConsole.setmicrowaveon(type,time,tempe);
		         }

				 

				 
		         setTemperatureTimer(); 
				 Log.e("lwxdebug", "tempe getAction="+tempe);

				 
			 }
			 else if("com.ctl.smatrt.microwave.temperaturereq".equals(str1))
			 {
                  pConsole.getmicrowavestatus();
			 }
			 else if("com.ctl.smatrt.microwave.stopmicrowave".equals(str1))
			 {  
         			    /*
                          if (mTimer != null) {
                             mTimer.cancel();
         					mTimer=null;
                          }*/

				 if (mwTimer != null) {
                      mwTimer.cancel();
                  }
				 
                 pConsole.setmicrowaveoff();
				if(pConsole.issteam(type)==true)
				   {
				      steamopen=false;
				      pConsole.work_end( type);

					  waterpump(1000);
				   }
			 }
			 else if("com.ctl.smatrt.microwave.setled".equals(str1))
			 {
			 
                 mbuddle=paramIntent.getExtras();
		         Log.e("lwxdebug", "LED ="+mbuddle.getString("led"));
                 pConsole.setKeyLed(mbuddle.getString("led"));
			 }
		     else if("com.ctl.smatrt.microwave.SETMACROWAVEPAUSE".equals(str1))
			  {
			       mbuddle=paramIntent.getExtras();
				   type=mbuddle.getString("type");
				   temperature=mbuddle.getString("temperatureStr");
				   time=mbuddle.getInt("time");
                   tempe =pConsole.gettemperature(temperature,type);
		          
				   pConsole.setmicrowavepause(type);

				  	if(pConsole.issteam(type)==true)
				   {
				       steamopen=false;
				       //pConsole.work_pause( type);
				   }
			

                             /*
         				    if (mTimer != null) {
                               mTimer.cancel();
         					  mTimer=null;
                             }*/
					

				  if (mwTimer != null) {
                      mwTimer.cancel();
                    }
			  }

			 
		  }
	 
	  }
     
	 public boolean isServiceInit()
	 {
	   return mIsServiceInit;
	 }

	 void initSerialPort()
	 {
         this.mApplication = ((Application)getApplication());
         try
         {
            mSerialPort = this.mApplication.getSerialPort();
            mOutputStream = this.mSerialPort.getOutputStream();
            mInputStream = this.mSerialPort.getInputStream();
			
			Log.e("lwxdebug", "creat ReadThread1 ");

           mReadThread = new ReadThread();
           mReadThread.start();

         }
         catch (SecurityException localSecurityException)
         {
            //DisplayError(R.string.error_security);
            	//pConsole = new ProtocolConsole(null);  
             Log.e("lwxdebug", "localSecurityException ");
         }
         catch (IOException localIOException)
         {
            // DisplayError(R.string.error_unknown);
              Log.e("lwxdebug", "localIOException"+localIOException);
         }
          catch (InvalidParameterException localInvalidParameterException)
         {
           //DisplayError(R.string.error_configuration);
             Log.e("lwxdebug", "localInvalidParameterException");
         }

	 }

	private boolean sleep(long paramLong)
	{
	  try
	  {
		Thread.sleep(paramLong);
		return true;
	  }
	  catch (InterruptedException localInterruptedException)
	  {
		localInterruptedException.printStackTrace();
	  }
	  return false;
	}



	int j=0;
    private class ReadThread extends Thread
    {

	  byte[] arrayOfByte = new byte[100];
	  
	  byte[]temtyte=new byte[30];

      private ReadThread()
      {
        Log.e("lwxdebug", "ReadThread1 gouzhao");
      }

      public void run()
      {
         super.run();
		 Log.e("lwxdebug", "ReadThread1 run");
		

		
         while (!isInterrupted())
      	 {
		

            try
            {
             
			  // Log.e("lwxdebug", "ReadThread mInputStream="+mInputStream);
              if (mInputStream != null)
              {
              
			    Log.e("lwxdebug", "ReadThread available()="+mInputStream.available()); 


				/*
    		    if((mInputStream.available()>0) == false)
				{

                     try
     	             {
     		            Thread.sleep(50L);
     		
     	             }
     	             catch (InterruptedException localInterruptedException)
     	             {
     		            localInterruptedException.printStackTrace();
     	             }

				
                     continue;
                }
				else
                {
                     try
     	             {
     		            Thread.sleep(30L);
     		
     	             }
     	             catch (InterruptedException localInterruptedException)
     	             {
     		            localInterruptedException.printStackTrace();
     	             }
                 }*/

				
                
				 Arrays.fill(arrayOfByte, (byte)0);
          		 int i = mInputStream.read(arrayOfByte);	


		   
		
	
  				if (i>0)
  				{
                   onDataReceived(arrayOfByte, i);
			
  				}

                   
				  
              }
             
            }
            catch (IOException localIOException)
            {
                 Log.e("lwxdebug", "ReadThread error");
                 localIOException.printStackTrace();
            }
        }
      }
   }

   void onDataReceived(byte[] paramArrayOfByte, int paramInt)
   {


       if(paramArrayOfByte!=null)
       {
		   Log.e("lwxdebug", "onDataReceived paramArrayOfByte[0]="+paramArrayOfByte[0]);

	        Log.e("lwxdebug", "onDataReceived paramArrayOfByte[1]="+paramArrayOfByte[1]);
          if(paramArrayOfByte[0]==0x03&&paramArrayOfByte[1]==0x0B)
          {
             int door=(int)paramArrayOfByte[2]; // door status 0x00,0x01
			 
			 int zfpgl =pConsole.bytesToInt(paramArrayOfByte,3);//zfp gl 0-1000
			 
			 int lqwd =pConsole.bytesToInt(paramArrayOfByte,5);//lqwd 0-1023
			 int sbdl =pConsole.bytesToInt(paramArrayOfByte,7);//sbdl 0-1023

			 
			 //int zfpwd =pConsole.bytesToInt(paramArrayOfByte,9); //zfpwd 0-1023
			 
			 int zfpwd=(int)paramArrayOfByte[10]; // door status 0x00,0x01
			 
			 
			 int alert=(int)paramArrayOfByte[11];
			 int infra_temp =(int)paramArrayOfByte[12]; //alert
			 int check_temp=0;

			 Log.e("lwxdebug", "onDataReceived door="+ door+" zfpgl="+zfpgl+" lqwd="+lqwd+" sbdl="+sbdl+" zfpwd="+zfpwd
			 	+" alert="+alert+" infra_temp="+infra_temp);

             
			 Log.e("lwxdebug", "type=="+type );
		
             

				 Log.e("lwxdebug", "door status=="+door_status+" init="+init);
			      
                  if(door==1)
                  {

					  if(door_status==0||init)
					  {
					     init=false;
                         pConsole.opendoor(type,true);
     				     sendBroadcastdooropen();
						 
					  }
					  door_status=door;
					  return ;
                             /*
             				 if (mTimer != null) {
                                 mTimer.cancel();
             					mTimer=null;
                              }*/
     				 
                  }
     			 else
     			 {
                     if(door_status==1||init)
                     {
                        init =false;
                        pConsole.opendoor(type,false);
					    door_status=door;
						return ;
                     }
					 
     				 //sendBroadcastdooropen();        
     			 }
			      door_status=door;

			 

		
             if(debug)
             {
                zfpwd=DRYFLAG;
             }
		
         
			 if(alert!=0)
			 {
               pConsole.setKeyLed("7RF");
			 }
			  /*
             if(pConsole.issteam(type))
             {
                if((zfpwd==1))
                {   
                    //ZFP_TEP=ZFP_TEP-20;
                    if(zfpwd!=dry_pre)
                    {
                      dry_pre=zfpwd;
                      Log.e("lwxdebug", "water dry" );
                      waterpumpafterdry(6*1000);
                    }
					
                }
				else
				{
				   if(zfpwd!=dry_pre)
				   {
				     dry_pre=zfpwd;
				     Log.e("lwxdebug", "water not dry" );
				     pConsole.sewaterpumpon(type,false,true);
                     setwaterTimer(1,RUN_TIME);
				   }
				}
             }*/
		
			 //check_temp=100;	
			 
             if(debug)
             {
               if(temreach)
               {
                  TEST_TEP=TEST_TEP-3;
               }
			   else
			   {
                  TEST_TEP=TEST_TEP+3;
			   }
			     check_temp=TEST_TEP;
             }
			 else
			 {
                check_temp=lqwd;
			 }
          
            

   


             if(pConsole.isrefeng(type)==true)
             {          
                pConsole.TempertureControlStage(tempe,lqwd,type);
             }
             else if(pConsole.issteam(type)==true&&steamopen)
    		 {
                        /*
           			 if((check_temp>=tempe-TEMP_INTERAL)&&(temreach==false)) 
           			 {
           			     temreach=true;
       			
           			    if(mwTimer!=null)
           				{
           				   mwTimer.cancel();
               			    elapseTime=(int)(System.currentTimeMillis()-previewTime)/1000;
       							
           				}
       					// Log.e("lwxdebug", "water not dry" );
                           pConsole.adjusttemperaturepoff(type);
       					 
           			 }
           			 else if((check_temp<=(tempe-2*TEMP_INTERAL))&&(temreach==true))
           			 {
           			     temreach=false;
                            pConsole.setmicrowaveon(type,time,tempe);
       					 Log.e("lwxdebug", "tep low restart elapseTime="+elapseTime );
       	
       					 if(water_time<5)
                            setwaterTimer((RUN_TIME-elapseTime),RUN_TIME);
       				
           			 }*/

				 
				pConsole.steam_temperature_control(tempe,lqwd,type,zfpwd,sbdl);	 
			 }


			// Log.e("lwxdebug", "mInputStream ="+ ACTION_MSG_MICORWAVE_STATUS_INFO);
             /*
             Intent sendintent=new Intent(ACTION_MSG_MICORWAVE_STATUS_INFO);
			 Bundle sendbundle=new Bundle();
			 sendbundle.putInt("door",door);
			 sendbundle.putInt("error",alert);	
			 if(debug) 
			 {
			   sendbundle.putInt("temp1",TEST_TEP);	
			   sendbundle.putInt("temp2",TEST_TEP);	
			 }
			 else
			 {
                sendbundle.putInt("temp1",lqwd);	
			    sendbundle.putInt("temp2",lqwd);	
			 }
			 sendbundle.putInt("dam",lqwd);	
			 sendbundle.putInt("infra_temp",infra_temp);	
			 sendintent.putExtras(sendbundle);
			 sendBroadcast(sendintent);*/

			 
          }
       }
   }

   public class ServiceBinder extends Binder
   {
     public ServiceBinder()
     {
     }
   
     public MicroWaveService getService()
     {
      	 return MicroWaveService.this;
     }
   }

  private void setTemperatureTimer() {
	  
	  if (mTimer != null) {
		 mTimer.cancel();
		 mTimer=null;
	  }
        mTimer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
	          Intent localIntent = new Intent("com.ctl.smatrt.microwave.temperaturereq");
	          sendBroadcast(localIntent);
            }
        };


		
        mTimer.schedule(task, 1000,1000);
  
    }

   
   private final Runnable waterpumpRuntime = new Runnable() {
   	@Override
   	public void run() {

	        Log.e("lwxdebug", "waterpumpRuntime" );
          pConsole.setmicrowaveoff();
		     
   	}
   };



	  private void waterpump(long time)
	  {
	
			mHandler.postDelayed(waterpumpRuntime,time);
	  
	  }
	  


	  
	  
	  private void waterpumpwithzq(long time)
	  {
				  Log.e("lwxdebug", "waterpumpwithzq" );
			pConsole.sewaterpumpon(type,true,false);
			mHandler.postDelayed(waterpumpafterdryRuntime,time);
	  
	  }


   

	  private final Runnable waterpumpafterdryRuntime = new Runnable() {
	   @Override
	   public void run() {
             dryout=false;
			 Log.e("lwxdebug", "waterpumpafterdryRuntime" );
			 pConsole.sewaterpumpon(type,false,true);
			 DRYFLAG=0;
			 if (mwTimer != null) {
                  mwTimer.cancel();
             } 	
	   }
   };



   private void waterpumpafterdry(long time)
   {
            Log.e("lwxdebug", "waterpumpafterdry" );
         pConsole.sewaterpumpon(type,true,true);
		 dryout=true;
		 setwaterTimer(1,RUN_TIME);
		 water_time=0;

   }


   




   
   private void setwaterTimer(int first,int sec) {
   	      Log.e("lwxdebug", "setwaterTimer first="+first+"water_time="+water_time );
		 mwTimer = new Timer();
		 TimerTask task = new TimerTask() {
			 public void run() {
               
			   Log.e("lwxdebug", "water 111 dryout"+dryout+"water_time="+water_time );
			   if(dryout==true)
			   	{
                   waterpumpwithzq(6*1000);
			   	}
			    else
			    {
			      if(water_time<5)
			      {
		             waterpump(3*1000);
			      }
				  else
				  {
				     DRYFLAG=1;
					 water_time=0;
				     if (mTimer != null) {
                        mTimer.cancel();
                     }
				  }
				  	
			    }
				water_time++; 

			    previewTime=System.currentTimeMillis();
			 }
		 };
		 
		 previewTime=System.currentTimeMillis();
		 
		 mwTimer.schedule(task, first*1000,sec*1000);
   
	 }


    private void sendBroadcastdooropen( )
    {
       Intent localIntent = new Intent("com.ctl.smatrt.microwave.dooropen");
	    sendBroadcast(localIntent);
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
		  mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, this.getClass().getName());
		  mWakeLock.setReferenceCounted(false);
	  }
	  if(!mWakeLock.isHeld()){
		  mWakeLock.acquire();
	  }
	}

}

