package com.ctl.smart.microwave.utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.activity.MainActivity;
import com.ctl.smart.microwave.views.AdvancedCountdownTimer;

public class BottomUtilOne {

	private ImageButton back;

	private ImageButton back_main;
	

	private TextView time;

	private Activity activity;

	private MyCount count;

	public BottomUtilOne(Activity context) {
		this.activity = context;
		back = (ImageButton) context.findViewById(R.id.back);
		time = (TextView) context.findViewById(R.id.time_one);
		back_main = (ImageButton) context.findViewById(R.id.back_main);
		TextViewUtils.setCommonSize(time);
		
	}

	public BottomUtilOne setBackListener() {
		if (this.back != null) {
			this.back.setVisibility(View.VISIBLE);
//			this.time.setVisibility(View.VISIBLE);
			this.back.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					StartActivityUtil.backPreActivity(activity);
				}
			});
		}
		return this;
	}


	public BottomUtilOne setAutoPlayListener(final long timeL,
			final Class<?> cls, final String name) {
		if (this.time != null) {
			this.time.setText(StringUtil.getSpannStringAbountSize(timeL));
			this.time.setVisibility(View.VISIBLE);
			count = new MyCount(timeL, 1000, cls, name);
			count.start();
			if (changeStaue != null) {
				changeStaue.onchangeStaue(RUN);
			}
		}

		return this;
	}

	public BottomUtilOne setTime(long timeL) {
		if (this.time!=null) {
			this.time.setVisibility(View.VISIBLE);
			this.time.setText(StringUtil.getSpannStringAbountSize(timeL));
		}
		
		return this;
	}

	public BottomUtilOne setBackMainListener() {
		if (this.back_main != null) {
			this.back_main.setVisibility(View.VISIBLE);
			this.back_main.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					StartActivityUtil.startActivityFinish(activity, MainActivity.class);
					StartActivityUtil.clearActivity();
				}
			});
		} else {
			this.back_main.setVisibility(View.GONE);
		}
		return this;
	}

	/* 定义一个倒计时的内部类 */
	class MyCount extends AdvancedCountdownTimer {

		private Class<?> cls;

		private String name;

		public MyCount(long millisInFuture, long countDownInterval,
				Class<?> cls, String name) {
			super(millisInFuture, countDownInterval);
			this.cls = cls;
			this.name = name;
		}

		@Override
		public void onFinish() {
			Bundle bundle = new Bundle();
			bundle.putString("title", name);
			StartActivityUtil.startActivityFinish(activity, cls, bundle);
			StartActivityUtil.clearActivity();
		}

		@Override
		public void onTick(long millisUntilFinished, int percent) {
			// TODO Auto-generated method stub
			time.setText(StringUtil
					.getSpannStringAbountSize(millisUntilFinished));
			if (currentPercent != null) {
				currentPercent.getCurrentPercen(millisUntilFinished,percent);
			}
		}
	}

	public interface onChangeStaueListener {
		public abstract void onchangeStaue(int statu);
	};

	public interface CurrentPercent {
		public abstract void getCurrentPercen(long millisUntilFinished, int percent);
	};

	public final static int PAUSE = 0;
	public final static int RUN = 1;
	private onChangeStaueListener changeStaue;

	public onChangeStaueListener getChangeStaue() {
		return changeStaue;
	}

	public void setChangeStaue(onChangeStaueListener changeStaue) {
		this.changeStaue = changeStaue;
	}

	private CurrentPercent currentPercent;

	public CurrentPercent getCurrentPercent() {
		return currentPercent;
	}

	public BottomUtilOne setCurrentPercent(CurrentPercent currentPercent) {
		this.currentPercent = currentPercent;
		return this;
	}
	public void setOnDestory(){
			if (count!=null) {
				count.cancel();
			}
	}
}
