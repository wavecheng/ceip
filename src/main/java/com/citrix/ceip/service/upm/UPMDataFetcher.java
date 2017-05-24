package com.citrix.ceip.service.upm;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.citrix.ceip.CISDataHelper;
import com.citrix.ceip.DataFetcher;
import com.citrix.ceip.model.AppNames;
import com.citrix.ceip.model.LastUpdateTime;
import com.citrix.ceip.model.upm.UPMDashboardData;
import com.citrix.ceip.repository.LastUpdateTimeRepository;
import com.citrix.ceip.repository.upm.UPMDashboardRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class UPMDataFetcher implements DataFetcher {

	protected static Logger log = LoggerFactory.getLogger(UPMDataFetcher.class);
	
	@Autowired
	private CISDataHelper cisDataHelper;
	
	@Autowired
	private UPMDashboardRepository uPMDashboardRepository;
	
	@Autowired
	private LastUpdateTimeRepository lastUpdateTimeRepository;
	
	public void getCISData() throws Exception{
	    
		String sql = "select uid, min(osname),max(upmversion),max(serviceactive),max(localprofileconflicthandling),"
				+ " max(migratewindowsprofilestouserstore) from upm.user_profile_management group by uid ";
		
		List<UPMDashboardData> list = new ArrayList<UPMDashboardData>();
		
		log.info("begin to fetch [upm_dashboard_data] ");
		ArrayNode results = cisDataHelper.getSqlResult(sql);
		Iterator<JsonNode> nodeIterator = results.elements();
		log.info("fetch [upm_dashboard_data] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
	    while(nodeIterator.hasNext()){
	    	JsonNode n = nodeIterator.next();
	    	UPMDashboardData u = new UPMDashboardData();
	    	u.setUid(n.get(0).asText());
	    	u.setOsName(n.get(1).asText());
	    	u.setUpmVersion(n.get(2).asText());
	    	u.setServiceActive(Boolean.parseBoolean(n.get(3).asText()));
	    	u.setLocalProfileConflictHandling(Integer.parseInt(n.get(4).asText()));
	    	u.setMigrateWindowsProfileToUserstore(Integer.parseInt(n.get(5).asText()));
	    	list.add(u);	    			    	
	    }	
	    
	    uPMDashboardRepository.deleteAll();
	    uPMDashboardRepository.save(list);
	    
	    Timestamp now = Timestamp.from(Calendar.getInstance().getTime().toInstant());
	    LastUpdateTime last = lastUpdateTimeRepository.findOne(AppNames.UPM);
		if(last == null){
			last = new LastUpdateTime();
			last.setAppName(AppNames.UPM);
		}
		last.setLastUpdateTime(now);
		lastUpdateTimeRepository.save(last);
		
	    log.info("[upm_dashboard_data] updated successfully....");
		
	}

	public String getAppName() {
		return AppNames.UPM;
	}
	
	

}
