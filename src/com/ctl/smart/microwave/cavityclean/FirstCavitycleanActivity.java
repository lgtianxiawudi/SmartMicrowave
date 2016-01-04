package com.ctl.smart.microwave.cavityclean;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ab.view.wheel.AbWheelView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.activity.CookingActivity;
import com.ctl.smart.microwave.adapter.FancyCoverFlowSampleAdapter;
import com.ctl.smart.microwave.automenu.FourLevelAutoMenuActivity;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import com.ctl.smart.microwave.utils.TextViewUtils;
import com.ctl.smart.microwave.utils.WheelUtils;
import android.view.WindowManager;

public class FirstCavitycleanActivity extends AbActivity implements OnClickListener {
	@AbIocView(id = R.id.wheelViewh)
	private AbWheelView hour;

	@AbIocView(id = R.id.wheelViewm)
	private AbWheelView mint;

	@AbIocView(id = R.id.wheelViews)
	private AbWheelView send;

	private String name = "";

	private int poistion;

	@AbIocView(id = R.id.remind)
	private TextView remind;

	@AbIocView(id = R.id.title_second)
	private TextView title_second;

	@AbIocView(id = R.id.title_minu)
	private TextView title_minu;

	@AbIocView(id = R.id.title_hour)
	private TextView title_hour;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.time_setting);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
		}
		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this).setBackListener().setback_mainListener();
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);
		WheelUtils.initWheelDatePicker(this, hour, mint, send);
		remind.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// initSize();
	}

	private void initSize() {
		TextViewUtils.setCommonSize(title_hour);
		TextViewUtils.setCommonSize(title_minu);
		TextViewUtils.setCommonSize(title_second);
		TextViewUtils.setHalfSize(remind);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ok: {
			int time = mint.getCurrentItem() * 60 + send.getCurrentItem();
			Bundle bundle = new Bundle();
			bundle.putString("title", name);
			bundle.putInt("time", time * 1000);
			StartActivityUtil.startActivityForResult(this, SeondCavitycleanActivity.class, bundle);
		}
			break;

		default:
			break;
		}
	}
}
