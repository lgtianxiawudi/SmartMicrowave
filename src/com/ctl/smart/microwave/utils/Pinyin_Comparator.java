package com.ctl.smart.microwave.utils;

import java.util.Comparator;

import com.ctl.smart.microwave.model.SaveModel;

public class Pinyin_Comparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		 SaveModel str1 = (SaveModel)o1;
		 SaveModel str2 = (SaveModel) o2;
	     return str1.getuPingYin().compareTo(str2.getuPingYin());
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

}
