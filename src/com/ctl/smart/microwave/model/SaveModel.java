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
	
	
	
	@Column(name = "u_needwater")
	private String needwater;
	
	@Column(name = "u_autopausetime")
	private String autoPauseTime;
	
	@Column(name = "u_contion")
	private String contion;
	
	@Column(name = "u_title")
	private String title;
	
	@Column(name = "u_parents")
	private String parents;
	
	@Column(name = "u_key")
	private String key;
	
	@Column(name = "u_contionname")
	private String contionName;
	
	@Column(name = "u_contentnamepy")
	private String contentNamePY;
	
	
	@Column(name="u_creadtime")
	private long creadtime;
	
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

	public String getNeedwater() {
		return needwater;
	}

	public void setNeedwater(String needwater) {
		this.needwater = needwater;
	}

	public String getAutoPauseTime() {
		return autoPauseTime;
	}

	public void setAutoPauseTime(String autoPauseTime) {
		this.autoPauseTime = autoPauseTime;
	}

	public String getContion() {
		return contion;
	}

	public void setContion(String contion) {
		this.contion = contion;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getParents() {
		return parents;
	}

	public void setParents(String parents) {
		this.parents = parents;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getContionName() {
		return contionName;
	}

	public void setContionName(String contionName) {
		this.contionName = contionName;
	}

	public String getContentNamePY() {
		return contentNamePY;
	}

	public void setContentNamePY(String contentNamePY) {
		this.contentNamePY = contentNamePY;
	}

	public long getCreadtime() {
		return creadtime;
	}

	public void setCreadtime(long creadtime) {
		this.creadtime = creadtime;
	}

	
	
}
