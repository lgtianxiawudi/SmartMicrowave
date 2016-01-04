package com.ctl.smart.microwave.ferment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.activity.MainActivity;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import android.view.WindowManager;

public class FermentFinishActivity extends AbActivity {
	private String name = "";
	@AbIocView(id = R.id.statu)
	TextView statu;

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
										FermentFinishActivity.this,
										MainActivity.class);
								StartActivityUtil.clearActivity();
							}
						});
		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
			String content = bundle.getString("content");
			if (TextUtils.isEmpty(content)) {
				statu.setText("发酵已结束...");
			} else {
				statu.setText(content + "");
			}
		}
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		StartActivityUtil.startActivityFinish(this, MainActivity.class);
		StartActivityUtil.clearActivity();
	}
}
