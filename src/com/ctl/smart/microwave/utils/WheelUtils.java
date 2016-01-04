package com.ctl.smart.microwave.utils;

import com.ab.activity.AbActivity;
import com.ab.view.wheel.AbNumericWheelAdapter;
import com.ab.view.wheel.AbStringWheelAdapter;
import com.ctl.smart.microwave.views.AbWheelView;
import com.ctl.smart.microwave.R;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class WheelUtils {
		
		public static void initWheelDatePicker(final AbActivity activity,final AbWheelView mWheelViewH,final AbWheelView mWheelViewM,final AbWheelView mWheelViewS){
			
			
			//设置"年"的显示数据
			mWheelViewH.setLabelTextColor(Color.WHITE);
			mWheelViewH.setValueTextColor(Color.WHITE);
			mWheelViewH.setAdapter(new AbNumericWheelAdapter(0, 4));
			mWheelViewH.setCyclic(true);// 可循环滚动
			mWheelViewH.setLabel("");  // 添加文字
			mWheelViewH.setCurrentItem(0);// 初始化时显示的数据
			mWheelViewH.setValueTextSize(50);
			mWheelViewH.setLabelTextSize(50);
//			mWheelViewH.setCenterSelectDrawable(activity.getResources().getDrawable(R.drawable.wheel_select));
			
			// 月
			mWheelViewM.setLabelTextColor(Color.WHITE);
			mWheelViewM.setValueTextColor(Color.WHITE);
			mWheelViewM.setAdapter(new AbNumericWheelAdapter(0, 59));
			mWheelViewM.setCyclic(true);
			mWheelViewM.setLabel("");
			mWheelViewM.setCurrentItem(0);
			mWheelViewM.setValueTextSize(50);
			mWheelViewM.setLabelTextSize(50);
//			mWheelViewM.setCenterSelectDrawable(activity.getResources().getDrawable(R.drawable.wheel_select));
			
			// 日
			mWheelViewS.setLabelTextColor(Color.WHITE);
			mWheelViewS.setValueTextColor(Color.WHITE);
			mWheelViewS.setAdapter(new AbNumericWheelAdapter(0, 59));
			mWheelViewS.setCyclic(true);
			mWheelViewS.setLabel("");
			mWheelViewS.setCurrentItem(0);
			mWheelViewS.setValueTextSize(50);
			mWheelViewS.setLabelTextSize(50);
//			mWheelViewS.setCenterSelectDrawable(activity.getResources().getDrawable(R.drawable.wheel_select));
			
			
			
	    }
		
		
		public static void initWheelDate(final AbActivity activity,final AbWheelView mWheelViewY,final AbWheelView mWheelViewM,final AbWheelView mWheelViewD){
			
			//设置"年"的显示数据
			mWheelViewY.setLabelTextColor(Color.WHITE);
			mWheelViewY.setValueTextColor(Color.WHITE);
			mWheelViewY.setAdapter(new AbNumericWheelAdapter(1970, 2037));
			mWheelViewY.setCyclic(true);// 可循环滚动
			mWheelViewY.setLabel("");  // 添加文字
			mWheelViewY.setCurrentItem(2015-1970);// 初始化时显示的数据
			mWheelViewY.setValueTextSize(50);
			mWheelViewY.setLabelTextSize(50);
//			mWheelViewH.setCenterSelectDrawable(activity.getResources().getDrawable(R.drawable.wheel_select));
			
			// 月
			mWheelViewM.setLabelTextColor(Color.WHITE);
			mWheelViewM.setValueTextColor(Color.WHITE);
			mWheelViewM.setAdapter(new AbNumericWheelAdapter(1, 12));
			mWheelViewM.setCyclic(true);
			mWheelViewM.setLabel("");
			mWheelViewM.setCurrentItem(0);
			mWheelViewM.setValueTextSize(50);
			mWheelViewM.setLabelTextSize(50);
//			mWheelViewM.setCenterSelectDrawable(activity.getResources().getDrawable(R.drawable.wheel_select));
			
			// 日
			mWheelViewD.setLabelTextColor(Color.WHITE);
			mWheelViewD.setValueTextColor(Color.WHITE);
			mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 31));
			mWheelViewD.setCyclic(true);
			mWheelViewD.setLabel("");
			mWheelViewD.setCurrentItem(0);
			mWheelViewD.setValueTextSize(50);
			mWheelViewD.setLabelTextSize(50);
//			mWheelViewS.setCenterSelectDrawable(activity.getResources().getDrawable(R.drawable.wheel_select));
			
	    }
		
		
		public static void initWheelTime(final AbActivity activity,final AbWheelView mWheelViewY,final AbWheelView mWheelViewM,final AbWheelView mWheelViewD){
			
			List<String> items = new ArrayList<String>();
			items.add(activity.getResources().getString(R.string.time_am));
			items.add(activity.getResources().getString(R.string.time_pm));
			
			//设置"时"的显示数据
			mWheelViewY.setLabelTextColor(Color.WHITE);
			mWheelViewY.setValueTextColor(Color.WHITE);
			mWheelViewY.setAdapter(new AbStringWheelAdapter(items));
			mWheelViewY.setCyclic(false);// 可循环滚动
			mWheelViewY.setLabel("");  // 添加文字
			mWheelViewY.setCurrentItem(0);// 初始化时显示的数据
			mWheelViewY.setValueTextSize(50);
			mWheelViewY.setLabelTextSize(50);
//			mWheelViewH.setCenterSelectDrawable(activity.getResources().getDrawable(R.drawable.wheel_select));
			
			// 分
			mWheelViewM.setLabelTextColor(Color.WHITE);
			mWheelViewM.setValueTextColor(Color.WHITE);
			mWheelViewM.setAdapter(new AbNumericWheelAdapter(0, 12));
			mWheelViewM.setCyclic(true);
			mWheelViewM.setLabel("");
			mWheelViewM.setCurrentItem(0);
			mWheelViewM.setValueTextSize(50);
			mWheelViewM.setLabelTextSize(50);
//			mWheelViewM.setCenterSelectDrawable(activity.getResources().getDrawable(R.drawable.wheel_select));
			
			// 日
			mWheelViewD.setLabelTextColor(Color.WHITE);
			mWheelViewD.setValueTextColor(Color.WHITE);
			mWheelViewD.setAdapter(new AbNumericWheelAdapter(0, 59));
			mWheelViewD.setCyclic(true);
			mWheelViewD.setLabel("");
			mWheelViewD.setCurrentItem(0);
			mWheelViewD.setValueTextSize(50);
			mWheelViewD.setLabelTextSize(50);
//			mWheelViewS.setCenterSelectDrawable(activity.getResources().getDrawable(R.drawable.wheel_select));
			
	    }
}
