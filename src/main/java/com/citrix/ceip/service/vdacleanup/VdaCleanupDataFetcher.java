package com.citrix.ceip.service.vdacleanup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.citrix.ceip.CISDataHelper;
import com.citrix.ceip.model.AppNames;
import com.citrix.ceip.model.vdacleanup.Customer;
import com.citrix.ceip.model.vdacleanup.Metadata;
import com.citrix.ceip.model.vdacleanup.RebootStat;
import com.citrix.ceip.model.vdacleanup.UninstallAll;
import com.citrix.ceip.model.vdacleanup.UninstallFailed;
import com.citrix.ceip.repository.vdaclearnup.VdaCleanupCustomerRepository;
import com.citrix.ceip.repository.vdaclearnup.VdaCleanupMetadataRepository;
import com.citrix.ceip.repository.vdaclearnup.VdaCleanupRebootStatRepository;
import com.citrix.ceip.repository.vdaclearnup.VdaCleanupUninstallAllRepository;
import com.citrix.ceip.repository.vdaclearnup.VdaCleanupUninstallFailedRepository;
import com.citrix.ceip.service.AbstractDataFetcher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class VdaCleanupDataFetcher extends AbstractDataFetcher {

	protected static Logger log = LoggerFactory.getLogger(VdaCleanupDataFetcher.class);
	
	@Autowired
	private CISDataHelper cisDataHelper;
	
	@Autowired
	private VdaCleanupCustomerRepository vdaCleanupCustomerRepository;
	
	@Autowired
	private VdaCleanupMetadataRepository vdaCleanupMetadataRepository;
	
	@Autowired
	private VdaCleanupRebootStatRepository vdaCleanupRebootStatRepository;
	
	@Autowired
	private VdaCleanupUninstallAllRepository vdaCleanupUninstallAllRepository;
	
	@Autowired
	private VdaCleanupUninstallFailedRepository vdaCleanupUninstallFailedRepository;
	
	
	public void getCISData() throws Exception {
		
		//1. get customer list
		String sql = "select max(convert(varchar,ctime,111)) day , max(clientip) ip, max(clienttoolversion) clientToolVersion from vdacleanup_uploadinfo where isInternalUpload='false' group by clientip ";
		
		log.info("begin to fetch [vdacleanup_customer] ");
		ArrayNode results = cisDataHelper.getAzureSqlResult(sql);

		Iterator<JsonNode> nodeIterator = results.elements();
		log.info("fetch [vdacleanup_customer] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
		List<Customer> list = new ArrayList<Customer>();
		while(nodeIterator.hasNext()){
			JsonNode n = nodeIterator.next();
			Customer u = new Customer();		
			u.setClientIP(n.get("ip").asText());
			u.setDay(n.get("day").asText());
			u.setCountry(ip2Country.getCountryByIP(u.getClientIP()));
			u.setClientToolVersion(n.get("clientToolVersion").asText());
			list.add(u);
		}		
		vdaCleanupCustomerRepository.deleteAll();
		vdaCleanupCustomerRepository.save(list);
		log.info("[vdacleanup_customer] updated successfully....");
		
		//2. get vdacleanup_uninstall_all data
		sql = " select type,name,status,count(*) cnt  "
			+ " from vdacleanup_uninstall_view a, vdacleanup_uploadinfo_view b "
			+ " where a.uploaduuid = b.uuid and b.isInternalUpload='false' group by type,name,status  ";
				
		List<UninstallAll> listDashboard = new ArrayList<UninstallAll>();
		
		log.info("begin to fetch [vdacleanup_uninstall_all] ");
		results = cisDataHelper.getAzureSqlResult(sql);
		nodeIterator = results.elements();
		log.info("fetch [vdacleanup_uninstall_all] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
	    while(nodeIterator.hasNext()){
	    	JsonNode n = nodeIterator.next();
	    	UninstallAll u = new UninstallAll();
	    	u.setType(n.get("type").asText());
	    	u.setCount(n.get("cnt").asInt());
	    	u.setName(n.get("name").asText());
	    	u.setStatus(n.get("status").asText());
	    	listDashboard.add(u);	
	    }	
	    
	    vdaCleanupUninstallAllRepository.deleteAll();
	    vdaCleanupUninstallAllRepository.save(listDashboard);
	    log.info("[vdacleanup_uninstall_all] updated successfully....");
		
		//3. get vdacleanup_uninstall_failed data
		sql = " select type, name, returncode, errorid,count(*) cnt  "
			+ " from vdacleanup_uninstall_view a, vdacleanup_uploadinfo_view b "
			+ " where a.uploaduuid = b.uuid and b.isInternalUpload='false' and a.status='Failed'"
			+ " group by type, name, returncode, errorid ";
				
		List<UninstallFailed> failedList = new ArrayList<UninstallFailed>();
		
		log.info("begin to fetch [vdacleanup_uninstall_failed] ");
		results = cisDataHelper.getAzureSqlResult(sql);
		nodeIterator = results.elements();
		log.info("fetch [vdacleanup_uninstall_failed] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
	    while(nodeIterator.hasNext()){
	    	JsonNode n = nodeIterator.next();
	    	UninstallFailed u = new UninstallFailed();
	    	u.setType(n.get("type").asText());
	    	u.setCount(n.get("cnt").asInt());
	    	u.setName(n.get("name").asText());
	    	u.setErrorid(n.get("errorid").asText());
	    	u.setReturncode(n.get("returncode").asText());
	    	failedList.add(u);	
	    }	
	    
	    vdaCleanupUninstallFailedRepository.deleteAll();
	    vdaCleanupUninstallFailedRepository.save(failedList);
	    log.info("[vdacleanup_uninstall_failed] updated successfully....");
	    
		//4. get vdacleanup_reboot count data
		sql = " select t.rebootTime, count(*) cnt from ( "
			+ "     select uploaduuid, count(*) rebootTime "
			+ "     from vdacleanup_reboot_view a, vdacleanup_uploadinfo_view b "
			+ "     where a.uploaduuid = b.uuid and b.isInternalUpload='false' group by uploaduuid "
			+ " ) as t group by t.rebootTime";
				
		List<RebootStat> rebootCountList = new ArrayList<RebootStat>();
		
		log.info("begin to fetch [vdacleanup_reboot_view] ");
		results = cisDataHelper.getAzureSqlResult(sql);
		nodeIterator = results.elements();
		log.info("fetch [vdacleanup_reboot_view] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
	    while(nodeIterator.hasNext()){
	    	JsonNode n = nodeIterator.next();
	    	RebootStat u = new RebootStat();
	    	u.setRebootTimes(n.get("rebootTime").asText());
	    	u.setCount(n.get("cnt").asInt());
	    	rebootCountList.add(u);	
	    }	
	    
	    vdaCleanupRebootStatRepository.deleteAll();
	    vdaCleanupRebootStatRepository.save(rebootCountList);
	    log.info("[vdacleanup_reboot_view] updated successfully....");
	    
		//5. get vdacleanup_metadata data
		sql = " select os,vda,parameter, avg(datediff(ss, starttime, endtime)) secSpend, count(*) cnt "
			+ " from vdacleanup_metadata_view a, vdacleanup_uploadinfo_view b "
			+ " where a.uploaduuid = b.uuid and b.isInternalUpload='false'  "
			+ " group by os,vda,parameter ";
				
		List<Metadata> metaDataList = new ArrayList<Metadata>();
		
		log.info("begin to fetch [vdacleanup_metadata] ");
		results = cisDataHelper.getAzureSqlResult(sql);
		nodeIterator = results.elements();
		log.info("fetch [vdacleanup_metadata] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
	    while(nodeIterator.hasNext()){
	    	JsonNode n = nodeIterator.next();
	    	Metadata u = new Metadata();
	    	u.setAvgTime(n.get("secSpend").asInt());
	    	u.setOs(n.get("os").asText());
	    	u.setParameter(n.get("parameter").asText());
	    	u.setVda(n.get("vda").asText());
	    	u.setCount(n.get("cnt").asInt());
	    	metaDataList.add(u);	
	    }	
	    
	    vdaCleanupMetadataRepository.deleteAll();
	    vdaCleanupMetadataRepository.save(metaDataList);
	    log.info("[vdacleanup_metadata] updated successfully....");
	    
	    updateLastUpdateTime(AppNames.VdaCleanup);
	}

	public String getAppName() {
		return AppNames.VdaCleanup;
	}

}
