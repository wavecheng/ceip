package com.citrix.ceip.service.ltsrass;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citrix.ceip.model.AppNames;
import com.citrix.ceip.model.sessionrecording.Recording;
import com.citrix.ceip.repository.sessionrecording.RecordingRepository;
import com.citrix.ceip.service.AbstractDataService;

@Service
public class LTSRAssDataService extends AbstractDataService {

	private String totalMachineCount = "";
	
	public List<Map> getCustomer(){
		String sql = " SELECT date(day) e_date , (SELECT  COUNT(DISTINCT uuid) FROM ltsrass_customer  WHERE DATE(day) <= e_date ) as cnt "
					+ " FROM ltsrass_customer AS e GROUP BY e_date order by e_date " ;	
		return doQuery(sql);		
	}
	
	public List<Map> getCountry(){
		String sql = " SELECT country, count(*) cnt FROM ltsrass_customer group by country order by cnt desc  " ;	
		return doQuery(sql);		
	}	
	
	public List<Map> getVersion(){
		String sql = " SELECT ltsrVersion, count(*) cnt FROM ltsrass_summary group by ltsrVersion order by cnt desc  " ;	
		return doQuery(sql);		
	}	

	public List<Map> getComplianceStatus(){
		String sql = " select complianceStatus , count(complianceStatus) from ltsrass_summary group by complianceStatus " ;	
		return doQuery(sql);		
	}	
	
	public Map getComplianceCount() {
		EntityManager em = entityManagerFactory.createEntityManager();
		Query q = em.createNativeQuery("select sum(totalmachines) total, sum(compliantmachines) compliant , sum(noncompliantmachines) non , sum(unvalidatedmachines) notvalidate from ltsrass_summary ");		
		List<Object[]> results = q.getResultList();
		
		Map m = new HashMap();
		for(Object[] obj : results){
			totalMachineCount = obj[0].toString();
			m.put("Compliant", obj[1]);
			m.put("Non-Compliant", obj[2]);
			m.put("Not Validated", obj[3]);
		}		
		em.close();		
		return m;
	}
	
	public String getTotalCheckedMachines(){
		return totalMachineCount;
	}
	
	public Timestamp getLastUpdateTime() {
		return getLastUpdateTime(AppNames.LTSRAssitant);
	}

	public int getTotalRecord() {		
		return getRecordCount("ltsrass_customer");
	}
}
