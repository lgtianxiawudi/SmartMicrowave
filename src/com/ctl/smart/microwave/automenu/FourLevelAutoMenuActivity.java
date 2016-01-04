package com.ctl.smart.microwave.automenu;

import java.util.Map;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.ctl.smart.microwave.utils.ExcelUtil;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import android.view.WindowManager;

public class FourLevelAutoMenuActivity extends AbActivity implements
		OnClickListener {
	@AbIocView(id = R.id.fancyCoverFlow)
	private FancyCoverFlow fancyCoverFlow;

	private String[] item;

	private int[] ImageId;

	private String name = "";

	private Bundle bundle;
	
	private int state=0;
	
	private String key;
	private String parents;
	private FancyCoverFlowSampleAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.level);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this).setBackListener()
				.setback_mainListener();
		bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
			key=bundle.getString("key");
			String parents=bundle.getString("parents");
			item=ExcelUtil.getDetailKinds(key);
			parents=bundle.getString("parents");
			if (item==null) {
				state=1;
				item=ExcelUtil.getKindsByKey(parents+key);
				
			}

		}
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);

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
								item, FourLevelAutoMenuActivity.this,
								width, height);
						if (isNeedSingle()) {
							adapter.setSingle(false);
						}else{
							adapter.setSingle(true);
						}
						fancyCoverFlow.setAdapter(adapter);
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
		// AbToastUtil.showToast(this, position + "");
		if (bundle==null) {
			bundle=new Bundle();
		}
		StartActivityUtil.startActivityOther(this,FiveLevelAutoMenuActivity.class,bundle,name+getString(R.string.point)+item[position],key,position,item[position],state);
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
	private boolean isNeedSingle(){
		boolean result=false;
		String[] datas=getResources().getStringArray(R.array.auton_menu_level1);
		String[] kinds=ExcelUtil.getKindsByKey(datas[0]);
		for (int i = 0; i < kinds.length; i++) {
			if (kinds[i].equals(parents)) {
				result= true;
				break;
			}
		}
		return result;
	}
}
