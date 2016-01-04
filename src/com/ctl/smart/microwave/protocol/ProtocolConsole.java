package  com.ctl.smart.microwave.protocol;

import java.io.OutputStream;
import com.ctl.smart.microwave.serialport.SerialPort;
import android.util.Log;
import com.ctl.smart.microwave.protocol.ProtocolShared;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import com.ctl.smart.microwave.protocol.temperturecontrolmodule;
public class ProtocolConsole
{
  private byte[] mContactId;
  private boolean mIsHeadsetFlag = false;
  private boolean voicesend=false;
  
  private OutputStream mOutputStream = null;
  private ProtocolShared mShared = null;

  private static int OvenTemperature = 0;   
  private static int g_u8DiffHeatTemperature = 0;
  private static int u16_RelayControlTimer = 0;	 
  private static int g_b1RelayControlFlag = 0;
  private static int g_u8SettingTemperatureBackup = 0;

  public static final byte[] mBuff = new byte[32];

  public static final  temperturecontrolmodule[] tmp_mod_list=new temperturecontrolmodule[8] ;
  
  private static int WORK_OFF = 0;
  private static int WORK_ON = 1;
  private static int WORK_PAUSE=2;
  private static int S_BREAK=1;
  private static int S_CONNECT =0;
  
  private static final int	 STA_DRYOUT =					 0	;			
  private static final int	 STA_POWER_CONTROL	=			 1;				 
  private static final int		 STA_END	=					 2	;			
  private static final int	 DEFAULT_RUNTIME	=			 40 	;		
  private static final int		 DEFAULT_WATERTIME=				 2	;	
   private static final int	  PumbAdc=						  130;			

  public ProtocolConsole(OutputStream mOutputStream)
  {
      mShared = new ProtocolShared(mOutputStream);
      int_mod_list();

     
  }

  public static int bytesToInt(byte[] src, int offset) {  
	  int value;	
	  value = (int) ((src[offset+1] & 0xFF)	
			  | ((src[offset] & 0xFF)<<8));	
	  return value;  
  }  


  private byte[] ctcToByteArray(int paramInt)
  {
    byte[] arrayOfByte = new byte[2];
    arrayOfByte[0] = (byte)(0xFF & paramInt >> 8);
    arrayOfByte[1] = (byte)(paramInt & 0xFF);
    return arrayOfByte;
  }

  private byte[] freqToByteArray(double paramDouble)
  {
    long l = (long)(1000000.0D * paramDouble);
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = (byte)(int)(0xFF & l >> 24);
    arrayOfByte[1] = (byte)(int)(0xFF & l >> 16);
    arrayOfByte[2] = (byte)(int)(0xFF & l >> 8);
    arrayOfByte[3] = (byte)(int)(l & 0xFF);
    return arrayOfByte;
  }

  private byte getchecksum(byte[] data, int len)
  {
     int i=0;
	 byte total=0x00;
	 for(i=0;i<len;i++)
	 {
       total=(byte)(data[i]+total);
	 }
	 return (byte)(total&0xFF);
  }


   public static String replaceBlank(String str) {
		  String dest = "";
		  if (str!=null) {
			  Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			  Matcher m = p.matcher(str);
			  dest = m.replaceAll("");
		  }
		  return dest;
	  }

  public int gettemperature(String tem ,String type)
  {
     int index=0;
    if(isweibo(type))
    {
       index=replaceBlank(tem).indexOf("W");
    }
	else
	{
       index=replaceBlank(tem).indexOf("\u2103");
	   Log.e("lwxdebug", "index ="+index);
	
	   if(index==-1)
	   {
          index=replaceBlank(tem).indexOf("°C");
	   }
		  Log.e("lwxdebug", "index ="+index);
	}
	
	if(index>0)
	{
       return  Integer.parseInt(tem.substring(0,index)) ;
	}
	else
	{
        return 0;
	}
  }
  private boolean sleep(long paramLong)
  {
    try
    {
      Thread.sleep(paramLong);
      return true;
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
    return false;
  }

  public boolean issteam(String type)
  {
      if(type!=null)
     	{
          if(type.indexOf("3")!=-1)
  	      return true;
  	      else 
  	      return false;
     	}
	  
	  return false;
  }


  public boolean isweibo(String type)
  { 
     if(type!=null)
     {
       if(type.indexOf("1")!=-1)
  	     return true;
  	   else 
  	     return false;
     }
	 return false;
  }


  public boolean isrefeng(String type)
  {
      if(type!=null)
      {
       if(type.indexOf("4")!=-1)
  	     return true;
  	   else 
  	     return false;
      }
	  return false;
  }


  public boolean setmicrowaveon (String type , int time , int temperature )
  {
     Arrays.fill(mBuff, (byte)0);
	 
     mBuff[0]=0x10;
	 mBuff[1]=0x00;
     mBuff[2]=0x0F;
	 
	 mBuff[3]=0x02;
	 Log.e("lwxdebug", "send setmicrowaveon");
     if(type.indexOf("1")!=-1)//weibo
     {
        mBuff[4]=0x02;

		
		//Log.e("lwxdebug", "send setmicrowaveon tem="+temperature);
		mBuff[11]=(byte)(0xFF & temperature >> 8);;
        mBuff[12]=(byte)(temperature & 0xFF);
		
     }

     if(type.indexOf("2")!=-1)//shaokao
     {
        mBuff[6]=0x02;
		mBuff[8]=0x01	;  //fengxian
     }
	 
	 if(type.indexOf("3")!=-1)//zengqi
	 {
	    mBuff[5]=0x02;
		mBuff[8]=0x01	;  //fengxian
	 }
	 
	 if(type.indexOf("4")!=-1) //refeng
	 {
	    mBuff[7]=0x02;
		mBuff[8]=0x02;  //fengxian
	 }
     
	
	 
     mBuff[9]=0x02;  //leng fengfengxian

     mBuff[17]=0x02;  // lu deng
	 
     
	 mBuff[18]=getchecksum(mBuff,18);
	 

	 
	 for(int i=0;i<19;i++)
	 {

		  // Log.e("lwxdebug", "send setmicrowaveon[6]="+mBuff[i]);
	 }

	 sleep(50L); 
	 mShared.writeOutputStream(mBuff, 0, 19);

	 return true;
  }


	


    public boolean setmicrowavepause (String type )
    {
        Arrays.fill(mBuff, (byte)0);
		  
		  mBuff[0]=0x10;
		  mBuff[1]=0x00;
		  mBuff[2]=0x0F;
		  
		  mBuff[3]=0x02;
		  
		  if(type.indexOf("1")!=-1)//weibo
		  {
			 mBuff[4]=0x01;
		  }
	   
		  if(type.indexOf("2")!=-1)//shaokao
		  {
			 mBuff[6]=0x01;
		  }
		  
		  if(type.indexOf("3")!=-1)//zengqi
		  {
			 mBuff[5]=0x01;
		  }
		  
		  if(type.indexOf("4")!=-1) //refeng
		  {
			 mBuff[7]=0x01;
			 mBuff[8]=0x01;  //fengxian
		  }
		  
		  //mBuff[8]=0x02;  //fengxian
	      mBuff[9]=0x01;  //fengxian

		  mBuff[17]=0x02;  // lu deng

		  
		  mBuff[18]=getchecksum(mBuff,18);
		  sleep(50L);
		  mShared.writeOutputStream(mBuff, 0, 19);
		  
			 Log.e("lwxdebug", "send setmicrowavepause");  
		  for(int i=0;i<19;i++)
		  {
		  
				//Log.e("lwxdebug", "send setmicrowavepause[6]="+mBuff[i]);
		  }

	
        return true;
    }

    public boolean adjusttemperaturepoff (String type)
    { 
       
	    Arrays.fill(mBuff, (byte)0);
		  
		  mBuff[0]=0x10;
		  mBuff[1]=0x00;
		  mBuff[2]=0x0F;
		  
		  mBuff[3]=0x02;
		  
		  if(type.indexOf("1")!=-1)//weibo
		  {
			 mBuff[4]=0x01;
		  }
	   
		  if(type.indexOf("2")!=-1)//shaokao
		  {
			 mBuff[6]=0x01;
		  }
		  
		  if(type.indexOf("3")!=-1)//zengqi
		  {
			 mBuff[5]=0x01;
		  }
		  
		  if(type.indexOf("4")!=-1) //refeng
		  {
			 mBuff[7]=0x01;
			 mBuff[8]=0x02;	//fengxian 
		  }
		  
	

		   mBuff[9]=0x02;	//lengfengxian


           mBuff[17]=0x02;  // lu deng
	   
		  mBuff[18]=getchecksum(mBuff,18);
		  sleep(50L);
		  mShared.writeOutputStream(mBuff, 0, 19);
		  
		  	 Log.e("lwxdebug", "send adjusttemperaturepoff");  
		  for(int i=0;i<19;i++)
		  {
		  
				//Log.e("lwxdebug", "send adjusttemperaturepoff[6]="+mBuff[i]);
		  }

	
        return true;
    }

     public boolean opendoor (String type)
    { 
       
	     Arrays.fill(mBuff, (byte)0);
		  
		  mBuff[0]=0x10;
		  mBuff[1]=0x00;
		  mBuff[2]=0x0F;
		  
		  mBuff[3]=0x02;
		  
	
			 mBuff[4]=0x01;
		  
		
			 mBuff[6]=0x01;
	

			 mBuff[5]=0x01;
	
		  
	
			 mBuff[7]=0x01;
	
		  
	
	       mBuff[8]=0x01; //fengxian 

		   mBuff[9]=0x01;	//lengfengxian


           mBuff[17]=0x02;  // lu deng
	   
		  mBuff[18]=getchecksum(mBuff,18);
		  sleep(50L);
		  mShared.writeOutputStream(mBuff, 0, 19);
		  
		  	 Log.e("lwxdebug", "opendoor");  
		  for(int i=0;i<19;i++)
		  {
		  
				//Log.e("lwxdebug", "send adjusttemperaturepoff[6]="+mBuff[i]);
		  }

	
        return true;
    }

	public boolean opendoor (String type,boolean flag)
	   { 
		  
			Arrays.fill(mBuff, (byte)0);
			 
			 mBuff[0]=0x10;
			 mBuff[1]=0x00;
			 mBuff[2]=0x0F;
			 
			 mBuff[3]=0x02;
			 
	   
				mBuff[4]=0x01;
			 
		   
				mBuff[6]=0x01;
	   
	
				mBuff[5]=0x01;
	   
			 
	   
				mBuff[7]=0x01;
	   
			 
	   
			  mBuff[8]=0x01; //fengxian 
	
			  mBuff[9]=0x01;   //lengfengxian 
	        	Log.e("lwxdebug", "opendoor flag="+flag);
	          if(flag)
			  mBuff[17]=0x01;  // lu deng
			  else
			  mBuff[17]=0x00;  // lu deng
		  
			 mBuff[18]=getchecksum(mBuff,18);
			 sleep(50L);
			 mShared.writeOutputStream(mBuff, 0, 19);
			 

			 for(int i=0;i<19;i++)
			 {
			 
				   //Log.e("lwxdebug", "send adjusttemperaturepoff[6]="+mBuff[i]);
			 }
	
	   
		   return true;
	   }


   
    public boolean setmicrowaveoff ()
   { 
	  
	     Arrays.fill(mBuff, (byte)0);
		 
		 mBuff[0]=0x10;
		 mBuff[1]=0x00;
		 mBuff[2]=0x0F;
		 mBuff[3]=0x01;
	
		 mBuff[18]=getchecksum(mBuff,18);

		 for(int i=0;i<18;i++)
		 {
		 
			   //Log.e("lwxdebug", "send setmicrowaveoff[6]="+mBuff[i]);
		 }



		 sleep(50L);
		 mShared.writeOutputStream(mBuff, 0, 19);
   
	     return true;
   }

	public boolean getmicrowavestatus()
	{
     
        Arrays.fill(mBuff, (byte)0);
		mBuff[0]=0x03;
		mBuff[1]=0x0F;
		mBuff[2]=0x0B;
	    mBuff[3]=getchecksum(mBuff,3);
		//mBuff[3]=0x17;
		Log.e("lwxdebug", "getmicrowavestatus");

		
		for(int i=0;i<4;i++)
		{
		
			  //Log.e("lwxdebug", "send getmicrowavestatus[6]="+mBuff[i]);
		}
		sleep(50L);
		mShared.writeOutputStream(mBuff, 0, 4);
		return true;
	}


   public boolean sewaterpumpon (String type,boolean flag,boolean zqflag)
   {
     Arrays.fill(mBuff, (byte)0);
	 
     mBuff[0]=0x10;
	 mBuff[1]=0x00;
     mBuff[2]=0x0F;
	 
	 mBuff[3]=0x02;
	 
     if(type.indexOf("1")!=-1)//weibo
     {
        mBuff[4]=0x02;
     }

	 if(type.indexOf("3")!=-1)//zengqi
	 {
	    if(zqflag)
	    mBuff[5]=0x02;
		else
	    mBuff[5]=0x01;
	 }
	 
     if(type.indexOf("2")!=-1)//shaokao
     {
        mBuff[6]=0x02;
     }
	 

	 if(type.indexOf("4")!=-1) //refeng
	 {
	    mBuff[7]=0x02;
		mBuff[8]=0x02;  //fengxian
	 }
     
		  	 Log.e("lwxdebug", "send sewaterpumpon flag="+flag);  
	 
     mBuff[9]=0x02;  //fengxian
     
     if(flag)
     mBuff[10]=0x02;  //shuibeng  
     else
     mBuff[10]=0x01;  //shuibeng    

	 mBuff[17]=0x02;  // lu deng
     
	 mBuff[18]=getchecksum(mBuff,18);
	 

	 
	 for(int i=0;i<19;i++)
	 {

		 // Log.e("lwxdebug", "send sewaterpumpon="+mBuff[i]);
	 }

	 sleep(50L); 
	 mShared.writeOutputStream(mBuff, 0, 19);

	 return true;
  }


  public void setKeyLed(String color){

		String keyLedPath = "/dev/scikey";

	
		
		byte[] bufferRed = new byte[14];
		
	    Arrays.fill(bufferRed, (byte)0);
	
		bufferRed = color.getBytes();
		




		
		File fileRed = new File(keyLedPath);
		//sleep(30L); 
	
		try {
				FileOutputStream fos = new FileOutputStream(fileRed);
				
				fos.write(bufferRed);
				fos.close();
	
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}


     
	 static  int SettingTemperatureToAdData( int SettingTemperature)
	 {
		  int AdData = 0;
		 
		 switch(SettingTemperature)
		 {
			 case  30: AdData = 980; break;
			 case  40: AdData = 948; break;
			 case  50: AdData = 930; break;
			 case  60: AdData = 895; break;
			 case  70: AdData = 856; break;
			 case  80: AdData = 810; break;
			 case  90: AdData = 765; break;
			 case 100: AdData = 705; break;
			 case 110: AdData = 645; break;
			 case 120: AdData = 585; break;
			 case 130: AdData = 530; break;
			 case 140: AdData = 460; break;
			 case 150: AdData = 395; break;
			 case 160: AdData = 340; break;
			 case 170: AdData = 290; break;
			 case 180: AdData = 235; break;
			 case 190: AdData = 196; break;
			 case 200: AdData = 168; break;
			 case 210: AdData = 143; break;
			 case 220: AdData = 120; break;
			 case 230: AdData = 105; break;
			 default: break;
		 }
		 
		 return AdData;
	 }
	 
	 static  int DiffTemperature( int AdData)
	 {
		  int DiffTemp = 0;
		 
		 switch(AdData)
		 {
			 case  30: DiffTemp = 3; break;
			 case  40: DiffTemp = 3; break;
			 case  50: DiffTemp = 3; break;
			 case  60: DiffTemp = 4; break;
			 case  70: DiffTemp = 4; break;
			 case  80: DiffTemp = 4; break;
			 case  90: DiffTemp = 4; break;
			 case 100: DiffTemp = 5; break;
			 case 110: DiffTemp = 5; break;
			 case 120: DiffTemp = 5; break;
			 case 130: DiffTemp = 5; break;
			 case 140: DiffTemp = 5; break;
			 case 150: DiffTemp = 5; break;
			 case 160: DiffTemp = 5; break;
			 case 170: DiffTemp = 5; break;
			 case 180: DiffTemp = 4; break;
			 case 190: DiffTemp = 4; break;
			 case 200: DiffTemp = 4; break;
			 case 210: DiffTemp = 3; break;
			 case 220: DiffTemp = 3; break;
			 case 230: DiffTemp = 3; break;
			 default: break;
		 }
		 
		 return DiffTemp;
	 }

    int g_u16OvenTemperatureBackup = 0;   
	int g_u8SettingTemperature = 0;

	int g_u16OvenTemperatureRef = 0;  
	int g_u8DiffTemperature = 0;
	
	int g_u8UpDownFlag = 0;  

public void TempertureControlStage(int g_u8SettingTemperatureBackup, int g_u16OvenTemperature, String type)
	{
	
	      Log.e("lwxdebug", "TempertureControlStage="+g_u8SettingTemperatureBackup+" g_u16OvenTemperature"+g_u16OvenTemperature);   
		{
			
	
			if(g_u8SettingTemperature != g_u8SettingTemperatureBackup) 
			{
				g_b1RelayControlFlag = 0;
				g_u8SettingTemperature = g_u8SettingTemperatureBackup;
				g_u8DiffTemperature = DiffTemperature(g_u8SettingTemperatureBackup);
				g_u16OvenTemperatureBackup = SettingTemperatureToAdData(g_u8SettingTemperatureBackup);
				g_u16OvenTemperatureRef = SettingTemperatureToAdData(g_u8SettingTemperatureBackup);
				g_b1RelayControlFlag =2;
				g_u8UpDownFlag = 1;
		
				setmicrowaveon(type,0,0);
			}
	
           Log.e("lwxdebug", "g_b1RelayControlFlag="+g_b1RelayControlFlag+" g_u8UpDownFlag"+g_u8UpDownFlag);

		
			if((g_b1RelayControlFlag>0) && (g_u16OvenTemperature != g_u16OvenTemperatureBackup))
			{
				if(g_u16OvenTemperature - g_u16OvenTemperatureBackup > 0 && g_u16OvenTemperature - g_u16OvenTemperatureBackup < 1024)
				{
					g_u8UpDownFlag = 1; 
					g_u16OvenTemperatureBackup = g_u16OvenTemperature;
				}
				else if(g_u16OvenTemperatureBackup - g_u16OvenTemperature > 0 && g_u16OvenTemperatureBackup - g_u16OvenTemperature < 1024)
				{
					g_u8UpDownFlag = 2; 
					g_u16OvenTemperatureBackup = g_u16OvenTemperature;
				}
			}
	
			  Log.e("lwxdebug", "g_u8UpDownFlag="+g_u8UpDownFlag+" g_u16OvenTemperatureBackup"+g_u16OvenTemperatureBackup);
	
			if(g_b1RelayControlFlag == 2)
			{
				if(g_u16OvenTemperature >= g_u16OvenTemperatureRef - g_u8DiffTemperature && g_u8UpDownFlag == 1)
				{

				
					setmicrowaveon(type,0,0);
	
					g_b1RelayControlFlag = 3;
				}
			}
			else if(g_b1RelayControlFlag == 3)
			{
				if((g_u16OvenTemperature <= g_u16OvenTemperatureRef + g_u8DiffTemperature) && g_u8UpDownFlag == 2)
				{
					adjusttemperaturepoff(type);
	
					g_b1RelayControlFlag = 2;
				}
			}
	
			if(--u16_RelayControlTimer > 250) u16_RelayControlTimer =0;
		}
	}


 

 
  int runtime;					
  int work_time;					
  int delay_time; 				
  int water_runtime;				
  boolean heating_flag;				
 private boolean delay_flag;					
 private boolean water_flag;					
  int work_flag;					
  int status;						
  int refresh; 				
  int sec;						
  int already_p;	
  boolean AddWaterFlag;

  boolean  heating_flag_back ;
  boolean delay_flag_back ;
  boolean water_flag_back ;
  int status_back ;
   
 /*
 typedef _temperture_control_module
 {
	 u8 tmp;
	 u8 heating_time;
	 u8 delay_time;
	 u16 ad;
 }tmp_mod;*/

 
 /*
 public class temperture_control_module{
	
	 int tmp;
	 int heating_time;
	 int delay_time;
	 int ad;
 
 
	public void temperture_control_module(int tmp, int heating_time, int delay_time, int ad) {
 
	 this.tmp = tmp;
	 this.heating_time = heating_time;
	 this.delay_time = delay_time;
	 this.ad = ad;
   }
 
 
	 
 }*/





public void int_mod_list()
{
       tmp_mod_list[0]=new temperturecontrolmodule(30,2,18,967);
	   tmp_mod_list[1]=new temperturecontrolmodule(40,2,14,938);
	   tmp_mod_list[2]=new temperturecontrolmodule(50,2,18,900);
	   tmp_mod_list[3]=new temperturecontrolmodule(60,3,12,850);
	   tmp_mod_list[4]=new temperturecontrolmodule(70,4,18,790);
	   tmp_mod_list[5]=new temperturecontrolmodule(80,5,16,735);
	   tmp_mod_list[6]=new temperturecontrolmodule(90,7,15,655);
	   tmp_mod_list[7]=new temperturecontrolmodule(100,-1,0,0);
	   
}
 
 /*const tmp_mod tmp_mod_list[] = {{30, 2, 18, 967}, {40, 2, 14, 938}, {50, 3, 18, 900}, {60, 3, 12, 850}, 
								 {70, 4, 18, 790}, {80, 5, 16, 735}, {90, 7, 15, 655}, {100, -1, 0, 0}};*/
 
			 
 int tmp_save;
 int heating_time_save;
 int delay_time_save;
 int ad_save;


public void steam_temperature_control(int set_temprature,int lqwd,String type,int zfbwd,int sbdl)
{

	 int i;
    
	Log.e("lwxdebug", "steam_temperature_control set_temprature="+set_temprature+" tmp_save="+tmp_save);
	
	if(set_temprature == tmp_save)
	{
		TemperatureControlStage(heating_time_save, delay_time_save, ad_save,lqwd,type,zfbwd,sbdl);
		return;
	}

	for(i = 0; i <8; i++)
	{
		if(set_temprature == tmp_mod_list[i].tmp)
		{
			tmp_save = tmp_mod_list[i].tmp;
			heating_time_save = tmp_mod_list[i].heating_time;
			delay_time_save = tmp_mod_list[i].delay_time;
			ad_save = tmp_mod_list[i].ad;
		}
	}
	Log.e("lwxdebug", "steam_temperature_control heating_time_save="+heating_time_save+" delay_time_save="+delay_time_save+" ad_save="+ad_save);
		

	TemperatureControlStage(heating_time_save, delay_time_save, ad_save,lqwd,type,zfbwd,sbdl);
}
								

public void work_start()
{
	Log.e("lwxdebug", "work_start"); 

  sec = 0;
  status = STA_DRYOUT;
  work_flag = WORK_ON;
}


public void work_end(String type)
{

   Log.e("lwxdebug", "work_end"); 
  //sewaterpumpon(type,false,false);  //water off; relay off

  
  
  sec = 0;
  heating_flag = false;
  delay_flag = false;
  water_flag = false;
  status = STA_END;
  work_flag = WORK_OFF;
}



 void open_water(int wtime,String type)
{

  Log.e("lwxdebug", "open_water wtime ="+wtime);  

  water_flag = true;              
  water_runtime = wtime;       
  //WATER_ON; 
  water_flag =true;
  sewaterpumpon(type,water_flag,delay_flag);
}

 boolean backup = false;

 void scan_water(String type,int zfpwd)
{
      
    
	Log.e("lwxdebug", "scan_water water_flag ="+water_flag+" backup="+backup+" zfpwd="+zfpwd);  
  if(water_flag)         
  {                 

    /*
    if(PumbAdc > zfpwd)  
    {
       times++;
	   if(times>1)
	   {
	      
          work_end(type);
		  
	   }

	  
      return;
    }
	else
	{
       times=0;
	}*/

    if(backup != water_flag)
    {
        backup = water_flag;
        AddWaterFlag =water_flag;
    } 
    
    if(0 < water_runtime)       
    {
     // WATER_ON;   
       water_flag=true;
	   sewaterpumpon(type,water_flag,delay_flag);  //water off; relay on
	 
	   
    }
    else
    {
       water_flag =false;          

	   sewaterpumpon(type,water_flag,delay_flag);  //water off; relay on

    }
    
    water_runtime--;            
  }
  else
  {
     water_flag =false;     
     sewaterpumpon(type,water_flag,delay_flag);  //water off; relay on       
  }
}


 void scan_heating(String type)
{


  Log.e("lwxdebug", "scan_heating delay_flag ="+delay_flag+" heating_flag="+heating_flag+" delay_time="+delay_time+" runtime="+runtime);  

  if(delay_flag)                
  {
    delay_time--;              
    if(0 < delay_time)         
    {
      //RELAY_OFF;       
       delay_flag=false;
       sewaterpumpon(type,false,delay_flag);  //water off; relay on       
      return;
    }
    else
    {
      delay_flag = false;           
    }
  }
  
  if(heating_flag)             
  {
    runtime--;                  
    
    if(0 < runtime)            
    {
     // RELAY_ON;
       delay_flag=true;
	 
       sewaterpumpon(type,water_flag,delay_flag);  //water off; relay on       
    }
    else                        
    {
      heating_flag = false;
     // RELAY_OFF;
      delay_flag=false;
      sewaterpumpon(type,water_flag,delay_flag);  //water off; relay on       
    }
  }
  else
  {
    //RELAY_OFF;
  }
}


 void open_heating(int watertime, int d_time)   
{
  runtime = DEFAULT_RUNTIME;               
  work_time = 0;

     Log.e("lwxdebug", "open_heating d_time ="+d_time);  
  if(d_time>0)                           
  {
    delay_flag = true;                    
    delay_time = d_time;
  }
  
  heating_flag =true;                     
}

 int times = 0; 
 int once = 0;	 

 void power_control(int zfpwd,String type)
{
         
   Log.e("lwxdebug", "power_control times ="+times+" heating_flag="+heating_flag+" once="+once);  
  if(4 > times)                
  {                            
    if(false==heating_flag)      
    {                           
      if(0 == times)            
      {
       
        //open_heating(7);
        if(once==0)              
        {
          once = 1;            
          //WATER_ON;
          water_flag=true;
          sewaterpumpon(type,water_flag,delay_flag);  //water on; relay on
          open_water(DEFAULT_WATERTIME + 3,type);
        }

        if(zfpwd==0)  
        {                              
          once = 0;                     
          open_heating(DEFAULT_WATERTIME + 3, 0);       
          times++;                      
        }
        else                           
        {
          //RELAY_OFF;
            delay_flag=false;
            sewaterpumpon(type,water_flag,delay_flag);  //water on; relay on
        }
        
        return;
      } 
      
     
      times++;                  
      open_heating(0,0);
      open_water(DEFAULT_WATERTIME,type);
    }
  }
  else                
  {
    times = 0;
    sec = 0;
    status = STA_DRYOUT;
  }
}




 void work_pause(String type)
{

  Log.e("lwxdebug", "work_pause already_p ="+already_p);

  if(already_p==1)		
  {
    return;
  }
  else
  {
    already_p = 1;
  }
  //WATER_OFF;
  //RELAY_OFF;
  water_flag=false;
  delay_flag=false;

  sewaterpumpon(type,water_flag,delay_flag);  //water off; relay off
  
  heating_flag_back = heating_flag;
  delay_flag_back = delay_flag;
  water_flag_back = water_flag;
  status_back = status;
  
  heating_flag = false;
  delay_flag = false;
  water_flag = false;
  status = STA_END;
  
  work_flag = WORK_PAUSE;
  refresh = 0;
}


 void work_continue()
{
	 Log.e("lwxdebug", "work_continue already_p ="+already_p);


	
  if(already_p==1)	
  {
      already_p = 0;
      heating_flag = heating_flag_back;
      delay_flag = delay_flag_back;
      water_flag = water_flag_back;
      status = status_back;
      
      work_flag = WORK_ON;
  }

}

 void work_control(String type ,int zfbwd,int sbdl)
{
  sec++;               


  Log.e("lwxdebug", "work_control work_time="+work_time+" sec="+sec);

   Log.e("lwxdebug", "work_control zfbwd="+zfbwd+" sbdl="+sbdl);
   /*
  if(work_time == sec) 
  {
    work_end(type);
  }*/
  
  scan_water(type,zfbwd);         
  scan_heating(type);      
  Log.e("lwxdebug", "work_control status="+status+" sec="+sec); 
  switch(status)       
  {
    case STA_DRYOUT: 
		dry_out(type,zfbwd);
		break;                 
    case STA_POWER_CONTROL:
		power_control(zfbwd,type);
		break;    
    default: break;
  }
}
 int CheckDelay = 0;
 int HeatingPauseFlag = 0;
 int HeatingTime = 0;
 int g_u8SettingTemperature1 = 0;

 int TemperatureAdc = 0;	

 void TemperatureControlStage( int HeatingTimeBackup,  int CheckDelayBackup, int g_u8SettingTemperaturBackup,
	    int lqwd,String type,int zfbwd,int sbdl)
{
    
 
    Log.e("lwxdebug", "TemperatureControlStage HeatingTimeBackup="+HeatingTimeBackup+" CheckDelayBackup="+CheckDelayBackup+" g_u8SettingTemperaturBackup="+g_u8SettingTemperaturBackup);
   
    {
       
        if(g_u8SettingTemperature1 != g_u8SettingTemperaturBackup)
        {
            g_u8SettingTemperature1 = g_u8SettingTemperaturBackup;
            HeatingTime = HeatingTimeBackup;
            HeatingPauseFlag = 2; 
            CheckDelay = CheckDelayBackup;               
        }

        TemperatureAdc = lqwd;

		Log.e("lwxdebug", "TemperatureControlStage TemperatureAdc="+TemperatureAdc+" HeatingPauseFlag="+HeatingPauseFlag+" HeatingTime="+HeatingTime);
		  
       Log.e("lwxdebug", "TemperatureControlStage AddWaterFlag="+AddWaterFlag+" work_flag="+work_flag);
		  
		Log.e("lwxdebug", "TemperatureControlStage g_u8SettingTemperature="+g_u8SettingTemperature1+" CheckDelay="+CheckDelay+" CheckDelayBackup="+CheckDelayBackup);

		
        if(HeatingPauseFlag == 0 && HeatingTime != 0)
        {
            HeatingPauseFlag = 1;
            HeatingTime = HeatingTimeBackup;
            work_continue();
        }
        else if(HeatingPauseFlag == 1 && HeatingTime == 0)
        {
            work_pause(type);
            HeatingPauseFlag = 2;
            CheckDelay = 0;
        }
        if(TemperatureAdc > g_u8SettingTemperature1 && HeatingPauseFlag == 2 && CheckDelay > CheckDelayBackup) 
        {
            HeatingPauseFlag =0;
            HeatingTime = HeatingTimeBackup + 2;
        }       


		
        if(AddWaterFlag ) 
        {
            AddWaterFlag = false;
            HeatingPauseFlag =0;
            HeatingTime = HeatingTimeBackup;
        }
		
		if(WORK_ON == work_flag)
		{
			work_control(type,zfbwd,sbdl);
		}
		else
		{
		    water_flag=false;
            delay_flag=false;
	
			sewaterpumpon(type,water_flag,delay_flag);  //water off; relay off
		}

		
        if(--HeatingTime < 0)
			HeatingTime = 0;
		Log.e("lwxdebug", "TemperatureControlStage HeatingTime="+HeatingTime);

		
        if(++CheckDelay > 65530)
			CheckDelay = 65530;

		Log.e("lwxdebug", "TemperatureControlStage CheckDelay="+CheckDelay);
    }
}







 void dry_out(String type,int zfbwd)              
{


  Log.e("lwxdebug", "work_control dry_out="+zfbwd+" sec="+zfbwd);

  if(1 == zfbwd)       
  {                                  
    //WATER_ON;                          
    //RELAY_OFF;    
    water_flag=true;
	delay_flag=false;
	sewaterpumpon(type,water_flag,delay_flag);  //water on; relay off    
	
    open_water(DEFAULT_WATERTIME+3,type);   
    open_heating(DEFAULT_WATERTIME+3, 0);
    
    sec = 0;
    status = STA_POWER_CONTROL;
  }
  else                  
  {
   // WATER_OFF;
   // RELAY_ON;
    water_flag=false;
    delay_flag=true;
	sewaterpumpon(type,water_flag,delay_flag);  //water on; relay off    
  }
}
 }

