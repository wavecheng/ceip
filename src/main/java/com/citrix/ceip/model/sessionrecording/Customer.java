package com.citrix.ceip.model.sessionrecording;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="sr_customer")
public class Customer {
	
	@Id
	private String id;
	private String os;
	private String day;	
	private String clientIP;	
	private String version;
	private String country;
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}		
}
