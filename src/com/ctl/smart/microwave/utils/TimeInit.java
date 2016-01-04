package com.ctl.smart.microwave.utils;

public class TimeInit {

	public static int timeStrToInt(String timeStr){
		if (timeStr.indexOf(":")!=-1) {
			String time[] = timeStr.split(":");
			int len=time.length;
			int s=Integer.parseInt(time[len-1]);
			if (len>1) {
				s=Integer.parseInt(time[len-2])*60+s;
			}
			if (len>2) {
				s=Integer.parseInt(time[len-3])*60*60+s;
			}
			return s*1000;
		}
		
		return 0;
	}
	
}
