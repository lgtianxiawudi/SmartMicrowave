package com.ctl.smart.microwave.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.ab.cache.image.AbImageBaseCache;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.activity.CookingActivity;
import com.ctl.smart.microwave.activity.CookingPositionActivity;
import com.ctl.smart.microwave.automenu.FourLevelAutoMenuActivity;
import android.util.Log;

public class StartActivityUtil {
	
	private static Map<String , Activity> listActivity=new HashMap<String, Activity>();
	
	public static void startActivityFinish(Activity context,Class<?> cls){
		Intent intent=new Intent(context, cls);
		context.startActivity(intent);
		context.finish();
	}
	public static void startActivityFinish(Activity context,Class<?> cls,Bundle bundle){
		Intent intent=new Intent(context, cls);
		intent.putExtras(bundle);
		context.startActivity(intent);
		context.finish();
	}
	public static void startActivityForResult(Activity context,Class<?> cls){
		Intent intent=new Intent(context, cls);
		context.startActivityForResult(intent,0);
		addActivity(context);
	}
	public static void startActivityForResult(Activity context,Class<?> cls,Bundle bundle){
		Intent intent=new Intent(context, cls);
		intent.putExtras(bundle);
		context.startActivityForResult(intent,0);
		addActivity(context);
	}
	public static void backPreActivity(Activity context){
		context.setResult(0);
		context.finish();
	}
	
	public static void addActivity(Activity activity){
		if (activity==null) {
			return;
		}
		listActivity.put(activity.getComponentName().getClassName(), activity);
	}
	
	public static void removeActivity(Activity activity){
		if (activity==null) {
			return;
		}
		System.out.println(activity.getComponentName().getClassName());
		listActivity.remove(activity.getComponentName().getClassName());
//		if (listActivity.size()==0) {
//			AbImageBaseCache cache = AbImageBaseCache.getInstance();
//			cache.clearBitmap();
//		}
	}
	public static void clearActivity(){
		for (Entry<String , Activity> iterable_element : listActivity.entrySet()) {
			String key=iterable_element.getKey();
			Activity activity=iterable_element.getValue();
			activity.finish();
		}
		AbImageBaseCache cache = AbImageBaseCache.getInstance();
		cache.clearBitmap();
	}
	
	public static void startActivityOther(Activity context,Class<?> cls,Bundle bundle,String name,String parents,int positon,String selectString,int state){

		if (bundle == null) {
			bundle = new Bundle();
		}
		bundle.putString("title", name);
		bundle.putString("parents", parents);
		bundle.putString("key", selectString);
		Log.e("lwxdebug", " startActivityOther positon"+positon);

		String[] kinds = ExcelUtil.getKindsByKey(selectString);

		if (kinds != null) {
			StartActivityUtil.startActivityForResult(context,
					cls, bundle);
			return;
		}

		 kinds = ExcelUtil.getKindsByKey(parents + selectString);

		if (kinds != null) {
			StartActivityUtil.startActivityForResult(context,
					cls, bundle);
			return;
		}

		Map<String, String> map = ExcelUtil.getDetailByKey(selectString);
		if (map != null && !TextUtils.isEmpty(map.get("4"))) {
			StartActivityUtil.startActivityForResult(context,
					cls, bundle);
			return;
		}
		if (map==null) {
			System.out.println("map == null"+selectString);
			map=ExcelUtil.getDetailByKey(parents);
		}
		if (map==null) {
			System.out.println("map == null"+parents);
			return;
		}
		if (state==0) {
			switch (positon) {
			case 0:
			{
				bundle.putString("time", map.get("5"));
			}
				break;
			case 1:
			{
				bundle.putString("time", map.get("7"));
			}
				break;
			case 2:
			{
				bundle.putString("time", map.get("9"));
			}
				break;
			case 3:
			{
				bundle.putString("time", map.get("11"));
			}
				break;
			default:
				break;
			}
		}else{
			bundle.putString("time", map.get("5"));
		}
		bundle.putString("imageid", ""+map.get("0"));
		bundle.putString("temperature", map.get("13"));
		bundle.putString("position", map.get("14"));
		bundle.putString("contion", map.get("2"));
		bundle.putString("needwater", map.get("3"));
		bundle.putString("constanttem", map.get("16"));

		
		String autoPauseTime=map.get("15");

		Log.e("lwxdebug", " startActivityOther positon1"+map.get("14"));
		
		if (!TextUtils.isEmpty(autoPauseTime)) {
			bundle.putString("autoPauseTime", autoPauseTime);
		}
		if("牛肉干".equals(selectString)){
            StartActivityUtil.startActivityForResult(context, CookingActivity.class,
				bundle);
		}else{
		      StartActivityUtil.startActivityForResult(context, CookingPositionActivity.class,
				bundle);
		}
	
	}
}
