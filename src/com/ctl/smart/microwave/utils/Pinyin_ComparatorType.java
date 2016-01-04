package com.ctl.smart.microwave.utils;

import java.util.Comparator;

import com.ctl.smart.microwave.model.SaveModel;

public class Pinyin_ComparatorType implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		 SaveModel str1 = (SaveModel)o1;
		 SaveModel str2 = (SaveModel) o2;
	     return str1.getContentNamePY().compareTo(str2.getContentNamePY());
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

}
