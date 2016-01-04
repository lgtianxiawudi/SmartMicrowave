package com.ctl.smart.microwave.activity;

import java.util.Calendar;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.adapter.FancyCoverFlowSampleAdapter;
import com.ctl.smart.microwave.automenu.FirstLevelAutoMenuActivity;
import com.ctl.smart.microwave.automenu.SecondLevelAutoMenuActivity;
import com.ctl.smart.microwave.cavityclean.FirstCavitycleanActivity;
import com.ctl.smart.microwave.defrost.FirstLevelDefrostActivity;
import com.ctl.smart.microwave.deodorize.FirstDeodorizeActivity;
import com.ctl.smart.microwave.disinfect.FirstDisinfectActivity;
import com.ctl.smart.microwave.favorite.FavoriteActivity;
import com.ctl.smart.microwave.ferment.FirstLevelFermentActivity;
import com.ctl.smart.microwave.healthybaby.FirstLevelHealthyBabyActivity;
import com.ctl.smart.microwave.reheart.FirstLevelReHeartActivity;
import com.ctl.smart.microwave.reheart.SecondLevelReHeartActivity;
import com.ctl.smart.microwave.snack.FirstLevelSnackActivity;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import com.ctl.smart.microwave.utils.TextViewUtils;
import com.ctl.smart.microwave.views.DigitalClock;
import com.ctl.smart.microwave.views.DigitalClock.TimeChangeListener;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

public class MainActivity extends AbActivity implements TimeChangeListener, OnClickListener {

	@AbIocView(id = R.id.time)
	private DigitalClock digitalClock;

	@AbIocView(id = R.id.day)
	private TextView day;

	private boolean mIsServiceBinded = false;
	private String item[] = null;
	private Typeface dayTypeface = null;
	private static final String ANDROID_CLOCK_FONT_FILE = "/system/fonts/AndroidClock.ttf";
	
	@AbIocView(id = R.id.fancyCoverFlow)
	private FancyCoverFlow fancyCoverFlow;


	private FancyCoverFlowSampleAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setAbContentView(R.layout.activity_main);
		digitalClock.setTimeChangeListener(this);
		dayTypeface = Typeface.createFromFile(ANDROID_CLOCK_FONT_FILE);
		if (dayTypeface != null) {
			// day.setTypeface(dayTypeface);
			digitalClock.setTypeface(dayTypeface);
		}
		// digitalClock.setFakeBoldText(true);
		// getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_SHOW_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	
		item = getResources().getStringArray(R.array.main);

		this.fancyCoverFlow.setUnselectedAlpha(0.5f);
		this.fancyCoverFlow.setUnselectedSaturation(0.0f);
		this.fancyCoverFlow.setUnselectedScale(0.5f);
		this.fancyCoverFlow.setMaxRotation(0);
		this.fancyCoverFlow.setScaleDownGravity(0.5f);
		this.fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
		this.fancyCoverFlow.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				int height = fancyCoverFlow.getMeasuredHeight();
				int width = fancyCoverFlow.getMeasuredWidth();
				adapter = new FancyCoverFlowSampleAdapter(item, MainActivity.this, width, height);
				fancyCoverFlow.setAdapter(adapter);
				fancyCoverFlow.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
	
	}
	
	
	private int LastPosition = -1;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initSize();
		sendLedToService("1O2O3O4O5O6O7BS\n");
		fancyCoverFlow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (LastPosition == arg2) {
					itemListener();
				}
			}
		});
		fancyCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				LastPosition = arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				LastPosition = -1;
			}
		});
	}

	private void itemListener() {
		int position = fancyCoverFlow.getSelectedItemPosition() % item.length;
		
		String name = item[position];
		
		Bundle bundle =  new Bundle();
		
		bundle.putString("title", name);
		
		switch (position) {
		case 0: {
			StartActivityUtil.startActivityForResult(this, FirstLevelAutoMenuActivity.class,bundle);
		}
			break;
		case 1: {
			StartActivityUtil.startActivityForResult(this, FirstLevelReHeartActivity.class,bundle);
		}
			break;
		case 2: {
			StartActivityUtil.startActivityForResult(this, FirstLevelDefrostActivity.class,bundle);
		}
			break;
		case 3: {
			StartActivityUtil.startActivityForResult(this, FirstLevelSnackActivity.class,bundle);
		}
			break;
		case 4: {
			StartActivityUtil.startActivityForResult(this, FirstLevelFermentActivity.class,bundle);
		}
			break;
		case 5: {
			StartActivityUtil.startActivityForResult(this, FirstDeodorizeActivity.class,bundle);
		}
			break;
		case 6: {
			StartActivityUtil.startActivityForResult(this, FirstDisinfectActivity.class,bundle);
		}
			break;
		case 7: {
			StartActivityUtil.startActivityForResult(this, FirstLevelHealthyBabyActivity.class,bundle);
		}
			break;
		case 8: {
			StartActivityUtil.startActivityForResult(this, FirstCavitycleanActivity.class,bundle);
		}
			break;
		case 9: {
			StartActivityUtil.startActivityForResult(this, FavoriteActivity.class,bundle);
		}
			break;
		case 10: {
//			 StartActivityUtil.startActivityForResult(this,
//			 SystemSettings.class,bundle);
		}
			break;
		default:
			break;
		}
	}

	public void initSize() {


		TextViewUtils.setTitleSize(digitalClock);
		TextViewUtils.setSixtyPercentSize(day);

	}

	@Override
	public void onTimeChange(Calendar calendar) {
		// TODO Auto-generated method stub
		int monthI = (calendar.get(Calendar.MONTH)) + 1;
		int dayI = calendar.get(Calendar.DAY_OF_MONTH);
		int weekI = calendar.get(Calendar.DAY_OF_WEEK);
		String[] weeks = getResources().getStringArray(R.array.week);
		day.setText(monthI + getString(R.string.month) + dayI + getString(R.string.day) + "   " + weeks[weekI - 1]);
	}

	@Override
	public void onClick(View v) {

	}

	protected void onStart() {
		super.onStart();
		Log.e("lwxdebug", "MicroWaveService onStart");

	}

	protected void onStop() {
		Log.e("lwxdebug", "MainActivity onStop");
		if (mIsServiceBinded) {
		}
		super.onStop();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		Log.e("lwxdebug", "onKeyDown keyCode=" + keyCode);

		switch (keyCode) {
		case KeyEvent.KEYCODE_1: // zengqi
			item = getResources().getStringArray(R.array.reheart_level1);

			Bundle bundle2 = new Bundle();
			bundle2.putString("title", item[0].split("•")[0]);
			bundle2.putInt("postion", 0);
			bundle2.putString("imageid", item[0]);
			StartActivityUtil.startActivityForResult(this, SecondLevelReHeartActivity.class, bundle2);

			break;

		case KeyEvent.KEYCODE_2:// weibobianp
			item = getResources().getStringArray(R.array.reheart_level1);

			Bundle bundle3 = new Bundle();
			bundle3.putString("title", item[1].split("•")[0]);
			bundle3.putInt("postion", 1);
			bundle3.putString("imageid", item[1]);
			StartActivityUtil.startActivityForResult(this, SecondLevelReHeartActivity.class, bundle3);

			break;

		case KeyEvent.KEYCODE_3:
			item = getResources().getStringArray(R.array.reheart_level1);

			Bundle bundle1 = new Bundle();
			bundle1.putString("title", item[2].split("•")[0]);
			bundle1.putInt("postion", 2);
			bundle1.putString("imageid", item[2]);
			StartActivityUtil.startActivityForResult(this, SecondLevelReHeartActivity.class, bundle1);

			break;

		case KeyEvent.KEYCODE_6:
			/*
			 * item=getResources().getStringArray(R.array.cavityclean_level1);
			 * Bundle bundle=new Bundle();
			 * 
			 * bundle.putString("title", item[0]); bundle.putInt("postion", 0);
			 * bundle.putString("imageid", item[0]);
			 * 
			 * StartActivityUtil.startActivityForResult(this,
			 * SeondCavitycleanActivity.class, bundle);
			 */

			StartActivityUtil.startActivityForResult(this, FirstCavitycleanActivity.class);
			break;

		case KeyEvent.KEYCODE_5:

			break;

		case KeyEvent.KEYCODE_4:
			Bundle bundle4 = new Bundle();
			item = getResources().getStringArray(R.array.reheart_level1);

			String item2[] = getResources().getStringArray(R.array.reheart_level2_1);

			bundle4.putString("title", item[1].split("•")[0] + getString(R.string.point) + item2[9]);

			bundle4.putInt("postion", 1);
			// bundle4.putInt("time", 30*1000);
			bundle4.putString("imageid", "quickmicro");
			bundle4.putString("temperature", item2[9]);

			bundle4.putString("time", "00" + ":" + "30");
			sendLedToService("4B5F\n");

			StartActivityUtil.startActivityForResult(this, CookingActivity.class, bundle4);
			break;

		}

		sendledBroadcastToService(keyCode, event);

		event.startTracking();
		return super.onKeyDown(keyCode, event);

	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {

		Log.e("lwxdebug", "onKeyLongPress");
		return super.onKeyLongPress(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		Log.e("lwxdebug", "onKeyUp");

		// sendledBroadcastToService(keyCode,event);

		return false;
	}

	private void sendledBroadcastToService(int keyCode, KeyEvent event) {
		Intent localIntent = new Intent("com.ctl.smatrt.microwave.setled");

		Bundle sendbudle = new Bundle();
		int led = 0;
		String code = "";

		switch (keyCode) {
		case KeyEvent.KEYCODE_1:
			led = 1;
			break;
		case KeyEvent.KEYCODE_2:
			led = 2;
			break;
		case KeyEvent.KEYCODE_3:
			led = 3;
			break;
		case KeyEvent.KEYCODE_4:
			led = 5;
			break;
		case KeyEvent.KEYCODE_5:
			led = 4;
			break;
		case KeyEvent.KEYCODE_6:
			led = 6;
			break;
		}

		if (event.getAction() == KeyEvent.ACTION_DOWN) {

			code = "" + led + "F" + "\n";
		} else {
			// code=""+led+"O";
		}
		// sendbudle.putString("led",code);
		sendbudle.putString("led", code);
		localIntent.putExtras(sendbudle);
		if (led != 4)
			sendBroadcast(localIntent);
	}

	private void sendLedToService(String code) {
		Intent localIntent = new Intent("com.ctl.smatrt.microwave.setled");

		Bundle sendbudle = new Bundle();
		// int led =0;
		// String code="";

		// code="1O2O3O4O5O6O";

		// sendbudle.putString("led",code);
		sendbudle.putString("led", code);
		localIntent.putExtras(sendbudle);
		sendBroadcast(localIntent);
	}
}
