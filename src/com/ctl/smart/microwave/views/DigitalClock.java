package com.ctl.smart.microwave.views;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义DigitalClock输出格式
 * 
 * @author 农民伯伯
 * 
 */
public class DigitalClock extends TextView {

	Calendar mCalendar;
	private final static String m12 = "h:mm aa";// h:mm:ss aa
	private final static String m24 = "k:mm";// k:mm:ss
	private FormatChangeObserver mFormatChangeObserver;

	private boolean mAttached = false;

	String mFormat;

	public DigitalClock(Context context) {
		super(context);
		initClock(context);
	}

	public DigitalClock(Context context, AttributeSet attrs) {
		super(context, attrs);
		initClock(context);
	}

	private void initClock(Context context) {
		if (mCalendar == null) {
			mCalendar = Calendar.getInstance();
		}

		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_TIME_CHANGED);
		filter.addAction(Intent.ACTION_TIME_TICK);
		filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
		getContext().registerReceiver(receiver, filter);
		mFormatChangeObserver = new FormatChangeObserver();
		getContext().getContentResolver().registerContentObserver(
				Settings.System.CONTENT_URI, true, mFormatChangeObserver);

		
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mAttached)
			return;
		mAttached = true;
		setFormat();
		initTime();
	}

	private void initTime() {
		mCalendar.setTimeInMillis(System.currentTimeMillis());
		setText(DateFormat.format(mFormat, mCalendar));
		invalidate();
		if (changeListener!=null) {
			changeListener.onTimeChange(mCalendar);
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (!mAttached)
			return;
		mAttached = false;
		getContext().unregisterReceiver(receiver);

		getContext().getContentResolver().unregisterContentObserver(
				mFormatChangeObserver);
	}

	/**
	 * Pulls 12/24 mode from system settings
	 */
	private boolean get24HourMode() {
		return android.text.format.DateFormat.is24HourFormat(getContext());
	}

	private void setFormat() {
		if (get24HourMode()) {
			mFormat = m24;
		} else {
			mFormat = m12;
		}
	}

	private class FormatChangeObserver extends ContentObserver {
		public FormatChangeObserver() {
			super(new Handler());
		}

		@Override
		public void onChange(boolean selfChange) {
			if (selfChange) {
				setFormat();
				initTime();
			}
		}
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_TIMEZONE_CHANGED)
					|| action.equals(Intent.ACTION_TIME_CHANGED)
					|| action.equals(Intent.ACTION_TIME_TICK)) {
				mCalendar = Calendar.getInstance();
				setFormat();
				initTime();
			}

		}
	};
	public interface TimeChangeListener{
		public abstract void onTimeChange(Calendar calendar);
	}
	private TimeChangeListener changeListener;
	public void setTimeChangeListener(TimeChangeListener changeListener){
		this.changeListener=changeListener;
	}
}