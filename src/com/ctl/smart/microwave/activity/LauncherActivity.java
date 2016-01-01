package com.ctl.smart.microwave.activity;


import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.utils.StartActivityUtil;

/**
 * 
 * Description: 欢迎页面
 * 
 * @author:
 * @version:
 */
public class LauncherActivity extends AbActivity {

	// private LinearLayout launcherView;
	private Animation mFadeIn;

	private Animation mFadeInScale;

	@AbIocView(id = R.id.lunch_img)
	private ImageView logo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.launcher);

		init();
		setListener();

	}

	private void setListener() {

		mFadeIn.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				logo.startAnimation(mFadeInScale);
			}
		});

		mFadeInScale.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				logo.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				StartActivityUtil.startActivityFinish(LauncherActivity.this, MainActivity.class);
			}
		});

	}

	private void init() {
		initAnim();
		logo.startAnimation(mFadeInScale);
	}

	private void initAnim() {
		mFadeIn = AnimationUtils.loadAnimation(LauncherActivity.this,
				R.anim.welcome_fade_in);
		mFadeIn.setDuration(500);
		mFadeIn.setFillAfter(true);

		mFadeInScale = AnimationUtils.loadAnimation(LauncherActivity.this,
				R.anim.welcome_fade_in_scale);
		mFadeInScale.setDuration(800);
		mFadeInScale.setFillAfter(true);
	}

}
