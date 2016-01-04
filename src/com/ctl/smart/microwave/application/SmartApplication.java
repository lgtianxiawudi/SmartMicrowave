package com.ctl.smart.microwave.application;

import com.ab.global.AbAppConfig;
import com.ctl.smart.microwave.utils.DataInit;
import com.ctl.smart.microwave.utils.ExcelUtil;

import android.app.Application;

public class SmartApplication extends Application {
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		try {
			DataInit.init(getApplicationContext());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ExcelUtil.readExcelFile(getApplicationContext());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int maxMemory = (int) Runtime.getRuntime().maxMemory();  
        int mCacheSize = maxMemory / 8;
        System.out.println(AbAppConfig.MAX_CACHE_SIZE_INBYTES);
        AbAppConfig.MAX_CACHE_SIZE_INBYTES=mCacheSize;
	}
}
