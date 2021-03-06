package com.ctl.smart.microwave.defrost;

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
import com.ctl.smart.microwave.adapter.FancyCoverFlowSampleAdapter;
import com.ctl.smart.microwave.cavityclean.SeondCavitycleanActivity;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import android.view.WindowManager;

public class FirstLevelDefrostActivity extends AbActivity implements
		OnClickListener {
	@AbIocView(id = R.id.fancyCoverFlow)
	private FancyCoverFlow fancyCoverFlow;

	private String item[] = null;
	private FancyCoverFlowSampleAdapter adapter = null;
	
	private String name;

	@Override
	protected void onCreate(Bundle back_maindInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(back_maindInstanceState);
		setAbContentView(R.layout.level);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this).setBackListener()
				;
		
		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
		}
		
		HeadUtil headUtil = new HeadUtil(this,true)
				.setTitleName(name);

		item = getResources().getStringArray(R.array.defrost_level1);

		this.fancyCoverFlow.setUnselectedAlpha(0.8f);
		this.fancyCoverFlow.setUnselectedSaturation(0.0f);
		this.fancyCoverFlow.setUnselectedScale(0.5f);
		this.fancyCoverFlow.setMaxRotation(0);
		this.fancyCoverFlow.setScaleDownGravity(0.5f);
		this.fancyCoverFlow
				.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
		this.fancyCoverFlow.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// TODO Auto-generated method stub
						int height = fancyCoverFlow.getMeasuredHeight();
						int width = fancyCoverFlow.getMeasuredWidth();
						adapter=new FancyCoverFlowSampleAdapter(
								item,
										FirstLevelDefrostActivity.this, width,
								height);
						adapter.setShowImage(false);
						fancyCoverFlow
								.setAdapter(adapter);
						fancyCoverFlow.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
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
		int position = this.fancyCoverFlow.getSelectedItemPosition()%item.length;
		Bundle bundle = new Bundle();
		bundle.putString("title",name+getString(R.string.point)+ item[position].split("•")[0]);
		bundle.putInt("postion", position);
		bundle.putString("imageid", ""+item[position]);
		bundle.putString("time", "1:30");

	    bundle.putString("temperature", "1000W");	
		bundle.putInt("status", R.string.thaw_starting);
		bundle.putString("needwater", item[position].split("•")[0]);
		bundle.putBoolean("isJieDong", true);
		StartActivityUtil.startActivityForResult(this,
				SecondLevelDefrostActivity.class, bundle);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ok: {
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
