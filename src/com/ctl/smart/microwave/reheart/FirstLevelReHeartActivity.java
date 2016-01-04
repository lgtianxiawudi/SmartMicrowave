package com.ctl.smart.microwave.reheart;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.activity.CookingActivity;
import com.ctl.smart.microwave.adapter.FancyCoverFlowSampleAdapter;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import android.util.Log;
import android.view.WindowManager;

public class FirstLevelReHeartActivity extends AbActivity implements OnClickListener {
	@AbIocView(id=R.id.fancyCoverFlow)
	private FancyCoverFlow fancyCoverFlow;
	
	private String item[]=null;
	
	private FancyCoverFlowSampleAdapter adapter = null;
	@Override
	protected void onCreate(Bundle back_maindInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(back_maindInstanceState);
		setAbContentView(R.layout.level);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this).setBackListener().setOkListener(this);
		HeadUtil headUtil=new HeadUtil(this,true).setTitleName(getString(R.string.heat_up));
		
		item=getResources().getStringArray(R.array.reheart_level1);
		
        this.fancyCoverFlow.setUnselectedAlpha(0.8f);
        this.fancyCoverFlow.setUnselectedSaturation(0.0f);
        this.fancyCoverFlow.setUnselectedScale(0.5f);
        this.fancyCoverFlow.setMaxRotation(0);
        this.fancyCoverFlow.setScaleDownGravity(0.5f);
        this.fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
        this.fancyCoverFlow.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				int height=fancyCoverFlow.getMeasuredHeight();
				int width=fancyCoverFlow.getMeasuredWidth();
				adapter=new FancyCoverFlowSampleAdapter(item,FirstLevelReHeartActivity.this,width,height);
				adapter.setShowImage(false);
				adapter.setSingle(true);
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
		fancyCoverFlow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (LastPosition == arg2) {
					itemListener();
				}
			}
		});
		fancyCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
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
		int position=this.fancyCoverFlow.getSelectedItemPosition()%item.length;
		Bundle bundle=new Bundle();
		bundle.putInt("postion", position);
		bundle.putInt("status", R.string.reheart_starting);
		bundle.putString("imageid", item[position]);
		bundle.putString("temperature", item[position]);
		bundle.putString("needwater", item[position].split("•")[0]);
		switch (position) {
		case 0:
		case 1:
		case 2:
		{
			bundle.putString("title", getString(R.string.heat_up)+getString(R.string.point)+item[position].split("•")[0]);
			StartActivityUtil.startActivityForResult(this, SecondLevelReHeartActivity.class, bundle);
		}
			break;
		case 3:
		case 4:
		case 5:
		{
			bundle.putString("time", "1:30");
			bundle.putString("autoPauseTime", "30");
			bundle.putString("title", getString(R.string.heat_up)+getString(R.string.point)+item[position]);
			StartActivityUtil.startActivityForResult(this, CookingActivity.class, bundle);
			
		}
			break;
		default:
			break;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ok:
		{
			itemListener();
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
		StartActivityUtil.removeActivity(this);
	}
}
