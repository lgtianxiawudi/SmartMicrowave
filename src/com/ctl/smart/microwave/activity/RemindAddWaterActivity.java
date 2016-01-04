package com.ctl.smart.microwave.activity;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.utils.BitmapUtil;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.DataInit;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import com.ctl.smart.microwave.views.RefreshProgress;

import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

public class RemindAddWaterActivity extends AbActivity implements OnClickListener {

	private String name = "";

	private Bundle bundle;
	
	@AbIocView(id=R.id.sure)
	private Button sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setAbContentView(R.layout.addwater);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");

		}
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);

		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this).setBackListener()
				.setback_mainListener();
		
		sure.setOnClickListener(this);

}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		StartActivityUtil.startActivityForResult(this, CookingActivity.class, bundle);
	}
}
