package com.ctl.smart.microwave.activity;

import java.util.Map;

import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.util.AbToastUtil;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.utils.BitmapUtil;
import com.ctl.smart.microwave.utils.BottomUtilThree;
import com.ctl.smart.microwave.utils.BottomUtilThree.onChangeStaueListener;
import com.ctl.smart.microwave.utils.DataInit;
import com.ctl.smart.microwave.utils.ExcelUtil;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.TextViewUtils;
import com.ctl.smart.microwave.utils.TimeInit;
import com.ctl.smart.microwave.views.CircleImageView;

public class CookingActivity extends AbActivity implements
		onChangeStaueListener {
	private String name = "";
	private String imageid = "";
	private String temperatureStr;
	private int time = 0;
	private String type = "";

	@AbIocView(id = R.id.staue)
	private TextView statu;
	@AbIocView(id = R.id.temperature)
	private TextView temperature;
	@AbIocView(id = R.id.cooking_logo)
	private CircleImageView cooking_logo;

	private BottomUtilThree bottomUtilThree;

	private int statusID = -1;

	@AbIocView(id = R.id.add_water)
	private TextView add_water;

	private MyCountDowntimer downtimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.cooking);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			name = bundle.getString("title");
			imageid = bundle.getString("imageid");
			String timeStr = bundle.getString("time");
			time = TimeInit.timeStrToInt(timeStr);
			temperatureStr = bundle.getString("temperature");
			statusID = bundle.getInt("status", -1);
			if (statusID == -1) {
				statusID = R.string.cook_starting;
			}

			if (!TextUtils.isEmpty(temperatureStr)) {
				temperature.setText(temperatureStr.replace("\n", ""));
			}

			initTimeAndTem(imageid);

			if (time == 0) {
				time = 15 * 1000;
			}

			if (name.indexOf("蒸汽") != -1) {

				downtimer = new MyCountDowntimer(5000, 1000);
				downtimer.start();
			}

		}
		bottomUtilThree = new BottomUtilThree(this, bundle).setBackListener()
				.setSaveListener().setPlayListener(time, CookingFinish.class);
		bottomUtilThree.setChangeStaue(this);
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);
		try {
			System.out.println(imageid);
			cooking_logo.setImageBitmap(BitmapUtil.readBitMap(this,
					DataInit.getDataByKey(imageid)));
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		switch (status) {
		case BottomUtilThree.PAUSE: {
			statu.setText(R.string.cook_stopping);
			cooking_logo.pauseAnimation();
		}
			break;
		case BottomUtilThree.RUN: {
			statu.setText(statusID);
			cooking_logo.startAnimation();
			onRun();
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
		if (downtimer != null) {
			downtimer.cancel();
		}
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
	}

	private void onRun() {
		AbToastUtil.showToast(CookingActivity.this, type + ":" + time + ":"
				+ temperatureStr);

	}

	private class MyCountDowntimer extends CountDownTimer {

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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) { // 监控/拦截/屏蔽返回键
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
