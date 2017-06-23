package com.citrix.ceip.service.linuxvda;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.citrix.ceip.CISDataHelper;
import com.citrix.ceip.model.AppNames;
import com.citrix.ceip.model.linuxvda.Customer;
import com.citrix.ceip.model.linuxvda.VdaInfo;
import com.citrix.ceip.repository.linuxvda.LinuxVdaCustomerRepository;
import com.citrix.ceip.repository.linuxvda.LinuxVdaInfoRepository;
import com.citrix.ceip.service.AbstractDataFetcher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class LinuxVdaDataFetcher extends AbstractDataFetcher {

	protected static Logger log = LoggerFactory.getLogger(LinuxVdaDataFetcher.class);
	
	@Autowired
	private CISDataHelper cisDataHelper;
	
	@Autowired
	private LinuxVdaCustomerRepository linuxVdaCustomerRepository;
	
	@Autowired
	private LinuxVdaInfoRepository linuxVdaInfoRepository;
	
	
	public void getCISData() throws Exception {
		
		//1. get customer list
		String sql = "select max(convert(varchar,ctime,111)) day , max(clientip) ip from linuxvdaceip_uploadinfo where isInternalUpload='false' group by clientip ";
		
		log.info("begin to fetch [linuxvda_customer] ");
		ArrayNode results = cisDataHelper.getAzureSqlResult(sql);

		Iterator<JsonNode> nodeIterator = results.elements();
		log.info("fetch [linuxvda_customer] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
		List<Customer> list = new ArrayList<Customer>();
		while(nodeIterator.hasNext()){
			JsonNode n = nodeIterator.next();
			Customer u = new Customer();		
			u.setClientIP(n.get("ip").asText());
			u.setDay(n.get("day").asText());
			u.setCountry(ip2Country.getCountryByIP(u.getClientIP()));			
			list.add(u);
		}		
		linuxVdaCustomerRepository.deleteAll();
		linuxVdaCustomerRepository.save(list);
		log.info("[linuxvda_customer] updated successfully....");
		
		//2. get vdainfo data
		sql = " select max(machine_guid) machine , max(ad_solution) ad , max(update_or_fresh_install) type, max(os_name_version) os, max(vda_version) vda "
			+ " from linuxvdaceip_vdaceip_view a, linuxvdaceip_uploadinfo_view b "
			+ " where a.uploaduuid = b.uuid and b.isInternalUpload='false' group by machine_guid  ";
		
		List<VdaInfo> listDashboard = new ArrayList<VdaInfo>();
		
		log.info("begin to fetch [linuxvda_vdainfo] ");
		results = cisDataHelper.getAzureSqlResult(sql);
		nodeIterator = results.elements();
		log.info("fetch [linuxvda_vdainfo] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
	    while(nodeIterator.hasNext()){
	    	JsonNode n = nodeIterator.next();
	    	VdaInfo u = new VdaInfo();
	    	u.setMachineId(n.get("machine").asText());
	    	u.setAdSolution(n.get("ad").asText());
	    	u.setInstallType(n.get("type").asText());
	    	u.setOsName(n.get("os").asText());
	    	u.setVersion(n.get("vda").asText());
	    	listDashboard.add(u);	
	    }	
	    
	    linuxVdaInfoRepository.deleteAll();
	    linuxVdaInfoRepository.save(listDashboard);
	    log.info("[linuxvda_vdainfo] updated successfully....");
		
	    updateLastUpdateTime(AppNames.LinuxVDA);
	}

	public String getAppName() {
		return AppNames.LinuxVDA;
	}

}
