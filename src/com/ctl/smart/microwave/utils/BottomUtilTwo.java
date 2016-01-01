package com.ctl.smart.microwave.utils;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ab.util.AbToastUtil;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.activity.MainActivity;

public class BottomUtilTwo {
	private ImageButton back_main;

	private ImageButton back;
	
	private Button ok;

	private Activity activity;
	
	public BottomUtilTwo(Activity context) {
		this.activity=context;
		back_main = (ImageButton) context.findViewById(R.id.back_main);
		back = (ImageButton) context.findViewById(R.id.back);
		ok = (Button) context.findViewById(R.id.ok);
		TextViewUtils.setCommonSize(ok);
	}


	

	public BottomUtilTwo setBackListener() {
		if (this.back != null) {
				this.back.setVisibility(View.VISIBLE);
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
	public BottomUtilTwo setBackListener(View.OnClickListener onClickListener) {
		if (this.back != null) {
				this.back.setVisibility(View.VISIBLE);
				this.back.setOnClickListener(onClickListener);
		}
		return this;
	}
	public BottomUtilTwo setback_mainListener() {
		
		if (this.back_main != null) {
				this.back_main.setVisibility(View.VISIBLE);
				this.back_main.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//AbToastUtil.showToast(activity, "back_main");
						StartActivityUtil.startActivityFinish(activity, MainActivity.class);
						StartActivityUtil.clearActivity();
					}
				});
		}
		return this;
	}
	public BottomUtilTwo setOkListener(View.OnClickListener onClickListener) {
		if (this.ok != null) {
			if (onClickListener != null) {
				this.ok.setVisibility(View.VISIBLE);
				this.ok.setOnClickListener(onClickListener);
			} else {
				this.ok.setVisibility(View.INVISIBLE);
			}
		}
		return this;
	}
	public BottomUtilTwo setOkListener() {
		if (this.ok != null) {
			this.ok.setVisibility(View.GONE);
		}
		return this;
	}
	
	public void setOkListener(boolean clickabled) {
		this.ok.setClickable(!clickabled);
	}

}
