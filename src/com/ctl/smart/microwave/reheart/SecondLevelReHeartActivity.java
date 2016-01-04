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
import com.ab.util.AbToastUtil;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.activity.CookingActivity;
import com.ctl.smart.microwave.adapter.FancyCoverFlowSampleAdapter;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import android.util.Log;
import android.view.WindowManager;

public class SecondLevelReHeartActivity extends AbActivity implements
		OnClickListener {
	@AbIocView(id = R.id.fancyCoverFlow)
	private FancyCoverFlow fancyCoverFlow;

	private String[] item;

	private int[] ImageId;

	private String name = "";

	private int poistion;
	
	private Bundle bundle;
	private  int selectPositon=0;

	private FancyCoverFlowSampleAdapter adapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.level);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this).setBackListener()
				.setback_mainListener();
		bundle= getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
			poistion = bundle.getInt("postion");
			switch (poistion) {
			case 0: {
				item = getResources().getStringArray(R.array.reheart_level2_0);
				selectPositon=4;
			}
				break;
			case 1: {
				item = getResources().getStringArray(R.array.reheart_level2_1);
				selectPositon=6;
			}
				break;
			case 2: {
				item = getResources().getStringArray(R.array.reheart_level2_2);
				selectPositon=12;
			}
				break;
			default:
				break;
			}
		}
		HeadUtil headUtil = new HeadUtil(this,true).setTitleName(name);
		Log.e("lwxdebug", " SecondLevelReHeartActivity");

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
						adapter = new FancyCoverFlowSampleAdapter(
								item, SecondLevelReHeartActivity.this,
								width, height);
						switch (poistion) {
						case 0:
						case 2:
							adapter.setPostionContent(selectPositon, getString(R.string.advice_tem));
							break;
						case 1:
							adapter.setPostionContent(selectPositon, getString(R.string.advice_gl));
							break;
						default:
							break;
						}
						adapter.setSingle(false);
						fancyCoverFlow.setAdapter(adapter);
						fancyCoverFlow.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
							fancyCoverFlow.setSelection(selectPositon);
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
		//AbToastUtil.showToast(this, position + "");
		bundle.putString("title", name +getString(R.string.point)+item[position]);
		bundle.putString("temperature", item[position]);
		switch (poistion) {
		case 0: {
			bundle.putInt("time", 5*60*1000);
			StartActivityUtil.startActivityForResult(this,
					CookingTimeSettingActivity.class, bundle);
		}
			break;
		case 1: {
			bundle.putInt("time", 30*1000);
			StartActivityUtil.startActivityForResult(this,
					CookingTimeSettingActivity.class, bundle);
		}
			break;
		case 2: {
			bundle.putInt("time", 5*60*1000);
			StartActivityUtil.startActivityForResult(this,
					ChoiceTypeReHeartActivity.class, bundle);
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
