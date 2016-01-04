package com.ctl.smart.microwave.utils;

import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.db.SaveDao;
import com.ctl.smart.microwave.deodorize.FirstDeodorizeActivity;
import com.ctl.smart.microwave.deodorize.SecondDeodorizeActivity;
import com.ctl.smart.microwave.model.SaveModel;
import com.ctl.smart.microwave.views.AdvancedCountdownTimer;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BottomUtilThree {
	private ImageButton play;

	private ImageButton back;

	private Button save;

	private TextView time;

	private Activity activity;

	private MyCount count;

	private String name;
	
	private Bundle bundle;
	private MyCountDownTimer dialogTimer;
	private boolean addWater=false;
	private String autoPaseTime="";
	private boolean isJieDong =false;
	private long timeL;

	public BottomUtilThree(Activity context,Bundle bundle) {
		this.bundle=bundle;
		name = bundle.getString("title");
		this.name = name.replace("\n", "").replace("\n", "");
		init(context, null);
	}
	private void init(Activity context,String name){
		this.activity = context;
		play = (ImageButton) context.findViewById(R.id.play);
		back = (ImageButton) context.findViewById(R.id.back);
		save = (Button) context.findViewById(R.id.save);
		time = (TextView) context.findViewById(R.id.bootometime);
		
		TextViewUtils.setCommonSize(time);
	}

	public BottomUtilThree setIsNeedAddWater(boolean addWater){
		this.addWater=addWater;
		return this;
	}
	public BottomUtilThree setIsJieDong(boolean isJieDong){
		this.isJieDong=isJieDong;
		return this;
	}
	public BottomUtilThree setAutoPauseTime(String autoPaseTime){
		this.autoPaseTime=autoPaseTime;
		return this;
	}
	public BottomUtilThree setBackListener() {
		if (this.back != null) {
			this.time.setVisibility(View.VISIBLE);
			this.back.setVisibility(View.VISIBLE);
			this.back.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int tat = 0;
					try {
						tat = (Integer) play.getTag();
					} catch (Exception e) {
						// TODO: handle exception
					}
					if (count != null && tat == 1) {
						WindowManager windowManager = activity
								.getWindowManager();
						Display display = windowManager.getDefaultDisplay();
						View view = LayoutInflater.from(activity).inflate(
								R.layout.back_dialog, null);
						view.setLayoutParams(new LayoutParams(display
								.getWidth(), display.getHeight()));
						Button ok = (Button) view.findViewById(R.id.back_ok);
						Button cancel = (Button) view
								.findViewById(R.id.back_cancel);
						ok.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AbDialogUtil.removeDialog(activity);
								StartActivityUtil.backPreActivity(activity);
                                sendStopMicroToService(activity);
							}
						});
						cancel.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AbDialogUtil.removeDialog(activity);

							}
						});
						AbDialogUtil.showFullScreenDialog(view);
						dialogTimer=new MyCountDownTimer(2*60*1000, 1000);
						dialogTimer.start();
						return;
					}
					StartActivityUtil.backPreActivity(activity);
					sendStopMicroToService(activity);
				}
			});
		}
		return this;
	}

	public BottomUtilThree setPlayListener(final long timeL,
			final Class<?> cls) {
		
		this.timeL=timeL;
		if (this.time != null && this.play != null) {
			this.play.setVisibility(View.VISIBLE);
			this.time.setVisibility(View.VISIBLE);
			this.time.setText(StringUtil.getSpannStringAbountSize(timeL));
			if (count == null) 
			{
    			 if (bundle != null) 
    			  {
    				  count = new MyCount(timeL, 1000, cls, bundle);
    			  } else {
    			 	 count = new MyCount(timeL, 1000, cls, name);
    			  }
			}
			play.setTag(3);			
          
 
			this.play.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// AbToastUtil.showToast(activity, "back_main");
					
					/*if (count == null) {
						play.setImageResource(R.drawable.pause);
						play.setTag(1);
					
					  if (bundle != null) 
					  	{
							count = new MyCount(timeL, 1000, cls, bundle);
						} else {
							count = new MyCount(timeL, 1000, cls, name);
						}

						
						count.start();
						if (changeStaue != null) {
							changeStaue.onchangeStaue(RUN);
						}
					} 
					else*/
					

					{
					
						int tat = (Integer) play.getTag();
						switch (tat) {
						case 3: {
									count.start();
									play.setImageResource(R.drawable.pause);
									play.setTag(1);
									if (changeStaue != null) {
										changeStaue.onchangeStaue(RUN);
									}
								}
						     break;
						case 1: {
							count.pause();
							play.setImageResource(R.drawable.start);
							play.setTag(2);
							if (changeStaue != null) {
								changeStaue.onchangeStaue(PAUSE);
							}
						}
							break;
						case 2: {
							count.resume();
							play.setImageResource(R.drawable.pause);
							play.setTag(1);
							if (changeStaue != null) {
								changeStaue.onchangeStaue(RUN);
							}
						}
						default:
							break;
						}
					}
				}
			});

		}
		return this;
	}

   public void pause()
   {
     if(count!=null)
     {
       	count.pause();
		play.setImageResource(R.drawable.start);
		play.setTag(2);
		if (changeStaue != null) {
		  changeStaue.onchangeStaue(PAUSE);
		}
     }

   }


   public void start()
   {
	  if(count!=null)
	  {			
        count.start();
  		play.setImageResource(R.drawable.pause);
  		play.setTag(1);
  		if (changeStaue != null) {
  		  changeStaue.onchangeStaue(RUN);
  		}
	  }
   }

   public void back()
   {
        StartActivityUtil.backPreActivity(activity);
		sendStopMicroToService(activity);
		sendLedToService(activity,"7BS");

		
   }
           
   public void addTime(long millisInFuture)
   	{
   	   if(count!=null)
   	   {
         count.addTime(millisInFuture);
   	   }
   	}

	public BottomUtilThree setAutoPlayListener(final long timeL,
			final Class<?> cls) {
		this.timeL=timeL;
		if (this.time != null) {
			this.time.setText(StringUtil.getSpannStringAbountSize(timeL));

			count = new MyCount(timeL, 1000, cls, name);
			count.start();
			Log.e("lwxdebug", "setAutoPlayListener changeStaue"+changeStaue);
			play.setImageResource(R.drawable.pause);
			play.setTag(1);

			
			if (changeStaue != null) {
				changeStaue.onchangeStaue(RUN);
			}
		}

		return this;
	}

	public BottomUtilThree setTime(long timeL) {
		this.timeL=timeL;
		this.time.setText(StringUtil.getSpannStringAbountSize(timeL));
		return this;
	}

	public BottomUtilThree setSaveListener() {
		if (this.save != null) {
			this.save.setVisibility(View.VISIBLE);
			boolean flag = isHaveSave();
			this.save.setText(activity.getString(R.string.save));
			this.save.setTag(flag);
			Log.e("lwxdebug", "isHaveSave flag"+flag);
			if (flag) {
				this.save.setEnabled(false);
				return this;
			}

			Log.e("lwxdebug", "isHaveSave flag1"+flag);
			
			this.save.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					boolean flag = (Boolean) save.getTag();
					SaveDao dao = new SaveDao(activity);
					dao.startWritableDatabase(false);
					if (flag) {
//						int num = dao.delete(" u_name = ?",
//								new String[] { name });
//						if (num > 0) {
//							save.setText(activity.getString(R.string.save));
//							save.setTag(false);
							AbToastUtil.showToast(
									activity,
									activity.getString(R.string.delete_fav_toase)
											+ name);
						
					} else {
						String imageid = bundle.getString("imageid");
						String secondtime = bundle.getString("secondtime");
						String time = bundle.getString("time");
						String temperatureStr = bundle.getString("temperature");
						String poiont=activity.getResources().getString(R.string.point);
						String uName=name.split(poiont)[0]+"---"+bundle.getString("imageid");
						
						String needwater=bundle.getString("needwater");
						String autoPauseTime=bundle.getString("autoPauseTime");
						String contion=bundle.getString("contion");
						String title=bundle.getString("title");
						String parents=bundle.getString("parents");
						String key=bundle.getString("key");
						String contionName=needwater+"---"+imageid;
						SaveModel model = new SaveModel();
						model.setuName(uName);
						model.setTitle(name);
						model.setImageid(imageid);
						model.setSecondtime(secondtime);
						model.setTemperature(temperatureStr);
						model.setTime(time);
						model.setuPingYin(PingYinUtil.getPingYin(uName)
								.substring(0, 1));
						model.setNeedwater(needwater);
						model.setAutoPauseTime(autoPauseTime);
						model.setContion(contion);
						model.setTitle(title);
						model.setParents(parents);
						model.setKey(key);
						model.setContionName(contionName);
						model.setContentNamePY(PingYinUtil.getPingYin(contionName)
								.substring(0, 1));
						model.setCreadtime(System.currentTimeMillis());
						long num = dao.insert(model);
						if (num > 0) {
//							save.setText(activity
//									.getString(R.string.delete_fav));
							save.setTag(true);
							save.setEnabled(false);
							AbToastUtil.showToast(activity,
									activity.getString(R.string.save_fav_toase)
											+ name);
						}
					}

					dao.closeDatabase();
				}
			});
		} else {
			this.save.setVisibility(View.INVISIBLE);
		}
		return this;
	}

	private boolean isHaveSave() {
		SaveDao dao = new SaveDao(activity);
		dao.startReadableDatabase();
		Log.e("lwxdebug", "isHaveSave name"+name);

		
		boolean b = dao.isExist("select * from local_user where  u_title =  ? ",
				new String[] { name });
		dao.closeDatabase();
		return b;
	}

	/* 定义一个倒计时的内部类 */
	class MyCount extends AdvancedCountdownTimer {

		private Class<?> cls;

		private String name;
		
		private Bundle bundle;

		public MyCount(long millisInFuture, long countDownInterval,
				Class<?> cls, String name) {
			super(millisInFuture, countDownInterval);
			this.cls = cls;
			this.name = name;
		}
		public MyCount(long millisInFuture, long countDownInterval,
				Class<?> cls, Bundle bundle) {
			super(millisInFuture, countDownInterval);
			this.cls = cls;
			this.bundle=bundle;
		}

		@Override
		public void onFinish() {
			if (bundle==null) {
				bundle = new Bundle();
				bundle.putString("title", name);
			}
			StartActivityUtil.startActivityFinish(activity, cls, bundle);
			StartActivityUtil.clearActivity();
		}

		@Override
		public void onTick(long millisUntilFinished, int percent) {
			// TODO Auto-generated method stub
			time.setText(StringUtil
					.getSpannStringAbountSize(millisUntilFinished));
			if (currentPercent != null) {
				currentPercent.getCurrentPercen(millisUntilFinished,percent);
			}
			
			if ((millisUntilFinished==2*60*1000&&addWater)||isAutoPauseTime(millisUntilFinished)) {
				handler.sendEmptyMessage(0);
			}
			if (millisUntilFinished==timeL/3&&isJieDong) {
				handler.sendEmptyMessage(0);
			}
		}
	}
	
	private boolean isAutoPauseTime(long millisUntilFinished){
		
		if ("".equals(autoPaseTime)||null==autoPaseTime) {
			return false;
		}
		boolean result= false;
		String[] times=autoPaseTime.split(":");
		
		for (String string : times) {
			int IStr=Integer.parseInt(string);
			if (millisUntilFinished==IStr*1000) {
				result=true;
				break;
			}
		}
		
		return result;
	}
	
	Handler handler=new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			count.pause();
			play.setImageResource(R.drawable.start);
			play.setTag(2);
			if (changeStaue != null) {
				changeStaue.onchangeStaue(PAUSE);
			}
		};
	};
	public interface onChangeStaueListener {
		public abstract void onchangeStaue(int statu);
	};

	public interface CurrentPercent {
		public abstract void getCurrentPercen(long millisUntilFinished, int percent);
	};

	public final static int PAUSE = 0;
	public final static int RUN = 1;
	private onChangeStaueListener changeStaue;

	public onChangeStaueListener getChangeStaue() {
		return changeStaue;
	}

	public void setChangeStaue(onChangeStaueListener changeStaue) {
		this.changeStaue = changeStaue;
	}

	private CurrentPercent currentPercent;

	public CurrentPercent getCurrentPercent() {
		return currentPercent;
	}

	public BottomUtilThree setCurrentPercent(CurrentPercent currentPercent) {
		this.currentPercent = currentPercent;
		return this;
	}

	public void setOnDestory() {
		if (count != null) {
			count.cancel();
		}
		if (dialogTimer!=null) {
			dialogTimer.cancel();
		}
	}
	private class MyCountDownTimer  extends CountDownTimer{

		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			AbDialogUtil.removeDialog(activity);
		}
	}
    private void sendStopMicroToService(Context paramContext)
    {
       Intent localIntent = new Intent("com.ctl.smatrt.microwave.stopmicrowave");
       paramContext.sendBroadcast(localIntent);
    }


    private void sendLedToService(Context paramContext,String code)
    {
    	Intent localIntent = new Intent("com.ctl.smatrt.microwave.setled");
       
    	Bundle sendbudle= new Bundle();
    
    	
    	sendbudle.putString("led",code);
    	localIntent.putExtras(sendbudle);
    	paramContext.sendBroadcast(localIntent);
    }
    
        
	
}
