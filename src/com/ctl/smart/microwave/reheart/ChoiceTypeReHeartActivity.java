package com.ctl.smart.microwave.reheart;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;

public class ChoiceTypeReHeartActivity extends AbActivity implements OnClickListener {
	@AbIocView(id=R.id.type_select)
	RadioGroup type_select;
	
	private Bundle bundle;
	
	private String name = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.type);
		bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
		}
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);
		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this).setBackListener()
				.setback_mainListener().setOkListener(this);
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

}
