package com.citrix.ceip.model.ltsrass;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="ltsrass_summary")
public class Summary {

	@Id
	private String uuid;
	
	private int unvalidatedMachines;
	private int compliantMachines;
	private int nonCompliantMachines;
	private int totalMachines;
	private String complianceStatus;
	private String ltsrVersion;
	private String createdOn;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getUnvalidatedMachines() {
		return unvalidatedMachines;
	}
	public void setUnvalidatedMachines(int unvalidatedMachines) {
		this.unvalidatedMachines = unvalidatedMachines;
	}
	public int getCompliantMachines() {
		return compliantMachines;
	}
	public void setCompliantMachines(int compliantMachines) {
		this.compliantMachines = compliantMachines;
	}
	public int getNonCompliantMachines() {
		return nonCompliantMachines;
	}
	public void setNonCompliantMachines(int nonCompliantMachines) {
		this.nonCompliantMachines = nonCompliantMachines;
	}
	public int getTotalMachines() {
		return totalMachines;
	}
	public void setTotalMachines(int totalMachines) {
		this.totalMachines = totalMachines;
	}
	public String getComplianceStatus() {
		return complianceStatus;
	}
	public void setComplianceStatus(String complianceStatus) {
		this.complianceStatus = complianceStatus;
	}
	public String getLtsrVersion() {
		return ltsrVersion;
	}
	public void setLtsrVersion(String ltsrVersion) {
		this.ltsrVersion = ltsrVersion;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}	
}
