package com.citrix.ceip.service.sessionrecording;

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
public class SRDataService extends AbstractDataService {

	@Autowired
	private RecordingRepository recordingRepository;
	
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

	public List<Map> getLoadBalancingStatus(){
		String sql = " SELECT name, count as cnt FROM sr_pie_item where type='loadbalancing_status' " ;	
		Map<String,Object> substitute = new HashMap<String,Object>();
		substitute.put("0", "Disabled");
		substitute.put("1", "Enabled");
		return doQuery(sql,substitute);		
	}

	public List<Map> getAdminLogginStatus(){
		String sql = " SELECT name, count as cnt FROM sr_pie_item where type='adminlogging_status' " ;	
		Map<String,Object> substitute = new HashMap<String,Object>();
		substitute.put("0", "Disabled");
		substitute.put("1", "Enabled");
		substitute.put("-1", "Unknown");
		return doQuery(sql,substitute);		
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
	
	public Map<String,Integer> getAgentSize() {		
		List<Recording> allRecords = recordingRepository.findAll();
		int[] count = new int[6];		
		for(Recording i : allRecords){
			int num = i.getRecordedAgentNum();
			if(num <= 10){
				count[0] ++;
			}else if( 11 <= num && num <= 50){
				count[1] ++;
			}else if( 50 < num && num <= 100){
				count[2] ++;
			}else if( 100 < num && num <= 500){
				count[3] ++;
			}else if( 500 < num && num <= 1000){
				count[4] ++;
			}else {
				count[5] ++;
			}						
		}
		
		Map<String,Integer> computed = new HashMap<String, Integer>();
		computed.put("(0,10)",count[0]);
		computed.put("(10,50)",count[1]);
		computed.put("(50,100)",count[2]);
		computed.put("(100,500)",count[3]);
		computed.put("(500,1000)",count[4]);
		computed.put("(>1000)",count[5]);		
		return computed;
	}
	
	public Timestamp getLastUpdateTime() {
		return getLastUpdateTime(AppNames.SessionRecording);
	}

	public int getTotalRecord() {		
		return getRecordCount("sr_customer");
	}
}
