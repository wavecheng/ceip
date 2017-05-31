package com.citrix.ceip.model.upm;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="upm_customer")
public class Customer {
	
	@Id
	private String uuid;
	private String day;	
	private String clientIP;	
	private String country;
		
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}		
}
