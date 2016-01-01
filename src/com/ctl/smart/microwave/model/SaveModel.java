package com.ctl.smart.microwave.model;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

@Table(name = "local_user")
public class SaveModel {

	// ID @Id主键,int类型,数据库建表时此字段会设为自增长
	@Id
	@Column(name = "_id")
	private int _id;

	@Column(name = "u_name")
	private String uName;
	@Column(name = "imageid")
	private String imageid;
	@Column(name = "time")
	private String time;
	@Column(name = "secondtime")
	private String secondtime;
	@Column(name = "temperature")
	private String temperature;

	@Column(name = "u_pingyin")
	private String uPingYin;

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getuPingYin() {
		return uPingYin;
	}

	public void setuPingYin(String uPingYin) {
		this.uPingYin = uPingYin;
	}

	public String getImageid() {
		return imageid;
	}

	public void setImageid(String imageid) {
		this.imageid = imageid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSecondtime() {
		return secondtime;
	}

	public void setSecondtime(String secondtime) {
		this.secondtime = secondtime;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

}
