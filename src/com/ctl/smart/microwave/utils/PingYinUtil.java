package com.ctl.smart.microwave.utils;


public class PingYinUtil {
	/**
	 * 将字符串中的中文转化为拼音,其他字符不变
	 * 
	 * @param inputString
	 * @return
	 */
	public static String getPingYin(String inputString) {
		String output = Trans2PinYin.trans2PinYin(inputString).toUpperCase();
		return output;
	}
}
