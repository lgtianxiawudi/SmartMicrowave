package com.ctl.smart.microwave.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

public class StringUtil {
	public static SpannableString getSpannStringAbountSize(long min){
		long send=min/60/1000;
		long mint=min/1000%60;
		String content="";
		if (send>0) {
			content=content+send+"分";
		}
		content=content+mint+"秒";
		SpannableString spannableString=new SpannableString(content);
		int positonF=0;
		if ((positonF=content.indexOf("分"))!=-1) {
			spannableString.setSpan(new RelativeSizeSpan(2f), 0, positonF, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		spannableString.setSpan(new RelativeSizeSpan(2f), positonF+1, content.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}
	public static SpannableString getSpannStringAbountSize(long min,String backContent){
		long send=min/60/1000;
		long mint=min/1000%60;
		String content="";
		if (send>0) {
			content=content+send+"分";
		}
		content=content+mint+"秒";
		SpannableString spannableString=new SpannableString(content+backContent);
		int positonF=0;
		if ((positonF=content.indexOf("分"))!=-1) {
			spannableString.setSpan(new RelativeSizeSpan(2f), 0, positonF, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		spannableString.setSpan(new RelativeSizeSpan(2f), positonF+1, content.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}
	public static float pixelToDp(Context context, float val) {
        float density = context.getResources().getDisplayMetrics().density;
        return val * density;
    }
}
