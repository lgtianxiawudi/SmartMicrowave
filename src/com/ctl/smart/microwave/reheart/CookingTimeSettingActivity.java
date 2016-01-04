package com.ctl.smart.microwave.reheart;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ab.view.wheel.AbWheelView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.activity.CookingPositionActivity;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import com.ctl.smart.microwave.utils.StringUtil;
import com.ctl.smart.microwave.utils.TextViewUtils;
import com.ctl.smart.microwave.utils.WheelUtils;
import android.view.WindowManager;
import android.view.WindowManager;
import com.ctl.smart.microwave.activity.CookingActivity;

public class CookingTimeSettingActivity extends AbActivity implements
		OnClickListener {
	@AbIocView(id = R.id.wheelViewh)
	private AbWheelView hour;

	@AbIocView(id = R.id.wheelViewm)
	private AbWheelView mint;

	@AbIocView(id = R.id.wheelViews)
	private AbWheelView send;

	private Bundle bundle;

	private String name = "";

	private int poistion;

	@AbIocView(id = R.id.remind)
	private TextView remind;

	@AbIocView(id = R.id.title_second)
	private TextView title_second;

	@AbIocView(id = R.id.title_minu)
	private TextView title_minu;

	@AbIocView(id = R.id.title_hour)
	private TextView title_hour;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.time_setting);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this).setBackListener()
				.setback_mainListener();
		bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
		}
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);
		WheelUtils.initWheelDatePicker(this, hour, mint, send);
		int time = bundle.getInt("time");
		mint.setCurrentItem(time / 1000 / 60);
		send.setCurrentItem(time / 1000 % 60);
		remind.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// initSize();
	}

	private void initSize() {
		TextViewUtils.setCommonSize(title_hour);
		TextViewUtils.setCommonSize(title_minu);
		TextViewUtils.setCommonSize(title_second);
		TextViewUtils.setHalfSize(remind);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ok: {
			bundle.putString("time", mint.getCurrentItem()+":"+send.getCurrentItem());
			
			if (name.indexOf("微波") != -1)
			{
               StartActivityUtil.startActivityForResult(this, CookingActivity.class,
				  bundle);
			}
			else
			{
			   StartActivityUtil.startActivityForResult(this,
					  CookingPositionActivity.class, bundle);
			}
			
		}
			break;

		default:
			break;
		}
	}
}
