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

	public List<Map> getCustomer(){
		String sql = " SELECT date(day) e_date , (SELECT  COUNT(DISTINCT id) FROM sr_customer  WHERE DATE(day) <= e_date ) as cnt "
					+ " FROM sr_customer AS e GROUP BY e_date order by e_date " ;	
		return doQuery(sql);		
	}
	
	public List<Map> getCountry(){
		String sql = " SELECT country, count(*) cnt FROM sr_customer group by country order by cnt desc  " ;	
		return doQuery(sql);		
	}	
	
	public List<Map> getVersion(){
		String sql = " SELECT version, count(*) cnt FROM sr_customer group by version order by cnt desc  " ;	
		return doQuery(sql);		
	}	

	public List<Map> getOS(){
		String sql = " SELECT os, count(*) cnt FROM sr_customer group by os order by cnt desc  " ;	
		return doQuery(sql);		
	}	
	
	public List<Map> getRecodingNumPerMonth() {
		EntityManager em = entityManagerFactory.createEntityManager();
		Query q = em.createNativeQuery("select substr(day,1,7) month, sum(RECORDINGNUM ) recordingNum, sum(APPRECORDINGNUM ) appRecordingNum ,sum(RECORDINGNUM ) - sum(APPRECORDINGNUM ) desktopNum "
				+ " from sr_recording group by month order by month ");		
		List<Object[]> results = q.getResultList();
		
		List<Map> computed = new ArrayList<Map>();
		for(Object[] obj : results){
			Map m = new HashMap();
			m.put("month", obj[0]);
			m.put("recordingNum", obj[1]);
			m.put("appRecordingNum", obj[2]);
			m.put("desktopNum", obj[3]);
			computed.add(m);
		}		
		em.close();		
		return computed;
	}
	
	public Timestamp getLastUpdateTime() {
		return getLastUpdateTime(AppNames.LTSRAssitant);
	}

	public int getTotalRecord() {		
		return getRecordCount("ltsrass_customer");
	}
}
