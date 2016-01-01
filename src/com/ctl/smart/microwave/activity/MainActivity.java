package com.ctl.smart.microwave.activity;

import java.util.Calendar;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.automenu.FirstLevelAutoMenuActivity;
import com.ctl.smart.microwave.cavityclean.FirstCavitycleanActivity;
import com.ctl.smart.microwave.defrost.FirstLevelDefrostActivity;
import com.ctl.smart.microwave.deodorize.FirstDeodorizeActivity;
import com.ctl.smart.microwave.disinfect.FirstDisinfectActivity;
import com.ctl.smart.microwave.favorite.FavoriteActivity;
import com.ctl.smart.microwave.ferment.FirstLevelFermentActivity;
import com.ctl.smart.microwave.healthybaby.FirstLevelHealthyBabyActivity;
import com.ctl.smart.microwave.reheart.FirstLevelReHeartActivity;
import com.ctl.smart.microwave.snack.FirstLevelSnackActivity;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import com.ctl.smart.microwave.utils.TextViewUtils;
import com.ctl.smart.microwave.views.DigitalClock;
import com.ctl.smart.microwave.views.DigitalClock.TimeChangeListener;

public class MainActivity extends AbActivity implements TimeChangeListener,OnClickListener{
	
	@AbIocView(id = R.id.time)
	private DigitalClock digitalClock;
	
	@AbIocView(id = R.id.day)
	private TextView day;

	@AbIocView(id=R.id.automatic_menu,click="onClick")
	private Button automatic_menu;
	
	@AbIocView(id=R.id.heat_up,click="onClick")
	private Button heat_up;
	
	@AbIocView(id=R.id.thaw,click="onClick")
	private Button thaw;
	
	@AbIocView(id=R.id.snacks,click="onClick")
	private Button snacks;
	
	@AbIocView(id=R.id.fermentation,click="onClick")
	private Button fermentation;
	
	@AbIocView(id=R.id.taste_removal,click="onClick")
	private Button taste_removal;
	
	@AbIocView(id=R.id.sterilize,click="onClick")
	private Button sterilize;
	
	@AbIocView(id=R.id.healthy_baby,click="onClick")
	private Button healthy_baby;
	
	@AbIocView(id=R.id.cavity_clean,click="onClick")
	private Button cavity_clean;
	
	@AbIocView(id=R.id.system,click="onClick")
	private Button system;
	
	@AbIocView(id=R.id.favorites,click="onClick")
	private Button favorites;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_main);
		digitalClock.setTimeChangeListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initSize();
	}
	public void initSize(){
	
		automatic_menu.setOnClickListener(this);
		heat_up.setOnClickListener(this);
		thaw.setOnClickListener(this);
		snacks.setOnClickListener(this);
		fermentation.setOnClickListener(this);
		taste_removal.setOnClickListener(this);
		sterilize.setOnClickListener(this);
		healthy_baby.setOnClickListener(this);
		cavity_clean.setOnClickListener(this);
		favorites.setOnClickListener(this);
		system.setOnClickListener(this);
	
		TextViewUtils.setTitleSize(digitalClock);
		TextViewUtils.setSixtyPercentSize(day);
		/*TextViewUtils.setCommonSize(automatic_menu);
		TextViewUtils.setCommonSize(cavity_clean);
		TextViewUtils.setCommonSize(favorites);
		TextViewUtils.setCommonSize(fermentation);
		TextViewUtils.setCommonSize(healthy_baby);
		TextViewUtils.setCommonSize(heat_up);
		TextViewUtils.setCommonSize(snacks);
		TextViewUtils.setCommonSize(sterilize);
		TextViewUtils.setCommonSize(system);
		TextViewUtils.setCommonSize(taste_removal);
		TextViewUtils.setCommonSize(thaw);*/
	}
	@Override
	public void onTimeChange(Calendar calendar) {
		// TODO Auto-generated method stub
		int monthI = calendar.get(Calendar.MONTH);
		int dayI = calendar.get(Calendar.DAY_OF_MONTH);
		int weekI = calendar.get(Calendar.DAY_OF_WEEK);
		String[] weeks = getResources().getStringArray(R.array.week);
		day.setText(monthI + getString(R.string.month) + dayI
				+ getString(R.string.day) + "   " + weeks[weekI-1]);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.automatic_menu:
		{
			StartActivityUtil.startActivityForResult(this, FirstLevelAutoMenuActivity.class);
		}
			break;
		case R.id.heat_up:
		{
			StartActivityUtil.startActivityForResult(this, FirstLevelReHeartActivity.class);
		}
			break;
		case R.id.thaw:
		{
			StartActivityUtil.startActivityForResult(this, FirstLevelDefrostActivity.class);
		}
			break;
		case R.id.snacks:
		{
			StartActivityUtil.startActivityForResult(this, FirstLevelSnackActivity.class);
		}
			break;
		case R.id.fermentation:
		{
			StartActivityUtil.startActivityForResult(this, FirstLevelFermentActivity.class);
		}
			break;
		case R.id.taste_removal:
		{
			StartActivityUtil.startActivityForResult(this, FirstDeodorizeActivity.class);
		}
			break;
		case R.id.sterilize:
		{
			StartActivityUtil.startActivityForResult(this, FirstDisinfectActivity.class);
		}
			break;
		case R.id.healthy_baby:
		{
			StartActivityUtil.startActivityForResult(this, FirstLevelHealthyBabyActivity.class);
		}
			break;
		case R.id.cavity_clean:
		{
			StartActivityUtil.startActivityForResult(this, FirstCavitycleanActivity.class);
		}
			break;
		case R.id.favorites:
		{
			StartActivityUtil.startActivityForResult(this, FavoriteActivity.class);
		}
			break;
		case R.id.system:
		{
//			StartActivityUtil.startActivityForResult(this, SystemSettings.class);
		}
			break;
		default:
			break;
		}
	}
}
