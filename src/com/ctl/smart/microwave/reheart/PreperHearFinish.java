package com.ctl.smart.microwave.reheart;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.activity.CookingActivity;
import com.ctl.smart.microwave.activity.MainActivity;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import com.ctl.smart.microwave.utils.TextViewUtils;
import android.view.WindowManager;

public class PreperHearFinish extends AbActivity implements OnClickListener {
	private String name = "";
	@AbIocView(id = R.id.statu)
	TextView statu;
	@AbIocView(id = R.id.remind)
	TextView remind;
	
	private Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.cookingfinish);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this)
				.setback_mainListener().setBackListener(
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								StartActivityUtil.startActivityFinish(
										PreperHearFinish.this, MainActivity.class);
								StartActivityUtil.clearActivity();
							}
						});
		bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
		}
		remind.setText(R.string.rehart_status);
		statu.setVisibility(View.GONE);
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//initSize();
	}
	public void initSize(){
		TextViewUtils.setCommonSize(statu);
		TextViewUtils.setHalfSize(remind);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		StartActivityUtil.startActivityFinish(this, MainActivity.class);
		StartActivityUtil.clearActivity();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ok: {
				StartActivityUtil.startActivityForResult(this,
						CookingTimeSettingActivity.class, bundle);
		}
			break;

		default:
			break;
		}
	}
}
