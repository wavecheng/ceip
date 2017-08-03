package com.citrix.ceip.model.linuxvda;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="linuxvda_vda")
public class VdaInfo {

	@Id
	private String machineId;
	
	private String adSolution;
	private String installType;
	private String osName;
	private String version;
	private String day;
	
	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public String getAdSolution() {
		return adSolution;
	}
	public void setAdSolution(String adSolution) {
		this.adSolution = adSolution;
	}
	public String getInstallType() {
		return installType;
	}
	public void setInstallType(String installType) {
		this.installType = installType;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}

	
}
