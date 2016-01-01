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

public class CookingPrepareActivity extends AbActivity {
	String name="";
	@AbIocView(id=R.id.secondtitle)
	private TextView secondtitle;
	@AbIocView(id=R.id.deadtime)
	private TextView deadtime;
	
	private MyCountDownTimer timer;
	
	private BottomUtilOne bottomUtilThree;
	
	private Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.deodorize);
		bundle=getIntent().getExtras();
		name=bundle.getString("title");
		bottomUtilThree = new BottomUtilOne(this)
		.setBackListener().setTime(35*1000);
		HeadUtil headUtil=new HeadUtil(this,true).setTitleName(name);
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
}
