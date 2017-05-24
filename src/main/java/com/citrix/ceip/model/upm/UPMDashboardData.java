package com.citrix.ceip.model.upm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity(name="upm_dashboard_data")
@Table(indexes={@Index(name="upm_data_idx_uid",columnList="uid")})
public class UPMDashboardData {
	@Id
	@GeneratedValue
	private int id;
	private String osName;
	private String upmVersion;
	private boolean isServiceActive;
	private int localProfileConflictHandling;
	private int migrateWindowsProfileToUserstore;	
	
	@Column(nullable=false,unique=true)
	private String uid;
		
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public String getUpmVersion() {
		return upmVersion;
	}
	public void setUpmVersion(String upmVersion) {
		this.upmVersion = upmVersion;
	}
	public boolean isServiceActive() {
		return isServiceActive;
	}
	public void setServiceActive(boolean isServiceActive) {
		this.isServiceActive = isServiceActive;
	}
	public int getLocalProfileConflictHandling() {
		return localProfileConflictHandling;
	}
	public void setLocalProfileConflictHandling(int localProfileConflictHandling) {
		this.localProfileConflictHandling = localProfileConflictHandling;
	}
	public int getMigrateWindowsProfileToUserstore() {
		return migrateWindowsProfileToUserstore;
	}
	public void setMigrateWindowsProfileToUserstore(int migrateWindowsProfileToUserstore) {
		this.migrateWindowsProfileToUserstore = migrateWindowsProfileToUserstore;
	}
	
}
