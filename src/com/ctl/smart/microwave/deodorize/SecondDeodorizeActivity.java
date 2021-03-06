package com.ctl.smart.microwave.deodorize;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.utils.BottomUtilOne;
import com.ctl.smart.microwave.utils.BottomUtilOne.CurrentPercent;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.TextViewUtils;
import android.content.Intent;
import android.util.Log;
import com.ctl.smart.microwave.utils.BottomUtilOne.onChangeStaueListener;
import android.view.WindowManager;

public class SecondDeodorizeActivity extends AbActivity implements CurrentPercent ,onChangeStaueListener {
	String name="";
	@AbIocView(id=R.id.secondtitle)
	private TextView secondtitle;
	@AbIocView(id=R.id.progress)
	private ProgressBar progress;
	
	private BottomUtilOne bottomUtilThree;
	@AbIocView(id=R.id.ll)
	private LinearLayout ll;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.deodorize_two);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Bundle bundle=getIntent().getExtras();
		if (bundle!=null) {
			name=bundle.getString("title");
		}
		bottomUtilThree = new BottomUtilOne(this).setBackMainListener()
		.setBackListener().setAutoPlayListener(60*1000, DeodorizeFinishActivity.class, name).setCurrentPercent(this);
		bottomUtilThree.setChangeStaue(this);
		Log.e("lwxdebug", " SecondDeodorizeActivity");

		sendBroadcastToService();
		HeadUtil headUtil=new HeadUtil(this).setTitleName(name);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initSize();
	}
	private void initSize(){
		TextViewUtils.setCommonSize(secondtitle);
	}
	@Override
	public void getCurrentPercen(long millisUntilFinished, int percent) {
		// TODO Auto-generated method stub
		System.out.println(percent);
		int width=ll.getWidth()*percent/100;
		progress.setLayoutParams(new LinearLayout.LayoutParams(width, LayoutParams.WRAP_CONTENT));
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (bottomUtilThree!=null) {
			bottomUtilThree.setOnDestory();
		}
	}

@Override
public void onchangeStaue(int status) {

 	Log.e("lwxdebug", " onchangeStaue");

	// TODO Auto-generated method stub
	switch (status) {
	case BottomUtilOne.PAUSE: {

	}
		break;
	case BottomUtilOne.RUN: {
       sendBroadcastToService();
	}
		break;
	default:
		break;
	}
}

private void sendBroadcastToService()
{
   Intent localIntent = new Intent("com.ctl.smatrt.microwave.SETMACROWAVEON");
   Bundle sendbudle= new Bundle();
 	Log.e("lwxdebug", " sendBroadcastToService");
	 
   sendbudle.putString("type","1");
   sendbudle.putInt("time",60);
   sendbudle.putString("temperatureStr","");
   localIntent.putExtras(sendbudle);
   sendBroadcast(localIntent);
}

	
}
