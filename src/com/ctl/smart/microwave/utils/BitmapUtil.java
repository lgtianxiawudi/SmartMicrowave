package com.ctl.smart.microwave.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.ab.cache.image.AbImageBaseCache;

public class BitmapUtil {
	/**
	 * 以最省内存的方式读取本地资源的图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		AbImageBaseCache cache = AbImageBaseCache.getInstance();
		Bitmap bitmap = cache.getBitmap(resId + "");
		if (bitmap != null) {
			return bitmap;
		}
		
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		bitmap = BitmapFactory.decodeStream(is, null, opt);
		cache.putBitmap(resId + "", bitmap);
		return bitmap;
	}
	/**
	 * 以最省内存的方式读取本地资源的图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, String name) {
		if (TextUtils.isEmpty(name)) {
			return null;
		}
		AbImageBaseCache cache = AbImageBaseCache.getInstance();
		Bitmap bitmap = cache.getBitmap(name + "");
		if (bitmap != null) {
			return bitmap;
		}
		
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		ApplicationInfo appInfo = context.getApplicationInfo();
		
		int resID = context.getResources().getIdentifier(name, "drawable", appInfo.packageName);
		
		InputStream in = null;
		
		if (resID != 0) {
			in = context.getResources().openRawResource(resID);
		}else{
			try {
				
				String fileName = "caidannew_new/"+name+"";
				
				if (name.startsWith("img_dish")) {
					fileName+=".jpg";
				}else{
					fileName+=".png";
				}
				
				in = context.getResources().getAssets().open(fileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if ( in != null) {
			
			bitmap = BitmapFactory.decodeStream(in, null, opt);
			cache.putBitmap(name + "", bitmap);
			
		}
		return bitmap;
	}
}
