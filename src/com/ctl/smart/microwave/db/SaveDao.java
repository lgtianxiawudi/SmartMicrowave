package com.ctl.smart.microwave.db;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.ctl.smart.microwave.model.SaveModel;

public class SaveDao extends AbDBDaoImpl<SaveModel> {
	public SaveDao(Context context) {
		super(new DBInsideHelper(context),SaveModel.class);
	}
}