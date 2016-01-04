package com.ctl.smart.microwave.deodorize;

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
import android.util.Log;
import android.view.WindowManager;

public class FirstDeodorizeActivity extends AbActivity {
	String name = "";
	@AbIocView(id = R.id.secondtitle)
	private TextView secondtitle;
	@AbIocView(id = R.id.deadtime)
	private TextView deadtime;

	private MyCountDownTimer timer;

	private BottomUtilOne bottomUtilThree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.deodorize);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
		}
		bottomUtilThree = new BottomUtilOne(this).setBackListener().setTime(60 * 1000);
		HeadUtil headUtil = new HeadUtil(this, true).setTitleName(name);

		Log.e("lwxdebug", "FirstDeodorizeActivity");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		timer = new MyCountDownTimer(5000, 1000);
		timer.start();
		initSize();
	}

	private void initSize() {
		TextViewUtils.setCommonSize(secondtitle);
		TextViewUtils.setCommonSize(deadtime);
	}

	private class MyCountDownTimer extends CountDownTimer {

		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			String str = FirstDeodorizeActivity.this.getResources().getString(R.string.start_time);
			deadtime.setText(StringUtil.getSpannStringAbountSize(millisUntilFinished, str));
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			Bundle bundle = new Bundle();
			bundle.putString("title", name);
			StartActivityUtil.startActivityForResult(FirstDeodorizeActivity.this, SecondDeodorizeActivity.class,
					bundle);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (timer != null) {
			timer.cancel();
		}
		if (bottomUtilThree != null) {
			bottomUtilThree.setOnDestory();
		}
	}
}
