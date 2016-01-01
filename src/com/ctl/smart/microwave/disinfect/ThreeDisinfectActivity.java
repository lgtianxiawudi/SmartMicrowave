package com.ctl.smart.microwave.disinfect;

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

public class ThreeDisinfectActivity extends AbActivity implements CurrentPercent {
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
		Bundle bundle=getIntent().getExtras();
		if (bundle!=null) {
			name=bundle.getString("title");
		}
		secondtitle.setText(name+"中......");
		bottomUtilThree = new BottomUtilOne(this).setBackMainListener()
		.setBackListener().setAutoPlayListener(35*1000, DisinfectFinishActivity.class, name).setCurrentPercent(this);
		HeadUtil headUtil=new HeadUtil(this).setTitleName(name);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
}