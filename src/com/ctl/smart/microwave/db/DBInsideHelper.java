package com.ctl.smart.microwave.db;

import android.content.Context;

import com.ab.db.orm.AbDBHelper;
import com.ctl.smart.microwave.model.SaveModel;

public class DBInsideHelper extends AbDBHelper {
	// 数据库名
	private static final String DBNAME = "SmartMicrowave.db";
    
    // 当前数据库的版本
	private static final int DBVERSION = 1;
	// 要初始化的表
	private static final Class<?>[] clazz = {SaveModel.class};

	public DBInsideHelper(Context context) {
		super(context, DBNAME, null, DBVERSION, clazz);
	}

}
