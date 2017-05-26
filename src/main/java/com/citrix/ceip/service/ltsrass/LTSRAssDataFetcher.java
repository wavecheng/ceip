package com.citrix.ceip.service.ltsrass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.citrix.ceip.IP2CountryHelper;
import com.citrix.ceip.model.AppNames;
import com.citrix.ceip.model.ltsrass.Customer;
import com.citrix.ceip.model.ltsrass.Summary;
import com.citrix.ceip.repository.ltsrass.LtsrCustomerRepository;
import com.citrix.ceip.repository.ltsrass.SummaryRepository;
import com.citrix.ceip.service.AbstractDataFetcher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class LTSRAssDataFetcher extends AbstractDataFetcher {

	protected static Logger log = LoggerFactory.getLogger(LTSRAssDataFetcher.class);
	
	@Autowired
	private LtsrCustomerRepository ltsrCustomerRepository;
	
	@Autowired
	private SummaryRepository summaryRepository;
	
	private IP2CountryHelper ip2Country = new IP2CountryHelper(LTSRAssDataFetcher.class.getResource("/ipcountry.txt").getFile());
	
	@Transactional
	public void getCISData() throws Exception {
		
		//1. get customer list
		String sql = "select clientip, max(substr(ctime,1,10)), max(uuid) from ltsrass.uploadinfo group by clientip ";
		
		log.info("begin to fetch [ltsrass_customer] ");
		ArrayNode results = cisDataHelper.getSqlResult(sql);

		Iterator<JsonNode> nodeIterator = results.elements();
		log.info("fetch [ltsrass_customer] complete with total=" + results.size());
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
		ltsrCustomerRepository.deleteAll();
		ltsrCustomerRepository.save(list);
		log.info("[ltsrass_customer] updated successfully....");
		
		//2. get recoding list
		sql = " select  a.compliancestatus, a.compliantmachines,a.createdon, a.ltsrversion,a.noncompliantmachines, a.totalmachines, a.unvalidatedmachines, a.uploaduuid "
			+ " from ltsrass.summary a, (select clientip, max(uuid) uuid from ltsrass.uploadinfo group by clientip) b "
			+ " where a.uploaduuid=b.uuid  ";
		
		log.info("begin to fetch [ltsrass_summary] ");
		results = cisDataHelper.getSqlResult(sql);

		nodeIterator = results.elements();
		log.info("fetch [ltsrass_summary] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
		List<Summary> listRecording = new ArrayList<Summary>();
		while(nodeIterator.hasNext()){
			JsonNode n = nodeIterator.next();
			Summary u = new Summary();
			u.setComplianceStatus(n.get(0).asText());
			u.setCompliantMachines(n.get(1).asInt());
			u.setCreatedOn(n.get(2).asText());
			u.setLtsrVersion(n.get(3).asText());
			u.setNonCompliantMachines(n.get(4).asInt());
			u.setTotalMachines(n.get(5).asInt());
			u.setUnvalidatedMachines(n.get(6).asInt());
			u.setUuid(n.get(7).asText());
			listRecording.add(u);
		}		
		summaryRepository.deleteAll();
		summaryRepository.save(listRecording);
		log.info("[ltsrass_summary] updated successfully....");
		
		updateLastUpdateTime(AppNames.LTSRAssitant);
		
	}

	public String getAppName() {
		return AppNames.SessionRecording;
	}

}
