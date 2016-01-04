package com.ctl.smart.microwave.serialport;

import android.util.Log;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.Vector;

public class SerialPortFinder
{
  private static final String TAG = "SerialPort";
  private Vector<Driver> mDrivers = null;

  public String[] getAllDevices()
  {
    Vector localVector = new Vector();
    try
    {
      Iterator localIterator1 = getDrivers().iterator();
      while (localIterator1.hasNext())
      {
        Driver localDriver = (Driver)localIterator1.next();
        Iterator localIterator2 = localDriver.getDevices().iterator();
        while (localIterator2.hasNext())
        {
          String str = ((File)localIterator2.next()).getName();
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = str;
          arrayOfObject[1] = localDriver.getName();
          localVector.add(String.format("%s (%s)", arrayOfObject));
        }
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return (String[])localVector.toArray(new String[localVector.size()]);
  }

  public String[] getAllDevicesPath()
  {
    Vector localVector = new Vector();
    try
    {
      Iterator localIterator1 = getDrivers().iterator();
      while (localIterator1.hasNext())
      {
        Iterator localIterator2 = ((Driver)localIterator1.next()).getDevices().iterator();
        while (localIterator2.hasNext())
          localVector.add(((File)localIterator2.next()).getAbsolutePath());
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return (String[])localVector.toArray(new String[localVector.size()]);
  }

  Vector<Driver> getDrivers()
    throws IOException
  {
    if (this.mDrivers == null)
    {
      this.mDrivers = new Vector();
      LineNumberReader localLineNumberReader = new LineNumberReader(new FileReader("/proc/tty/drivers"));
      while (true)
      {
        String str1 = localLineNumberReader.readLine();
        if (str1 == null)
          break;
        String str2 = str1.substring(0, 21).trim();
        String[] arrayOfString = str1.split(" +");
        if ((arrayOfString.length < 5) || (!arrayOfString[(-1 + arrayOfString.length)].equals("serial")))
          continue;
        Log.d("SerialPort", "Found new driver " + str2 + " on " + arrayOfString[(-4 + arrayOfString.length)]);
        this.mDrivers.add(new Driver(str2, arrayOfString[(-4 + arrayOfString.length)]));
      }
      localLineNumberReader.close();
    }
    return this.mDrivers;
  }

  public class Driver
  {
    private String mDriverName;
    private String mDriverRoot;
    Vector<File> mDrivers;

    public Driver(String paramString1, String arg3)
    {
      this.mDriverName = paramString1;
  
      this.mDriverRoot = arg3;
    }

    public Vector<File> getDevices()
    {
      if (this.mDrivers == null)
      {
        this.mDrivers = new Vector();
        File[] arrayOfFile = new File("/dev").listFiles();
        for (int i = 0; i < arrayOfFile.length; i++)
        {
          if (!arrayOfFile[i].getAbsolutePath().startsWith(this.mDriverRoot))
            continue;
          Log.d("SerialPort", "Found new device: " + arrayOfFile[i]);
          this.mDrivers.add(arrayOfFile[i]);
        }
      }
      return this.mDrivers;
    }

    public String getName()
    {
      return this.mDriverName;
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\Android逆向助手\classes_dex2jar.jar
 * Qualified Name:     com.twowayradio.serialport.SerialPortFinder
 * JD-Core Version:    0.6.0
 */
