package com.ctl.smart.microwave.serialport;

import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class SerialPort
{
  private static final String TAG = "SerialPort";
  private FileDescriptor mFd;
  private FileInputStream mFileInputStream;
  private FileOutputStream mFileOutputStream;

  public SerialPort(File paramFile, int paramInt1, int paramInt2)
    throws SecurityException, IOException
  {
   	  /*  
     if ((!paramFile.canRead()) || (!paramFile.canWrite()))
	
      try
      {
      
        Process localProcess = Runtime.getRuntime().exec("/system/bin/su");
		
        String str = "chmod 777 " + paramFile.getAbsolutePath() + "\n" + "exit\n";
        localProcess.getOutputStream().write(str.getBytes());
        if ((localProcess.waitFor() != 0) || (!paramFile.canRead()) || (!paramFile.canWrite()))
          throw new SecurityException();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        throw new SecurityException();
      }*/
	  
      this.mFd = open(paramFile.getAbsolutePath(), paramInt1, paramInt2);
    if (this.mFd == null)
    {
      Log.e("lwxdebug", "native open returns null");
      throw new IOException();
    }
    mFileInputStream = new FileInputStream(this.mFd);
    mFileOutputStream = new FileOutputStream(this.mFd);

	
  }


	public InputStream getInputStream() {
		return mFileInputStream;
	}

	public OutputStream getOutputStream() {
		return mFileOutputStream;
	}

	// JNI
	private native static FileDescriptor open(String path, int baudrate, int flags);
	public native static void IntercomStart();
	public native static void IntercomStop();
	public native void close();

	static {
		System.loadLibrary("serial_port");
	}
}

/* Location:           C:\Documents and Settings\Administrator\桌面\Android逆向助手\classes_dex2jar.jar
 * Qualified Name:     com.twowayradio.serialport.SerialPort
 * JD-Core Version:    0.6.0
 */
