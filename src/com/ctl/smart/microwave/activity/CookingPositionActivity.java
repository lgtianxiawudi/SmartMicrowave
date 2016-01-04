package com.ctl.smart.microwave.activity;

import java.util.Map;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.ab.activity.AbActivity;
import com.ab.cache.image.AbImageBaseCache;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.favorite.FavoriteActivity;
import com.ctl.smart.microwave.utils.BitmapUtil;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.ExcelUtil;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import android.view.WindowManager;

public class CookingPositionActivity extends AbActivity implements OnClickListener{
			
	private String name = "";

	private Bundle bundle;
	
	private String imageid = "";
	
	private ImageView imageView;
	private AnimationDrawable ad;
	private String position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setAbContentView(R.layout.position);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		bundle = getIntent().getExtras();

		if (bundle != null) {
			name = bundle.getString("title");
			imageid = bundle.getString("imageid");
			position = bundle.getString("position");
		}
		
		if (TextUtils.isEmpty(position)) {
			position="15";
		}
		
		imageView=(ImageView)findViewById(R.id.position_view);
		
		ad = new AnimationDrawable();  
		String[] contents = {};
		
		if ("1".equals(position)) {
			contents=getResources().getStringArray(R.array.bottom1);
		}else if ("4".equals(position)) {
			contents=getResources().getStringArray(R.array.bottom4);
		}else if ("5".equals(position)) {
			contents=getResources().getStringArray(R.array.bottom5);
		}else if ("14".equals(position)) {
			contents=getResources().getStringArray(R.array.bottom14);
		}else if ("15".equals(position)) {
			contents=getResources().getStringArray(R.array.bottom15);
		}else if ("16".equals(position)) {
			contents=getResources().getStringArray(R.array.bottom16);
		}else if ("134".equals(position)) {
			contents=getResources().getStringArray(R.array.bottom134);
		}
		
		for (String s : contents) {
			Drawable drawable=new BitmapDrawable(BitmapUtil.readBitMap(this, s));
			ad.addFrame(drawable, 100); 
		}
        ad.setOneShot(false);//true则只运行一次，false可以循环  
          
        imageView.setBackgroundDrawable(ad);  
		
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
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (ad!=null) {
			ad.start();
		}
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (ad!=null) {
			ad.stop();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
