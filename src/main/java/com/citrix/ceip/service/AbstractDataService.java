package com.citrix.ceip.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.citrix.ceip.model.AppNames;

public abstract class AbstractDataService {
	
	protected static Log log = LogFactory.getLog(AbstractDataService.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	protected EntityManagerFactory entityManagerFactory;
	
	protected List<Map> doQuery(String sql){
		return doQuery(sql,null);
	}
	
	protected List<Map> doQuery(String sql, Map<String,Object> substitue) {
		
		EntityManager em = entityManagerFactory.createEntityManager();
		Query q = em.createNativeQuery(sql);
		
		List<Object[]> results = q.getResultList();
		
		List<Map> computed = new ArrayList<Map>();
		for(Object[] obj : results){
			Map m = new HashMap();
			if(substitue != null)
				m.put("name",substitue.getOrDefault(obj[0].toString(), obj[0]));
			else
				m.put("name", obj[0]);
			
			m.put("cnt", obj[1]);
			computed.add(m);
		}
		
		for(Map m : computed){
			log.debug(m.get("name").toString() + ":" +  m.get("cnt"));
		}
		
		em.close();	
		
		return computed;
	}
	
	public abstract int getTotalRecord();
	
	public abstract Timestamp getLastUpdateTime();
	
	protected Timestamp getLastUpdateTime(String appName){
		EntityManager em = entityManagerFactory.createEntityManager();
		Query q = em.createNativeQuery("select lastUpdateTime from last_update_time where appName='" + appName +"' ");
		Timestamp time = (Timestamp)q.getSingleResult();
		em.close();
		return time;
	}
	
	protected int getRecordCount(String tableName){
		EntityManager em = entityManagerFactory.createEntityManager();
		Query q = em.createNativeQuery("select count(*) from " + tableName);
		int count = ((BigInteger)q.getSingleResult()).intValue();
		em.close();
		return count;		
	}
}
