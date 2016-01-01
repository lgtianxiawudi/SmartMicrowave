package com.ctl.smart.microwave.cavityclean;

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

public class SeondCavitycleanActivity extends AbActivity {
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
		if (bundle!=null) {
			name=bundle.getString("title");
		}
		secondtitle.setText(name);
		int time=getIntent().getExtras().getInt("time");
		bottomUtilThree = new BottomUtilOne(this)
		.setBackListener().setTime(time);
		HeadUtil headUtil=new HeadUtil(this).setTitleName(name);
		deadtime.setText(R.string.clean_detail);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		timer=new MyCountDownTimer(5000, 1000);
		timer.start();
		//initSize();
	}
	private void initSize(){
		TextViewUtils.setCommonSize(deadtime);
		TextViewUtils.setCommonSize(secondtitle);
	}
	private class MyCountDownTimer  extends CountDownTimer{

		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			String str= SeondCavitycleanActivity.this.getResources().getString(R.string.start_time);
//			deadtime.setText(StringUtil.getSpannStringAbountSize(millisUntilFinished,str));
		}
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			bundle.putString("title", name);
			StartActivityUtil.startActivityForResult(SeondCavitycleanActivity.this, ThreeCavitycleanActivity.class,bundle);
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
