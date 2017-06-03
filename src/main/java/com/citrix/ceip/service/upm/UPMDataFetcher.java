package com.citrix.ceip.service.upm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.citrix.ceip.CISDataHelper;
import com.citrix.ceip.model.AppNames;
import com.citrix.ceip.model.upm.Customer;
import com.citrix.ceip.model.upm.UPMDashboardData;
import com.citrix.ceip.repository.upm.UPMDashboardRepository;
import com.citrix.ceip.repository.upm.UpmCustomerRepository;
import com.citrix.ceip.service.AbstractDataFetcher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class UPMDataFetcher extends AbstractDataFetcher  {

	protected static Logger log = LoggerFactory.getLogger(UPMDataFetcher.class);
	
	@Autowired
	private CISDataHelper cisDataHelper;
	
	@Autowired
	private UPMDashboardRepository uPMDashboardRepository;
	
	@Autowired
	private UpmCustomerRepository upmCustomerRepository;
	
	public void getCISData() throws Exception{
	
		
		//1. get customer list
		String sql = "select clientip, max(substr(ctime,1,10)), max(uuid) from upm.uploadinfo group by clientip ";
		
		log.info("begin to fetch [upm_customer] ");
		ArrayNode results = cisDataHelper.getSqlResult(sql);

		Iterator<JsonNode> nodeIterator = results.elements();
		log.info("fetch [upm_customer] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
		List<Customer> list = new ArrayList<Customer>();
		while(nodeIterator.hasNext()){
			JsonNode n = nodeIterator.next();
			Customer u = new Customer();		
			u.setClientIP(n.get(0).asText());
			u.setDay(n.get(1).asText());
			u.setUuid(n.get(2).asText());
			u.setCountry(ip2Country.getCountryByIP(u.getClientIP()));			
			list.add(u);
		}		
		upmCustomerRepository.deleteAll();
		upmCustomerRepository.save(list);
		log.info("[upm_customer] updated successfully....");
		
		//2. get dashboard data
		sql = "select uid, min(osname),max(upmversion),max(serviceactive),max(localprofileconflicthandling),"
				+ " max(migratewindowsprofilestouserstore) from upm.user_profile_management group by uid ";
		
		List<UPMDashboardData> listDashboard = new ArrayList<UPMDashboardData>();
		
		log.info("begin to fetch [upm_dashboard_data] ");
		results = cisDataHelper.getSqlResult(sql);
		nodeIterator = results.elements();
		log.info("fetch [upm_dashboard_data] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
	    while(nodeIterator.hasNext()){
	    	try{
		    	JsonNode n = nodeIterator.next();
		    	UPMDashboardData u = new UPMDashboardData();
		    	u.setUid(n.get(0).asText());
		    	u.setOsName(n.get(1).asText());
		    	u.setUpmVersion(n.get(2).asText());
		    	u.setServiceActive(Boolean.parseBoolean(n.get(3).asText()));
		    	u.setLocalProfileConflictHandling(Integer.parseInt(n.get(4).asText()));
		    	u.setMigrateWindowsProfileToUserstore(Integer.parseInt(n.get(5).asText()));
		    	listDashboard.add(u);	
	    	}catch(Exception ex){
	    		log.error("Dashboard parse error:" + ex);
	    	}
	    }	
	    
	    uPMDashboardRepository.deleteAll();
	    uPMDashboardRepository.save(listDashboard);
	    log.info("[upm_dashboard_data] updated successfully....");
		
	    updateLastUpdateTime(AppNames.UPM);
	}

	public String getAppName() {
		return AppNames.UPM;
	}
	
	

}
