package com.ctl.smart.microwave.utils;

import java.util.Comparator;

import com.ctl.smart.microwave.model.SaveModel;

public class Pinyin_ComparatorTime implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		 SaveModel str1 = (SaveModel)o1;
		 SaveModel str2 = (SaveModel) o2;
	     return str1.getCreadtime()>str2.getCreadtime()?1:0;
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

}
