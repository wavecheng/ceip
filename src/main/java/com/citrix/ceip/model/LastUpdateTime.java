package com.citrix.ceip.model;

import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="last_update_time")
public class LastUpdateTime {

	@Id
	private String appName;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	//30 = 2000- 1970 
	private Timestamp lastUpdateTime = Timestamp.from(Instant.ofEpochSecond(3600*24*365*30));
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
}
