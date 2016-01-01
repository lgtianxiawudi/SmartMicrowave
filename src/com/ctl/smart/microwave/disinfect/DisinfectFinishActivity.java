package com.ctl.smart.microwave.disinfect;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.activity.MainActivity;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import com.ctl.smart.microwave.utils.TextViewUtils;

public class DisinfectFinishActivity extends AbActivity {
	private String name = "";
	@AbIocView(id = R.id.statu)
	TextView statu;
	@AbIocView(id = R.id.remind)
	TextView remind;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.cookingfinish);
		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this)
				.setback_mainListener().setBackListener(
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								StartActivityUtil.startActivityFinish(
										DisinfectFinishActivity.this,
										MainActivity.class);
								StartActivityUtil.clearActivity();
							}
						});
		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
			String content = bundle.getString("content");
			statu.setText(name + "已结束...");
		}
		remind.setVisibility(View.GONE);
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//initSize();
	}
	public void initSize(){
		TextViewUtils.setHalfSize(remind);
		TextViewUtils.setCommonSize(statu);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		StartActivityUtil.startActivityFinish(this, MainActivity.class);
		StartActivityUtil.clearActivity();
	}
}
