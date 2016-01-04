package com.ctl.smart.microwave.protocol;

import java.io.IOException;
import java.io.OutputStream;
//import com.twowayradio.util.BytesUtil;
import android.util.Log;
public class ProtocolShared
{
  private OutputStream mOutputStream;

  public ProtocolShared(OutputStream paramOutputStream)
  {
     mOutputStream = paramOutputStream;
  }

  byte Setup_Parity(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte i = 0;
    int j = paramInt1 + paramInt2;
    for (int k = paramInt1; k < j; k++)
      i = (byte)(i ^ paramArrayOfByte[k]);
    return i;
  }

  boolean writeOutputStream(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    OutputStream localOutputStream = this.mOutputStream;
    boolean i = false;
	
    if (localOutputStream != null)
    {
      try
      {
       // Log.e("lwxdebug", "writeOutputStream ="+ paramArrayOfByte);
	    
        mOutputStream.write(paramArrayOfByte, paramInt1, paramInt2);
        mOutputStream.flush();
        return i;
    }
    catch (IOException localIOException)
    {

	
      localIOException.printStackTrace();
    }
    }
	else
		 Log.e("lwxdebug", "localOutputStream ==null");
	
    return false;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\Android逆向助手\classes_dex2jar.jar
 * Qualified Name:     com.twowayradio.protocols.ProtocolShared
 * JD-Core Version:    0.6.0
 */