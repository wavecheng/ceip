package com.citrix.ceip.model.vdacleanup;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="vdacleanup_reboot")
public class RebootStat {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String rebootTimes;	
	private int count;	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getRebootTimes() {
		return rebootTimes;
	}
	public void setRebootTimes(String rebootTimes) {
		this.rebootTimes = rebootTimes;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
