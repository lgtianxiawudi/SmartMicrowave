package com.ctl.smart.microwave.activity;

import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.utils.BitmapUtil;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.DataInit;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import com.ctl.smart.microwave.views.CircleImageView;

public class RemindAddWaterActivity extends AbActivity implements OnClickListener{
	
private String name = "";

private Bundle bundle;

private String imageid = "";

@AbIocView(id = R.id.cooking_logo)
private CircleImageView cooking_logo;

@Override
protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
super.onCreate(savedInstanceState);

setAbContentView(R.layout.addwater);

bundle = getIntent().getExtras();

if (bundle != null) {
	name = bundle.getString("title");
	imageid = bundle.getString("imageid");

}
HeadUtil headUtil = new HeadUtil(this).setTitleName(name);

BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this).setBackListener()
		.setOkListener(this).setback_mainListener();

try {
	System.out.println(imageid);
	cooking_logo.setImageBitmap(BitmapUtil.readBitMap(this, DataInit.getDataByKey(imageid)));
} catch (NotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}

@Override
public void onClick(View v) {
// TODO Auto-generated method stub
StartActivityUtil.startActivityForResult(this, CookingActivity.class,
		bundle);
}
}
