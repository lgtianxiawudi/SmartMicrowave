package com.ctl.smart.microwave.ferment;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.ab.activity.AbActivity;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.ExcelUtil;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;

public class FermentPositionActivity extends AbActivity implements OnClickListener{
			
	private String name = "";

	private Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setAbContentView(R.layout.position);
		
		bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");

		}
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);
		
		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this).setBackListener()
				;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		StartActivityUtil.startActivityForResult(this, FermentingActivity.class,
				bundle);
	}
}
