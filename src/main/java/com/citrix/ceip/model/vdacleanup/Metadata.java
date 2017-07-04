package com.citrix.ceip.model.vdacleanup;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="vdacleanup_metadata")
public class Metadata {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String os;	
	private String vda;	
	private String parameter;
	private int avgTime;
	private int count;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getVda() {
		return vda;
	}
	public void setVda(String vda) {
		this.vda = vda;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public int getAvgTime() {
		return avgTime;
	}
	public void setAvgTime(int avgTime) {
		this.avgTime = avgTime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
