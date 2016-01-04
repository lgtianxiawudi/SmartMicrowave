package com.ctl.smart.microwave.reheart;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import android.util.Log;
import android.view.WindowManager;

public class ChoiceTypeReHeartActivity extends AbActivity implements OnClickListener, OnCheckedChangeListener {
	@AbIocView(id=R.id.type_select)
	RadioGroup type_select;
	
	private Bundle bundle;
	
	private String name = "";
	
	@AbIocView(id=R.id.jj)
	private TextView jj;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.type);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
		}
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);
		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this).setBackListener()
				.setback_mainListener();
	type_select.setOnCheckedChangeListener(this);
			Log.e("lwxdebug", " ChoiceTypeReHeartActivity");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ok: {
			switch (type_select.getCheckedRadioButtonId()) {
			case R.id.rehear_no:
			{
				StartActivityUtil.startActivityForResult(this,
						CookingTimeSettingActivity.class, bundle);
			}
				break;
			case R.id.rehear_ok:
			{
				StartActivityUtil.startActivityForResult(this,
						CookingPrepareActivity.class, bundle);
			}
				break;
			default:
				break;
			}
		}
			break;

		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rehear_no:
		{
			jj.setVisibility(View.INVISIBLE);
		}
			break;
		case R.id.rehear_ok:
		{
			jj.setVisibility(View.VISIBLE);
		}
			break;
		default:
			break;
		}
	}
}
