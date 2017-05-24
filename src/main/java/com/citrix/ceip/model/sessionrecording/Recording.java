package com.citrix.ceip.model.sessionrecording;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="sr_recording")
public class Recording {

	@Id
	private String uuid;
	
	private String day;
	private int appRecordingNum;
	private int recordedAgentNum;
	private int recordingNum;
	
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
	public int getAppRecordingNum() {
		return appRecordingNum;
	}
	public void setAppRecordingNum(int appRecordingNum) {
		this.appRecordingNum = appRecordingNum;
	}
	public int getRecordedAgentNum() {
		return recordedAgentNum;
	}
	public void setRecordedAgentNum(int recordedAgentNum) {
		this.recordedAgentNum = recordedAgentNum;
	}
	public int getRecordingNum() {
		return recordingNum;
	}
	public void setRecordingNum(int recordingNum) {
		this.recordingNum = recordingNum;
	}
		
}
