package com.citrix.ceip.model.linuxvda;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity(name="linuxvda_customer")
@Table(indexes={@Index(name="linuxvda_customer_idx_day",columnList="day")})
public class Customer {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String day;	
	private String clientIP;	
	private String country;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
