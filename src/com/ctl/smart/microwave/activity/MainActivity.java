package com.ctl.smart.microwave.activity;

import java.util.Calendar;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.adapter.FancyCoverFlowSampleAdapter;
import com.ctl.smart.microwave.utils.TextViewUtils;
import com.ctl.smart.microwave.views.DigitalClock;
import com.ctl.smart.microwave.views.DigitalClock.TimeChangeListener;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.TextView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

public class MainActivity extends AbActivity implements TimeChangeListener, OnClickListener {

	@AbIocView(id = R.id.time)
	private DigitalClock digitalClock;

	@AbIocView(id = R.id.day)
	private TextView day;

	@AbIocView(id = R.id.fancyCoverFlow)
	private FancyCoverFlow fancyCoverFlow;
	
	@AbIocView(id = R.id.left)
	private ImageView left;
	
	@AbIocView(id = R.id.right)
	private ImageView right;
	
	private String[] item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_main);
		digitalClock.setTimeChangeListener(this);
		item = getResources().getStringArray(R.array.auton_menu_level1);

		this.fancyCoverFlow.setUnselectedAlpha(1.0f);
		this.fancyCoverFlow.setUnselectedSaturation(0.0f);
		this.fancyCoverFlow.setUnselectedScale(0.5f);
		this.fancyCoverFlow.setMaxRotation(0);
		this.fancyCoverFlow.setScaleDownGravity(0.4f);
		this.fancyCoverFlow.setSpacing(-200);
		this.fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
		fancyCoverFlow.setAdapter(
				new FancyCoverFlowSampleAdapter(item, MainActivity.this, 500,0));
		
		left.setOnClickListener(this);
		right.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initSize();
	}

	public void initSize() {

		TextViewUtils.setTitleSize(digitalClock);
		TextViewUtils.setSixtyPercentSize(day);
	}

	@Override
	public void onTimeChange(Calendar calendar) {
		// TODO Auto-generated method stub
		int monthI = calendar.get(Calendar.MONTH);
		int dayI = calendar.get(Calendar.DAY_OF_MONTH);
		int weekI = calendar.get(Calendar.DAY_OF_WEEK);
		String[] weeks = getResources().getStringArray(R.array.week);
		day.setText(monthI + getString(R.string.month) + dayI + getString(R.string.day) + "   " + weeks[weekI - 1]);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.automatic_menu:
	// {
	// StartActivityUtil.startActivityForResult(this,
	// FirstLevelAutoMenuActivity.class);
	// }
	// break;
	// case R.id.heat_up:
	// {
	// StartActivityUtil.startActivityForResult(this,
	// FirstLevelReHeartActivity.class);
	// }
	// break;
	// case R.id.thaw:
	// {
	// StartActivityUtil.startActivityForResult(this,
	// FirstLevelDefrostActivity.class);
	// }
	// break;
	// case R.id.snacks:
	// {
	// StartActivityUtil.startActivityForResult(this,
	// FirstLevelSnackActivity.class);
	// }
	// break;
	// case R.id.fermentation:
	// {
	// StartActivityUtil.startActivityForResult(this,
	// FirstLevelFermentActivity.class);
	// }
	// break;
	// case R.id.taste_removal:
	// {
	// StartActivityUtil.startActivityForResult(this,
	// FirstDeodorizeActivity.class);
	// }
	// break;
	// case R.id.sterilize:
	// {
	// StartActivityUtil.startActivityForResult(this,
	// FirstDisinfectActivity.class);
	// }
	// break;
	// case R.id.healthy_baby:
	// {
	// StartActivityUtil.startActivityForResult(this,
	// FirstLevelHealthyBabyActivity.class);
	// }
	// break;
	// case R.id.cavity_clean:
	// {
	// StartActivityUtil.startActivityForResult(this,
	// FirstCavitycleanActivity.class);
	// }
	// break;
	// case R.id.favorites:
	// {
	// StartActivityUtil.startActivityForResult(this, FavoriteActivity.class);
	// }
	// break;
	// case R.id.system:
	// {
	//// StartActivityUtil.startActivityForResult(this, SystemSettings.class);
	// }
	// break;
	// default:
	// break;
	// }
	// }
}
