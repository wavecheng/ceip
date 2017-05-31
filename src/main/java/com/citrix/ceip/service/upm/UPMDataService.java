package com.citrix.ceip.service.upm;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citrix.ceip.model.AppNames;
import com.citrix.ceip.repository.LastUpdateTimeRepository;
import com.citrix.ceip.service.AbstractDataService;

@Service
public class UPMDataService extends AbstractDataService {

	@Autowired
	private LastUpdateTimeRepository lastUpdateTimeRepository;
	
	public List<Map> getOSName() {
		String sql = " select osname, count(*) cnt from upm_dashboard_data group by osname  order by  cnt desc ";		
		return doQuery(sql);			
	}
	
	public List<Map> getVersion() {
		String sql = "select substr(upmVersion,1,5), count(*) cnt from upm_dashboard_data group by substr(upmVersion,1,5) order by  cnt desc ";
		return doQuery(sql);		
	}

	public List<Map> getCustomer(){
		String sql = " SELECT day, COUNT(DISTINCT uuid) cnt FROM upm_customer GROUP BY day order by day " ;	
		return doQuery(sql);		
	}
	
	public List<Map> getCountry(){
		String sql = " SELECT country, count(*) cnt FROM upm_customer group by country order by cnt desc  " ;	
		return doQuery(sql);		
	}	
	
	public List<Map> getServiceActive() {

		Map<String,Object> substitue = new HashMap<String,Object>();
		substitue.put("false","Service Disable");
		substitue.put("true","Service Active");
		substitue.put("0","Service Disable");
		substitue.put("1","Service Active");
		
		String sql = "select isServiceActive, count(*) cnt from upm_dashboard_data group by isServiceActive order by  cnt desc  ";
		return doQuery(sql,substitue);		
	}
	
	public List<Map> getMigrateProfile() {
		EntityManager em = entityManagerFactory.createEntityManager();
		Map<String,Object> substitue = new HashMap<String,Object>();
		substitue.put("1","Migrate local and roaming profile");
		substitue.put("2", "Migrate local profile");
		substitue.put("3", "Migrate roaming profile");
		substitue.put("4", "Do not migrate");
				
		String sql = "select migrateWindowsProfileToUserstore, count(*) cnt from upm_dashboard_data group by migrateWindowsProfileToUserstore order by  cnt desc  ";
		return doQuery(sql,substitue);		
	}

	public List<Map> getProfileHandling() {
		
		Map<String,Object> substitue = new HashMap<String,Object>();
		substitue.put("1", "Use local profile");
		substitue.put("2", "Delete local profile");
		substitue.put("3", "Rename Local  profile");
		
		String sql = "select localProfileConflictHandling, count(*) cnt from upm_dashboard_data group by localProfileConflictHandling order by  cnt desc  ";
		return doQuery(sql, substitue);		
	}

	public Timestamp getLastUpdateTime(){
		return getLastUpdateTime(AppNames.UPM);
	}

	@Override
	public int getTotalRecord() {
		 return getRecordCount("upm_dashboard_data");
	}
	
}
