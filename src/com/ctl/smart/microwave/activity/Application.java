package com.ctl.smart.microwave.activity;

import android.content.SharedPreferences;
import  com.ctl.smart.microwave.serialport.SerialPort;
import  com.ctl.smart.microwave.serialport.SerialPortFinder;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import com.ctl.smart.microwave.utils.DataInit;
import com.ctl.smart.microwave.utils.ExcelUtil;

public class Application extends android.app.Application
{
  private SerialPort mSerialPort = null;
  public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
	private int baudrate = 9600;
	@Override
	public void onCreate() {
		super.onCreate();
		try {
			DataInit.init(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    try {
			ExcelUtil.readExcelFile(getApplicationContext());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
  public void closeSerialPort()
  {
    if (this.mSerialPort != null)
    {
      this.mSerialPort.close();
      this.mSerialPort = null;
    }
  }

  public SerialPort getSerialPort()
    throws SecurityException, IOException
  {
    if (this.mSerialPort == null)
    {
     // SharedPreferences localSharedPreferences = getSharedPreferences("com.android.twowayradio_preferences", 0);

	  
     // String str = localSharedPreferences.getString("DEVICE", "/dev/ttyMT2");
      //int i = Integer.decode(localSharedPreferences.getString("BAUDRATE", "57600")).intValue();

	  String str="/dev/ttyMT1";
	  
      if ((str.length() == 0) )
        throw new InvalidParameterException();
	  
      this.mSerialPort = new SerialPort(new File(str),baudrate, 0);
    }
    return this.mSerialPort;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\Android逆向助手\classes_dex2jar.jar
 * Qualified Name:     com.twowayradio.ui.Application
 * JD-Core Version:    0.6.0
 */