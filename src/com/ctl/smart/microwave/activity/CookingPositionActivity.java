package com.ctl.smart.microwave.activity;

import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.ab.activity.AbActivity;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.favorite.FavoriteActivity;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.ExcelUtil;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;

public class CookingPositionActivity extends AbActivity implements OnClickListener{
			
	private String name = "";

	private Bundle bundle;
	
	private String imageid = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setAbContentView(R.layout.position);
		
		bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
			imageid = bundle.getString("imageid");

		}
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);
		
		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this).setBackListener()
				.setOkListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Map<String, String> map=ExcelUtil.getDetailByKey(imageid);
		
		if (map!=null&&"蒸汽".equals(map.get("3"))) {
			StartActivityUtil.startActivityForResult(this, RemindAddWaterActivity.class,
					bundle);
			return;
		}
		
		if (name.indexOf("蒸汽")!=-1) {
			StartActivityUtil.startActivityForResult(this, RemindAddWaterActivity.class,
					bundle);
			return;
		}
		
		StartActivityUtil.startActivityForResult(this, CookingActivity.class,
				bundle);
	}
}
