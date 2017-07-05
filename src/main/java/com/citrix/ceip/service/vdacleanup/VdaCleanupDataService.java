package com.citrix.ceip.service.vdacleanup;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.citrix.ceip.model.AppNames;
import com.citrix.ceip.service.AbstractDataService;

@Service
public class VdaCleanupDataService extends AbstractDataService {

	public List<Map> getCustomer(){
		String sql = " SELECT date(day) e_date , (SELECT  COUNT(DISTINCT clientip) FROM vdacleanup_customer  WHERE DATE(day) <= e_date ) as cnt "
					+ " FROM vdacleanup_customer AS e GROUP BY e_date order by e_date " ;	
		return doQuery(sql);		
	}
	
	public List<Map> getCountry(){
		String sql = " SELECT country, count(*) cnt FROM vdacleanup_customer group by country order by cnt desc  " ;	
		return doQuery(sql);		
	}	

	public List<Map> getRebootTimes(){
		String sql = " SELECT rebootTimes, count FROM vdacleanup_reboot  " ;	
		return doQuery(sql);		
	}	
	
	public List<Map> getRunType(){
		String sql = " SELECT parameter, sum(count) cnt FROM vdacleanup_metadata group by parameter order by cnt desc  " ;	
		return doQuery(sql);		
	}	
		
	public List getOsCleanTime(){
		String sql = " SELECT os, sum(count) cnt , round(avg(avgTime)/60) minutes FROM vdacleanup_metadata group by os order by cnt desc " ;	
		return doNativeQuery(sql);		
	}	

	public List getVdaVersionCleanTime(){
		String sql = " SELECT vda, sum(count) cnt , round(avg(avgTime)/60) minutes FROM vdacleanup_metadata group by vda order by cnt desc  " ;	
		return doNativeQuery(sql);		
	}
	
	public List<Map> getVcuVersion(){
		String sql = " SELECT clienttoolversion, count(*) cnt FROM vdacleanup_customer group by clienttoolversion order by cnt desc  " ;	
		return doQuery(sql);		
	}	

	public List<Map> getUninstallType(){
		String sql = " SELECT type, sum(count) cnt FROM vdacleanup_uninstall_all group by type order by cnt desc  " ;	
		return doQuery(sql);		
	}	

	public List<Map> getUninstallTopFile(){
		String sql = " SELECT name , sum(count) cnt FROM vdacleanup_uninstall_all group by name order by cnt desc limit 10  " ;	
		return doQuery(sql);		
	}	

	public List<Map> getUninstallStatus(){
		String sql = " SELECT status, sum(count) cnt FROM vdacleanup_uninstall_all group by status order by cnt desc  " ;	
		return doQuery(sql);		
	}
			
	public List getUninstallFailedTopFile(){
		String sql = " SELECT type, name, sum(count) cnt FROM vdacleanup_uninstall_failed group by type, name order by cnt desc limit 20   " ;	
		return doNativeQuery(sql);		
	}	
	
	public List getUninstallFailedErrorId(){
		String sql = " SELECT errorid,name, sum(count) cnt FROM vdacleanup_uninstall_failed group by errorid,name order by cnt desc limit 20   " ;	
		return doNativeQuery(sql);		
	}	
	
	@Override
	public Timestamp getLastUpdateTime() {
		return getLastUpdateTime(AppNames.VdaCleanup);
	}

	@Override
	public int getTotalRecord() {
		return getRecordCount("vdacleanup_customer");
	}

	public int getTotalCustomerCount(){
		return getRecordCount("vdacleanup_customer");
	}
}
