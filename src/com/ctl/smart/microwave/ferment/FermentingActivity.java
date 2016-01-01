package com.ctl.smart.microwave.ferment;

import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.util.AbToastUtil;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.utils.BottomUtilThree;
import com.ctl.smart.microwave.utils.DataInit;
import com.ctl.smart.microwave.utils.BottomUtilThree.onChangeStaueListener;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.views.CircleImageView;

public class FermentingActivity extends AbActivity implements
		onChangeStaueListener {
	private String name = "";
	private int imageid = 0;
	private String temperatureStr;
	private int time = 0;
	private String type = "";

	@AbIocView(id = R.id.staue)
	private TextView statu;
	@AbIocView(id = R.id.temperature)
	private TextView temperature;
	@AbIocView(id = R.id.cooking_logo)
	private CircleImageView cooking_logo;

	private BottomUtilThree bottomUtilThree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.cooking);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			name = bundle.getString("title");
			imageid = bundle.getInt("imageid");
			time = bundle.getInt("time");
			temperatureStr = bundle.getString("temperature");
			initTimeAndTem(name);
			if (!TextUtils.isEmpty(temperatureStr)) {
				temperature.setText(temperatureStr);
			}
			if (time == 0) {
				time = 15 * 1000;
			}

		}
		bottomUtilThree = new BottomUtilThree(this, bundle).setBackListener()
				.setSaveListener()
				.setPlayListener(time, FermentFinishActivity.class);
		bottomUtilThree.setChangeStaue(this);
		HeadUtil headUtil = new HeadUtil(this).setTitleName(name);
		try {
			System.out.println(imageid);
			cooking_logo.setImageResource(imageid);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onchangeStaue(int status) {
		// TODO Auto-generated method stub
		switch (status) {
		case BottomUtilThree.PAUSE: {
			statu.setText(R.string.cook_stopping);
			cooking_logo.pauseAnimation();
		}
			break;
		case BottomUtilThree.RUN: {
			statu.setText(R.string.ferment_starting);
			cooking_logo.startAnimation();
			onRun();
		}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (bottomUtilThree != null) {
			bottomUtilThree.setOnDestory();
		}
	}

	private void initTimeAndTem(String name) {
		String[] names = name.split(getString(R.string.point));
		String result = DataInit.getDataByKey(names[names.length - 1]);
		if (result != null) {
			String[] results = result.split("\\|");
			if (results.length == 3) {
				if (results[0].indexOf("微波") != -1) {
					type = type + 1;
				}
				if (results[0].indexOf("烧烤") != -1) {
					type = type + 2;
				}
				if (results[0].indexOf("蒸汽") != -1) {
					type = type + 3;
				}
				if (results[0].indexOf("热风") != -1) {
					type = type + 4;
				}
				time = Integer.parseInt(results[2]) * 1000;
				temperatureStr = results[1];
				if (TextUtils.isEmpty(temperatureStr)
						|| temperatureStr.indexOf("℃") == -1) {
					temperatureStr = "";
				}

			}
		}
		if (!TextUtils.isEmpty(temperatureStr)) {
			temperature.setText(temperatureStr.replace("\n", ""));
		}
	}

	private void onRun() {
		AbToastUtil.showToast(FermentingActivity.this, type + ":" + time + ":"
				+ temperatureStr);

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) { // 监控/拦截/屏蔽返回键
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
