package  com.ctl.smart.microwave.protocol;


public class temperturecontrolmodule{
   
	int tmp;
	int heating_time;
	int delay_time;
	int ad;


   public temperturecontrolmodule(int tmp, int heating_time, int delay_time, int ad) {

	this.tmp = tmp;
	this.heating_time = heating_time;
	this.delay_time = delay_time;
	this.ad = ad;
  }


	
}

